package game.controller.Unit;

/**
 * Cavalry.java  Date Created: Nov 8, 2012
 *
 * Purpose: To represent one Cavalry unit.
 *
 * Description:
 *
 * @author Chrisb
 */
public class Cavalry extends MilitaryUnit {
    public Cavalry(int owningNation) {
        this.owningNation = owningNation;
        cost = 9;
        movement = 2;
        battleActions = 2;
        defence = 2;
        range = 1;
        canFire = false;
        canCharge = true;
        canSkirmish = false;
        canForceMarch = true;
        canCountercharge = false;
        type = CAVALRY;

        isSkirmishing = false;
        movesRemaining = movement;
        battleActionsRemaining = battleActions;
    }

    public String toString() { return "Cavalry"; }
}