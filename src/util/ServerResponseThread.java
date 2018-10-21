package util;

import game.packet.GameOps;
import game.packet.GamePacket;
import game.util.GameLogger;

import java.io.IOException;
import java.net.*;

/**
 * ServerResponseThread.java  Date Created: Aug 26, 2013
 *
 * Purpose: To time out after a set number of seconds if client has not heard back from the server.
 *
 * Description: Depending on the 'type' of ServerResponseThread (SRT) different actions will be taken
 * when the server does not respond within the set time frame.
 *
 * @author Chrisb
 */

public abstract class ServerResponseThread extends Thread {
    private static final int THREAD_TIMEOUT = 5000;

    public ServerResponseThread(byte[] gameId) {
        try {
            this.address = InetAddress.getByName(Messages.getString("game.server.address"));
            socket = new MulticastSocket(Ports.GAME_CLIENT_PORT);
            socket.setSoTimeout(THREAD_TIMEOUT);
            this.setDaemon(true);
            ((MulticastSocket)socket).joinGroup(Utilities.idAsInetAddress(gameId));

            running = this;
            failPacket = false;
        } catch (IOException e) {
            e.printStackTrace();
            GameLogger.log("ServerResponseThread:constructor - " + e.getMessage());
        }
    }

    /*
     * This is the default run menthod.
     * Nothing much happens here but print statements based on reply from server.
     */
    public void run() {
        System.out.println("ServerResponseThread started threadOpCode:" + threadOpCode);
        Thread thisThread = Thread.currentThread();
        while (running == thisThread) {
            try {
                outgoingPacket = new DatagramPacket(threadPacket.getPacket(), threadPacket.getPacket().length, address, Ports.GAME_SERVER_PORT);
                socket.send(outgoingPacket);

                incoming = new byte[256];
                incomingPacket = new DatagramPacket(incoming, incoming.length);
                socket.receive(incomingPacket);

                if (incoming[0] == threadOpCode) {
                    System.out.println("ServerResponse Thread: packet received threadOpCode:" + threadOpCode);
                } else if (incoming[0] == GameOps.FAILURE.valueOf()) {
                    System.err.println("ServerResponse Thread: failPacket threadOpCode:" + threadOpCode);
                    failPacket = true;
                } else {
                    //Unknown Packet Received
                    System.err.println("ServerResponse Thread: unknownPacket threadOpCode:" + threadOpCode);
                    failPacket = true;
                }

                if (!failPacket)
                    stopThread();  //todo resending is not bad?
            } catch (SocketTimeoutException e) {
                onTimeout();
            } catch (IOException e) {
                e.printStackTrace();
                GameLogger.log("ServerResponseThread:sendPacket - Failed to send - " + e.getMessage() + "\nthreadOpCode:" + threadOpCode);
            }
        }
    }
    
    public void stopThread() {
        System.out.println("ServerResponseThread:stopThread threadOpCode:" + threadOpCode);
        running = null;
        try {
            join(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onTimeout() {
        //todo
        System.err.println("ServerResponseThread onTimeout");
        GameLogger.log("ServerResponseThread onTimeout");
        //If no response received from server:
        // 1. Server Crashed - No response will be received.
        // 2. Bad Ping - Will come in after timeout.
        // 3. Loss of internet - server may or may not have received the AUP.
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