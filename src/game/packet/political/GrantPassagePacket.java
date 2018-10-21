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
public class GrantPassagePacket extends GamePacket {

    public GrantPassagePacket(byte[] packet) {
        this.packet = packet;
    }

    public GrantPassagePacket(byte[] gameId, int grantingNation, int receivingNation, boolean isVoluntary) {
        packet = new byte[256];
        packet[0] = GameOps.GRANT_PASSAGE.valueOf();
        setGameId(gameId);
        packet[GRANTING_NATION] = (byte) grantingNation;
        packet[RECEIVING_NATION] = (byte) receivingNation;
        packet[IS_VOLUNTARY] = isVoluntary? (byte)1 : (byte)0;
    }

    public int getGrantingNation() { return packet[GRANTING_NATION] & 0xff; }
    public int getReceivingNation() { return packet[RECEIVING_NATION] & 0xff; }
    public boolean isVoluntary() { return packet[IS_VOLUNTARY] == 1; }

    private final static int GRANTING_NATION = 3;
    private final static int RECEIVING_NATION = 4;
    private final static int IS_VOLUNTARY = 5;
}