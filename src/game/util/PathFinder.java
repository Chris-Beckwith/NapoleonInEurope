package game.util;

import game.controller.MapInstance;
import game.controller.NationInstance;
import game.controller.Unit.MilitaryUnit;
import game.controller.Region.Region;
import game.controller.Region.Border;
import game.controller.Region.SeaRegion;
import game.controller.Region.LandRegion;

import java.util.*;

/**
 * PathFinder.java  Date Created: Feb 28, 2013
 *
 * Purpose: Methods for finding shortest paths around the map.
 *
 * Description: There will be static paths and dynamic paths, that will be affected by the state of the game.
 * The static paths will be run once and never again unless the map changes or a new map is implemented.
 * The dynamic paths will be run the user requests to see a path route.
 *
 * Options for modifying path finding:
 *  [ ] Use Straits
 *  [ ] Use Sea Regions
 *  Winter (only affects sea)
 *
 *  Dynamic Options
 *  ---------------
 *  [ ] Observe Rights of Passage
 *  [ ] Avoid interception (by sea)
 *
 *
 *  [ ] Avoid enemy ground troops
 *
 * @author Chrisb
 */

/**
 * Different Option Settings
 * -------------------------
 *
 * [ ] Use Straits
 * [ ] Use Sea Regions
 * [ ] isWinter
 *
 * [x] Use Straits
 * [ ] Use Sea Regions
 * [ ] isWinter
 *
 * [ ] Use Straits
 * [x] Use Sea Regions
 * [ ] isWinter
 *
 * [x] Use Straits
 * [x] Use Sea Regions
 * [ ] isWinter
 *
 * [ ] Use Straits
 * [ ] Use Sea Regions
 * [x] isWinter
 *
 * [x] Use Straits
 * [ ] Use Sea Regions
 * [x] isWinter
 *
 * [ ] Use Straits
 * [x] Use Sea Regions
 * [x] isWinter
 *
 * [x] Use Straits
 * [x] Use Sea Regions
 * [x] isWinter
 *
 **/

