package game.server;

import game.controller.Region.Region;
import game.controller.Region.LandRegion;
import game.controller.Unit.*;
import game.util.GameUtilities;
import lobby.packet.OpCode;
import lobby.packet.StartGamePacket;
import lobby.packet.JoinLobbyPacket;
import lobby.controller.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.util.ArrayList;

import game.util.GameServerLogger;
import game.util.PoliticalUtilities;
import game.controller.GameInstance;
import game.controller.UnitController;
import game.packet.*;
import game.packet.political.*;
import game.packet.political.SueForPeacePacket;
import util.Utilities;
import util.Ports;

/**
 * GameServerThread.java  Date Created: Oct 30, 2012
 *
 * Purpose: This is the lobby.server thread that will listen for client requests.
 *
 * Description:  This is where all the magic happens.  When a client makes
 * a request it comes here and gets handled according to what it received.
 * Anything from creating a game lobby, to sending chat, to changing a game option
 * will be handled in this thread.
 *
 * @author Chrisb
 */
public class GameServerThread extends Thread {
    //Objects used by the lobby.server
    protected DatagramSocket socket;
    protected DatagramPacket returnPacket;
    protected GenericPacket genericPacket;
    protected GameInstance gameInstance;
    protected InetAddress gameAddress;
    protected InetAddress address;
    protected int port;
    protected ArrayList<GameInstance> gameList;
    protected byte[] incoming;
    protected byte[] outgoing;
    protected boolean userExists;

    /*
     * Constructors
     */
    public GameServerThread() throws IOException {
        this("GameServerThread");
    }

    public GameServerThread(String name) throws IOException {
        super(name);
        socket = new DatagramSocket(Ports.GAME_SERVER_PORT);
        gameList = new ArrayList<GameInstance>();
    }

