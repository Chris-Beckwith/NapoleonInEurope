package game.controller.Region;

import game.controller.Unit.NavalSquadron;
import game.controller.Unit.MilitaryUnit;

import java.util.ArrayList;

/**
 * SeaRegion.java  Date Created: Feb 26, 2013
 *
 * Purpose: To keep track of information related to a specific sea region.
 *
 * Description:
 *
 * @author Chrisb
 */
public class SeaRegion extends Region {

    public SeaRegion(String name) {
        super(name);
        type = SEA_REGION;
    }

    public String getSeaName() { return "Sea - " + super.toString(); }
}