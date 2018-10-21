package game.controller;

import game.client.*;
import game.client.political.*;
import game.controller.Region.CapitalRegion;
import game.controller.Region.LandRegion;
import game.controller.Region.Port;
import game.controller.Region.Region;
import game.controller.Unit.*;
import game.packet.GameAddUnitsPacket;
import game.packet.GamePlaceUnitsPacket;
import game.util.*;
import lobby.controller.Nation;
import lobby.controller.User;
import shared.controller.LobbyConstants;
import util.Utilities;

import javax.swing.*;
import java.util.*;

/**
 * GameController.java  Date Created: Oct 24, 2012
 *
 * Purpose: To control the game.
 *
 * Description:
 *
 * @author Chrisb
 */
public class GameController {

    public GameController(User user, byte[] gameId) {
        isGameReady = false;
        this.user = user;
        this.gameId = gameId;
        chatNation = Nation.GLOBAL;

        gct = new GameClientThread(Utilities.idAsInetAddress(gameId), this);
        gct.setDaemon(true);
        gct.start();
    }

    public void requestGameInfo() {
        gct.requestGameInfo(gameId, getUserNation());
        gct.requestGameUsers(gameId, getUserNation());
    }

    /**
     * createGame is used to initialize the game object before setting up the gui.
     * @param gameId - unique id of the game, used by server to know which game to send packets to.
     * @param options - the game options.
     * @param msd - the game mode, scenario and duration.
     */
    public void createGame(byte[] gameId, boolean[] options, int[] msd) {
        if (game == null) {
            System.out.println("***** GameController:createGame");
            if (Utilities.compareLobbyIds(this.gameId,gameId)) {
                game = new GameInstance(gameId, options, msd);
    //            unitController = new UnitController(this);
            } else
                GameLogger.log("GameController:createGame - lobbyIds do not match");
        }
    }

    /**
     * addUsers is used to initialize the users in the game befroe setting up the gui.
     * @param gameId - unique id of the game, used by server to know which game to send packets to.
     * @param users - list of users in the game.
     */
    public void addUsers(byte[] gameId, ArrayList<User> users) {
        System.out.println("GameController:addUsers");
        if (game != null) {
            System.out.println("GameController:addUsers game not null");
            if (Utilities.compareLobbyIds(game.getGameId(), gameId)) {
                System.out.println("GameController:addUsers add users.." + users.size());
                game.addUsers(users);
                System.out.println("GameController:addUsers totalSize: " + game.getUsers().size());
            } else {
                GameLogger.log("GameController:addUsers - lobbyIds do not match");
            }
        }
    }

    /**
     * Change the nation with which this.nation will send/view messages.
     * @param newNation - the new nation, this.nation wishes to send/view messages.
     */
    public void changeChatNation(int newNation) {
        if (newNation == Nation.GLOBAL) {
            display.changeChatNation(chatNation, newNation);
            chatNation = newNation;
        } else if (game.isUserNation(newNation)) {
            display.changeChatNation(chatNation, newNation);
            chatNation = newNation;
        }
    }

    /**
     * Send a chat message to the server.  Include the gameId which it came from,
     * the nation who is sending the message, the nation(s) which the message will be sent
     * and the message the user is sending.
     * @param message - the communique.
     */
    public void sendChatMessage(String message) {
        gct.sendChatMessage(gameId, user.getNation(), getChatNation(),  message);
    }

    /**
     * Receive a communique from the server.  Depending on who it was sent to/from add the message
     * to the appropriate chat window.
     * @param gameId - id of game which message was sent from.
     * @param fromNation - the nation who sent the message.
     * @param toNation - the nation the message was sent to.
     * @param message - the communique
     */
    public void receiveChatMessage(byte[] gameId, int fromNation, int toNation, String message) {
        if (Utilities.compareLobbyIds(gameId, this.gameId)) {
            String fromLeaderName = "ErrorName";
            if (toNation == getUserNation()) {
                for (User u : game.getUsers())
                    if (u.getNation() == fromNation)
                        fromLeaderName = u.getLeader();
                display.receiveChatMsg(fromNation, fromLeaderName, message);
            } else if (fromNation == getUserNation()) {
                for (User u: game.getUsers())
                    if (u.getNation() == fromNation)
                        fromLeaderName = u.getLeader();
                display.addChatMsg(toNation, fromLeaderName, message);            
            } else if (toNation == Nation.GLOBAL) {
                for (User u: game.getUsers())
                    if (u.getNation() == fromNation)
                        fromLeaderName = u.getLeader();
                if (getChatNation() == Nation.GLOBAL)
                    display.addChatMsg(toNation, fromLeaderName, message);
                else
                    display.receiveChatMsg(toNation, fromLeaderName, message);
            }
        }
    }

    public void save() {
        //Save todo
    }

    //todo
    public void setupDiplomaticRound(int actionType, int startingNation, int toNation) {
        if (!game.isDiplomaticRound()) {
            System.out.println("GameController:setupDiplomaticRound");
            game.setupDiplomaticRound(actionType, startingNation, toNation);
        } else {
            //Action taken during diplomatic round, do nothing..
            //This method should not be called when during a diplomatic round.
        }
        display.refreshGameStateInfo();
    }

