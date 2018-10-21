package game.packet.political;

import game.packet.GamePacket;
import game.packet.GameOps;

/**
 * ConcludeArmisticePacket.java  Date Created: Dec 3, 2013
 *
 * Purpose:
 *
 * Description:
 *
 * @author Chrisb
 */
public class ConcludeArmisticePacket  extends GamePacket {

    public ConcludeArmisticePacket(byte[] packet) {
        this.packet = packet;
    }

    public ConcludeArmisticePacket(byte[] gameId, int nationOne, int nationTwo, boolean isRequest, boolean hasAccepted) {
        packet = new byte[256];
        packet[0] = GameOps.ARMISTICE.valueOf();
        setGameId(gameId);
        packet[NATION_ONE] = (byte) nationOne;
        packet[NATION_TWO] = (byte) nationTwo;
        packet[IS_REQUEST] = isRequest ? (byte) 1 : (byte) 0;
        packet[HAS_ACCEPTED] = hasAccepted ? (byte)1 : (byte)0;
    }

    public int getNationOne() { return packet[NATION_ONE] & 0xff; }
    public int getNationTwo() { return packet[NATION_TWO] & 0xff; }
    public boolean isRequest() { return packet[IS_REQUEST] == 1; }
    public boolean hasAccepted() { return packet[HAS_ACCEPTED] == 1; }

    private final static int NATION_ONE = 3;
    private final static int NATION_TWO = 4;
    private final static int IS_REQUEST = 5;
    private final static int HAS_ACCEPTED = 6;
}