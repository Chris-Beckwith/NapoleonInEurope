
package game.controller.Battle;

import game.controller.Unit.*;
import game.util.Dice;

import java.util.ArrayList;

/**
 *
 * BattleBoardInstance.java  Time Created: 9/14/13 2:30 PM
 *
 * Author - DanP
 *
 * Purpose - BattleBoardInstance Class Definition
 *
 * Description -
 *
 *
 **/

public class BattleBoardInstance {

    public BattleBoardInstance () {

        /*****
        ** Battle Board Layout
        *****
                                    ------------------------------
                                    |      defenderRoutZone      |
                                    ------------------------------
                                    |     defenderReserveZone    |
        --------------------------------------------------------------------------------------
        |  defenderLeftBattleZone   |  defenderCenterBattleZone  |  defenderRightBattleZone  |
        --------------------------------------------------------------------------------------
        |    midLeftBattleZone      |     midCenterBattleZone    |     midRightBattleZone     |
        -------------------------------------------------------------------------------------
        |  attackerLeftBattleZone   |  attackerCenterBattleZone  |  attackerRightBattleZone  |
        --------------------------------------------------------------------------------------
                                    |     attackerReserveZone    |
                                    ------------------------------
                                    |      attackerRoutZone      |
                                    ------------------------------
         */

        // Attacker rout Zone
        attackerRoutZone = new BattleZone();

        // Defender rout Zone
        defenderRoutZone = new BattleZone();

        // Attacker reserve Zone
        attackerReserveZone = new BattleZone();

        // Defender reserve Zone
        defenderReserveZone = new BattleZone();

        // The nine Combat Zones
        attackerLeftBattleZone = new BattleZone();
        attackerCenterBattleZone = new BattleZone();
        attackerRightBattleZone = new BattleZone();

        midLeftBattleZone = new BattleZone();
        midCenterBattleZone = new BattleZone();
        midRightBattleZone = new BattleZone();

        defenderLeftBattleZone = new BattleZone();
        defenderCenterBattleZone = new BattleZone();
        defenderRightBattleZone = new BattleZone();

        // Dead units
        attackerDeadUnits = new BattleZone();
        defenderDeadUnits = new BattleZone();

        attackRoll = 0;
        defenseRoll = 0;

    }

    // Used to initialize the two teams in battle
    // These two methods should only be called once per battle
    public void setAttackingTeam ( TeamInstance team ) { attackingTeam = team; }
    public void setDefendingTeam ( TeamInstance team ) { defendingTeam = team; }

    // Records battle events
    public void battleMessage ( String eventMessage ) {
        message = eventMessage;
        messageLog.add(message);
    }

    // Place units on the battle board for initial setup
    public void placeUnit ( BattleUnit unit, BattleZone battleZone ) {

            battleZone.add(unit);
            unit.setBattleZone(battleZone);
    }

    // TODO Finalize placement of units so battle can begin TODO
    public boolean finalizePlacement () {

        return false;
    }

    // This method is called when a unit is killed in battle
    // Removes the unit from its current Battle Zone
    // Places the unit among its team's dead units
    //
    public void killUnit ( BattleUnit unit ) {

        unit.getBattleZone().remove(unit);

        if ( unit.getOwningTeam() == attackingTeam ) {

            attackerDeadUnits.add(unit);
            unit.setBattleZone(attackerDeadUnits);
        }

        if ( unit.getOwningTeam() == defendingTeam ) {

            defenderDeadUnits.add(unit);
            unit.setBattleZone(defenderDeadUnits);
        }

    }


