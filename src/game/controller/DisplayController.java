package game.controller;

import game.gui.MainScreen;
import game.gui.PurchaseMenu;
import game.gui.menu.*;
import game.gui.placement.PlacementFrame;

import javax.swing.*;

import game.gui.info.NationInfoDialog;
import game.util.*;
import game.controller.Unit.MilitaryUnit;
import game.controller.Region.Region;
import game.controller.Region.Port;
import game.controller.Region.SeaRegion;
import game.client.political.*;
import game.client.political.CongressActionThread;
import lobby.controller.Nation;
import shared.commCards.CommCardFrame;
import shared.controller.LobbyConstants;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * DisplayController.java  Date Created: Oct 24, 2012
 *
 * Purpose: To control what is displayed on the screen.
 *
 * Description: This class shall control what the user sees on screen.
 *
 * @author Chrisb
 */
public class DisplayController {
    public final static Color FRENCH_COLOR = Color.BLUE;
    public final static Color BRITISH_COLOR = Color.RED;
    public final static Color PRUSSIAN_COLOR = Color.PINK;
    public final static Color RUSSIAN_COLOR = Color.ORANGE;
    public final static Color OTTOMAN_COLOR = Color.YELLOW;
    public final static Color AUSTRIAN_COLOR = Color.GREEN;
    public final static Color SPANISH_COLOR = Color.CYAN;
    public final static Color NEUTRAL_COLOR = Color.DARK_GRAY;

    public DisplayController(JFrame frame, GameController controller) {
        this.frame = frame;
        this.controller = controller;

        globalChat = "";
        frenchChat = "";
        britishChat = "";
        prussianChat = "";
        russianChat = "";
        ottomanChat = "";
        austrianChat = "";
        spanishChat = "";
    }

    public void setInitContentPane() {
        setContentPane(MAIN_SCREEN);
    }

    private void setContentPane(int screen) {
        switch (screen) {
            case MAIN_SCREEN: frame.setContentPane(mainScreen);
                break;
        }
    }

    public void init() {
        //init based on Scenario/Mode todo secondary nations
        mainScreen = new MainScreen(controller, this);
        mainScreen.chat.chatChooser.setupCursors();
        gameMenuBar = new GameMenuBar(controller, this);
        frame.setJMenuBar(gameMenuBar);
        userNation = controller.getUserNation();
        setInitContentPane();
        if (controller.game.getGameScenario() == LobbyConstants.X)
            showPlacePanel( controller.getNationInstance(userNation).getMilitary(), controller.getPlaceableRegions(userNation) );
    }

    public void quit() {
        if (MessageDialog.quitDialog(getMap()) == JOptionPane.YES_OPTION) {
            System.out.println("quitting");
            controller.save();
            for (Frame f : Frame.getFrames())
                f.dispose();
        }
    }

    /**
     * Save the old nation chat window. Change to the new nation chat window.
     * Update the chatChooser.
     *
     * @param oldNation - nation chat window user was viewing.
     * @param newNation - nation chat window user is now viewing.
     */
    public void changeChatNation(int oldNation, int newNation) {
        mainScreen.chat.chatWindow.changeChatNation(newNation);
        mainScreen.chat.chatChooser.setChatNation(oldNation, newNation);
        refresh();
    }

    public void saveChatWindow(int nation, String log) {
//        if (log.length() > ) todo max length of log file?

        switch (nation) {
            case Nation.GLOBAL: globalChat = log; break;
            case Nation.FRANCE: frenchChat = log; break;
            case Nation.GREAT_BRITAIN: britishChat = log; break;
            case Nation.PRUSSIA: prussianChat = log; break;
            case Nation.RUSSIA: russianChat = log; break;
            case Nation.OTTOMANS: ottomanChat = log; break;
            case Nation.AUSTRIA_HUNGARY: austrianChat = log; break;
            case Nation.SPAIN: spanishChat = log; break;
        }
    }

