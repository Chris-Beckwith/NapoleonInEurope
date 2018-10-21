package game.packet.political;

import game.packet.GamePacket;
import game.packet.GameOps;

import java.util.ArrayList;

import lobby.controller.User;

/**
 * WarDeclarationPacket.java  Date Created: Sep 30, 2013
 *
 * Purpose: To send war declaration information to and from the server.
 *
 * Description: The player chooses if he wants to control the nations, unless he is the last player in the group, then he must control nation.
 *
 * Information to send to server:
 * 1) gameId, declaring and enemy Nations, papCost.
 *
 * 2) a) gameId, declaring and enemy Nations, papCost, nationToControl
 *    b) gameId, declaring and enemy Nations, papCost, listOfNations, listOfSizes
 *
 * 3) gameId, declaring and enemy Nations, papCost, isAtWar (false)
 *
 * @author Chrisb
 */
public class WarDeclarationPacket extends GamePacket {
    public WarDeclarationPacket(byte[] packet) {
        this.packet = packet;
    }

    /**
     * This constructor is used when enemyNation is controlled.
     * @param gameId - id of the game request is being made from.
     * @param declaringNation - nation declaring war.
     * @param enemyNation - nation war is being declared on.
     * @param papCost - cost of the war declaration.
     */
    public WarDeclarationPacket(byte[] gameId, int declaringNation, int enemyNation, int papCost) {
        packet = new byte[256];
        packet[0] = GameOps.DECLARE_WAR.valueOf();
        setGameId(gameId);
        packet[DECLARER] = (byte)declaringNation;
        packet[ENEMY] = (byte)enemyNation;
        packet[PAP_COST] = (byte)papCost;
        packet[TYPE_INDEX] = (byte) CONTROLLED;
        packet[NUM_OF_USERS] = (byte)0;
    }

    /**
     * This constructor is used when enemyNation is uncontrolled and declaringNation is already at
     * war with other nation(s).  If only one nation or if last nation in the group, nationToControl
     * must take control of the nation.  Otherwise can opt not to control the uncontrolled nation.
     * @param gameId - id of the game this request is being made from.
     * @param declaringNation - the nation declaring war.
     * @param enemyNation - the nation war is being declared on.
     * @param papCost - cost of the war declaration.
     * @param nation - the nation that must gain control of enemyNation.
     */
    public WarDeclarationPacket(byte[] gameId, int declaringNation, int enemyNation, int papCost, int nation) {
        this(gameId, declaringNation, enemyNation, papCost);
        packet[TYPE_INDEX] = (byte) UNCONTROLLED_ATWAR;
        packet[NUM_OF_USERS] = (byte)1;
        packet[NATION_INDEX] = (byte)nation;
    }

    /**
     * This constructor is used when enemyNation is uncontrolled and declaringNation is already at
     * war with other nation(s).  If only one nation or if last nation in the group, nationToControl
     * must take control of the nation.  Otherwise can opt not to control the uncontrolled nation.
     * @param gameId - id of the game this request is being made from.
     * @param declaringNation - the nation declaring war.
     * @param enemyNation - the nation war is being declared on.
     * @param papCost - cost of the war declaration.
     * @param userNations - list of users that can gain control of enemyNation, represented by the user's main nation.
     */
    public WarDeclarationPacket(byte[] gameId, int declaringNation, int enemyNation, int papCost, ArrayList<Integer> userNations) {
        this(gameId, declaringNation, enemyNation, papCost);
        packet[TYPE_INDEX] = (byte) UNCONTROLLED_ATWAR;
        packet[NUM_OF_USERS] = (byte) userNations.size();

        for (int i = 0; i < userNations.size(); i++ )
            packet[NATION_INDEX + i] = (byte) userNations.get(i).intValue();
    }

    /**
     * This constructor is used when enemyNation is uncontrolled and declaringNation is not already
     * at war with any other nations.  Then all other eligible nations have a chance to delcare war
     * on declaringNation.  After everyone has a chance then choose a nation to control enemyNation.
     * @param gameId - id of the game this request is being made from.
     * @param declaringNation - the nation declaring war.
     * @param enemyNation - the nation war is being declared on.
     * @param papCost - cost of the war declaration.
     * @param isAtWar - dummy variable to get UNCONTROLLED_NEUTRAL type.
     */
    public WarDeclarationPacket(byte[] gameId, int declaringNation, int enemyNation, int papCost, boolean isAtWar) {
        this(gameId, declaringNation, enemyNation, papCost);
        packet[TYPE_INDEX] = (byte)UNCONTROLLED_NEUTRAL;
    }

    public int getDeclaringNation() { return packet[DECLARER] & 0xff; }
    public int getEnemyNation() { return packet[ENEMY] & 0xff; }
    public int getPapCost() { return packet[PAP_COST] & 0xff; }
    public int getType() { return packet[TYPE_INDEX] & 0xff; }
    public int getNumOfUsers() { return packet[NUM_OF_USERS] & 0xff; }
    public int getNationToControl() { return packet[NATION_INDEX] & 0xff; }
    public ArrayList<Integer> getUserNations() {
        ArrayList<Integer> nations = new ArrayList<Integer>();

        for (int i = NATION_INDEX; i < NATION_INDEX + getNumOfUsers(); i++)
            nations.add(packet[i] & 0xff);

        return nations;
    }

    private static final int DECLARER = 3;
    private static final int ENEMY = 4;
    private static final int PAP_COST = 5;
    private static final int TYPE_INDEX = 6;
    private static final int NUM_OF_USERS = 7;
    private static final int NATION_INDEX = 8;

    public static final int CONTROLLED = 0;
    public static final int UNCONTROLLED_ATWAR = 1;
    public static final int UNCONTROLLED_NEUTRAL = 2;
}