/**
 *
 * [ ] Use Straits
 * [ ] Use Sea Regions
 * [ ] Observe Rights of Passage
 * [ ] Avoid interception (by sea)
 * [ ] isWinter
 *
 * [x] Use Straits
 * [ ] Use Sea Regions
 * [ ] Observe Rights of Passage
 * [ ] Avoid interception (by sea)
 * [ ] isWinter
 *
 * [ ] Use Straits
 * [x] Use Sea Regions
 * [ ] Observe Rights of Passage
 * [ ] Avoid interception (by sea)
 * [ ] isWinter
 *
 * [x] Use Straits
 * [x] Use Sea Regions
 * [ ] Observe Rights of Passage
 * [ ] Avoid interception (by sea)
 * [ ] isWinter
 *
 * [ ] Use Straits
 * [ ] Use Sea Regions
 * [x] Observe Rights of Passage
 * [ ] Avoid interception (by sea)
 * [ ] isWinter
 *
 * [x] Use Straits
 * [ ] Use Sea Regions
 * [x] Observe Rights of Passage
 * [ ] Avoid interception (by sea)
 * [ ] isWinter
 *
 * [ ] Use Straits
 * [x] Use Sea Regions
 * [x] Observe Rights of Passage
 * [ ] Avoid interception (by sea)
 * [ ] isWinter
 *
 * [x] Use Straits
 * [x] Use Sea Regions
 * [x] Observe Rights of Passage
 * [ ] Avoid interception (by sea)
 * [ ] isWinter
 *
 * [ ] Use Straits
 * [ ] Use Sea Regions
 * [ ] Observe Rights of Passage
 * [x] Avoid interception (by sea)
 * [ ] isWinter
 *
 * [x] Use Straits
 * [ ] Use Sea Regions
 * [ ] Observe Rights of Passage
 * [x] Avoid interception (by sea)
 * [ ] isWinter
 *
 * [ ] Use Straits
 * [x] Use Sea Regions
 * [ ] Observe Rights of Passage
 * [x] Avoid interception (by sea)
 * [ ] isWinter
 *
 * [x] Use Straits
 * [x] Use Sea Regions
 * [ ] Observe Rights of Passage
 * [x] Avoid interception (by sea)
 * [ ] isWinter
 *
 * [ ] Use Straits
 * [ ] Use Sea Regions
 * [x] Observe Rights of Passage
 * [x] Avoid interception (by sea)
 * [ ] isWinter
 *
 * [x] Use Straits
 * [ ] Use Sea Regions
 * [x] Observe Rights of Passage
 * [x] Avoid interception (by sea)
 * [ ] isWinter
 *
 * [ ] Use Straits
 * [x] Use Sea Regions
 * [x] Observe Rights of Passage
 * [x] Avoid interception (by sea)
 * [ ] isWinter
 *
 * [x] Use Straits
 * [x] Use Sea Regions
 * [x] Observe Rights of Passage
 * [x] Avoid interception (by sea)
 * [ ] isWinter
 *
 * [ ] Use Straits
 * [ ] Use Sea Regions
 * [ ] Observe Rights of Passage
 * [ ] Avoid interception (by sea)
 * [x] isWinter
 *
 * [x] Use Straits
 * [ ] Use Sea Regions
 * [ ] Observe Rights of Passage
 * [ ] Avoid interception (by sea)
 * [x] isWinter
 *
 * [ ] Use Straits
 * [x] Use Sea Regions
 * [ ] Observe Rights of Passage
 * [ ] Avoid interception (by sea)
 * [x] isWinter
 *
 * [x] Use Straits
 * [x] Use Sea Regions
 * [ ] Observe Rights of Passage
 * [ ] Avoid interception (by sea)
 * [x] isWinter
 *
 * [ ] Use Straits
 * [ ] Use Sea Regions
 * [x] Observe Rights of Passage
 * [ ] Avoid interception (by sea)
 * [x] isWinter
 *
 * [x] Use Straits
 * [ ] Use Sea Regions
 * [x] Observe Rights of Passage
 * [ ] Avoid interception (by sea)
 * [x] isWinter
 *
 * [ ] Use Straits
 * [x] Use Sea Regions
 * [x] Observe Rights of Passage
 * [ ] Avoid interception (by sea)
 * [x] isWinter
 *
 * [x] Use Straits
 * [x] Use Sea Regions
 * [x] Observe Rights of Passage
 * [ ] Avoid interception (by sea)
 * [x] isWinter
 *
 * [ ] Use Straits
 * [ ] Use Sea Regions
 * [ ] Observe Rights of Passage
 * [x] Avoid interception (by sea)
 * [x] isWinter
 *
 * [x] Use Straits
 * [ ] Use Sea Regions
 * [ ] Observe Rights of Passage
 * [x] Avoid interception (by sea)
 * [x] isWinter
 *
 * [ ] Use Straits
 * [x] Use Sea Regions
 * [ ] Observe Rights of Passage
 * [x] Avoid interception (by sea)
 * [x] isWinter
 *
 * [x] Use Straits
 * [x] Use Sea Regions
 * [ ] Observe Rights of Passage
 * [x] Avoid interception (by sea)
 * [x] isWinter
 *
 * [ ] Use Straits
 * [ ] Use Sea Regions
 * [x] Observe Rights of Passage
 * [x] Avoid interception (by sea)
 * [x] isWinter
 *
 * [x] Use Straits
 * [ ] Use Sea Regions
 * [x] Observe Rights of Passage
 * [x] Avoid interception (by sea)
 * [x] isWinter
 *
 * [ ] Use Straits
 * [x] Use Sea Regions
 * [x] Observe Rights of Passage
 * [x] Avoid interception (by sea)
 * [x] isWinter
 *
 * [x] Use Straits
 * [x] Use Sea Regions
 * [x] Observe Rights of Passage
 * [x] Avoid interception (by sea)
 * [x] isWinter
 */


public final class PathFinder {

    private MapInstance map;

