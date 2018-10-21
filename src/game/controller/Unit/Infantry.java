package game.controller.Unit;

import lobby.controller.Nation;

/**
 * Infantry.java  Date Created: Nov 8, 2012
 *
 * Purpose: To represent one Infantry unit.
 *
 * Description:
 *
 * @author Chrisb
 */
public class Infantry extends MilitaryUnit {
    public Infantry(int owningNation) {
        this.owningNation = owningNation;
        if (owningNation == Nation.AUSTRIA_HUNGARY)
            cost = 5;
        else
            cost = 6;
        movement = 1;
        battleActions = 1;
        defence = 2;
        range = 1;
        canFire = true;
        canCharge = true;
        canSkirmish = true;
        canForceMarch = true;
        canCountercharge = false;
        type = INFANTRY;

        isSkirmishing = false;
        movesRemaining = movement;
        battleActionsRemaining = battleActions;
    }

    public String toString() { return "Infantry"; }
}