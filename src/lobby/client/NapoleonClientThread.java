package lobby.client;

import lobby.packet.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import lobby.controller.User;
import lobby.controller.NapoleonController;
import shared.controller.LobbyConstants;
import util.Logger;
import util.Messages;
import util.Ports;

/**
 * NapoleonClientThread.java  Date Created: Mar 30, 2012
 *
 * Purpose: This is the client thread that will listen for client responses.
 *
 * Description:  This is where all the magic happens.  When the lobby.server sends
 * out a response to this specific user it goes here to get handled.  This is
 * also where request to the lobby.server are made.
 *
 * @author Chrisb
 */
public class NapoleonClientThread extends Thread {
    private byte[] incoming;
    private byte[] outgoing;
    private DatagramSocket socket;
    private DatagramPacket packet;
    private InetAddress address;
    private volatile Thread running;

    public NapoleonClientThread() {
        try {
            incoming = new byte[256];
            outgoing = new byte[256];
            socket = new DatagramSocket();
            running = this;

            address = InetAddress.getByName(Messages.getString("server.address"));
            //Response Thread Team
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("NapoleonClientThread:Constructor - " + e.getMessage());
        }
    }

//    public void login(String userName) {
//        try {
//            System.out.println("login: " + userName);
//
//            outgoing = new LoginPacket(userName,"default").getPacket();
//            packet = new DatagramPacket(outgoing, outgoing.length, address, PORT);
//            socket.send(packet);
//        } catch (IOException e) {
//            e.printStackTrace();
//            Logger.log("NapoleonClientThread:login - " + e.getMessage());
//        }
//    }

    public void logout(String userName) {
        try {
            System.out.println("logout: " + userName);

            outgoing = new LogoutPacket(userName).getPacket();
            packet = new DatagramPacket(outgoing, outgoing.length, address, Ports.LOBBY_SERVER_PORT);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("NapoleonClientThread:logout - " + e.getMessage());
        }
    }

    public void joinLobby(byte[] lobbyId, User user) {
        try {
            System.out.println("joinLobby Before " + user.getUserName());

            outgoing = new JoinLobbyPacket(lobbyId, user).getPacket();
            packet = new DatagramPacket(outgoing, outgoing.length, address, Ports.LOBBY_SERVER_PORT);
            socket.send(packet);

            System.out.println("joinLobby Sent");
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("NapoleonClientThread:joinLobby - " + e.getMessage());
        }
    }

    public void createLobby(String lobbyName, User user) {
        try {
            System.out.println("createLobbyBefore");

            outgoing = new CreateLobbyPacket(lobbyName, user).getPacket();
            packet = new DatagramPacket(outgoing, outgoing.length, address, Ports.LOBBY_SERVER_PORT);
            socket.send(packet);

            System.out.println("createLobbySent");
        } catch (IOException e ) {
            e.printStackTrace();
            Logger.log("NapoleonClientThread:createLobby - " + e.getMessage());
        }
    }

    public void leaveLobby(byte[] lobbyId, String userName) {
        try {
            System.out.println("leaveLobby Before");

            outgoing = new LeaveLobbyPacket(lobbyId, userName).getPacket();
            packet = new DatagramPacket(outgoing, outgoing.length, address, Ports.LOBBY_SERVER_PORT);
            socket.send(packet);

            System.out.println("leaveLobby sent");
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("NapoleonClientThread:leaveLobby - " + e.getMessage());
        }
    }

    public void refreshLobbyList() {
        try {
            System.out.println("refreshLobbyList Before");

            outgoing = new ActiveLobbyPacket().getPacket();
            packet = new DatagramPacket(outgoing, outgoing.length, address, Ports.LOBBY_SERVER_PORT);
            socket.send(packet);

            System.out.println("refreshLobbyList After");
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("NapoleonClientThread:refreshLobby - " + e.getMessage());
        }
    }

