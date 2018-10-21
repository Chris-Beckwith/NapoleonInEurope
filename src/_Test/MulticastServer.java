

import java.io.IOException;

/*
* (c) Copyright 2012 Deluxe Digital Studios. All Rights Reserved.
*/
public class MulticastServer {
    public static void main(String[] args) throws IOException {
        new MulticastServerThread().start();
    }
}
