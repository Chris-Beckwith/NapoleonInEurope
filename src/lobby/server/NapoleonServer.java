package lobby.server;

import util.ServerLogger;
import util.Messages;

import java.io.*;

/**
 * NapoleonServer.java  Date Created: Mar 30, 2012
 *
 * Purpose: To create and run the lobby.server thread.
 *
 * Description: This will run the lobby.server to listen for
 * requests made by clients.
 *
 * @author Chrisb
 */
public class NapoleonServer {
    public static void main(String[] args) throws IOException {
        Messages.load();
        ServerLogger.createServerLogFile();
        new NapoleonServerThread().start();
    }

}
