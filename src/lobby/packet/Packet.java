package lobby.packet;

import lobby.controller.User;

/**
 * Packet.java  Date Created:
 *
 * Purpose:
 *
 * Description:
 *
 * @author Chrisb
 */
public class Packet {
    protected byte[] packet;

    /**
	 * Gets the opcode used by the lobby.packet.
	 * @return OpCode.
	 */
	public byte getOpCode() {
		return packet[0];
	}

	/**
	 * Gets the lobby.packet.
	 * @return Packet.
	 */
	public byte[] getPacket() {
		return packet;
	}

    /**
     * Generic add user to a lobby.packet method.
     *
     *  __ __________ __ __________
     *  1      20     1      20
     * Position      Nation
     *     UserName1    LeaderName
     *
     * @param index - index in lobby.packet to start adding user.
     * @param u - user to be added
     * @return index - the last index needed for user.
     */
    protected int addUser(int index, User u) {
        packet[index++] = (byte)u.getPosition();

        for(char c: u.getUserName().toCharArray())
            packet[index++] = (byte)c;
        for(int i = u.getUserName().length(); i < User.MAX_USER_LENGTH; i++)
            packet[index++]  = OpCode.EMPTY.valueOf();

        packet[index++] = (byte)u.getNation();

        for(char c: u.getLeader().toCharArray())
            packet[index++] = (byte)c;
        for(int i = u.getLeader().length(); i < User.MAX_LEADER_LENGTH; i++)
            packet[index++] = OpCode.EMPTY.valueOf();
        return index;
    }       

}
