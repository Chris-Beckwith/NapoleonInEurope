package lobby.packet;

import lobby.controller.User;
import util.Logger;

/**
 * LeaveLobbyPacket.java  Date Created: Apr 12, 2012
 *
 * Purpose: To tell the lobby.server that a user is leaving a lobby.
 *
 * Description: This lobby.packet will have a userName of the user
 * leaving the lobby with the given lobbyId.
 *
 *   __ ____ _____________
 *   1   2        20
 * OpCode      UserName
 *     LobbyId
 *
 * @author Chrisb
 */

public class LeaveLobbyPacket extends Packet {
    public LeaveLobbyPacket(byte[] packet) {
        this.packet = packet;
    }

    public LeaveLobbyPacket(byte[] lobbyId, String userName) {
        packet = new byte[256];
        packet[0] = OpCode.LEAVE_LOBBY.valueOf();
        packet[1] = lobbyId[0];
        packet[2] = lobbyId[1];

        int index = userNameIndex;
        for (char c: userName.toCharArray())
            packet[index++] = (byte)c;
        for(int i = userName.length(); i < User.MAX_USER_LENGTH + 1; i++)
            packet[index++]  = OpCode.EMPTY.valueOf();
        packet[index] = OpCode.LAST.valueOf();
    }

    public String getUserName() {
        int userNameLength = -1;

        while (packet[userNameIndex + userNameLength] != OpCode.EMPTY.valueOf()
                && packet[userNameIndex + userNameLength] != OpCode.LAST.valueOf())
            userNameLength++;

        try {
            byte[] userName = new byte[userNameLength];
            System.arraycopy(packet, userNameIndex, userName, 0, userName.length);

            return new String(userName);
        } catch (Exception e) {
            System.err.println("LeaveLobbyPacket:getUserName");
            e.printStackTrace();
            Logger.log("LeaveLobbyPacket:getUserName - " + e.getMessage());
            return "";
        }
    }

    public byte[] getLobbyId() {
        return new byte[]{packet[1], packet[2]};
    }

    private final int userNameIndex = 3;
}
