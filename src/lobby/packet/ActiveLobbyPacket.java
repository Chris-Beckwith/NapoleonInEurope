package lobby.packet;

import lobby.controller.LobbyInstance;

/**
 * ActiveLobbyPacket.java  Date Created: Apr 19, 2012
 *
 * Purpose: To request or send the active lobbys.
 *
 * Description: Client will use this lobby.packet to request the list of lobbys currently
 * being hosted by the lobby.server.
 *
 * The Server will use this lobby.packet to send back lists of the lobbys available.  More than one lobby.packet
 * may be needed to send the full list of lobbys. Up to 11 lobbys can be sent in one lobby.packet.
 *
 *   __ __ (____ ____________) x11
 *   1  1    2       32
 * OpCode   LobbyId
 *    NumOfLobbys  LobbyName
 *
 * @author Chrisb
 */

public class ActiveLobbyPacket extends Packet {
    public ActiveLobbyPacket(byte[] packet) {
        this.packet = packet;
    }

    public ActiveLobbyPacket() {
        packet = new byte[1];
        packet[0] = OpCode.GETLOBBYS.valueOf();
    }

    public ActiveLobbyPacket(int numOfLobbys, byte[][] lobbyIds, String[] lobbyNames) {
        packet = new byte[256];
        packet[0] = OpCode.GETLOBBYS.valueOf();
        packet[1] = (byte)numOfLobbys;

        int index = 2;
        for (int i = 0; i < numOfLobbys; i++) {
            //Add LobbyId
            packet[index++] = lobbyIds[i][0];
            packet[index++] = lobbyIds[i][1];

            //Add LobbyName
            for ( char c: lobbyNames[i].toCharArray() )
                packet[index++] = (byte)c;
            for (int j = lobbyNames[i].length(); j < LobbyInstance.MAX_NAME_LENGTH; j++)
                packet[index++] = OpCode.EMPTY.valueOf();
        }

        packet[index] = OpCode.LAST.valueOf();
    }

    public ActiveLobbyPacket(byte opCode, int numOfLobbys, byte[][] lobbyIds, String[] lobbyNames) {
        packet = new byte[256];
        packet[0] = opCode;
        packet[1] = (byte)numOfLobbys;

        int index = 2;
        for (int i = 0; i < numOfLobbys; i++) {
            //Add LobbyId
            packet[index++] = lobbyIds[i][0];
            packet[index++] = lobbyIds[i][1];

            //Add LobbyName
            for ( char c: lobbyNames[i].toCharArray() )
                packet[index++] = (byte)c;
            for (int j = lobbyNames[i].length(); j < LobbyInstance.MAX_NAME_LENGTH; j++)
                packet[index++] = OpCode.EMPTY.valueOf();
        }

        packet[index] = OpCode.LAST.valueOf();
    }

    public byte[][] getLobbyIds() {
        int numOfLobbys = packet[1] & 0xff;
        byte[][] lobbyIds = new byte[numOfLobbys][2];

        for (int i = 0; i < numOfLobbys; i++) {
            lobbyIds[i][0] = packet[2 + i*(LobbyInstance.MAX_NAME_LENGTH + 2)];
            lobbyIds[i][1] = packet[3 + i*(LobbyInstance.MAX_NAME_LENGTH + 2)];
        }
        return lobbyIds;
    }

    public String[] getLobbyNames() {
        int numOfLobbys = packet[1] & 0xff;
        String[] lobbyNames = new String[numOfLobbys];
        
        for (int i = 0; i < numOfLobbys; i++) {
            int length = 0;
//            System.out.print((char)lobby.packet[length + 4 + i*(LobbyInstance.MAX_NAME_LENGTH + 2) - 2] + " ");
//            System.out.print((char)lobby.packet[length + 4 + i*(LobbyInstance.MAX_NAME_LENGTH + 2) - 1] + " ");
//            System.out.print((char)lobby.packet[length + 4 + i*(LobbyInstance.MAX_NAME_LENGTH + 2) ] + " ");
//            System.out.println((char)lobby.packet[length + 4 + i*(LobbyInstance.MAX_NAME_LENGTH + 2) + 1]);

            while (packet[length + startOfName + i*(LobbyInstance.MAX_NAME_LENGTH + 2)] != OpCode.EMPTY.valueOf() && length < LobbyInstance.MAX_NAME_LENGTH)
                length++;

            char[] tempLobbyName = new char[length];
            for (int j = 0; j < tempLobbyName.length; j++)
                tempLobbyName[j] = (char)packet[j + startOfName + i*(LobbyInstance.MAX_NAME_LENGTH + 2)];

            lobbyNames[i] = new String(tempLobbyName);
        }

        return lobbyNames;
    }

    private final int startOfName = 4;
}
