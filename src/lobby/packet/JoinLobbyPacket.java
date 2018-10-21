package lobby.packet;

import lobby.controller.User;
import lobby.controller.LobbyInstance;

import java.util.ArrayList;

/**
 * JoinLobbyPacket.java  Date Created: Apr 4, 2012
 *
 * Purpose: Information to send lobby.server, related to join lobby request.
 *
 * Description:  A join lobby request is made by creating one of these packets
 * and sending it to the lobby.server.  It will only have one user, the one requesting to join.
 * The response back will include the users in the lobby, excluding the user that requested
 * to join the lobby.
 *
 *   __ ____ (__ __________ __ __________)x6 [Up to 6 users]
 *   1   2    1      20     1      20
 * OpCode   Position      Nation
 *     LobbyId    UserName     LeaderName
 *
 * @author Chrisb
 */

public class JoinLobbyPacket extends Packet {

    public JoinLobbyPacket(byte[] packet) {
        this.packet = packet;
    }

    public JoinLobbyPacket(byte opCode) {
        packet[0] = opCode;
    }

    /**
     * Contructor with one user, the user attempting to join the lobby.
     * @param lobbyId - Id of the lobby. Also, the IP address to connect to.
     * @param user - user wanting to join lobby.
     */
    public JoinLobbyPacket(byte[] lobbyId, User user) {
        packet = new byte[256];
        packet[0] = OpCode.JOIN.valueOf();
        packet[1] = lobbyId[0];
        packet[2] = lobbyId[1];

        int index = 3;                
        index = addUser(index, user);

        packet[index] = OpCode.LAST.valueOf();
    }

    public JoinLobbyPacket(byte[] lobbyId, ArrayList<User> userList) {
        packet = new byte[256];
        packet[0] = OpCode.JOIN.valueOf();
        packet[1] = lobbyId[0];
        packet[2] = lobbyId[1];

        int index = 3;
        for(User u: userList)
            index = addUser(index, u);
        packet[index] = OpCode.LAST.valueOf();
    }

    public byte[] getLobbyId() {
        return new byte[]{packet[1],packet[2]};
    }

    public ArrayList<User> getUsers() {

        //TODO TESTING - Prob going to put in superclass.
        ArrayList<User> users = new ArrayList<User>();

        int positionIndex = 3;
        int userNameIndex = 4;
        int nationIndex   = 24;
        int leaderIndex   = 25;
        int increment     = 42;

        int tempPosition;
        int tempUserNameLength;
        byte[] tempUserName;
        int tempNation;
        int tempLeaderLength;
        byte[] tempLeaderName;
        User tempUser;

        for (int i = 0; i < LobbyInstance.MAX_USERS - 1; i++) {
            //Nothing else of value
            if ( packet[positionIndex + (increment*i)] == OpCode.LAST.valueOf() )
                break;

            tempPosition = packet[positionIndex + (increment*i)];
            tempNation = packet[nationIndex + (increment*i)];
            tempUserNameLength = -1;
            tempLeaderLength = -1;

            for (int j = 0; j < 20; j++) {
                //Nothing else of value
                if (packet[userNameIndex + (increment*i) + j] == OpCode.EMPTY.valueOf()) {
                    tempUserNameLength = j;
                    break;
                }
            }
            if (tempUserNameLength < 0)
                tempUserNameLength = 20;
            for (int j = 0; j < 20; j++) {
                //Nothing else of value
                if (packet[leaderIndex + (increment*i) + j] == OpCode.EMPTY.valueOf()) {
                    tempLeaderLength = j;
                    break;
                }
            }
            if (tempLeaderLength < 0)
                tempLeaderLength = 20;

            tempUserName = new byte[tempUserNameLength];
            tempLeaderName = new byte[tempLeaderLength];

            System.arraycopy(packet, userNameIndex + (increment*i), tempUserName, 0, tempUserName.length);
            System.arraycopy(packet, leaderIndex + (increment*i), tempLeaderName, 0, tempLeaderName.length);

            System.out.println("JoinLobbyPacket: " + tempPosition + " " + new String(tempUserName) + " " + tempNation + " " + new String(tempLeaderName));
            users.add(new User(new String(tempUserName), tempPosition, tempNation, new String(tempLeaderName)));
        }

        return users;
    }
    
}
