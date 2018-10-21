package game.client.political;

import util.ServerResponseThread;

import game.packet.political.CongressActionPacket;

/**
 * CongressActionThread.java  Date Created: Dec 27, 2013
 *
 * Purpose: To send the server information about the next congress nation.
 *
 * Description:
 *
 * @author Chrisb
 */
public class CongressActionThread extends ServerResponseThread {
    //Pass Action Constructor
    public CongressActionThread(byte[] gameId, int sueingNation, int turnIndex, int actionNation) {
        super(gameId);
        threadPacket = new CongressActionPacket(gameId, sueingNation, turnIndex, actionNation);
        threadOpCode = threadPacket.getOpCode();
    }
    //Annex Action Constructor
    public CongressActionThread(byte[] gameId, int sueingNation, int turnIndex, int actionNation, int regionOne) {
        super(gameId);
        threadPacket = new CongressActionPacket(gameId, sueingNation, turnIndex, actionNation, regionOne);
        threadOpCode = threadPacket.getOpCode();
    }
    //Restore Action Constructor
    public CongressActionThread(byte[] gameId, int sueingNation, int turnIndex, int actionNation, int regionOne, int regionTwo) {
        super(gameId);
        threadPacket = new CongressActionPacket(gameId, sueingNation, turnIndex, actionNation, regionOne, regionTwo);
        threadOpCode = threadPacket.getOpCode();
    }
    //Free Serfs Action Constructor
    public CongressActionThread(byte[] gameId, int sueingNation, int turnIndex, int actionNation, int regionOne, int regionTwo, int regionThree) {
        super(gameId);
        threadPacket = new CongressActionPacket(gameId, sueingNation, turnIndex, actionNation, regionOne, regionTwo, regionThree);
        threadOpCode = threadPacket.getOpCode();
    }
}