    public PathFinder (MapInstance map) {
        this.map = map;
        landRegions = map.getLandRegions();
        seaRegions = map.getSeaRegions();
    }

    //Static path finding.. will only be run once and paths will be saved for future reference.

    /*
     * Land Only (000) & (100)
     * [ ] Use Straits
     * [ ] Use Sea Regions
     * [ ] isWinter
     */
    public void findLandOnlyPaths() {
        Border[] landBorders = map.getLandBorders();
        landPaths = new ArrayList< HashMap<Region, Region> >(landRegions.length);

        for (Region r: landRegions) {
            landPaths.add(DijkstraAlgorithm(landBorders, r));
        }
    }
    /*
     * Land Only with Straits (001) & (101)
     * [x] Use Straits
     * [ ] Use Sea Regions
     * [ ] isWinter
     */
    public void findLandOnlyPathsWithStraits() {
        Border[] landStraitBorders = map.getLandAndStraitBorders();
        landStraitPaths = new ArrayList< HashMap<Region, Region> >(landRegions.length);

        for (Region r: landRegions) {
            landStraitPaths.add(DijkstraAlgorithm(landStraitBorders, r));
        }
    }
    /*
     * Land and Sea, no straits (010)
     * [ ] Use Straits
     * [x] Use Sea Regions
     * [ ] isWinter
     */
    public void findLandSeaPathsWithoutStraits() {
        Border[] landSeaBorders_noStraits = map.getAllLandSeaBorders_noStraits();
        landSeaPaths_noStraits = new ArrayList< HashMap<Region, Region> >(landRegions.length);

        for (Region r: landRegions) {
            landSeaPaths_noStraits.add(DijkstraAlgorithm(landSeaBorders_noStraits, r));
        }
    }
    /*
     * All Paths (not winter) (011)
     * [x] Use Straits
     * [x] Use Sea Regions
     * [ ] isWinter
     */
    public void findAllPaths() {
        Border[] allBorders = map.getAllBorders();
        allPaths = new ArrayList< HashMap<Region, Region> >(landRegions.length);

        for (Region r: landRegions) {
            allPaths.add(DijkstraAlgorithm(allBorders, r));
        }

        findSeaPaths();
    }
    /*
     * Land and Sea during Winter (110)
     * [ ] Use Straits
     * [x] Use Sea Regions
     * [x] isWinter
     */
    public void findLandSeaWinterPathsWithoutStraits() {
        Border[] allWinterBorders_noStraits = map.getAllWinterBorders_noStraits();
        landSeaWinterPaths_noStraits = new ArrayList< HashMap<Region, Region> >(landRegions.length);

        for (Region r: landRegions) {
            landSeaWinterPaths_noStraits.add(DijkstraAlgorithm(allWinterBorders_noStraits, r));
        }
    }
    /*
     * All Paths (winter) (111)
     * [x] Use Straits
     * [x] Use Sea Regions
     * [x] isWinter
     */
    public void findAllWinterPaths() {
        Border[] allWinterBorders = map.getAllWinterBorders();
        allWinterPaths = new ArrayList< HashMap<Region, Region> >(landRegions.length);

        for (Region r: landRegions) {
            allWinterPaths.add(DijkstraAlgorithm(allWinterBorders, r));
        }

        findSeaWinterPaths();
    }

    //Find Sea Paths
    public void findSeaPaths() {
        Border[] seaBorders = map.getSeaBorders();
        seaPaths = new ArrayList< HashMap<Region, Region> >(seaRegions.length);

        for (Region s: seaRegions) {
            seaPaths.add(DijkstraAlgorithm(seaBorders, s));
        }
    }

    public void findSeaWinterPaths() {
        Border[] seaBorders = map.getWinterSeaBorders();
        seaWinterPaths = new ArrayList< HashMap<Region, Region> >(seaRegions.length);

        for (Region s: seaRegions) {
            seaWinterPaths.add(DijkstraAlgorithm(seaBorders, s));
        }
    }

