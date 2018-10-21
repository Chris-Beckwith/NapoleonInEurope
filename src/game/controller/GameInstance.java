package game.controller;

import lobby.controller.LobbyInstance;
import lobby.controller.User;
import shared.controller.LobbyConstants;
import lobby.controller.Nation;

import java.util.ArrayList;

import game.util.GameMsg;
import game.controller.Unit.MilitaryUnit;
import game.controller.Unit.NavalSquadron;

/**
 * GameInstance.java  Date Created: Oct 31, 2012
 *
 * Purpose: Keep track of all relavant information related to a game.
 *
 * Description: This object will keep track of all relavant information for a game.
 *   including pre-game settings: Game Mode, Game Scenario, Game Duration and all pre-game options.
 *   and in-game settings: Army size and location, Navy size and location, Regions controlled (pp),
 *   PAPs owned, Month (round) and Year, Turn, Alliances and War Declarations, Right of Passage, Grace Period, etc..
 *
 * @author Chrisb
 */
public class GameInstance {
    /**
     * Contructor
     *
     * This the main contructor used.
     *
     * @param gameId - unique identifier as well as the IP address to connect to.
     * @param options - the boolean array of options related to a game.
     * @param msd - the int array of game settings (Mode, Scenario, Duration).
     */
    public GameInstance(byte[] gameId, boolean[] options, int[] msd) {
        this.users = new ArrayList<User>(LobbyInstance.MAX_USERS);
        this.gameId = gameId;
        this.options = options;
        step = START_OF_GAME;
        setAllGameOptions(options);
        setGameMode(msd[0]);
        setGameScenario(msd[1]);
        setGameDuration(msd[2]);
    }

    /**
     * Used once this game instance has all the information related to the game.
     * But not the users involved.. do I care about users??
     */
    public void setupGame() {
        System.out.println("Setting up Game Instance");
        //TODO other scenarios
        switch(gameMode) {
            case LobbyConstants.EMPIRE:
                break;
            case LobbyConstants.TEAM: break;
        }

        switch(gameScenario) {
            case LobbyConstants.I: break;
            case LobbyConstants.II: break;
            case LobbyConstants.III: break;
            case LobbyConstants.IV: break;
            case LobbyConstants.V: break;
            case LobbyConstants.V_V: break;
            case LobbyConstants.VI: break;
            case LobbyConstants.VII: break;
            case LobbyConstants.VIII: break;
            case LobbyConstants.IX: break;
            case LobbyConstants.X:
                //Initialize Turn, Round and Year
                turn = Nation.NEUTRAL;
                round = 3;
                year = 1820;
                //Initialize Map
                map = new MapInstance();
                //Initialize all nations for Scenario X
                initNations(LobbyConstants.X, map);
                //Initialize Minor Nations Navy
                //Swe - 1 || Den - 0 || Por - 1 || Hol - 1
                UnitController.moveSeaUnit(new NavalSquadron(Nation.NEUTRAL), map.getPort(GameMsg.getString("port.holland")));
                UnitController.moveSeaUnit(new NavalSquadron(Nation.NEUTRAL), map.getPort(GameMsg.getString("port.portugal")));
                UnitController.moveSeaUnit(new NavalSquadron(Nation.NEUTRAL), map.getPort(GameMsg.getString("port.swedenNorth")));
                nextTurn();
                break;
        }

        switch(gameDuration) {
            case LobbyConstants.ROUNDS_6: roundsRemaining = initialRounds = 6; break;
            case LobbyConstants.ROUNDS_12: roundsRemaining = initialRounds = 12; break;
            case LobbyConstants.ROUNDS_18: roundsRemaining = initialRounds = 18; break;
            case LobbyConstants.ROUNDS_24: roundsRemaining = initialRounds = 24; break;
            case LobbyConstants.ROUNDS_36: roundsRemaining = initialRounds = 36; break;
            case LobbyConstants.ROUNDS_48: roundsRemaining = initialRounds = 48; break;
            default: roundsRemaining = initialRounds = 2;
        }

    }