    /*
     * Set both nations at war with eachother.
     * Tell the display to update political status and nation summary
     * Add Event to event log.
     */
    public void doWarDeclaration(int declaringNation, int enemyNation, int papCost) {
        getNationInstance(declaringNation).declareWar(enemyNation, papCost, true);
        getNationInstance(enemyNation).declareWar(declaringNation, 0, false);
        String eventString = EventLogger.addWarEvent(declaringNation, enemyNation, game.getMonth(), game.getYear());
        display.displayEvent(eventString);
    }

    //Declare War Utility method - More than one player eligible to control npn, ask first user in list.
    public void controlNationRequest(int declaringNation, int uncontrolledNation, ArrayList<Integer> userNations) {
        int nationToControl = userNations.remove(0);
        setDiplomaticTurn(nationToControl);

        if (getUserNation() == nationToControl)
            display.controlRequestDialog(declaringNation, uncontrolledNation, userNations);
    }

    /**
     * This method is called when a nation declares war on an uncontrolled nation and is not already at war.
     * This method will start a chain of events that allows every other eligible nation a chance to declare war
     * on the declaring nation.
     *
     * Declare War Utility method
     *
     * After everyone has a chance to declare war.
     * If enemies of declarer contain more than just enemyUncontrolledNation.
     *     A User in that group will gain control of enemyUncontrolledNation.
     * Else
     *     One of the Users other than declaringUser will gain control of enemyUncontrolledNation.
     *
     * @param declaringNation - nation that just declared war.
     * @param enemyUncontrolledNation - nation war was declared on.
     */
    public void allowNationToDeclareWar(int declaringNation, int enemyUncontrolledNation) {
        nextDiplomaticTurn();
        System.out.println("GameController:allowNationToDeclareWar - declarer: " + declaringNation + " uncontrolled: " + enemyUncontrolledNation);
        System.out.println("Diplomatic Turn: " + game.getDiplomaticNationTurn() + " startingNation: " + game.getDiplomaticStartingNation());
        if (game.getDiplomaticNationTurn() == getUserNation() && game.getDiplomaticStartingNation() != getUserNation()) {
            if ( political.canDeclareWar(getUserNation(), declaringNation) ) {
                int papCost = PoliticalUtilities.getWarDecPapCost(declaringNation, enemyUncontrolledNation, this);
                display.allowNationToDeclareWar(declaringNation, enemyUncontrolledNation, papCost, getUserNation());
            } else
                new RetaliationWarDecThread(getGameId(), declaringNation, enemyUncontrolledNation, getUserNation()).start();

        } else if (game.getDiplomaticNationTurn() == game.getDiplomaticStartingNation()
                && game.getDiplomaticNationTurn() == getUserNation()) {
            //Get Nations at war and pick one from group
            ArrayList<Integer> enemies = getNationInstance(getUserNation()).getEnemies();
            ArrayList<User> userGroup = PoliticalUtilities.getUsersAtWarWith(declaringNation, enemies, getAllUsers());

            if (userGroup.size() == 1)
                new NationToControlThread(getGameId(), declaringNation, enemyUncontrolledNation, userGroup.get(0).getNation()).start();
            else {
                ArrayList<Integer> userNations;
                if (userGroup.size() == 0) {
                    userGroup = new ArrayList<User>();
                    for (User u : getAllUsers())
                        if ( user.getNation() != u.getNation() )
                            userGroup.add(u);

                    userNations = PoliticalUtilities.sortUsersBySize(userGroup, this);
                } else
                    userNations = PoliticalUtilities.sortUsersBySize(userGroup, this);

                if (userNations.size() == 1)
                    new NationToControlThread(getGameId(), declaringNation, enemyUncontrolledNation, userNations.get(0)).start();
                else
                    new MultiNationControlThread(getGameId(), declaringNation, enemyUncontrolledNation, userNations).start();
            }
        }
    }

    //Declare War Utility method.  Word received from server about if nation wants to declare retaliation war.
    public void retaliationWarDeclaration(int initialDeclarer, int retaliatingNation, boolean isDeclaring, int papCost, int uncontrolledNation) {
        System.out.println("GameController:retaliationWarDeclaration - initialDeclarer: " + initialDeclarer + " retaliator: " + retaliatingNation + " uncontrolled: " + uncontrolledNation);
        if (isDeclaring) {
            getNationInstance(retaliatingNation).declareWar(initialDeclarer, papCost, true);
            getNationInstance(initialDeclarer).declareWar(retaliatingNation, 0, false);
            String eventString = EventLogger.addWarEvent(retaliatingNation, initialDeclarer, game.getMonth(), game.getYear());
            display.displayEvent(eventString);
        }
        allowNationToDeclareWar(initialDeclarer, uncontrolledNation);
    }

    public void doSueForPeace(int sueingNation) {
        setupDiplomaticRound(EventLogger.SUE_FOR_PEACE, sueingNation, -1);
        game.startDiplomaticRound();

        congressNations = PoliticalUtilities.doSueForPeace(sueingNation, game);
        ArrayList<Integer> firstNations = PoliticalUtilities.spoilsOfWar(congressNations, getNationInstance(sueingNation));
        int secondNation = firstNations.remove(firstNations.size() - 1);
        
        String eventString = EventLogger.addSueForPeaceEvent(sueingNation, congressNations, firstNations, secondNation, game.getMonth(), game.getYear());
        display.displayEvent(eventString);

        //Hold Congress
        holdPeaceCongress(sueingNation, 0);
    }

