package game.packet;

import lobby.packet.Packet;

/**
 * GameReadyPacket.java  Date Created: Nov 7, 2012
 *
 * Purpose: To tell the client the game is ready.
 *
 * Description: When the server has finished sending all the information related
 * to a specific game, send the client notification that the game is ready to go.
 *
 * @author Chrisb
 */
public class GameReadyPacket extends GamePacket {
    public GameReadyPacket(byte[] packet) {
        this.packet = packet;
    }

    public GameReadyPacket(byte[] gameId, int nation) {
        packet = new byte[256];
        packet[0] = GameOps.GAME_READY.valueOf();
        packet[1] = gameId[0];
        packet[2] = gameId[1];
        packet[3] = (byte)nation;
    }

    public int getNation() {
        return packet[3] & 0xff;
    }
}