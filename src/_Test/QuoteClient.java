
/*
* (c) Copyright 2012 Deluxe Digital Studios. All Rights Reserved.
*/

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;

public class QuoteClient {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java QuoteClient <hostname>");
            return;
        }

        DatagramSocket socket = new DatagramSocket();

        byte[] buf = new byte[256];
        buf[0] = 'p';
        buf[1] = '1';
        buf[2] = '2';

        InetAddress address = InetAddress.getByName(args[0]);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);

        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Quote of the Moment: " + received);

        socket.close();
    }
}
