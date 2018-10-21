package lobby.client;

import lobby.packet.*;

import java.net.MulticastSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.io.IOException;

import lobby.controller.NapoleonController;
import util.Ports;

/**
 * NapoleonMCClientThread.java  Date Created: Apr 10, 2012
 *
 * Purpose: Receive mass responses from the lobby.server.
 *
 * Description: When a user joins a lobby a copy of this thread
 * is created to wait for responses sent to everyone in the lobby.
 *
 * @author Chrisb
 */

public class NapoleonMCClientThread extends Thread {
    private MulticastSocket socket;
    private volatile Thread running;

    public NapoleonMCClientThread(InetAddress address) {
        try {
            socket = new MulticastSocket(Ports.LOBBY_MC_PORT);
            socket.joinGroup(address);
            running = this;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Thread thisThread = Thread.currentThread();
        while (running == thisThread) {
            try {
                byte[] incoming = new byte[256];
                DatagramPacket packet = new DatagramPacket(incoming, incoming.length);
                socket.receive(packet);
                System.out.println("MC lobby.packet received");

                if (incoming[0] == OpCode.JOIN.valueOf()) {
                    JoinLobbyPacket addUser = new JoinLobbyPacket(incoming);
                    NapoleonController.addUserToLobby(addUser.getLobbyId(), addUser.getUsers().get(0));
                } else if (incoming[0] == OpCode.LEAVE_LOBBY.valueOf()) {
                    LeaveLobbyPacket removeUser = new LeaveLobbyPacket(incoming);
                    NapoleonController.removeUserFromLobby(removeUser.getLobbyId(), removeUser.getUserName());
                } else if (incoming[0] == OpCode.SET_NATION.valueOf()) {
                    SetNationPacket snp = new SetNationPacket(incoming);
                    NapoleonController.doSetNation(snp.getLobbyId(), snp.getPosition(), snp.getNation());
                } else if (incoming[0] == OpCode.NATION_DESC.valueOf()) {
                    SendNationDescription snd = new SendNationDescription(incoming);
                    NapoleonController.receiveNationDescription(snd.getLobbyId(), snd.getPosition(), snd.getLeaderName());
                } else if (incoming[0] == OpCode.MAKE_READY.valueOf()) {
                    MakeReadyPacket mrp = new MakeReadyPacket(incoming);
                    NapoleonController.doMakeReady(mrp.getLobbyId(), mrp.getPosition(), mrp.isReady());
                } else if (incoming[0] == OpCode.CHAT.valueOf()) {
                    ChatPacket cp = new ChatPacket(incoming);
                    NapoleonController.receiveChat(cp.getLobbyId(), cp.getUserName(), cp.getMessage());
                } else if (incoming[0] == OpCode.SET_OPTION.valueOf()) {
                    SetLobbyOptionPacket sgo = new SetLobbyOptionPacket(incoming);
                    NapoleonController.changeGameOption(sgo.getLobbyId(), sgo.getOption(), sgo.isOn());
                } else if (incoming[0] == OpCode.COUNTDOWN.valueOf()) {
                    CountdownPacket cdp = new CountdownPacket(incoming);
                    NapoleonController.doCountdown(cdp.getLobbyId(), cdp.getSecondsRemaining());
                } else {
                    //Unknown Packet Received
                    System.err.println("Unknown Packet Received");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }

    public void stopThread() {
        running = null;
    }

}
