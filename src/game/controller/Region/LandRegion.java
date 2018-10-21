package game.controller.Region;

import lobby.controller.Nation;
import game.controller.Unit.MilitaryUnit;
import game.util.GameMsg;

import java.util.ArrayList;

/**
 * Region.java  Date Created: Nov 8, 2012
 *
 * Purpose: To keep track of the details of a region.
 *
 * Description: name, isHomeland, controllingNation, productionValue,
 *
 * @author Chrisb
 */
public class LandRegion extends Region {

    //Capital Region
    public LandRegion(String regionName, int controllingNation, boolean isCapital, boolean onCoast, boolean hasPort, boolean isAfrica) {
        super(regionName);
        isHomeland = true;
        this.isCapital = isCapital;
        homelandNation = controllingNation;
        this.controllingNation = controllingNation;
        this.hasPort = hasPort;
        this.onCoast = onCoast;
        this.isAfrica = isAfrica;
        uprising = 0;
        type = LAND_REGION;
    }

    //Homeland Region
    public LandRegion(String regionName, int controllingNation, boolean onCoast, boolean hasPort, boolean isAfrica) {
        this(regionName, controllingNation, false, onCoast, hasPort, isAfrica);
    }

    //Non-Homeland Region
    public LandRegion(String regionName, boolean onCoast, boolean hasPort, boolean isAfrica) {
        super(regionName);
        isHomeland = false;
        isCapital = false;
        homelandNation = Nation.NEUTRAL;
        controllingNation = Nation.NEUTRAL;
        this.hasPort = hasPort;
        this.onCoast = onCoast;
        this.isAfrica = isAfrica;
        uprising = 0;
        type = LAND_REGION;
    }

    public ArrayList<MilitaryUnit> getNationsUnits(int nation) {
        ArrayList<MilitaryUnit> nationsUnits = new ArrayList<MilitaryUnit>();

        for ( MilitaryUnit unit : getOccupyingUnits() ) {
            if (unit.getOwningNation() == nation) {
                nationsUnits.ensureCapacity(nationsUnits.size() + 1);
                nationsUnits.add(unit);
            }
        }
        return nationsUnits;
    }

    public int getControllingNation() { return controllingNation; }
    public boolean isNativeOwned() { return getControllingNation() == getHomelandNation(); }
    public boolean isUprising() { return uprising > 0; }

    public boolean isEnemyOccupied() {
        if (isOccupied())
            for ( MilitaryUnit u : getOccupyingUnits() )
                if ( u.getOwningNation() != getControllingNation() )
                    return true;

        return false;
    }

    public void setControllingNation(int nation) { controllingNation = nation; }
    public void fomentUprising() { if (uprising < 1) uprising++; }
    public void addUprising() { if (uprising < 2) uprising++; }
    public void suppressUprising() { uprising = 0; }

    public boolean isHomeland() { return isHomeland; }
    public boolean isCapital() { return isCapital; }
    public int getHomelandNation() { return homelandNation; }
    public boolean hasPort() { return hasPort; }
    public boolean isOnCoast() { return onCoast; }
    public boolean isAfrica() { return isAfrica; }

    public int getProductionValue() {
        if (isUprising() || isEnemyOccupied())
            return 0;
        else if (controllingNation == Nation.OTTOMANS)
            return ottomanProductionValue;
        else if (isAfrica && !regionName.equals(GameMsg.getString("Egypt")))
            return africanProductionValue;
        else
            return fullProductionValue;
    }

    protected final boolean isHomeland;
    protected final boolean isCapital;
    protected final int homelandNation;
    protected final boolean hasPort;
    protected final boolean onCoast;
    protected final boolean isAfrica;
    protected int controllingNation;
    protected int uprising;

    protected static final int ottomanProductionValue = 1;
    protected static final int africanProductionValue = 1;
    protected static final int fullProductionValue = 2;
}