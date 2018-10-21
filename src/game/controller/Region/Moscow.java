package game.controller.Region;

import lobby.controller.Nation;
import game.util.GameMsg;

/**
 * Moscow.java  Date Created: Nov 9, 2012
 *
 * Purpose: To represent the region of Moscow
 *
 * Description:
 *
 * @author Chrisb
 */
public class Moscow extends CapitalRegion {
    public Moscow() {
        super(GameMsg.getString("region.Moscow"), Nation.RUSSIA, 3, false, false);
    }

    public int getProductionValue() {
        if (isUprising() || isEnemyOccupied())
            return 0;

        switch (controllingNation) {
            case Nation.RUSSIA:
                return capitalProductionValue;
            case Nation.FRANCE:
            case Nation.GREAT_BRITAIN:
            case Nation.PRUSSIA:
            case Nation.AUSTRIA_HUNGARY:
            case Nation.SPAIN:
                return 2;
            case Nation.OTTOMANS:
                return 1;
            default:
                return 0;
        }
    }
}