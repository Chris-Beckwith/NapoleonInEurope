package game.controller;

import game.controller.Region.LandRegion;
import game.controller.Region.Region;
import game.controller.Unit.MilitaryUnit;
import game.util.*;
import game.client.political.*;
import game.client.political.SueForPeaceThread;
import lobby.controller.Nation;
import lobby.controller.User;
import shared.controller.LobbyConstants;

import javax.swing.*;
import java.util.ArrayList;

/**
 * PoliticalController.java  Date Created: Sep 20, 2013
 *
 * Purpose: To handle the political actions that take place during the game.
 *
 * Description:
 *
 * Political Actions
 * ~~~~~~~~~~~~~~~~~
 * 1  Declare War                  W
 * 2  Sue for Peace                P
 * 3  Conclude Armistice           C
 * 4  Form Alliance                F
 * 5  Break Alliance               B
 * 6  Recruit Minor Nation         M
 * 7  Annex Minor Nation           A
 * 8  Restore Region               R
 * 9  Abandon Region               D
 * 10 Foment Uprising              U
 * 11 Suppress Uprising            S
 * 12 Grant Right of Passage       G
 * 13 Rescind Right of Passage     P
 * 14 Enforce Continental System   E
 * 15 Control Non-Player Nation    N
 *
 * @author Chrisb
 */
public class PoliticalController {
    private GameController controller;
//    private DisplayController display;
    private int numOfCSEnforcedThisTurn;

    public PoliticalController(GameController controller, DisplayController display) {
        this.controller = controller;
//        this.display = display;
        numOfCSEnforcedThisTurn = 0;
    }

    public boolean isPoliticalActionAllowed(int type, int doNation, int toNation) {
        //todo
        if ( controller.game.getStep() == GameInstance.DIPLOMATIC_ROUND ) {
            return controller.game.getDiplomaticNationTurn() == doNation;
        } else if ( controller.game.getStep() == GameInstance.START_OF_GAME ) {
            return false; //Placing units is all that is done here.
        } else if ( controller.game.getStep() == GameInstance.ROLL_FOR_PAP ) {
            return false; //Actions can not be taken while rolling for PAP (may be removed as this will be automated)
        } else if ( controller.game.getStep() == GameInstance.START_OF_TURN && controller.game.getTurn() != doNation ) {
            return false; //Start of move the player whos turn it is has priority.
        } else if ( controller.game.getStep() == GameInstance.NAVAL_BATTLE ) {
//            if ( type == EventLogger.SUE_FOR_PEACE && controller.getBattleNations() )
//                return true;  todo, losing nation may be forced to sue for peace.
            return false; //todo make sure no other actions can be taken.
        } else if ( controller.game.getStep() == GameInstance.LAND_BATTLE ) {
            //todo 'Consequences of Land Battles'
            return false;
        } else if ( controller.game.getStep() == GameInstance.END_OF_TURN ) {
//            if ( type == EventLogger.SUE_FOR_PEACE ) todo this must be forced via 'Captured Capitals'
//                return true;
            return false;
        } else if ( controller.game.getStep() == GameInstance.PRODUCTION_ROUND ) {
            //todo Steps vs Steps of Production Round
            //this is the only place this action can be taken.
            return type == EventLogger.ENFORCE_CS;
        } else {
            return true;
        }
    }