    public void holdPeaceCongress(int sueingNation, int turn) {
        NationInstance sueNation = getNationInstance(sueingNation);
        if (congressNations.size() > 0) {
            //turn order is based on old enemy order
            NationInstance cn = getNationInstance(congressNations.get(turn));
            setCongressNationTurn(cn.getNationNumber());

            if ( !sueNation.isExtinct() )
                if ( user.getNation() == game.getDiplomaticNationTurn() || user.getControlledNPNs().contains(game.getDiplomaticNationTurn()) )
                    display.showPeaceCongressOptions(sueingNation, game.getDiplomaticNationTurn(), turn);
            else {
                //Update Menus
                //End Congress
                PoliticalUtilities.makeNationExtinct(sueNation);
                MessageDialog.sueingNationExtinct(display.getMap(), sueingNation);
                PoliticalUtilities.endPeaceCongress(sueNation, game, this);
            }
        } else {
            //End Congress
            if ( !sueNation.isExtinct() && !sueNation.isPlayerNation() )
                PoliticalUtilities.moveNPNMilitary(sueNation, game, this);
            else
                PoliticalUtilities.endPeaceCongress(sueNation, game, this);
        }
    }

    private void setCongressNationTurn(int nation) {
        if (game.isDiplomaticRound())
            setDiplomaticTurn(nation);
    }

    public void congressAnnex(int sueingNation, int turnIndex, int regionOneIndex, int congressNation) {
        PoliticalUtilities.doCongressAnnex( getNationInstance(sueingNation), getNationInstance(congressNation),
                (LandRegion)game.getMap().getRegionFromIndex(regionOneIndex, Region.LAND_REGION) );
        if ( getNationInstance(sueingNation).isExtinct() )
            PoliticalUtilities.makeNationExtinct(getNationInstance(sueingNation));
        display.refreshTerritoryInfo();
        //Next Nation's Turn
        holdPeaceCongress(sueingNation, incrementTurnIndex(turnIndex));
    }

    public void congressRestore(int sueingNation, int turnIndex, int regionOneIndex, int regionTwoIndex) {
        PoliticalUtilities.doCongressRestore( getNationInstance(sueingNation),
                (LandRegion)game.getMap().getRegionFromIndex(regionOneIndex, Region.LAND_REGION) );
        if (regionTwoIndex > 0) {
            PoliticalUtilities.doCongressRestore( getNationInstance(sueingNation),
                (LandRegion)game.getMap().getRegionFromIndex(regionTwoIndex, Region.LAND_REGION) );
        }
        if ( getNationInstance(sueingNation).isExtinct() )
            PoliticalUtilities.makeNationExtinct(getNationInstance(sueingNation));
        //Next Nation's Turn
        holdPeaceCongress(sueingNation, incrementTurnIndex(turnIndex));
    }

    public void congressFreeSerfs(int sueingNation, int turnIndex, int regionOneIndex, int regionTwoIndex, int regionThreeIndex) {
        PoliticalUtilities.doCongressFreeSerfs( (LandRegion)game.getMap().getRegionFromIndex(regionOneIndex, Region.LAND_REGION) );
        if (regionTwoIndex > 0)
            PoliticalUtilities.doCongressFreeSerfs( (LandRegion)game.getMap().getRegionFromIndex(regionTwoIndex, Region.LAND_REGION) );
        if (regionThreeIndex > 0)
            PoliticalUtilities.doCongressFreeSerfs( (LandRegion)game.getMap().getRegionFromIndex(regionThreeIndex, Region.LAND_REGION) );
        //Next Nation's Turn
        holdPeaceCongress(sueingNation, incrementTurnIndex(turnIndex));
    }

    public void congressPass(int sueingNation, int turnIndex) {
        Integer nation = congressNations.remove(turnIndex);
        getNationInstance(nation).resetAnnexedHomelandThisCongress();
        holdPeaceCongress(sueingNation, turnIndex);
    }

    private int incrementTurnIndex(int turnIndex) {
        turnIndex++;
        if (turnIndex == congressNations.size())
            turnIndex = 0;
        return turnIndex;
    }

