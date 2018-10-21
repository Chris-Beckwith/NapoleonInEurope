package game.server;

import game.util.GameServerLogger;
import game.util.GameMsg;

import java.io.IOException;

/**
 * GameServer.java  Date Created: Oct 30, 2012
 *
 * Purpose: To create and run the lobby.server thread.
 *
 * Description: This will run the lobby.server to listen for
 * requests made by clients.
 *
 * @author Chrisb
 */
public class GameServer {
    public static void main(String[] args) throws IOException {
        GameMsg.load();
        GameServerLogger.createServerLogFile();
        new GameServerThread().start();
    }
}