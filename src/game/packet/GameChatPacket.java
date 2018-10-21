package game.packet;

/**
 * GameChatPacket.java  Date Created: Nov 2, 2012
 *
 * Purpose: To deliver a chat message to the correct nation.
 *
 * Description: This packet will contain one message sent by a user
 * to a certain nation.
 *
 *   __ ____   __   __   __  ______________________________
 *   1   2     1    1    1               250
 * GameOp  FromNation  MsgLength     ChatMessage
 *     GameId    ToNation
 *
 * @author Chrisb
 */
public class GameChatPacket extends GamePacket {

    public GameChatPacket(byte[] packet) {
        this.packet = packet;
    }

    public GameChatPacket(byte[] gameId, int fromNation, int toNation, String chatMessage) {
        packet = new byte[256];
        packet[0] = GameOps.CHAT.valueOf();
        packet[1] = gameId[0];
        packet[2] = gameId[1];
        packet[FROM_INDEX] = (byte)fromNation;
        packet[TO_INDEX] = (byte)toNation;
        packet[LENGTH_INDEX] = (byte)chatMessage.length();
        int index = MSG_INDEX;
        for (char c: chatMessage.toCharArray())
            if (index < 256)
                packet[index++] = (byte)c;
    }

    public String getChatMsg() {
        int length = packet[LENGTH_INDEX] & 0xff;
        byte[] message = new byte[length];

        for (int i = 0; i < length; i++)
            message[i] = packet[MSG_INDEX + i];

        return new String(message);
    }

    public int getFromNation() { return packet[FROM_INDEX] & 0xff; }
    public int getToNation() { return packet[TO_INDEX] & 0xff; }

    private final static int FROM_INDEX = 3;
    private final static int TO_INDEX = 4;
    private final static int LENGTH_INDEX = 5;
    private final static int MSG_INDEX = 6;
}