    /**
     * This method used for when someone successfully gains control of a NPN.
     *
     * Also, when war is declared on an uncontrolled nation another user must take control of that nation.
     * This method will be called when a user has accepted control or when a user has to accept
     * control because they is only one user in the group eligible to control.
     *
     * Control NPN
     * Conclude Armistices
     * Allow Form Alliance (only between nations he controls)
     * May Spend all production points
     *    Then place all these pieces
     *
     * @param declaringNation - If this is a valid nation number, then this action was caused by a warDeclaration.
     * @param uncontrolledNation - The uncontrolled nation, soon to be controlled.
     * @param nationToControl - The nation taking control of the NPN.
     * @param isNotification - Is this a notification for users not gaining control?
     */
    public void doControlNonPlayerNation(int declaringNation, int uncontrolledNation, int nationToControl, boolean isNotification) {
        //Change diplomatic turn to newly controlled nation so they may complete their actions.
        setDiplomaticTurn(uncontrolledNation);

        //If nationToControl is controlled by user, give user control.
        if ( isNationControlledByUser(nationToControl) && !isNotification ) {
            //Give control after Armistices are concluded.  Add control to user and user in game.
            hasBeenControlled = PoliticalUtilities.doControlNPN(getNationInstance(uncontrolledNation), game.getUserByNation(nationToControl),
                    user, game.getUserByNPN(uncontrolledNation));
            display.refreshNationSummary();

            System.out.println("doControlNonPlayerNation - " + user.getControlledNPNs().contains(uncontrolledNation));

            //Event Log
            String eventString = EventLogger.addControlNPNEvent(user.getUserName(), user.getNation(),
                    uncontrolledNation, game.getMonth(), game.getYear());
            display.displayEvent(eventString);

            //Tell User they just got control of a nation.
            if (declaringNation > 0)
                MessageDialog.informUserOfControl(display.getMap(), declaringNation, uncontrolledNation);

            //Conclude Armistice
            controlNPNConcludeArmistices(uncontrolledNation);

        } else if ( !(user.getNation() == nationToControl) && !user.getControlledNPNs().contains(nationToControl) ) {
            //For the users not gaining control update them about the user that is.
            User userToControl = game.getUserByNation(nationToControl);
            if ( user.getControlledNPNs().contains(uncontrolledNation) )
                user.removeControlledNPN(uncontrolledNation);
            
            PoliticalUtilities.doControlNPN(getNationInstance(uncontrolledNation), userToControl, null, game.getUserByNPN(uncontrolledNation));
            display.refreshNationSummary();

            //Event Log
            String eventString = EventLogger.addControlNPNEvent(userToControl.getUserName(), userToControl.getNation(),
                    uncontrolledNation, game.getMonth(), game.getYear());
            display.displayEvent(eventString);
        }
    }

    //Used to conclude armistices before doing controlNPN.
    public void controlNPNConcludeArmistices(int uncontrolledNation) {
        //In Team game, break alliances with 'uncontrolledNation' and any nations on opposing team. todo
        NationInstance NPNation = getNationInstance(uncontrolledNation);
        //Armistices
        if (NPNation.isEnemy(user.getNation())) {
            new ConcludeArmisticeThread(getGameId(), uncontrolledNation, user.getNation(), false, true).start();
            return;
        } else {
            for (Integer n : user.getControlledNPNs()) {
                if (NPNation.isEnemy(n)) {
                    new ConcludeArmisticeThread(getGameId(), uncontrolledNation, n, false, true).start();
                    return;
                }
            }
        }
        //After all armistices have been concluded, allow User to form Alliances (only between nations (s)he controls)
        controllNPNFormAlliance(uncontrolledNation, -1);
    }

    public void controllNPNFormAlliance(int requester, int nationTwo) {
        ArrayList<Integer> potentialNations = new ArrayList<Integer>();
        potentialNations.add(user.getNation());
        potentialNations.addAll(user.getControlledNPNs());
        if (nationTwo > 0)
            potentialNations.remove((Integer)nationTwo);
        ArrayList<Integer> potentialAllies = PoliticalUtilities.getNationsThatCanAlly(political, requester, potentialNations);

        if (potentialAllies.size() > 0)
            display.formAllianceKa(requester, potentialAllies);
        else
            controlNPNPurchase(requester);
    }

    //Called after forming alliances is complete.
    public void controlNPNPurchase(int uncontrolledNation) {
        NationInstance NPNation = getNationInstance(uncontrolledNation);
        //User May Spend Production Points and Place Purchases
        boolean canPurchaseSomething = false;
        for (int unitType = MilitaryUnit.GENERAL; unitType <= MilitaryUnit.PAP; unitType++) {
            ArrayList<Integer> fauxPurchase = new ArrayList<Integer>();
            fauxPurchase.add(unitType);
            canPurchaseSomething = NPNation.canPurchaseUnit(unitType, fauxPurchase, game.getYear());
        }
        if (canPurchaseSomething)
            display.showPurchasePanel(uncontrolledNation, hasBeenControlled);
        else {
            MessageDialog.notEnoughProductionToBuy(display.getMap(), uncontrolledNation);
            if (!hasBeenControlled)
                display.showPlacePanel(NPNation.getMilitary(), getPlaceableRegions(uncontrolledNation));
            else
                new AllUnitsPlacedThread(getGameId(), uncontrolledNation).start();
        }
    }

    public void requestPermissionToControlNPN(int playerNation, int nonPlayerNation) {
        System.out.println("GameController:requestPermissionToControlNPN - nonPlayerNation: " + nonPlayerNation);
        if (user.getControlledNPNs().contains(nonPlayerNation)) {
            int choice = MessageDialog.requestPermissionToControlNPN(getDisplay().getMap(), game.getUserByNation(playerNation).getUserName(), nonPlayerNation);
            if (choice == JOptionPane.YES_OPTION)
                new ControlNPNThread(getGameId(), playerNation, nonPlayerNation, getNationInstance(nonPlayerNation).isControlledNation(), 1).start();
            else
                new ControlNPNThread(getGameId(), playerNation, nonPlayerNation, getNationInstance(nonPlayerNation).isControlledNation(), 0).start();
        }
    }

