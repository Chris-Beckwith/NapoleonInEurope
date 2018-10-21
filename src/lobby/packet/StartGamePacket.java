package lobby.packet;

import shared.controller.LobbyConstants;

/**
 * StartGamePacket.java  Date Created: May 4, 2012
 *
 * Purpose: Used to request and approve starting a game.
 *
 * Description: This lobby.packet will be sent to the lobby.server when a game lobby
 * wishes to start a game.  The lobby.server will use this lobby.packet to respond
 * telling the client to start the game or that some users are not ready.
 *
 *   __  ____  ________________  ______
 *   1    2          19            3
 * OpCode         options
 *      LobbyId                   msd
 *
 * @author Chrisb
 */
public class StartGamePacket extends Packet {
    public StartGamePacket(byte[] packet) {
        this.packet = packet;
    }

    public StartGamePacket(byte opCode, byte[] lobbyId) {
        packet = new byte[256];
        packet[0] = opCode;
        packet[1] = lobbyId[0];
        packet[2] = lobbyId[1];
    }

    public StartGamePacket(byte[] lobbyId, boolean[] options, int[] msd, int nation) {
        packet = new byte[256];
        packet[0] = OpCode.START_GAME.valueOf();
        packet[1] = lobbyId[0];
        packet[2] = lobbyId[1];
        packet[3] = (byte)nation;
        int index = optionsIndex;
        for (boolean option : options)
            packet[index++] = option ? (byte) 1 : (byte) 0;
        for (int aMsd : msd)
            packet[index++] = (byte) aMsd;

    }

    public byte[] getLobbyId() {
        return new byte[]{packet[1],packet[2]};
    }

    public boolean[] getOptions() {
        boolean[] options = new boolean[LobbyConstants.NUM_OF_OPTIONS];

        for (int i = 0; i < LobbyConstants.NUM_OF_OPTIONS; i++)
            options[i] = packet[i+optionsIndex] == 1;

        return options;
    }

    public int[] getMSD() {
        int[] msd = new int[3];
        msd[0] = packet[optionsIndex + LobbyConstants.NUM_OF_OPTIONS] & 0xff;
        msd[1] = packet[optionsIndex + LobbyConstants.NUM_OF_OPTIONS+1] & 0xff;
        msd[2] = packet[optionsIndex + LobbyConstants.NUM_OF_OPTIONS+2] & 0xff;

        return msd;
    }

    public int getNation() {
        return packet[3] & 0xff;
    }

    private final int optionsIndex = 4;
}
