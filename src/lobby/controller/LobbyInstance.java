package lobby.controller;

import util.Logger;
import util.ServerLogger;
import util.Utilities;

import java.util.ArrayList;
import java.util.Random;
import java.net.InetAddress;
import java.io.IOException;

import lobby.server.CountdownThread;
import shared.controller.LobbyConstants;

/**
 * LobbyInstance.java  Date Created: Apr 03, 2012
 *
 * Purpose: Contains all of the information related to a lobby.
 *
 * Description:  All the information related to a lobby is stored
 * here, including the users that are currently in the lobby.
 *
 * @author Chrisb
 */
public class LobbyInstance {
    private String lobbyName;
    private byte[] lobbyId;
    private ArrayList<User> users;
    private boolean[] options;
    private int gameMode, gameScene, gameDuration;
    private boolean isStarting;
    private int mcPort;
    private CountdownThread cdt;

    /**
     * Contructor to make lobby with initial set of users.
     *
     * @param lobbyId - unique identifier as well as the IP address to connect to.
     * @param users - list of users in the lobby.
     */
    public LobbyInstance(byte[] lobbyId, ArrayList<User> users) {
        this.users = new ArrayList<User>(MAX_USERS);
        this.users.addAll(users);
        this.lobbyId = lobbyId;
        options = new boolean[LobbyConstants.NUM_OF_OPTIONS];
        gameMode = gameScene = gameDuration = -1;
        isStarting = false;
    }

    /**
     * Constructor to create a game lobby.
     *
     * Initialize user list, add creator.
     *
     * @param lobbyName - Name to be displayed in game lists.
     * @param lobbyCreator - User who created the lobby.
     */
    @Deprecated
    public LobbyInstance(String lobbyName, User lobbyCreator) {
        this.lobbyName = lobbyName;
        users = new ArrayList<User>(MAX_USERS);
        users.add(lobbyCreator);
        options = new boolean[LobbyConstants.NUM_OF_OPTIONS];
        gameMode = gameScene = gameDuration = -1;
        isStarting = false;
    }

    /**
     * Contructor
     *
     * @param lobbyId - unique identifier as well as the IP address to connect to.
     * @param lobbyName - Name of the lobby, also must be unique
     */
    public LobbyInstance(byte[] lobbyId, String lobbyName) {
        users = new ArrayList<User>(MAX_USERS);
        this.lobbyId = lobbyId;
        this.lobbyName = lobbyName;
        options = new boolean[LobbyConstants.NUM_OF_OPTIONS];
        gameMode = gameScene = gameDuration = -1;
        isStarting = false;
    }

    /**
     * Contructor
     *
     * @param lobbyId - unique identifier as well as the IP address to connect to.
     * @param lobbyName - Name of the lobby, also must be unique.
     * @param mcPort - the port used by the lobby.server to send messages to all users in this lobby.
     */
    public LobbyInstance(byte[] lobbyId, String lobbyName, int mcPort) {
        users = new ArrayList<User>(MAX_USERS);
        this.lobbyId = lobbyId;
        this.lobbyName = lobbyName;
        options = new boolean[LobbyConstants.NUM_OF_OPTIONS];
        gameMode = gameScene = gameDuration = -1;
        this.mcPort = mcPort;
        isStarting = false;
    }

    /**
     * Contructor
     * 
     * @param users - list of users in the lobby
     */
    public LobbyInstance(ArrayList<User> users) {
        this.users = new ArrayList<User>(users);
        options = new boolean[LobbyConstants.NUM_OF_OPTIONS];
        gameMode = gameScene = gameDuration = -1;
        isStarting = false;
    }

    /**
     * Sets the 'nation' that will be controlled by a 'player'.
     *
     * First checks if the 'nation' is already taken by another player.  If not assign to 'player'.
     *
     * @param player - user wanting to control a nation.
     * @param nation - nation user wants to control.
     *
     * @return boolean - true if successful, false if nation is taken.
     */
    public boolean setNation(int player, int nation) {
        if (isNationOpen(nation)) {
            try {
                users.get(player).setNation(nation);
                return true;
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Invalid player index: " + player +  " LobbyInstance:setNation");
                e.printStackTrace();
                Logger.log("LobbyInstance:setNation Invalid player index: " + player);
                return false;
            }
        }
        return false;
    }

