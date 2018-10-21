package game.client.political;

import game.packet.political.BreakAlliancePacket;
import util.ServerResponseThread;

/**
 * BreakAllianceThread.java  Date Created: Dec 5, 2013
 *
 * Purpose: To tell the server an alliance has been dismantled, server will then tell the clients.
 *
 * Description: This class shall run a thread that sends a packet to the server informing it of an alliance
 * that needs to be dismantled.  The responds back with the same packet to inform the clients of the same information.
 *
 * @author Chrisb
 */
public class BreakAllianceThread extends ServerResponseThread {
    public BreakAllianceThread(byte[] gameId, int breakingNation, int alliedNation) {
        super(gameId);
        threadPacket = new BreakAlliancePacket(gameId, breakingNation, alliedNation);
        threadOpCode = threadPacket.getOpCode();
    }
}