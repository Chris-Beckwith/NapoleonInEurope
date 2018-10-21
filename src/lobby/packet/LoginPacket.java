package lobby.packet;

import lobby.controller.User;

/**
 * LoginPacket.java  Date Created: Apr 10, 2012
 *
 * Purpose: Send one of these packets to the lobby.server for a login request.
 *
 * Description:  This lobby.packet will contain the userName and encrypted password of the user
 * requesting to login.  The lobby.server will use this information to check against the user
 * database.
 *
 *     __  __________ [__________]
 *     1      20           20
 *  OpCode UserName     Password
 *
 * @author Chrisb
 */
public class LoginPacket extends Packet {

    public LoginPacket (byte[] packet) {
        this.packet = packet;
    }

    /**
     * Constructor with password.  This will be used by the client to
     * send the credentials for logging in.  Both userName and password
     * will be encrypted.
     * @param userName - name of the user.
     * @param password - password for user account.
     */
    public LoginPacket (String userName, String password) {
        packet = new byte[41];
        packet[0] = OpCode.LOGIN.valueOf();

        int index = 1;
        for (char c: userName.toCharArray())
            packet[index++] = (byte)c;
        for(int i = userName.length(); i < User.MAX_USER_LENGTH; i++)
            packet[index++] = OpCode.EMPTY.valueOf();

        for (char c: password.toCharArray())
            packet[index++] = (byte)c;

    }

    /**
     * Contructor without password. This will be used by lobby.server to
     * send back just the userName of the user that successfully logged in.
     * @param userName - name of the user that logged in.
     */
    public LoginPacket(String userName) {
        packet = new byte[21];
        packet[0] = OpCode.LOGIN.valueOf();

        int index = 1;
        for (char c: userName.toCharArray())
            packet[index++] = (byte)c;
        for(int i = userName.length(); i < User.MAX_USER_LENGTH; i++)
            packet[index++] = OpCode.EMPTY.valueOf();

        packet[index] = OpCode.LAST.valueOf();
    }

    /**
     * @return - the userName logging in.
     */
    public String getUserName() {
        int length = 1;

        while (packet[length] != OpCode.EMPTY.valueOf() && packet[length] != OpCode.LAST.valueOf())
            length++;

        char[] userName = new char[length - 1];

        for (int i = 0; i < userName.length; i++)
            userName[i] = (char)packet[i+1];

        return new String(userName);
    }

    public String getPassword() {
        //TODO
        return "";
    }
}
