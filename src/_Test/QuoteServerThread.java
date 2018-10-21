
/*
* (c) Copyright 2012 Deluxe Digital Studios. All Rights Reserved.
*/

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Date;

public class QuoteServerThread extends Thread {


    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected boolean moreQuotes = true;

    public QuoteServerThread() throws IOException {
        this("QuoteServerThread");
    }

    public QuoteServerThread(String name) throws IOException {
        super(name);
        socket = new DatagramSocket(4445);

        try {
            in = new BufferedReader(new FileReader("one-liners.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("Could not open quote file. Serving time instead.");
        }
    }

    public void run() {

        while (moreQuotes) {
            try {
                System.out.println("running...");
                byte[] buf = new byte[256];

                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                System.out.println("received");

                String dString = null;

                if (buf[0] == 'p') {
                    System.out.println("p found");
                    if (buf[1] == '1')
                        System.out.println("1 found");
                        if (buf[2] == '1')
                            dString = "Player 1 takes France";
                        if (buf[2] == '2')
                            dString = "Player 1 takes Great Britain";
                        if (buf[2] == '3')
                            dString = "Player 1 takes Prussia";
                        if (buf[2] == '4')
                            dString = "Player 1 takes Russia";
                        if (buf[2] == '5')
                            dString = "Player 1 takes Ottomans Empire";
                        if (buf[2] == '6')
                            dString = "Player 1 takes Austria/Hungary";
                        if (buf[2] == '7')
                            dString = "Player 1 takes Spain";

                } else {
                    if (in == null)
                        dString = new Date().toString();
                    else
                        dString = getNextQuote();
                }

                buf = dString.getBytes();

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
                moreQuotes = false;
            }
        }
        socket.close();
    }

    protected String getNextQuote() {
        String returnValue = null;
        try {
            if ((returnValue = in.readLine()) == null) {
                in.close();
                moreQuotes = false;
                returnValue = "No more quotes. Goodbye.";
            }
        } catch (IOException e) {
            returnValue = "IOException occurred in server.";
        }
        return returnValue;
    }
}
