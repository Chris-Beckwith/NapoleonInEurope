
package game.controller.Battle;

import game.controller.Unit.MilitaryUnit;
import game.controller.NationInstance;
import game.util.Dice;
import java.util.ArrayList;


/**
 *
 * TeamInstance.java  Time Created: 9/17/13 6:00 AM
 *
 * Author - DanP
 *
 * Purpose - TeamInstance Class Definition
 *
 * Description
 *
 * Keeps track of information related to teams in a battle such as
 *      - The nations on the team
 *      - The units on the team
 *      - The team's initiative score
 *
 *
 **/

public class TeamInstance {

    public TeamInstance () {

        // Variables -
        // list of nations
        // list of units
        // initiative roll
        //
        // team name String teamName = "";
        // team color
        //

        initiativeRoll = 0;

    }

    public void addNation (NationInstance nation) { teamNationList.add(nation); }

    // Converts the MilitaryUnit to a BattleUnit and adds it the team
    public void addUnit (MilitaryUnit unit) { teamUnitList.add( new BattleUnit( unit, this ) ); }

    public ArrayList<NationInstance> getNations () { return teamNationList; }
    public ArrayList<BattleUnit> getUnits () { return teamUnitList; }

    // TODO Initiative - roll each nation separately, add modifiers, take highest value (look up rule) TODO
    public void rollInitiative () { initiativeRoll = Dice.rollTwoDSix(); }
    public int getInitiative() { return initiativeRoll; }

    private ArrayList<NationInstance> teamNationList = new ArrayList<NationInstance>();

    private ArrayList<BattleUnit> teamUnitList = new ArrayList<BattleUnit>();



    private int initiativeRoll;
}