    public boolean canTakePoliticalAction(int type, int doNation, int toNation) {
        //Nations can not take actions on themselves
        if (doNation == toNation)
            return false;

        if ( controller.getNationInstance(doNation).isExtinct() || controller.getNationInstance(toNation).isExtinct() )
            return false;

        //Check if Political Actions can be taken now.
        if ( !isPoliticalActionAllowed(type, doNation, toNation) )
            return false;

        //Check Conditions
        switch (type) {
            case EventLogger.DECLARE_WAR: return canDeclareWar(doNation, toNation);
            case EventLogger.SUE_FOR_PEACE: return canSueForPeace(doNation);
            case EventLogger.CONCLUDE_ARMISTICE: return canConcludeArmistice(doNation, toNation);
            case EventLogger.FORM_ALLIANCE: return canFormAlliance(doNation, toNation);
            case EventLogger.BREAK_ALLIANCE: return canBreakAlliance(doNation, toNation);
            case EventLogger.RECRUIT_MINOR: return canRecruitMinorNation(doNation, toNation);
            case EventLogger.ANNEX_MINOR: return canAnnexMinorNation(doNation, toNation);
            case EventLogger.RESTORE_REGION: return canRestoreRegion(doNation, toNation);
            case EventLogger.ABANDON_REGION: return canAbandonRegion(doNation, toNation);
            case EventLogger.FOMENT_UPRISING: return canFomentUprising(doNation, toNation);
            case EventLogger.SUPPRESS_UPRISING: return canSuppressUprising(doNation, toNation);
            case EventLogger.GRANT_PASSAGE: return canGrantRightOfPassage(doNation, toNation);
            case EventLogger.RESCIND_PASSAGE: return canRescindRightOfPassage(doNation, toNation);
            case EventLogger.ENFORCE_CS: return canEnforceContinentalSystem(doNation);
            case EventLogger.CONTROL_NPN: return canControlNPN(doNation, toNation);
            case EventLogger.RELEASE_NPN: return canReleaseNPN(doNation, toNation);
            default: GameLogger.log("PoliticalController.canTakePoliticalAction : Invalid event type found - " + type); return false;
        }
    }

    public void takePoliticalAction(int type, int doNation, int toNation) {
        //Confirm Dialog
        int confirm;
        //If during actions..? todo
        if ( (controller.game.isDiplomaticRound() && !controller.game.hasRoundStarted())
                && (controller.game.getDiplomaticActionType() == EventLogger.DECLARE_WAR
                || controller.game.getDiplomaticActionType() == EventLogger.CONTROL_NPN) )
            confirm = JOptionPane.YES_OPTION;
        else if (type == EventLogger.CONTROL_NPN)
            confirm = JOptionPane.YES_OPTION; //to be confirmed later.
        else
            confirm = MessageDialog.confirmPoliticalAction(controller, type, doNation, toNation);

        //If, Yes
        if (confirm == JOptionPane.YES_OPTION) {
            //take action
            switch (type) {
                case EventLogger.DECLARE_WAR: declareWar(doNation, toNation); break;
                case EventLogger.SUE_FOR_PEACE: sueForPeace(doNation); break;
                case EventLogger.CONCLUDE_ARMISTICE: concludeArmistice(doNation, toNation); break;
                case EventLogger.FORM_ALLIANCE: formAlliance(doNation, toNation); break;
                case EventLogger.BREAK_ALLIANCE: breakAlliance(doNation, toNation); break;
                case EventLogger.RECRUIT_MINOR: recruitMinorNation(doNation, toNation); break;
                case EventLogger.ANNEX_MINOR: annexMinorNation(doNation, toNation); break;
                case EventLogger.RESTORE_REGION: restoreRegion(doNation, toNation); break;
                case EventLogger.ABANDON_REGION: abandonRegion(doNation, toNation); break;
                case EventLogger.FOMENT_UPRISING: fomentUprising(doNation, toNation); break;
                case EventLogger.SUPPRESS_UPRISING: suppressUprising(doNation, toNation); break;
                case EventLogger.GRANT_PASSAGE: grantRightOfPassage(doNation, toNation); break;
                case EventLogger.RESCIND_PASSAGE: rescindRightOfPassage(doNation, toNation); break;
                case EventLogger.ENFORCE_CS: enforceContinentalSystem(doNation); break;
                case EventLogger.CONTROL_NPN: controlNPN(doNation, toNation); break;
                case EventLogger.RELEASE_NPN: releaseNPN(doNation, toNation); break;
            }
        }
    }

    public boolean canDeclareWar(int declaringNation, int enemyNation) {
        NationInstance declarer = controller.getNationInstance(declaringNation);
        NationInstance enemy = controller.getNationInstance(enemyNation);

        //Check Grace Periods
        if (enemy.inGracePeriodWith(declaringNation))
            return false;

        if (declarer.inReverseGraceWith(enemyNation))
            return false;

        //Nations cannot be controlled by same player
        if ( controller.getUserControlledNPNs().contains(controller.getUserNation()) )
            return false;

        //Nations must be neutral
        if ( !declarer.isNeutral(enemy.getNationNumber()) )
            return false;

        //Nation must rescind Right of Passage to enemy nation first
        if ( enemy.hasRightOfPassage(declarer.getNationNumber()) )
            return false;

        //Cost 2, unless allied with someone at war with 'enemy' already, then 1.
        return declarer.getPaps() >= PoliticalUtilities.getWarDecPapCost(declaringNation, enemyNation, controller);
    }

