package game.controller.Unit;

import lobby.controller.Nation;
import game.controller.Region.Region;

/**
 * MilitaryUnit.java  Date Created: Nov 08, 2012
 *
 * Purpose: To store information related to a military unit.
 *
 * Description: This is a for all the specific military units to implement.
 *
 * @author Chrisb
 */
public abstract class MilitaryUnit {
    //General
    public int getCost() { return cost; }
    public int getType() { return type; }
    public int getOwningNation() { return owningNation; }

    //Movement
    public int getMovement() { return movement; }
    public boolean canForceMarch() { return canForceMarch; }
    public void resetMovement() { movesRemaining = movement; }
    public void useOneMovement() { movesRemaining--; }
    public int movesRemaining() { return movesRemaining; }
    public Region getLocation() { return location; }
    public boolean moveUnit(Region loc) {
        if (location != null)
            location.removeUnit(this);
        location = loc;
        return location.addUnit(this);
    }

    //Battle Methods
    public boolean canFire() { return canFire; }
    public boolean canCharge() { return canCharge; }
    public boolean canSkirmish() { return canSkirmish; }
    public boolean canCountercharge() { return canCountercharge; }
    public int getBattleActions() { return battleActions; }
    public int getDefence() { return defence; }
    public int getRange() { return range; }
    public boolean isSkirmishing() { return isSkirmishing; }
    public void resetBattleActions() { battleActionsRemaining = battleActions; }
    public void useOneBattleAction() { battleActionsRemaining--; }
    public int battleActionsRemaining() { return battleActionsRemaining; }

    private void setOwningNation(int nation) { owningNation = nation; }

    public static String getUnitName(int type) {
        switch (type) {
            case GENERAL: return "General";
            case ADMIRAL: return "Admiral";
            case INFANTRY: return "Infantry";
            case ELITE_INFANTRY: return "Elite Infantry";
            case CAVALRY: return "Cavalry";
            case HEAVY_CAVALRY: return "Heavy Cavalry";
            case IRREGULAR_CAVALRY: return "Irregular Cavalry";
            case ARTILLERY: return "Artillery";
            case HORSE_ARTILLERY: return "Horse Artillery";
            case MILITIA: return "Militia";
            case NAVAL_SQUADRON: return "Naval Squadron";
            case NATIONAL_HERO: return "National Hero";
            case PAP: return "Political Action Point";
        }
        return "UnknownUnit";
    }

    public static int getCost(int type, int nation) {
        switch (type) {
            case MilitaryUnit.GENERAL: return 12;
            case MilitaryUnit.INFANTRY: return nation == Nation.AUSTRIA_HUNGARY ? 5 : 6;
            case MilitaryUnit.ELITE_INFANTRY: return 8;
            case MilitaryUnit.CAVALRY: return 9;
            case MilitaryUnit.HEAVY_CAVALRY: return 11;
            case MilitaryUnit.IRREGULAR_CAVALRY: return 6;
            case MilitaryUnit.ARTILLERY: return nation == Nation.OTTOMANS ? 18 : 10;
            case MilitaryUnit.HORSE_ARTILLERY: return 13;
            case MilitaryUnit.MILITIA: return 3;
            case MilitaryUnit.ADMIRAL: return nation == Nation.GREAT_BRITAIN ? 18 : 25;
            case MilitaryUnit.NAVAL_SQUADRON: return 15;
            case MilitaryUnit.NATIONAL_HERO: return 12;
            case MilitaryUnit.PAP: return 10;
            default: return -1;
        }
    }

    public abstract String toString();

    protected int cost;
    protected int type;
    protected int owningNation;

    protected int movement;
    protected int movesRemaining;
    protected boolean canForceMarch;
    protected Region location;

    protected int range;
    protected int defence;
    protected int battleActions;
    protected boolean canFire;
    protected boolean canCharge;
    protected boolean canSkirmish;

    protected boolean canCountercharge;
    protected boolean isSkirmishing;
    protected int battleActionsRemaining;

    public static final int GENERAL = 0;
    public static final int ADMIRAL = 1;
    public static final int INFANTRY = 2;
    public static final int ELITE_INFANTRY = 3;
    public static final int CAVALRY = 4;
    public static final int HEAVY_CAVALRY = 5;
    public static final int IRREGULAR_CAVALRY = 6;
    public static final int ARTILLERY = 7;
    public static final int HORSE_ARTILLERY = 8;
    public static final int MILITIA = 9;
    public static final int NAVAL_SQUADRON = 10;
    public static final int NATIONAL_HERO = 11;
    public static final int PAP = 12;
}
