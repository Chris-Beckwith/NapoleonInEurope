package lobby.packet;

/**
 * SetLobbyOptionPacket.java  Date Created: Apr 26, 2012
 *
 * Purpose: Tell the lobby.server that a game option has changed and which one.
 *
 * Description: When the host changed a game option, one of these packets
 * is created and sent to the lobby.server so the lobby.server can tell all the users
 * in the lobby.
 *
 *   __   ____   __   __
 *   1     2     1    1
 *  OpCode      Option
 *        LobbyId    IsOn
 *
 * @author Chrisb
 */
public class SetLobbyOptionPacket extends Packet {
    public SetLobbyOptionPacket(byte[] packet) {
        this.packet = packet;
    }

    public SetLobbyOptionPacket(byte[] lobbyId, int option, boolean isOn) {
        packet = new byte[6];
        packet[0] = OpCode.SET_OPTION.valueOf();
        packet[1] = lobbyId[0];
        packet[2] = lobbyId[1];
        packet[3] = (byte)option;
        packet[4] = isOn ? (byte)1 : (byte)0;
        packet[5] = OpCode.LAST.valueOf();
    }

    public byte[] getLobbyId() {
        return new byte[]{packet[1],packet[2]};
    }

    public int getOption() {
        return packet[3] & 0xff;
    }

    public boolean isOn() {
        return packet[4] == (byte)1;
    }
}
