package game.controller.Region;

import game.util.GameMsg;
import lobby.controller.Nation;

/**
 * Berlin.java  Date Created: Nov 9, 2012
 *
 * Purpose: To represent the region of Berlin
 *
 * Description:
 *
 * @author Chrisb
 */
public class Berlin extends CapitalRegion {
    public Berlin() {
        super(GameMsg.getString("region.Berlin"), Nation.PRUSSIA, 12, false, false);
    }

    public int getProductionValue() {
        if (isUprising() || isEnemyOccupied())
            return 0;

        switch (controllingNation) {
            case Nation.PRUSSIA:
                return capitalProductionValue;
            case Nation.FRANCE:
            case Nation.GREAT_BRITAIN:
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