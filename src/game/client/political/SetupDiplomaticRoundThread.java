package game.client.political;

import util.ServerResponseThread;
import game.packet.political.SetupDiplomaticRoundPacket;

/**
 * SetupDiplomaticRoundThread.java  Date Created: Dec 12, 2013
 *
 * Purpose: To tell everyone that a diplomatic round has started.
 *
 * Description:
 *
 * @author Chrisb
 */
public class SetupDiplomaticRoundThread extends ServerResponseThread {
    public SetupDiplomaticRoundThread(byte[] gameId, int type, int doNation, int toNation) {
        super(gameId);
        threadPacket = new SetupDiplomaticRoundPacket(gameId, type, doNation, toNation);
        threadOpCode = threadPacket.getOpCode();
    }
}