    public void declareWar(int declaringNation, int enemyNation) {
        NationInstance declarer = controller.getNationInstance(declaringNation);
        NationInstance enemy = controller.getNationInstance(enemyNation);
        int papCost = PoliticalUtilities.getWarDecPapCost(declaringNation, enemyNation, controller);

        //If uncontrolled nation
        //Decide which WarDeclarationThread should be constructed
        if (!enemy.isControlledNation()) {
            //Declarer is at war, get the group of users at war with declarer.
            if ( declarer.isAtWar() ) {
                ArrayList<User> users = controller.getAllUsers();
                ArrayList<Integer> enemies = declarer.getEnemies();
                //Get Users
                ArrayList<User> group = PoliticalUtilities.getUsersAtWarWith(declaringNation, enemies, users);

                //If only 1 user with a nation at war with declarer,
                //That user must take control of the uncontrolled (enemy)Nation.
                if (group.size() == 1)
                    new WarDeclarationThread(controller.getGameId(), declaringNation, enemyNation, papCost, group.get(0).getNation()).start();
                else
                    //If more than 1 user in group, add them and their respective total nations unit count to arrayLists.
                    //Currently just counts the number of units, maybe should count total production value of army. todo
                    new WarDeclarationThread(controller.getGameId(), declaringNation, enemyNation, papCost,
                            PoliticalUtilities.sortUsersBySize(group, controller)).start();
            } else
                new WarDeclarationThread(controller.getGameId(), declaringNation, enemyNation, papCost, false).start();
        } else
            new WarDeclarationThread(controller.getGameId(), declaringNation, enemyNation, papCost).start();
    }

    public boolean canSueForPeace(int sueingNation) { //Check if sueingNation is at war and Paps >= 3;
        return controller.getNationInstance(sueingNation).isAtWar() && controller.getNationInstance(sueingNation).getPaps() > 2;
    }

    public void sueForPeace(int sueingNation) {
        new SueForPeaceThread(controller.getGameId(), sueingNation).start();
    }

    public boolean canConcludeArmistice(int requestingNation, int enemyNation) {
        //Check if nationOne is at war with nationTwo and Check paps >= 1 for both nationOne and nationTwo
        //This method will not be called as a result of a declareWar/ControlNPN
        NationInstance requester = controller.getNationInstance(requestingNation);
        NationInstance enemy = controller.getNationInstance(enemyNation);

        return requester.isEnemy(enemyNation) && requester.getPaps() > 0 && enemy.getPaps() > 0;
    }

    public void concludeArmistice(int nationOne, int nationTwo) {
        new ConcludeArmisticeThread(controller.getGameId(), nationOne, nationTwo, true, false).start();
    }

    /*
     * Check that both nations are controlled
     * Check that both nations have at least 1 PAP.
     * Check that nations are neutral and check Reverse Grace periods.
     */
    public boolean canFormAlliance(int requestingNation, int allyNation) {
        NationInstance requester = controller.getNationInstance(requestingNation);
        NationInstance ally = controller.getNationInstance(allyNation);

        if ( !requester.isControlledNation() || !ally.isControlledNation() )
            return false;

        //noinspection SimplifiableIfStatement
        if ( requester.inReverseGraceWith(allyNation) )
            return false;

        return requester.isNeutral(allyNation) && requester.getPaps() > 0 && ally.getPaps() > 0;
    }

    public void formAlliance(int nationOne, int nationTwo) {           //isRequest //hasAccepted
        new FormAllianceThread(controller.getGameId(), nationOne, nationTwo, true, false).start();
    }

    public boolean canBreakAlliance(int breakingNation, int alliedNation) {
        //Check if Team game
        //Check if breakingNation is on alliedNation's allianceList
        //Check paps >= 1 for breakingNation
        NationInstance breaker = controller.getNationInstance(breakingNation);
        if (controller.game.getGameMode() == LobbyConstants.TEAM)
            if (breaker.isAllied(alliedNation))
                return false;

        return breaker.isAllied(alliedNation) && breaker.getPaps() > 0;
    }

    public void breakAlliance(int breakingNation, int alliedNation) {
        new BreakAllianceThread(controller.getGameId(), breakingNation, alliedNation).start();
    }

