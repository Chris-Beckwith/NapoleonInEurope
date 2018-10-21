package game.controller.Region;

import game.util.GameMsg;
import lobby.controller.Nation;

/**
 * Petersburg.java  Date Created: Nov 9, 2012
 *
 * Purpose: To represent the region of St. Petersburg
 *
 * Description:
 *
 * @author Chrisb
 */
public class Petersburg extends CapitalRegion {
    public Petersburg() {
        super(GameMsg.getString("region.Petersburg"), Nation.RUSSIA, 3, true, true);
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