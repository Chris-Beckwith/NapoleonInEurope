package game.packet.political;

import game.packet.GameOps;
import game.packet.GamePacket;

/**
 * RetaliationWarDecPacket.java  Date Created: Oct 31, 2013
 *
 * Purpose: To send/receive info to/from server about war declarations made when a nation declares war
 * on an uncontrolled nation and no nations were previously at war with declaringNation.
 *
 * Description:
 *
 * @author Chrisb
 */
public class RetaliationWarDecPacket extends GamePacket {

    public RetaliationWarDecPacket(byte[] packet) {
        this.packet = packet;
    }

    /**
     * This contructor is used to send/receive information about if a nation is declaring war on intialDeclarer.
     * @param gameId - id of the game this request is being made from.
     * @param intialDeclarer - the nation that declared war on an uncontrolled nation.
     * @param retaliationNation - the nation declaring war on intialDeclarer.
     * @param isDeclaringWar - boolean set to true if nation is declaring war on intialDeclarer.
     * @param papCost - the cost in PAP to declare war.
     * @param uncontrolledNation - the nation war was declared upon.
     */
    public RetaliationWarDecPacket(byte[] gameId, int intialDeclarer, int uncontrolledNation, boolean isDeclaringWar,
                                   int papCost, int retaliationNation) {
        packet = new byte[256];
        packet[0] = GameOps.RETALIATION_WAR.valueOf();
        setGameId(gameId);
        packet[DECLARER] = (byte) intialDeclarer;
        packet[UNCONTROLLED] = (byte) uncontrolledNation;
        packet[IS_DECLARING] = isDeclaringWar ? (byte)1 : (byte)0;
        packet[PAP_COST] = (byte) papCost;
        packet[RETALIATOR] = (byte) retaliationNation;
    }

    public RetaliationWarDecPacket(byte[] gameId, int initalDeclarer, int uncontrolledNation, int retaliationNation) {
        this(gameId, initalDeclarer, uncontrolledNation, false, 0, retaliationNation);
    }

    public int getInitDeclarer() { return packet[DECLARER] & 0xff; }
    public int getUncontrolledNation() { return packet[UNCONTROLLED] & 0xff; }
    public boolean isDeclaring() { return packet[IS_DECLARING] == 1; }
    public int getPapCost() { return packet[PAP_COST] & 0xff; }
    public int getRetaliator() { return packet[RETALIATOR] & 0xff; }

    private static final int DECLARER = 3;
    private static final int UNCONTROLLED = 4;
    private static final int IS_DECLARING = 5;
    private static final int PAP_COST = 6;
    private static final int RETALIATOR = 7;
}