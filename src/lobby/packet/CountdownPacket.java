package lobby.packet;

/**
 * CountdownPacket.java  Date Created: Oct 3, 2012
 *
 * Purpose: Relay the countdown status to the clients currently in the lobby that is about to start.
 *
 * Description: This class will contain status information related to progress of the countdown.  It shall be
 * sent to each client in the lobby.  The only parameter is the number of seconds left in the countdown.
 *
 *  __ ____ __
 *  1   2   1
 *   LobbyId
 * OpCode  SecondsRemaining
 *
 * @author Chrisb
 */
public class CountdownPacket extends Packet {

    public CountdownPacket (byte[] packet) {
        this.packet = packet;
    }
    /**
     * Contructor used to create a lobby.packet to request control of a nation.
     * @param lobbyId - the Id of the lobby where request was made
     * @param seconds - the number of seconds remaining in the countdown
     */
    public CountdownPacket(byte[] lobbyId, int seconds) {
        packet = new byte[256];
        packet[0] = OpCode.COUNTDOWN.valueOf();
        packet[1] = lobbyId[0];
        packet[2] = lobbyId[1];
        packet[3] = (byte)seconds;
    }

    public byte[] getLobbyId() {
        return new byte[]{packet[1], packet[2]};
    }

    public int getSecondsRemaining() {
        return packet[3] & 0xff;
    }

    public void setSecondsRemaining(int seconds) {
        packet[3] = (byte)seconds;
    }
}