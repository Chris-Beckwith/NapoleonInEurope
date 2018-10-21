package game.controller.Unit;

/**
 * HorseArtillery.java  Date Created: Nov 8, 2012
 *
 * Purpose: To represent one Horse Artillery unit.
 *
 * Description:
 *
 * @author Chrisb
 */
public class HorseArtillery extends MilitaryUnit {
    public HorseArtillery(int owningNation) {
        this.owningNation = owningNation;
        cost = 13;
        movement = 1;
        battleActions = 2;
        defence = 1;
        range = 2;
        canFire = true;
        canCharge = false;
        canSkirmish = false;
        canForceMarch = true;
        canCountercharge = false;
        type = HORSE_ARTILLERY;

        isSkirmishing = false;
        movesRemaining = movement;
        battleActionsRemaining = battleActions;

        hasFired = false;
    }

    public void fired() { hasFired = true; }
    public boolean hasFired() { return hasFired; }

    public void resetBattleActions() {
        hasFired = false;
        super.resetBattleActions();
    }

    public String toString() { return "Horse Artillery"; }

    private boolean hasFired;
}