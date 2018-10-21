package game.controller.Unit;

/**
 * IrregularCavalry.java  Date Created: Nov 8, 2012
 *
 * Purpose: To represent one Irregular Cavalry unit.
 *
 * Description:
 *
 * @author Chrisb
 */
public class IrregularCavalry extends MilitaryUnit {
    public IrregularCavalry(int owningNation) {
        this.owningNation = owningNation;
        cost = 6;
        movement = 2;
        battleActions = 2;
        defence = 1;
        range = 1;
        canFire = false;
        canCharge = true;
        canSkirmish = false;
        canForceMarch = false;
        canCountercharge = true;
        type = IRREGULAR_CAVALRY;

        isSkirmishing = false;
        movesRemaining = movement;
        battleActionsRemaining = battleActions;
    }
    public String toString() { return "Irregular Cavalry"; }
}