    public String loadChatWindow(int nation) {
        switch (nation) {
            case Nation.GLOBAL: return globalChat;
            case Nation.FRANCE: return frenchChat;
            case Nation.GREAT_BRITAIN: return britishChat;
            case Nation.PRUSSIA: return prussianChat;
            case Nation.RUSSIA: return russianChat;
            case Nation.OTTOMANS: return ottomanChat;
            case Nation.AUSTRIA_HUNGARY: return austrianChat;
            case Nation.SPAIN: return spanishChat;
        }
        return "Could not find chat log related to nation: " + nation;
    }

    public void receiveChatMsg(int fromNation, String name, String msg) {
        addChatMsg(fromNation, name, msg);
        mainScreen.chat.chatChooser.messageReceived(fromNation);
    }

    public void addChatMsg(int nation, String name, String msg) {
        switch (nation) {
            case Nation.GLOBAL:
                if (globalChat.length() > 0)
                    globalChat = globalChat.concat("\n");
                globalChat = globalChat.concat(name + ": " + msg);
                break;
            case Nation.FRANCE:
                if (frenchChat.length() > 0)
                    frenchChat = frenchChat.concat("\n");
                frenchChat = frenchChat.concat(name + ": " + msg);
                break;
            case Nation.GREAT_BRITAIN:
                if (britishChat.length() > 0)
                    britishChat = britishChat.concat("\n");
                britishChat = britishChat.concat(name + ": " + msg);
                break;
            case Nation.PRUSSIA:
                if (prussianChat.length() > 0)
                    prussianChat = prussianChat.concat("\n");
                prussianChat = prussianChat.concat(name + ": " + msg);
                break;
            case Nation.RUSSIA:
                if (russianChat.length() > 0)
                    russianChat = russianChat.concat("\n");
                russianChat = russianChat.concat(name + ": " + msg);
                break;
            case Nation.OTTOMANS:
                if (ottomanChat.length() > 0)
                    ottomanChat = ottomanChat.concat("\n");
                ottomanChat = ottomanChat.concat(name + ": " + msg);
                break;
            case Nation.AUSTRIA_HUNGARY:
                if (austrianChat.length() > 0)
                    austrianChat = austrianChat.concat("\n");
                austrianChat = austrianChat.concat(name + ": " + msg);
                break;
            case Nation.SPAIN:
                if (spanishChat.length() > 0)
                    spanishChat = spanishChat.concat("\n");
                spanishChat = spanishChat.concat(name + ": " + msg);
                break;
        }
        mainScreen.chat.chatWindow.messageReceived(nation);
        mainScreen.getPanelHeightWidths();
    }

    public void showCommCard(int nation) {
        System.out.println("show CommCard " + nation);
        if ( commFrame != null ) {
            commScreenX = commFrame.getX();
            commScreenY = commFrame.getY();
            commScreenCoordinates = true;
            commFrame.dispose();
        }
        commFrame = new CommCardFrame(nation);

        if (commScreenCoordinates) {
            commFrame.setLocation(commScreenX, commScreenY);
        } else {
            int x = frame.getWidth()/2 - commFrame.getWidth()/2;
            int y = frame.getHeight()/2 - commFrame.getWidth()/2;
            x = x <= 0 ? 0 : x;
            y = y <= 0 ? 0 : y;

            commFrame.setLocation(x,y);
        }
    }

    public void showNationSummary(int nation) {
        System.out.println("show NationInfoDialog " + nation);
        if (nationSummaryDialog != null) {
            nationSummX = nationSummaryDialog.getX();
            nationSummY = nationSummaryDialog.getY();
            nationSummCoordinates = true;
            nationSummaryDialog.dispose();
        }
        nationSummaryDialog = new NationInfoDialog(controller.getNationInstance(nation), controller);

        if (nationSummCoordinates) {
            nationSummaryDialog.setLocation(nationSummX, nationSummY);
        } else {
            int x = frame.getWidth()/2 - nationSummaryDialog.getWidth()/2;
            int y = frame.getHeight()/2 - nationSummaryDialog.getWidth()/2;
            nationSummaryDialog.setLocation(x,y);
        }
        nationSummaryDialog.pack();
    }

