package game.packet.political;

import game.packet.GamePacket;
import game.packet.GameOps;

/**
 * NextDiplomatTurnPacket.java  Date Created: Nov 06, 2013
 *
 * Purpose: To tell the server to tell the clients it is the next diplomats turn.
 *
 * Description: This shall send a packet to the server which will be sent back to all clients.
 * Each client shall go to the next diplomat's turn, and if it is a nation controlled by the user
 * ask that user if they would like to take a political action during this diplomatic round with that nation.
 *
 * @author Chrisb
 */
public class NextDiplomatTurnPacket extends GamePacket {

    public NextDiplomatTurnPacket(byte[] packet) {
        this.packet = packet;
    }

    public NextDiplomatTurnPacket(byte[] gameId, int type) {
        packet = new byte[256];
        packet[0] = GameOps.NEXT_DIPLOMAT.valueOf();
        setGameId(gameId);
        packet[ACTION_TYPE] = (byte) type;
    }

    public int getActionType() { return packet[ACTION_TYPE] & 0xff; }

    private final static int ACTION_TYPE = 3;
}