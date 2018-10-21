package game.packet.political;

import game.packet.GamePacket;
import game.packet.GameOps;

/**
 * SueForPeacePacket.java  Date Created: Dec 23, 2013
 *
 * Purpose:
 *
 * Description:
 *
 * @author Chrisb
 */
public class SueForPeacePacket extends GamePacket {

    public SueForPeacePacket(byte[] packet) {
        this.packet = packet;
    }

    public SueForPeacePacket(byte[] gameId, int sueingNation) {
        packet = new byte[256];
        packet[0] = GameOps.SUE_FOR_PEACE.valueOf();
        setGameId(gameId);
        packet[SUEING_NATION] = (byte) sueingNation;
    }

    public int getSueingNation() { return packet[SUEING_NATION] & 0xff; }

    private final static int SUEING_NATION = 3;
}