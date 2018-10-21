package lobby.packet;

import lobby.controller.User;

/**
 * ChatPacket.java  Date Created: Apr 11, 2012
 *
 * Purpose: To deliver a chat message sent by a user.
 *
 * Description:  This lobby.packet will hold one chat message
 * sent by a user.
 *
 *  __ ____ _____________ __ _______________________________
 *  1   2       20        1               200
 *   LobbyId          msgLength       ChatMessage
 * OpCode     UserName
 *
 * @author Chrisb
 */
public class ChatPacket extends Packet {

    public ChatPacket (byte[] packet) {
        this.packet = packet;
    }

    public ChatPacket(byte[] lobbyId, String userName, String chatMessage) {
        packet = new byte[256];
        packet[0] = OpCode.CHAT.valueOf();
        packet[1] = lobbyId[0];
        packet[2] = lobbyId[1];

        int index = 3;
        for (char c: userName.toCharArray())
            packet[index++] = (byte)c;
        for(int i = userName.length(); i < User.MAX_USER_LENGTH + 1; i++)
            packet[index++]  = OpCode.EMPTY.valueOf();

        packet[index++] = chatMessage.length() < MAX_MSG_LENGTH ? (byte)chatMessage.length() : (byte)MAX_MSG_LENGTH;

        System.out.println(packet[index - 1] & 0xff);
        
        for (char c: chatMessage.toCharArray())
            if (index < 256)
                packet[index++] = (byte)c;
    }

    public String getUserName() {
        int userNameIndex = 3;
        int userNameLength = -1;

        while (packet[userNameIndex + userNameLength] != OpCode.EMPTY.valueOf()) {
            userNameLength++;
        }

        byte[] userName = new byte[userNameLength];

        System.arraycopy(packet, userNameIndex, userName, 0, userName.length);

        return new String(userName);
    }

    public String getMessage() {
        int length = packet[24] & 0xff;
        byte[] message = new byte[length];

        System.out.println(length);

        for (int i = 0; i < length; i++)
            message[i] = packet[25+i];

        return new String(message);
    }

    public byte[] getLobbyId() {
        return new byte[]{packet[1], packet[2]};
    }

    
    
    private final static int MAX_MSG_LENGTH = 231;
}