    // Checks if the given unit can move sideways
    // Arguments -
    //    1 ) Unit to be moved
    //    2 ) The Battle Zone that is contesting the current Zone
    //    3 ) The destination Zone
    //    4 ) The battle Zone that is contesting the destination Zone
    //
    boolean canMoveSideways ( BattleUnit unit,
                              BattleZone contestingCurrentZone,
                              BattleZone destinationZone,
                              BattleZone contestingDestinationZone) {

        // If either of the two Battle Zones contesting
        // the current Zone or the destination Zone are friendly, then the unit can move
        if ( contestingCurrentZone.isFriendly(unit)
                || contestingDestinationZone.isFriendly(unit) )
            return true;

        BattleZone currentZone = new BattleZone();
        currentZone.addAll(unit.getBattleZone());
        // The unit that is moving should not be counted
        currentZone.remove(unit);

        // If the team that the unit is on has more units in both Battle Zones, then the unit can move
        if ( currentZone.getModifiedUnitCount() > contestingCurrentZone.getModifiedUnitCount()
                && destinationZone.getModifiedUnitCount() > contestingDestinationZone.getModifiedUnitCount() )
            return true;

        // If these conditions are not met, then the unit cannot move
        return false;
    }

/*
    - Tactical Movement -

    1 ) Unit cannot be voluntary moved into a Rout Zone
    2 ) Unit cannot move if it is squared
    3 ) Unit cannot move if the destination contains enemy units
    4 ) Unit can only move to an adjacent battle Zone
    5 ) Unit can only move sideways if the unit's team has more units than the enemy team in
        both the current Zone and the destination than the enemy team does in the contesting zones

        a ) The unit that is moving does not count
        b ) Generals do not count
        c ) Squared infantry count as 1/2 a unit
        d ) Skirmishing infantry count as 3 units but when any skirmishers are present in a Battle Zone
            all of the infantry that are not skirmishing are not counted in that Battle Zone
*/

    // 1 ) Unit cannot be voluntary moved into a Rout Zone
    // This method is called when a unit is routed in battle
    // Removes the unit from its current Battle Zone
    // Places the unit in its team's Rout Zone
    //
    public void routUnit ( BattleUnit unit ) {

        unit.getBattleZone().remove(unit);

        if ( unit.getOwningTeam() == attackingTeam ) {

            attackerRoutZone.add(unit);
            unit.setBattleZone(attackerRoutZone);
        }
        if ( unit.getOwningTeam() == defendingTeam ) {

            defenderRoutZone.add(unit);
            unit.setBattleZone(defenderRoutZone);
        }
    }

