package game.controller.Unit;

/**
 * HeavyCavalry.java  Date Created: Nov 8, 2012
 *
 * Purpose: To represent one Heavy Cavalry unit.
 *
 * Description:
 *
 * @author Chrisb
 */
public class HeavyCavalry extends MilitaryUnit {
    public HeavyCavalry(int owningNation) {
        this.owningNation = owningNation;
        cost = 11;
        movement = 2;
        battleActions = 2;
        defence = 3;
        range = 1;
        canFire = false;
        canCharge = true;
        canSkirmish = false;
        canForceMarch = false;
        canCountercharge = true;
        type = HEAVY_CAVALRY;

        isSkirmishing = false;
        movesRemaining = movement;
        battleActionsRemaining = battleActions;
    }

    public String toString() { return "Heavy Cavalry"; }
}