    private void initNations(int scenario, MapInstance map) {
        switch (scenario) {
            case LobbyConstants.I: break;
            case LobbyConstants.II: break;
            case LobbyConstants.III: break;
            case LobbyConstants.IV: break;
            case LobbyConstants.V: break;
            case LobbyConstants.V_V: break;
            case LobbyConstants.VI: break;
            case LobbyConstants.VII: break;
            case LobbyConstants.VIII: break;
            case LobbyConstants.IX: break;
            case LobbyConstants.X:
                nations = new NationInstance[7];
                for (int i = 0; i < 7; i++)
                    nations[i] = new NationInstance(i+1, 4, scenario, map);
                for (User u : users) {
                    nations[u.getNation() - 1].setControlledNation(true, u.getUserName());
                    nations[u.getNation() - 1].setPlayerNation(true);

                    if ( u.getSecondaryNation() > 0 )
                        nations[u.getSecondaryNation() - 1].setControlledNation(true, u.getUserName());

                    for (Integer n : u.getControlledNPNs())
                        nations[n - 1].setControlledNation(true, u.getUserName());    
                }
                break;
        }
    }

    public NationInstance getNation(int nation) { return nations[nation - 1]; }

    public void addUser(User u) {
        if (users.size() < 7) {
            users.ensureCapacity(users.size() + 1);
            users.add(u);
        }
    }

    public void addUsers(ArrayList<User> users) {
        if (this.users.size() + users.size() < 8) {
            this.users.ensureCapacity(this.users.size() + users.size());
            this.users.addAll(users);
        }
    }

    public boolean removeUser(User u) { return users.remove(u); }

    public boolean isControlledNation(int nation) {
        return nations[nation - 1].isControlledNation();
    }

    public boolean isUserNation(int nation) {
        for (User u : users)
            if (u.getNation() == nation)
                return true;
        return false;
    }

    public User getUserByNPN(int nonPlayerNation) {
        for (User u : getUsers())
            if (u.getControlledNPNs().contains(nonPlayerNation))
                return u;
        return null;
    }

    public User getUserByNation(int nation) {
        for (User u : getUsers())
            if ( u.getNation() == nation)
                return u;
        return null;
    }

    public int getInitialRounds() { return initialRounds; }
    public int getRoundsRemaining() { return roundsRemaining; }
    public boolean isStartOfGame() { return step == START_OF_GAME; }

    public String getProductionRoundAsString() {
        if (round == 1 || round == 2 || round == 3 )
            return GameMsg.getString("month.full.3");
        else if (round ==  4 || round == 5 || round == 6)
            return GameMsg.getString("month.full.6");
        else if (round == 7 || round == 8 || round == 9 )
            return GameMsg.getString("month.full.9");
        else if (round == 10 || round == 11 || round == 12 )
            return GameMsg.getString("month.full.12");

        return "ErrorProductionRound";
    }

    public String getRoundYearAsString() { return GameMsg.getString("month." + round) + ", " + year; }
    public String getRoundAsString() { return GameMsg.getString("month." + round); }
    public int getStep() { return step; }
    public int getTurn() { return turn; }
    public int getMonth(){ return round;}
    public int getYear() { return year; }
    public int getProductionStep() { return productionStep; }

    public void setStep(int step) { this.step = step; }
    public void setProductionStep(int pStep) { productionStep = pStep; }

    public void setupDiplomaticRound(int roundType, int startingNation, int toNation) {
        diplomaticPrevStep = step;
        setStep(DIPLOMATIC_ROUND);
        diplomaticActionType = roundType;
        diplomaticStartingNation = startingNation;
        diplomaticToNation = toNation;
        diplomaticNationTurn = startingNation;
        diplomaticRoundStarted = false;
    }

    public void endDiplomaticRound() {
        setStep(diplomaticPrevStep);
        diplomaticPrevStep = -1;
        diplomaticActionType = -1;
        diplomaticStartingNation = -1;
        diplomaticToNation = -1;
        diplomaticNationTurn = -1;
    }

    public void nextDiplomaticTurn() {
        diplomaticNationTurn++;
        if (diplomaticNationTurn > Nation.TOTAL_NATIONS)
            diplomaticNationTurn = Nation.FRANCE;
        if (!isTurnOnUserControlledNation(diplomaticNationTurn))
            nextDiplomaticTurn();
    }