    public void moveUnit ( BattleUnit unit, BattleZone destination ) {

        BattleZone currentZone = unit.getBattleZone();

        // canMove is initially set to true
        // If any of the conditions for movement are not met, canMove is set to false
        boolean canMove = true;

        // 2 ) Unit cannot move if it is squared
        if ( unit.isSquared() ) {

            canMove = false;
            battleMessage("Squared units can't move. Return this unit to normal formation first.");
        }

        // 3 ) Unit cannot move if the destination contains any enemy units
        else if ( !destination.isFriendly(unit) ) {

            canMove = false;
            battleMessage("Can't move to a Zone that contains any enemy units");
        }

        // 4 ) Check if the Destination is adjacent to the unit's Current Zone
        // 5 ) Check for valid sideways movement
        else {

            // Check if the Destination is adjacent to the unit's Current Zone
            if ( currentZone == attackerReserveZone ) {

                if (destination != attackerLeftBattleZone
                        && destination != attackerCenterBattleZone
                        && destination != attackerRightBattleZone) {

                    canMove = false;
                    battleMessage("Unit can only move to an adjacent battle Zone");
                }
            }

            // Check if the Destination is adjacent to the unit's Current Zone
            if ( currentZone == defenderReserveZone ) {

                if (destination != defenderLeftBattleZone
                        && destination != defenderCenterBattleZone
                        && destination != defenderRightBattleZone) {

                    canMove = false;
                    battleMessage("Unit can only move to an adjacent battle Zone");
                }
            }

            // Check if the Destination is adjacent to the unit's Current Zone
            if ( currentZone == attackerLeftBattleZone ) {

                if (destination != attackerReserveZone
                        && destination != attackerCenterBattleZone
                        && destination != midLeftBattleZone) {

                    canMove = false;
                    battleMessage("Unit can only move to an adjacent battle Zone");
                }

                // Sideways movement - from attackerLeftBattleZone to attackerCenterBattleZone
                else if ( destination == attackerCenterBattleZone &&
                        !canMoveSideways(unit, midLeftBattleZone, destination, midCenterBattleZone) ) {

                    canMove = false;
                    battleMessage("Unit cannot move sideways. Not enough allied troops in the Zones.");
                }
            }

            // If currentZone is not adjacent to destination the unit will not move
            if ( currentZone == attackerCenterBattleZone ) {

                if (destination != attackerReserveZone
                        && destination != attackerLeftBattleZone
                        && destination != midCenterBattleZone
                        && destination != attackerRightBattleZone) {

                    canMove = false;
                    battleMessage("Unit can only move to an adjacent battle Zone");
                }

                // Sideways movement - from attackerCenterBattleZone to attackerLeftBattleZone
                else if (destination == attackerLeftBattleZone
                        && !canMoveSideways(unit, midCenterBattleZone, destination, midLeftBattleZone) ) {

                        canMove = false;
                        battleMessage("Unit cannot move sideways. Not enough allied troops in the Zones.");
                }

                // Sideways movement - from attackerCenterBattleZone to attackerRightBattleZone
                else if (destination == attackerRightBattleZone
                        &&!canMoveSideways(unit, midCenterBattleZone, destination, midRightBattleZone) ) {

                        canMove = false;
                        battleMessage("Unit cannot move sideways. Not enough allied troops in the Zones.");
                }
            }

            // If currentZone is not adjacent to destination the unit will not move
            if ( currentZone == attackerRightBattleZone ) {

                if (destination != attackerReserveZone
                        && destination != attackerCenterBattleZone
                        && destination != midRightBattleZone) {

                    canMove = false;
                    battleMessage("Unit can only move to an adjacent battle Zone");
                }

                // Sideways movement - from attackerRightBattleZone to attackerCenterBattleZone
                else if (destination == attackerCenterBattleZone
                        && !canMoveSideways(unit, midRightBattleZone, destination, midCenterBattleZone) ) {

                        canMove = false;
                        battleMessage("Unit cannot move sideways. Not enough allied troops in the Zones.");
                }
            }

            // If currentZone is not adjacent to destination the unit will not move
            if ( currentZone == midLeftBattleZone ) {

                if (destination != attackerLeftBattleZone
                        && destination != midCenterBattleZone
                        && destination != defenderLeftBattleZone) {

                    canMove = false;
                    battleMessage("Unit can only move to an adjacent battle Zone");
                }

                // Sideways movement - from midLeftBattleZone to midCenterBattleZone
                else if (destination == midCenterBattleZone) {

                    // Unit is owned by attacking team
                    if ( unit.getOwningTeam() == attackingTeam
                            && !canMoveSideways(unit, defenderLeftBattleZone, destination, defenderCenterBattleZone) ) {

                        canMove = false;
                        battleMessage("Unit cannot move sideways. Not enough allied troops in the Zones.");
                    }

                    // Unit is owned by defending team
                    if ( unit.getOwningTeam() == defendingTeam
                            && !canMoveSideways(unit, attackerLeftBattleZone, destination, attackerCenterBattleZone) ) {

                        canMove = false;
                        battleMessage("Unit cannot move sideways. Not enough allied troops in the Zones.");
                    }
                }
            }

            // If currentZone is not adjacent to destination the unit will not move
            if ( currentZone == midCenterBattleZone ) {
                if (destination != attackerCenterBattleZone
                        && destination != midLeftBattleZone
                        && destination != defenderCenterBattleZone
                        && destination != midRightBattleZone) {

                    canMove = false;
                    battleMessage("Unit can only move to an adjacent battle Zone");
                }

                // Sideways movement - from midCenterBattleZone to midLeftBattleZone
                else if (destination == midLeftBattleZone) {

                    // Unit is owned by attacking team
                    if ( unit.getOwningTeam() == attackingTeam
                            && !canMoveSideways(unit, defenderCenterBattleZone, destination, defenderLeftBattleZone) ) {

                        canMove = false;
                        battleMessage("Unit cannot move sideways. Not enough allied troops in the Zones.");
                    }

                    // Unit is owned by defending team
                    if ( unit.getOwningTeam() == defendingTeam
                            && !canMoveSideways(unit, attackerCenterBattleZone, destination, attackerLeftBattleZone) ) {

                        canMove = false;
                        battleMessage("Unit cannot move sideways. Not enough allied troops in the Zones.");
                    }
                }

                // Sideways movement - from midCenterBattleZone to midRightBattleZone
                else if (destination == midRightBattleZone) {

                    // Unit is owned by attacking team
                    if ( unit.getOwningTeam() == attackingTeam
                            && !canMoveSideways(unit, defenderCenterBattleZone, destination, defenderRightBattleZone) ) {

                        canMove = false;
                        battleMessage("Unit cannot move sideways. Not enough allied troops in the Zones.");
                    }

                    // Unit is owned by defending team
                    if ( unit.getOwningTeam() == defendingTeam
                            && !canMoveSideways(unit, attackerCenterBattleZone,destination, attackerRightBattleZone) ) {

                        canMove = false;
                        battleMessage("Unit cannot move sideways. Not enough allied troops in the Zones.");
                    }
                }
            }

            // If currentZone is not adjacent to destination the unit will not move
            if ( currentZone == midRightBattleZone ) {
                if (destination != attackerRightBattleZone
                        && destination != midCenterBattleZone
                        && destination != defenderRightBattleZone) {

                    canMove = false;
                    battleMessage("Unit can only move to an adjacent battle Zone");
                }

                // Sideways movement - from midRightBattleZone to midCenterBattleZone
                else if (destination == midCenterBattleZone) {

                    // Unit is owned by attacking team
                    if ( unit.getOwningTeam() == attackingTeam
                            && !canMoveSideways(unit, defenderRightBattleZone, destination, defenderCenterBattleZone) ) {

                        canMove = false;
                        battleMessage("Unit cannot move sideways. Not enough allied troops in the Zones.");
                    }

                    // Unit is owned by defending team
                    if ( unit.getOwningTeam() == defendingTeam
                            && !canMoveSideways(unit, attackerRightBattleZone, destination, attackerCenterBattleZone) ) {

                        canMove = false;
                        battleMessage("Unit cannot move sideways. Not enough allied troops in the Zones.");
                    }
                }
            }

            // If currentZone is not adjacent to destination the unit will not move
            if ( currentZone == defenderLeftBattleZone ) {

                if (destination != defenderReserveZone
                        && destination != defenderCenterBattleZone
                        && destination != midLeftBattleZone) {

                    canMove = false;
                    battleMessage("Unit can only move to an adjacent battle Zone");
                }

                // Sideways movement - from defenderLeftBattleZone to defenderCenterBattleZone
                else if (destination == defenderCenterBattleZone
                        && !canMoveSideways(unit, midLeftBattleZone, destination, midCenterBattleZone) ) {

                    canMove = false;
                    battleMessage("Unit cannot move sideways unless allies have more units in both Zones");
                }
            }

            // If currentZone is not adjacent to destination the unit will not move
            if ( currentZone == defenderCenterBattleZone ) {

                if (destination != defenderReserveZone
                        && destination != defenderLeftBattleZone
                        && destination != midCenterBattleZone) {

                    canMove = false;
                    battleMessage("Unit can only move to an adjacent battle Zone");
                }

                // Sideways movement - from defenderCenterBattleZone to defenderLeftBattleZone
                else if (destination == defenderLeftBattleZone
                        && !canMoveSideways(unit, midCenterBattleZone, destination, midLeftBattleZone) ) {

                    canMove = false;
                    battleMessage("Unit cannot move sideways unless allies have more units in both Zones");
                }

                // Sideways movement - from defenderCenterBattleZone to defenderRightBattleZone
                else if (destination == defenderRightBattleZone
                        && !canMoveSideways(unit, midCenterBattleZone, destination, midRightBattleZone) ) {

                    canMove = false;
                    battleMessage("Unit cannot move sideways unless allies have more units in both Zones");
                }
            }

            // If currentZone is not adjacent to destination the unit will not move
            if ( currentZone == defenderRightBattleZone ){
                if (destination != defenderReserveZone
                        && destination != defenderCenterBattleZone
                        && destination != midRightBattleZone) {

                    canMove = false;
                    battleMessage("Unit can only move to an adjacent battle Zone");
                }

                // Sideways movement - from defenderRightBattleZone to defenderCenterBattleZone
                else if (destination == defenderCenterBattleZone
                        && !canMoveSideways(unit, midRightBattleZone, destination, midCenterBattleZone) ) {

                    canMove = false;
                    battleMessage("Unit cannot move sideways unless allies have more units in both Zones");
                }
            }
        }

        // If all of the conditions are met then the unit moves
        if (canMove) {
            unit.useOneBattleAction();
            currentZone.remove(unit);
            destination.add(unit);
            unit.setBattleZone(destination);

            battleMessage( "Unit moved" );
        }
    }

