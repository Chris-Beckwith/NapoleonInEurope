package lobby.packet;

/**
 * SetNationPacket.java  Date Created: Apr 12, 2012
 *
 * Purpose: Information to send to lobby.server to request nation change.
 *
 * Description: A set nation request is made by sending the following
 * information to the lobby.server.  The lobby.server will check if nation is
 * currently controlled by another player then respond back with
 * the new nation, if it is open, or the previously selected nation.
 *
 *    __  ____  __  __  __________
 *    1    2    1   1      20
 * OpCode    position  leaderName
 *      lobbyId   nation
 *
 * @author Chrisb
 */

public class SetNationPacket extends Packet {

    /**
     * Default constructor
     * @param packet - default lobby.packet
     */
    public SetNationPacket(byte[] packet) {
        this.packet = packet;
    }

    /**
     * Contructor used to create a lobby.packet to request control of a nation.
     * @param lobbyId - the Id of the lobby where request was made
     * @param userPosition - user requesting control
     * @param nation - nation user wants to control
     * @param leaderName - the leader name of the nation user wants to control
     */
    public SetNationPacket(byte[] lobbyId, int userPosition, int nation, String leaderName) {
        packet = new byte[256];
        packet[0] = OpCode.SET_NATION.valueOf();
        packet[1] = lobbyId[0];
        packet[2] = lobbyId[1];
        packet[3] = (byte)userPosition;
        packet[4] = (byte)nation;

        int index = leaderIndex;
        for (char c: leaderName.toCharArray())
            packet[index++] = (byte)c;

        packet[index] = OpCode.LAST.valueOf();
        
    }

    public byte[] getLobbyId() {
        return new byte[]{packet[1], packet[2]};
    }

    public int getPosition() {
        return packet[3] & 0xff;
    }

    public int getNation() {
        return packet[4] & 0xff;
    }

    public String getLeaderName() {
        int length = 0;
        while (packet[length + leaderIndex] != OpCode.LAST.valueOf())
            length++;

        char[] leaderName = new char[length];
        for (int i = 0; i < leaderName.length; i++)
            leaderName[i] = (char)packet[i+leaderIndex];

        return new String(leaderName);
    }

    private final int leaderIndex = 5;
}
