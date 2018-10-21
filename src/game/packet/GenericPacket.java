package game.packet;

import lobby.packet.Packet;

/**
 * GenericPacket.java  Date Created: Nov 1, 2012
 *
 * Purpose:
 *
 * Description:
 *
 * @author Chrisb
 */
public class GenericPacket extends GamePacket {

    public GenericPacket(byte[] packet) {
        this.packet = packet;
     }

//    public GenericPacket() {
//        packet[0] = GameOps.FAILURE.valueOf();
//    }

     public GenericPacket(byte opCode) {
         packet = new byte[1];
         packet[0] = opCode;
     }

     public GenericPacket(byte opCode, byte[] gameId, int nation) {
         packet = new byte[4];
         packet[0] = opCode;
         packet[1] = gameId[0];
         packet[2] = gameId[1];
         packet[3] = (byte)nation;
     }

    public int getNation() {
        return packet[3] & 0xff;
    }
}