    // TODO implement facing forward and facing backward for cavalry TODO

    public void cavalryCounterCharge ( Cavalry cavalry, BattleUnit target ) {

        // TODO check if cavalryCounterCharge target and conditions are valid TODO
        boolean canCounterCharge = true;

        battleMessage( "Begin counter charge ");
    }

    public void cavalryCharge ( BattleUnit chargingCavalry, BattleUnit target ) {

        // TODO check if cavalryCharge target and conditions are valid TODO
        boolean canCharge = true;

        battleMessage( "Begin cavalry charge" );

        chargingCavalry.useOneBattleAction();

        // TODO create step for infantry to square and cavalry to cancel or proceed with charge TODO

        // Can only square if the infantry is not already squared or skirmishing
        if ( target.isInfantryNormalFormation() ) {

            String answer = "yes";

            battleMessage( "Your infantry unit is being charged by a cavalry. Do you want the infantry to square?" );
            // TODO ?? prompt to square infantry ?? TODO
            // answer = "no";
            if ( answer.equals("yes") ) target.square();

            battleMessage( "Do you want to carry through the charge?" );
            // TODO ?? prompt to carry through charge ?? TODO
            // answer = "no";
            if ( answer.equals("yes") ) cavalryCarryThroughCharge( chargingCavalry, target );

        }
        else battleMessage( "Your unit is being charged by a cavalry. Proceeding to next step." );
    }

