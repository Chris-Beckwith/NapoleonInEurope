package game.client.political;

import game.packet.political.NextDiplomatTurnPacket;
import util.ServerResponseThread;

/**
 * NextDiplomatTurnThread.java  Date Created: Nov 6, 2013
 *
 * Purpose: To tell the server to tell the clients it is the next diplomats turn.
 *
 * Description: This shall send a packet to the server which will be sent back to all clients.
 * Each client shall go to the next diplomat's turn, and if it is a nation controlled by the user
 * ask that user if they would like to take a political action during this diplomatic round with that nation.
 *
 * @author Chrisb
 */
public class NextDiplomatTurnThread extends ServerResponseThread {
    public NextDiplomatTurnThread(byte[] gameId, int actionType) {
        super(gameId);
        threadPacket = new NextDiplomatTurnPacket(gameId, actionType);
        threadOpCode = threadPacket.getOpCode();
    }
}