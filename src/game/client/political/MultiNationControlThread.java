package game.client.political;

import game.packet.political.MultiNationPacket;
import util.ServerResponseThread;

import java.util.ArrayList;

/**
 * MultiNationControlThread.java  Date Created: Oct 28, 2013
 *
 * Purpose: To send the server information about nations that are eligible to take control of uncontrolled nation.
 *
 * Description:  When a nation declares war on an uncontrolled nation, someone has to take control of that nation.
 * If there is more than one nation eligible after the first one declines this thread is used to send the information
 * back to the server to inform the clients again of the next in line.
 *
 * @author Chrisb
 */
public class MultiNationControlThread extends ServerResponseThread {
    public MultiNationControlThread(byte[] gameId, int declaringNation, int uncontrolledNation, ArrayList<Integer> userNations) {
        super(gameId);
        threadPacket = new MultiNationPacket(gameId, declaringNation, uncontrolledNation, userNations);
        threadOpCode = threadPacket.getOpCode();
    }
}