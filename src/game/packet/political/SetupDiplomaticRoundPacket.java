package game.packet.political;

import game.packet.GamePacket;
import game.packet.GameOps;

/**
 * SetupDiplomaticRoundPacket.java  Date Created: Dec 12, 2013
 *
 * Purpose: To send information relating to a diplomatic round that needs to be setup.
 *
 * Description:
 *
 * @author Chrisb
 */
public class SetupDiplomaticRoundPacket extends GamePacket {

    public SetupDiplomaticRoundPacket(byte[] packet) {
        this.packet = packet;
    }

    public SetupDiplomaticRoundPacket(byte[] gameId, int type, int doNation, int toNation) {
        packet = new byte[256];
        packet[0] = GameOps.SETUP_DIPLOMAT.valueOf();
        setGameId(gameId);
        packet[TYPE] = (byte) type;
        packet[TO_NATION] = (byte) toNation;
        packet[DO_NATION] = (byte) doNation;
    }

    public int getType() { return packet[TYPE] & 0xff; }
    public int getToNation() { return packet[TO_NATION] & 0xff; }
    public int getDoNation() { return packet[DO_NATION] & 0xff; }

    private final static int TYPE = 3;
    private final static int DO_NATION = 4;
    private final static int TO_NATION = 5;
}