package game.util;

import game.controller.*;
import game.controller.Unit.MilitaryUnit;
import game.controller.Region.*;
import game.client.political.MoveNPNUnitsThread;
import lobby.controller.User;
import lobby.controller.Nation;

import java.util.ArrayList;

/**
 * PoliticalUtilities.java  Date Created: Oct 29, 2013
 *
 * Purpose: To provide utility methods for political actions.
 *
 * Description:  Any reusable methods shall be placed here for public use through out the project.
 *
 * @author Chrisb
 */
public final class PoliticalUtilities {

    /**
     * This will determine which user(s) are at war with a declaringNation.
     * @param declaringNation - declaringNation in question.
     * @param nationEnemies - enemies of declaringNation.
     * @param allUsers - all users in game.
     * @return - The user(s) at war with declaringNation.
     */
    public static ArrayList<User> getUsersAtWarWith(int declaringNation, ArrayList<Integer> nationEnemies, ArrayList<User> allUsers) {
        ArrayList<User> group = new ArrayList<User>();

        for (User u : allUsers) {
            //Make sure user is not the same user declaringNation
            if (u.getNation() != declaringNation && !u.getControlledNPNs().contains(declaringNation)) {
                //If it's users primary nation add to group
                if (nationEnemies.contains(u.getNation()))
                    group.add(u);

                //If it's users secondary nation add to group, if not in group already.
                for (Integer n : u.getControlledNPNs())
                    if (nationEnemies.contains(n))
                        if (!group.contains(u))
                            group.add(u);
            }
        }

        return group;
    }

    /**
     * This method will take a list of users and sort them based on the sizes of their controlled nations.
     * If there are any ties it will randomize those as well.  todo add functionality for ties of NOT the smallest.
     * @param group - list of users to be sorted.
     * @param controller - GameController used to get nationInstances.
     * @return - returns a sorted list of the users main nation.
     */
    public static ArrayList<Integer> sortUsersBySize(ArrayList<User> group, GameController controller) {
        int totalUnits;
        ArrayList<Integer> userSizes = new ArrayList<Integer>();
        ArrayList<Integer> groupUserNations = new ArrayList<Integer>();

        for (User u : group) {
            //Main Nation
            totalUnits = controller.getNationInstance(u.getNation()).getMilitary().size();

            //Controlled NPNs
            for (Integer n : u.getControlledNPNs())
                totalUnits += controller.getNationInstance(n).getMilitary().size();

            //Add to array
            userSizes.add(totalUnits);
            groupUserNations.add(u.getNation());
        }

        //Sort by size (using bubble sort)
        boolean swapped = true;
        int n = userSizes.size();

        while (swapped) {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if ( userSizes.get(i - 1) > userSizes.get(i) ) {
                    //swap values
                    userSizes.add(i - 1, userSizes.remove(i));
                    groupUserNations.add(i - 1, groupUserNations.remove(i));
                    swapped = true;
                }
            }
            n -= 1;
        }

        //Check if there is a tie for smallest nation(s).
        int smallest = userSizes.get(0);
        int subListIndex = 0;
        for (int i = 1; i < userSizes.size(); i++) {
                subListIndex = i;
            if (userSizes.get(i) > smallest)
                break;
            else
                subListIndex++;
        }

        for (int i = 1; i < subListIndex; i++) {
            int index = (int) ( Math.random() * (subListIndex + 1 - i ) ) + i - 1;
            userSizes.add(i - 1, userSizes.remove(index));
            groupUserNations.add(i - 1, groupUserNations.remove(index));
        }