    public void cavalryCarryThroughCharge ( BattleUnit chargingCavalry, BattleUnit target ) {

        battleMessage( "Carry through cavalry charge" );

        attackRoll = Dice.rollTwoDSix();
        defenseRoll = Dice.rollTwoDSix();

        // TODO if attack roll is 12, kill enemy general TODO

        // TODO +1 to attack if at least 1 friendly general in same battle Zone TODO

        // TODO +1 for combined arms (at least 1 infantry, 1 cavalry, and 1 artillery in same battle Zone) TODO

        // TODO counter charge TODO

        // Modifiers will only apply to attacking roll
        attackModifier = 0;

        // No modifier if attacking unit is normal cavalry
        //

        // +1 to attack if attacking unit is heavy cavalry
        if ( chargingCavalry.getType() == MilitaryUnit.HEAVY_CAVALRY ) attackModifier++;
        // -1 to attack if attacking unit is irregular cavalry
        if ( chargingCavalry.getType() == MilitaryUnit.IRREGULAR_CAVALRY ) attackModifier--;

        // Get unit type of the target unit
        targetType = target.getType();

        // Target is infantry, elite infantry, or militia
        // No modifier if target is normal infantry
        // -1 to attack if target is elite infantry
        // +1 to attack if target is militia
        // +2 to attack if infantry is not squared
        // -4 to attack if infantry is squared
        // +1 to attack if infantry is skirmishing
        if ( targetType == MilitaryUnit.INFANTRY || targetType == MilitaryUnit.ELITE_INFANTRY || targetType == MilitaryUnit.MILITIA ) {

            if ( targetType == MilitaryUnit.ELITE_INFANTRY ) attackModifier--;  // Target is elite infantry
            if ( targetType == MilitaryUnit.MILITIA ) attackModifier++; // Target is militia
            if ( !target.isSquared() ) attackModifier += 2;
            if ( target.isSquared() ) attackModifier -= 4;
            if ( target.isSkirmishing() ) attackModifier++;
        }

        // No modifiers if target is normal cavalry
        //

        // -1 to attack if target is heavy cavalry
        if ( targetType == MilitaryUnit.HEAVY_CAVALRY ) attackModifier--;

        // +1 to attack if target is irregular cavalry
        if ( targetType == MilitaryUnit.IRREGULAR_CAVALRY ) attackModifier++;

        // +3 to attack if target is artillery
        if ( targetType == MilitaryUnit.ARTILLERY || targetType == MilitaryUnit.HORSE_ARTILLERY) attackModifier += 3;

        // Determine combat result
        combatResult = attackRoll + attackModifier - defenseRoll;

        // If rolls are equal, neither unit is affected
        if ( combatResult == 0 ) { battleMessage( "Neither unit was harmed" ); }

        // Attacking unit got higher roll
        if ( combatResult > 0 ) {

            if ( combatResult <= target.getDefence() ) {
                routUnit(target);
                battleMessage( "Defending unit was routed" );
            }
            if ( combatResult > target.getDefence() ) {
                killUnit(target);
                battleMessage( "Defending unit was killed" );
            }
        }

        // Defending unit got higher roll
        if ( combatResult < 0 ) {

            // Make combat result a positive number (defending unit won the roll-off)
            combatResult = -combatResult;

            if ( combatResult <= chargingCavalry.getDefence() ) {
                routUnit(chargingCavalry);
                battleMessage( "Attacking unit was routed" );
            }
            if ( combatResult > chargingCavalry.getDefence() ) {
                killUnit(chargingCavalry);
                battleMessage( "Attacking unit was killed" );
            }

        }

    }

