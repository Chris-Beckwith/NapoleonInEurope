package game.controller.Region;

import lobby.controller.Nation;
import game.util.GameMsg;

/**
 * Madrid.java  Date Created: Nov 9, 2012
 *
 * Purpose: To represent the region of Madrid
 *
 * Description:
 *
 * @author Chrisb
 */
public class Madrid extends CapitalRegion {
    public Madrid() {
        super(GameMsg.getString("region.Madrid"), Nation.SPAIN, 3, false, false);
    }

    public int getProductionValue() {
        if (isUprising() || isEnemyOccupied())
            return 0;

        switch (controllingNation) {
            case Nation.SPAIN:
                return capitalProductionValue;
            case Nation.FRANCE:
            case Nation.GREAT_BRITAIN:
            case Nation.PRUSSIA:
            case Nation.RUSSIA:
            case Nation.AUSTRIA_HUNGARY:
                return 2;
            case Nation.OTTOMANS:
                return 1;
            default:
                return 0;
        }
    }
}