    public void findDynamicPaths(NationInstance nation, boolean useStraits, boolean useSea, boolean isWinter, boolean observeRightsOfPassage, boolean avoidIntercept) {
        ArrayList<Border> borders = new ArrayList<Border>();
        allDynamicPaths = new ArrayList< HashMap<Region, Region> >(landRegions.length);

        if (useStraits)
            borders.addAll(Arrays.asList(map.getStraitBorders()));
        if (useSea)
            if (!isWinter)
                borders.addAll(Arrays.asList(map.getAllLandSeaBorders_noStraits()));
            else
                borders.addAll(Arrays.asList(map.getAllWinterBorders_noStraits()));
        else
            borders.addAll(Arrays.asList(map.getLandBorders()));

        for (Region r: landRegions) {
            allDynamicPaths.add(DynamicDijkstraAlgorithm(borders, r, nation, observeRightsOfPassage, avoidIntercept));
        }
    }

    /**
     * Dynamic Dijkstra's Algorithm
     * --------------------
     * Used to find shortest paths from a 'source' region (node) to all other regions using the provided borders (edges)
     * @param borders - the edges that will be used to calculate paths.
     * @param source - the starting point for with paths will be calculated from.
     * @param nation - the nation path finding is being created for.
     * @param observeRightsOfPassage - whether or not to observe rights of passage.
     * @param avoidIntercept -  whether or not to avoid potential interception from enemy Naval squadrons.
     * @return - all shortests paths from source to all other regions.
     */
    private HashMap<Region, Region> DynamicDijkstraAlgorithm(ArrayList<Border> borders, Region source,
                                             NationInstance nation, boolean observeRightsOfPassage, boolean avoidIntercept) {
        edges = new ArrayList<Border>(borders);

        settledNodes = new HashSet<Region>();
        unsettledNodes = new HashSet<Region>();
        distance = new HashMap<Region, Integer>();
        predecessors = new HashMap<Region, Region>();

        distance.put(source, 0);
        unsettledNodes.add(source);

        while (unsettledNodes.size() > 0) {
            Region node = getMinimum(unsettledNodes);
            settledNodes.add(node);
            unsettledNodes.remove(node);
            findDynamicMinimalDistances(node, nation, observeRightsOfPassage, avoidIntercept);
        }

        return (HashMap<Region, Region>)predecessors;
    }

