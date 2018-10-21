package game.packet.political;

import game.packet.GamePacket;
import game.packet.GameOps;

/**
 * GrantPassagePacket.java  Date Created: Dec 6, 2013
 *
 * Purpose: To send/receive the information about a right of passage that has been granted.
 *
 * Description:
 *
 * @author Chrisb
 */
public class RescindPassagePacket extends GamePacket {

    public RescindPassagePacket(byte[] packet) {
        this.packet = packet;
    }

    public RescindPassagePacket(byte[] gameId, int rescindingNation, int losingNation) {
        packet = new byte[256];
        packet[0] = GameOps.RESCIND_PASSAGE.valueOf();
        setGameId(gameId);
        packet[RESCINDING_NATION] = (byte) rescindingNation;
        packet[LOSING_NATION] = (byte) losingNation;
    }

    public int getRescindingNation() { return packet[RESCINDING_NATION] & 0xff; }
    public int getLosingNation() { return packet[LOSING_NATION] & 0xff; }

    private final static int RESCINDING_NATION = 3;
    private final static int LOSING_NATION = 4;
}