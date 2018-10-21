package lobby.gui;

import lobby.controller.User;

import javax.swing.*;
import java.util.ArrayList;

/**
 * MenuResources.java  Date Created: Mar 22, 2012
 *
 * Purpose: Interface for localized menu controllers to implement.
 *
 * Description:
 *
 * @author Chrisb
 */
public interface MenuResources {
        
    public void setInitialContentPane();
    public void showSubPanel(int menu, int country);
    public void hideSubPanel(int menu);
    public void login(String userName);
    public void setLoginErrorMsg(String msg);
    public void showMainMenu();
    public void showCreateLobby();
    public void showJoinLobby();
    public void showParentMenu(JComponent menu);
    public void refreshLobbyList(String[] lobbyNames);
    public void addToLobbyList(String[] lobbyNames);
    public void initializeLobby();
    public void joinLobby(ArrayList<User> users, int userPosition, boolean isHost);
    public void fullLobby();
    public void startedLobby();
    public void lobbyExists();
    public void addUserToLobby(User user);
    public void removeUserFromLobby(User user, boolean isHost);
    public void changeGameOption(int option, boolean isOn);
    public void setNation(int position, int nation, String leaderName);
    public void receiveNationDescription(int position, String leaderName);
    public void makeReady(int position, boolean isReady);
    public void receiveChat(String userName, String message);
    public void receiveCountdownMsg(int secondsRemaining);
    public void countdownStarted();
    public void countdownComplete(byte[] lobbyId);
    public void lobbyNotReady();
    public void refresh();

    public static final int LOGIN_MENU = 0;
    public static final int MAIN_MENU = 1;
    public static final int CREATELOBBY = 2;
    public static final int JOINLOBBY = 3;
    public static final int GAMELOBBY = 4;
    public static final int NATIONDESC = 5;
}