    private void findDynamicMinimalDistances(Region node, NationInstance nation, boolean observeRightsOfPassage, boolean avoidIntercept) {
        List<Region> adjacentNodes = getDynamicNeighbors(node, nation, observeRightsOfPassage, avoidIntercept);

        for (Region target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                unsettledNodes.add(target);
            }
        }
    }

    //Dynamic neighbor finding
    private List<Region> getDynamicNeighbors(Region node, NationInstance nation, boolean observeRightsOfPassage, boolean avoidIntercept) {
        List<Region> neighbors = new ArrayList<Region>();
        for (Border edge : edges) {
            if ( edge.getBorderingRegions()[0].equals(node) && !isSettled(edge.getBorderingRegions()[1]) )  {
                if (isNeighborSafe(edge.getBorderingRegions()[1], nation, observeRightsOfPassage, avoidIntercept))
                    neighbors.add(edge.getBorderingRegions()[1]);
            } else if ( edge.getBorderingRegions()[1].equals(node) && !isSettled(edge.getBorderingRegions()[0]) ) {
                if (isNeighborSafe(edge.getBorderingRegions()[0], nation, observeRightsOfPassage, avoidIntercept))
                    neighbors.add(edge.getBorderingRegions()[0]);
            }
        }
        return neighbors;
    }

    private boolean isNeighborSafe(Region neighbor, NationInstance nation, boolean observeRightsOfPassage, boolean avoidIntercept) {
        try {
            LandRegion landRegion = (LandRegion)neighbor;
            int controllingNation = landRegion.getControllingNation();

            if (controllingNation == nation.getNationNumber())
                return true;

            if (observeRightsOfPassage)
                return nation.hasRightOfPassage(controllingNation) || nation.isAllied(controllingNation);

        } catch (ClassCastException e) {
            //Not a land region, this is fine.
        }

        try {
            SeaRegion seaRegion = (SeaRegion)neighbor;

            if (avoidIntercept)
                for (MilitaryUnit ns: seaRegion.getOccupyingUnits())
                    if (nation.isEnemy(ns.getOwningNation()))
                        return false;

        } catch (ClassCastException e) {
            //Not a sea region, this is fine as long as it's a land region.
        }

        return true;
    }

    /**
     * Dijkstra's Algorithm
     * --------------------
     * Used to find shortest paths from a 'source' region (node) to all other regions using the provided borders (edges)
     * @param borders - the edges that will be used to calculate paths.
     * @param source - the starting point for with paths will be calculated from.
     * @return - all shortests paths from source to all other regions.
     */
    private HashMap<Region, Region> DijkstraAlgorithm(Border[] borders, Region source) {
        edges = new ArrayList<Border>(Arrays.asList(borders));

        settledNodes = new HashSet<Region>();
        unsettledNodes = new HashSet<Region>();
        distance = new HashMap<Region, Integer>();
        predecessors = new HashMap<Region, Region>();

        distance.put(source, 0);
        unsettledNodes.add(source);

        while (unsettledNodes.size() > 0) {
            Region node = getMinimum(unsettledNodes);
            settledNodes.add(node);
            unsettledNodes.remove(node);
            findMinimalDistances(node);
        }

        return (HashMap<Region, Region>)predecessors;
    }

    //Static neighbor finding
    private List<Region> getNeighbors(Region node) {
        List<Region> neighbors = new ArrayList<Region>();
        for (Border edge : edges) {
            if ( edge.getBorderingRegions()[0].equals(node) && !isSettled(edge.getBorderingRegions()[1]) )  {
                neighbors.add(edge.getBorderingRegions()[1]);
            } else if ( edge.getBorderingRegions()[1].equals(node) && !isSettled(edge.getBorderingRegions()[0]) ) {
                neighbors.add(edge.getBorderingRegions()[0]);
            }
        }
        return neighbors;
    }

    //Dijkstra Helper Methods
    private void findMinimalDistances(Region node) {
        List<Region> adjacentNodes = getNeighbors(node);

        for (Region target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                unsettledNodes.add(target);
            }
        }
    }

    private int getDistance(Region node, Region target) {
        for (Border edge : edges ) {
            if ( (edge.getBorderingRegions()[0].equals(node) && edge.getBorderingRegions()[1].equals(target))
                || (edge.getBorderingRegions()[1].equals(node) && edge.getBorderingRegions()[0].equals(target))) {
                if (node.getType() == Region.SEA_REGION && target.getType() == Region.SEA_REGION)
                    return 0;  //if moving between two seas cost is zero, 0.  Units must not have moved before or after moving by sea.
                return 1;      //This is not accounted for with this method but should still provide accurate paths.  todo
            }
        }
        throw new RuntimeException("Could not find distance");
    }

    private Region getMinimum(Set<Region> regions) {
        Region min = null;
        for (Region r : regions) {
            if (min == null)
                min = r;
            else
                if (getShortestDistance(r) < getShortestDistance(min))
                    min = r;
        }
        return min;
    }

    private boolean isSettled(Region region) {
        return settledNodes.contains(region);
    }

    private int getShortestDistance(Region destination) {
        Integer d = distance.get(destination);
        return (d == null) ? Integer.MAX_VALUE : d;
    }


    /*
     * Static Path Look-up
     */
    public LinkedList<Region> getLandPath(Region source, Region target) {
        if (!isLandRegion(source) || !isLandRegion(target))
            return null;
        return getPath(landPaths.get(getLandRegionIndex(source)), target);
    }

    public LinkedList<Region> getLandStraitPath(Region source, Region target) {
        if (!isLandRegion(source) || !isLandRegion(target))
            return null;
        return getPath(landStraitPaths.get(getLandRegionIndex(source)), target);
    }

    public LinkedList<Region> getLandSeaPathWithoutStraits(Region source, Region target) {
        if (!isLandRegion(source) || !isLandRegion(target))
            return null;
        return getPath(landSeaPaths_noStraits.get(getLandRegionIndex(source)), target);
    }

    public LinkedList<Region> getAllPath(Region source, Region target) {
        if (!isLandRegion(source) || !isLandRegion(target))
            return null;
        return getPath(allPaths.get(getLandRegionIndex(source)), target);
    }

    public LinkedList<Region> getLandSeaWinterPathWithoutStraits(Region source, Region target) {
        if (!isLandRegion(source) || !isLandRegion(target))
            return null;
        return getPath(landSeaWinterPaths_noStraits.get(getLandRegionIndex(source)), target);
    }

    public LinkedList<Region> getAllWinterPath(Region source, Region target) {
        if (!isLandRegion(source) || !isLandRegion(target))
            return null;
        return getPath(allWinterPaths.get(getLandRegionIndex(source)), target);
    }

    public LinkedList<Region> getSeaPath(Region source, Region target) {
        if (!isSeaRegion(source) || !isSeaRegion(target))
            return null;
        return getPath(seaPaths.get(getSeaRegionIndex(source)), target);
    }

    public LinkedList<Region> getSeaWinterPath(Region source, Region target) {
        if (!isSeaRegion(source) || !isSeaRegion(target))
            return null;
        return getPath(seaWinterPaths.get(getSeaRegionIndex(source)), target);
    }

    /*
     * Dynamic Path Look-up
     */
    public LinkedList<Region> getDynamicPath(Region source, Region target) {
        if (!isLandRegion(source) || !isLandRegion(target))
            return null;
        return getPath(allDynamicPaths.get(getLandRegionIndex(source)), target);
    }

    /**
     * Search the sourcePaths of a region to see if there is a path from 'source' to 'target'
     * @param sourcePaths - paths from 'source' region to all regions.
     * @param target - the destination region of the requested path.
     * @return - the shortest path between the two regions, if one exists; returns null otherwise.
     */
    private LinkedList<Region> getPath(Map<Region, Region> sourcePaths, Region target) {
        LinkedList<Region> path = new LinkedList<Region> ();
        Region step = target;

        if (sourcePaths.get(step) == null)
            return null;

        path.add(step);

        while (sourcePaths.get(step) != null) {
            step = sourcePaths.get(step);
            path.add(step);
        }
        Collections.reverse(path);
        return path;
    }

    //Utility methods
    private int getLandRegionIndex(Region source) {
        int i = 0;
        while (!landRegions[i].equals(source))
            i++;
        return i;
    }

    private boolean isLandRegion(Region region) {
        return region.getType() == Region.LAND_REGION;
    }

    private int getSeaRegionIndex(Region source) {
        int i = 0;
        while (!seaRegions[i].equals(source))
            i++;
        return i;
    }

    private boolean isSeaRegion(Region region) {
        return region.getType() == Region.SEA_REGION;
    }

    //Variables
    private ArrayList<Border> edges;
    private Set<Region> settledNodes;
    private Set<Region> unsettledNodes;
    private Map<Region, Integer> distance;
    private Map<Region, Region> predecessors;
    private ArrayList< HashMap<Region, Region> > landPaths;
    private ArrayList< HashMap<Region, Region> > landStraitPaths;
    private ArrayList< HashMap<Region, Region> > allPaths;
    private ArrayList< HashMap<Region, Region> > allWinterPaths;
    private ArrayList< HashMap<Region, Region> > seaPaths;
    private ArrayList< HashMap<Region, Region> > seaWinterPaths;
    private ArrayList< HashMap<Region, Region> > landSeaPaths_noStraits;
    private ArrayList< HashMap<Region, Region> > landSeaWinterPaths_noStraits;
    private ArrayList< HashMap<Region, Region> > allDynamicPaths;
    private Region[] landRegions;
    private Region[] seaRegions;
}