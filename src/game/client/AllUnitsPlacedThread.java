package game.client;

import game.packet.AllUnitsPlacedPacket;
import util.ServerResponseThread;

/**
 * AllUnitsPlacedThread.java  Date Created: Dec 18, 2013
 *
 * Purpose:
 *
 * Description:
 *
 * @author Chrisb
 */
public class AllUnitsPlacedThread  extends ServerResponseThread {
    public AllUnitsPlacedThread(byte[] gameId, int nation) {
        super(gameId);
        threadPacket = new AllUnitsPlacedPacket(gameId, nation);
        threadOpCode = threadPacket.getOpCode();
    }
}