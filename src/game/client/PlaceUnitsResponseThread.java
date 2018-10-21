package game.client;

import game.controller.Unit.MilitaryUnit;
import game.packet.AllUnitsPlacedPacket;
import game.packet.GameOps;
import game.packet.GamePlaceUnitsPacket;
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
 * Purpose: To send all placeUnitPackets (PUP) to the server and wait for responses.
 *
 * Description: This thread will gather all PUPs and send them individually to the server.
 * Upon receiving confirmation the server was able to process each AUP the next AUP will be sent.
 *
 * @author Chrisb
 */
public class PlaceUnitsResponseThread extends ServerResponseThread {
    public PlaceUnitsResponseThread(byte[] gameId, int nation) {
        super(gameId);
        outgoingPUPs = new ArrayList<byte[]>();
        tempPackets = new ArrayList<byte[]>();
        this.gameId = gameId;
        this.nation = nation;
    }

    public void addPacket(byte[] gameId, int regionIndex, int regionType, ArrayList<MilitaryUnit> units) {
        outgoingPUPs.ensureCapacity(outgoingPUPs.size() + 1);
        outgoingPUPs.add(new GamePlaceUnitsPacket(gameId, regionIndex, regionType, units).getPacket());
    }

    public void sendPackets() { start(); }

    public void run() {
        Thread thisThread = Thread.currentThread();
        while (running == thisThread) {
            if (failPacket) {
                outgoingPUPs = tempPackets;
                tempPackets.clear();
                failPacket = false;
            }
            for (byte[] pup : outgoingPUPs) {
                outgoing = pup;
                outgoingPacket = new DatagramPacket(outgoing, outgoing.length, address, Ports.GAME_SERVER_PORT);
                try {
                    socket.send(outgoingPacket);

                    incoming = new byte[256];
                    incomingPacket = new DatagramPacket(incoming, incoming.length);
                    socket.receive(incomingPacket);

                    if (incoming[0] == GameOps.PLACE_UNITS.valueOf()) {
                        System.out.println("PlaceUnitsRT addAllUnits");
                        if (!Arrays.equals(outgoing, incoming)) {
                            //Fail different packet received.  For now ignore todo
                            System.err.println("PlaceUnitsRT: Failed to match incoming with outgoing package");
                        } else
                            System.out.println("PlaceUnitsRT: PUP added successfully");
                    } else if (incoming[0] == OpCode.FAILURE.valueOf()) {
                        //Packet returned fail packet.  Place original packet to temp array to resend.
                        failPacket = true;
                        tempPackets.add(pup);
                        System.err.println("PlaceUnitsRT: Fail placeUnit Packet Received: " + incoming[0]);
                    } else {
                        //Unknown Packet Received
                        failPacket = true;
                        tempPackets.add(pup);
                        System.err.println("PlaceUnitsRT: Non-placeUnit related Packet Received: " + incoming[0]);
                    }
                } catch (SocketTimeoutException e) {
                    onTimeout();
                } catch (IOException e) {             // would like to wait to hear from server that first packet was successful.
                    e.printStackTrace();
                    GameLogger.log("PlaceUnitsResponseThread:sendPackets - Failed to send packet - " + e.getMessage());
                }
            }
            System.out.println("PlaceUnitsRT: End of for-loop");
            if (!failPacket)
                sendUnitsPlacedPacket();
        }
    }

    private void sendUnitsPlacedPacket() {
        try {
            outgoing = new AllUnitsPlacedPacket(gameId, nation).getPacket();
            outgoingPacket = new DatagramPacket(outgoing, outgoing.length, address, Ports.GAME_SERVER_PORT);
            socket.send(outgoingPacket);

            incoming = new byte[256];
            incomingPacket = new DatagramPacket(incoming, incoming.length);
            socket.receive(incomingPacket);

            if (incoming[0] == GameOps.UNITS_PLACED.valueOf())
                System.out.println("PlaceUnitsRT.UnitsPlaced - received");
//                controller.allUnitsPlaced(new AllUnitsPlacedPacket(incoming).getNation());
        } catch (SocketTimeoutException e) {
            //Units Placeed timeout
            GameLogger.log("PlaceUnitsRT.UnitsPlaced - timeout");
            sendUnitsPlacedPacket();
        } catch (IOException e) {
            e.printStackTrace();
            GameLogger.log("PlaceUnitsRT.UnitsPlacedPacket - " + e.getMessage());
        }
        System.out.println("PlaceUnitsRT - stopping thread");
        stopThread();
    }

    private ArrayList<byte[]> outgoingPUPs;
    private ArrayList<byte[]> tempPackets;
    private byte[] gameId;
    private int nation;
}