    public void setNation(byte[] lobbyId, int userPosition, int nation, String leaderName) {
        try {
            System.out.println("setNation before");

            outgoing = new SetNationPacket(lobbyId, userPosition, nation, leaderName).getPacket();
            packet = new DatagramPacket(outgoing, outgoing.length, address, Ports.LOBBY_SERVER_PORT);
            socket.send(packet);

            System.out.println("setNation sent");
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("NapoleonClientThread:setNation - " + e.getMessage());
        }
    }

    public void sendNationDescription(byte[] lobbyId, int userPosition, String leaderName) {
        try {
            System.out.println("sendNationDescription before");

            outgoing = new SendNationDescription(lobbyId, userPosition, leaderName).getPacket();
            packet = new DatagramPacket(outgoing, outgoing.length, address, Ports.LOBBY_SERVER_PORT);
            socket.send(packet);

            System.out.println("sendNationDescription after");
        } catch(IOException e) {
            e.printStackTrace();
            Logger.log("NapoleonClientThread:sendNationDescription - " + e.getMessage());
        }
    }

    public void sendMakeReady(byte[] lobbyId, int userPostion, boolean isReady) {
        try {
            System.out.println("sendMakeReady before");

            outgoing = new MakeReadyPacket(lobbyId, userPostion, isReady).getPacket();
            packet = new DatagramPacket(outgoing, outgoing.length, address, Ports.LOBBY_SERVER_PORT);
            socket.send(packet);

            System.out.println("sendMakeReady after");
        } catch(IOException e) {
            e.printStackTrace();
            Logger.log("NapoleonClientThread:sendMakeReady - " + e.getMessage());
        }
    }

    public void sendChat(byte[] lobbyId, String userName, String message) {
        try {
            System.out.println("sendChat Before");

            outgoing = new ChatPacket(lobbyId, userName, message).getPacket();
            packet = new DatagramPacket(outgoing, outgoing.length, address, Ports.LOBBY_SERVER_PORT);
            socket.send(packet);

            System.out.println("sendChat Sent");
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("NapoleonClientThread:sendChat - " + e.getMessage());
        }
    }

    public void setLobbyOption(byte[] lobbyId, int option, boolean isOn) {
        try {
            System.out.println("setLobbyOption Before " + option + " " + isOn);

            outgoing = new SetLobbyOptionPacket(lobbyId, option, isOn).getPacket();
            packet = new DatagramPacket(outgoing, outgoing.length, address, Ports.LOBBY_SERVER_PORT);
            socket.send(packet);

            System.out.println("setLobbyOption Sent");
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("NapoleonClientThread:setLobbyOption - " + e.getMessage());
        }
    }

    public void startCountdown(byte[] lobbyId) {
        try {
            System.out.println("startCountdown Before");
            outgoing = new CountdownPacket(lobbyId, LobbyConstants.COUNTDOWN_SECONDS).getPacket();
            packet = new DatagramPacket(outgoing, outgoing.length, address, Ports.LOBBY_SERVER_PORT);
            socket.send(packet);

            System.out.println("startCountdown Sent");
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("NapoleonClientThread:startCountdown - " + e.getMessage());
        }
    }

    public void stopCountdown(byte[] lobbyId) {
        try {
            System.out.println("stopCountdown Before");
            outgoing = new CountdownPacket(lobbyId, LobbyConstants.COUNTDOWN_ABORT).getPacket();
            packet = new DatagramPacket(outgoing, outgoing.length, address, Ports.LOBBY_SERVER_PORT);
            socket.send(packet);

            System.out.println("stopCountdown Sent");
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("NapoleonClientThread:stopCountdown - " + e.getMessage());
        }
    }

    public void doneCountdown(byte[] lobbyId) {
        try {
            System.out.println("doneCountdown Before");
                                                 //todo HARD CODED
            outgoing = new CountdownPacket(lobbyId, 0).getPacket();
            packet = new DatagramPacket(outgoing, outgoing.length, address, Ports.LOBBY_SERVER_PORT);
            socket.send(packet);

            System.out.println("doneCountdown Sent");
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("NapoleonClientThread:doneCountdown - " + e.getMessage());
        }
    }

