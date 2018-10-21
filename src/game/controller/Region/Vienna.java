package game.controller.Region;

import lobby.controller.Nation;
import game.util.GameMsg;

/**
 * Vienna.java  Date Created: Nov 9, 2012
 *
 * Purpose: To represent the region of Vienna
 *
 * Description:
 *
 * @author Chrisb
 */
public class Vienna extends CapitalRegion {
    public Vienna() {
        super(GameMsg.getString("region.Vienna"), Nation.AUSTRIA_HUNGARY, 10, false, false);
    }

    public int getProductionValue() {
        if (isUprising() || isEnemyOccupied())
            return 0;

        switch (controllingNation) {
            case Nation.AUSTRIA_HUNGARY:
                return capitalProductionValue;
            case Nation.FRANCE:
            case Nation.GREAT_BRITAIN:
            case Nation.PRUSSIA:
            case Nation.RUSSIA:
            case Nation.SPAIN:
                return capitalProductionValue/2;
            case Nation.OTTOMANS:
                return 2;
            default:
                return 0;
        }
    }
}