package game.client;

import game.packet.GameAddUnitsPacket;
import game.packet.GameOps;
import game.util.GameLogger;
import lobby.packet.OpCode;
import util.Ports;
import util.ServerResponseThread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * PlaceUnitsResponseThread.java  Date Created: Aug 27, 2013
 *
 * Purpose: To send all addUnitPackets (PUP) to the server and wait for responses.
 *
 * Description: This thread will gather all PUPs and send them individually to the server.
 * Upon receiving confirmation the server was able to process each AUP the next AUP will be sent.
 *
 * @author Chrisb
 */
public class AddUnitsResponseThread extends ServerResponseThread {
    public AddUnitsResponseThread(byte[] gameId) {
        super(gameId);
        this.gameId = gameId;
    }

    public void addNewUnits(ArrayList<Integer> purchases, int nationNumber) {
        addUnitPacket = new GameAddUnitsPacket(gameId, nationNumber, purchases);
//        threadOpCode = addUnitPacket.getOpCode();
        start();
    }

    public void run() {
        Thread thisThread = Thread.currentThread();
//        failPacket = false;
        while (running == thisThread) {
            try {
                outgoing = addUnitPacket.getPacket();
                outgoingPacket = new DatagramPacket(outgoing, outgoing.length, address, Ports.GAME_SERVER_PORT);
                socket.send(outgoingPacket);

                incoming = new byte[256];
                incomingPacket = new DatagramPacket(incoming, incoming.length);
                socket.receive(incomingPacket);

                if (incoming[0] == GameOps.ADD_UNITS.valueOf()) {
                    System.out.println("AddUnitsRT addAllUnits " + failPacket);
                    if (!Arrays.equals(outgoing, incoming)) {
                        //Fail different packet received.  For now ignore todo
                        System.err.println("AddUnitsRT: Failed to match incoming with outgoing package");
                    } else
                        System.out.println("AddUnitsRT: AUP added successfully " + failPacket);
                } else if (incoming[0] == OpCode.FAILURE.valueOf()) {
                    //Packet returned fail packet.  Add original packet to temp array to resend.
                    failPacket = true;
                    System.err.println("AddUnitsRT: Fail addUnit Packet Received: " + incoming[0]);
                } else {
                    //Unknown Packet Received
                    failPacket = true;
                    System.err.println("AddUnitsRT: Non-addUnit related Packet Received: " + incoming[0]);
                }
            } catch (SocketTimeoutException e) {
                onTimeout();
            } catch (IOException e) {             // would like to wait to hear from server that first packet was successful.
                e.printStackTrace();
                GameLogger.log("AddUnitsResponseThread:sendPackets - Failed to send packet - " + e.getMessage());
            }
            System.out.println("AddUnitsRT: End " + failPacket);
            if (!failPacket)
                stopThread();
        }
    }

    private GameAddUnitsPacket addUnitPacket;
//    private boolean failPacket;
    private byte[] gameId;
}