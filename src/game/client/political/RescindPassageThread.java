package game.client.political;

import util.ServerResponseThread;
import game.packet.political.GrantPassagePacket;
import game.packet.political.RescindPassagePacket;

/**
 * RescindPassageThread.java  Date Created: Dec 6, 2013
 *
 * Purpose: To tell the server/client that Right of Passage has been rescinded.
 *
 * Description: A controlled nation can rescind Right of Passage to another controlled nation.
 *
 * @author Chrisb
 */

public class RescindPassageThread extends ServerResponseThread {
    public RescindPassageThread(byte[] gameId, int rescindingNation, int losingNation) {
        super(gameId);
        threadPacket = new RescindPassagePacket(gameId, rescindingNation, losingNation);
        threadOpCode = threadPacket.getOpCode();
    }
}