    public void showPurchasePanel(int nationNumber) {
        showPurchasePanel(nationNumber, false);
    }

    public void showPurchasePanel(int nationNumber, boolean hasBeenControlled) {
        System.out.println("show Purchase Panel");
        if (purchaseDialog != null) {
            purchaseX = purchaseDialog.getX();
            purchaseY = purchaseDialog.getY();
            purchaseCoordinates = true;
            purchaseDialog.dispose();
        }
        purchaseDialog = new PurchaseMenu(controller, controller.getNationInstance(nationNumber), hasBeenControlled);

        if (purchaseCoordinates) {
            purchaseDialog.setLocation(purchaseX, purchaseY);
        } else {
            int x = frame.getWidth()/2 - purchaseDialog.getWidth()/2;
            int y = frame.getHeight()/2 - purchaseDialog.getWidth()/2;
            purchaseDialog.setLocation(x,y);
        }
        purchaseDialog.pack();
    }

    public void showPlacePanel(ArrayList<MilitaryUnit> unitsToPlace, ArrayList<Region> regionsToPlace) {
        System.out.println("show Placement Panel");
        if (placementDialog != null) {
            placementX = placementDialog.getX();
            placementY = placementDialog.getY();
            placementCoordinates = true;
            placementDialog.dispose();
        }
        placementDialog = new PlacementFrame(controller, this, unitsToPlace, regionsToPlace);

        if (placementCoordinates) {
            placementDialog.setLocation(placementX, placementY);
        } else {
            int x = frame.getWidth()/2 - placementDialog.getWidth()/2;
            int y = frame.getHeight()/2 - placementDialog.getWidth()/2;
            placementDialog.setLocation(x,y);
        }
        placementDialog.pack();
    }

    public void showEventLog() {
        if (eventLogDialog != null) {
            eventLogX = eventLogDialog.getX();
            eventLogY = eventLogDialog.getY();
            eventLogCoordinates = true;
            eventLogDialog.dispose();
        }
        eventLogDialog = new EventLogMenu();

        if (eventLogCoordinates) {
            eventLogDialog.setLocation(eventLogX, eventLogY);
        } else {
            int x = frame.getWidth()/2 - eventLogDialog.getWidth()/2;
            int y = frame.getHeight()/2 - eventLogDialog.getWidth()/2;

            eventLogDialog.setLocation(x,y);
        }
        eventLogDialog.pack();
    }

    public void showPoliticalStatus() {
        if (politicalStatusDialog != null) {
            politicalX = politicalStatusDialog.getX();
            politicalY = politicalStatusDialog.getY();
            politicalStatusCoordinates = true;
            politicalStatusDialog.dispose();
        }
        politicalStatusDialog = new PoliticalStatusMenu(controller, this);

        if (politicalStatusCoordinates) {
            politicalStatusDialog.setLocation(politicalX, politicalY);
        } else {
            int x = frame.getWidth()/2 - politicalStatusDialog.getWidth()/2;
            int y = frame.getHeight()/2 - politicalStatusDialog.getWidth()/2;

            politicalStatusDialog.setLocation(x,y);
        }
        politicalStatusDialog.pack();
    }

    public void showDefinitionsPanel() {
        if (definitionsDialog != null) {
            definitionX = definitionsDialog.getX();
            definitionY = definitionsDialog.getY();
            definitionCoordinates = true;
            definitionsDialog.dispose();
        }
        definitionsDialog = new DefinitionsMenu(controller, this);

        if (definitionCoordinates) {
            definitionsDialog.setLocation(definitionX, definitionY);
        } else {
            int x = frame.getWidth()/2 - definitionsDialog.getWidth()/2;
            int y = frame.getHeight()/2 - definitionsDialog.getWidth()/2;

            definitionsDialog.setLocation(x,y);
        }
        definitionsDialog.pack();
    }

