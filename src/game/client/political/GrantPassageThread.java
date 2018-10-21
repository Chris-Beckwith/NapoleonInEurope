package game.client.political;

import util.ServerResponseThread;
import game.packet.political.GrantPassagePacket;

/**
 * GrantPassageThread.java  Date Created: Dec 6, 2013
 *
 * Purpose: To tell the server/client that Right of Passage has been granted.
 *
 * Description: A controlled nation can grant Right of Passage to another controlled nation.
 * This can be voluntary or forced (Sue for Peace).  Can also be done after land battles (except Annexation Battles).
 *
 * @author Chrisb
 */

public class GrantPassageThread extends ServerResponseThread {
    public GrantPassageThread(byte[] gameId, int grantingNation, int receivingNation, boolean isVoluntary) {
        super(gameId);
        threadPacket = new GrantPassagePacket(gameId, grantingNation, receivingNation, isVoluntary);
        threadOpCode = threadPacket.getOpCode();
    }
}