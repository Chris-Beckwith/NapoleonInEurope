package game.controller.Unit;

/**
 * General.java  Date Created: Nov 8, 2012
 *
 * Purpose: To represent one General unit.
 *
 * Description: Yes, General?  Right away, General!
 *
 * @author Chrisb
 */
public class General extends MilitaryUnit {
    public General(int owningNation) {
        this.owningNation = owningNation;
        cost = 12;
        movement = 3;
        battleActions = 2;
        defence = 0;
        range = 0;
        canFire = false;
        canCharge = false;
        canSkirmish = false;
        canForceMarch = false;
        canCountercharge = false;
        type = GENERAL;

        isSkirmishing = false;
        movesRemaining = movement;
        battleActionsRemaining = battleActions;
    }

    public String toString() { return "General"; }
}