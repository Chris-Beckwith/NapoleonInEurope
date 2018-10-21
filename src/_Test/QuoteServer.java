
/*
* (c) Copyright 2012 Deluxe Digital Studios. All Rights Reserved.
*/

import java.io.IOException;

public class QuoteServer {
    public static void main(String[] args) throws IOException {
        new QuoteServerThread().start();
    }
}
