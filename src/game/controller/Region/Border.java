package game.controller.Region;

/**
 * Border.java  Date Created: Feb 25, 2013
 *
 * Purpose: To represent a border between two regions.
 *
 * Description: To function as an edge in a graph with weight = 1.
 *
 * @author Chrisb
 */
public class Border {

    public Border(Region one, Region two) {
        this.one = one;
        this.two = two;
        this.movementCost = 1;
    }

    public Region[] getBorderingRegions() {
        return new Region[] { one, two };
    }

    public int getWeight() { return movementCost; }

    private final int movementCost;
    private final Region one;
    private final Region two;
}