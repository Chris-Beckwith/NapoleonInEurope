package game.controller.Region;

import game.util.GameMsg;
import lobby.controller.Nation;

/**
 * London.java  Date Created: Nov 9, 2012
 *
 * Purpose: To represent the region of London
 *
 * Description:
 *
 * @author Chrisb
 */
public class London extends CapitalRegion {
    public London() {
        super(GameMsg.getString("region.London"), Nation.GREAT_BRITAIN, 12, true, true);
    }

    public int getProductionValue() {
        if (isUprising() || isEnemyOccupied())
            return 0;

        switch (controllingNation) {
            case Nation.GREAT_BRITAIN:
                return capitalProductionValue;
            case Nation.FRANCE:
            case Nation.PRUSSIA:
            case Nation.RUSSIA:
            case Nation.AUSTRIA_HUNGARY:
            case Nation.SPAIN:
                return capitalProductionValue/2;
            case Nation.OTTOMANS:
                return capitalProductionValue/4;
            default:
                return 0;
        }
    }
}