    public void showAboutPanel() {
        JDialog aboutMenu = new AboutMenu();
        Point p = frame.getLocation();
        int x = p.x + getMap().getWidth()/2 - aboutMenu.getWidth();
        int y = p.y + getMap().getHeight()/2 - aboutMenu.getWidth();
        aboutMenu.setLocation(x,y);
        aboutMenu.pack();
    }

    public void showHelpDoc() {
        //todo show help doc
    }

    public void showCongressMenu(int sueingNation, int congressNation, int turnIndex) {
        if (congressDialog != null) {
            congressX = congressDialog.getX();
            congressY = congressDialog.getY();
            congressCoordinates = true;
            congressDialog.dispose();
        }
        congressDialog = new CongressMenu(sueingNation, congressNation, turnIndex);

        if (congressCoordinates) {
            congressDialog.setLocation(congressX, congressY);
        } else {
            int x = frame.getWidth()/2 - congressDialog.getWidth()/2;
            int y = frame.getHeight()/2 - congressDialog.getWidth()/2;

            congressDialog.setLocation(x,y);
        }
        congressDialog.pack();
    }

    public void showPeaceCongressOptions(int sueingNation, int congressNation, int turnIndex) {
        NationInstance sueNation = controller.getNationInstance(sueingNation);
        NationInstance cNation = controller.getNationInstance(congressNation);

        //1. If, sueingNation have regions outside their homelands?
        if ( PoliticalUtilities.hasRegionsOutsideHomeland(sueNation) )
            if ( cNation.getPaps() > 0)  //Restore/Annex nonHomeland costs 1.
                showCongressMenu(sueingNation, congressNation, turnIndex);
        else {
            //2. If, SN is Spain or CN hasn't annexed a HL this congress and has two PAP.
            if ( (sueingNation == Nation.SPAIN || !cNation.hasAnnexedHomelandThisCongress()) && cNation.getPaps() > 1 )
                showCongressMenu(sueingNation, congressNation, turnIndex);
            //3. If, there are eligible homelands to free serfs?
            else if ( PoliticalUtilities.getRegionsToFreeSerfs(sueNation, cNation).size() > 0 && cNation.getPaps() > 0 )
                showCongressMenu(sueingNation, congressNation, turnIndex);
            else {
                //4. Has annexed homeland this congress - PASS
                if (cNation.hasAnnexedHomelandThisCongress() && sueingNation != Nation.SPAIN) {
                    MessageDialog.congressAlreadyAnnexedHomeland(getMap(), congressNation);
                    new CongressActionThread(controller.getGameId(), sueingNation, turnIndex, congressNation).start();
                } //5. Not enough PAP - PASS
                else {
                    MessageDialog.congressNotEnoughPap(getMap(), congressNation, sueingNation);
                    new CongressActionThread(controller.getGameId(), sueingNation, turnIndex, congressNation).start();
                }
            }
        }
    }

    public void moveNPNUnits(int sueNation, ArrayList<Integer> regionsToMove, ArrayList<Integer> portsToMoveInTo, ArrayList<Integer> portsToMoveOutOf) {
        NationInstance sueingNation = controller.getNationInstance(sueNation);

        if ( sueingNation.getLastNationToControl() == userNation ) {
            if (regionsToMove != null && regionsToMove.size() > 0) {
                ArrayList<Region> placeableRegions = new ArrayList<Region>();
                for (int i : regionsToMove)
                    placeableRegions.add(controller.getLandRegion(i));
                showPlacePanel(sueingNation.getArmy(), placeableRegions);
            }

            if ( portsToMoveInTo != null && portsToMoveInTo.size() > 0 ) {
                ArrayList<Region> placeableRegions = new ArrayList<Region>();
                for (int i : portsToMoveInTo)
                    placeableRegions.add(controller.getPortRegion(i));
                showPlacePanel(sueingNation.getNavy(), placeableRegions);
            } else if ( portsToMoveOutOf != null && portsToMoveOutOf.size() > 0 ) {
                for (int i : portsToMoveOutOf) {
                    //Do a placement panel for each port with NavalSquadrons that need to move out
                    ArrayList<Region> seasToMoveTo = new ArrayList<Region>();
                    ArrayList<MilitaryUnit> unitsToMove = new ArrayList<MilitaryUnit>();
                    Port p = ((Port)controller.getPortRegion(i));

                    for (Region s : p.getSeaAdjacencies())
                        seasToMoveTo.add(s);

                    for (MilitaryUnit u : p.getOccupyingUnits())
                        if (u.getOwningNation() == sueNation)
                            unitsToMove.add(u);

                    showPlacePanel(unitsToMove, seasToMoveTo);
                }
            }
        }

        //Back to Ending Congress
        
    }

