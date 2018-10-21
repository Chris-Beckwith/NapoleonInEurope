package game.packet;

/**
 * EventFailControlNPNPacket.java  Date Created: Dec 12, 2013
 *
 * Purpose: To send information about a failed controlNPN action .
 *
 * Description:
 *
 * @author Chrisb
 */
public class EventFailControlNPNPacket extends GamePacket {

    public EventFailControlNPNPacket(byte[] packet) {
        this.packet = packet;
    }

    public EventFailControlNPNPacket(byte[] gameId, int userNation, int nonPlayerNation, int month, int year) {
        packet = new byte[256];
        packet[0] = GameOps.FAIL_CONTROL_NPN.valueOf();
        setGameId(gameId);
        packet[USER_NATION] = (byte) userNation;
        packet[NON_PLAYER_NATION] = (byte) nonPlayerNation;
        packet[MONTH] = (byte) month;
        packet[YEAR] = (byte) year;
    }

    public int getUserNation() { return packet[USER_NATION] & 0xff; }
    public int getNonPlayerNation() { return packet[NON_PLAYER_NATION] & 0xff; }
    public int getMonth() { return packet[MONTH] & 0xff; }
    public int getYear() { return packet[YEAR] & 0xff; }

    private final static int USER_NATION = 3;
    private final static int NON_PLAYER_NATION = 4;
    private final static int MONTH = 5;
    private final static int YEAR = 6;
}