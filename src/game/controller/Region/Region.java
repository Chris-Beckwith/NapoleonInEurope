package game.controller.Region;

import game.controller.Unit.MilitaryUnit;

import java.util.ArrayList;

/**
 * Region.java  Date Created: Feb 28, 2013
 *
 * Purpose:
 *
 * Description:
 *
 * @author Chrisb
 */
public class Region {
    public Region(String name) {
        regionName = name;
        occupyingUnits = new ArrayList<MilitaryUnit>();
    }

    public boolean equals(Region region) {
        return (region.toString()).equals(this.toString());
    }

    public ArrayList<MilitaryUnit> getOccupyingUnits() { return occupyingUnits; }
    
    public boolean isOccupied() { return getOccupyingUnits().size() > 0; }

    public boolean addUnit(MilitaryUnit unit) {
        occupyingUnits.ensureCapacity(occupyingUnits.size() + 1);
        return occupyingUnits.add(unit);
    }

    public boolean removeUnit(MilitaryUnit unit) { return occupyingUnits.remove(unit); }
    public boolean removeUnits(ArrayList<MilitaryUnit> units) { return occupyingUnits.removeAll(units); }

    public String toString() { return regionName; }
    public int getType() { return type; }

    protected String regionName;
    protected ArrayList<MilitaryUnit> occupyingUnits;
    protected int type;

    public static final int LAND_REGION = 0;
    public static final int SEA_REGION  = 1;
    public static final int PORT_REGION = 2;
}