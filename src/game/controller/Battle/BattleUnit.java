
package game.controller.Battle;

import game.controller.Unit.MilitaryUnit;
import game.controller.NationInstance;

import java.util.ArrayList;

/**
 *
 * BattleUnit.java  Time Created: 9/17/13 6:00 AM
 *
 * Author - DanP
 *
 * Purpose - BattleUnit Class Definition
 *
 * Description - Represents a unit in a battle
 *
 *
 **/


public class BattleUnit {

    public BattleUnit ( MilitaryUnit militaryUnit, TeamInstance owningTeam ) {

        unit = militaryUnit;
        team = owningTeam;

        squared = false;
        skirmishing = false;
        facingForward = true;

    }

    public MilitaryUnit getMilitaryUnit () { return unit; }

    // Methods from MilitaryUnit

    public int getOwningNation() { return unit.getOwningNation(); }

    public int getType () { return unit.getType(); }

    public int getDefence () { return unit.getDefence(); }

    public int getBattleActions() { return unit.getBattleActions(); }
    public int battleActionsRemaining() { return unit.battleActionsRemaining(); }
    public void useOneBattleAction () { unit.useOneBattleAction(); }
    public void resetBattleActions () { unit.resetBattleActions(); }

    public TeamInstance getOwningTeam () { return team; }

    public void setBattleZone ( BattleZone newZone ) { battleZone = newZone; }
    public BattleZone getBattleZone () { return battleZone; }


    public void square() { squared = true; }
    public void unSquare() { squared = false; }

    public void skirmish() { skirmishing = true; }
    public void unSkirmish() { skirmishing = false; }

    public void faceForward() { facingForward = true; }
    public void faceBackward() { facingForward = false; }


    public boolean isSquared() { return squared; }
    public boolean isSkirmishing() { return skirmishing; }

    public boolean isInfantryNormalFormation() {

        return (getType() == MilitaryUnit.INFANTRY
                || getType() == MilitaryUnit.ELITE_INFANTRY
                || getType() == MilitaryUnit.MILITIA)
                && (!squared && !skirmishing);
    }

    public boolean isFacingForward() { return facingForward; }

    private BattleZone battleZone;
    private MilitaryUnit unit;
    private TeamInstance team;

    private boolean squared;
    private boolean skirmishing;
    private boolean facingForward;

}
