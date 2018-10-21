package game.controller.Region;

import game.controller.Unit.NavalSquadron;
import game.controller.Unit.MilitaryUnit;

/**
 * Ports.java  Date Created: Nov 19, 2012
 *
 * Purpose: To keep track of the information related to a port.
 *
 * Description:
 *
 * @author Chrisb
 */
public class Port extends SeaRegion {

    public Port(String name, LandRegion portRegion, SeaRegion[] seaAdjacencies) {
        super(name);
        this.portRegion = portRegion;
        this.seaAdjacencies = seaAdjacencies;
        type = PORT_REGION;
        this.heading = -1;
    }

    public Port(String name, LandRegion portRegion, SeaRegion[] seaAdjacencies, int heading) {
        this(name, portRegion, seaAdjacencies);
        this.heading = heading;
    }

    public boolean isEnemyOccupied() {
        if (isOccupied())
            for ( MilitaryUnit u : getOccupyingUnits() )
                if (u.getOwningNation() != portRegion.getControllingNation() )
                    return true;

        return false;
    }

    public boolean isSeaAdjacent(SeaRegion sea) {
        for (SeaRegion adjacent : getSeaAdjacencies())
            if ( adjacent.equals(sea) )
                return true;

        return false;
    }

    public boolean canPlaceNavalSquadron() { return turnsToBuild <= 0; }
    public void navalSquadronBuilt() { turnsToBuild = 4; }

    public SeaRegion[] getSeaAdjacencies() { return seaAdjacencies; }
    public LandRegion getPortRegion() { return portRegion; }
    public String getPortName() { return "Port - " + regionName + " (" + portRegion.toString() + getHeading() + ")"; }

    private String getHeading() {
        if (heading < 0)
            return "";
        switch (heading) {
            case NORTH: return " - North";
            case NORTH_WEST: return " - NorthWest";
            case WEST: return " - West";
            case SOUTH_WEST: return " - SouthWest";
            case SOUTH: return " - South";
            case SOUTH_EAST: return " - SouthEast";
            case EAST: return " - East";
            case NORTH_EAST: return " - NorthEast";
        }
        return "";
    }

    private int heading;
    private LandRegion portRegion;
    private SeaRegion[] seaAdjacencies;
    private int turnsToBuild = 0;

    public static final int NORTH = 0;
    public static final int NORTH_WEST = 1;
    public static final int WEST = 2;
    public static final int SOUTH_WEST = 3;
    public static final int SOUTH = 4;
    public static final int SOUTH_EAST = 5;
    public static final int EAST = 6;
    public static final int NORTH_EAST = 7;
}