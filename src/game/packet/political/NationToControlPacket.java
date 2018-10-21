package game.packet.political;

import game.packet.GamePacket;
import game.packet.GameOps;

/**
 * NationToControlPacket.java  Date Created: Oct 28, 2013
 *
 * Purpose: To send eligible nation information to and from the server.
 *
 * Description: This user is last or only eligible user so he must control nation.
 *
 * @author Chrisb
 */
public class NationToControlPacket extends GamePacket {
    
    public NationToControlPacket(byte[] packet) {
        this.packet = packet;
    }

    /**
     * This constructor is used to send the eligible nation to/from the server.
     * @param gameId - id of the game this request is being made from.
     * @param declaringNation - the nation declaring war.
     * @param uncontrolledNation - the nation war is being declared on.
     * @param userToControl - the user nation that must control the uncontrolled nation.
     */
    public NationToControlPacket(byte[] gameId, int declaringNation, int uncontrolledNation, int userToControl) {
        packet = new byte[256];
        packet[0] = GameOps.NATION_TO_CONTROL.valueOf();
        setGameId(gameId);
        packet[DECLARER] = (byte)declaringNation;
        packet[ENEMY] = (byte) uncontrolledNation;
        packet[USER_TO_CONTROL] = (byte) userToControl;
    }

    public NationToControlPacket(byte[] gameId, int declaringNation, int uncontrolledNation, int userToControl,
                                 boolean isNotification) {
        this(gameId, declaringNation, uncontrolledNation, userToControl);
        packet[IS_NOTIFICATION] = isNotification ? (byte)1 : (byte)0;
    }

    public int getDeclaringNation() { return packet[DECLARER] & 0xff; }
    public int getUncontrolledNation() { return packet[ENEMY] & 0xff; }
    public int getUserToControl() { return packet[USER_TO_CONTROL] & 0xff; }
    public boolean isNotification() { return packet[IS_NOTIFICATION] == 1; }

    private static final int DECLARER = 3;
    private static final int ENEMY = 4;
    private static final int USER_TO_CONTROL = 5;
    private static final int IS_NOTIFICATION = 6;
}