package game.packet.political;

import game.packet.GamePacket;
import game.packet.GameOps;

/**
 * ReleaseNPNPacket.java  Date Created: Dec 19, 2013
 *
 * Purpose: To send the information to/from server related to a releaseNPN action.
 *
 * Description:
 *
 * @author Chrisb
 */
public class ReleaseNPNPacket extends GamePacket {

    public ReleaseNPNPacket(byte[] packet) {
        this.packet = packet;
    }

    public ReleaseNPNPacket(byte[] gameId, int playerNation, int nonPlayerNation) {
        packet = new byte[256];
        packet[0] = GameOps.RELEASE_NPN.valueOf();
        setGameId(gameId);
        packet[PLAYER_NATION] = (byte) playerNation;
        packet[NPN_NATION] = (byte) nonPlayerNation;
    }

    public int getPlayerNation() { return packet[PLAYER_NATION] & 0xff; }
    public int getNonPlayerNation() { return packet[NPN_NATION] & 0xff; }

    private final static int PLAYER_NATION = 3;
    private final static int NPN_NATION = 4;
}