package game.client;

import util.ServerResponseThread;
import game.packet.EventFailControlNPNPacket;

/**
 * EventFailControlNPNThread.java  Date Created: Dec 12, 2013
 *
 * Purpose: To inform everyone of a failed control NPN event.
 *
 * Description:
 *
 * @author Chrisb
 */
public class EventFailControlNPNThread extends ServerResponseThread {
    public EventFailControlNPNThread(byte[] gameId, int userNation, int nonPlayerNation, int month, int year) {
        super(gameId);
        threadPacket = new EventFailControlNPNPacket(gameId, userNation, nonPlayerNation, month, year);
        threadOpCode = threadPacket.getOpCode();
    }
}