    /**
     * Displays the list of nations based on the action type and eligiblility of each nation.
     * If user controls more than one nations that can take the action, first display that list of nations.
     * @param type - action type that user is trying to take.
     * @param doNation - the nation that will be doing the action, used when a user can take actions with more than one nation.
     */
    public void showPickANationPanel(int type, int doNation) {
        System.out.println("showPickANationPanel");

        ArrayList<Integer> toNations = new ArrayList<Integer>();
        ArrayList<Integer> uncontrolledNPNs = new ArrayList<Integer>();
        ArrayList<Integer> userControlledNations = new ArrayList<Integer>();

        if (type == SUMMARY || type == COMMANDER) {
            toNations = GameUtilities.getAllNationsAsInteger();
        } else if ( controller.isNation(doNation) ) {
            //Pick toNations based on doNation
            for (Integer nTo : GameUtilities.getAllNationsAsInteger())
                if ( controller.political.canTakePoliticalAction(type, doNation, nTo) )
                    toNations.add(nTo);
        } else {
            //If doNation is not a nation, get all nations user controls that can take actions.
            for (Integer toNation : GameUtilities.getAllNationsAsInteger())
                if ( controller.political.canTakePoliticalAction(type, userNation, toNation) && !userControlledNations.contains(userNation) )
                    userControlledNations.add(userNation);
            for (Integer nDo : controller.getUserControlledNPNs())
                for (Integer nTo : GameUtilities.getAllNationsAsInteger())
                    if ( controller.political.canTakePoliticalAction(type, nDo, nTo) && !userControlledNations.contains(nDo) )
                        userControlledNations.add(nDo);
            if (userControlledNations.size() == 1) {
                //If only one eligible userControlledNation, use that as doNation.
                for (Integer nTo : GameUtilities.getAllNationsAsInteger())
                    if ( controller.political.canTakePoliticalAction(type, userControlledNations.get(0), nTo) )
                        toNations.add(nTo);
            }
        }

        //Check if action can be done to uncontrolledNations, only Declare War and ControlNPN.
        if ( type == EventLogger.DECLARE_WAR ) {
            for (Integer n : GameUtilities.getAllNationsAsInteger()) {
                if (!controller.getNationInstance(n).isControlledNation()
                        && controller.political.canTakePoliticalAction(type, userNation, n)) {
                    uncontrolledNPNs.add(n);
                    toNations.remove(n);
                }
            }
        }

        //Display list of doNations, or toNations or Error Message.
        if ( userControlledNations.size() > 1 ) {
            System.out.println("userControlledNations size > 1 -- " + userControlledNations.size());
            //Display possible doNations.
            String title = "Your Controlled Nations";
            userControlledNations.remove((Integer)userNation);
            JDialog dblPickANation = new PickANationMenu(userNation, userControlledNations, this, type, title);

            Point p = frame.getLocation();
            int x = p.x + getMap().getWidth()/2 - dblPickANation.getWidth()/2;
            int y = p.y + getMap().getHeight()/2 - dblPickANation.getWidth()/2;
            dblPickANation.setLocation(x,y);
            dblPickANation.pack();
        } else if ( toNations.size() > 0 ) {
            System.out.println("toNations size > 0");
            //Display to Nations
            if ( toNations.size() == 1 && uncontrolledNPNs.size() == 0 ) {
                //If only 1 toNation, pick nation for user.
                if ( controller.isNation(doNation) )
                    nationPicked(type, doNation, toNations.get(0));
                else
                    nationPicked(type, toNations.get(0));
            } else {
                JDialog pickANation;
                if ( controller.isNation(doNation) ) {
                    System.out.println("doNation isNation");
                    pickANation = new PickANationMenu(doNation, toNations, this, type);
                } else {
                    if (uncontrolledNPNs.size() > 0)
                        pickANation = new PickANationMenu(toNations, uncontrolledNPNs, this, type, GameMsg.getString("pickANation.title"));
                    else
                        pickANation = new PickANationMenu(toNations, this, type);
                }

                Point p = frame.getLocation();
                int x = p.x + getMap().getWidth()/2 - pickANation.getWidth()/2;
                int y = p.y + getMap().getHeight()/2 - pickANation.getWidth()/2;
                pickANation.setLocation(x,y);
                pickANation.pack();
            }
        } else {
            //Display error message todo more details
            if ( controller.isNation(doNation) )
                MessageDialog.failPoliticalAction(getMap(), controller, type, doNation, -1);
            else
                MessageDialog.failPoliticalAction(getMap(), controller, type, userNation, -1);
        }
    }

