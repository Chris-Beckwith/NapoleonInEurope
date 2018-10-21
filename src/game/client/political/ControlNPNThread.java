package game.client.political;

import game.packet.political.ControlNPNPacket;
import util.ServerResponseThread;

/**
 * ControlNPNThread.java  Date Created: Dec 8, 2013
 *
 * Purpose: To inform the server/client of a control NPN action.
 *
 * Description: Send information to server about the action. Server bounces it back to clients.
 * If NPN is controlled, ask controlling nation if they want to give permission. Server Bounce.
 * After permission has been decided, then confirm political action with added line for permission.
 *
 * Else, take action, if roll is successful tell server (NationToControl).
 *
 * @author Chris
 */
public class ControlNPNThread extends ServerResponseThread {
    public ControlNPNThread(byte[] gameId, int playerNation, int nonPlayerNation, boolean isControlled, int permission) {
        super(gameId);
        threadPacket = new ControlNPNPacket(gameId, playerNation, nonPlayerNation, isControlled, permission);
        threadOpCode = threadPacket.getOpCode();
    }
}
