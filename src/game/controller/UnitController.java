package game.controller;

import game.controller.Region.LandRegion;
import game.controller.Region.SeaRegion;
import game.controller.Region.Region;
import game.controller.Region.Port;
import game.controller.Unit.MilitaryUnit;
import game.controller.Unit.NavalSquadron;
import game.util.GameLogger;

import java.util.ArrayList;

/**
 * UnitController.java  Date Created: 07 05, 2013
 *
 * Purpose: To what units can an can't do as well as their location on the map.
 *
 * Description:  This class shall contain all the logic for unit actions and movement.
 * The units themselves shall store of what actions and movements they have taken.
 * Units shall not be added to the controller until they are placed on the map.
 * If a unit is KIA then that unit shall be removed from the controller.
 * (Captured units shall remain in the controller and noted via something)
 *
 * @author Chris
 */
public class UnitController {

    public static boolean moveAllUnits(ArrayList<MilitaryUnit> units, Region region) {
        if (units.get(0).getType() == MilitaryUnit.NAVAL_SQUADRON || units.get(0).getType() == MilitaryUnit.ADMIRAL) {
            return moveAllSeaUnits(units, (SeaRegion)region);
        } else {
            return moveAllLandUnits(units, (LandRegion)region);
        }
    }

    public static boolean moveAllLandUnits(ArrayList<MilitaryUnit> units, LandRegion region) {
        for (MilitaryUnit unit : units)
            if (!moveLandUnit(unit, region))
                return false;
        return true;
    }

    public static boolean moveLandUnit(MilitaryUnit unit, LandRegion region) {
        try {
            if ( unit.getType() == MilitaryUnit.NAVAL_SQUADRON || unit.getType() == MilitaryUnit.ADMIRAL )
                throw new UnitTypeException(unit.getType(), unit.getOwningNation());
            return unit.moveUnit(region);
        } catch (UnitTypeException e) {
            GameLogger.log("UnitController.moveLandUnit - " + e.toString());
            return false;
        }
    }

    public static boolean moveAllSeaUnits(ArrayList<MilitaryUnit> units, SeaRegion region) {
        for (MilitaryUnit unit : units)
            if (!moveSeaUnit(unit, region))
                return false;
        return true;
    }

    public static boolean moveSeaUnit(MilitaryUnit unit, SeaRegion region) {
        try {
            if ( unit.getType() != MilitaryUnit.NAVAL_SQUADRON && unit.getType() != MilitaryUnit.ADMIRAL )
                throw new UnitTypeException(unit.getType(), unit.getOwningNation());
            return unit.moveUnit(region);
        } catch (UnitTypeException e) {
            GameLogger.log("UnitController.moveSeaUnit - " + e.toString());
            return false;
        }
    }

    public static boolean isPortClosedTo(NavalSquadron ns, Port p, GameInstance game) {
        LandRegion portRegion = p.getPortRegion();
        NationInstance portNation = game.getNation(portRegion.getControllingNation());
        NationInstance nationInPort = game.getNation(ns.getOwningNation());
        //Squadron Move Rule #5
        if ( nationInPort.isEnemy(portRegion.getControllingNation()) ) {
            boolean alliesOccupy = false;
            boolean enemiesOccupy = false;
            for (MilitaryUnit u : portRegion.getOccupyingUnits()) {
                if ( nationInPort.isEnemy(u.getOwningNation()) )
                    enemiesOccupy = true;
                if ( (nationInPort.getNationNumber() == u.getOwningNation()) )
                    alliesOccupy = true;
                if ( nationInPort.isAllied(u.getOwningNation()) && portNation.isEnemy(u.getOwningNation()) )
                    alliesOccupy = true;
                //else
                //  enemiesOccupy = true;
            }

            if (!alliesOccupy || enemiesOccupy)
                return true;
        }

        return false;
    }

    //Before moving check any/all move conditions, are units in a region where they no longer have right of passage.
    //Withdrawing - Done at start of movement

    //After moving a units check if movingNation received non-voluntary right of passage from a nation and are no longer in their territory.


    private static class UnitTypeException extends Exception {
        int type, nation;
        private UnitTypeException(int type, int nation) { this.type = type; this.nation = nation; }
        public String toString() { return "Incorrect unit type found: " + type + " owningNation: " + nation; }
    }
}
