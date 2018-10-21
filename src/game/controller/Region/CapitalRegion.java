package game.controller.Region;

/**
 * CapitalRegion.java  Date Created: Nov 8, 2012
 *
 * Purpose: To keep track of the details of a Captial Region.
 *
 * Description:
 *
 * @author Chrisb
 */
public abstract class CapitalRegion extends LandRegion {

    public CapitalRegion(String regionName, int nation, int productionValue, boolean onCoast, boolean hasPort) {
        super(regionName, nation, true, onCoast, hasPort, false);
        capitalStatus = LIBERATED;
        capitalProductionValue = productionValue;
    }

    public void captureCapital() { capitalStatus = CAPTURED; }
    public void annexCapital() { capitalStatus = ANNEXED; }
    public void liberateCapital() { capitalStatus = LIBERATED; }

    public int getCaptialStatus() { return capitalStatus; }

    private int capitalStatus;

    protected static int capitalProductionValue;

    public static final int CAPTURED     = 0;
    public static final int ANNEXED      = 1;
    public static final int LIBERATED    = 2;
}