    public void artilleryFire ( BattleUnit artillery, BattleUnit target ) {

        // TODO check if artilleryFire target and conditions are valid TODO
        boolean canFire = true;

        // TODO if attack roll is 12, kill enemy general TODO
        artillery.useOneBattleAction();

        attackRoll = Dice.rollTwoDSix();

        attackModifier = 0;

        targetType = target.getType();

        // -1 to attack if artillery is horse artillery
        if (artillery.getType() == MilitaryUnit.HORSE_ARTILLERY) attackModifier--;

        // TODO -2 to attack if artillery is at long range TODO
        //int range = 1;
        //if (range == 2) attackModifier -=2;

        // Target is cavalry
        if (targetType == MilitaryUnit.CAVALRY || targetType == MilitaryUnit.HEAVY_CAVALRY || targetType == MilitaryUnit.IRREGULAR_CAVALRY) {

            // +1 to attack if target is irregular cavalry
            if (targetType == MilitaryUnit.IRREGULAR_CAVALRY) attackModifier++;
            // Base to hit cavalry is 6
            combatResult = attackRoll + attackModifier - 6;
        }

        // Target is infantry, elite infantry, or militia
        if (targetType == MilitaryUnit.INFANTRY || targetType == MilitaryUnit.ELITE_INFANTRY || targetType == MilitaryUnit.MILITIA) {

            // -1 to attack if target is elite Infantry
            if ( targetType == MilitaryUnit.ELITE_INFANTRY ) attackModifier--;
            // +1 to attack if target is militia
            if ( targetType == MilitaryUnit.MILITIA ) attackModifier++;
            // -1 to attack if target is skirmishing
            if ( target.isSkirmishing() ) attackModifier--;
            // +2 to attack if target is squared
            if ( target.isSquared() ) attackModifier += 2;

            // Base to hit infantry is 7
            combatResult = attackRoll + attackModifier - 7;
        }

        // Target is artillery or horse artillery
        if (targetType == MilitaryUnit.ARTILLERY || targetType == MilitaryUnit.HORSE_ARTILLERY) {

            // Base to hit artillery is 8
            combatResult = attackRoll + attackModifier - 8;
        }

        // Resolve fire combat
        if ( combatResult < 0 ) {
            battleMessage("Miss");
        }

        if ( combatResult >=0 && combatResult < target.getDefence() ) {

            battleMessage("Unit routed");
            routUnit(target);
        }

        if ( combatResult >= target.getDefence() ) {

            battleMessage("Unit killed");
            killUnit(target);
        }


    }

    public void infantryFire ( BattleUnit infantry, BattleUnit target ) {

        // TODO check if infantryFire target and conditions are valid TODO
        boolean canFire = true;

        // TODO if attack roll is 12, kill enemy general TODO

        // TODO +1 to attack if Firing piece is unsquared British infantry after skirmishers fire TODO

        infantry.useOneBattleAction();

        attackRoll = Dice.rollTwoDSix();

        attackModifier = 0;

        targetType = target.getType();

        // -1 to attack if infantry is militia
        if (infantry.getType() == MilitaryUnit.MILITIA) attackModifier--;
        // -1 to attack if infantry is squared
        if ( infantry.isSquared() ) attackModifier--;

        // Target is cavalry
        if (targetType == MilitaryUnit.CAVALRY || targetType == MilitaryUnit.HEAVY_CAVALRY || targetType == MilitaryUnit.IRREGULAR_CAVALRY) {

            // +1 to attack if target is irregular cavalry
            if (targetType == MilitaryUnit.IRREGULAR_CAVALRY) attackModifier++;
            // Base to hit cavalry is 8
            combatResult = attackRoll + attackModifier - 8;
        }

        // Target is infantry, elite infantry, or militia
        if (targetType == MilitaryUnit.INFANTRY || targetType == MilitaryUnit.ELITE_INFANTRY || targetType == MilitaryUnit.MILITIA) {

            // -1 to attack if target is elite Infantry
            if ( targetType == MilitaryUnit.ELITE_INFANTRY ) attackModifier--;
            // +1 to attack if target is militia
            if ( targetType == MilitaryUnit.MILITIA ) attackModifier++;
            // -1 to attack if target is skirmishing
            if ( target.isSkirmishing() ) attackModifier--;
            // +1 to attack if target is squared
            if ( target.isSquared() ) attackModifier++;

            // Base to hit infantry is 9
            combatResult = attackRoll + attackModifier - 9;
        }

        // Target is artillery or horse artillery
        if (targetType == MilitaryUnit.ARTILLERY || targetType == MilitaryUnit.HORSE_ARTILLERY) {

            // Base to hit artillery is 10
            combatResult = attackRoll + attackModifier - 10;
        }

        // Resolve fire combat
        if ( combatResult < 0 ) {
            battleMessage("Miss");
        }

        if ( combatResult >=0 && combatResult < target.getDefence() ) {

            battleMessage("Unit routed");
            routUnit(target);
        }

        if ( combatResult >= target.getDefence() ) {

            battleMessage("Unit killed");
            killUnit(target);
        }
    }

