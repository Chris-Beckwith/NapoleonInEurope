package lobby.server;

import lobby.controller.LobbyInstance;
import lobby.controller.Nation;
import lobby.controller.User;
import shared.controller.LobbyConstants;
import lobby.packet.*;
import util.Messages;
import util.ServerLogger;
import util.Utilities;
import util.Ports;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import org.joda.time.DateTime;

/**
 * NapoleonServerThread.java  Date Created: Mar 30, 2012
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
public class NapoleonServerThread extends Thread {
    //Objects used by the lobby.server
    protected DatagramSocket socket;
    protected InetAddress mcAddress;
    protected InetAddress address;
    protected int port;
    protected ArrayList<LobbyInstance> lobbyList;
    protected byte[] incoming;
    protected byte[] outgoing;
    protected boolean lobbyExists;
    protected boolean userExists;

    /*
       Contructors
     */
    public NapoleonServerThread() throws IOException {
        this("NapoleonServerThread");
    }

    public NapoleonServerThread(String name) throws IOException {
        super(name);
        socket = new DatagramSocket(Ports.LOBBY_SERVER_PORT);
        lobbyList = new ArrayList<LobbyInstance>();
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
                lobbyExists = false;
                userExists = false;
                mcAddress = null;

                //Receive a lobby.packet
                DatagramPacket packet = new DatagramPacket(incoming, incoming.length);
                socket.receive(packet);

                System.out.println("lobby.packet received");

                //Obtain port and address of the sender.
                address = packet.getAddress();
                port = packet.getPort();

                //Check the first byte of the lobby.packet, this tells the lobby.server how to respond.
                if (incoming[0] == OpCode.LOGIN.valueOf()) {
                    /*
                     *  Login Packet Received
                     */

                    LoginPacket lp = new LoginPacket(incoming);
                    //TODO check database and get UserId and loginFlag = true
                    System.out.println(lp.getUserName());

                    //Create loginPacket and send it.
                    outgoing = lp.getPacket();
                    packet = new DatagramPacket(outgoing, outgoing.length, address, port);
                    socket.send(packet);
                    //END
                } else if (incoming[0] == OpCode.LOGOUT.valueOf()) {
                    /*
                     * Logout Packet Received
                     */
                    LogoutPacket lp = new LogoutPacket(incoming);

                    //TODO Update loginFlag in database

                    //Respond
                    outgoing = lp.getPacket();
                    packet = new DatagramPacket(outgoing, outgoing.length, address, port);
                    socket.send(packet);
                    //END
                } else if (incoming[0] == OpCode.CREATE.valueOf()) {
                    /*
                     * Create Lobby Packet Received
                     */
                    System.out.println("create: lobby.packet");
                    CreateLobbyPacket clp = new CreateLobbyPacket(incoming);
                    //Check if lobbyName already exsists.
                    for(LobbyInstance l: lobbyList) {
                        if (l.getLobbyName().compareTo(clp.getLobbyName()) == 0) {
                            //Lobby name already used.
                            //Fail request - error LobbyNameInUse;
                            System.out.println("create: name already exists");
                            outgoing = new FailurePacket(clp.getLobbyId(), OpCode.LOBBY_EXISTS.valueOf()).getPacket();
                            packet = new DatagramPacket(outgoing, outgoing.length, address, port);
                            socket.send(packet);
                            lobbyExists = true;
                            break;
                        }
                    }
                    //If not, Host game.  (Add to list of games)
                    if (!lobbyExists) {
                        System.out.println("create: add new lobby");
                        byte[] lobbyId = generateLobbyId();

                        LobbyInstance lobby = new LobbyInstance(lobbyId, clp.getLobbyName(), Ports.LOBBY_MC_PORT);
                        User user = clp.getUser();
                        user.setPosition(lobby.getFirstOpenPosition());
                        lobby.addLobbyUser(user);

                        System.out.println("lobbySize before: " + lobbyList.size());
                        lobbyList.ensureCapacity(lobbyList.size() + 1);
                        lobbyList.add(lobby);
                        System.out.println("lobbySize after: " + lobbyList.size());

                        //Send response
                        outgoing = new CreateLobbyPacket(clp.getLobbyName(), lobbyId, clp.getUser()).getPacket();
                        packet = new DatagramPacket(outgoing, outgoing.length, address, port);

                        System.out.println("create: lobby.packet created and ready to send, opCode: " + outgoing[0] + " " + packet.getData()[0]);

                        socket.send(packet);
                    }
                    //END
                } else if (incoming[0] == OpCode.JOIN.valueOf()) {
                    /*
                     * Join Lobby Packet Received
                     */
                    System.out.println("join: lobby.packet");
                    JoinLobbyPacket jlp = new JoinLobbyPacket(incoming);
                    //Check if lobby is full
                    for (LobbyInstance l: lobbyList) {
                        if (Utilities.compareLobbyIds(l.getLobbyId(), jlp.getLobbyId())) {
                            System.out.println("join: lobby exists");
                            lobbyExists = true;
                            if (l.isLobbyFull()) {
                                System.out.println("join: lobby is full");
                                outgoing = new FailurePacket(l.getLobbyId(), OpCode.LOBBY_FULL.valueOf()).getPacket();
                                packet = new DatagramPacket(outgoing, outgoing.length, address, port);
                                socket.send(packet);
                                break;
                            } else if (l.isStarting()) {
                                outgoing = new FailurePacket(l.getLobbyId(), OpCode.STARTING.valueOf()).getPacket();
                                packet = new DatagramPacket(outgoing, outgoing.length, address, port);
                                socket.send(packet);
                                break;
                            } else {
                                //If not, send response lobby.packet with users currently in lobby.
                                System.out.println("join: lobby is NOT full");
                                outgoing = new JoinLobbyPacket(l.getLobbyId(), l.getLobbyUsers()).getPacket();
                                packet = new DatagramPacket(outgoing, outgoing.length, address, port);
                                socket.send(packet);

                                outgoing = new JoinSettingsPacket(l.getLobbyId(), l.getReadyStates(), l.getGameOptions(), l.getMSD()).getPacket();
                                packet = new DatagramPacket(outgoing, outgoing.length, address, port);
                                socket.send(packet);

                                outgoing = new JoinLobbyPacket(l.getLobbyId(), jlp.getUsers().get(0)).getPacket();
                                packet = new DatagramPacket(outgoing, outgoing.length, l.getInetAddress(), Ports.LOBBY_MC_PORT);
                                socket.send(packet);

                                User userToAdd = jlp.getUsers().get(0);
                                userToAdd.setPosition(l.getFirstOpenPosition());
                                l.addLobbyUser(userToAdd);

                                break;
                            }
                        }
                    }
                    if (!lobbyExists) {
                        //Could not find lobby
                        System.out.println("join: could not find lobby");
                        outgoing = new FailurePacket(jlp.getLobbyId(), jlp.getOpCode()).getPacket();
                        packet = new DatagramPacket(outgoing, outgoing.length, address, port);
                        socket.send(packet);
                    }
                    //END
                } else if (incoming[0] == OpCode.LEAVE_LOBBY.valueOf()) {
                    /*
                     * Leave Lobby Packet Received
                     */
                    LeaveLobbyPacket llp = new LeaveLobbyPacket(incoming);
                    boolean isStarted = false;

                    //Find 'lobby' which 'user' wishes to leave
                    for (LobbyInstance l: lobbyList) {
                        if (Utilities.compareLobbyIds(l.getLobbyId(), llp.getLobbyId())) {
                            lobbyExists = true;
                            //Find 'user' in lobby
                            for (User u: l.getLobbyUsers()) {
                                if (u.getUserName().compareTo(llp.getUserName()) == 0) {
                                    userExists = true;
                                    l.removeLobbyUser(u);
                                    if (l.isStarting()) {
                                        isStarted = true;
                                        l.stopCountdown();
                                    }
                                    mcAddress = l.getInetAddress();

                                    break;
                                }
                            }
                            if (l.isLobbyEmpty())
                                lobbyList.remove(l);
                            break;
                        }
                    }
                    if (!lobbyExists)
                        outgoing = new FailurePacket(llp.getLobbyId(), OpCode.LOBBY_EXISTS.valueOf()).getPacket();
                    else if (!userExists)
                        outgoing = new FailurePacket(llp.getLobbyId(), OpCode.USER_EXISTS.valueOf()).getPacket();
                    else
                        outgoing = incoming;

                    packet = new DatagramPacket(outgoing, outgoing.length, mcAddress, Ports.LOBBY_MC_PORT);
                    socket.send(packet);

                    if (lobbyExists && userExists && isStarted) {
                        outgoing = new CountdownPacket(llp.getLobbyId(),-1).getPacket();
                        DatagramPacket abortPacket = new DatagramPacket(outgoing, outgoing.length, mcAddress, Ports.LOBBY_MC_PORT);
                        socket.send(abortPacket);
                    }

                    //END
                } else if (incoming[0] == OpCode.GETLOBBYS.valueOf()) {
                    /*
                     * Get Lobby Packet Received
                     */
                    System.out.println("Get Lobby Packet");
                    final int MAX_LOBBYS_PER_PACKET = 7;
                    int numOfLobbys = 0;

                    for (LobbyInstance l : lobbyList)
                        if (!l.isLobbyFull() && !l.isStarting())
                            numOfLobbys++;
                    
                    int numOfPackets = numOfLobbys / MAX_LOBBYS_PER_PACKET;
                    int sizeOfArray = numOfLobbys > MAX_LOBBYS_PER_PACKET ? MAX_LOBBYS_PER_PACKET : numOfLobbys;
                    byte[][] lobbyIds = new byte[sizeOfArray][2];
                    String[] lobbyNames = new String[sizeOfArray];

                    if ( numOfLobbys % MAX_LOBBYS_PER_PACKET > 0 )
                        numOfPackets++;

                    for (int i = 0; i < sizeOfArray; i++) {
                        lobbyIds[i] = lobbyList.get(i).getLobbyId();
                        lobbyNames[i] = lobbyList.get(i).getLobbyName();
                    }

                    if (numOfPackets == 0) {
                        outgoing = new ActiveLobbyPacket().getPacket();
                        packet = new DatagramPacket(outgoing, outgoing.length, address, port);
                        socket.send(packet);
                    } else if (numOfPackets == 1) {
                        outgoing = new ActiveLobbyPacket(numOfLobbys, lobbyIds, lobbyNames).getPacket();
                        packet = new DatagramPacket(outgoing, outgoing.length, address, port);
                        socket.send(packet);
                    } else if (numOfPackets > 1) {
                        //First one
                        outgoing = new ActiveLobbyPacket(MAX_LOBBYS_PER_PACKET, lobbyIds, lobbyNames).getPacket();
                        packet = new DatagramPacket(outgoing, outgoing.length, address, port);
                        socket.send(packet);

                        numOfLobbys = numOfLobbys - MAX_LOBBYS_PER_PACKET;
                        int packetNumber = 1;

                        //Middle Ones
                        while (numOfLobbys > MAX_LOBBYS_PER_PACKET) {
                            byte[][] subsetLobbyIds = new byte[MAX_LOBBYS_PER_PACKET][2];
                            String[] subsetLobbyNames = new String[MAX_LOBBYS_PER_PACKET];
                            int n = 0;

                            for (int i = MAX_LOBBYS_PER_PACKET*packetNumber; i < MAX_LOBBYS_PER_PACKET*(packetNumber+1); i++) {
                                subsetLobbyIds[n] = lobbyList.get(i).getLobbyId();
                                subsetLobbyNames[n] = lobbyList.get(i).getLobbyName();
                                n++;
                            }

                            outgoing = new ActiveLobbyPacket(OpCode.MORELOBBYS.valueOf(), MAX_LOBBYS_PER_PACKET, subsetLobbyIds, subsetLobbyNames).getPacket();
                            packet = new DatagramPacket(outgoing, outgoing.length, address, port);
                            socket.send(packet);

                            numOfLobbys = numOfLobbys - MAX_LOBBYS_PER_PACKET;
                            packetNumber++;
                        }


                        byte[][] lastSetLobbyIds = new byte[numOfLobbys][2];
                        String[] lastSetLobbyNames = new String[numOfLobbys];
                        int index = MAX_LOBBYS_PER_PACKET*packetNumber;


                        for (int i = 0; i < numOfLobbys; i++) {
                            lastSetLobbyIds[i] = lobbyList.get(index).getLobbyId();
                            lastSetLobbyNames[i] = lobbyList.get(index).getLobbyName();
                            index++;
                        }

                        //Last One
                        outgoing = new ActiveLobbyPacket(OpCode.MORELOBBYS.valueOf(), numOfLobbys, lastSetLobbyIds, lastSetLobbyNames).getPacket();
                        packet = new DatagramPacket(outgoing, outgoing.length, address, port);
                        socket.send(packet);
                    }

                    //END
                } else if (incoming[0] == OpCode.SET_NATION.valueOf()) {
                    /*
                     * Set Nation Packet Received
                     */
                    boolean nationTaken = false;
                    SetNationPacket snp = new SetNationPacket(incoming);
                    outgoing = null;

                    //Find the lobby request was made from
                    for (LobbyInstance l: lobbyList) {
                        lobbyExists = true;
                        if (Utilities.compareLobbyIds(l.getLobbyId(), snp.getLobbyId())) {
                            System.out.println("Found Lobby");
                            //Check if nation already taken.
                            if (snp.getNation() != Nation.RANDOM) {
                                for (User u: l.getLobbyUsers()) {
                                    if (u.getNation() == snp.getNation()) {
                                        //Nation taken, return currently selected nation.
                                        System.out.println("Nation taken");
                                        nationTaken = true;
                                        break;
                                    }
                                }
                            }

                            //find user
                            for (User u: l.getLobbyUsers()) {
                                userExists = true;
                                if (u.getPosition() == snp.getPosition()) {
                                    if (nationTaken) {
                                        outgoing = new SetNationPacket(l.getLobbyId(), u.getPosition(), u.getNation(), u.getLeader()).getPacket();
                                    } else {
                                        u.setNation(snp.getNation());
                                        u.setLeader(snp.getLeaderName());
                                        outgoing = snp.getPacket();
                                        mcAddress = l.getInetAddress();
                                    }
                                }
                            }
                        }
                    }

                    //Send Response
                    if (!lobbyExists) {
                        //Lobby Not found
                        outgoing = new FailurePacket(snp.getLobbyId(), OpCode.LOBBY_EXISTS.valueOf()).getPacket();
                        packet = new DatagramPacket(outgoing, outgoing.length, address, port);
                    } else if (!userExists) {
                        //User Not found
                        outgoing = new FailurePacket(snp.getLobbyId(), OpCode.USER_EXISTS.valueOf()).getPacket();
                        packet = new DatagramPacket(outgoing, outgoing.length, address, port);
                    } else if (nationTaken)
                        //Nation Taken return previously selected nation.
                        packet = new DatagramPacket(outgoing, outgoing.length, address, port);
                    else
                        //Nation available, tell everyone in lobby.
                        packet = new DatagramPacket(outgoing, outgoing.length, mcAddress, Ports.LOBBY_MC_PORT);

                    socket.send(packet);
                    //END
                } else if (incoming[0] == OpCode.NATION_DESC.valueOf()) {
                    /*
                     * Nation Description Packet Received
                     */
                    SendNationDescription snd = new SendNationDescription(incoming);

                    System.out.println("Nation Description received");

                    //Find lobby
                    for (LobbyInstance l: lobbyList) {
                        if (Utilities.compareLobbyIds(l.getLobbyId(), snd.getLobbyId())) {
                            System.out.println("NationDesc: LobbyFound");
                            lobbyExists = true;
                            //Find User
                            for (User u: l.getLobbyUsers()) {
                                if (u.getPosition() == snd.getPosition()) {
                                    System.out.println("NationDesc: User Found");
                                    userExists = true;
                                    //Change leader name
                                    u.setLeader(snd.getLeaderName());
                                    mcAddress = l.getInetAddress();
                                }
                            }
                        }
                    }

                    if (lobbyExists && userExists) {
                        System.out.println("NationDesc: responding");
                        outgoing = snd.getPacket();
                        packet = new DatagramPacket(outgoing, outgoing.length, mcAddress, Ports.LOBBY_MC_PORT);
                    } else {
                        System.out.println("NationDesc: failure " + lobbyExists + " " + userExists);
                        if (!lobbyExists)
                            outgoing = new FailurePacket(snd.getLobbyId(), OpCode.LOBBY_EXISTS.valueOf()).getPacket();
                        else
                            outgoing = new FailurePacket(snd.getLobbyId(), OpCode.USER_EXISTS.valueOf()).getPacket();
                        packet = new DatagramPacket(outgoing, outgoing.length, address, port);
                    }

                    socket.send(packet);
                    //END
                } else if (incoming[0] == OpCode.MAKE_READY.valueOf()) {
                    /*
                     * Make Ready Packet Received
                     */
                    System.out.println("Make Ready Packet Received");

                    MakeReadyPacket mrp = new MakeReadyPacket(incoming);

                    //Find Lobby
                    for (LobbyInstance l: lobbyList) {
                        if (Utilities.compareLobbyIds(l.getLobbyId(), mrp.getLobbyId())) {
                            lobbyExists = true;
                            //Find User
                            for (User u: l.getLobbyUsers()) {
                                if (u.getPosition() == mrp.getPosition()) {
                                    userExists = true;
                                    u.setReady(mrp.isReady());
                                    mcAddress = l.getInetAddress();
                                }
                            }
                        }
                    }

                    if (lobbyExists && userExists) {
                        outgoing = mrp.getPacket();
                        packet = new DatagramPacket(outgoing, outgoing.length, mcAddress, Ports.LOBBY_MC_PORT);
                    } else {
                        if (!lobbyExists)
                            outgoing = new FailurePacket(mrp.getLobbyId(), OpCode.LOBBY_EXISTS.valueOf()).getPacket();
                        else
                            outgoing = new FailurePacket(mrp.getLobbyId(), OpCode.USER_EXISTS.valueOf()).getPacket();
                        packet = new DatagramPacket(outgoing, outgoing.length, address, port);
                    }

                    socket.send(packet);
                    //END
                } else if (incoming[0] == OpCode.CHAT.valueOf()) {
                    /*
                     * Chat Packet Received
                     */
                    ChatPacket cp = new ChatPacket(incoming);
                    mcAddress = Utilities.idAsInetAddress(cp.getLobbyId());
                    outgoing = cp.getPacket();

                    for (LobbyInstance l : lobbyList)
                        if (Utilities.compareLobbyIds(cp.getLobbyId(), l.getLobbyId()))
                            lobbyExists = true;

                    packet = new DatagramPacket(outgoing, outgoing.length, mcAddress, Ports.LOBBY_MC_PORT);
                    if (lobbyExists)
                        socket.send(packet);
                    //END
                } else if (incoming[0] == OpCode.SET_OPTION.valueOf()) {
                    /*
                     * Set Lobby Option Packet Received
                     */
                    System.out.println("LobbyOption: received");
                    SetLobbyOptionPacket slo = new SetLobbyOptionPacket(incoming);

                    for (LobbyInstance l: lobbyList) {
                        if (Utilities.compareLobbyIds(l.getLobbyId(), slo.getLobbyId())) {
                            lobbyExists = true;
                            mcAddress = l.getInetAddress();
                            System.out.println("option: " + slo.getOption() + " isOn: " + slo.isOn());
                            l.setGameOption(slo.getOption(), slo.isOn());
                        }
                    }

                    if (lobbyExists) {
                        System.out.println("LobbyOption: responding");
                        outgoing = slo.getPacket();
                        packet = new DatagramPacket(outgoing, outgoing.length, mcAddress, Ports.LOBBY_MC_PORT);
                    } else {
                        System.out.println("LobbyOption: failure");
                        outgoing = new FailurePacket(slo.getLobbyId(), OpCode.LOBBY_EXISTS.valueOf()).getPacket();
                        packet = new DatagramPacket(outgoing, outgoing.length, address, port);
                    }
                    socket.send(packet);
                    //END
                } else if (incoming[0] == OpCode.COUNTDOWN.valueOf()) {
                    /*
                     * Start Countdown Packet Received
                     */
                    System.out.println("Countdown Packet Received");
                    CountdownPacket cdp = new CountdownPacket(incoming);

                    for (LobbyInstance l: lobbyList) {
                        if (Utilities.compareLobbyIds(l.getLobbyId(), cdp.getLobbyId())) {
                            if (cdp.getSecondsRemaining() == LobbyConstants.COUNTDOWN_SECONDS) {
                                l.checkForRandom(l.getInetAddress(), Ports.LOBBY_MC_PORT);  //TODO make this work for the server instead of client.
                                l.startCountdown();
                            } else if (cdp.getSecondsRemaining() == LobbyConstants.COUNTDOWN_ABORT) {
                                l.stopCountdown();
                                mcAddress = l.getInetAddress();
                                outgoing = new CountdownPacket(cdp.getLobbyId(),-1).getPacket();
                                DatagramPacket abortPacket = new DatagramPacket(outgoing, outgoing.length, mcAddress, Ports.LOBBY_MC_PORT);
                                socket.send(abortPacket);
                            } else if (cdp.getSecondsRemaining() == LobbyConstants.COUNTDOWN_DONE) {
                                //Send Game Options                                                         //Not A Nation (-1)
                                outgoing = new StartGamePacket(l.getLobbyId(), l.getGameOptions(), l.getMSD(), -1).getPacket();
                                InetAddress gameAddress = InetAddress.getByName(Messages.getString("game.server.address"));
                                DatagramPacket gameStartedPacket = new DatagramPacket(outgoing, outgoing.length, gameAddress, Ports.GAME_SERVER_PORT);
                                socket.send(gameStartedPacket);

                                //Send Users
                                if (!l.isLobbyFull()) {
                                    //One Packet
                                    outgoing = new JoinLobbyPacket(l.getLobbyId(), l.getLobbyUsers()).getPacket();
                                    DatagramPacket addUsersPacket = new DatagramPacket(outgoing, outgoing.length, gameAddress, Ports.GAME_SERVER_PORT);
                                    socket.send(addUsersPacket);
                                } else {
                                    //Two Packets
                                    ArrayList<User> firstSixUsers = new ArrayList<User>(l.getLobbyUsers().subList(0,6));
                                    outgoing = new JoinLobbyPacket(l.getLobbyId(), firstSixUsers).getPacket();
                                    DatagramPacket firstSixPacket = new DatagramPacket(outgoing, outgoing.length, gameAddress, Ports.GAME_SERVER_PORT);
                                    socket.send(firstSixPacket);

                                    outgoing = new JoinLobbyPacket(l.getLobbyId(), l.getLobbyUsers().get(6)).getPacket();
                                    DatagramPacket lastUserPacket = new DatagramPacket(outgoing, outgoing.length, gameAddress, Ports.GAME_SERVER_PORT);
                                    socket.send(lastUserPacket);
                                }

                                //Remove lobby from list
                                lobbyList.remove(l);
                            }
                            break;
                        } else {
                            System.err.println("Countdown Packet: Unknown lobbyId");
                        }
                    }
                } else {
                    //Unknown Packet
                    System.err.println("Unknown lobby.packet received");
                }

            } catch (IOException e) {
                System.err.println("Server Thread error.");
                e.printStackTrace();
                ServerLogger.log("NapoleonServerThread - " + e.getMessage());
                break;
            }
        }
        socket.close();
    }

    /*
       Helper Methods
     */

    /**
     * This method will generate a unique lobby id that will also
     * double as the ip address that will be used to send information
     * to users in the different lobbys.
     *
     * Non-Reserved Multicast Address (224.0.1.0 - 238.255.255.255)
     *
     * Ip Address : 224.[1].1.[0]
     *
     * @return - valid lobby id. (0-255)(0-255)
     *
     */
    private byte[] generateLobbyId() {
        byte[] lobbyId = new byte[2];

        do {
            lobbyId[0] = (byte)( (lobbyList.size() * 31 + DateTime.now().getMillisOfDay() % 100) % 256 );
            lobbyId[1] = (byte)( (lobbyList.size() * 41 + DateTime.now().getMillisOfDay() / 100) % 256 );
        } while (isLobbyIdDuplicate(lobbyId));

        return lobbyId;
    }

    /**
     *
     * @param id - identifier for the lobby.
     * @return - true iff there is no other lobby with this id.
     */
    private boolean isLobbyIdDuplicate(byte[] id) {
        for (LobbyInstance l: lobbyList) {
            if (Utilities.compareLobbyIds(l.getLobbyId(), id)) {
                return true;
            }
        }
        return false;
    }
}
