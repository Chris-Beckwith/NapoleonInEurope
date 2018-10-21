package game.packet.political;

import game.packet.GamePacket;
import game.packet.GameOps;

import java.util.ArrayList;

/**
 * MultiNationPacket.java  Date Created: Oct 28, 2013
 *
 * Purpose: To send eligible nation information to and from the server.
 *
 * Description: The player chooses if he wants to control the nation, unless he is the last player in the group, then he must control nation.
 *
 * @author Chrisb
 */
public class MultiNationPacket extends GamePacket {
    public MultiNationPacket(byte[] packet) {
        this.packet = packet;
    }

    /**
     * This contructor is used to send the list of eligible nations to the server.
     * @param gameId - id of the game this request is being made from.
     * @param declaringNation - the nation declaring war.
     * @param uncontrolledNation - the nation war is being declared on.
     * @param userNations - list of users that can gain control of uncontrolledNation, represented by the user's main nation.
     */
    public MultiNationPacket(byte[] gameId, int declaringNation, int uncontrolledNation, ArrayList<Integer> userNations) {
        packet = new byte[256];
        packet[0] = GameOps.MULTI_NATION.valueOf();
        setGameId(gameId);
        packet[DECLARER] = (byte)declaringNation;
        packet[ENEMY] = (byte) uncontrolledNation;
        packet[NUM_OF_USERS] = (byte) userNations.size();

        for (int i = 0; i < userNations.size(); i++ )
            packet[NATION_INDEX + i] = (byte) userNations.get(i).intValue();
    }

    public int getDeclaringNation() { return packet[DECLARER] & 0xff; }
    public int getUncontrolledNation() { return packet[ENEMY] & 0xff; }
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
    private static final int NUM_OF_USERS = 6;
    private static final int NATION_INDEX = 7;
}