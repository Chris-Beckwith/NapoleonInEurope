package game.controller.Region;

import lobby.controller.Nation;
import game.util.GameMsg;

/**
 * Constantinople.java  Date Created: Nov 9, 2012
 *
 * Purpose: To represent the region of Constantinople
 *
 * Description:
 *
 * @author Chrisb
 */
public class Constantinople extends CapitalRegion {
    public Constantinople() {
        super(GameMsg.getString("region.Constantinople"), Nation.OTTOMANS, 3, true, true);
    }

    public int getProductionValue() {
        if (isUprising() || isEnemyOccupied())
            return 0;

        switch (controllingNation) {
            case Nation.OTTOMANS:
                return capitalProductionValue;
            case Nation.FRANCE:
            case Nation.GREAT_BRITAIN:
            case Nation.PRUSSIA:
            case Nation.RUSSIA:
            case Nation.AUSTRIA_HUNGARY:
            case Nation.SPAIN:
                return 2;
            default:
                return 0;
        }
    }
}