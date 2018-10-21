package game.client.political;

import game.packet.political.FormAlliancePacket;
import util.ServerResponseThread;

/**
 * FormAllianceThread.java  Date Created: Nov 18, 2013
 *
 * Purpose: To tell the server an alliance has been created, server will then tell the clients.
 *
 * Description: This class shall run a thread that sends a packet to the server informing it of an alliance
 * that needs to be created.  The responds back with the same packet to inform the clients of the same information.
 *
 * @author Chrisb
 */
public class FormAllianceThread extends ServerResponseThread {
    public FormAllianceThread(byte[] gameId, int nationOne, int nationTwo, boolean isRequest, boolean hasAccepted) {
        super(gameId);
        threadPacket = new FormAlliancePacket(gameId, nationOne, nationTwo, isRequest, hasAccepted);
        threadOpCode = threadPacket.getOpCode();
    }
}