    /**
     * Magic happens here..
     * Every request made comes to this method to be handled.
     */
    public void run() {
        while (true) {
            try {
                System.out.println("running...");
                //Reset all relavent variables
                incoming = new byte[256];
                outgoing = new byte[256];
                userExists = false;
                gameAddress = null;
                returnPacket = null;
                genericPacket = null;
                gameInstance = null;

                //Receive a game.packet (or maybe lobby.packet)
                DatagramPacket packet = new DatagramPacket(incoming, incoming.length);
                socket.receive(packet);

                System.out.println("gameServer packet received");

                //Obtain port and address of the sender.
                address = packet.getAddress();
                port = packet.getPort();

                if (incoming[0] == OpCode.START_GAME.valueOf()) {
                    /*
                     * PRE-GAME: A new game has just been started.  Add it to the list of currently active games.
                     */
                    System.out.println("game.server START_GAME");
                    StartGamePacket sgp = new StartGamePacket(incoming);
                    gameList.ensureCapacity(gameList.size() + 1);
                    GameInstance newGame = new GameInstance(sgp.getLobbyId(), sgp.getOptions(), sgp.getMSD());
                    gameList.add(newGame);
                    //END
                } else if (incoming[0] == OpCode.JOIN.valueOf()) {
                    /*
                     * PRE-GAME: A game has been started, add these users to the game.
                     */
                    System.out.println("game.server JOIN");
                    JoinLobbyPacket jlp = new JoinLobbyPacket(incoming);
                    for (GameInstance game : gameList)
                        if (Utilities.compareLobbyIds(game.getGameId(), jlp.getLobbyId()))
                            game.addUsers(jlp.getUsers());
                    //END
                } else {
                    outgoing = incoming;
                    genericPacket = new GenericPacket(incoming);
                    gameAddress = Utilities.idAsInetAddress(genericPacket.getGameId());

                    for (GameInstance game : gameList)
                        if (Utilities.compareLobbyIds(genericPacket.getGameId(), game.getGameId()))
                            gameInstance = game;
                }

                if (gameInstance != null) {
                    if (incoming[0] == GameOps.GAME_INFO.valueOf()) {
                        //A new game is being requested to start.  If lobby Id is not in use it can start.
                        System.out.println("game.server GAME_INFO");
                        GenericPacket gp = new GenericPacket(incoming);
                        //Send Game Options
                        outgoing = new StartGamePacket(gameInstance.getGameId(), gameInstance.getAllOptions(), gameInstance.getMSD(), gp.getNation()).getPacket();
                        //END
                    } else if (incoming[0] == GameOps.GAME_USERS.valueOf()) {
                        /*
                         * Users of a game have been requested.  Send the list of users in game with matching game Id.
                         */
                        System.out.println("game.server GAME_USERS");
                        GenericPacket gp = new GenericPacket(incoming);
                        gameInstance.setupGame();
                        //Send Users
                        if (!gameInstance.isGameFull()) {
                            //One Packet
                            outgoing = new JoinLobbyPacket(gameInstance.getGameId(), gameInstance.getUsers()).getPacket();
                            DatagramPacket addUsersPacket = new DatagramPacket(outgoing, outgoing.length, gameAddress, Ports.GAME_CLIENT_PORT);
                            socket.send(addUsersPacket);
                        } else {
                            //Two Packets
                            ArrayList<User> firstSixUsers = new ArrayList<User>(gameInstance.getUsers().subList(0,6));
                            outgoing = new JoinLobbyPacket(gameInstance.getGameId(), firstSixUsers).getPacket();
                            DatagramPacket firstSixPacket = new DatagramPacket(outgoing, outgoing.length, gameAddress, Ports.GAME_CLIENT_PORT);
                            socket.send(firstSixPacket);

                            outgoing = new JoinLobbyPacket(gameInstance.getGameId(), gameInstance.getUsers().get(6)).getPacket();
                            DatagramPacket lastUserPacket = new DatagramPacket(outgoing, outgoing.length, gameAddress, Ports.GAME_CLIENT_PORT);
                            socket.send(lastUserPacket);
                        }
                        outgoing = new GameReadyPacket(gp.getGameId(), gp.getNation()).getPacket();
                        //END
                    } else if (incoming[0] == GameOps.PLACE_UNITS.valueOf()) {
                        /*
                         * Units have been placed on the map.  Update the servers instance of the map.
                         */
                        System.out.println("game.server PLACE_UNITS");
                        GamePlaceUnitsPacket placeUnits = new GamePlaceUnitsPacket(incoming);

                        Region region = gameInstance.getMap().getRegionFromIndex(placeUnits.getRegionIndex(), placeUnits.getRegionType());
                        ArrayList<MilitaryUnit> units = Utilities.getUnitsFromPacket(placeUnits);
                        boolean unitsAdded = UnitController.moveAllUnits(units, gameInstance.getMap().getRegion(region.toString()));

                        if (!unitsAdded) {
                            GameServerLogger.log("Unable to place units to region: " + region.toString() + " for nation: " + placeUnits.getOwningNation());
                            gameAddress = null; //todo if units aren't added.
                        }
                        //END
                    } else if (incoming[0] == GameOps.UNITS_PLACED.valueOf()) {
                        //Adding units have been finished for a specific nation.
                        System.out.println("game.server ALL_UNITS_PLACED");
                        //END
                    } else if (incoming[0] == GameOps.ADD_UNITS.valueOf()) {
                        System.out.println("game.server ADD_UNITS");
                        GameAddUnitsPacket addUnits = new GameAddUnitsPacket(incoming);
                        GameUtilities.doPurchaseUnits(addUnits, gameInstance.getNation(addUnits.getOwningNation()));
                        //END
                    /*
                     * Political Actions
                     */
                    } else if (incoming[0] == GameOps.DECLARE_WAR.valueOf()) {
                        System.out.println("game.server DECLARE_WAR");
                        WarDeclarationPacket warDec = new WarDeclarationPacket(incoming);
                        gameInstance.getNation(warDec.getDeclaringNation()).declareWar(warDec.getEnemyNation(), warDec.getPapCost(), true);
                        gameInstance.getNation(warDec.getEnemyNation()).declareWar(warDec.getDeclaringNation(), 0, false);
                        //END
                    } else if (incoming[0] == GameOps.SUE_FOR_PEACE.valueOf()) {
                        System.out.println("game.server SUE FOR PEACE");
                        SueForPeacePacket sfp = new SueForPeacePacket(incoming);
                        PoliticalUtilities.doSueForPeace(sfp.getSueingNation(), gameInstance);
                    } else if (incoming[0] == GameOps.NATION_TO_CONTROL.valueOf()) {
                        //Related to War Dec
                        System.out.println("game.server NATION_TO_CONTROL");
                        NationToControlPacket ntc = new NationToControlPacket(incoming);
                        for (User u : gameInstance.getUsers()) {
                            if (u.getNation() == ntc.getUserToControl()
                                    || u.getControlledNPNs().contains(ntc.getUserToControl()))
                                PoliticalUtilities.doControlNPN(gameInstance.getNation(ntc.getUncontrolledNation()), u, null,
                                        gameInstance.getUserByNPN(ntc.getUncontrolledNation()));
                        }
                        //END
                    } else if (incoming[0] == GameOps.MULTI_NATION.valueOf()) {
                        //Related to War Dec
                        System.out.println("game.server MULTI_NATION");
                        //END
                    } else if (incoming[0] == GameOps.RETALIATION_WAR.valueOf()) {
                        //Related to War Dec
                        System.out.println("game.server RETALIATION_WAR");
                        RetaliationWarDecPacket warRetaliation = new RetaliationWarDecPacket(incoming);
                        if (warRetaliation.isDeclaring()) {
                            gameInstance.getNation(warRetaliation.getRetaliator()).declareWar(warRetaliation.getInitDeclarer(), warRetaliation.getPapCost(), true);
                            gameInstance.getNation(warRetaliation.getInitDeclarer()).declareWar(warRetaliation.getRetaliator(), 0, false);
                        }
                        //END
                    } else if (incoming[0] == GameOps.SUE_FOR_PEACE.valueOf()) {
                        SueForPeacePacket sfp = new SueForPeacePacket(incoming);
                        ArrayList<Integer> congressNations = PoliticalUtilities.doSueForPeace(sfp.getSueingNation(), gameInstance);
                        PoliticalUtilities.spoilsOfWar(congressNations, gameInstance.getNation(sfp.getSueingNation()));
                        //END
                    } else if (incoming[0] == GameOps.FORM_ALLIANCE.valueOf()) {
                        System.out.println("game.server FORM ALLIANCE");
                        FormAlliancePacket fap = new FormAlliancePacket(incoming);
                        //Alliance Accepted
                        if ( !fap.isRequest() && fap.hasAccepted() ) {
                            int nationOne = fap.getNationOne();
                            int nationTwo = fap.getNationTwo();
                            gameInstance.getNation(nationOne).formAlliance(nationTwo);
                            gameInstance.getNation(nationTwo).formAlliance(nationOne);
                        }
                        //Alliance Rejected do nothing, just forward onto clients.
                        //Alliance Requested do nothing, just forward onto clients.
                        //END
                    } else if (incoming[0] == GameOps.BREAK_ALLIANCE.valueOf()) {
                        System.out.println("game.server BREAK ALLIANCE");
                        BreakAlliancePacket bap = new BreakAlliancePacket(incoming);
                        PoliticalUtilities.doBreakAlliance(gameInstance.getNation(bap.getBreakingNation()), gameInstance.getNation(bap.getAlliedNation()));
                        //END
                    } else if (incoming[0] == GameOps.ARMISTICE.valueOf()) {
                        System.out.println("game.server CONCLUDE ARMISTICE");
                        ConcludeArmisticePacket cap = new ConcludeArmisticePacket(incoming);
                        //Armistice Accepted
                        if ( !cap.isRequest() && cap.hasAccepted() )
                            PoliticalUtilities.doConcludeArmistice(gameInstance.getNation(cap.getNationOne()), gameInstance.getNation(cap.getNationTwo()));
                        //Armistice Rejected do nothing, just forward onto clients.
                        //Armistice Requested do nothing, just forward onto clients.
                        //END
                    } else if (incoming[0] == GameOps.GRANT_PASSAGE.valueOf()) {
                        System.out.println("game.server GRANT PASSAGE");
                        GrantPassagePacket gpp = new GrantPassagePacket(incoming);
                        PoliticalUtilities.doGrantPassage(gameInstance.getNation(gpp.getGrantingNation()), gpp.getReceivingNation(), gpp.isVoluntary());
                        //END
                    } else if (incoming[0] == GameOps.RESCIND_PASSAGE.valueOf()) {
                        System.out.println("game.server RESCIND PASSAGE");
                        RescindPassagePacket rpp = new RescindPassagePacket(incoming);
                        PoliticalUtilities.doRescindPassage(gameInstance.getNation(rpp.getRescindingNation()), rpp.getLosingNation());
                        //END
                    } else if (incoming[0] == GameOps.CONTROL_NPN.valueOf()) {
                        System.out.println("game.server CONTROL NPN");
                    } else if (incoming[0] == GameOps.RELEASE_NPN.valueOf()) {
                        System.out.println("game.server RELEASE_NPN");
                        ReleaseNPNPacket rnp = new ReleaseNPNPacket(incoming);
                        PoliticalUtilities.doReleaseNPN( gameInstance.getNation(rnp.getNonPlayerNation()), gameInstance.getUserByNation(rnp.getPlayerNation()),
                                null, gameInstance.getMonth(), gameInstance.getYear());
                    } else if (incoming[0] == GameOps.FAIL_CONTROL_NPN.valueOf()) {
                        System.out.println("game.server FAIL_CONTROL NPN");
                    } else if (incoming[0] == GameOps.SETUP_DIPLOMAT.valueOf()) {
                        System.out.println("game.server SETUP_DIPLOMAT");
                    } else if (incoming[0] == GameOps.NEXT_DIPLOMAT.valueOf()) {
                        System.out.println("game.server NEXT_DIPLOMAT");
                    } else if (incoming[0] == GameOps.CONGRESS_ANNEX.valueOf()) {
                        System.out.println("game.server CONGRESS_ANNEX");
                        CongressActionPacket cap = new CongressActionPacket(incoming);
                        PoliticalUtilities.doCongressAnnex( gameInstance.getNation(cap.getSueingNation()), gameInstance.getNation(cap.getActionNation()),
                                (LandRegion)gameInstance.getMap().getRegionFromIndex(cap.getRegionOneIndex(), Region.LAND_REGION) );
                        if ( gameInstance.getNation(cap.getSueingNation()).isExtinct() )
                            PoliticalUtilities.makeNationExtinct( gameInstance.getNation(cap.getSueingNation()) );
                    } else if (incoming[0] == GameOps.CONGRESS_RESTORE.valueOf()) {
                        System.out.println("game.server CONGRESS_RESTORE");
                        CongressActionPacket cap = new CongressActionPacket(incoming);
                        PoliticalUtilities.doCongressRestore( gameInstance.getNation(cap.getSueingNation()),
                                (LandRegion)gameInstance.getMap().getRegionFromIndex(cap.getRegionOneIndex(), Region.LAND_REGION) );
                        if (cap.getRegionTwoIndex() > 0) {
                            PoliticalUtilities.doCongressRestore( gameInstance.getNation(cap.getSueingNation()),
                                (LandRegion)gameInstance.getMap().getRegionFromIndex(cap.getRegionTwoIndex(), Region.LAND_REGION) );
                        }
                        if ( gameInstance.getNation(cap.getSueingNation()).isExtinct() )
                            PoliticalUtilities.makeNationExtinct( gameInstance.getNation(cap.getSueingNation()) );
                    } else if (incoming[0] == GameOps.CONGRESS_FREESERF.valueOf()) {
                        System.out.println("game.server CONGRESS_FREESERF");
                        CongressActionPacket cap = new CongressActionPacket(incoming);
                        PoliticalUtilities.doCongressFreeSerfs( (LandRegion)gameInstance.getMap().getRegionFromIndex(cap.getRegionOneIndex(), Region.LAND_REGION) );
                        if (cap.getRegionTwoIndex() > 0) {
                            PoliticalUtilities.doCongressFreeSerfs( (LandRegion)gameInstance.getMap().getRegionFromIndex(cap.getRegionTwoIndex(), Region.LAND_REGION) );
                        }
                        if (cap.getRegionThreeIndex() > 0) {
                            PoliticalUtilities.doCongressFreeSerfs( (LandRegion)gameInstance.getMap().getRegionFromIndex(cap.getRegionThreeIndex(), Region.LAND_REGION) );
                        }
                    } else if (incoming[0] == GameOps.CONGRESS_PASS.valueOf()) {
                        System.out.println("game.server CONGRESS_PASS");
                    } else if (incoming[0] == GameOps.CONGRESS_MOVENPN.valueOf()) {
                        System.out.println("game.server CONGRESS_MOVENPN");
                        MoveNPNUnitsPacket mnu = new MoveNPNUnitsPacket(incoming);
                        PoliticalUtilities.moveNPNMilitary( gameInstance.getNation(mnu.getSueingNation()), gameInstance, null );
                    /*
                     * END Political Actions
                     */
                    } else if (incoming[0] == GameOps.CHAT.valueOf()) {
                        //A game chat packet has been received.
                        System.out.println("game.server CHAT");
                        //END
                    }
                    returnPacket = new DatagramPacket(outgoing, outgoing.length, gameAddress, Ports.GAME_CLIENT_PORT);
                } else {
                    outgoing = new GameNotFoundPacket(genericPacket.getGameId(), genericPacket.getOpCode()).getPacket();
                    returnPacket = new DatagramPacket(outgoing, outgoing.length, address, port);
                }

                //Send Packet if necessary
                if (gameAddress != null)
                    socket.send(returnPacket);

            } catch (IOException e) {
                System.err.println("Game Server Thread IOerror.");
                e.printStackTrace();
                GameServerLogger.log("GameServerThread - " + e.getMessage());
                break;
            } catch (Exception e) {
                System.err.println("Game Server Thread has thrown an Error");
                e.printStackTrace();
                GameServerLogger.log("GameServerThread - " + e.getMessage());
            }
        }
    }
}