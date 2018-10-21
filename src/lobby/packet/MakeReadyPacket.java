package lobby.packet;

/**
 * MakeReadyPacket.java  Date Created: May 1, 2012
 *
 * Purpose: Tell the lobby.server when a user makes/unmakes ready.
 *
 * Description: Inform the lobby.server when a user changes their ready state.
 * The lobby.server will then inform the other users in the lobby.
 *
 *   __  ____  __  __
 *   1    2    1   1
 * OpCode   Position
 *     LobbyId   isGameReady
 *
 * @author Chrisb
 */
public class MakeReadyPacket extends Packet {
    public MakeReadyPacket(byte[] packet) {
        this.packet = packet;
    }

    public MakeReadyPacket(byte[] lobbyId, int position, boolean isReady) {
        packet = new byte[5];
        packet[0] = OpCode.MAKE_READY.valueOf();
        packet[1] = lobbyId[0];
        packet[2] = lobbyId[1];
        packet[3] = (byte)position;
        packet[4] = isReady ? (byte)1 : (byte)0;
    }

    public byte[] getLobbyId() {
        return new byte[]{packet[1], packet[2]};
    }

    public int getPosition() {
        return packet[3] & 0xff;
    }

    public boolean isReady() {
        return packet[4] == (byte)1;
    }
}
