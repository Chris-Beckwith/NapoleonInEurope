package game.packet.political;

import game.packet.GamePacket;
import game.packet.GameOps;

/**
 * BreakAlliancePacket.java  Date Created: Dec 5, 2013
 *
 * Purpose: To tell the server about an alliance to be broken.
 *
 * Description:
 *
 * @author Chrisb
 */
public class BreakAlliancePacket extends GamePacket {

    public BreakAlliancePacket(byte[] packet) {
        this.packet = packet;
    }

    public BreakAlliancePacket(byte[] gameId, int breakingNation, int alliedNation) {
        packet = new byte[256];
        packet[0] = GameOps.BREAK_ALLIANCE.valueOf();
        setGameId(gameId);
        packet[BREAKING_NATION] = (byte) breakingNation;
        packet[ALLIED_NATION] = (byte) alliedNation;
    }

    public int getBreakingNation() { return packet[BREAKING_NATION] & 0xff; }
    public int getAlliedNation() { return packet[ALLIED_NATION] & 0xff; }

    private final static int BREAKING_NATION = 3;
    private final static int ALLIED_NATION = 4;
}