    //Confirm then roll.
    public void rollToControlNPN(int playerNation, int nonPlayerNation, boolean hasConsent) {
        if (user.getNation() == playerNation) {
            NationInstance npn = getNationInstance(nonPlayerNation);
            NationInstance userNation = getNationInstance(getUserNation());
            boolean isControlled = npn.isControlledNation();

//            if (If team member controls NPN, cannot attempt control without their consent) {}todo
            int choice = MessageDialog.confirmControlNPN(getDisplay().getMap(), nonPlayerNation, isControlled, hasConsent, npn.getControllingUser());

            if (choice == JOptionPane.YES_OPTION) {
                userNation.minusPaps(2);
                display.refreshNationSummary();

                int roll;
                if ( isControlled && hasConsent ) //add 4 to roll.
                    roll = Dice.rollTwoDSix(4);
                else
                    roll = Dice.rollTwoDSix();

                System.out.println("GameController.rollToControlNPN roll: " + roll);
                if ( roll >= userNation.getDiplomaticRating(nonPlayerNation) ) {
                    //success
                    new SetupDiplomaticRoundThread(getGameId(), EventLogger.CONTROL_NPN, getUserNation(), nonPlayerNation).start();
                    new NationToControlThread(getGameId(), -1, nonPlayerNation, getUserNation(), true).start();

                    setupDiplomaticRound(EventLogger.CONTROL_NPN, getUserNation(), nonPlayerNation);
                    doControlNonPlayerNation(-1, nonPlayerNation, getUserNation(), false);
                } else //failure
                    new EventFailControlNPNThread(getGameId(), user.getNation(), nonPlayerNation, game.getMonth(), game.getYear()).start();
            }
        }
    }

    public void doReleaseNPN(int nonPlayerNation, int playerNation) {
        User releasingUser = game.getUserByNation(playerNation);

        if (releasingUser.getNation() == user.getNation())
            PoliticalUtilities.doReleaseNPN(game.getNation(nonPlayerNation), game.getUserByNation(playerNation), user, game.getMonth(), game.getYear());
        else
            PoliticalUtilities.doReleaseNPN(game.getNation(nonPlayerNation), game.getUserByNation(playerNation), null, game.getMonth(), game.getYear());

        String eventString = EventLogger.addReleaseNPNEvent(releasingUser.getUserName(), playerNation, nonPlayerNation, game.getMonth(), game.getYear());
        display.displayEvent(eventString);
    }

    public void failControlNPNEvent(int userNation, int nonPlayerNation, int month, int year) {
        String userName = game.getUserByNation(userNation).getUserName();
        String eventString = EventLogger.addFailControlNPNEvent(userName, userNation, nonPlayerNation, month, year);
        display.displayEvent(eventString);
    }

    public boolean isDuringWarDecControlNPN(int doNation) {
        //Come from formAllianceKa?  Is in DiplomaticRound?  Is it DECLARE_WAR or CONTROL_NPN?  Is it Diplomatic Turn of requester?  Is nation controlled by User?
        return game.isDiplomaticRound() && game.getDiplomaticToNation() == doNation && !game.hasRoundStarted()
                && (game.getDiplomaticActionType() == EventLogger.DECLARE_WAR || game.getDiplomaticActionType() == EventLogger.CONTROL_NPN);
    }

    public void allianceRejected(int requester, int nationTwo) {
        if (isNationControlledByUser(requester)) {
            MessageDialog.allianceRejected(display.getMap(), requester, nationTwo);
            if (isDuringWarDecControlNPN(requester))
                controllNPNFormAlliance(requester, nationTwo);
        }
    }

    public void doFormAlliance(int requester, int nationTwo) {
        setupDiplomaticRound(EventLogger.FORM_ALLIANCE, requester, nationTwo);
        getNationInstance(requester).formAlliance(nationTwo);
        getNationInstance(nationTwo).formAlliance(requester);
        display.refreshPoliticalStatus();

        String eventString = EventLogger.addFormAllianceEvent(requester, nationTwo, game.getMonth(), game.getYear());
        display.displayEvent(eventString);

        if (isDuringWarDecControlNPN(requester))
            if (isNationControlledByUser(requester))
                controllNPNFormAlliance(requester, nationTwo);
        else
            nextDiplomatTurn();
    }

    public void doConcludeArmistice(int requester, int nationTwo) {
        setupDiplomaticRound(EventLogger.CONCLUDE_ARMISTICE, requester, nationTwo);
        PoliticalUtilities.doConcludeArmistice(getNationInstance(requester), getNationInstance(nationTwo));
        display.refreshPoliticalStatus();

        String eventString = EventLogger.addArmisticeEvent(requester, nationTwo, game.getMonth(), game.getYear());
        display.displayEvent(eventString);

        if ( isDuringWarDecControlNPN(requester) )
            if (isNationControlledByUser(requester))
                controlNPNConcludeArmistices(requester);
        else
            nextDiplomatTurn();
    }

