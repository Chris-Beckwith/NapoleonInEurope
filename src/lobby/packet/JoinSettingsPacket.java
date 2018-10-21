package lobby.packet;

/**
 * JoinSettingsPacket.java  Date Created: May 1, 2012
 *
 * Purpose: Send the client the initial lobby settings.
 *
 * Description: These settings include whether each other user has
 * readied or not and what the game options are currently set at.
 *
 *   __  ____  ______________  ________________________  ______
 *   1    2          7                   20                3
 * OpCode       readyStates         gameOptions          M/S/D
 *     lobbyId                                   (mode)/(scene)/(duration)
 *
 * @author Chrisb
 */
public class JoinSettingsPacket extends Packet {
    public JoinSettingsPacket(byte[] packet) {
        this.packet = packet;
    }

    public JoinSettingsPacket(byte[] lobbyId, boolean[] readyStates, boolean[] options, int[] modeSceneDuration) {
        packet = new byte[256];
        packet[0] = OpCode.JOIN_SETTINGS.valueOf();
        packet[1] = lobbyId[0];
        packet[2] = lobbyId[1];

        int index = 3;
        for (boolean b: readyStates)
            packet[index++] = b ? (byte)1 : (byte)0;
        for (boolean b: options)
            packet[index++] = b ? (byte)1 : (byte)0;
        for (int i: modeSceneDuration)
            packet[index++] = (byte)i;
    }

    public byte[] getLobbyId() {
        return new byte[]{packet[1], packet[2]};
    }

    public boolean[] getReadyStates() {
        boolean[] readyStates = new boolean[READYLENGTH];

        for (int i = 0; i < readyStates.length; i++)
            readyStates[i] = packet[READY_INDEX + i] == 1;

        return readyStates;
    }

    public boolean[] getOptions() {
        boolean[] options = new boolean[OPTIONLENGTH];

        for (int i = 0; i < options.length; i++)
            options[i] = packet[OPTIONS_INDEX + i] == 1;

        return options;
    }

    public int[] getMSD() {
        int[] msd = new int[MSDLENGTH];

        for (int i = 0; i < msd.length; i++)
            msd[i] = packet[MSD_INDEX + i] & 0xff;

        return msd;
    }

    private final int READY_INDEX = 3;
    private final int OPTIONS_INDEX = 10;
    private final int MSD_INDEX = 30;

    private final int READYLENGTH = 7;
    private final int OPTIONLENGTH = 20;
    private final int MSDLENGTH = 3;
}
