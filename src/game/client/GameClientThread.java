package game.client;

import game.client.political.WarDeclarationThread;
import game.controller.GameController;
import game.controller.Unit.MilitaryUnit;
import game.packet.*;
import game.packet.political.*;
import game.packet.political.CongressActionPacket;
import game.packet.political.SueForPeacePacket;
import game.util.GameLogger;
import game.util.MessageDialog;
import game.util.PoliticalUtilities;
import lobby.packet.JoinLobbyPacket;
import lobby.packet.OpCode;
import lobby.packet.StartGamePacket;
import util.Messages;
import util.Ports;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

/**
 * GameClientThread.java  Date Created: Oct 31, 2012
 *
 * Purpose: To receive any and all messages from the game server.
 *
 * Description: depending on the packet received call the appropriate controller method.
 *
 * @author Chrisb
 */
public class GameClientThread extends Thread {
    GameController controller;
    private byte[] outgoing;
    private InetAddress gameAddress;
    private MulticastSocket socket;
    private DatagramPacket packet;

    private volatile Thread running;

    public GameClientThread(InetAddress address, GameController controller) {
        try {
            System.out.println("GameClientThread created");
            this.controller = controller;
            gameAddress = InetAddress.getByName(Messages.getString("game.server.address"));
            socket = new MulticastSocket(Ports.GAME_CLIENT_PORT);
            socket.joinGroup(address);
            running = this;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestGameInfo(byte[] gameId, int nation) {
        try {
            System.out.println("GameClientThread:requestGameInfo");

            outgoing = new GenericPacket(GameOps.GAME_INFO.valueOf(), gameId, nation).getPacket();
            packet = new DatagramPacket(outgoing, outgoing.length, gameAddress, Ports.GAME_SERVER_PORT);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
            GameLogger.log("GameClientThread:requestGameInfo - " + e.getMessage());
        }
    }

    public void requestGameUsers(byte[] gameId, int nation) {
        try {
            System.out.println("GameClientThread:requestGameUsers");

            outgoing = new GenericPacket(GameOps.GAME_USERS.valueOf(), gameId, nation).getPacket();
            packet = new DatagramPacket(outgoing, outgoing.length, gameAddress, Ports.GAME_SERVER_PORT);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
            GameLogger.log("GameClientThread:requestGameUsers - " + e.getMessage());
        }
    }

    public void sendChatMessage(byte[] gameId, int fromNation, int toNation, String message) {
        try {
            System.out.println("GameClientThread:sendChatMessage");

            outgoing = new GameChatPacket(gameId, fromNation, toNation, message).getPacket();
            packet = new DatagramPacket(outgoing, outgoing.length, gameAddress, Ports.GAME_SERVER_PORT);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
            GameLogger.log("GameClientThread:sendChatMessage - " + e.getMessage());
        }
    }

    public void addUnitsToRegion(byte[] gameId, int regionIndex, int regionType, ArrayList<MilitaryUnit> units) {
        try {
            System.out.println("GameClientThread:addUnitsToRegion");
            outgoing = new GamePlaceUnitsPacket(gameId, regionIndex, regionType, units).getPacket();
            packet = new DatagramPacket(outgoing, outgoing.length, gameAddress, Ports.GAME_SERVER_PORT);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
            GameLogger.log("GameClientThread:addUnitsToRegion - " + e.getMessage());
        }
    }

    @Override
    public void run() {
        Thread thisThread = Thread.currentThread();
        while (running == thisThread) {
            try {
                byte[] incoming = new byte[256];
                packet = new DatagramPacket(incoming, incoming.length);
                socket.receive(packet);
                System.out.println("gameClient packet received");

                if (incoming[0] == OpCode.START_GAME.valueOf()) {
                    System.out.println("gameClient START_GAME");
                    StartGamePacket sgp = new StartGamePacket(incoming);
                    controller.createGame(sgp.getLobbyId(), sgp.getOptions(), sgp.getMSD());
                } else if (incoming[0] == OpCode.JOIN.valueOf()) {
                    System.out.println("gameClient JOIN");
                    JoinLobbyPacket jlp = new JoinLobbyPacket(incoming);
                    controller.addUsers(jlp.getLobbyId(), jlp.getUsers());
                } else if (incoming[0] == GameOps.GAME_READY.valueOf()) {
                    System.out.println("gameClient makeGameReady");
                    GameReadyPacket grp = new GameReadyPacket(incoming);
                    controller.makeGameReady(grp.getGameId());
                } else if (incoming[0] == GameOps.PLACE_UNITS.valueOf()) {
                    System.out.println("gameClient placeUnits");
                    GamePlaceUnitsPacket pup = new GamePlaceUnitsPacket(incoming);
                    controller.processUnitPlacement(pup);
                } else if (incoming[0] == GameOps.UNITS_PLACED.valueOf()) {
                    System.out.println("gameClient unitsPlaced");
                    AllUnitsPlacedPacket aua = new AllUnitsPlacedPacket(incoming);
                    controller.allUnitsPlaced(aua.getNation());
                } else if (incoming[0] == GameOps.ADD_UNITS.valueOf()) {
                    System.out.println("gameClient addAllUnits");
                    GameAddUnitsPacket gau = new GameAddUnitsPacket(incoming);
                    controller.doPurchaseUnits(gau, gau.getOwningNation());
                } else if (incoming[0] == GameOps.DECLARE_WAR.valueOf()) {
                    System.out.println("gameClient warDeclaration");
                    WarDeclarationThread.parseWarDeclaration(new WarDeclarationPacket(incoming), controller);
                } else if (incoming[0] == GameOps.SUE_FOR_PEACE.valueOf()) {
                    System.out.println("gameClient sueForPeace");
                    SueForPeacePacket sfp = new SueForPeacePacket(incoming);
                    controller.doSueForPeace(sfp.getSueingNation());
                } else if (incoming[0] == GameOps.NATION_TO_CONTROL.valueOf()) {
                    System.out.println("gameClient NationToControl");
                    NationToControlPacket ntc = new NationToControlPacket(incoming);
                    controller.doControlNonPlayerNation(ntc.getDeclaringNation(), ntc.getUncontrolledNation(),
                            ntc.getUserToControl(), ntc.isNotification());
                } else if (incoming[0] == GameOps.MULTI_NATION.valueOf()) {
                    System.out.println("gameClient multiNationControl");
                    MultiNationPacket mnp = new MultiNationPacket(incoming);
                    controller.controlNationRequest(mnp.getDeclaringNation(), mnp.getUncontrolledNation(),mnp.getUserNations());
                } else if (incoming[0] == GameOps.RETALIATION_WAR.valueOf()) {
                    System.out.println("gameClient retaliation war dec");
                    RetaliationWarDecPacket rwd = new RetaliationWarDecPacket(incoming);
                    controller.retaliationWarDeclaration(rwd.getInitDeclarer(), rwd.getRetaliator(), rwd.isDeclaring(),
                            rwd.getPapCost(), rwd.getUncontrolledNation());
                } else if (incoming[0] == GameOps.ARMISTICE.valueOf()) {
                    System.out.println("gameClient armistice");
                    ConcludeArmisticePacket cap = new ConcludeArmisticePacket(incoming);
                    if ( !cap.isRequest() && cap.hasAccepted() )  //Armistice Accepted
                        controller.doConcludeArmistice(cap.getNationOne(), cap.getNationTwo());
                    else if ( !cap.isRequest() && !cap.hasAccepted() )  //Armistice Rejected tell client.
                        if (controller.isNationControlledByUser(cap.getNationOne()))
                            MessageDialog.armisticeRejected(controller.getDisplay().getMap(), cap.getNationOne(), cap.getNationTwo());
                    else if ( cap.isRequest() )  //Armistice Requested ask client.
                        controller.getDisplay().armisticeRequested(cap.getNationOne(), cap.getNationTwo());
                } else if (incoming[0] == GameOps.FORM_ALLIANCE.valueOf()) {
                    System.out.println("gameClient form alliance");
                    FormAlliancePacket fap = new FormAlliancePacket(incoming);
                    if ( !fap.isRequest() && fap.hasAccepted() )  //Alliance Accepted.
                        controller.doFormAlliance(fap.getNationOne(), fap.getNationTwo());
                    else if ( !fap.isRequest() && !fap.hasAccepted() ) //Alliance Rejected tell client.
                        controller.allianceRejected(fap.getNationOne(), fap.getNationTwo());
                    else if ( fap.isRequest() ) //Alliance Requested ask client.
                        controller.getDisplay().allianceRequested(fap.getNationOne(), fap.getNationTwo());
                } else if (incoming[0] == GameOps.BREAK_ALLIANCE.valueOf()) {
                    System.out.println("gameClient break alliance");
                    BreakAlliancePacket bap = new BreakAlliancePacket(incoming);
                    controller.doBreakAlliance(bap.getBreakingNation(), bap.getAlliedNation());
                } else if (incoming[0] == GameOps.GRANT_PASSAGE.valueOf()) {
                    System.out.println("gameClient grant passage");
                    GrantPassagePacket gpp = new GrantPassagePacket(incoming);
                    controller.doGrantPassage(gpp.getGrantingNation(), gpp.getReceivingNation(), gpp.isVoluntary());
                } else if (incoming[0] == GameOps.RESCIND_PASSAGE.valueOf()) {
                    System.out.println("gameClient rescind passage");
                    RescindPassagePacket rpp = new RescindPassagePacket(incoming);
                    controller.doRescindPassage(rpp.getRescindingNation(), rpp.getLosingNation());
                } else if (incoming[0] == GameOps.CONTROL_NPN.valueOf()) {
                    ControlNPNPacket npn = new ControlNPNPacket(incoming);
                    System.out.println("gameClient control NPN " + npn.getPermission());
                    //NPN is controlled, asked if controlling nation wants to give permission
                    if (npn.getPermission() == 2 && npn.isControlled())
                        controller.requestPermissionToControlNPN(npn.getPlayerNation(), npn.getNonPlayerNation());
                    //NPN is controlled, permission has been granted/denied, confirm and roll.
                    if ( (npn.getPermission() == 0 || npn.getPermission() == 1) && npn.isControlled())
                        controller.rollToControlNPN(npn.getPlayerNation(), npn.getNonPlayerNation(), npn.hasPermission());
                } else if (incoming[0] == GameOps.RELEASE_NPN.valueOf()) {
                    ReleaseNPNPacket rnp = new ReleaseNPNPacket(incoming);
                    controller.doReleaseNPN(rnp.getNonPlayerNation(), rnp.getPlayerNation());
                } else if (incoming[0] == GameOps.FAIL_CONTROL_NPN.valueOf()) {
                    System.out.println("gameClient fail control NPN");
                    EventFailControlNPNPacket fcn = new EventFailControlNPNPacket(incoming);
                    controller.failControlNPNEvent(fcn.getUserNation(), fcn.getNonPlayerNation(), fcn.getMonth(), fcn.getYear());
                } else if (incoming[0] == GameOps.SETUP_DIPLOMAT.valueOf()) {
                    System.out.println("gameClient setup diplomat round");
                    SetupDiplomaticRoundPacket sdr = new SetupDiplomaticRoundPacket(incoming);
                    controller.setupDiplomaticRound(sdr.getType(), sdr.getDoNation(), sdr.getToNation());
                } else if (incoming[0] == GameOps.NEXT_DIPLOMAT.valueOf()) {
                    System.out.println("gameClient next diplomat turn");
                    controller.nextDiplomatTurn();
                } else if (incoming[0] == GameOps.CONGRESS_ANNEX.valueOf()) {
                    System.out.println("gameClient congress annex");
                    CongressActionPacket cap = new CongressActionPacket(incoming);
                    controller.congressAnnex(cap.getSueingNation(), cap.getTurnIndex(), cap.getRegionOneIndex(), cap.getActionNation());
                } else if (incoming[0] == GameOps.CONGRESS_RESTORE.valueOf()) {
                    System.out.println("gameClient congress restore");
                    CongressActionPacket cap = new CongressActionPacket(incoming);
                    controller.congressRestore(cap.getSueingNation(), cap.getTurnIndex(), cap.getRegionOneIndex(), cap.getRegionTwoIndex());
                } else if (incoming[0] == GameOps.CONGRESS_FREESERF.valueOf()) {
                    System.out.println("gameClient congress free serf");
                    CongressActionPacket cap = new CongressActionPacket(incoming);
                    controller.congressFreeSerfs(cap.getSueingNation(), cap.getTurnIndex(),
                            cap.getRegionOneIndex(), cap.getRegionTwoIndex(), cap.getRegionThreeIndex());
                } else if (incoming[0] == GameOps.CONGRESS_PASS.valueOf()) {
                    System.out.println("gameClient congress pass turn");
                    CongressActionPacket ncn = new CongressActionPacket(incoming);
                    controller.congressPass(ncn.getSueingNation(), ncn.getTurnIndex());
                } else if (incoming[0] == GameOps.CONGRESS_MOVENPN.valueOf()) {
                    MoveNPNUnitsPacket mnu = new MoveNPNUnitsPacket(incoming);
                    if ( mnu.getRegionsSize() > 0 || mnu.getPortsInSize() > 0 || mnu.getPortsOutSize() > 0 )
                        controller.getDisplay().moveNPNUnits(mnu.getSueingNation(), mnu.getRegionsToMove(), mnu.getPortsIn(), mnu.getPortsOut());
                    else
                        PoliticalUtilities.endPeaceCongress(controller.getNationInstance(mnu.getSueingNation()), controller.game, controller);
                } else if (incoming[0] == GameOps.CHAT.valueOf()) {
                    GameChatPacket gcp = new GameChatPacket(incoming);
                    System.out.println("gameClient chat received from: " + gcp.getFromNation() + " to: " + gcp.getToNation());
                    controller.receiveChatMessage(gcp.getGameId(), gcp.getFromNation(), gcp.getToNation(), gcp.getChatMsg());
                } else if (incoming[0] == GameOps.GAME_NOT_FOUND.valueOf()) {
                    System.err.println("gameClient Game Not Found");
                    GameNotFoundPacket gnf = new GameNotFoundPacket(incoming);
                    gnf.getRequestedGameId();
                    gnf.getRequestedOpCode();
                    //todo resend?
                } else {
                    System.err.println("gameClient unknown packet received");
                }

            } catch (IOException e) {
                e.printStackTrace();
                GameLogger.log("Error occured in GameClientThread - " + e.getMessage());
            }
        }
    }

    public void stopThread() {
        running = null;
    }

}