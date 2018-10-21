package game.controller.Unit;

/**
 * EliteInfantry.java  Date Created: Nov 8, 2012
 *
 * Purpose: To represent one Elite Infantry unit.
 *
 * Description:
 *
 * @author Chrisb
 */
public class EliteInfantry extends MilitaryUnit {
    public EliteInfantry(int owningNation) {
        this.owningNation = owningNation;
        cost = 8;
        movement = 1;
        battleActions = 1;
        defence = 3;
        range = 1;
        canFire = true;
        canCharge = true;
        canSkirmish = true;
        canForceMarch = true;
        canCountercharge = false;
        type = ELITE_INFANTRY;

        isSkirmishing = false;
        movesRemaining = movement;
        battleActionsRemaining = battleActions;
    }

    public String toString() { return "Elite Infantry"; }
}