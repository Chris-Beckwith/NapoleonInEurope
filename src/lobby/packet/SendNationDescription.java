package lobby.packet;

/**
 * SendNationDescription.java  Date Created: Apr 17, 2012
 *
 * Purpose: Send the nation description info to the lobby.server.
 *
 * Description: This lobby.packet will hold the userName of the 'user' making
 * the change, the lobbyId, and description of the nation.
 *
 *   __ ____ __________ ___________
 *   1   2      20          20
 * OpCode    UserName
 *    LobbyId           LeaderName
 *
 * @author Chrisb
 */

public class SendNationDescription extends Packet {
    public SendNationDescription(byte[] packet) {
        this.packet = packet;
    }

    public SendNationDescription(byte[] lobbyId, int userPosition, String leaderName) {
        packet = new byte[256];
        packet[0] = OpCode.NATION_DESC.valueOf();
        packet[1] = lobbyId[0];
        packet[2] = lobbyId[1];

        packet[3] = (byte)userPosition;

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

    public String getLeaderName() {
        int length = 0;
        while (packet[length + leaderIndex] != OpCode.LAST.valueOf())
            length++;

        char[] leaderName = new char[length];
        for (int i = 0; i < leaderName.length; i++)
            leaderName[i] = (char)packet[i+leaderIndex];

        return new String(leaderName);
    }

    private final int leaderIndex = 4;
}
