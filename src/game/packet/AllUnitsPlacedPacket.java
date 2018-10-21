package game.packet;

/**
 * AllUnitsPlacedPacket.java  Date Created: Aug 29, 2013
 *
 * Purpose: To let the server/client know when all units for a nation have been placed.
 *
 * Description: This packet will be sent to the server, and to all clients as well, once
 * all units for a nation have been placed for each production round and the opening round.
 *   __  ____ __
 *   1    2   1
 * GameOp   Nation
 *      GameId
 *
 * @author Chrisb
 */
public class AllUnitsPlacedPacket extends GamePacket {

    public AllUnitsPlacedPacket(byte[] packet) {
        this.packet = packet;
    }

    public AllUnitsPlacedPacket(byte[] gameId, int nation) {
        packet = new byte[256];
        packet[0] = GameOps.UNITS_PLACED.valueOf();
        packet[1] = gameId[0];
        packet[2] = gameId[1];
        packet[nationIndex] = (byte)nation;
    }

    public int getNation() { return packet[nationIndex] & 0xff; }

    private static int nationIndex = 3;
}