package game.packet;

/**
 * GamePacket.java  Date Created: Jun 27, 2013
 *
 * Purpose:
 *
 * Description:
 *
 * @author Chrisb
 */
public class GamePacket {

    protected byte[] packet;

    /**
	 * Gets the opcode used by the GenericPacket.
	 * @return OpCode.
	 */
	public byte getOpCode() {
		return packet[0];
	}

    /**
	 * Gets the GenericPacket.
	 * @return Packet.
	 */
	public byte[] getPacket() {
		return packet;
	}

    /**
     * Sets teh gameId
     * @param gameId - id of the game
     */
    public void setGameId(byte[] gameId) {
        packet[1] = gameId[0];
        packet[2] = gameId[1];
    }

    /**
     * Gets the gameId
     * @return GameId
     */
    public byte[] getGameId() {
        return new byte[]{packet[1], packet[2]};
    }
}