    public boolean isNationOpen(int nation) {
        //Random is never blocked.
        if (nation == Nation.RANDOM)
            return true;

        for (User user : users)
            if (user.getNation() == nation)
                return false;

        return true;
    }

    public int getFirstOpenPosition() {
        int position = 0;

        for(int j = 0; j < MAX_USERS; j++) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getPosition() == position) {
                    position++;
                    break;
                }
                if (i == users.size() - 1)
                    j = MAX_USERS;
            }
        }

        return position;
    }

    public boolean isHost(User u) {
        return users.size() > 0 && users.get(0).getUserName().compareTo(u.getUserName()) == 0;
    }

    public byte[] getLobbyId() { return lobbyId; }

    //Non-Reserved Multicast Address (224.0.1.0 - 238.255.255.255)
    public InetAddress getInetAddress() {
        try {
            return InetAddress.getByName("224." + (lobbyId[0] & 0xff) + ".1." + (lobbyId[1] & 0xff) );
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("LobbyInstance:getInetAddress - " + e.getMessage());
            return null;
        }
    }

    public int[] getMSD() {
        return new int[]{gameMode, gameScene, gameDuration};
    }

    public boolean[] getGameOptions() {
        return options;
    }

    public void setGameOption(int option, boolean isOn) {
        System.out.println("option minus NUM_OF_OPTIONS: " + (option - LobbyConstants.NUM_OF_OPTIONS) );
        switch(option) {
            case LobbyConstants.QUICK_BATTLES         : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;
            case LobbyConstants.HEROES                : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;
            case LobbyConstants.FORCE_MARCH           : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;
            case LobbyConstants.SKIRMISH              : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;
            case LobbyConstants.COUNTER_CHARGE        : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;
            case LobbyConstants.ELITE                 : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;
            case LobbyConstants.GENERALS              : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;
            case LobbyConstants.ADMIRALS              : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;
            case LobbyConstants.RIGHT_OF_PASSAGE      : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;
            case LobbyConstants.UPRISING              : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;
            case LobbyConstants.VOLUNTARY_PAP         : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;
            case LobbyConstants.CONTROL_NPN           : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;
            case LobbyConstants.ANNEX                 : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;
            case LobbyConstants.RECRUIT               : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;
            case LobbyConstants.RESTORE               : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;
            case LobbyConstants.CONTINENTAL_SYSTEM    : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;
            case LobbyConstants.RAISE_MILITIA_BY_SEA  : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;
            case LobbyConstants.RAISE_MILITIA_BY_LAND : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;
            case LobbyConstants.HARSH_CAMPAIGNS       : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;
            case LobbyConstants.BLIND_SETUP           : options[option - LobbyConstants.NUM_OF_OPTIONS] = isOn; break;

            case LobbyConstants.EMPIRE                : gameMode = LobbyConstants.EMPIRE; break;
            case LobbyConstants.TEAM                  : gameMode = LobbyConstants.TEAM; break;

            case LobbyConstants.I                     : gameScene = LobbyConstants.I; break;
            case LobbyConstants.II                    : gameScene = LobbyConstants.II; break;
            case LobbyConstants.III                   : gameScene = LobbyConstants.III; break;
            case LobbyConstants.IV                    : gameScene = LobbyConstants.IV; break;
            case LobbyConstants.V                     : gameScene = LobbyConstants.V; break;
            case LobbyConstants.V_V                   : gameScene = LobbyConstants.V_V; break;
            case LobbyConstants.VI                    : gameScene = LobbyConstants.VI; break;
            case LobbyConstants.VII                   : gameScene = LobbyConstants.VII; break;
            case LobbyConstants.VIII                  : gameScene = LobbyConstants.VIII; break;
            case LobbyConstants.IX                    : gameScene = LobbyConstants.IX; break;
            case LobbyConstants.X                     : gameScene = LobbyConstants.X; break;

            case LobbyConstants.ROUNDS_6              : gameDuration = LobbyConstants.ROUNDS_6; break;
            case LobbyConstants.ROUNDS_12             : gameDuration = LobbyConstants.ROUNDS_12; break;
            case LobbyConstants.ROUNDS_18             : gameDuration = LobbyConstants.ROUNDS_18; break;
            case LobbyConstants.ROUNDS_24             : gameDuration = LobbyConstants.ROUNDS_24; break;
            case LobbyConstants.ROUNDS_36             : gameDuration = LobbyConstants.ROUNDS_36; break;
            case LobbyConstants.ROUNDS_48             : gameDuration = LobbyConstants.ROUNDS_48; break;
        }
    }

    public boolean isLobbyReady() {
        for (User u: users)
            if (!u.isReady())
                return false;
        return true;
    }

    public boolean[] getReadyStates() {
        boolean[] readyStates = new boolean[MAX_USERS];
        for (User u: users)
            readyStates[u.getPosition()] = u.isReady();
        return readyStates;
    }

    public boolean addLobbyUser(User user) {
        if (!isLobbyFull()) {
            user.setPosition(getFirstOpenPosition());
            return users.add(user);
        } else
            return false;
    }

    public void startCountdown() {
        try {
            cdt = new CountdownThread(getLobbyId(), getInetAddress(), mcPort);
            cdt.setDaemon(true);
            cdt.start();
            starting();
        } catch (IOException e) {
            e.printStackTrace();
            ServerLogger.log("LobbyInstance:startCountdown - " + e.getMessage());
        }
    }

    public void stopCountdown() {
        try {
            if (cdt != null) {
                cdt.abort();
                cdt.join(100);
                stopping();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            ServerLogger.log("LobbyInstance:stopCountdown - " + e.getMessage());
        }
    }

    public void checkForRandom(InetAddress address, int port) {
        ArrayList<Integer> availNations = new ArrayList<Integer>();

        for (int temp = Nation.FRANCE; temp <= Nation.SPAIN; temp++)
            if ( isNationOpen(temp) )
                availNations.add(temp);

        for (User u : users) {
            if (u.getNation() == Nation.RANDOM) {
                u.setNation(availNations.remove(new Random().nextInt(availNations.size())));
                Utilities.setNation(lobbyId, u.getPosition(), u.getNation(), u.getLeader(), address, port);
            }
        }
    }

//    public void setNation(byte[] lobbyId, int userPosition, int nation, String leaderName) {
//        try {
//
//            byte[] outgoing;
//            DatagramSocket socket = new DatagramSocket();
//
//            InetAddress address = InetAddress.getByName(Messages.getString("server.address"));
//            System.out.println("LobbyInstance:setNation before");
//
//            outgoing = new SetNationPacket(lobbyId, userPosition, nation, leaderName).getPacket();
//            DatagramPacket packet = new DatagramPacket(outgoing, outgoing.length, address, LOBBY_SERVER_PORT);
//            socket.send(packet);
//
//            System.out.println("LobbyInstance:setNation sent");
//
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Logger.log("NapoleonClientThread:setNation - " + e.getMessage());
//        }
//    }

    public void removeLobbyUser(User user) { users.remove(user); }

    public ArrayList<User> getLobbyUsers() { return users; }

    public boolean hasUsers() { return users.size() > 0; }

    public String getLobbyName() { return lobbyName; }

    public void starting() { isStarting = true; }

    public void stopping() { isStarting = false; }

    public boolean isStarting() { return isStarting; }

    public void setMcPort(int port) { mcPort = port; }
        
    public boolean isLobbyFull() { return users.size() == MAX_USERS; }

    public boolean isLobbyEmpty() { return users.size() == EMPTY; }

    private static final int EMPTY = 0;
    public static final int MAX_USERS = 7;
    public static final int MAX_NAME_LENGTH = 32;
}
