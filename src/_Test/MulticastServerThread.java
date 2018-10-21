
import java.io.IOException;
import java.util.Date;
import java.net.InetAddress;
import java.net.DatagramPacket;

/*
* (c) Copyright 2012 Deluxe Digital Studios. All Rights Reserved.
*/
public class MulticastServerThread extends QuoteServerThread {

    private long TEN_SECONDS = 10000;

    public MulticastServerThread() throws IOException {
        super("MulticastServerThread");
    }

    public void run() {
        while (moreQuotes) {
            try {
                byte[] buf = new byte[256];

                String dString = null;
                if (in == null)
                    dString = new Date().toString();
                else
                    dString = getNextQuote();
                buf = dString.getBytes();
                //Multicast Address - (224.0.0.0 - 239.255.255.255)
                //Non-Reserved Multicast Address (224.0.1.0 - 238.255.255.255)
                InetAddress group = InetAddress.getByName("224.1.1.102");
                DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 2222);
                socket.send(packet);

                try {
                    sleep((long)(Math.random() * TEN_SECONDS));
                } catch (InterruptedException e) {}

            } catch (IOException e) {
                e.printStackTrace();
                moreQuotes = false;

            }
        }

        socket.close();
    }
}
