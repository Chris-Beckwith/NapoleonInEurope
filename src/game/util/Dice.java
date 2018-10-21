
package game.util;

import java.util.Random;

/**
 * DiceInstance.java  Time Created: 9/14/13 6:00 PM
 *
 * Purpose: Roll dice
 *
 * Description:
 *
 * @author DanP
 */
public final class Dice {
    // Roll a die with numSides
    // todo add event for dice rolls..
    public static int roll (int numSides) {
        return (new Random()).nextInt(numSides);
    }

    public static int rollDSix () {
        return roll(6);
    }

    public static int rollTwoDSix () {
        return rollDSix() + rollDSix();
    }

    public static int rollTwoDSix(int addToRoll) {
        return rollDSix() + rollDSix() + addToRoll;
    }
}