        return groupUserNations;
    }

    public static int getWarDecPapCost(int declaringNation, int enemyNation, GameController controller) {
        NationInstance declarer = controller.getNationInstance(declaringNation);
        NationInstance enemy = controller.getNationInstance(enemyNation);
        int papCost = 2;

        for (int n : GameUtilities.getAllNationsAsInteger() )
            if ( declarer.isAllied(n) && enemy.isEnemy(n) )
                papCost = 1;

        return papCost;
    }

    public static ArrayList<Integer> getNationsThatCanAlly(PoliticalController political, Integer nation, ArrayList<Integer> potentialNations) {
        ArrayList<Integer> nationsThatCanAlly = new ArrayList<Integer>();
        for (Integer n : potentialNations)
            if ( !n.equals(nation) && political.canFormAlliance(nation, n) )
                nationsThatCanAlly.add(n);

        return nationsThatCanAlly;
    }

    public static ArrayList<LandRegion> getCongressAnnexRegions(NationInstance sueingNation, NationInstance congressNation) {
        ArrayList<LandRegion> annexableRegions = new ArrayList<LandRegion>();
        boolean haveNonHomeland = false;

        //Check if homeland or non-homeland.
        for ( LandRegion region : sueingNation.getTerritory() ) {
            if ( !sueingNation.getHomelands().contains(region) ) {
                haveNonHomeland = true;
                if ( region.isHomeland() ) {
                    boolean alliedHomeland = false;

                    for (Integer ally : congressNation.getAllies())
                        if ( congressNation.getHomelandsOfNation(ally).contains(region) )
                            alliedHomeland = true;

                    //todo check teammates

                    if (!alliedHomeland)
                        annexableRegions.add(region);
                } else
                    annexableRegions.add(region);
            }
        }

        if (!haveNonHomeland)
            return sueingNation.getTerritory();
        else
            return annexableRegions;
    }

    public static ArrayList<LandRegion> getForcedRestoreRegions(NationInstance sueingNation, NationInstance congressNation) {
        ArrayList<LandRegion> forcedRestoreRegions = new ArrayList<LandRegion>();

        //congressNation homeland controlled by sueingNation
        for ( LandRegion region : congressNation.getHomelands() )
            if ( region.getControllingNation() == sueingNation.getNationNumber() )
                forcedRestoreRegions.add(region);

        return forcedRestoreRegions;
    }

    public static ArrayList<LandRegion> getCongressRestoreRegions(NationInstance sueingNation) {
        ArrayList<LandRegion> restoreRegions = new ArrayList<LandRegion>();

        //Non-Homeland sueingNation controlled territory
        for ( LandRegion region : sueingNation.getTerritory() )
            if ( !sueingNation.getHomelands().contains(region) )
                restoreRegions.add(region);

        return restoreRegions;
    }

    public static ArrayList<LandRegion> getCongressFreeSerfRegions(NationInstance sueingNation, NationInstance congressNation) {
        ArrayList<LandRegion> freeSerfableRegions = new ArrayList<LandRegion>();

        if (sueingNation.getNationNumber() != Nation.RUSSIA)
            return freeSerfableRegions;

        //Is homeland of the sueingNation(Russia) and occupied by congressNation troops.
        for ( LandRegion region : sueingNation.getTerritory() )
            if ( sueingNation.getHomelands().contains(region) )
                for ( MilitaryUnit unit : region.getOccupyingUnits() )
                    if ( !freeSerfableRegions.contains(region) && unit.getOwningNation() == congressNation.getNationNumber() )
                        freeSerfableRegions.add(region);

        return freeSerfableRegions;
    }

    public static ArrayList<Integer> doSueForPeace(int sueingNationNumber, GameInstance game) {
        NationInstance sueingNation = game.getNation(sueingNationNumber);
        ArrayList<Integer> oldEnemies = sueingNation.getEnemies();
        //Cost to sue for peace
        sueingNation.minusPaps(3);

        //End all alliance
        for (int n : sueingNation.getAllies()) {
            game.getNation(n).breakAlliance(sueingNationNumber, false);
            sueingNation.breakAlliance(n, false);
        }

        //Rescind Right of Passages sueingNation has received
        for (int n : GameUtilities.getAllNationsAsInteger())
            game.getNation(n).rescindRightOfPassage(sueingNationNumber);

        //Remove all Right of Passages sueingNation has granted
        sueingNation.removeAllRightOfPassages();

        //If NPN, set to uncontrolled
        if (!sueingNation.isPlayerNation()) {
            User controllingUser = game.getUserByNPN(sueingNationNumber);
            controllingUser.removeControlledNPN(sueingNationNumber);
            sueingNation.setLastNationToControl(controllingUser.getNation());
            sueingNation.setControlledNation(false, null);
        }

        //Set grace/reverse grace periods
        for (int n : sueingNation.getEnemies()) {
            sueingNation.addGracePeriod(n, game.getMonth(), game.getYear() + 1);
            if (sueingNation.getNationNumber() != Nation.SPAIN) {
                sueingNation.addReverseGrace(n, GameUtilities.getMonthPlusSixMonths(game.getMonth()),
                        GameUtilities.getYearPlusSixMonths(game.getMonth(), game.getYear()));
            }
        }

        //Remove Enemies
        sueingNation.removeAllEnemies();
        for (int n : GameUtilities.getAllNationsAsInteger())
            if ( n != sueingNationNumber && game.getNation(n).getEnemies().contains(sueingNationNumber) )
                game.getNation(n).removeEnemy(sueingNationNumber);

        //Reset Captured Capital todo
        //Reset SueingNations Captured Capitals
        //Remove markers sueingNation has on other Capitals

        //Reset Commitment Roll todo

        //Return National Hero so it is available for purchase todo

        //Team Stuff todo

        return oldEnemies;
    }

    public static ArrayList<Integer> spoilsOfWar(ArrayList<Integer> congressNations, NationInstance sueingNation) {
        ArrayList<Integer> startingWarNations = sueingNation.getStartingWarNations();
        ArrayList<Integer> firstNations = new ArrayList<Integer>();
        if ( startingWarNations.size() > 0 ) {
            firstNations.addAll(startingWarNations);
            if (congressNations.size() > startingWarNations.size())
                firstNations.add(congressNations.get(startingWarNations.size()));
            else
                firstNations.add(-1);
        } else {
            firstNations.add(congressNations.get(0));
            if (congressNations.size() > 1)
                firstNations.add(congressNations.get(1));
            else
                firstNations.add(-1);
        }
        return firstNations;
    }

    public static void moveNPNMilitary(NationInstance sueingNation, GameInstance game, GameController controller) {
        //If NPN, move military pieces back to captials/homelands/friendly
        //All Nation's army pieces are moved to capital region(s)
        //Or homeland regions if no capitals
        //Or friendly regions outside homeland
        ArrayList<LandRegion> tempCapitals = new ArrayList<LandRegion>();
        ArrayList<LandRegion> tempHomelands = new ArrayList<LandRegion>();
        ArrayList<LandRegion> tempNonHomelands = new ArrayList<LandRegion>();
        for (LandRegion region : sueingNation.getTerritory()) {
            if ( region.isCapital() && ((CapitalRegion)region).getCaptialStatus() == CapitalRegion.LIBERATED )
                tempCapitals.add(region);
            else if ( region.isHomeland() && region.getHomelandNation() == region.getControllingNation() )
                tempHomelands.add(region);
            else
                tempNonHomelands.add(region);
        }

        ArrayList<LandRegion> regionsToMoveUnits = new ArrayList<LandRegion>();
        if (tempCapitals.size() > 0)
            regionsToMoveUnits.addAll(tempCapitals);
        else if (tempHomelands.size() > 0)
            regionsToMoveUnits.addAll(tempHomelands);
        else
            regionsToMoveUnits.addAll(tempNonHomelands);

        if (regionsToMoveUnits.size() == 1) //Auto-Move army units.
            UnitController.moveAllLandUnits(sueingNation.getArmy(), regionsToMoveUnits.get(0));

        //All Naval pieces are moved to homeland port(s) that can be reached in non-winter sea movement
        //Or friendly region ports if no homeland ports
        //Or moved out of port to adjacent sea area if no friendly ports
        //Or left in the sea area they are in.
        ArrayList<Port> portsToMoveInTo;
        ArrayList<Port> portsToMoveOutOf = new ArrayList<Port>();
        if (sueingNation.getHomelandPorts().size() > 0)
            portsToMoveInTo = sueingNation.getHomelandPorts();
        else if (sueingNation.getNonHomelandPorts().size() > 0)
            portsToMoveInTo = sueingNation.getNonHomelandPorts();
        else
            portsToMoveInTo = new ArrayList<Port>();

        if (portsToMoveInTo.size() == 1) { //Auto-Move naval units. Only one Port to move too.
            UnitController.moveAllSeaUnits(sueingNation.getNavy(), portsToMoveInTo.get(0));
        } else if (portsToMoveInTo.size() == 0) {
            //Stay put or move out of port, if in port.
            for (MilitaryUnit ns : sueingNation.getNavy()) {
                Region location = game.getMap().getRegion(ns.getLocation().toString());
                if ( location.getType() == Region.PORT_REGION ) {
                    if ( ((Port)location).getSeaAdjacencies().length > 1 )
                        portsToMoveOutOf.add((Port)location);
                    else
                        UnitController.moveSeaUnit(ns, ((Port)location).getSeaAdjacencies()[0] );
                }
            }
        }

        //Start Thread to move the required units if necessary.
        if (controller != null) {
            new MoveNPNUnitsThread(controller.getGameId(), sueingNation.getNationNumber(),
                    GameUtilities.getLandRegionsAsIntegers(regionsToMoveUnits, game),
                    GameUtilities.getPortsAsIntegers(portsToMoveInTo, game),
                    GameUtilities.getPortsAsIntegers(portsToMoveOutOf, game)).start();
        }
    }

    public static void endPeaceCongress(NationInstance sueingNation, GameInstance game, GameController controller) {
        //Give Right of Passage - it ends when sueingNation reverseGrace ends or pieces leave sueingNation's regions
        int sueNation = sueingNation.getNationNumber();
        for (LandRegion region : sueingNation.getTerritory()) {
            for (MilitaryUnit unit : region.getOccupyingUnits()) {
                if ( unit.getOwningNation() != sueNation && !sueingNation.hasRightOfPassage(unit.getOwningNation()) ) {
                    if (controller != null)
                        controller.doGrantPassage(sueNation, unit.getOwningNation(), false);
                    else
                        doGrantPassage(sueingNation, unit.getOwningNation(), false);
                }
            }
        }

        leaveClosedPortCheck(game);

        //todo
        //Leaving Closed Port causes - Intercepting Squadrons
        //Withdrawing
        //liberate capital - Capturing Capitals
    }

    public static void leaveClosedPortCheck(GameInstance game) {
        //Leaving Closed Port - Done immediately after action that caused it, no other action except RAISING MILITIA.
        for (Port p : game.getMap().getPorts()) {
            for (MilitaryUnit u : p.getOccupyingUnits()) {
                
            }
        }

        //First to leave closed port is most hostile (Enemy, neutral w/o Right of Passage, neutral w/ Right of Passage, minor nation, ally, friendly)
        //Recheck conditions after each nation's fleet leaves as the port may longer be closed.
        //All squadrons of the leaving nation must leave at the same time. Roll one die for each squadron on 1 or 2 it is eliminated.
        //Leaving fleet may not form combined fleet
        //Chance to intercept a fleet leaving closed port is same as open (leaving nation is moving nation, enemy fleet in sea area is non-moving)
        //Leaving fleet may NOT try to intercept a fleet already in the sea area.

    }

    //Congress Helper Methods
    public static boolean hasRegionsOutsideHomeland(NationInstance nation) {
        for (LandRegion r : nation.getTerritory())
            if (!nation.getHomelands().contains(r))
                return true;

        return false;
    }

    public static ArrayList<LandRegion> getRegionsToFreeSerfs(NationInstance sueingNation, NationInstance congressNation) {
        if (sueingNation.getNationNumber() != Nation.RUSSIA)
            return new ArrayList<LandRegion>();

        ArrayList<LandRegion> freeSerfRegions = new ArrayList<LandRegion>();

        for ( LandRegion land : sueingNation.getTerritory() )
            if (sueingNation.getHomelands().contains(land) && land.getNationsUnits(congressNation.getNationNumber()).size() > 0 && !land.isUprising())
                freeSerfRegions.add(land);

        return freeSerfRegions;
    }


    //Remove 1 pap from each nation or 2 pap from one nation.
    //Remove nationOne from nationTwo's atWar list.  Remove nationTwo from nationOne's atWar list.
    public static void doConcludeArmistice(NationInstance nationOne, NationInstance nationTwo) {
        int nationOnePapCost = 0, nationTwoPapCost = 0;
        if (nationOne.getPaps() > 0 && nationTwo.getPaps() > 0) {
            nationOnePapCost = 1;
            nationTwoPapCost = 1;
        } else if (nationOne.getPaps() == 0)
            nationTwoPapCost = 2;
        else
            nationOnePapCost = 2;

        nationOne.concludeArmistice(nationTwo.getNationNumber(), nationOnePapCost);
        nationTwo.concludeArmistice(nationOne.getNationNumber(), nationTwoPapCost);
    }

    public static void doBreakAlliance(NationInstance breakingNation, NationInstance alliedNation) {
        breakingNation.breakAlliance(alliedNation.getNationNumber(), true);
        alliedNation.breakAlliance(breakingNation.getNationNumber(), false);
    }

    public static void doGrantPassage(NationInstance grantingNation, int receivingNation, boolean isVoluntary) {
        grantingNation.grantRightOfPassage(receivingNation, isVoluntary);
    }

    public static void doRescindPassage(NationInstance rescindingNation, int losingNation) {
        rescindingNation.rescindRightOfPassage(losingNation);
    }

    public static boolean doControlNPN(NationInstance nonPlayerNation, User userToControl, User userInController, User prevUser) {
        boolean hasBeenControlled = nonPlayerNation.hasBeenControlled();
        
        userToControl.addControlledNPN(nonPlayerNation.getNationNumber());
        nonPlayerNation.setControlledNation(true, userToControl.getUserName());

        if (userInController != null)
            userInController.addControlledNPN(nonPlayerNation.getNationNumber());

        if (prevUser != null)
            prevUser.removeControlledNPN(nonPlayerNation.getNationNumber());

        return hasBeenControlled;
    }

    public static void doReleaseNPN(NationInstance nonPlayerNation, User controllingUser, User userInController, int month, int year) {
        nonPlayerNation.setControlledNation(false, null);
        controllingUser.removeControlledNPN(nonPlayerNation.getNationNumber());

        if (userInController != null)
            userInController.removeControlledNPN(nonPlayerNation.getNationNumber());

        //Imposes a 1-year grace period from playerNation, playerNations other controlledNPNs and playerNations Team nations.
        nonPlayerNation.addGracePeriod(controllingUser.getNation(), month, year+1);
        for (Integer n : controllingUser.getControlledNPNs())
            nonPlayerNation.addGracePeriod(n, month, year);

        //todo Team nations
    }

    public static void makeNationExtinct(NationInstance nation) {
        if (nation.isExtinct()) {
            //Lose all pieces
            nation.clearMilitary(); //Any national heroes lost this way are neither killed nor captured
            //Lose all production points
            nation.setProductionPoints(0);
            //Lose all PAP and PAP debt
            nation.setPaps(0);
            //Make Neutral to all Nations
            nation.makeNeutralToAll();
            //If was playerNation still playerNation
        } else
            GameLogger.log("PoliticalUtilities.makeNationExtinct: the nation was not actually extinct, nation - " + nation.getNationNumber());
    }

    public static void doCongressAnnex(NationInstance sueingNation, NationInstance actionNation, LandRegion region) {
        //Change ownership
        sueingNation.removeTerritory(region);
        actionNation.addTerritory(region);
        region.setControllingNation(actionNation.getNationNumber());
        //If region is Spanish Homeland add uprising if not already in uprising
        if (sueingNation.getHomelandsOfNation(Nation.SPAIN).contains(region) && !region.isUprising())
            region.fomentUprising();
    }

    public static void doCongressRestore(NationInstance sueingNation, LandRegion region) {
        //Remove ownership, if it is a major nations homeland return ownership to that nation.
        sueingNation.removeTerritory(region);

        if (region.isHomeland())
            region.setControllingNation(region.getHomelandNation());
        else
            region.setControllingNation(Nation.NEUTRAL);
    }

    public static void doCongressFreeSerfs(LandRegion region) {
        region.fomentUprising();
    }
}