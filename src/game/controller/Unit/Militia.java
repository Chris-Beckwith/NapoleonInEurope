package game.controller.Unit;

/**
 * Militia.java  Date Created: Nov 8, 2012
 *
 * Purpose: To represent one Militia unit.
 *
 * Description:
 *
 * @author Chrisb
 */
public class Militia extends MilitaryUnit {
    public Militia(int owningNation) {
        this.owningNation = owningNation;
        cost = 3;
        movement = 1;
        battleActions = 1;
        defence = 1;
        range = 1;
        canFire = true;
        canCharge = true;
        canSkirmish = false;
        canForceMarch = false;
        canCountercharge = false;
        type = MILITIA;

        isSkirmishing = false;
        movesRemaining = movement;
        battleActionsRemaining = battleActions;
    }

    public String toString() { return "Militia"; }
}