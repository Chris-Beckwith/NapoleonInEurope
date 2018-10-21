package game.util;

import game.controller.NationInstance;
import game.controller.GameInstance;
import game.controller.Region.Region;
import game.controller.Region.LandRegion;
import game.controller.Region.Port;
import game.controller.Unit.*;
import game.packet.GameAddUnitsPacket;

import java.util.ArrayList;

import lobby.controller.Nation;

/**
 * GameUtilities.java  Date Created: Dec 08, 2013
 *
 * Purpose: To contain generic game methods in a central location.
 *
 * Description: This class will contain generic methods that will be used throughout a game.
 *
 * @author Chris
 */
public final class GameUtilities {

    public static void doPurchaseUnits(GameAddUnitsPacket addUnits, NationInstance nation) {
        int nationNumber = nation.getNationNumber();

        for (int i = 0; i < addUnits.getNumGenerals(); i++) {
            nation.addMilitaryUnit(new General(nationNumber));
            nation.minusProductionPoints(new General(nationNumber).getCost());
        }
        for (int i = 0; i < addUnits.getNumAdmirals(); i++) {
            nation.addMilitaryUnit(new Admiral(nationNumber));
            nation.minusProductionPoints(new Admiral(nationNumber).getCost());
        }
        for (int i = 0; i < addUnits.getNumInfantry(); i++) {
            nation.addMilitaryUnit(new Infantry(nationNumber));
            nation.minusProductionPoints(new Infantry(nationNumber).getCost());
        }
        for (int i = 0; i < addUnits.getNumEliteInf(); i++) {
            nation.addMilitaryUnit(new EliteInfantry(nationNumber));
            nation.minusProductionPoints(new EliteInfantry(nationNumber).getCost());
        }
        for (int i = 0; i < addUnits.getNumCavalry(); i++) {
            nation.addMilitaryUnit(new Cavalry(nationNumber));
            nation.minusProductionPoints(new Cavalry(nationNumber).getCost());
        }
        for (int i = 0; i < addUnits.getNumHeavyCav(); i++) {
            nation.addMilitaryUnit(new HeavyCavalry(nationNumber));
            nation.minusProductionPoints(new HeavyCavalry(nationNumber).getCost());
        }
        for (int i = 0; i < addUnits.getNumIrregularCav(); i++) {
            nation.addMilitaryUnit(new IrregularCavalry(nationNumber));
            nation.minusProductionPoints(new IrregularCavalry(nationNumber).getCost());
        }
        for (int i = 0; i < addUnits.getNumArtillery(); i++) {
            nation.addMilitaryUnit(new Artillery(nationNumber));
            nation.minusProductionPoints(new Artillery(nationNumber).getCost());
        }
        for (int i = 0; i < addUnits.getNumHorseArt(); i++) {
            nation.addMilitaryUnit(new HorseArtillery(nationNumber));
            nation.minusProductionPoints(new HorseArtillery(nationNumber).getCost());
        }
        for (int i = 0; i < addUnits.getNumMilitia(); i++) {
            nation.addMilitaryUnit(new Militia(nationNumber));
            nation.minusProductionPoints(new Militia(nationNumber).getCost());
        }
        for (int i = 0; i < addUnits.getNumNavalSquads(); i++) {
            nation.addMilitaryUnit(new NavalSquadron(nationNumber));
            nation.minusProductionPoints(new NavalSquadron(nationNumber).getCost());
        }
//            for (int i = 0; i < addAllUnits.getNumNationalHero(); i++) {
//                nation.addMilitaryUnit(new NationalHero(nationNumber));
//                nation.minusProductionPoints(new NationalHero(nationNumber).getCost());
//            }
        if (addUnits.getNumPaps() > 0) {
            nation.addPaps(addUnits.getNumPaps());
            nation.minusProductionPoints(
                    MilitaryUnit.getCost(MilitaryUnit.PAP, nationNumber) * addUnits.getNumPaps()
            );
        }
    }

    public static int getMonthPlusSixMonths(int month) { return ( month + 5 ) % 12 + 1; }
    public static int getYearPlusSixMonths(int month, int year) { return (month + 6) > 12 ? year + 1 : year; }

    public static ArrayList<Integer> getAllNationsAsInteger() {
        ArrayList<Integer> nations = new ArrayList<Integer>();
        nations.add(Nation.FRANCE);
        nations.add(Nation.GREAT_BRITAIN);
        nations.add(Nation.PRUSSIA);
        nations.add(Nation.RUSSIA);
        nations.add(Nation.OTTOMANS);
        nations.add(Nation.AUSTRIA_HUNGARY);
        nations.add(Nation.SPAIN);
        return nations;
    }

    public static ArrayList<Integer> getLandRegionsAsIntegers(ArrayList<LandRegion> regions, GameInstance game) {
        ArrayList<Integer> regionsAsIntegers = new ArrayList<Integer>();
        for (LandRegion r : regions)
            regionsAsIntegers.add( game.getMap().getRegionIndex(r) );

        return regionsAsIntegers;
    }

    public static ArrayList<Integer> getPortsAsIntegers(ArrayList<Port> ports, GameInstance game) {
        ArrayList<Integer> regionsAsIntegers = new ArrayList<Integer>();
        for (Port p : ports)
            regionsAsIntegers.add( game.getMap().getRegionIndex(p) );

        return regionsAsIntegers;
    }
}