    public void infantryCharge ( BattleUnit infantry, BattleUnit target ) {

        // TODO check if infantryCharge target and conditions are valid TODO
        boolean canCharge = true;

        // TODO if attack roll is 12, kill enemy general TODO
        // TODO if attack roll is 10-12 and charging unit is infantry, change to elite infantry TODO

        // TODO Targeted cavalry can avoid charge TODO

        // TODO Targeted horse artillery can avoid charge TODO

        // TODO If targeted infantry is skirmishing it can avoid charge TODO

        // TODO +1 French infantry charge attacking after skirmishers fire TODO

        // TODO +1 to attack if friendly general in same zone TODO

        // TODO +1 for combined arms TODO

        battleMessage("Infantry Charge");

        infantry.useOneBattleAction();

        attackRoll = Dice.rollTwoDSix();
        defenseRoll = Dice.rollTwoDSix();

        attackModifier = 0;

        // +1 to attack if charging unit is elite infantry
        if ( infantry.getType() == MilitaryUnit.ELITE_INFANTRY ) attackModifier++;
        // -1 to attack if charging unit is militia
        if ( infantry.getType() == MilitaryUnit.MILITIA ) attackModifier--;

        // Get unit type of the target unit
        targetType = target.getType();

        // Target is infantry, elite infantry, or militia
        // No modifier if target is normal infantry
        // -1 to attack if target is elite infantry
        // +1 to attack if target is militia
        // +1 to attack if target is skirmishing
        if ( targetType == MilitaryUnit.ELITE_INFANTRY ) attackModifier--;  // Target is elite infantry
        if ( targetType == MilitaryUnit.MILITIA ) attackModifier++; // Target is militia
        if ( target.isSkirmishing() ) attackModifier++; // Target is skirmishing

        // -1 to attack if target is irregular cavalry
        if ( targetType == MilitaryUnit.IRREGULAR_CAVALRY ) attackModifier--;

        // -2 to attack if target is cavalry
        if ( targetType == MilitaryUnit.CAVALRY) attackModifier -= 2;

        // -3 to attack if target is heavy cavalry
        if ( targetType == MilitaryUnit.HEAVY_CAVALRY ) attackModifier -= 3;

        // +3 to attack if target is artillery
        if ( targetType == MilitaryUnit.ARTILLERY || targetType == MilitaryUnit.HORSE_ARTILLERY) attackModifier += 3;

        // Determine combat result
        combatResult = attackRoll + attackModifier - defenseRoll;

        // If rolls are equal, neither unit is affected
        if ( combatResult == 0 ) { battleMessage( "Neither unit was harmed" ); }

        // Attacking unit got higher roll
        if ( combatResult > 0 ) {

            if ( combatResult <= target.getDefence() ) {
                routUnit(target);
                battleMessage( "Defending unit was routed" );
            }
            if ( combatResult > target.getDefence() ) {
                killUnit(target);
                battleMessage( "Defending unit was killed" );
            }
        }

        // Defending unit got higher roll
        if ( combatResult < 0 ) {

            // Make combat result a positive number
            combatResult = -combatResult;

            if ( combatResult <= infantry.getDefence() ) {
                routUnit(infantry);
                battleMessage( "Attacking unit was routed" );
            }
            if ( combatResult > infantry.getDefence() ) {
                killUnit(infantry);
                battleMessage( "Attacking unit was killed" );
            }

        }
    }

    public void infantrySquare ( BattleUnit infantry ) {

        // Infantry cannot already be squared or skirmishing

        infantry.useOneBattleAction();

        // Militia have a 50% chance to fail to square
        if (infantry.getType() == MilitaryUnit.MILITIA && Dice.rollDSix() < 4) {
            battleMessage("Militia failed to square");
        }
        else {
            infantry.square();
            battleMessage("Infantry unit is now in square formation");
        }
    }

