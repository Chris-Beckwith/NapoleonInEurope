package game.packet.political;

import game.packet.GamePacket;
import game.packet.GameOps;

import java.util.ArrayList;

/**
 * MoveNPNUnitsPacket.java  Date Created: Jan 21, 2014
 *
 * Purpose: To send/receive information about how to move NPN units after a peace congress.
 *
 * Description:
 *
 * @author Chrisb
 */
public class MoveNPNUnitsPacket extends GamePacket {

    public MoveNPNUnitsPacket(byte[] packet) {
        this.packet = packet;
    }

    public MoveNPNUnitsPacket(byte[] gameId, int sueNation, ArrayList<Integer> regionsToMoveUnits,
                              ArrayList<Integer> portsToMoveInTo, ArrayList<Integer> portsToMoveOutOf) {
        packet = new byte[256];
        packet[0] = GameOps.CONGRESS_MOVENPN.valueOf();
        setGameId(gameId);
        packet[SUE_NATION] = (byte)sueNation;
        packet[NUM_OF_REGIONS] = (byte)regionsToMoveUnits.size();
        packet[NUM_OF_IN_PORTS] = (byte)portsToMoveInTo.size();
        packet[NUM_OF_OUT_PORTS] = (byte)portsToMoveOutOf.size();

        int index = INDEX_START;
        for (int i : regionsToMoveUnits)
            packet[index++] = (byte)i;
        for (int i : portsToMoveInTo)
            packet[index++] = (byte)i;
        for (int i : portsToMoveOutOf)
            packet[index++] = (byte)i;
    }

    public int getSueingNation() { return packet[SUE_NATION] & 0xff; }

    public int getRegionsSize() { return packet[NUM_OF_REGIONS] & 0xff; }
    public int getPortsInSize() { return packet[NUM_OF_IN_PORTS] & 0xff; }
    public int getPortsOutSize() { return packet[NUM_OF_OUT_PORTS] & 0xff; }

    public ArrayList<Integer> getRegionsToMove() {
        ArrayList<Integer> regions = new ArrayList<Integer>();
        for (int i = INDEX_START; i < getRegionsSize() + INDEX_START; i++)
            regions.add(packet[i] & 0xff);

        return regions;
    }

    public ArrayList<Integer> getPortsIn() {
        ArrayList<Integer> ports = new ArrayList<Integer>();
        for (int i = INDEX_START + getRegionsSize(); i < getPortsInSize() + INDEX_START + getRegionsSize(); i++)
            ports.add(packet[i] & 0xff);

        return ports;
    }

    public ArrayList<Integer> getPortsOut() {
        ArrayList<Integer> ports = new ArrayList<Integer>();
        for (int i = INDEX_START + getRegionsSize() + getPortsInSize(); i < getPortsOutSize() + INDEX_START + getRegionsSize() + getPortsInSize(); i++)
            ports.add(packet[i] & 0xff);

        return ports;
    }

    private final static int SUE_NATION = 3;
    private final static int NUM_OF_REGIONS = 4;
    private final static int NUM_OF_IN_PORTS = 5;
    private final static int NUM_OF_OUT_PORTS = 6;
    private final static int INDEX_START = 7;
}