package game.client.political;

import game.packet.political.RetaliationWarDecPacket;
import util.ServerResponseThread;

/**
 * RetaliationWarDecThread.java  Date Created: Oct 31, 2013
 *
 * Purpose: To allow nations to declare war on a nation that has declared war on an uncontrolled nation.
 *
 * Description: If no nations are at war with the declaringNation when they declare war on an
 * uncontrolled nation, all nations have a chance to declare war on the declaringNation.
 *
 * @author Chrisb
 */
public class RetaliationWarDecThread extends ServerResponseThread {
    public RetaliationWarDecThread(byte[] gameId, int declaringNation, int uncontrolledNation, boolean isDeclaringWar,
                                   int papCost, int retaliationNation) {
        super(gameId);
        threadPacket = new RetaliationWarDecPacket(gameId, declaringNation, uncontrolledNation, isDeclaringWar, papCost, retaliationNation);
        threadOpCode = threadPacket.getOpCode();
    }

    public RetaliationWarDecThread(byte[] gameId, int declaringNation, int uncontrolledNation, int retaliationNation) {
        this(gameId, declaringNation, uncontrolledNation, false, 0, retaliationNation);
    }
}