
package game.controller.Battle;

import game.controller.Unit.MilitaryUnit;

import java.util.ArrayList;

/**
 *
 * BattleZone.java  Time Created: 9/17/13 6:00 AM
 *
 * Author - DanP
 *
 * Purpose - BattleZone Class Definition
 *
 * Description - Represents a zone that units can occupy in battle
 *
 *
 **/

public class BattleZone {

    public BattleZone () {

        units = new ArrayList<BattleUnit>();

    }

    // The units in the Battle Zone
    private ArrayList<BattleUnit> units;

    // Returns all of the units in this Battle Zone
    public ArrayList<BattleUnit> getUnits () { return units; }

    // Add a unit - used for placement, movement, routing, killing
    public void add (BattleUnit unit) { units.add(unit); }

    // Remove a unit - used for movement, routing, killing
    public void remove (BattleUnit unit) { units.remove(unit); }

    // Adds all of the units in the given Battle Zone to this Battle Zone
    public void addAll (BattleZone battleZone) { units.addAll(battleZone.getUnits()); }

    // Returns the TeamInstance that controls the units in battleZone
    // Returns null if battleZone is empty
    public TeamInstance getOccupyingTeam () {

        if ( units.isEmpty() ) return null;
        return units.get(0).getOwningTeam();
    }

    // Returns true if this Battle Zone is empty
    // Returns true if the units are friendly to the given unit
    // Returns false if the units are hostile to the given unit
    public boolean isFriendly ( BattleUnit unit ) {

        return units.isEmpty() || unit.getOwningTeam() == getOccupyingTeam();
    }

    // Returns the number of units of the given type in this Battle Zone
    public int countUnitType ( int type ) {

        int count = 0;

        for (int i = 0; i < units.size(); i++)
            if (units.get(i).getType() == type) count++;

        return count;
    }

    // Returns the number of infantry in normal formation in this Battle Zone
    public int countNormalFormationInfantry () {

        int count = 0;

        for (int i = 0; i < units.size(); i++)
            if ( units.get(i).isInfantryNormalFormation() ) count++;

        return count;
    }

    // Returns the number of skirmishing infantry in this Battle Zone
    int countSkirmishers () {

        int count = 0;

        for (int i = 0; i < units.size(); i++)
            if ( units.get(i).isSkirmishing() ) count++;

        return count;
    }

    // Returns the number of squared infantry in this Battle Zone
    int countSquaredInfantry () {

        int count = 0;

        for (int i = 0; i < units.size(); i++)
            if ( units.get(i).isSquared() ) count++;

        return count;
    }


    // Counts the units in this Battle Zone for the purpose of sideways movement
    double getModifiedUnitCount () {

        // Generals do not count
        double count =
                countUnitType(MilitaryUnit.CAVALRY) +
                        countUnitType(MilitaryUnit.HEAVY_CAVALRY) +
                        countUnitType(MilitaryUnit.IRREGULAR_CAVALRY) +
                        countUnitType(MilitaryUnit.ARTILLERY) +
                        countUnitType(MilitaryUnit.HORSE_ARTILLERY);


        // Skirmishing infantry count as 3 units, but if there are skirmishers in the Battle Zone,
        //  all of the infantry that are not skirmishing are not counted in the Battle Zone
        int numberOfSkirmishers = countSkirmishers();
        if ( numberOfSkirmishers > 0 )
            count += 3 * numberOfSkirmishers;

        // If there are no skirmishers in the Battle Zone,
        // Normal infantry count as 1 unit, Squared infantry count as 1/2 a unit
        else
            count += countNormalFormationInfantry() + countSquaredInfantry() / 2;

        return count;
    }

}
