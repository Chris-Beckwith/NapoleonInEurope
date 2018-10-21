package lobby.packet;

/**
 * LogoutPacket.java  Date Created: Apr 12, 2012
 *
 * Purpose: Tell the lobby.server that 'user' is logging out.
 *
 * Description:  This lobby.packet will tell the lobby.server that 'user'
 * will be logging out.
 *
 *   __ _____________
 *   1      20
 * OpCode UserName
 *
 * @author Chrisb
 */

/*
* (c) Copyright 2012 Deluxe Digital Studios. All Rights Reserved.
*/
public class LogoutPacket extends Packet {
    public LogoutPacket(byte[] packet) {
        this.packet = packet;
    }

    public LogoutPacket(String userName) {
        packet = new byte[256];
        packet[0] = OpCode.LOGOUT.valueOf();

        int index = userNameIndex;
        for (char c: userName.toCharArray())
            packet[index++] = (byte)c;

        packet[index] = OpCode.LAST.valueOf();
    }

    /**
     * @return - the userName logging in.
     */
    public String getUserName() {
        int length = 0;
        while (packet[userNameIndex + length] != OpCode.LAST.valueOf())
            length++;

        char[] userName = new char[length];

        for (int i = 0; i < userName.length; i++)
            userName[i] = (char)packet[i+userNameIndex];

        return new String(userName);
    }

    private final int userNameIndex = 1;
}
