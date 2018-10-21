package lobby.client;

import lobby.controller.NapoleonController;
import lobby.packet.*;
import util.ServerResponseThread;
import util.Messages;
import util.Logger;
import util.Ports;

import java.net.*;
import java.io.IOException;

import game.packet.GamePacket;

/**
 * LoginResponseThread.java  Date Created: Aug 26, 2013
 *
 * Purpose: To run until timeout is reached or response received from server.
 *
 * Description: This thread shall run until it receives word that the server has responded
 * with valid information.  It the timeout is reached before server responds it shall act accordingly.
 * LRT:  Tell the user no response was received, via an error msg on the login menu.
 *
 * @author Chrisb
 */
public class LoginResponseThread extends Thread {
    private static final int LOGIN_TIMEOUT = 10000;

    public LoginResponseThread(String userName) {
        try {
            running = this;
            incoming = new byte[256];
            outgoing = new LoginPacket(userName,"default").getPacket();
            socket = new DatagramSocket();
            socket.setSoTimeout(LOGIN_TIMEOUT);
            address = InetAddress.getByName(Messages.getString("server.address"));
            this.setDaemon(true);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("NapoleonClientThread:Constructor - " + e.getMessage());
        }
    }

    public void attemptLogin() {
        try {
            System.out.println("Attempting login");
            outgoingPacket = new DatagramPacket(outgoing, outgoing.length, address, Ports.LOBBY_SERVER_PORT);
            socket.send(outgoingPacket);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("LoginResponseThread:login - " + e.getMessage());
        }
    }

    public void run() {
        Thread thisThread = Thread.currentThread();
        while (running == thisThread) {
            try {
                System.out.println("LRT running..");
                incoming = new byte[256];
                incomingPacket = new DatagramPacket(incoming, incoming.length);
                socket.receive(incomingPacket);

                if (incoming[0] == OpCode.LOGIN.valueOf()) {
                    //Login Successful
                    NapoleonController.doLogin(new LoginPacket(incoming).getUserName());
                    stopThread();
                    join(10);
                } else if (incoming[0] == OpCode.FAILURE.valueOf())
                    //todo receive fail login packets
                    System.err.println("Fail Login Packet Received: " + incoming[0]);
                else
                    //Unknown Packet Received
                    System.err.println("Non-Login related Packet Received: " + incoming[0]);
            } catch (SocketTimeoutException e) {
                onTimeout();
            } catch (IOException e) {
                e.printStackTrace();
                Logger.log("LoginResponseThread:IOExectpion - " + e.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
                Logger.log("LoginResponseThread:InterruptedException - " + e.getMessage());
            }
        }
        socket.close();
        System.out.println("LRT Closed");
    }

    public void stopThread() {
        running = null;
        try {
            join(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void onTimeout() {
        stopThread();
        NapoleonController.doNotLogin();
        System.out.println("LRT Timing Out..");
    }

    protected boolean failPacket;
    protected GamePacket threadPacket;
    protected byte threadOpCode;
    protected volatile Thread running;
    protected DatagramPacket outgoingPacket;
    protected DatagramPacket incomingPacket;

    protected byte[] incoming;
    protected byte[] outgoing;
    protected DatagramSocket socket;
    protected InetAddress address;
}