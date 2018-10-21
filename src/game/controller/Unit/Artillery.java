package game.controller.Unit;

import lobby.controller.Nation;

/**
 * Artillery.java  Date Created: Nov 8, 2012
 *
 * Purpose: To represent one Artillery  unit.
 *
 * Description:
 *
 * @author Chrisb
 */
public class Artillery extends MilitaryUnit {
    public Artillery(int owningNation) {
        this.owningNation = owningNation;
        if (owningNation == Nation.OTTOMANS)
            cost = 18;
        else
            cost = 10;
        movement = 1;
        battleActions = 1;
        defence = 1;
        range = 2;
        canFire = true;
        canCharge = false;
        canSkirmish = false;
        canForceMarch = false;
        canCountercharge = false;
        type = ARTILLERY;

        isSkirmishing = false;
        movesRemaining = movement;
        battleActionsRemaining = battleActions;
    }

    public String toString() { return "Artillery"; }
}