    public void doBreakAlliance(int breakingNation, int alliedNation) {
        setupDiplomaticRound(EventLogger.BREAK_ALLIANCE, breakingNation, alliedNation);
        PoliticalUtilities.doBreakAlliance(getNationInstance(breakingNation), getNationInstance(alliedNation));
        display.refreshPoliticalStatus();

        String eventString = EventLogger.addBreakAllianceEvent(breakingNation, alliedNation, game.getMonth(), game.getYear());
        display.displayEvent(eventString);

        nextDiplomatTurn();
    }

    public void doGrantPassage(int grantingNation, int receivingNation, boolean isVoluntary) {
        setupDiplomaticRound(EventLogger.GRANT_PASSAGE, grantingNation, receivingNation);
        PoliticalUtilities.doGrantPassage(getNationInstance(grantingNation), receivingNation, isVoluntary);
        display.refreshPoliticalStatus();

        String eventString = EventLogger.addRightOfPassageEvent(grantingNation, receivingNation, game.getMonth(), game.getYear());
        display.displayEvent(eventString);

        nextDiplomatTurn();
    }

    public void doRescindPassage(int rescindingNation, int losingNation) {
        setupDiplomaticRound(EventLogger.RESCIND_PASSAGE, rescindingNation, losingNation);
        PoliticalUtilities.doRescindPassage(getNationInstance(rescindingNation), losingNation);
        display.refreshPoliticalStatus();

        String eventString = EventLogger.addRescindRightsEvent(rescindingNation, losingNation, game.getMonth(), game.getYear());
        display.displayEvent(eventString);

        nextDiplomatTurn();
    }

    public void nextDiplomatTurn() {
        System.out.println("GameController:nextDiplomatTurn " + game.isDiplomaticRound());
        if (game.isDiplomaticRound()) {
            nextDiplomaticTurn();
            if (!game.hasRoundStarted()) {
                game.startDiplomaticRound();
                display.refreshGameStateInfo();
            }
            System.out.println("NextDiplomatTurn - nationTurn , startingNation " + game.getDiplomaticNationTurn() + ", " + game.getDiplomaticStartingNation() +
                " isDiploRound: " + game.isDiplomaticRound());
            if (game.getDiplomaticNationTurn() == game.getDiplomaticStartingNation()) {
                game.endDiplomaticRound();
                //After nation takes an action during the start of their turn
                if ( game.getStep() == GameInstance.START_OF_TURN)
                    game.setStep(GameInstance.SEA_MOVEMENT);
                display.refreshGameStateInfo();
            } else
                display.diplomaticRoundDialog();
        }
    }

    public boolean isTurnToPlaceUnits(int placingNation) {
        if ( game.getStep() == GameInstance.DIPLOMATIC_ROUND )
            if ( game.getDiplomaticActionType() == EventLogger.DECLARE_WAR || game.getDiplomaticActionType() == EventLogger.CONTROL_NPN )
                return game.getDiplomaticNationTurn() == placingNation;

        return game.isBlindSetup() || (game.getTurn() == placingNation);
    }

    public void processUnitPlacement(GamePlaceUnitsPacket pup) {
        if (pup.getOwningNation() != user.getNation() && !user.getControlledNPNs().contains(pup.getOwningNation())) {
            if ( game.isBlindSetup() && game.isStartOfGame() ) //If blind setup and start of game, store the AUP until all nations have placed units.
                nationsPUP.add(pup);
            else {
                boolean unitsAdded = placeUnits(pup);
                if (!unitsAdded) {
                    System.err.println("processUnitPlacement(pup) - Failed to add units");
                    GameLogger.log("processUnitPlacement(pup) - Failed to add units");
                } else
                    display.refreshTerritoryInfo();
            }
        } else {
            System.out.println("owningNation do not place units " + user.getNation() + " == " + pup.getOwningNation());
        }
    }

    public void processUnitPlacement(HashMap<Region, ArrayList<MilitaryUnit>> placementMap) {
        boolean unitsAdded;
        PlaceUnitsResponseThread placeUnitsRT = new PlaceUnitsResponseThread(gameId, this.user.getNation());
        for (Region region : placementMap.keySet() ) {
            unitsAdded = UnitController.moveAllUnits(placementMap.get(region), region);
            if (unitsAdded) {
                placeUnitsRT.addPacket(gameId, game.getMap().getRegionIndex(region), region.getType(), placementMap.get(region));
            } else {
                GameLogger.log("Failed to add units to the following region, " + region.toString() + ", num of units (" + placementMap.get(region).size() + ")");
                System.err.println("Failed to add units to the following region, " + region.toString() + ", num of units (" + placementMap.get(region).size() + ")");
            }
        }
        display.refreshTerritoryInfo();
        placeUnitsRT.sendPackets();
    }

    private boolean placeUnits(GamePlaceUnitsPacket pup) {
        Region region = game.getMap().getRegionFromIndex(pup.getRegionIndex(), pup.getRegionType());
        return UnitController.moveAllUnits(Utilities.getUnitsFromPacket(pup), region);
    }

    /*
     * When blind setup is on this method will be called once all nations have placed units.
     */
    private void blindSetupPlaceUnits() {
        for (GamePlaceUnitsPacket pup : nationsPUP) {
            System.out.println("GameController - blindSetup packets");
            if (!placeUnits(pup)) {
                System.err.println("blindSetupPlaceUnits - failed to add units; nation: " + pup.getOwningNation()  + " regionType: " + pup.getRegionType() + " regionIndex " + pup.getRegionIndex());
                GameLogger.log("blindSetupPlaceUnits - failed to add units; nation: " + pup.getOwningNation()  + " regionType: " + pup.getRegionType() + " regionIndex " + pup.getRegionIndex());
            }
        }
        nationsPUP.clear();
        display.refreshTerritoryInfo();
    }