    public boolean isDiplomaticRound() { return getStep() == DIPLOMATIC_ROUND; }
    public void startDiplomaticRound() { diplomaticRoundStarted = true; }
    public boolean hasRoundStarted() { return diplomaticRoundStarted; }
    public int getDiplomaticPrevStep() { return diplomaticPrevStep; }
    public int getDiplomaticActionType() { return diplomaticActionType; }
    public int getDiplomaticStartingNation() { return diplomaticStartingNation; }
    public int getDiplomaticToNation() { return diplomaticToNation; }
    public int getDiplomaticNationTurn() { return diplomaticNationTurn; }
    public void setDiplomaticNationTurn(int nation) { diplomaticNationTurn = nation; }

    public void nextTurn() {
        turn++;
        if (turn > Nation.TOTAL_NATIONS)
            turn = Nation.FRANCE;
        if (!isTurnOnUserControlledNation(turn))
            nextTurn();
    }

    private boolean isTurnOnUserControlledNation(int nation) {
        for (NationInstance n : nations)
            if (nation == n.getNationNumber() && n.isControlledNation())
                return true;
        return false;
    }

    public MapInstance getMap() { return map; }
    public byte[] getGameId() { return gameId; }
    public ArrayList<User> getUsers() { return users; }
    public boolean isGameFull() { return users.size() == LobbyInstance.MAX_USERS; }

    public ArrayList<Integer> getControlledNations() {
        ArrayList<Integer> controlledNations = new ArrayList<Integer>();
        for (NationInstance n : nations)
            if (n.isControlledNation())
                controlledNations.add(n.getNationNumber());
        return controlledNations;
    }

    public ArrayList<Integer> getUserNations() {
        ArrayList<Integer> userNations = new ArrayList<Integer>();
        for (User u : users)
            userNations.add(u.getNation());

        return userNations;
    }

    /*
    * Option Getters
    */
    public int getGameMode() { return gameMode; }
    public int getGameScenario() { return gameScenario; }
    public int getGameDuration() { return gameDuration; }
    public boolean isQuickBattles() { return quickBattles; }
    public boolean isHeroes() { return heroes; }
    public boolean isForceMarch() { return forceMarch; }
    public boolean isSkirmish() { return skirmish; }
    public boolean isCounterCharge() { return counterCharge; }
    public boolean isElite() { return elite; }
    public boolean isGenerals() { return generals; }
    public boolean isAdmirals() { return admirals; }
    public boolean isRightOfPassage() { return rightOfPassage; }
    public boolean isUprising() { return uprising; }
    public boolean isVoluntaryPAP() { return voluntaryPAP; }
    public boolean isControlNPN() { return controlNPN; }
    public boolean isAnnex() { return annex; }
    public boolean isRecruit() { return recruit; }
    public boolean isRestore() { return restore; }
    public boolean isContinentalSystem() { return continentalSystem; }
    public boolean isRaiseMilitia() { return raiseMilitia; }
    public boolean isRaiseMilitiaByLand() { return raiseMilitiaByLand; }
    public boolean isHarshCampaign() { return harshCampaign; }
    public boolean isBlindSetup() { return blindSetup; }
    public boolean[] getAllOptions() { return options; }
    public int[] getMSD() { return new int[]{gameMode, gameScenario, gameDuration}; }

    /*
     * Option Setters - can't be changed after start.
     */
    private void setGameMode(int gameMode) { this.gameMode = gameMode; }
    private void setGameScenario(int gameScenario) { this.gameScenario = gameScenario; }
    private void setGameDuration(int gameDuration) { this.gameDuration = gameDuration; }
    private void setQuickBattles(boolean quickBattles) { this.quickBattles = quickBattles; }
    private void setHeroes(boolean heroes) { this.heroes = heroes; }
    private void setForceMarch(boolean forceMarch) { this.forceMarch = forceMarch; }
    private void setSkirmish(boolean skirmish) { this.skirmish = skirmish; }
    private void setCounterCharge(boolean counterCharge) { this.counterCharge = counterCharge; }
    private void setElite(boolean elite) { this.elite = elite; }
    private void setGenerals(boolean generals) { this.generals = generals; }
    private void setAdmirals(boolean admirals) { this.admirals = admirals; }
    private void setRightOfPassage(boolean rightOfPassage) { this.rightOfPassage = rightOfPassage; }
    private void setUprising(boolean uprising) { this.uprising = uprising; }
    private void setVoluntaryPAP(boolean voluntaryPAP) { this.voluntaryPAP = voluntaryPAP; }
    private void setControlNPN(boolean controlNPN) { this.controlNPN = controlNPN; }
    private void setAnnex(boolean annex) { this.annex = annex; }
    private void setRecruit(boolean recruit) { this.recruit = recruit; }
    private void setRestore(boolean restore) { this.restore = restore; }
    private void setContinentalSystem(boolean continentalSystem) { this.continentalSystem = continentalSystem; }
    private void setRaiseMilitia(boolean raiseMilitia) { this.raiseMilitia = raiseMilitia; }
    private void setRaiseMilitiaByLand(boolean raiseMilitiaByLand) { this.raiseMilitiaByLand = raiseMilitiaByLand; }
    private void setHarshCampaign(boolean harshCampaign) { this.harshCampaign = harshCampaign; }
    private void setBlindSetup(boolean blindSetup) { this.blindSetup = blindSetup; }