    public boolean canRecruitMinorNation(int majorNation, int minorNation) {
        //Check minorNation is uncontrolled
        //Check minorNation not Gibraltar
        //Check paps >= 2 for majorNation
        NationInstance recruiter = controller.getNationInstance(majorNation);
        Region region =  controller.getLandRegion(minorNation);

        if (recruiter.getPaps() < 2)
            return false;
        if ( region.getType() == Region.LAND_REGION )
            if ( ((LandRegion)region).getControllingNation() >= Nation.FRANCE
                || ((LandRegion)region).getControllingNation() <= Nation.SPAIN )
                    return false;
        else
            return false;

        return !region.toString().equals(GameMsg.getString("Gibraltar"));
    }

    public void recruitMinorNation(int majorNation, int minorNation) {
        //canRecruitMinorNation()
            //Remove 2 paps from majorNation
            //send request to server to attempt recruit
    }

    public boolean canAnnexMinorNation(int majorNation, int minorNation) {
        //Check minorNation is uncontrolled
        //Check paps >= 1 for majorNation
        //Check majorNation has units in minorNation
        NationInstance annexer  = controller.getNationInstance(majorNation);
        Region region =  controller.getLandRegion(minorNation);
        ArrayList<MilitaryUnit> units = region.getOccupyingUnits();

        if (annexer.getPaps() < 1)
            return false;
        if ( region.getType() == Region.LAND_REGION )
            if ( ((LandRegion)region).getControllingNation() >= Nation.FRANCE
                || ((LandRegion)region).getControllingNation() <= Nation.SPAIN )
                    return false;
        else
            return false;

        for (MilitaryUnit u : units)
            if (u.getOwningNation() == majorNation)
                return true;

        return false;
    }

    public void annexMinorNation(int majorNation, int minorNation) {
        //canAnnexMinorNation()
            //Remove 1 pap from majorNation
            //send request to server to attempt annexation
    }

    public boolean canRestoreRegion(int majorNation, int region) {
        NationInstance restorer = controller.getNationInstance(majorNation);
        Region enemyRegion =  controller.getLandRegion(region);

        //has a pap.
        if (restorer.getPaps() < 1)
            return false;

        //is LandRegion and MajorNation owned.
        if ( enemyRegion.getType() == Region.LAND_REGION )
            if ( ((LandRegion)enemyRegion).getControllingNation() <= Nation.FRANCE
                || ((LandRegion)enemyRegion).getControllingNation() >= Nation.SPAIN )
                    return false;
        else
            return false;

        NationInstance controllingNation = controller.getNationInstance(((LandRegion)enemyRegion).getControllingNation());

        //is at war with controllingNation (also confirms not same nation).
        //Is not a homeland of controllingNation.
        if ( !restorer.isEnemy(controllingNation.getNationNumber()) )
            return false;
        for (LandRegion r : controllingNation.getHomelands())
            if (r.equals(enemyRegion))
                return false;

        int restoringUnitsOccupying = 0;
        int controllingUnitsOccupying = 0;
        ArrayList<Integer> allies = new ArrayList<Integer>();
        for (int i = Nation.FRANCE; i < Nation.GLOBAL; i++)
            if (controllingNation.isAllied(i))
                allies.add(i);

        for(MilitaryUnit u : enemyRegion.getOccupyingUnits()) {
            if (u.getOwningNation() == majorNation)
                restoringUnitsOccupying++;
            if (u.getOwningNation() == controllingNation.getNationNumber())
                controllingUnitsOccupying++;
            if (allies.contains(u.getOwningNation()))
                controllingUnitsOccupying++;
        }
        //has at least 3 units in region and no enemy (or any allies of enemy) units in region.
        return restoringUnitsOccupying >= 3 && controllingUnitsOccupying <= 0;
    }

    public void restoreRegion(int majorNation , int region) {
        //canRestoreRegion()
            //Remove 1 pap from majorNation
            //send request to server to attempt restoration
    }

    public boolean canAbandonRegion(int majorNation, int region) {
        //Check is majorNation controls region
        //Check if majorNation has required PAPs (1 is pc, 2 if npc)
        NationInstance abandoner = controller.getNationInstance(majorNation);
        Region abandonee =  controller.getLandRegion(region);
        ArrayList<Integer> controlledNations = controller.getUserControlledNPNs();

        if ( abandonee.getType() == Region.LAND_REGION )
            if ( ((LandRegion)abandonee).getControllingNation() <= Nation.FRANCE
                    || ((LandRegion)abandonee).getControllingNation() >= Nation.SPAIN )
                return false;
            else
                return false;

        int abandoneeNation = ((LandRegion)abandonee).getControllingNation();

        if ( abandoneeNation == abandoner.getNationNumber() )
            if ( abandoner.getPaps() < 1)
                return false;
        else if ( controlledNations.contains(abandoneeNation) )
            if ( abandoner.getPaps() < 2)
                return false;
        else
            return false;
        return true;
    }