    /*
     * Add nation to the list of nations who have placed units.
     * Then check if all nations have placed, if so reset nationPlaced and add all units for all nations
     * to the clients version of the map.
     */
    public void allUnitsPlaced(int nation) {
        System.out.println("GameController.allUnitsPlaced: " + nation + " nationsPlaced.size(): " + nationsPlaced.size());

        if ( game.isStartOfGame() || game.getStep() == GameInstance.PRODUCTION_ROUND ) {
            nextTurn();
            nationsPlaced.add(nation);

            System.out.println("GameController.allUnitsPlaced: nationsPlaced.size(): " + nationsPlaced.size() + " game.getControlledNations().size(): " + game.getControlledNations().size());
            if (nationsPlaced.size() == game.getControlledNations().size()) {
                System.out.println("GameController - nationsPlaced.size() == game.getControlledNations().size()");
                boolean allNationsReady = true;
                //Should be one integer for each nation, if not probably an error.
                for (Integer n : game.getControlledNations()) {
                    if ( !nationsPlaced.contains(n) ) {
                        try {
                            allNationsReady = false;
                            throw new Exception();
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.err.println("GameController.allUnitsPlaced:  Something went wrong adding units - userSize: " + game.getUsers().size() + " nationPlaced.size(): " + nationsPlaced.size());
                            GameLogger.log("GameController.allUnitsPlaced - userSize: " + game.getUsers().size() + " nationPlaced.size(): " + nationsPlaced.size() + " Msg: "  + e.getMessage());
                        }
                    }
                }
                if ( allNationsReady ) {
                    System.out.println("GameController - allNationsReady");
                    if (game.isBlindSetup())
                        blindSetupPlaceUnits();
                    nationsPlaced.clear();
                    String eventString = EventLogger.addGameStartEvent(game.getMonth(), game.getYear());
                    display.displayEvent(eventString);
                    //todo take it from here
                    //START THE GAME!!!!!!!
                    game.setStep(GameInstance.START_OF_TURN);
                    display.refreshGameStateInfo();
                }
            }
        } else if ( game.getStep() == GameInstance.DIPLOMATIC_ROUND ) {
            //This is the end of the steps needed to ControlNPN
            if (game.getDiplomaticActionType() == EventLogger.DECLARE_WAR || game.getDiplomaticActionType() == EventLogger.CONTROL_NPN) {
                display.refreshChatCursors();
                setDiplomaticTurn(game.getDiplomaticStartingNation());
                nextDiplomatTurn();
            }
        }
        // else {
            //Proceed to next step.
        // }
    }

    /*
     * Create any units purchased.
     * Add those units to User Nation's military and subtract cost from production points.
     * Send the list of units to display controller for placement (Do not include PAPs)
     * Send the list of units to server so everyone can add those units to the right nation.
     */
    public void finalizeUnitPurchases(ArrayList<Integer> purchases, int nationNumber, boolean hasBeenControlled) {
        ArrayList<MilitaryUnit> unitsToPlace = new ArrayList<MilitaryUnit>();

        for (Integer u : purchases) {
            switch (u) {
                case MilitaryUnit.GENERAL: unitsToPlace.add(new General(nationNumber)); break;
                case MilitaryUnit.ADMIRAL: unitsToPlace.add(new Admiral(nationNumber)); break;
                case MilitaryUnit.INFANTRY: unitsToPlace.add(new Infantry(nationNumber)); break;
                case MilitaryUnit.ELITE_INFANTRY: unitsToPlace.add(new EliteInfantry(nationNumber)); break;
                case MilitaryUnit.CAVALRY: unitsToPlace.add(new Cavalry(nationNumber)); break;
                case MilitaryUnit.HEAVY_CAVALRY: unitsToPlace.add(new HeavyCavalry(nationNumber)); break;
                case MilitaryUnit.IRREGULAR_CAVALRY: unitsToPlace.add(new IrregularCavalry(nationNumber)); break;
                case MilitaryUnit.ARTILLERY: unitsToPlace.add(new Artillery(nationNumber)); break;
                case MilitaryUnit.HORSE_ARTILLERY: unitsToPlace.add(new HorseArtillery(nationNumber)); break;
                case MilitaryUnit.MILITIA: unitsToPlace.add(new Militia(nationNumber)); break;
                case MilitaryUnit.NAVAL_SQUADRON: unitsToPlace.add(new NavalSquadron(nationNumber)); break;
//                case MilitaryUnit.NATIONAL_HERO: unitsToPlace.add(new (nationNumber)); break;
                case MilitaryUnit.PAP:
                    getNationInstance(nationNumber).minusProductionPoints(MilitaryUnit.getCost(u, nationNumber));
                    getNationInstance(nationNumber).addPap();
                    break;
            }
        }

        for (MilitaryUnit u : unitsToPlace) {
            getNationInstance(nationNumber).addMilitaryUnit(u);
            getNationInstance(nationNumber).minusProductionPoints(u.getCost());
        }

        new AddUnitsResponseThread(gameId).addNewUnits(purchases, nationNumber);
        if (!hasBeenControlled)
            unitsToPlace.addAll(getNationInstance(nationNumber).getMilitary());
        display.showPlacePanel(unitsToPlace, getPlaceableRegions(nationNumber));
    }

