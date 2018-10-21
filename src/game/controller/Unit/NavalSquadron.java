package game.controller.Unit;

/**
 * NavalSquadron.java  Date Created: Nov 8, 2012
 *
 * Purpose: To represent one Naval Squadron.
 *
 * Description:
 *
 * @author Chrisb
 */
public class NavalSquadron extends MilitaryUnit {
    public NavalSquadron(int owningNation) {
        this.owningNation = owningNation;
        cost = 15;
        movement = 0;
        battleActions = 0;
        defence = 0;
        range = 0;
        canFire = false;
        canCharge = false;
        canSkirmish = false;
        canForceMarch = false;
        canCountercharge = false;
        type = NAVAL_SQUADRON;

        isSkirmishing = false;
        movesRemaining = movement;
        battleActionsRemaining = battleActions;

        admiral = null;
    }

    public NavalSquadron() {
    }

    public Admiral getAdmiral() { return admiral; }
    public void setAdmiral(Admiral admiral) { this.admiral = admiral; }
    public void clearAdmiral() { admiral = null; }

    public String toString() { return "Naval Squadron"; }

    private Admiral admiral;
}