
package game.controller.Battle;

import game.controller.GameController;
import game.controller.Region.Region;
import game.controller.Unit.MilitaryUnit;
import game.controller.NationInstance;
import java.util.ArrayList;

/**
 *
 * BattleInstance.java  Time Created: 9/14/13 2:40 PM
 *
 * Author - DanP
 *
 * Purpose - BattleInstance Class Definition
 *
 * Description
 *
 * Arguments -
 *      - The GameController
 *      - The region where the battle is occurring
 *      - An ArrayList of the nations in the region
 *
 * Sets up the initial battle conditions -
 *      - The two teams of nations
 *      - The units on each team
 *      - The battle board
 *
 * Keeps track of the current Battle Phase
 * Calls the methods in BattleBoardInstance
 *
 **/

    // TODO Ability to name battles, see record of battle (units involved/ units lost), replays? TODO
    // TODO Ability to name units/generals/armies? TODO

public class BattleInstance {

    public BattleInstance ( GameController gameControl,
                            Region battleRegion,
                            ArrayList<NationInstance> nationsInRegion) {

        // Initialize the two teams of armies
        TeamInstance teamA = new TeamInstance();
        TeamInstance teamB = new TeamInstance();

        // mapTurn is the nation whose turn it is on the World Map
        NationInstance initialNation = gameControl.getNationInstance(gameControl.game.getTurn());

        // The player whose turn it is goes on teamA
        teamA.addNation(initialNation);

        // Add allied players to teamA
        // Add at war players to teamB
        for (int i = 0; i < nationsInRegion.size(); i++) {

            if ( initialNation.isAllied(i) )
                teamA.addNation( nationsInRegion.get(i) );

            if ( initialNation.isEnemy(i) )
                teamB.addNation( nationsInRegion.get(i) );

        }

        // Get all units in the region where the battle is occurring
        ArrayList<MilitaryUnit> unitsInRegion = battleRegion.getOccupyingUnits();
        int owningNation;

        // Add units from region to teamA
        // Add units from region to teamB
        // MilitaryUnit is converted to BattleUnit in TeamInstance Class
        for (int i = 0; i < unitsInRegion.size(); i++) {

            owningNation = unitsInRegion.get(i).getOwningNation();

            if ( owningNation == gameControl.game.getTurn() )
                teamA.addUnit( unitsInRegion.get(i) );

            if ( initialNation.isAllied(owningNation) )
                teamA.addUnit( unitsInRegion.get(i) );

            if ( initialNation.isEnemy(owningNation) )
                teamB.addUnit( unitsInRegion.get(i) );

        }

        // Initialize battle board
        BattleBoardInstance battleBoard = new BattleBoardInstance();

        // Roll initiative
        // Re-roll if tied
        while ( teamA.getInitiative() == teamB.getInitiative() ) {
            teamA.rollInitiative();
            teamB.rollInitiative();
        }

        // Determine attacker and defender
        // For now winner of initiative roll is attacker
        // TODO Normally winner of initiative roll gets choice TODO

        TeamInstance attackingTeam;
        TeamInstance defendingTeam;

        if ( teamA.getInitiative() > teamB.getInitiative() ) {

            attackingTeam = teamA;
            defendingTeam = teamB;
        }
        else {

            attackingTeam = teamB;
            defendingTeam = teamA;
        }

        battleBoard.setAttackingTeam( attackingTeam );
        battleBoard.setDefendingTeam( defendingTeam );

        // Place units on battle board
        // For now placing all units in reserve areas
        for (int i = 0; i < teamA.getUnits().size(); i++) {

            battleBoard.placeUnit( attackingTeam.getUnits().get(i) , battleBoard.getAttackerReserveZone() );
        }

        for (int i = 0; i < teamB.getUnits().size(); i++) {

            battleBoard.placeUnit( defendingTeam.getUnits().get(i) , battleBoard.getDefenderReserveZone() );
        }

        // Begin Battle

        String phase = "attackerCavalry";

        // TODO Battle Turn order TODO
        // 1 ) Attacker cavalry
        // 2 ) Defender cavalry
        // 3 ) Attacker artillery
        // 4 ) Defender artillery
        // 5 ) Attacker Infantry
        // 6 ) Defender Infantry
        // 7 ) Attacker General
        // 8 ) Defender General
        // 9 ) Next round


    }
}
