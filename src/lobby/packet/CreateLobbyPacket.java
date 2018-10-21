package lobby.packet;

import lobby.controller.User;
import lobby.controller.LobbyInstance;

/**
 * CreateLobbyPacket.java  Date Created: Apr 5, 2012
 *
 * Purpose: Information to send to lobby.server to create a new lobby.
 *
 * Description: A create lobby request is made by creating one of these
 * packets and sending it to the lobby.server.
 *
 *    __ ____ ________________ _____________________
 *    1   2        32                  42
 *  OpCode      LobbyName             User
 *     LobbyId
 *
 * @author Chrisb
 */

public class CreateLobbyPacket extends Packet {

    public CreateLobbyPacket (byte[] packet) {
        this.packet = packet;
    }

    public CreateLobbyPacket(String lobbyName, User user) {
        packet = new byte[256];        
        packet[0] = OpCode.CREATE.valueOf();
        packet[1] = OpCode.EMPTY.valueOf();
        packet[2] = OpCode.EMPTY.valueOf();

        int index = lobbyNameIndex;
        for (char c: lobbyName.toCharArray())
            packet[index++] = (byte)c;
        for(int i = lobbyName.length(); i < LobbyInstance.MAX_NAME_LENGTH + 1; i++)
            packet[index++]  = OpCode.EMPTY.valueOf();

        index = addUser(index, user);
        
        packet[index] = OpCode.LAST.valueOf();
    }

    public CreateLobbyPacket(String lobbyName, byte[] lobbyId, User user) {
        packet = new byte[256];
        packet[0] = OpCode.CREATE.valueOf();
        packet[1] = lobbyId[0];
        packet[2] = lobbyId[1];

        int index = lobbyNameIndex;
        for (char c: lobbyName.toCharArray())
            packet[index++] = (byte)c;
        for(int i = lobbyName.length(); i < LobbyInstance.MAX_NAME_LENGTH + 1; i++)
            packet[index++]  = OpCode.EMPTY.valueOf();

        index = addUser(index, user);

        packet[index] = OpCode.LAST.valueOf();
    }

    public String getLobbyName() {
        int length = 0;
        while (packet[length + lobbyNameIndex] != OpCode.EMPTY.valueOf())
            length++;

        char[] lobbyName = new char[length];
        for (int i = 0; i < lobbyName.length; i++)
            lobbyName[i] = (char)packet[i+lobbyNameIndex];

        return new String(lobbyName);        
    }

    //TODO Do I move this to super?
    public User getUser() {
        int positionIndex = LobbyInstance.MAX_NAME_LENGTH + 4;
        int userNameIndex = LobbyInstance.MAX_NAME_LENGTH + 5;
        int nationIndex   = LobbyInstance.MAX_NAME_LENGTH + User.MAX_USER_LENGTH + 5;
        int leaderIndex   = LobbyInstance.MAX_NAME_LENGTH + User.MAX_USER_LENGTH + 6;

        int tempUserNameLength = -1;
        byte[] tempUserName;

        int tempLeaderLength = -1;
        byte[] tempLeaderName;

        //Nothing else of value
        if ( packet[positionIndex] == OpCode.LAST.valueOf() )
            return null;

        for (int j = 0; j < 20; j++) {
            //Nothing else of value
            if (packet[userNameIndex + j] == OpCode.EMPTY.valueOf()) {
                tempUserNameLength = j;
                break;
            }
        }
        if (tempUserNameLength < 0)
            tempUserNameLength = 20;
        for (int j = 0; j < 20; j++) {
            //Nothing else of value
            if (packet[leaderIndex + j] == OpCode.EMPTY.valueOf()) {
                tempLeaderLength = j;
                break;
            }
        }
        if (tempLeaderLength < 0)
            tempLeaderLength = 20;

        tempUserName = new byte[tempUserNameLength];
        tempLeaderName = new byte[tempLeaderLength];

        System.arraycopy(packet, userNameIndex, tempUserName, 0, tempUserName.length);
        System.arraycopy(packet, leaderIndex, tempLeaderName, 0, tempLeaderName.length);

        return new User(new String(tempUserName), packet[positionIndex], packet[nationIndex], new String(tempLeaderName));
    }

    public byte[] getLobbyId() {
        return new byte[]{packet[1],packet[2]};
    }

    private final int lobbyNameIndex = 3;
}
