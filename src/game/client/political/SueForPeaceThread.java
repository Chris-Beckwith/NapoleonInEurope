package game.client.political;

import util.ServerResponseThread;
import game.packet.political.SueForPeacePacket;

/**
 * SueForPeaceThread.java  Date Created: Dec 23, 2013
 *
 * Purpose: To tell the server a nation is sueing for peace.
 *
 * Description:
 *
 * @author Chrisb
 */
public class SueForPeaceThread extends ServerResponseThread {
    public SueForPeaceThread(byte[] gameId, int sueingNation) {
        super(gameId);
        threadPacket = new SueForPeacePacket(gameId, sueingNation);
        threadOpCode = threadPacket.getOpCode();
    }
}