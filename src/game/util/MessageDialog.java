package game.util;

import game.controller.GameController;
import game.controller.GameInstance;
import game.controller.Region.LandRegion;
import game.gui.DialogMessagePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * MessageDialog.java  Date Created: Aug 22, 2013
 *
 * Purpose: To display a message or error code.
 *
 * Description: This class will be used to display messages throughout the game as well as
 * used for debugging purposes to display error codes/msgs.
 *
 * @author Chrisb
 */
public final class MessageDialog {

    public static void displayMessage(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg);
    }

    public static void displayErrorCode(Component parent, String code) {
        JOptionPane.showMessageDialog(parent, code, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public static void displayWarning(Component parent, String warning) {
        JOptionPane.showMessageDialog(parent, warning, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public static int quitDialog(Component comp) {
        Object[] options = {GameMsg.getString("quit.yes"), GameMsg.getString("quit.no")};
        return JOptionPane.showOptionDialog(comp, GameMsg.getString("quit.question"),
                GameMsg.getString("quit.title"), JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[1]);
    }

    public static int confirmPoliticalAction(GameController controller, int type, int doNation, int toNation) {
        Object[] options = {GameMsg.getString("yes.political." + type), GameMsg.getString("no.political")};
        String question;
        if (type == EventLogger.RECRUIT_MINOR || type == EventLogger.ANNEX_MINOR
                || type == EventLogger.RESTORE_REGION || type == EventLogger.ABANDON_REGION
                || type == EventLogger.FOMENT_UPRISING || type == EventLogger.SUPPRESS_UPRISING )
            question = GameMsg.getString("question.political." + type) + " " + controller.getLandRegion(toNation).toString() + "?";
        else
            question = GameMsg.getString("question.political." + type) + " " + GameMsg.getString("nation." + toNation) + "?";

        String title = GameMsg.getString("title.political." + type);
        int optionType = JOptionPane.YES_NO_OPTION;
        int messageType;
//        Icon icon;

        switch (type) {
            case EventLogger.DECLARE_WAR: messageType = JOptionPane.ERROR_MESSAGE; break;
            case EventLogger.SUE_FOR_PEACE: messageType = JOptionPane.QUESTION_MESSAGE; question = GameMsg.getString("question.political." + type); break;
            case EventLogger.CONCLUDE_ARMISTICE: messageType = JOptionPane.INFORMATION_MESSAGE; break;
            case EventLogger.FORM_ALLIANCE: messageType = JOptionPane.QUESTION_MESSAGE; break;
            case EventLogger.BREAK_ALLIANCE: messageType = JOptionPane.QUESTION_MESSAGE; break;
            case EventLogger.RECRUIT_MINOR: messageType = JOptionPane.QUESTION_MESSAGE; break;
            case EventLogger.ANNEX_MINOR: messageType = JOptionPane.QUESTION_MESSAGE; break;
            case EventLogger.RESTORE_REGION: messageType = JOptionPane.QUESTION_MESSAGE; break;
            case EventLogger.ABANDON_REGION: messageType = JOptionPane.QUESTION_MESSAGE; break;
            case EventLogger.FOMENT_UPRISING: messageType = JOptionPane.QUESTION_MESSAGE; break;
            case EventLogger.SUPPRESS_UPRISING: messageType = JOptionPane.QUESTION_MESSAGE; break;
            case EventLogger.GRANT_PASSAGE: messageType = JOptionPane.INFORMATION_MESSAGE; break;
            case EventLogger.RESCIND_PASSAGE: messageType = JOptionPane.INFORMATION_MESSAGE; break;
            case EventLogger.ENFORCE_CS: messageType = JOptionPane.QUESTION_MESSAGE; question = GameMsg.getString("question.political." + type); break;
            case EventLogger.CONTROL_NPN: messageType = JOptionPane.QUESTION_MESSAGE; break;
            case EventLogger.RELEASE_NPN: messageType = JOptionPane.QUESTION_MESSAGE; break;
            default: messageType = JOptionPane.QUESTION_MESSAGE;
        }

        return JOptionPane.showOptionDialog(controller.getDisplay().getMap(), question, title, optionType, messageType, null, options, options[1]);
    }

    /*
     * If a political action can not be taken, this method will display why.
     *
     * Reasons action can not be taken:
     * 1) Not allowed during current step
     * 2) Not enough PAPs
     * 3) No qualified nations to take action on
     *
     * Examples:
     * 1) Can not take an action during land battles.
     * 2) Can not declare war without 2 PAP.
     * 3) Can not break an alliance, if nation is not in any alliances.
     */
    public static void failPoliticalAction(Component comp, GameController controller, int type, int doNation, int toNation) {
        String messageText = GameMsg.getString("failPolitical.first"), title = GameMsg.getString("failPolitical.title");
        int step = controller.game.getStep();

        //Can't take actions during start of game.
        if ( step == GameInstance.START_OF_GAME ) {
            messageText += " " + GameMsg.getString("failPolitical.startOfGame");
        } else if ( step == GameInstance.START_OF_TURN && controller.game.getTurn() != doNation ) {
            messageText += " " + GameMsg.getString("failPolitical.startOfMove");
        } else if ( step == GameInstance.ROLL_FOR_PAP ) {
            messageText += " " + GameMsg.getString("failPolitical.roll");
        } else if ( step == GameInstance.NAVAL_BATTLE ) {
            messageText += " " + GameMsg.getString("failPolitical.navalBattle");
        } else if ( step == GameInstance.LAND_BATTLE ) {
            messageText += " " + GameMsg.getString("failPolitical.landBattle");
        } else if ( step == GameInstance.END_OF_TURN ) {
            messageText += " " + GameMsg.getString("failPolitical.endTurn");
        } else if ( step == GameInstance.PRODUCTION_ROUND && type != EventLogger.ENFORCE_CS ) {
            messageText += " " + GameMsg.getString("failPolitical.production");
        } else if ( step == GameInstance.DIPLOMATIC_ROUND && controller.game.getDiplomaticNationTurn() != doNation ) {
            messageText += " " + GameMsg.getString("failPolitical.diplomatic");
        } else {
            //Not enough PAP or no eligible nations
            messageText = GameMsg.getString("failPolitical.default") + " step:" + step;
        }

        JOptionPane.showMessageDialog(comp, messageText, title, JOptionPane.ERROR_MESSAGE, null);
    }

    //todo current setup only works for 'NATION did action to NATION'
    //France has declared war on Russia which has started a diplomatic round.
    //Would you like to take a political action with Spain during this diplomatic round?
    public static int diplomaticRoundAction(GameController controller) {
        int turnNation = controller.game.getDiplomaticNationTurn();
        int type = controller.game.getDiplomaticActionType();

        String question = GameMsg.getString("nation." + controller.game.getDiplomaticStartingNation()) + " " + GameMsg.getString("question.diplomat." + type)
                + " " + GameMsg.getString("nation." + controller.game.getDiplomaticToNation()) + ", " + GameMsg.getString("diplomat.statement")
                + "\nWould " + GameMsg.getString("nation." + turnNation) + " " + GameMsg.getString("diplomat.question.1");
        Object[] options = {GameMsg.getString("yes.diplomat"), GameMsg.getString("no.diplomat")};

        return JOptionPane.showOptionDialog(controller.getDisplay().getMap(), question,
                GameMsg.getString("diplomat.title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    /*
     * Control NPN Dialogs
     */
    public static int nationThatCanAlly(Integer newControlledNation, ArrayList<Integer> nations, Component comp) {
        if (nations.size() > 1) {
            //plural
            String question = GameMsg.getString("fak.p.question.1") + GameMsg.getString("nation." + newControlledNation)
                    + GameMsg.getString("fak.p.question.2");
            Object[] options = {GameMsg.getString("fak.p.yes"), GameMsg.getString("fak.no")};
            return JOptionPane.showOptionDialog(comp, question,
                    GameMsg.getString("fak.p.title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);
        } else if (nations.size() == 1) {
            //singular
            String question = GameMsg.getString("fak.s.question.1") + GameMsg.getString("nation." + newControlledNation)
                    + GameMsg.getString("fak.s.question.2") + " " + GameMsg.getString("nation." + nations.get(0)) + "?";
            Object[] options = {GameMsg.getString("fak.s.yes"), GameMsg.getString("fak.no")};
            return JOptionPane.showOptionDialog(comp, question,
                    GameMsg.getString("fak.s.title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);
        } else {
            return JOptionPane.NO_OPTION;
        }
    }

    public static void informUserOfControl(Component comp, int declaringNation, int uncontrolledNation) {
        String statement = GameMsg.getString("nation." + declaringNation) + " " + GameMsg.getString("control.statement.1")
                + " " + GameMsg.getString("nation." + uncontrolledNation) + GameMsg.getString("control.statement.2")
                + GameMsg.getString("control.statement.3") + " " + GameMsg.getString("nation." + uncontrolledNation) + ".";
        JOptionPane.showMessageDialog(comp, statement, GameMsg.getString("control.title"), JOptionPane.INFORMATION_MESSAGE);
    }

    //France has declared war on The Ottoman Empire, an uncontrolled nation.
    //Would you like to take control of this nation for zero (0) PAP?
    public static int controlRequest(Component comp, int declaringNation, int uncontrolledNation) {
        String question = GameMsg.getString("nation." + declaringNation) + " " + GameMsg.getString("control.statement.1")
                + " " + GameMsg.getString("nation." + uncontrolledNation) + GameMsg.getString("control.statement.2")
                + GameMsg.getString("control.request.question");
        Object[] options = {GameMsg.getString("control.request.yes"), GameMsg.getString("control.request.no")};
        return JOptionPane.showOptionDialog(comp, question,
                GameMsg.getString("control.title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
    }

    //NATION has declared war on NATION, an uncontrolled nation.
    //Would you like to declare war on NATION for papCost PAP?
    public static int allowNationToDeclareWar(Component comp, int declaringNation, int uncontrolledNation, int papCost, int retaliationNation) {
        String question = GameMsg.getString("nation." + declaringNation) + " " + GameMsg.getString("control.statement.1")
                + " " + GameMsg.getString("nation." + uncontrolledNation) + GameMsg.getString("control.statement.2")
                + "\nWould " + GameMsg.getString("nation." + retaliationNation) + " " + GameMsg.getString("declare.request.question") 
                + " " + GameMsg.getString("nation." + declaringNation) + " for " + papCost + " PAP?";
        Object[] options = {GameMsg.getString("yes.political.0"), GameMsg.getString("declare.request.no")};
        return JOptionPane.showOptionDialog(comp, question,
                GameMsg.getString("title.political.0"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
    }

    //todo
    public static int endStepDialog(Component comp, int step, int pStep, int dTurn, boolean dRound) {
        String question = "errorQuestion";
        Object[] options = {"toError", "toNotError"};
        String title = "errorTitle";
        if (dRound) {
            question = GameMsg.getString("endStep.dRound.0") + GameMsg.getString("nation." + dTurn) + GameMsg.getString("endStep.dRound.1");
            options = new String[]{GameMsg.getString("endStep.dRound.yes"), GameMsg.getString("endStep.dRound.no")};
            title = GameMsg.getString("endStep.dRound.title");
        }
        return JOptionPane.showOptionDialog(comp, question, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    //None of the nations you control are eligible to TYPE
    public static void noEligibleNation(Component comp, int type) {
        String statement = GameMsg.getString("noEligibleNations") + " " + GameMsg.getString("title.political." + type);
        JOptionPane.showMessageDialog(comp, statement, GameMsg.getString("noEligibleNations.title"), JOptionPane.INFORMATION_MESSAGE);
    }

    //NATION does not have enough production to buy any new military units or PAPs.
    public static void notEnoughProductionToBuy(Component comp, int nation) {
        String statement = GameMsg.getString("nation." + nation) + " " + GameMsg.getString("notEnoughProductionToBuy");
        JOptionPane.showMessageDialog(comp, statement, GameMsg.getString("notEnoughProduct.title"), JOptionPane.INFORMATION_MESSAGE);
    }

    //NATIONTWO has rejected your request for an alliance with your nation, REQUESTER.
    public static void allianceRejected(Component comp, int requester, int nationTwo) {
        String statement = GameMsg.getString("nation." + nationTwo) + " " + GameMsg.getString("alliance.rejected")
                + " " + GameMsg.getString("nation." + requester) + ".";
        JOptionPane.showMessageDialog(comp, statement, GameMsg.getString("alliance.rejected.title"), JOptionPane.INFORMATION_MESSAGE);
    }

    //REQUESTER has requested an alliance with your nation, NATIONTWO.
    //Would NATIONTWO like to form an alliance with REQUESTER?
    public static int allianceRequested(Component comp, int requester, int nationTwo) {
        String question = GameMsg.getString("nation." + requester) + " " + GameMsg.getString("alliance.statement") + " " + GameMsg.getString("nation." + nationTwo) + "."
                + "\nWould " + GameMsg.getString("nation." + nationTwo) + " " + GameMsg.getString("alliance.request.question") + " " + GameMsg.getString("nation." + requester) + "?";
        Object[] options = {GameMsg.getString("alliance.request.yes"), "No"};
        return JOptionPane.showOptionDialog(comp, question,
                GameMsg.getString("alliance.request.title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
    }

    //NATIONTWO has rejected your request to conclude an armistice with your nation, REQUESTER.
    public static void armisticeRejected(Component comp, int requester, int nationTwo) {
        String statement = GameMsg.getString("nation." + nationTwo) + " " + GameMsg.getString("armistice.rejected")
                + " " + GameMsg.getString("nation." + requester) + ".";
        JOptionPane.showMessageDialog(comp, statement, GameMsg.getString("armistice.rejected.title"), JOptionPane.INFORMATION_MESSAGE);
    }

    public static int armisticeRequested(Component comp, int requester, int nationTwo) {
        String question = GameMsg.getString("nation." + requester) + " " + GameMsg.getString("armistice.statement") + " " + GameMsg.getString("nation." + nationTwo) + "."
                + "\nWould " + GameMsg.getString("nation." + nationTwo) + " " + GameMsg.getString("armistice.request.question") + " " + GameMsg.getString("nation." + requester) + "?";
        Object[] options = {GameMsg.getString("armistice.request.yes"), "No"};
        return JOptionPane.showOptionDialog(comp, question,
                GameMsg.getString("armistice.request.title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
    }

    //USER would like to attempt to gain control of the non-player nation, NATION, you control.
    //If you give your consent USER will add four (4) to their roll if they attempt control.
    //Would you like to give your consent for USER to attempt control of NATION?
    public static int requestPermissionToControlNPN(Component comp, String userToControl, int nonPlayerNation) {
        String question = userToControl + " " + GameMsg.getString("controlNPN.request.statement.1") + " "
                + GameMsg.getString("nation." + nonPlayerNation) + ", you control." + GameMsg.getString("controlNPN.request.statement.2") + " "
                + userToControl + " " + GameMsg.getString("controlNPN.request.statement.3") + GameMsg.getString("controlNPN.request.question.1") + " "
                + userToControl + " " + GameMsg.getString("controlNPN.request.question.2") + " " + GameMsg.getString("nation." + nonPlayerNation) + "?";
        Object[] options = {GameMsg.getString("controlNPN.request.yes"), "No"};
        return JOptionPane.showOptionDialog(comp, question,
                GameMsg.getString("controlNPN.request.title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
    }

    //USER has not given consent to attempt to gain control of the non-player nation, NATION.
    //Would you like to attempt to gain control of the non-player nation, NATION?
    public static int confirmControlNPN(Component comp, int nonPlayerNation, boolean isControlled, boolean hasConsent, String userName) {
        Object[] options = {GameMsg.getString("yes.political." + EventLogger.CONTROL_NPN), GameMsg.getString("no.political")};
        String title = GameMsg.getString("title.political." + EventLogger.CONTROL_NPN);
        String question = "";

        if (isControlled) {
            question += userName + " has";
            if (!hasConsent)
                question += " not";
            question += " " + GameMsg.getString("controlNPN.consent") + " " + GameMsg.getString("nation." + nonPlayerNation) + ".\n";
        }
        question += GameMsg.getString("question.political." + EventLogger.CONTROL_NPN) + " " + GameMsg.getString("nation." + nonPlayerNation) + "?";

        int choice = JOptionPane.showOptionDialog(comp, question, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

        if (choice == JOptionPane.CLOSED_OPTION)
            return confirmControlNPN(comp, nonPlayerNation, isControlled, hasConsent, userName);
        else
            return choice;
    }

    //Your nation, NATION, has already annexed a homeland region during this congress and there are
    //no other actions available to your nation.  Your nation will be removed from this congress.
    public static void congressAlreadyAnnexedHomeland(Component comp, int congressNation) {
        String statement = "Your nation, " + GameMsg.getString("nation." + congressNation) + GameMsg.getString("congress.alreadyAnnexed");
        JOptionPane.showMessageDialog(comp, statement, GameMsg.getString("congress.alreadyAnnexed.title"), JOptionPane.INFORMATION_MESSAGE);
    }

    //Your nation, NATION, does not have enough PAP to take any of the available actions
    //during the SUENATION Peace Congress.  Your nation will be removed from this congress.
    public static void congressNotEnoughPap(Component comp, int congressNation, int sueingNation) {
        String statement = "Your nation, " + GameMsg.getString("nation." + congressNation) + GameMsg.getString("congress.papShortage.1")
                + " " + GameMsg.getString("nation.pos." + sueingNation) + " " +  GameMsg.getString("congress.papShortage.2");
        JOptionPane.showMessageDialog(comp, statement, GameMsg.getString("notEnoughProduct.title"), JOptionPane.INFORMATION_MESSAGE);
    }

    //The sueing nation, SUENATION, is now extinct!  They may be missed by some..
    //
    //..or they may not.
    public static void sueingNationExtinct(Component comp, int sueingNation) {
        String statement = "The sueing nation, " + GameMsg.getString("nation." + sueingNation) + GameMsg.getString("congress.extinct");
        JOptionPane.showMessageDialog(comp, statement, GameMsg.getString("nation." + sueingNation) + " " + GameMsg.getString("congress.extinct.title"),
                JOptionPane.WARNING_MESSAGE);
    }

    //The action you have selected requires you to choose at least one region.
    public static void congressSelectRegion(Component comp) {
        String statement = GameMsg.getString("congress.selectRegion");
        JOptionPane.showMessageDialog(comp, statement, GameMsg.getString("congress.selectRegion.title"), JOptionPane.INFORMATION_MESSAGE);
    }

    //You have selected the following Region to Annex from SUENATION: RegionOne.
    //This action will cost you 1(2) PAP(s), do you wish to Annex this Region?
    public static int confirmCongressAnnex(Component comp, int sueingNation, LandRegion one, int papCost) {
        String[] lines = new String[2];
        String papString = papCost > 1 ? " PAPs" : " PAP";
        lines[0] = GameMsg.getString("congress.annexConfirm.1") + " " + GameMsg.getString("nation." + sueingNation) + ": " + one.toString() + ".";
        lines[1] = GameMsg.getString("congress.annexConfirm.2") + " " + papCost + papString + GameMsg.getString("congress.annexConfirm.3");

        DialogMessagePanel questionPanel = new DialogMessagePanel(lines);

        Object[] options = {GameMsg.getString("congress.annex.yes"), "No"};
        return JOptionPane.showOptionDialog(comp, questionPanel,
                GameMsg.getString("congress.annex.title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
    }

    //You have selected the following Region(s) to Restore from SUENATION:
    // RegionOne, RegionTwo.
    //This action will cost you 1 PAP, do you wish to Restore this[these] Region[s]?
    public static int confirmCongressRestore(Component comp, int sueingNation, LandRegion one, LandRegion two) {
        String[] lines = new String[3];
        lines[0] = two != null ? GameMsg.getString("congress.restoreConfirm.1.plural") : GameMsg.getString("congress.restoreConfirm.1.single")
                + GameMsg.getString("nation." + sueingNation) + ":";
        lines[1] = one.toString() + (two != null ? ", " + two.toString() : "");
        lines[2] = two != null ? GameMsg.getString("congress.restoreConfirm.2.plural")  : GameMsg.getString("congress.restoreConfirm.2.single");

        DialogMessagePanel questionPanel = new DialogMessagePanel(lines);

        Object[] options = {GameMsg.getString("congress.restore.yes"), "No"};
        return JOptionPane.showOptionDialog(comp, questionPanel,
                GameMsg.getString("congress.restore.title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
    }

    //You have selected the following Region[s] to Free Russian Serfs:
    // RegionOne, RegionTwo, RegionThree.
    //This action will cost you 1 PAP, do you wish to Free Russian Serfs?
    public static int confirmCongressFreeSerfs(Component comp, LandRegion one, LandRegion two, LandRegion three) {
        String[] lines = new String[3];
        lines[0] = two != null ? GameMsg.getString("congress.freeSerf.1.plural") : GameMsg.getString("congress.freeSerf.1.single");
        lines[1] = one.toString() + (two != null ? ", " + two.toString() : "") + (three != null ? ", " + three.toString() : "");
        lines[2] = GameMsg.getString("congress.freeSerf.2");

        DialogMessagePanel questionPanel = new DialogMessagePanel(lines);

        Object[] options = {GameMsg.getString("congress.freeSerf.yes"), "No"};
        return JOptionPane.showOptionDialog(comp, questionPanel,
                GameMsg.getString("congress.freeSerf.title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
    }

    //If you pass you will be removed from this congress and
    // cannot take any other actions during this congress.
    //  Are you sure you want to pass in this congress?
    public static int confirmCongressPass(Component comp) {
        String[] lines = {GameMsg.getString("congress.passConfirm.1"),
                GameMsg.getString("congress.passConfirm.2"),
                GameMsg.getString("congress.passConfirm.3")};

        DialogMessagePanel questionPanel = new DialogMessagePanel(lines);

        Object[] options = {GameMsg.getString("congress.pass.yes"), "No"};
        return JOptionPane.showOptionDialog(comp, questionPanel,
                GameMsg.getString("congress.pass.title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
    }

    public static int LEFT_ALIGN = 0;
    public static int RIGHT_ALIGN = 1;
    public static int CENTER_ALIGN = 2;
}