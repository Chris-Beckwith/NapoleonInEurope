package game.controller.Unit;

import lobby.controller.Nation;

/**
 * Admiral.java  Date Created: Nov 8, 2012
 *
 * Purpose: To represent one Admiral unit.
 *
 * Description:
 *
 * @author Chrisb
 */
public class Admiral extends NavalSquadron {
    public Admiral(int owningNation) {
        this.owningNation = owningNation;
        if (owningNation == Nation.GREAT_BRITAIN)
            cost = 18;
        else
            cost = 25;
        movement = 0;
        battleActions = 0;
        defence = 0;
        range = 0;
        canFire = false;
        canCharge = false;
        canSkirmish = false;
        canForceMarch = false;
        canCountercharge = false;
        type = ADMIRAL;

        isSkirmishing = false;
        movesRemaining = movement;
        battleActionsRemaining = battleActions;
    }

    public Admiral getAdmiral() { return this; }
    public void setAdmiral(Admiral admiral) { /*Do Nothing*/ }

    public String toString() { return "Admiral"; }
}