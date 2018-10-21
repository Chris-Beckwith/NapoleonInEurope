package lobby.packet;

/**
 * FailurePacket.java  Date Created: Apr 5, 2012
 *
 * Purpose: When a request fails, send this lobby.packet.
 *
 * Description:
 *
 * @author Chrisb
 */

public class FailurePacket extends Packet {
    public FailurePacket(byte[] packet) {
        this.packet = packet;
    }

    public FailurePacket() {
        packet[0] = OpCode.FAILURE.valueOf();
    }

    public FailurePacket(byte[] lobbyId, byte failedOpCode) {
        packet = new byte[4];
        packet[0] = OpCode.FAILURE.valueOf();
        packet[1] = lobbyId[0];
        packet[2] = lobbyId[1];
        packet[3] = failedOpCode;
    }
        
    public byte[] getLobbyId() {
        return new byte[]{packet[1],packet[2]};
    }
}
