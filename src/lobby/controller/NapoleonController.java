package lobby.controller;
/*
* (c) Copyright 2012 Deluxe Digital Studios. All Rights Reserved.
*/

import lobby.gui.MenuResources;

import lobby.client.NapoleonClientThread;
import lobby.client.NapoleonMCClientThread;
import lobby.client.LoginResponseThread;
import util.Messages;
import util.InvalidLobbyIdException;
import util.Utilities;
import util.Logger;

import javax.swing.*;
import java.util.ArrayList;

import org.joda.time.DateTime;
import game.NiEGame;
import shared.controller.LobbyConstants;

public class NapoleonController {

    private static NapoleonClientThread napoleonCT;
    private static NapoleonMCClientThread napoleonMCCT;

    private static MenuResources menuResources;
    private static LobbyInstance lobby;
    private static User user;

    private static ArrayList<byte[]> lobbyIds;

    private DateTime lastRefresh = DateTime.now();

    /**
     * Contructor  --  Start listening
     */
    public NapoleonController() {
        napoleonCT = new NapoleonClientThread();
        napoleonCT.setDaemon(true);
        napoleonCT.start();
    }

    /*
     * Methods to send reqeusts to lobby.server via client thread.
     */

    public void login(String userName) {
        new LoginResponseThread(userName).attemptLogin();
//        napoleonCT.login(userName);
    }

    public void cleanUp() {
        if (lobby != null)
            napoleonCT.leaveLobby(lobby.getLobbyId(), user.getUserName());
        if (user != null)
            napoleonCT.logout(user.getUserName());

        try {
            if (napoleonCT != null) {
                napoleonCT.stopThread();
                napoleonCT.join(100);
            }
            if (napoleonMCCT != null) {
                napoleonMCCT.stopThread();
                napoleonMCCT.join(100);
            }
        } catch (InterruptedException e) {
            Logger.log("NapoleonController:cleanUp - " + e.getMessage());
        }
    }

    public void joinLobby(byte[] lobbyId) {
        napoleonCT.joinLobby(lobbyId, user);
    }

    public void joinLobby(int lobbyNumber) {
        if (lobbyNumber >= 0 && lobbyNumber < lobbyIds.size())
            napoleonCT.joinLobby(lobbyIds.get(lobbyNumber), user);
    }

    public void createLobby(String name) {
        napoleonCT.createLobby(name, user);
    }

    public void refreshLobbyList() {        
        if (lastRefresh.plusSeconds(5).compareTo(DateTime.now()) < 0) {
            lastRefresh = DateTime.now();
            napoleonCT.refreshLobbyList();
        }
    }

    public void setNation(int nation, String leaderName) {
        if (lobby != null && user != null && napoleonCT.isAlive()) {
            user.setNation(nation);
            napoleonCT.setNation(lobby.getLobbyId(), user.getPosition(), nation, leaderName);
        }
    }

    public void sendNationDescription(String leaderName) {
        if (user.getLeader().compareTo(leaderName) != 0) {
            user.setLeader(leaderName);
            napoleonCT.sendNationDescription(lobby.getLobbyId(), user.getPosition(), leaderName);
        }
    }

    public void makeReady(int userPosition, boolean isReady) {
        try {
            if (user.getPosition() == userPosition) {
                user.setReady(isReady);
                napoleonCT.sendMakeReady(lobby.getLobbyId(), userPosition, isReady);
            } else
                throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log("NapoleonController:makeGameReady - " + e.getMessage());
        }
    }

    public void sendChat(String message) {
        napoleonCT.sendChat(lobby.getLobbyId(), user.getUserName(), message);
    }

    public void setLobbyOption(int option, boolean isOn) {
        napoleonCT.setLobbyOption(lobby.getLobbyId(), option, isOn);
    }

    public boolean isLobbyReady() {
        return lobby.isLobbyReady();
    }

    public void startCountdown() {
        napoleonCT.startCountdown(lobby.getLobbyId());
    }

