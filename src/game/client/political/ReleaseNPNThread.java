package game.client.political;

import util.ServerResponseThread;
import game.packet.political.ReleaseNPNPacket;

/**
 * ReleaseNPNThread.java  Date Created: Dec 19, 2013
 *
 * Purpose: To tell the server a user is releasing control of a nonPlayer Nation.
 *
 * Description:
 *
 * @author Chrisb
 */
public class ReleaseNPNThread extends ServerResponseThread {
    public ReleaseNPNThread(byte[] gameId, int playerNation, int nonPlayerNation) {
        super(gameId);
        threadPacket = new ReleaseNPNPacket(gameId, playerNation, nonPlayerNation);
        threadOpCode = threadPacket.getOpCode();
    }
}