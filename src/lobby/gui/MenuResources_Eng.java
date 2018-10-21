package lobby.gui;

import lobby.controller.NapoleonController;
import lobby.controller.User;

import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import util.Messages;

/**
 * MenuResources_Eng.java  Date Created: Mar 22, 2012
 *
 * Purpose: To control and display the english menus.
 *
 * Description: This class displays the correct menu when actions are taken.
 *
 * @author Chrisb
 */
public class MenuResources_Eng implements MenuResources {

    private LoginMenu loginMenu;
    private MainMenu mainMenu;
    private CreateLobby createLobby;
    private JoinLobby joinLobby;
    private GameLobby gameLobby;
    private Lobby lobby;
    private NationDescription nationDesc;
    private JFrame frame;
    private ReturnAction returnAction;

    private NapoleonController controller;

    /**
     * Constructor to initialize all menus and any other objects needed to handle the meus.
     * @param frame - where all windows are displayed.
     * @param controller - all menus see this object to allow calls to be made to it from any menu.
     */
    public MenuResources_Eng(JFrame frame, NapoleonController controller) {
        this.frame = frame;
        this.controller = controller;

        loginMenu = new LoginMenu(controller);
        mainMenu = new MainMenu(controller, loginMenu);
        createLobby = new CreateLobby(controller, mainMenu);
        joinLobby = new JoinLobby(controller, mainMenu);
        lobby = new Lobby(controller);
        nationDesc = new NationDescription(controller);
        gameLobby = new GameLobby(controller, lobby, nationDesc, mainMenu);

        returnAction = new ReturnAction();

        createLobby.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke("ESCAPE"), "returnAction");
        createLobby.getActionMap().put("returnAction", returnAction);

        joinLobby.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke("ESCAPE"), "returnAction");
        joinLobby.getActionMap().put("returnAction", returnAction);
    }

    public void login(String userName) {
        if (loginMenu.isDefaultOn())
            Messages.setClientProperty("userName.defaultUser",userName);
        setContentPane(MAIN_MENU);
    }

    public void setLoginErrorMsg(String msg) {
        loginMenu.setErrorMsg(msg);
    }

    public void showMainMenu() {
        setContentPane(MAIN_MENU);
    }

    public void showCreateLobby() {
        setContentPane(CREATELOBBY);
    }

    public void showJoinLobby() {
        setContentPane(JOINLOBBY);
    }

    public void showParentMenu(JComponent menu) {
        frame.setContentPane(menu);
        refresh();
    }

    public void initializeLobby() {
        lobby.initialize();
    }

    public void joinLobby(ArrayList<User> users, int userPosition, boolean isHost) {
        setupLobby(users, userPosition, isHost);
        joinLobby.hideErrorMsg();
        setContentPane(GAMELOBBY);
    }

    public void fullLobby() {
        joinLobby.displayFullLobbyMsg();
        refresh();
    }

    public void startedLobby() {
        joinLobby.displayStartedLobbyMsg();
        refresh();
    }

    public void lobbyExists() {
        System.out.println("MenuResource:lobbyExists");
        createLobby.lobbyExists();
        refresh();
    }

    public void refreshLobbyList(String[] lobbyNames) {
        joinLobby.hideErrorMsg();
        joinLobby.refreshLobbyList(lobbyNames);
        refresh();
    }

    public void addToLobbyList(String[] lobbyNames) {
        joinLobby.addToLobbyList(lobbyNames);
    }

    /**
     * Set the first menu that shows.
     */
    public void setInitialContentPane() {
        setContentPane(LOGIN_MENU);
    }

    /**
     * Used to switch from one menu to another.
     * @param menu - The menu that you want to switch too.
     */
    private void setContentPane(int menu) {
        switch (menu) {
            case LOGIN_MENU: frame.setContentPane(loginMenu); break;
            case MAIN_MENU: frame.setContentPane(mainMenu); break;
            case CREATELOBBY: frame.setContentPane(createLobby); createLobby.requestFocus(); break;
            case JOINLOBBY: frame.setContentPane(joinLobby); joinLobby.requestFocus(); break;
            case GAMELOBBY: frame.setContentPane(gameLobby); break;
        }
        refresh();
    }

    public void addUserToLobby(User user) {
        lobby.setLabels(user.getUserName(), user.getPosition(), user.getNation(), user.getLeader(), true);
        lobby.joinMessage(user.getUserName());
        refresh();
    }

    public void removeUserFromLobby(User leavingUser, boolean isHost) {
        int position = leavingUser.getPosition();
        lobby.setLabels("Player " + (position + 1), position, 0, Messages.getString("nation.0.leader"), false);
        lobby.leaveMessage(leavingUser.getUserName());
        lobby.setHost(isHost);
        refresh();
    }

    public void changeGameOption(int option, boolean isOn) {
         lobby.setGameOption(option, isOn);
    }

    public void lobbyNotReady() {
        lobby.forceStartDialog();
    }

    private void setupLobby(ArrayList<User> users, int userPosition, boolean isHost) {
        for (User u: users)
            lobby.setLabels(u.getUserName(), u.getPosition(), u.getNation(), u.getLeader(), true);
        lobby.setUserPosition(userPosition);
        lobby.setHost(isHost);
    }

    public void setNation(int position, int nation, String leaderName) {
        lobby.setNation(position, nation, leaderName);
    }

    public void receiveNationDescription(int position, String leaderName) {
        lobby.setLeader(position, leaderName);
    }

    public void makeReady(int position, boolean isReady) {
        lobby.makeReady(position, isReady);
    }

    public void receiveChat(String userName, String message) {
        lobby.chatMessage(userName, message);
    }

    public void countdownStarted() {
        lobby.disableMenus();
    }

    public void receiveCountdownMsg(int secondsRemaining) {
        lobby.countdownMsg(secondsRemaining);
    }

    public void countdownComplete(byte[] lobbyId) {
        frame.dispose();
        controller.countdownComplete(lobbyId);
    }

    public void showSubPanel(int panel, int country) {
        switch (panel) {
            case NATIONDESC: nationDesc.showMe(country); lobby.toggleEnabled(); refresh(); break;
        }
    }

    public void hideSubPanel(int panel) {
        switch (panel) {
            case NATIONDESC: nationDesc.hideMe(); lobby.toggleEnabled(); refresh(); break;
        }
    }

    public class ReturnAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            if (joinLobby.getClass().equals(e.getSource().getClass())) {
                showParentMenu(joinLobby.getParentMenu());
                joinLobby.hideErrorMsg();
            }
            if (createLobby.getClass().equals(e.getSource().getClass()))
                showParentMenu(createLobby.getParentMenu());
        }
    }

    /**
     * Refresh the display of the frame.
     */
    public void refresh() {
        frame.pack();
        frame.validate();
        frame.repaint();
    }
}