    public void doPurchaseUnits(GameAddUnitsPacket addUnits, int nationNumber) {
        if ( !isNationControlledByUser(nationNumber) )
            GameUtilities.doPurchaseUnits(addUnits, getNationInstance(nationNumber));
    }

    private void nextTurn() {
        game.nextTurn();
        display.refreshGameStateInfo();
    }

    private void setDiplomaticTurn(int nation) {
        game.setDiplomaticNationTurn(nation);
        display.refreshGameStateInfo();
    }

    private void nextDiplomaticTurn() {
        game.nextDiplomaticTurn();
        display.refreshGameStateInfo();
    }

    public ArrayList<Integer> getControlledNations() {
        ArrayList<Integer> nations = new ArrayList<Integer>();
        for (Integer n : GameUtilities.getAllNationsAsInteger())
            if ( game.isControlledNation(n) )
                nations.add(n);

        return nations;
    }

    public ArrayList<MilitaryUnit> getUnitsInRegion(String regionName) {
        return game.getMap().getRegion(regionName).getOccupyingUnits();
    }

    public Region getLandRegion(int index) { return game.getMap().getRegionFromIndex(index, Region.LAND_REGION); }
    public Region getPortRegion(int index) { return game.getMap().getRegionFromIndex(index, Region.PORT_REGION); }
    public Region getSeaRegion(int index) { return game.getMap().getRegionFromIndex(index, Region.SEA_REGION); }
    public Region getRegion(String regionName) { return game.getMap().getRegion(regionName); }
    public ArrayList<LandRegion> getNationHomelands(int nation) { return game.getNation(nation).getHomelands(); }
    public ArrayList<LandRegion> getNationTerritory(int nation) { return game.getNation(nation).getTerritory(); }
    public ArrayList<Port> getNationHomelandPorts(int nation) { return game.getNation(nation).getHomelandPorts(); }
    public ArrayList<Region> getAllRegions() { return game.getMap().getAllRegions(); }
    public boolean isNation(int nation) { return GameUtilities.getAllNationsAsInteger().contains(nation); }

    /**
     * If Start of game, can place in all regions owned by nations (S.X only), otherwise only homelands.
     * All homeland ports that have not built a ship in last three (3) production rounds.
     * No units may be placed in regions occupied by enemy troops or that is in uprising.
     * Regions must be under native control.
     * @param nation - the nation placing units.
     * @return an ArrayList of the regions the userNation can place units in.
     */
    public ArrayList<Region> getPlaceableRegions(int nation) {
        ArrayList<Region> placeableRegions = new ArrayList<Region>();

        //Land Regions              todo scenario
        if (game.isStartOfGame() && game.getGameScenario() == LobbyConstants.X)
            placeableRegions.addAll(getNationTerritory(nation));
        else
            placeableRegions.addAll(getNationInstance(nation).getPlaceableLandRegions());

        //Ports
        placeableRegions.addAll(getNationInstance(nation).getPlaceablePortRegions());

        return placeableRegions;
    }

    /*
     * When word is recieved from server, flag the game as ready and set up the game for first turn placement.
     */
    public void makeGameReady(byte[] gameId) {
        if (Utilities.compareLobbyIds(this.gameId,gameId) ) {
            isGameReady = true;
            EventLogger.createEventFile(gameId);
            political = new PoliticalController(this, display);
            nationsPlaced = Collections.synchronizedSet( new HashSet<Integer>(getAllUsers().size()) );
        }
    }

    public boolean isGameReady() { return isGameReady; }
    public byte[] getGameId() { return gameId; }
    public NationInstance getNationInstance(int nation) { return game.getNation(nation); }

    //User Methods
    public int getUserNation() { return user.getNation(); }
    public String getUserLeader() { return user.getLeader(); }
    public String getUserName() { return user.getUserName(); }
    public ArrayList<Integer> getUserControlledNPNs() { return user.getControlledNPNs(); }
    public boolean isNationControlledByUser(int nation) { return user.getNation() == nation || user.getControlledNPNs().contains(nation); }

    //Misc
    public void setDisplayController(DisplayController display) { this.display = display; }
    public DisplayController getDisplay() { return display; }

    public ArrayList<User> getAllUsers() { return game.getUsers(); }
    public int getChatNation() { return chatNation; }

    private User user;
    private byte[] gameId;
    public GameInstance game;
    private GameClientThread gct;
    private DisplayController display;
    public PoliticalController political;
    private int chatNation;
    private boolean isGameReady;
    private Set<Integer> nationsPlaced;
    private boolean hasBeenControlled;
    private List<GamePlaceUnitsPacket> nationsPUP = new ArrayList<GamePlaceUnitsPacket>();
    private ArrayList<Integer> congressNations = new ArrayList<Integer>();

    public static final int MAX_CHAT_LENGTH = 250;
}