    public void nationPicked(int type, int nation) {
        if (type == SUMMARY)
            showNationSummary(nation);
        else if (type == COMMANDER)
            showCommCard(nation);
        else //This method will show popup dialog
            controller.political.takePoliticalAction(type, userNation, nation);
    }

    public void nationPicked(int type, int doNation, int toNation) {
        if (doNation > 0 && toNation > 0) {
            if (type == SUMMARY)
                showNationSummary(toNation);
            else if (type == COMMANDER)
                showCommCard(toNation);
            else //This method will show popup dialog
                controller.political.takePoliticalAction(type, doNation, toNation);
        }
    }

    /*
     * Displays a dialog asking if user wishes to form any alliances
     */
    public void formAllianceKa(int newlyControlledNation, ArrayList<Integer> nationsThatCanAlly) {
        System.out.println("DisplayController:formAllianceKa");

        int choice = MessageDialog.nationThatCanAlly(newlyControlledNation, nationsThatCanAlly, getMap());

        if (choice == JOptionPane.YES_OPTION) {
            if (nationsThatCanAlly.size() == 1)
                controller.political.takePoliticalAction(EventLogger.FORM_ALLIANCE, newlyControlledNation, nationsThatCanAlly.get(0));
            else {
                JDialog pickANation = new PickANationMenu(newlyControlledNation, nationsThatCanAlly, this, EventLogger.FORM_ALLIANCE);
                Point p = frame.getLocation();
                int x = p.x + getMap().getWidth()/2 - pickANation.getWidth()/2;
                int y = p.y + getMap().getHeight()/2 - pickANation.getWidth()/2;
                pickANation.setLocation(x,y);
                pickANation.pack();
            }
        } else if ( choice == JOptionPane.CLOSED_OPTION ) {
            formAllianceKa(newlyControlledNation, nationsThatCanAlly);
        } else {
            //No alliances to be made or NO_OPTION
            controller.controlNPNPurchase(newlyControlledNation);
        }
    }

    public void controlRequestDialog(int declaringNation, int uncontrolledNation, ArrayList<Integer> userNations) {
        int choice = MessageDialog.controlRequest(getMap(), declaringNation, uncontrolledNation);

        if ( choice == JOptionPane.YES_OPTION ) {
            new NationToControlThread(controller.getGameId(), declaringNation, uncontrolledNation, userNation, true).start();
            controller.doControlNonPlayerNation(-1, uncontrolledNation, userNation, false);
        } else if ( choice == JOptionPane.CLOSED_OPTION ) {
            controlRequestDialog(declaringNation, uncontrolledNation, userNations);
        } else { //tell server to line up next guy..
            if (userNations.size() == 1)
                new NationToControlThread(controller.getGameId(), declaringNation, uncontrolledNation, userNations.get(0)).start();
            else
                new MultiNationControlThread(controller.getGameId(), declaringNation, uncontrolledNation, userNations).start();
        }
    }