    public void stopCountdown() {
        napoleonCT.stopCountdown(lobby.getLobbyId());
    }

    /*
     * Methods to receive information from the lobby.server.
     */

    /**
     * Creates a new user and updates the display.
     * @param userName - user that is logging in.
     */
    public static void doLogin(String userName) {
        Messages.loadUser(userName);
        Messages.setClientProperty("userName",userName);

        //TODO UserId
        user = new User(userName);

        menuResources.login(userName);
    }

    public static void doNotLogin() {
        menuResources.setLoginErrorMsg(Messages.getString("errorMsg.noServerResponse"));
    }

    /**
     * Create a lobby with a lobby name and id.  Add this user to the lobby
     * as the creator of the lobby.
     * @param lobbyId - ID of the lobby user is creating.
     * @param lobbyName - Name of the lobby user is creating.
     */
    public static void doCreate(byte[] lobbyId, String lobbyName) {
        lobby = new LobbyInstance(lobbyId, lobbyName);
        lobby.addLobbyUser(user);
        boolean isHost = lobby.isHost(user);
        user.setHost(isHost);

        menuResources.joinLobby(lobby.getLobbyUsers(), user.getPosition(), isHost);
        menuResources.initializeLobby();
        napoleonMCCT = new NapoleonMCClientThread(lobby.getInetAddress());
        napoleonMCCT.setDaemon(true);
        napoleonMCCT.start();
    }

    /**
     * Adds everyone already in the lobby to the lobby, then adds the user to the lobby.
     * Update GUI, Start MCCT.
     * @param lobbyId - ID of the lobby user is joining.
     * @param users - list of users in the lobby.
     */
    public static void doJoin(byte[] lobbyId, ArrayList<User> users) {
        lobby = new LobbyInstance(lobbyId, users);
        lobby.addLobbyUser(user);
        boolean isHost = lobby.isHost(user);
        user.setHost(isHost);

        menuResources.joinLobby(lobby.getLobbyUsers(), user.getPosition(), isHost);
        napoleonMCCT = new NapoleonMCClientThread(lobby.getInetAddress());
        napoleonMCCT.setDaemon(true);
        napoleonMCCT.start();
    }

    /**
     * Tell the user that the lobby they attempted to join is full.
     */
    public static void doLobbyFull() {
        menuResources.fullLobby();
    }

    /**
     * Tell the user that the lobby they attempted to join has started.
     */
    public static void doLobbyStarted() {
        menuResources.startedLobby();
    }

    /**
     * Tell the user that the lobby they attempted to create already exists.
     */
    public static void doLobbyExists() {
        menuResources.lobbyExists();
    }