    public void abandonRegion(int majorNation, int region) {
        //canAbandonRegion()
            //Tell server
    }

    public boolean canFomentUprising(int majorNation, int region) {
        //Check paps >= 1 for majorNation
        //Check region is not in uprising
        //Check if region is legal (Ireland, Vendee, Don Basin, Arabia, foreign-owned homeland, non-homeland region (other than Gibraltar)
        //Can be done in secret.. PAPs are not public knowledge..
        NationInstance fomenter = controller.getNationInstance(majorNation);

        if ( controller.getLandRegion(region).getType() == Region.LAND_REGION ) {
            LandRegion fomentee = ((LandRegion)controller.getLandRegion(region));
            int fomenteeNationNumber = fomentee.getControllingNation();

            if (fomentee.isUprising())
                return false;

            if (controller.game.getGameMode() == LobbyConstants.TEAM)
                if ( fomenter.getAllies().contains(fomenteeNationNumber) )
                    return false;
            if (fomenter.getNationNumber() == fomenteeNationNumber)
                return false;
            if (controller.getUserControlledNPNs().contains(fomenteeNationNumber))
                return false;

            //Region Check
            NationInstance fomenteeNation = controller.getNationInstance(fomenteeNationNumber);
            if (fomenteeNation.getHomelands().contains(fomentee))
                if (!fomentee.equals(controller.getRegion(GameMsg.getString("Ireland")))
                        || !fomentee.equals(controller.getRegion(GameMsg.getString("Vendee")))
                        || !fomentee.equals(controller.getRegion(GameMsg.getString("DonBasin")))
                        || !fomentee.equals(controller.getRegion(GameMsg.getString("Arabia"))))
                return false;

            if (fomentee.equals(controller.getRegion(GameMsg.getString("Gibraltar"))))
                return false;

        } else
            return false;

        return true;
    }

    public void fomentUprising(int majorNation, int region) {
        //canFomentUprising()
            //Tell Server
    }

    public boolean canSuppressUprising(int majorNation, int region) {
        //Check region is in uprising
        //Are at least two (2) units in region
        NationInstance suppresser = controller.getNationInstance(majorNation);
        Region uprisingRegion = controller.getLandRegion(region);
        int unitsOccupying = 0;

        if ( uprisingRegion.getType() == Region.LAND_REGION )
            if ( ((LandRegion) uprisingRegion).getControllingNation() == suppresser.getNationNumber() )
                for ( MilitaryUnit u : uprisingRegion.getOccupyingUnits() )
                    if ( u.getOwningNation() == suppresser.getNationNumber() )
                        unitsOccupying++;

        return unitsOccupying >= 2;
    }

    public void suppressUprising(int majorNation, int region) {
        //canSuppressUprising()
            //Send request to server to attempt suppress uprising
    }

    public boolean canGrantRightOfPassage(int grantingNation, int receivingNation) {
        //Check grantingNation and receivingNation are neutral
        //Check reverse grace period
        //Nations that have sued for peace can only grant right of passage to former enemies.
        //Nations can be granted right of passage when retreating, except after an annexation battle..
        NationInstance granter = controller.getNationInstance(grantingNation);
        NationInstance receiver = controller.getNationInstance(receivingNation);

        if ( !granter.isControlledNation() || !receiver.isControlledNation() )
            return false;

        if (!granter.isNeutral(receivingNation))
            return false;

        if (granter.inReverseGraceWith(receivingNation))
            return false;

        if (granter.getRightOfPassages().contains(receivingNation))
            return false;

        //if (after Annexing Battle) todo
//            return false;

        return true;
    }

    public void grantRightOfPassage(int grantingNation, int receivingNation) {
        new GrantPassageThread(controller.getGameId(), grantingNation, receivingNation, true).start();
    }