    @Override
    public void run() {
        Thread thisThread = Thread.currentThread();
        while (running == thisThread) {
            try {
                System.out.println("running..");
                incoming = new byte[256];

                packet = new DatagramPacket(incoming, incoming.length);
                socket.receive(packet);


                System.out.println("packet Received: " + incoming[0]);
                //Check the first byte of the lobby.packet, this is tells the client what to do.
                /*if (incoming[0] == OpCode.LOGIN.valueOf()) {
                    //Login Successful
                    if (lrt.isAlive()) {
                        NapoleonController.doLogin(new LoginPacket(incoming).getUserName());
                        lrt.stopThread();
                        lrt.join(10);
                    }
                } else*/ if (incoming[0] == OpCode.CREATE.valueOf()) {
                    //Create lobby successful, Join group to start receiving broadcast packets.
                    CreateLobbyPacket clp = new CreateLobbyPacket(incoming);
                    NapoleonController.doCreate(clp.getLobbyId(), clp.getLobbyName());
                } else if (incoming[0] == OpCode.JOIN.valueOf()) {
                    //Join was successful
                    JoinLobbyPacket jlp = new JoinLobbyPacket(incoming);
                    NapoleonController.doJoin(jlp.getLobbyId(), jlp.getUsers());
                } else if (incoming[0] == OpCode.JOIN_SETTINGS.valueOf()) {
                    //Initial Lobby Settigns
                    JoinSettingsPacket jsp = new JoinSettingsPacket(incoming);
                    NapoleonController.doLobbySettings(jsp.getLobbyId(), jsp.getReadyStates(), jsp.getOptions(), jsp.getMSD());
                } else if (incoming[0] == OpCode.LOBBY_FULL.valueOf()) {
                    //Lobby is full
                    NapoleonController.doLobbyFull();
                } else if (incoming[0] == OpCode.STARTING.valueOf()) {
                    //Lobby has started
                    NapoleonController.doLobbyStarted();
                } else if (incoming[0] == OpCode.LOBBY_EXISTS.valueOf()) {
                    //Lobby Name exists
                    NapoleonController.doLobbyExists();
                } else if (incoming[0] == OpCode.GETLOBBYS.valueOf()) {
                    //Initial Lobbylist
                    ActiveLobbyPacket alp = new ActiveLobbyPacket(incoming);
                    NapoleonController.doRefreshLobbyList(alp.getLobbyIds(), alp.getLobbyNames());
                } else if (incoming[0] == OpCode.MORELOBBYS.valueOf()) {
                    //Additional Lobbys
                    ActiveLobbyPacket alp = new ActiveLobbyPacket(incoming);
                    NapoleonController.addToLobbyList(alp.getLobbyIds(), alp.getLobbyNames());
                } else if (incoming[0] == OpCode.SET_NATION.valueOf()) {
                    //Set Nation
                    SetNationPacket snp = new SetNationPacket(incoming);
                    NapoleonController.doSetNation(snp.getLobbyId(), snp.getPosition(), snp.getNation());
                } else if (incoming[0] == OpCode.LOBBY_NOT_READY.valueOf()) {
                    //Lobby Not Ready
                    StartGamePacket sgp = new StartGamePacket(incoming);
                    NapoleonController.lobbyNotReady(sgp.getLobbyId());
                } else if (incoming[0] == OpCode.LOGOUT.valueOf()) {
                    //Logout
                    LogoutPacket lp = new LogoutPacket(incoming);
                    if (NapoleonController.getUserName().compareTo(lp.getUserName()) == 0)
                        stopThread();
                } else if (incoming[0] == OpCode.FAILURE.valueOf())
                    //todo receive fail packets
                    System.err.println("Failure Packet Received: " + incoming[0]);
                else
                    //Unknown Packet Received
                    System.err.println("Unknown Packet Received: " + incoming[0]);
            } catch (IOException e) {
                e.printStackTrace();
                Logger.log("NapoleonClientThread:IOException - " + e.getMessage());
            }
        }
        socket.close();
    }

    public void stopThread() {
        running = null;
    }
}
