package game.client.political;

import game.packet.political.NationToControlPacket;
import util.ServerResponseThread;

/**
 * NationToControlThread.java  Date Created: Oct 28, 2013
 *
 * Purpose: This thread is called when last or only one nation is eligible to control
 * the uncontrolled nation that just had war declared on it.
 *
 * Description:
 *
 * @author Chrisb
 */
public class NationToControlThread extends ServerResponseThread {
    public NationToControlThread(byte[] gameId, int declaringNation, int uncontrolledNation, int userToControl,
                                 boolean isNotification) {
        super(gameId);
        threadPacket = new NationToControlPacket(gameId, declaringNation, uncontrolledNation, userToControl, isNotification);
        threadOpCode = threadPacket.getOpCode();
    }

    public NationToControlThread(byte[] gameId, int declaringNation, int uncontrolledNation, int userToControl) {
        this(gameId, declaringNation, uncontrolledNation, userToControl, false);
    }
}