package game.client.political;

import game.packet.political.ConcludeArmisticePacket;
import util.ServerResponseThread;

/**
 * ConcludeArmisticeThread.java  Date Created: Dec 3, 2013
 *
 * Purpose: To tell the server an armistice has been created, server will then tell the clients.
 *
 * Description: This class shall run a thread that sends a packet to the server informing it of an armistice
 * that needs to be created.  The responds back with the same packet to inform the clients of the same information.
 *
 * @author Chrisb
 */
public class ConcludeArmisticeThread extends ServerResponseThread {
    public ConcludeArmisticeThread(byte[] gameId, int nationOne, int nationTwo, boolean isRequest, boolean hasAccepted) {
        super(gameId);
        threadPacket = new ConcludeArmisticePacket(gameId, nationOne, nationTwo, isRequest, hasAccepted);
        threadOpCode = threadPacket.getOpCode();
    }
}