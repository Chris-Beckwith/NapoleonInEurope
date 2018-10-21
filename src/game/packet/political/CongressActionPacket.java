package game.packet.political;

import game.packet.GamePacket;
import game.packet.GameOps;

/**
 * CongressActionPacket.java  Date Created: Dec 27, 2013
 *
 * Purpose: To send information to/from server about the next congress nation.
 *
 * Description:
 *
 * @author Chrisb
 */
public class CongressActionPacket extends GamePacket {
    public CongressActionPacket(byte[] packet) {
        this.packet = packet;
    }

    public CongressActionPacket(byte[] gameId, int sueingNation, int turnIndex, int actionNation) {
        packet = new byte[256];
        packet[0] = GameOps.CONGRESS_PASS.valueOf();
        setGameId(gameId);
        packet[TURN_INDEX] = (byte) turnIndex;
        packet[SUEING_NATION] = (byte) sueingNation;
        packet[ACTION_NATION] = (byte) actionNation;
    }

    //Annex Action Constructor
    public CongressActionPacket(byte[] gameId, int sueingNation, int turnIndex, int actionNation, int regionOneIndex) {
        this(gameId, sueingNation, turnIndex, actionNation);
        //Change OpCode
        packet[0] = GameOps.CONGRESS_ANNEX.valueOf();
        //Add Land Regions
        packet[REGION_ONE] = (byte)regionOneIndex;
        packet[REGION_TWO] = -1;
        packet[REGION_THREE] = -1;
    }

    //Restore Action Constructor
    public CongressActionPacket(byte[] gameId, int sueingNation, int turnIndex, int actionNation, int regionOneIndex, int regionTwoIndex) {
        this(gameId, sueingNation, turnIndex, actionNation);
        //Change OpCode
        packet[0] = GameOps.CONGRESS_RESTORE.valueOf();
        //Add Land Regions
        packet[REGION_ONE] = (byte)regionOneIndex;
        packet[REGION_TWO] = (byte)regionTwoIndex;
        packet[REGION_THREE] = -1;
    }

    //Free Serfs Action Constructor
    public CongressActionPacket(byte[] gameId, int sueingNation, int turnIndex, int actionNation, int regionOneIndex, int regionTwoIndex, int regionThreeIndex) {
        this(gameId, sueingNation, turnIndex, actionNation);
        //Change OpCode
        packet[0] = GameOps.CONGRESS_FREESERF.valueOf();
        //Add Land Regions
        packet[REGION_ONE] = (byte)regionOneIndex;
        packet[REGION_TWO] = (byte)regionTwoIndex;
        packet[REGION_THREE] = (byte)regionThreeIndex;
    }

    public int getTurnIndex() { return packet[TURN_INDEX] & 0xff; }
    public int getSueingNation() { return packet[SUEING_NATION] & 0xff; }
    public int getActionNation() { return packet[ACTION_NATION] & 0xff; }

    //No bitwise and operation, so I can return negative numbers.
    public int getRegionOneIndex() { return packet[REGION_ONE]; }
    public int getRegionTwoIndex() { return packet[REGION_TWO]; }
    public int getRegionThreeIndex() { return packet[REGION_THREE]; }

    private final static int TURN_INDEX = 3;
    private final static int SUEING_NATION = 4;
    private final static int ACTION_NATION = 5;
    private final static int REGION_ONE = 6;
    private final static int REGION_TWO = 7;
    private final static int REGION_THREE = 8;
}