    private void setAllGameOptions(boolean[] options) {
        setQuickBattles(options[0]);
        setHeroes(options[1]);
        setForceMarch(options[2]);
        setSkirmish(options[3]);
        setCounterCharge(options[4]);
        setElite(options[5]);
        setGenerals(options[6]);
        setAdmirals(options[7]);
        setRightOfPassage(options[8]);
        setUprising(options[9]);
        setVoluntaryPAP(options[10]);
        setControlNPN(options[11]);
        setAnnex(options[12]);
        setRecruit(options[13]);
        setRestore(options[14]);
        setContinentalSystem(options[15]);
        setRaiseMilitia(options[16]);
        setRaiseMilitiaByLand(options[17]);
        setHarshCampaign(options[18]);
        setBlindSetup(options[19]);
    }

    /*
     * Variables
     */
    private int gameMode;
    private int gameScenario;
    private int gameDuration;

    private boolean quickBattles;
    private boolean heroes;
    private boolean forceMarch;
    private boolean skirmish;
    private boolean counterCharge;
    private boolean elite;
    private boolean generals;
    private boolean admirals;
    private boolean rightOfPassage;
    private boolean uprising;
    private boolean voluntaryPAP;
    private boolean controlNPN;
    private boolean annex;
    private boolean recruit;
    private boolean restore;
    private boolean continentalSystem;
    private boolean raiseMilitia;
    private boolean raiseMilitiaByLand;
    private boolean harshCampaign;
    private boolean blindSetup;
    private boolean[] options;

    private int step;
    private int productionStep;
    private int turn;
    private int round;
    private int roundsRemaining;
    private int initialRounds;
    private int year;
    private int diplomaticPrevStep;
    private int diplomaticActionType;
    private int diplomaticStartingNation;
    private int diplomaticToNation;
    private int diplomaticNationTurn;
    private boolean diplomaticRoundStarted;
    private NationInstance[] nations;
    private MapInstance map;

    private byte[] gameId;
    private ArrayList<User> users;

    //Steps of a round
    public static int START_OF_GAME             = 0;
    public static int ROLL_FOR_PAP              = 1;
    public static int START_OF_TURN             = 2;
    public static int SEA_MOVEMENT              = 3;
    public static int NAVAL_BATTLE              = 4;
    public static int LAND_MOVEMENT             = 5;
    public static int LAND_BATTLE               = 6;
    public static int END_OF_TURN               = 7;
    public static int DIPLOMATIC_ROUND          = 8;
    public static int PRODUCTION_ROUND          = 9;

    //Steps of production
    public static int NOT_PRODUCTION            = 10;
    public static int CONTINENTAL_SYSTEM        = 11;
    public static int PAP_TO_FRANCE             = 12;
    public static int COLONIAL_TRADE            = 13;
    public static int DISBANDING_MILITIA        = 14;
    public static int TRAINING_MILITIA          = 15;
    public static int SPENDING_POINTS           = 16;
    public static int PLACING_PIECES            = 17;
    public static int HARSH_CAMPAIGNS           = 18;
    public static int SUCCESS_UPRISINGS         = 19;
    public static int LINE_OF_COMMUNICATIONS    = 20;


    //Steps of a Production Round
    //---------------------------
    //Enforce Continental System
    //1 Pap to France (Not Scene.X)
    //Colonial Trade
    //Production Turn
    //  Disbanding Militia
    //  Training Militia
    //  Spending Production Points
    //  Placing Pieces
    //Harsh Campaigns
    //Successful Uprisings
    //Line of Communications
}