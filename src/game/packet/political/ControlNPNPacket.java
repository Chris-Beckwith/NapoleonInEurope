package game.packet.political;

import game.packet.GameOps;
import game.packet.GamePacket;

/**
 * ControlNPNPacket.java  Date Created: Dec 8, 2013
 *
 * Purpose:
 *
 * Description: Permission ~ -1, has not been asked and/or roll has not been taken.
 *              Permission ~  0, has not been granted.
 *              Permission ~  1, has been granted.
 *
 * @author Chris
 */
public class ControlNPNPacket extends GamePacket {

    public ControlNPNPacket(byte[] packet) {
        this.packet = packet;
    }

    /**
     * This constructor is used to send the eligible nation to/from the server.
     * @param gameId - id of the game this request is being made from.
     * @param playerNation - the nation attempting control.
     * @param nonPlayerNation - the nation attempting to be controlled.
     * @param isControlled - states if the nonPlayerNation is currently controlled.
     * @param permission - has permission been granted, has permission been denied, has permission been asked.
     */
    public ControlNPNPacket(byte[] gameId, int playerNation, int nonPlayerNation, boolean isControlled, int permission) {
        packet = new byte[256];
        packet[0] = GameOps.CONTROL_NPN.valueOf();
        setGameId(gameId);
        packet[PLAYER_NATION] = (byte)playerNation;
        packet[NP_NATION] = (byte) nonPlayerNation;
        packet[IS_CONTROLLED] = isControlled ? (byte)1 : (byte)0;
        packet[PERMISSION] = (byte)permission;
    }

    public int getPlayerNation() { return packet[PLAYER_NATION] & 0xff; }
    public int getNonPlayerNation() { return packet[NP_NATION] & 0xff; }
    public boolean isControlled() { return packet[IS_CONTROLLED] == 1; }
    public int getPermission() { return packet[PERMISSION] & 0xff; }
    public boolean hasPermission() { return packet[PERMISSION] == 1; }

    private static final int PLAYER_NATION = 3;
    private static final int NP_NATION = 4;
    private static final int IS_CONTROLLED = 5;
    private static final int PERMISSION = 6;
}