    /**
     * A user has changed their nation.  Update lobby instance and notify display code.
     * @param lobbyId - Id where nation change took place, used to confirm this is correct lobby.
     * @param position - position of user that made the change.
     * @param nation - nation the user is changing to.
     */
    public static void doSetNation(byte[] lobbyId, int position, int nation) {
        try {
            if ( Utilities.compareLobbyIds(lobby.getLobbyId(), lobbyId) ) {
                for (User u: lobby.getLobbyUsers()) {
                    if (u.getPosition() == position) {
                        u.setNation(nation);
                        menuResources.setNation(position, nation, u.getLeader());
                        if (u.equals(user)) {
                            user.setNation(nation);
                            user.setLeader(Messages.getString("nation." + nation + ".leader"));
                            napoleonCT.sendNationDescription(lobby.getLobbyId(), user.getPosition(), user.getLeader());
                        }
                    }
                }
            } else
                throw new InvalidLobbyIdException();
        } catch(InvalidLobbyIdException e) {
            System.err.println("Invalid LobbyId");
            e.printStackTrace();
            Logger.log("NapoleonController:doSetNation - " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log("NapoleonController:doSetNation - " + e.getMessage());
        }
    }

    /**
     * A user has changed their nation description (for now just leader).
     * Update lobby instance and notify display code.
     * @param lobbyId - lobbyId where description change took place, used to confirm this is correct lobby.
     * @param position - position of user that made the change.
     * @param leaderName - new name of the leader.
     */
    public static void receiveNationDescription(byte[] lobbyId, int position, String leaderName) {
        try {
            if ( Utilities.compareLobbyIds(lobby.getLobbyId(), lobbyId) ) {
                for (User u: lobby.getLobbyUsers()) {
                    if (u.getPosition() == position) {
                        u.setLeader(leaderName);
                        menuResources.receiveNationDescription(position, leaderName);
                    }
                }
            } else {
                throw new InvalidLobbyIdException();
            }
        } catch (InvalidLobbyIdException e) {
            System.err.println("Invalid lobbyId");
            e.printStackTrace();
            Logger.log("NapoleonController:receiveNationDescription - " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log("NapoleonController:receiveNationDescription - " + e.getMessage());
        }
    }

    /**
     * A user has changed their ready state, Update the lobby.
     * @param lobbyId - Id of lobby where change was made.
     * @param position - position of the user who made the change.
     * @param isReady - the ready state of the user.
     */
    public static void doMakeReady(byte[] lobbyId, int position, boolean isReady) {
        try {
            if ( Utilities.compareLobbyIds(lobby.getLobbyId(), lobbyId) ) {
                for (User u: lobby.getLobbyUsers()) {
                    if (u.getPosition() == position) {
                        u.setReady(isReady);
                        menuResources.makeReady(position, isReady);
                    }
                }
            } else {
                throw new InvalidLobbyIdException();
            }
        } catch (InvalidLobbyIdException e) {
            System.err.println("Invalid lobbyId");
            e.printStackTrace();
            Logger.log("NapoleonController:doMakeReady - " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log("NapoleonController:doMakeReady - " + e.getMessage());
        }
    }

    /**
     * Another user has joined the lobby this.user is currently in.
     * Add user to lobby instance and notify display code.
     * @param lobbyId - Id of lobby, used to confirm this is the correct lobby.
     * @param user - the user who has joined the lobby.
     */
    public static void addUserToLobby(byte[] lobbyId, User user) {
        try {
            if ( Utilities.compareLobbyIds(lobby.getLobbyId(), lobbyId) ) {
                user.setPosition(lobby.getFirstOpenPosition());
                lobby.addLobbyUser(user);
                menuResources.addUserToLobby(user);
            } else {
                throw new InvalidLobbyIdException();
            }
        } catch (InvalidLobbyIdException e) {
            System.err.println("Invalid LobbyId");
            e.printStackTrace();
            Logger.log("NapoleonController:addUserToLobby - " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log("NapoleonController:addUserToLobby - " + e.getMessage());
        }
    }

    /**
     * A user has left the lobby.  The user can be this.user, if so destroy lobby.
     * @param lobbyId - Id of lobby user is leaving, used to confirm this is the correct lobby.
     * @param userName - the user who has left the lobby.
     */
    public static void removeUserFromLobby(byte[] lobbyId, String userName) {
        try {
            if (lobby != null && lobby.hasUsers() ) {
                if ( Utilities.compareLobbyIds(lobby.getLobbyId(), lobbyId) ) {
                    for (User u: lobby.getLobbyUsers()) {
                        if (u.getUserName().compareTo(userName) == 0) {

                            lobby.removeLobbyUser(u);
                            boolean isHost = lobby.isHost(user);
                            user.setHost(isHost);
                            
                            if (u.getUserName().compareTo(user.getUserName()) != 0)
                                menuResources.removeUserFromLobby(u, isHost);

                            if (u.getUserName().compareTo(user.getUserName()) == 0)
                                lobby = null;

                            return;
                        }
                    }
                } else {
                    throw new InvalidLobbyIdException();
                }
            }
        } catch (InvalidLobbyIdException e) {
            System.err.println(e.getLocalizedMessage());
            e.printStackTrace();
            Logger.log("NapoleonController:removeUserFromLobby - " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log("NapoleonController:removeUserFromLobby - " + e.getMessage());
        }
    }

    /**
     * Creates a new arrayList to store the lobbyIds of the currently hosted lobbys.
     * Then sends the lobby names to the display code.
     * @param lobbyId - array of lobbyIds
     * @param lobbyNames - array of lobby names
     */
    public static void doRefreshLobbyList(byte[][] lobbyId, String[] lobbyNames) {
        lobbyIds = new ArrayList<byte[]>(lobbyId.length);

        for(byte[] b: lobbyId )
            lobbyIds.add(b);

        menuResources.refreshLobbyList(lobbyNames);
    }

    /**
     * Adds to the arrayList of lobbyIds to represent the addition of new lobbys.
     * Then sends the lobby name additions to the display code.
     * @param lobbyId - array of lobbyIds
     * @param lobbyNames - array of lobby names
     */
    public static void addToLobbyList(byte[][] lobbyId, String[] lobbyNames) {
        lobbyIds.ensureCapacity(lobbyIds.size() + lobbyId.length);

        for (byte[] b: lobbyId)
            lobbyIds.add(b);

        menuResources.addToLobbyList(lobbyNames);
    }

    /**
     * A chat lobby.packet has been received, confirm it is for this lobby, then
     * tell the display to add chat message to chat area.
     * @param lobbyId - id of lobby chat was sent from.
     * @param userName - name of user who sent the message.
     * @param message - chat message being sent.
     */
    public static void receiveChat(byte[] lobbyId, String userName, String message) {
        try {
            if ( Utilities.compareLobbyIds(lobby.getLobbyId(), lobbyId) ) {
                menuResources.receiveChat(userName, message);
            } else {
                throw new InvalidLobbyIdException();
            }
        } catch (InvalidLobbyIdException e) {
            System.err.println(e.getLocalizedMessage());
            e.printStackTrace();
            Logger.log("NapoleonController:receiveChat - " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log("NapoleonController:receiveChat - " + e.getMessage());
        }
    }

    /**
     * When successfully joining a lobby, set the game options for the lobby.
     * @param lobbyId - id of lobby joined.
     * @param readyStates - ready states for the users.
     * @param options - game option settings.
     * @param msd - mode/scenario/duration settings.
     */
    public static void doLobbySettings(byte[] lobbyId, boolean[] readyStates, boolean[] options, int[] msd) {
        try {
            if ( Utilities.compareLobbyIds(lobby.getLobbyId(), lobbyId) ) {
                for (int i = 0; i < readyStates.length; i++)
                    menuResources.makeReady(i, readyStates[i]);
                for (int i = 0; i < options.length; i++)
                    menuResources.changeGameOption(i + LobbyConstants.NUM_OF_OPTIONS, options[i]);

                menuResources.changeGameOption(msd[0], false);
                menuResources.changeGameOption(msd[1], false);
                menuResources.changeGameOption(msd[2], false);
            } else {
                throw new InvalidLobbyIdException();
            }
        } catch (InvalidLobbyIdException e) {
            System.err.println(e.getLocalizedMessage());
            e.printStackTrace();
            Logger.log("NapoleonController:doLobbySettings - " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log("NapoleonController:doLobbySettings - " + e.getMessage());
        }

    }

    /**
     * A game option has been changed, update the GUI and the lobbyInstance.
     * @param lobbyId - id of the lobby where change was made.
     * @param option - option which was changed.
     * @param isOn - current state of option.
     */
    public static void changeGameOption(byte[] lobbyId, int option, boolean isOn) {
        try {
            if (lobby != null) {
                if (Utilities.compareLobbyIds(lobby.getLobbyId(), lobbyId)) {
                    lobby.setGameOption(option, isOn);
                    menuResources.changeGameOption(option, isOn);
                } else {
                    throw new InvalidLobbyIdException();
                }
            }
        } catch (InvalidLobbyIdException e) {
            System.err.print(e.getLocalizedMessage());
            e.printStackTrace();
            Logger.log("NapoleonController:changeGameOption - " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log("NapoleonController:changeGameOption - " + e.getMessage());
        }
    }

    /**
     * A start game request was made but not everyone was ready.
     * Would you like to start even though everyone is not ready?
     * @param lobbyId - id of lobby to confirm this is correct lobby.
     */
    public static void lobbyNotReady(byte[] lobbyId) {
        try {
            if ( Utilities.compareLobbyIds(lobby.getLobbyId(), lobbyId)) {
                menuResources.lobbyNotReady();
            } else {
                throw new InvalidLobbyIdException();
            }
        } catch (InvalidLobbyIdException e) {
            System.err.print(e.getLocalizedMessage());
            e.printStackTrace();
            Logger.log("NapoleonController:lobbyNotReady - " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log("NapoleonController:lobbyNotReady - " + e.getMessage());
        }
    }

    /**
     * A countdown message has been received, confirm it is for this lobby, then
     * tell the display to show the time remaining.
     * @param lobbyId - id of lobby countdown is taking place.
     * @param secondsRemaining - time remaining in the countdown.
     */
    public static void doCountdown(byte[] lobbyId, int secondsRemaining) {
        if (lobby != null) {
            try {
                if ( Utilities.compareLobbyIds(lobby.getLobbyId(), lobbyId) ) {
                    if (secondsRemaining == LobbyConstants.COUNTDOWN_SECONDS)
                        menuResources.countdownStarted();
                    if (secondsRemaining == LobbyConstants.COUNTDOWN_DONE)
                        menuResources.countdownComplete(lobbyId);
                    menuResources.receiveCountdownMsg(secondsRemaining);
                } else {
                    throw new InvalidLobbyIdException();
                }
            } catch (InvalidLobbyIdException e) {
                System.err.println(e.getLocalizedMessage());
                e.printStackTrace();
                Logger.log("NapoleonController:doCountdown - " + e.getLocalizedMessage());
            } catch (Exception e) {
                e.printStackTrace();
                Logger.log("NapoleonController:doCountdown - " + e.getMessage());
            }
        }
    }

    /**
     * The final countdown message has been received.  Countdown has successfully completed.
     * Proceed to destroy all client threads and all client GUIs.  Save the game setting information.
     * Start new client game threads and new client game GUIs.
     *
     * @param lobbyId - Id of the lobby that completed countdown.
     */
    public void countdownComplete(byte[] lobbyId) {
        napoleonCT.doneCountdown(lobbyId);
        try {
            if (napoleonCT != null) {
                napoleonCT.stopThread();
                napoleonCT.join(100);
            }
            if (napoleonMCCT != null) {
                napoleonMCCT.stopThread();
                napoleonMCCT.join(100);
            }
        } catch (InterruptedException e) {
            Logger.log("NapoleonController:cleanUp - " + e.getMessage());
        }

        //todo Save game info and shit
        (new NiEGame(user, lobby.getLobbyId())).start();
    }

    /*
     * Miscellaneous Methods
     */

    public void showMainMenu() {
        menuResources.showMainMenu();
    }

    public void showCreateLobby() {
        menuResources.showCreateLobby();
    }

    public void showJoinLobby() {
        napoleonCT.refreshLobbyList();
        menuResources.showJoinLobby();
    }

    public void refreshDisplay() {
        menuResources.refresh();
    }

    public void setMenuResource(MenuResources menuResources) {
        NapoleonController.menuResources = menuResources;
    }

    public void showSubPanel(int panel, int country) {
        menuResources.showSubPanel(panel, country);
    }

    public void hideSubPanel(int panel) {
        menuResources.hideSubPanel(panel);
    }

    public static String getUserName() {
        return user.getUserName();
    }

    public String getLobbyName() {
        return lobby.getLobbyName();
    }

    public void showParentMenu(JComponent menu) {
        menuResources.showParentMenu(menu);
    }
}