    public boolean canRescindRightOfPassage(int rescindingNation, int receivingNation) {
        //Were rights of passage granted voluntarily
        NationInstance rescinder = controller.getNationInstance(rescindingNation);
        return rescinder.hasRightOfPassage(receivingNation) && rescinder.isPassageVoluntary(receivingNation);
    }

    public void rescindRightOfPassage(int rescindingNation, int losingNation) {
        new RescindPassageThread(controller.getGameId(), rescindingNation, losingNation).start();
    }

    public boolean canEnforceContinentalSystem(int majorNation) {
        //Is majorNation France
        //Is start of Production round
        //Check paps >= 1 for majorNation
        //Is Britain only major nation at war with France
        //Has enforced < 2 for this production round
        NationInstance france = controller.getNationInstance(majorNation);

        if (france.getNationNumber() != Nation.FRANCE)
            return false;

        if (france.getEnemies().size() != 1)
            return false;

        if (!france.getEnemies().contains(Nation.GREAT_BRITAIN))
            return false;

        if (france.getPaps() < 1)
            return false;

        //noinspection SimplifiableIfStatement
        if (controller.game.getStep() != GameInstance.CONTINENTAL_SYSTEM)
            return false;

        return numOfCSEnforcedThisTurn >= 2;
    }

    public void enforceContinentalSystem(int majorNation) {
        //canEnforceContinentalSystem()
            //Tell Server
    }

    public boolean canControlNPN(int majorNation, int npNation) {
        //Check paps >= 2 (or more) for majorNation
        //Check npNation is not a player nation
        NationInstance playerNation = controller.getNationInstance(majorNation);
        NationInstance nonPlayerNation = controller.getNationInstance(npNation);

        if (controller.game.getUserByNation(majorNation).getControlledNPNs().contains(npNation))
            return false;  //majorNation cannot already control NPN

        if (!playerNation.isPlayerNation())
            return false; //controlNPN can only be done with a player nation

        if ( nonPlayerNation.isPlayerNation() )
            return false;  //then this nation is a playerNation, can only control NPNs

        if (playerNation.getPaps() < 2)
            return false;  //2 paps are required for the initial action


        int npnPaps = nonPlayerNation.getPaps();
        ArrayList<Integer> userNations = new ArrayList<Integer>();
        userNations.add(majorNation);
        userNations.addAll(controller.getUserControlledNPNs());

        //2 paps are required for each armistice, NPN can use one of their pap for each armistice.
        for ( Integer n : userNations ) {
            if (nonPlayerNation.isEnemy(n)) {
                if (n == majorNation) {
                    if (npnPaps > 0) {
                        npnPaps--;
                        if (playerNation.getPaps() < 3)
                            return false;
                    } else if (playerNation.getPaps() < 4)
                        return false;
                } else {
                    if (npnPaps > 0) {
                        npnPaps--;
                        if (controller.getNationInstance(n).getPaps() < 1)
                            return false;
                    } else if (controller.getNationInstance(n).getPaps() < 2)
                        return false;
                }
            }
        }

        if (controller.game.getGameMode() == LobbyConstants.TEAM) {
            //todo team stuff
        }

        return true;
    }

    public void controlNPN(int playerNation, int nonPlayerNation) {
        if ( controller.getNationInstance(nonPlayerNation).isControlledNation() ) {
            //Send request to controller of nation.
            new ControlNPNThread(controller.getGameId(), playerNation, nonPlayerNation,
                    controller.getNationInstance(nonPlayerNation).isControlledNation(), 2).start();
        } else //Confirm and Roll
            controller.rollToControlNPN(playerNation, nonPlayerNation, false);
    }

    public boolean canReleaseNPN(int playerNation, int nonPlayerNation) {
        //Must be neutral to ALL major nations.
        //Imposes a 1-year grace period from playerNation, playerNations other controlledNPNs and playerNations Team nations.
        NationInstance nonPlayer = controller.getNationInstance(nonPlayerNation);

        if ( !controller.getUserControlledNPNs().contains(nonPlayerNation) || !nonPlayer.isControlledNation() )
            return false;

        for (Integer n : GameUtilities.getAllNationsAsInteger())
            if (!nonPlayer.isNeutral(n))
                return false;
        return true;
    }

    public void releaseNPN(int playerNation, int nonPlayerNation) {
        //todo
        new ReleaseNPNThread(controller.getGameId(), playerNation, nonPlayerNation).start();
    }
    /*
     * END Political Actions
     */
}