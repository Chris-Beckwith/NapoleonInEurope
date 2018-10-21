package game.client.political;

import game.controller.Region.LandRegion;
import game.controller.Region.Port;
import game.packet.political.MoveNPNUnitsPacket;
import util.ServerResponseThread;

import java.util.ArrayList;

/**
 * MoveNPNUnitsThread.java  Date Created: Jan 21, 2014
 *
 * Purpose: To move NPN Units after the nation sues for peace.
 *
 * Description: If an NPN sues for peace the nation becomes uncontrolled.
 *
 * @author Chrisb
 */
public class MoveNPNUnitsThread extends ServerResponseThread {
    public MoveNPNUnitsThread(byte[] gameId, int sueNation, ArrayList<Integer> regionsToMoveUnits,
                              ArrayList<Integer> portsToMoveInTo, ArrayList<Integer> portsToMoveOutOf) {
        super(gameId);
        threadPacket = new MoveNPNUnitsPacket(gameId, sueNation, regionsToMoveUnits, portsToMoveInTo, portsToMoveOutOf);
        threadOpCode = threadPacket.getOpCode();
    }
}