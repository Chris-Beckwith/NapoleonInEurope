package util;

/**
 * InvalidLobbyIdException.java  Date Created: Apr 18, 2012
 *
 * Purpose: A custom exception to be thrown when lobbyIds do not match.
 *
 * Description: When a user in a lobby receives a lobby.packet from the lobby.server,
 * that lobby.packet will have a lobbyId associated with it.  In the event that
 * the lobbyId in the lobby.packet does not match the lobbyId the user is currently
 * in, this exception should be thrown.
 *
 * @author Chrisb
 */
public class InvalidLobbyIdException extends Exception {
    public InvalidLobbyIdException() {
        super();
    }

    public String getLocalizedMessage() {
        return "InvalidLobbyId Exception";
    }
}
