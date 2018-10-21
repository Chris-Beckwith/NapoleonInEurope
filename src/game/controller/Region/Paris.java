package game.controller.Region;

import game.util.GameMsg;
import lobby.controller.Nation;

/**
 * Paris.java  Date Created: Nov 9, 2012
 *
 * Purpose: To represent the region of Paris
 *
 * Description:
 *
 * @author Chrisb
 */
public class Paris extends CapitalRegion {
    public Paris() {
        super(GameMsg.getString("region.Paris"), Nation.FRANCE, 12, false, false);
    }

    public int getProductionValue() {
        if (isUprising() || isEnemyOccupied())
            return 0;

        switch (controllingNation) {
            case Nation.FRANCE:
                return capitalProductionValue;
            case Nation.GREAT_BRITAIN:
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