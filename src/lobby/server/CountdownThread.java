package lobby.server;

import lobby.packet.CountdownPacket;
import shared.controller.LobbyConstants;

import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;

import util.ServerLogger;
import util.Ports;

/**
 * CountdownThread.java  Date Created: Sep 19, 2012
 *
 * Purpose: To start a countdown when a game is about to start.
 *
 * Description:
 *
 * @author Chrisb
 */
public class CountdownThread extends Thread {
    protected DatagramSocket socket;
    protected InetAddress mcAddress;
    protected int mcPort;
    protected byte[] outgoing;
    protected byte[] lobbyId;

    private volatile Thread abort;
    private int secondsRemaining;
    private CountdownPacket cdp;

    public CountdownThread(byte[] lobbyId, InetAddress mcAddress, int mcPort) throws IOException {
        socket = new DatagramSocket(Ports.COUNTDOWN_PORT);
        this.mcAddress = mcAddress;
        this.mcPort = mcPort;
        this.lobbyId = lobbyId;
        secondsRemaining = LobbyConstants.COUNTDOWN_SECONDS;
        cdp = new CountdownPacket(lobbyId, secondsRemaining);
        abort = this;
    }

    public void run() {
        Thread thisThread = Thread.currentThread();
        while ( (secondsRemaining > LobbyConstants.COUNTDOWN_DONE) && (abort == thisThread) ) {
            try {
                System.out.println("Countdown Running..");
                outgoing = new byte[256];
                outgoing = cdp.getPacket();

                DatagramPacket packet = new DatagramPacket(outgoing, outgoing.length, mcAddress, mcPort);
                socket.send(packet);

                sleep(1000);
                secondsRemaining--;
                cdp.setSecondsRemaining(secondsRemaining);
                if (secondsRemaining == LobbyConstants.COUNTDOWN_DONE) {
                    outgoing = cdp.getPacket();
                    DatagramPacket lastPacket = new DatagramPacket(outgoing, outgoing.length, mcAddress, mcPort);
                    socket.send(lastPacket);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Countdown Thread error.");
                ServerLogger.log("CountdownThread - " + e.getMessage());
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("Countdown Thread InterruptedException");
                ServerLogger.log("CountdownThread - " + e.getMessage());
                break;
            }
        }
        socket.close();
        System.out.println("Countdown done..");
    }

    public void abort() {
        abort = null;
    }
}