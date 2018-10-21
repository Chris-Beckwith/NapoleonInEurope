package lobby.controller;

import util.Messages;

import java.util.ArrayList;

/**
 * User.java  Date Created: Apr 4, 2012
 *
 * Purpose: To keep track of individual user properties.
 *
 * Description: User object is created upon successful login and
 * initialized with a user name and any other relavant properties.
 *
 * @author Chrisb
 */

public class User {

    private String userName;
    private int position;
    private int nation;
    private String leader;
    private boolean isReady;
    private boolean isHost;

    private int secondNation;
    private ArrayList<Integer> controlledNPNs;

    public User(String name) {
        this(name, 0, 0, Messages.getString("nation.0.leader"));
    }

    public User(String name, int position, int nation, String leader) {
        userName = name;
        this.position = position;
        this.nation = nation;
        this.leader = leader;
        isReady = false;
        secondNation = 0;
        controlledNPNs = new ArrayList<Integer>();
    }

    public void setSecondaryNation(int nation) { secondNation = nation; addControlledNPN(nation); }
    public int getSecondaryNation() { return secondNation; }

    public void addControlledNPN(int nation) {
        if (!controlledNPNs.contains(nation))
            controlledNPNs.add(nation);
    }

    public boolean removeControlledNPN(int nation) {
        return getSecondaryNation() != nation && controlledNPNs.remove(new Integer(nation));
    }

    public ArrayList<Integer> getControlledNPNs() { return controlledNPNs; }

    public String getUserName() { return userName; }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }

    public int getNation() { return nation; }
    public void setNation(int nation) { this.nation = nation; }

    public String getLeader() { return leader; }
    public void setLeader(String leader) { this.leader = leader; }

    public boolean isReady() { return isReady; }
    public void setReady(boolean isReady) { this.isReady = isReady; }

    public boolean isHost() { return isHost; }
    public void setHost(boolean isHost) { this.isHost = isHost; }

    public static final int MAX_USER_LENGTH = 20;
    public static final int MAX_LEADER_LENGTH = 20;
}
