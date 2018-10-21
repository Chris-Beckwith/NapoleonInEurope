
import java.io.IOException;
import java.net.MulticastSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;

/*
* (c) Copyright 2012 Deluxe Digital Studios. All Rights Reserved.
*/
public class MulticastClient {

    public static void main(String[] args) throws IOException {

        MulticastSocket socket = new MulticastSocket(2222);
        //Multicast Address - (224.0.0.0 - 239.255.255.255)
        //Non-Reserved Multicast Address (224.0.1.0 - 238.255.255.255)        
        InetAddress address = InetAddress.getByName("224.1.1.102");
        socket.joinGroup(address);

        DatagramPacket packet;

        for (int i = 0; i < 5; i++) {
            byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Quote of the Moment: " + received);
        }

        socket.leaveGroup(address);
        socket.close();
    }
}