    public void allowNationToDeclareWar(int declaringNation, int uncontrolledNation, int papCost, int retaliationNation) {
        int choice = MessageDialog.allowNationToDeclareWar(getMap(), declaringNation, uncontrolledNation, papCost, retaliationNation);
        if ( choice == JOptionPane.YES_OPTION )
            new RetaliationWarDecThread(controller.getGameId(), declaringNation, uncontrolledNation, true, papCost, retaliationNation).start();
        else if ( choice == JOptionPane.NO_OPTION )
            new RetaliationWarDecThread(controller.getGameId(), declaringNation, uncontrolledNation, retaliationNation).start();
        else if ( choice == JOptionPane.CLOSED_OPTION )
            allowNationToDeclareWar(declaringNation, uncontrolledNation, papCost, retaliationNation);
    }

    public void allianceRequested(int requester, int nationTwo) {
        if (controller.isNationControlledByUser(nationTwo)) {
            int choice = MessageDialog.allianceRequested(getMap(), requester, nationTwo);
            if ( choice == JOptionPane.YES_OPTION )
                new FormAllianceThread(controller.getGameId(), requester, nationTwo, false, true).start();
            else if ( choice == JOptionPane.NO_OPTION )
                new FormAllianceThread(controller.getGameId(), requester, nationTwo, false, false).start();
            else if ( choice == JOptionPane.CLOSED_OPTION )
                allianceRequested(requester, nationTwo);
        }
    }

    public void armisticeRequested(int requester, int nationTwo) {
        if (controller.isNationControlledByUser(nationTwo)) {
            int choice = MessageDialog.armisticeRequested(getMap(), requester, nationTwo);
            if ( choice == JOptionPane.YES_OPTION )
                new ConcludeArmisticeThread(controller.getGameId(), requester, nationTwo, false, true).start();
            else if ( choice == JOptionPane.NO_OPTION )
                new ConcludeArmisticeThread(controller.getGameId(), requester, nationTwo, false, false).start();
            else if ( choice == JOptionPane.CLOSED_OPTION )
                armisticeRequested(requester, nationTwo);
        }
    }

    //If diplomatic turn is on nation controlled by user, display dialog asking that nation
    //if they would like to take a diplomatic action during this diplomatic round.
    public void diplomaticRoundDialog() {
        System.out.print("\ndiploRoundDialog turn: " + controller.game.getDiplomaticNationTurn() + " userNation: " + userNation + " UserControlledNPNs: ");
        for (Integer n : controller.getUserControlledNPNs())
            System.out.print(n + " ");
        System.out.println();

        if ( userNation == controller.game.getDiplomaticNationTurn()
                || controller.getUserControlledNPNs().contains(controller.game.getDiplomaticNationTurn()) ) {
            int choice = MessageDialog.diplomaticRoundAction(controller);

            if ( choice == JOptionPane.CLOSED_OPTION )
                diplomaticRoundDialog();
            else if ( choice == JOptionPane.NO_OPTION )
                new NextDiplomatTurnThread(controller.getGameId(), controller.game.getDiplomaticActionType()).start();
        }
    }

    public void endStepDialog() {
        int step = controller.game.getStep();
        int pStep = controller.game.getProductionStep();
        int dTurn = controller.game.getDiplomaticNationTurn();
        boolean diplomaticRound = controller.game.isDiplomaticRound();

        System.out.println("DisplayController:endStepDialog");
        //todo
        if ( diplomaticRound && (dTurn == userNation || controller.getUserControlledNPNs().contains(dTurn)) ) {
            if ( MessageDialog.endStepDialog(getMap(), step, pStep, dTurn, diplomaticRound) == JOptionPane.YES_OPTION ) {
                new NextDiplomatTurnThread(controller.getGameId(), controller.game.getDiplomaticActionType()).start();
            }
        }
    }

