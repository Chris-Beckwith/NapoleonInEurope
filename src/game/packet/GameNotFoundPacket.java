package game.packet;

import lobby.packet.Packet;

/**
 * GameNotFoundPacket.java  Date Created: Nov 7, 2012
 *
 * Purpose: To send to the client when information requested about the game was not found.
 *
 * Description: When a request is made to the server for information on a specific game and
 * the game id does not match any in the list of games.  Send one of these packets back to the
 * client.  It will contain the Opcode and the gameId related to the request sent to the server.
 *
 * @author Chrisb
 */
public class GameNotFoundPacket extends GamePacket {

    public GameNotFoundPacket(byte[] packet) {
        this.packet = packet;
    }

    public GameNotFoundPacket(byte[] gameId, byte opCode) {
        packet = new byte[256];
        packet[0] = GameOps.GAME_NOT_FOUND.valueOf();
        packet[1] = gameId[0];
        packet[2] = gameId[1];
        packet[3] = opCode;
    }

    public byte[] getRequestedGameId() {
        return getGameId();
    }

    public byte getRequestedOpCode() {
        return packet[3];
    }
}