    public void infantryUnSquare ( BattleUnit infantry ) {

        infantry.useOneBattleAction();
        infantry.unSquare();

        battleMessage("Infantry unit is now in regular formation");
    }

    public void infantryDeployAsSkirmishers ( BattleUnit infantry ) {

        // Infantry cannot already be squared or skirmishing
        // Must be regular or elite infantry
        // Owning nation must have skirmisher ability

        infantry.useOneBattleAction();
        infantry.skirmish();

        battleMessage("Infantry unit is now skirmishing");
    }

    public void infantryRecallSkirmishers ( BattleUnit infantry ) {

        infantry.useOneBattleAction();
        infantry.unSkirmish();

        battleMessage("Infantry unit is now in regular formation");
    }

    public void rally ( BattleUnit general, BattleUnit target ) {

        // TODO check if rally target and conditions are valid TODO
        boolean canRally = true;

        general.useOneBattleAction();

        rallyResult = Dice.rollTwoDSix();

        targetType = target.getType();

        // +1 to rally elite infantry
        if (targetType == MilitaryUnit.ELITE_INFANTRY) rallyResult++;

        // -1 To rally militia or irregular cavalry
        // TODO -1 TO RALLY FOR BRITISH CAVALRY TODO
        if (targetType == MilitaryUnit.MILITIA || targetType == MilitaryUnit.IRREGULAR_CAVALRY) rallyResult--;

        if (rallyResult >= 8 ) {
            if ( target.getOwningTeam() == attackingTeam ) moveUnit(target, attackerReserveZone);
            if ( target.getOwningTeam() == defendingTeam ) moveUnit(target, defenderReserveZone);

            battleMessage("Unit rallied");
        }
        else battleMessage("Unit failed to rally");
    }

    // TODO Overrunning Generals TODO

    // TODO break flank, retreat, pursuit, end of battle TODO

    public TeamInstance getAttackingTeam() { return attackingTeam; }
    public TeamInstance getDefendingTeam() { return defendingTeam; }

    public BattleZone getAttackerRoutZone() { return attackerRoutZone; }
    public BattleZone getDefenderRoutZone() { return defenderRoutZone; }

    public BattleZone getAttackerReserveZone() { return attackerReserveZone; }
    public BattleZone getDefenderReserveZone() { return defenderReserveZone; }

    public BattleZone getAttackerLeftBattleZone() { return attackerLeftBattleZone; }
    public BattleZone getAttackerCenterBattleZone() { return attackerCenterBattleZone; }
    public BattleZone getAttackerRightBattleZone() { return attackerRightBattleZone; }

    public BattleZone getMidLeftBattleZone() { return midLeftBattleZone; }
    public BattleZone getMidCenterBattleZone() { return midCenterBattleZone; }
    public BattleZone getMidRightBattleZone() { return midRightBattleZone; }

    public BattleZone getDefenderRightBattleZone() { return defenderRightBattleZone; }
    public BattleZone getDefenderCenterBattleZone() { return defenderCenterBattleZone; }
    public BattleZone getDefenderLeftBattleZone() { return defenderLeftBattleZone; }

    public BattleZone getAttackerDeadUnits() { return attackerDeadUnits; }
    public BattleZone getDefenderDeadUnits() { return defenderDeadUnits; }

    private BattleZone attackerRoutZone;
    private BattleZone defenderRoutZone;

    private BattleZone attackerReserveZone;
    private BattleZone defenderReserveZone;

    private BattleZone attackerLeftBattleZone;
    private BattleZone attackerCenterBattleZone;
    private BattleZone attackerRightBattleZone;

    private BattleZone midLeftBattleZone;
    private BattleZone midCenterBattleZone;
    private BattleZone midRightBattleZone;

    private BattleZone defenderRightBattleZone;
    private BattleZone defenderCenterBattleZone;
    private BattleZone defenderLeftBattleZone;

    private BattleZone attackerDeadUnits;
    private BattleZone defenderDeadUnits;

    private TeamInstance attackingTeam;
    private TeamInstance defendingTeam;

    private int attackRoll;
    private int attackModifier;
    private int defenseRoll;
    private int targetType;
    private int combatResult;
    private int rallyResult;

    private String message;

    private ArrayList<String> messageLog;
}