    public void displayEvent(String eventString) {
        showEvent(EventLogger.parseEvent(eventString));
        refreshEventLog();
    }

    @Deprecated public void refreshInfo() {
        refreshNationSummary();
        refreshPoliticalStatus();
        refreshEventLog();
        refreshGameStateInfo();
        refreshTerritoryInfo();
    }

    public void refreshNationSummary() {
        if (nationSummaryDialog != null) {
            ((NationInfoDialog)nationSummaryDialog).updateSummary();
            nationSummaryDialog.pack();
        }
    }

    public void refreshPoliticalStatus() {
        if (politicalStatusDialog != null) {
            ((PoliticalStatusMenu)politicalStatusDialog).updateStatus();
            politicalStatusDialog.pack();
        }
    }

    public void refreshEventLog() {
        if (eventLogDialog != null) {
            ((EventLogMenu)eventLogDialog).updateLog();
            eventLogDialog.pack();
        }
    }

    public void refreshChatCursors() { mainScreen.chat.chatChooser.setupCursors(); }
    public void refreshGameStateInfo() { mainScreen.roundTurn.refreshGameState(); }
    public void refreshTerritoryInfo() { mainScreen.sidePanel.tiPanel.refreshTerritoryInfo(); }
    public void showEvent(String event) { mainScreen.map.eventOverlay.showEvent(event); }

    public Component getFrame() { return frame; }
    public Component getMainScreen() { return mainScreen; }
    public Component getMap() { return mainScreen.map; }

    public static Color getNationColor(int nation) {
        switch (nation) {
            case 0: return DisplayController.NEUTRAL_COLOR;
            case 1: return DisplayController.FRENCH_COLOR;
            case 2: return DisplayController.BRITISH_COLOR;
            case 3: return DisplayController.PRUSSIAN_COLOR;
            case 4: return DisplayController.RUSSIAN_COLOR;
            case 5: return DisplayController.OTTOMAN_COLOR;
            case 6: return DisplayController.AUSTRIAN_COLOR;
            case 7: return DisplayController.SPANISH_COLOR;
            default: return DisplayController.NEUTRAL_COLOR;
        }
    }

    /**
     * Refresh the display of the frame.
     */
    public void refresh() {
        frame.validate();
        frame.repaint();
    }

    private JFrame frame;
    private GameMenuBar gameMenuBar;
    private MainScreen mainScreen;
    private JDialog commFrame;
    private JDialog nationSummaryDialog;
    private JDialog purchaseDialog;
    private JDialog placementDialog;
    private JDialog eventLogDialog;
    private JDialog politicalStatusDialog;
    private JDialog definitionsDialog;
    private JDialog congressDialog;

    private GameController controller;
    private int userNation;

    private String globalChat;
    private String frenchChat;
    private String britishChat;
    private String prussianChat;
    private String russianChat;
    private String ottomanChat;
    private String austrianChat;
    private String spanishChat;

    private final int MAIN_SCREEN = 0;

    public final int SUMMARY = -1;
    public final int COMMANDER = -2;

    private boolean purchaseCoordinates;
    private boolean placementCoordinates;
    private boolean eventLogCoordinates;
    private boolean politicalStatusCoordinates;
    private boolean commScreenCoordinates;
    private boolean nationSummCoordinates;
    private boolean definitionCoordinates;
    private boolean congressCoordinates;
    private int purchaseX = -1;
    private int purchaseY = -1;
    private int placementX = -1;
    private int placementY = -1;
    private int eventLogX = -1;
    private int eventLogY = -1;
    private int politicalX = -1;
    private int politicalY = -1;
    private int commScreenX = -1;
    private int commScreenY = -1;
    private int nationSummX = -1;
    private int nationSummY = -1;
    private int definitionX = -1;
    private int definitionY = -1;
    private int congressX = -1;
    private int congressY = -1;
}