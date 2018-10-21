package game.controller;

import game.controller.Region.LandRegion;
import game.controller.Region.Region;
import game.controller.Region.Port;
import game.controller.Unit.*;
import game.util.GameMsg;
import game.util.GameLogger;
import shared.controller.LobbyConstants;
import lobby.controller.Nation;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * NationInstance.java  Date Created: Nov 7, 2012
 *
 * Purpose: To keep track of everything related to a Nation in game.
 *
 * Description:
 *
 * @author Chrisb
 */
public class NationInstance {

    public NationInstance(int nationNumber, int startingPap, int startingScene, MapInstance map) {
        this.nationNumber = nationNumber;
        controllingUser = GameMsg.getString("controllingUser.uncontrolled");
        this.map = map;
        scenario = startingScene;
        PAPs = startingPap;
        productionPoints = 0;
        isPlayerNation = false;
        hasBeenControlled = false;
        isControlledNation = false;
        hasAnnexedHomelandThisCongress = false;
        passageRight = new ArrayList<Integer>();
        passageVoluntary = new ArrayList<Integer>();
        alliedNations = new ArrayList<Integer>();
        enemyNations = new ArrayList<Integer>();
        startingWarNations = new ArrayList<Integer>();
        diplomaticRatings = new int[7];
        gracePeriods = new HashMap<Integer, int[]>();
        reverseGracePeriods = new HashMap<Integer, int[]>();
        commitmentRating = 0;

        //move this
        isCapitalCaptured = false;
        hasCapitalBeenCaptured = false;
        isCapitalAnnexed = false;

        initNation();
    }

    private void initNation() {
        military = new ArrayList<MilitaryUnit>();
        territory = new ArrayList<LandRegion>();
        territory.addAll(getHomelands());
        switch(nationNumber) {
            case Nation.FRANCE:
                switch (scenario) {
                    case LobbyConstants.I: break;
                    case LobbyConstants.II: break;
                    case LobbyConstants.III: break;
                    case LobbyConstants.IV: break;
                    case LobbyConstants.V: break;
                    case LobbyConstants.V_V: break;
                    case LobbyConstants.VI: break;
                    case LobbyConstants.VII: break;
                    case LobbyConstants.VIII: break;
                    case LobbyConstants.IX: break;
                    case LobbyConstants.X:
                        //12 Inf || 1 E. Inf
                        //3  Cav || 1 H. Cav
                        //2  Art || 1 Gen || 3 Nav
                        for (int i = 0; i < 12; i++)
                            military.add(new Infantry(nationNumber));
                        military.add(new EliteInfantry(nationNumber));
                        for (int i = 0; i < 3; i++)
                            military.add(new Cavalry(nationNumber));
                        military.add(new HeavyCavalry(nationNumber));
                        for (int i = 0; i < 2; i++)
                            military.add(new Artillery(nationNumber));
                        military.add(new General(nationNumber));
                        for (int i = 0; i < 3; i++)
                            military.add(new NavalSquadron(nationNumber));
                        //French Homelands only
                        //Diplomatic Ratings
                        diplomaticRatings[0] = -1;
                        diplomaticRatings[1] =  9;
                        diplomaticRatings[2] =  9;
                        diplomaticRatings[3] =  9;
                        diplomaticRatings[4] =  9;
                        diplomaticRatings[5] =  9;
                        diplomaticRatings[6] =  9;
                        //Commitment Rating
                        commitmentRating = 7;
                        break;
                }
                break;
            case Nation.GREAT_BRITAIN:
                switch (scenario) {
                    case LobbyConstants.I: break;
                    case LobbyConstants.II: break;
                    case LobbyConstants.III: break;
                    case LobbyConstants.IV: break;
                    case LobbyConstants.V: break;
                    case LobbyConstants.V_V: break;
                    case LobbyConstants.VI: break;
                    case LobbyConstants.VII: break;
                    case LobbyConstants.VIII: break;
                    case LobbyConstants.IX: break;
                    case LobbyConstants.X:
                        //6 Inf || 1 E. Inf
                        //1 Cav || 1 H. Cav
                        //1 Art || 1 Gen || 6 Nav
                        for (int i = 0; i < 6; i++)
                            military.add(new Infantry(nationNumber));
                        military.add(new EliteInfantry(nationNumber));
                        military.add(new Cavalry(nationNumber));
                        military.add(new HeavyCavalry(nationNumber));
                        military.add(new Artillery(nationNumber));
                        military.add(new General(nationNumber));
                        military.add(new Admiral(nationNumber));
                        for (int i = 0; i < 6; i++)
                            military.add(new NavalSquadron(nationNumber));
                        //Great Britain Starting Land
                        territory.add(map.getLandRegion(GameMsg.getString("Gibraltar")));
                        territory.add(map.getLandRegion(GameMsg.getString("Hanover")));
                        map.getLandRegion(GameMsg.getString("Gibraltar")).setControllingNation(nationNumber);
                        map.getLandRegion(GameMsg.getString("Hanover")).setControllingNation(nationNumber);
                        //Diplomatic Ratings
                        diplomaticRatings[0] =  9;
                        diplomaticRatings[1] = -1;
                        diplomaticRatings[2] =  9;
                        diplomaticRatings[3] =  9;
                        diplomaticRatings[4] =  9;
                        diplomaticRatings[5] =  9;
                        diplomaticRatings[6] =  9;
                        //Commitment Rating
                        commitmentRating = 6;
                        break;
                }
                break;
            case Nation.PRUSSIA:
                switch (scenario) {
                    case LobbyConstants.I: break;
                    case LobbyConstants.II: break;
                    case LobbyConstants.III: break;
                    case LobbyConstants.IV: break;
                    case LobbyConstants.V: break;
                    case LobbyConstants.V_V: break;
                    case LobbyConstants.VI: break;
                    case LobbyConstants.VII: break;
                    case LobbyConstants.VIII: break;
                    case LobbyConstants.IX: break;
                    case LobbyConstants.X:
                        //9 Inf || 1 E. Inf
                        //2 Cav || 1 H. Cav
                        //2 Art || 1 Gen
                        for (int i = 0; i < 9; i++)
                            military.add(new Infantry(nationNumber));
                        military.add(new EliteInfantry(nationNumber));
                        for (int i = 0; i < 2; i++)
                            military.add(new Cavalry(nationNumber));
                        military.add(new HeavyCavalry(nationNumber));
                        for (int i = 0; i < 2; i++)
                            military.add(new Artillery(nationNumber));
                        military.add(new General(nationNumber));
                        //Prussian Starting Land
                        territory.add(map.getLandRegion(GameMsg.getString("Westphalia")));
                        territory.add(map.getLandRegion(GameMsg.getString("HesseBerg")));
                        territory.add(map.getLandRegion(GameMsg.getString("Saxony")));
                        map.getLandRegion(GameMsg.getString("Westphalia")).setControllingNation(nationNumber);
                        map.getLandRegion(GameMsg.getString("HesseBerg")).setControllingNation(nationNumber);
                        map.getLandRegion(GameMsg.getString("Saxony")).setControllingNation(nationNumber);
                        //Diplomatic Ratings
                        diplomaticRatings[0] =  9;
                        diplomaticRatings[1] =  9;
                        diplomaticRatings[2] = -1;
                        diplomaticRatings[3] =  9;
                        diplomaticRatings[4] =  9;
                        diplomaticRatings[5] =  9;
                        diplomaticRatings[6] =  9;
                        //Commitment Rating
                        commitmentRating = 7;
                        break;
                }
                break;
            case Nation.RUSSIA:
                switch (scenario) {
                    case LobbyConstants.I: break;
                    case LobbyConstants.II: break;
                    case LobbyConstants.III: break;
                    case LobbyConstants.IV: break;
                    case LobbyConstants.V: break;
                    case LobbyConstants.V_V: break;
                    case LobbyConstants.VI: break;
                    case LobbyConstants.VII: break;
                    case LobbyConstants.VIII: break;
                    case LobbyConstants.IX: break;
                    case LobbyConstants.X:
                        //12 Inf || 1 E. Inf
                        //2  Cav || 1 H. Cav
                        //2  Art || 2 Nav
                        for (int i = 0; i < 12; i++)
                            military.add(new Infantry(nationNumber));
                        military.add(new EliteInfantry(nationNumber));
                        for (int i = 0; i < 2; i++)
                            military.add(new Cavalry(nationNumber));
                        military.add(new HeavyCavalry(nationNumber));
                        for (int i = 0; i < 2; i++)
                            military.add(new Artillery(nationNumber));
                        for (int i = 0; i < 2; i++)
                            military.add(new NavalSquadron(nationNumber));
                        //Russian Starting Lands
                        territory.add(map.getLandRegion(GameMsg.getString("Finland")));
                        territory.add(map.getLandRegion(GameMsg.getString("GrandDuchyWarsaw")));
                        territory.add(map.getLandRegion(GameMsg.getString("Moldavia")));
                        map.getLandRegion(GameMsg.getString("Finland")).setControllingNation(nationNumber);
                        map.getLandRegion(GameMsg.getString("GrandDuchyWarsaw")).setControllingNation(nationNumber);
                        map.getLandRegion(GameMsg.getString("Moldavia")).setControllingNation(nationNumber);
                        //Diplomatic Ratings
                        diplomaticRatings[0] =  9;
                        diplomaticRatings[1] =  9;
                        diplomaticRatings[2] =  9;
                        diplomaticRatings[3] = -1;
                        diplomaticRatings[4] =  9;
                        diplomaticRatings[5] =  9;
                        diplomaticRatings[6] =  9;
                        //Commitment Rating
                        commitmentRating = 7;
                        break;
                }
                break;
            case Nation.OTTOMANS:
                switch (scenario) {
                    case LobbyConstants.I: break;
                    case LobbyConstants.II: break;
                    case LobbyConstants.III: break;
                    case LobbyConstants.IV: break;
                    case LobbyConstants.V: break;
                    case LobbyConstants.V_V: break;
                    case LobbyConstants.VI: break;
                    case LobbyConstants.VII: break;
                    case LobbyConstants.VIII: break;
                    case LobbyConstants.IX: break;
                    case LobbyConstants.X:
                        //8 Mil || 4 I. Cav
                        //1 Art || 2 Nav
                        for (int i = 0; i < 8; i++)
                            military.add(new Militia(nationNumber));
                        for (int i = 0; i < 4; i++)
                        military.add(new IrregularCavalry(nationNumber));
                        military.add(new Artillery(nationNumber));
                        for (int i = 0; i < 2; i++)
                            military.add(new NavalSquadron(nationNumber));
                        //Ottoman Starting Land
                        territory.add(map.getLandRegion(GameMsg.getString("Egypt")));
                        territory.add(map.getLandRegion(GameMsg.getString("Wallachia")));
                        territory.add(map.getLandRegion(GameMsg.getString("Bulgaria")));
                        territory.add(map.getLandRegion(GameMsg.getString("Serbia")));
                        territory.add(map.getLandRegion(GameMsg.getString("Bosnia")));
                        territory.add(map.getLandRegion(GameMsg.getString("Albania")));
                        territory.add(map.getLandRegion(GameMsg.getString("Macedonia")));
                        territory.add(map.getLandRegion(GameMsg.getString("Greece")));

                        map.getLandRegion(GameMsg.getString("Egypt")).setControllingNation(nationNumber);
                        map.getLandRegion(GameMsg.getString("Wallachia")).setControllingNation(nationNumber);
                        map.getLandRegion(GameMsg.getString("Bulgaria")).setControllingNation(nationNumber);
                        map.getLandRegion(GameMsg.getString("Serbia")).setControllingNation(nationNumber);
                        map.getLandRegion(GameMsg.getString("Bosnia")).setControllingNation(nationNumber);
                        map.getLandRegion(GameMsg.getString("Albania")).setControllingNation(nationNumber);
                        map.getLandRegion(GameMsg.getString("Macedonia")).setControllingNation(nationNumber);
                        map.getLandRegion(GameMsg.getString("Greece")).setControllingNation(nationNumber);
                        //Diplomatic Ratings
                        diplomaticRatings[0] =  9;
                        diplomaticRatings[1] =  9;
                        diplomaticRatings[2] =  9;
                        diplomaticRatings[3] =  9;
                        diplomaticRatings[4] = -1;
                        diplomaticRatings[5] =  9;
                        diplomaticRatings[6] =  9;
                        //Commitment Rating
                        commitmentRating = 8;
                        break;
                }
                break;
            case Nation.AUSTRIA_HUNGARY:
                switch (scenario) {
                    case LobbyConstants.I: break;
                    case LobbyConstants.II: break;
                    case LobbyConstants.III: break;
                    case LobbyConstants.IV: break;
                    case LobbyConstants.V: break;
                    case LobbyConstants.V_V: break;
                    case LobbyConstants.VI: break;
                    case LobbyConstants.VII: break;
                    case LobbyConstants.VIII: break;
                    case LobbyConstants.IX: break;
                    case LobbyConstants.X:
                        //12 Inf || 1 E. Inf
                        //3  Cav || 1 H. Cav
                        //2  Art || 1 Gen || 1 Nav
                        for (int i = 0; i < 12; i++)
                            military.add(new Infantry(nationNumber));
                        military.add(new EliteInfantry(nationNumber));
                        for (int i = 0; i < 3; i++)
                            military.add(new Cavalry(nationNumber));
                        military.add(new HeavyCavalry(nationNumber));
                        for (int i = 0; i < 2; i++)
                            military.add(new Artillery(nationNumber));
                        military.add(new General(nationNumber));
                        for (int i = 0; i < 1; i++)
                            military.add(new NavalSquadron(nationNumber));
                        //Austria-Hungary Starting Lands
                        territory.add(map.getLandRegion(GameMsg.getString("Milan")));
                        territory.add(map.getLandRegion(GameMsg.getString("Venice")));
                        territory.add(map.getLandRegion(GameMsg.getString("Tuscany")));
                        map.getLandRegion(GameMsg.getString("Milan")).setControllingNation(nationNumber);
                        map.getLandRegion(GameMsg.getString("Venice")).setControllingNation(nationNumber);
                        map.getLandRegion(GameMsg.getString("Tuscany")).setControllingNation(nationNumber);
                        //Diplomatic Ratings
                        diplomaticRatings[0] =  9;
                        diplomaticRatings[1] =  9;
                        diplomaticRatings[2] =  9;
                        diplomaticRatings[3] =  9;
                        diplomaticRatings[4] =  9;
                        diplomaticRatings[5] = -1;
                        diplomaticRatings[6] =  9;
                        //Commitment Rating
                        commitmentRating = 7;
                        break;
                }
                break;
            case Nation.SPAIN:
                switch (scenario) {
                    case LobbyConstants.I: break;
                    case LobbyConstants.II: break;
                    case LobbyConstants.III: break;
                    case LobbyConstants.IV: break;
                    case LobbyConstants.V: break;
                    case LobbyConstants.V_V: break;
                    case LobbyConstants.VI: break;
                    case LobbyConstants.VII: break;
                    case LobbyConstants.VIII: break;
                    case LobbyConstants.IX: break;
                    case LobbyConstants.X:
                        //7 Inf || 1 Cav
                        //1 Art || 2 Nav
                        for (int i = 0; i < 7; i++)
                            military.add(new Infantry(nationNumber));
                        military.add(new Cavalry(nationNumber));
                        military.add(new Artillery(nationNumber));
                        for (int i = 0; i < 2; i++)
                            military.add(new NavalSquadron(nationNumber));
                        //Spanish Starting
                        territory.add(map.getLandRegion(GameMsg.getString("Sicily")));
                        map.getLandRegion(GameMsg.getString("Sicily")).setControllingNation(nationNumber);
                        //Diplomatic Ratings
                        diplomaticRatings[0] =  9;
                        diplomaticRatings[1] =  9;
                        diplomaticRatings[2] =  9;
                        diplomaticRatings[3] =  9;
                        diplomaticRatings[4] =  9;
                        diplomaticRatings[5] =  9;
                        diplomaticRatings[6] = -1;
                        //Commitment Rating
                        commitmentRating = 9;
                        break;
                }
                break;
        }
    }

    public void addStartingWarNations(ArrayList<Integer> startingWarNations) {
        this.startingWarNations = startingWarNations;
        enemyNations.addAll(startingWarNations);
    }

    public ArrayList<Integer> getStartingWarNations() { return startingWarNations; }

    public ArrayList<Integer> getAllies() { return alliedNations; }
    public ArrayList<Integer> getEnemies() { return enemyNations; }
    public ArrayList<Integer> getRightOfPassages() { return passageRight; }

    public boolean isAtWar() { return enemyNations.size() > 0; }

    public int getProductionValue() {
        int value = 0;
        for (LandRegion r : territory)
            value += r.getProductionValue();
        return value;
    }

    //TODO move to CapitalRegion?? Yes
    public void annexCapital() { isCapitalAnnexed = true; isCapitalCaptured = false; }
    public void liberateCapital() { isCapitalCaptured = false; isCapitalAnnexed = false; }
    public void capitalCaptured() { isCapitalCaptured = true; hasCapitalBeenCaptured = true; isCapitalAnnexed = false; }
    public boolean hasCapitalBeenCaptured() { return hasCapitalBeenCaptured; }
    public boolean isCapitalCaptured() { return isCapitalCaptured; }
    public boolean isCapitalAnnexed() { return isCapitalAnnexed; }
    //TODO END

    public void makeNeutralToAll() {
        alliedNations.clear();
        enemyNations.clear();
        startingWarNations.clear();
        endGracePeriod();
        endReverseGrace();
        resetAnnexedHomelandThisCongress();
    }

    private boolean addAlly(int nation) {
        if (!alliedNations.contains(nation))
            return alliedNations.add(nation);
        else
            System.err.println("NationInstance:addAlly - nation already contained in allies");
        return false;
    }

    private boolean removeAlly(int nation) {
        if (alliedNations.contains(nation))
            return alliedNations.remove((Integer)nation);
        else
            System.err.println("NationInstance:removeAlly - nation is not contained in allies");
        return false;
    }

    private boolean addEnemy(int nation) {
        if (!enemyNations.contains(nation))
            return enemyNations.add(nation);
        else
            System.err.println("NationInstance:addEnemy - nation already contained in enemies");
        return false;
    }

    public boolean removeEnemy(int nation) {
        startingWarNations.remove((Integer)nation);
        if (enemyNations.contains(nation))
            return enemyNations.remove((Integer)nation);
        else
            System.err.println("NationInstance:removeEnemy - nation is not contained in enemies");
        return false;
    }

    private boolean addPassage(int nation) {
        if (!passageRight.contains(nation))
            return passageRight.add(nation);
        else
            System.err.println("NationInstance:grantPassage - nation is already contained in passageRight");
        return false;
    }

    public void removeAllEnemies() { enemyNations.clear(); startingWarNations.clear(); }

    /**
     * Declare war on a nation or get war declared on this nation.
     * @param nation - the new enemy nation.
     * @param papCost - cost to declare war, zero for non-declarer.
     * @param isDeclarer - is this the nation declaring the war.
     */
    public void declareWar(int nation, int papCost, boolean isDeclarer) {
        if ( !addEnemy(nation) )
            return;

        if (isDeclarer)
            minusPaps(papCost);

        if (!isDeclarer)
            if (hasRightOfPassage(nation))
                rescindRightOfPassage(nation);

        if (isDeclarer && isInGracePeriod())
            endGracePeriod();

        if (!isDeclarer && inReverseGraceWith(nation))
            endReverseGrace();
    }

    public void concludeArmistice(int nation, int papCost) {
        if ( !removeEnemy(nation) )
            return;
        minusPaps(papCost);
    }

    public void formAlliance(int nation) {
        if ( !addAlly(nation) )
            return;
        minusPaps(1);
    }

    public void breakAlliance(int nation, boolean isBreaker) {
        if ( !removeAlly(nation) )
            return;
        if (isBreaker)
            minusPaps(1);
    }

    public void grantRightOfPassage(int nation, boolean isVoluntary) {
        if ( !addPassage(nation) )
            return;
        if (isVoluntary)
            if ( !passageVoluntary.contains(nation) )
                passageVoluntary.add(nation);
    }

    public void rescindRightOfPassage(int nation) {
        passageRight.remove((Integer)nation);
        passageVoluntary.remove((Integer)nation);
    }

    public void removeAllRightOfPassages() {
        passageRight.clear();
        passageVoluntary.clear();
    }

    private void endGracePeriod() { gracePeriods.clear(); }
    private void endReverseGrace() { reverseGracePeriods.clear(); }

    public void addGracePeriod(int nation, int month, int year) {
        if (gracePeriods.get(nation) != null)
            gracePeriods.remove(nation);

        gracePeriods.put(nation, new int[]{month, year});
    }

    public void addReverseGrace(int nation, int month, int year) {
        if (reverseGracePeriods.get(nation) != null)
            reverseGracePeriods.remove(nation);

        reverseGracePeriods.put(nation, new int[]{month, year});
    }

    public void checkGracePeriods(int month, int year) {
        ArrayList<Integer> listToRemove = new ArrayList<Integer>();
        
        for (Integer n : gracePeriods.keySet())
            if ( gracePeriods.get(n)[0] == month && gracePeriods.get(n)[1] == year )
                listToRemove.add(n);

        for (Integer n : listToRemove)
            gracePeriods.remove(n);
        listToRemove.clear();

        for (Integer n : reverseGracePeriods.keySet())
            if ( reverseGracePeriods.get(n)[0] == month && reverseGracePeriods.get(n)[1] == year )
                listToRemove.add(n);

        for (Integer n : listToRemove) {
            reverseGracePeriods.remove(n);
            if (hasRightOfPassage(n) && !isPassageVoluntary(n))
                passageRight.remove(n);
        }
    }

    public boolean isNeutral(int nation) { return !alliedNations.contains(nation) && !enemyNations.contains(nation); }
    public boolean isAllied(int nation) { return alliedNations.contains(new Integer(nation)); }
    public boolean isEnemy(int nation) { return enemyNations.contains(new Integer(nation)); }
    public boolean hasRightOfPassage(int nation) { return passageRight.contains(nation); }
    public boolean isPassageVoluntary(int nation) { return passageVoluntary.contains(nation); }
    public boolean isInGracePeriod() { return gracePeriods.size() > 0; }
    public boolean isInReverseGrace() { return reverseGracePeriods.size() > 0; }
    public boolean inGracePeriodWith(int nation) { return gracePeriods.get(nation) != null; }
    public boolean inReverseGraceWith(int nation) { return reverseGracePeriods.get(nation) != null; }
    public int getDiplomaticRating(int nation) { return diplomaticRatings[nation - 1]; }
    public int getCommitmentRating() { return commitmentRating; }

    public int getNationNumber() { return nationNumber; }
    public int getPaps() { return PAPs; }
    public int getProductionPoints() { return productionPoints; }
    public void setPlayerNation(boolean playerNation) { isPlayerNation = playerNation; }
    public boolean isPlayerNation() { return isPlayerNation; }
    public boolean hasBeenControlled() { return hasBeenControlled; }
    public boolean isControlledNation() { return isControlledNation; }
    public void annexedHomelandThisCongress() { hasAnnexedHomelandThisCongress = true; }
    public boolean hasAnnexedHomelandThisCongress() { return hasAnnexedHomelandThisCongress; }
    public void resetAnnexedHomelandThisCongress() { hasAnnexedHomelandThisCongress = false; }
    public boolean isExtinct() { return territory.size() == 0; }
    public void setLastNationToControl(int nation) { lastNationToControl = nation; }
    public int getLastNationToControl() { return lastNationToControl; }
    public String getControllingUser() { return controllingUser; }
    public void setControlledNation(boolean isControlledNation, String userName) {
        this.isControlledNation = isControlledNation;

        if ( isControlledNation ) {
            hasBeenControlled = true;
            lastNationToControl = -1;
            if ( userName != null && !userName.equals("") )
                controllingUser = userName;
            else
                controllingUser = "UserNameNotProvided";
        } else
            controllingUser = GameMsg.getString("controllingUser.uncontrolled");
    }

    public ArrayList<MilitaryUnit> getMilitary() { return military; }
    public ArrayList<LandRegion> getTerritory() { return territory; }

    public void addMilitaryUnit(MilitaryUnit unit) { military.add(unit); }
    public void addTerritory(LandRegion landRegion) { if (!territory.contains(landRegion)) territory.add(landRegion); }

//    public void removeMilitaryUnit(MilitaryUnit unit) {
        //todo
//    }

    public void removeTerritory(LandRegion landRegion) { territory.remove(landRegion); }

    public void clearMilitary() {
        if (isExtinct())
            military.clear();
    }

    public ArrayList<LandRegion> getHomelandsOfNation(int nation) {
        switch (nation) {
            case Nation.FRANCE: return map.getFrenchHomelands();
            case Nation.GREAT_BRITAIN: return map.getBritishHomelands();
            case Nation.PRUSSIA: return map.getPrussianHomelands();
            case Nation.RUSSIA: return map.getRussianHomelands();
            case Nation.OTTOMANS: return map.getOttomanHomelands();
            case Nation.AUSTRIA_HUNGARY: return map.getAustrianHomelands();
            case Nation.SPAIN: return map.getSpanishHomelands();
        }
        GameLogger.log("NationInstance.getHomelands: 'nation' does not match one of the seven nations");
        return null;
    }

    public ArrayList<LandRegion> getHomelands() {
        switch (getNationNumber()) {
            case Nation.FRANCE: return map.getFrenchHomelands();
            case Nation.GREAT_BRITAIN: return map.getBritishHomelands();
            case Nation.PRUSSIA: return map.getPrussianHomelands();
            case Nation.RUSSIA: return map.getRussianHomelands();
            case Nation.OTTOMANS: return map.getOttomanHomelands();
            case Nation.AUSTRIA_HUNGARY: return map.getAustrianHomelands();
            case Nation.SPAIN: return map.getSpanishHomelands();
        }
        GameLogger.log("NationInstance.getHomelands: 'nation' does not match one of the seven nations");
        return null;
    }

    public ArrayList<LandRegion> getNonHomelands() {
        ArrayList<LandRegion> nonHomelands = new ArrayList<LandRegion>();
        for (LandRegion r : getTerritory())
            if (!getHomelands().contains(r))
                nonHomelands.add(r);
        return nonHomelands;
    }

    public ArrayList<Port> getHomelandPorts() {
        ArrayList<Port> ports = new ArrayList<Port>();
        ArrayList<LandRegion> homelands = getHomelands();

        for (Port p : map.getPorts())
            if( homelands.contains(p.getPortRegion()) && p.getPortRegion().isNativeOwned() )
                ports.add(p);

        //Belgium Port counts as homeland port for France, if owned by France.
        if ( nationNumber == Nation.FRANCE && territory.contains(map.getLandRegion(GameMsg.getString("Belgium"))) )
            ports.add(map.getPort(GameMsg.getString("port.belgium")));

        return ports;
    }

    public ArrayList<Port> getNonHomelandPorts() {
        ArrayList<Port> ports = new ArrayList<Port>();
        ArrayList<LandRegion> nonHomelands = getNonHomelands();

        for (Port p : map.getPorts())
            if ( nonHomelands.contains(p.getPortRegion()) )
                ports.add(p);

        return ports;
    }

    public ArrayList<MilitaryUnit> getNavy() {
        ArrayList<MilitaryUnit> navy = new ArrayList<MilitaryUnit>();

        for (MilitaryUnit u : military)
            if (u.getType() == MilitaryUnit.NAVAL_SQUADRON || u.getType() == MilitaryUnit.ADMIRAL)
                navy.add(u);

        return navy;
    }

    public ArrayList<MilitaryUnit> getArmy() {
        ArrayList<MilitaryUnit> army = new ArrayList<MilitaryUnit>();

        for (MilitaryUnit u : military) {
            if (u.getType() == MilitaryUnit.GENERAL)
                army.add(u);
            else if (u.getType() == MilitaryUnit.INFANTRY)
                army.add(u);
            else if (u.getType() == MilitaryUnit.ELITE_INFANTRY)
                army.add(u);
            else if (u.getType() == MilitaryUnit.CAVALRY)
                army.add(u);
            else if (u.getType() == MilitaryUnit.HEAVY_CAVALRY)
                army.add(u);
            else if (u.getType() == MilitaryUnit.IRREGULAR_CAVALRY)
                army.add(u);
            else if (u.getType() == MilitaryUnit.ARTILLERY)
                army.add(u);
            else if (u.getType() == MilitaryUnit.HORSE_ARTILLERY)
                army.add(u);
            else if (u.getType() == MilitaryUnit.MILITIA)
                army.add(u);
        }
        return army;
    }

    public boolean containsGeneral() {
        for (MilitaryUnit u : military)
            if (u.getType() == MilitaryUnit.GENERAL)
                return true;
        return false;
    }

    public boolean containsArtillery() {
        for (MilitaryUnit u : military)
            if (u.getType() == MilitaryUnit.ARTILLERY)
                return true;
        return false;
    }

    /**
     * All homeland ports that have not built a ship in last three (3) production rounds.
     * No units may be placed in regions occupied by enemy troops or that is in uprising.
     * Regions must be under native control.
     * THIS IS NOT CHECKED If first round, can place in all regions owned by nations (S.X only), otherwise only homelands.  THIS IS NOT CHECKED
     * @return an ArrayList of the regions the userNation can place units in.
     */
    public ArrayList<Region> getPlaceableLandRegions() {
        ArrayList<Region> placeableRegions = new ArrayList<Region>();
        //Land Regions
        for (LandRegion r : getHomelands())
            if ( r.getControllingNation() == nationNumber && !r.isUprising() && !r.isEnemyOccupied() )
                placeableRegions.add(r);
        return placeableRegions;
    }

    //Does not check if port has built Naval Squadron recently
    public ArrayList<Region> getPlaceablePortRegions() {
        ArrayList<Region> placeablePorts = new ArrayList<Region>();
        //Ports
        for (Region p : getHomelandPorts()) {
            LandRegion region = ((Port)p).getPortRegion();
            if ( region.getControllingNation() == nationNumber && !region.isUprising() && !region.isEnemyOccupied())
                placeablePorts.add(p);
        }
        return placeablePorts;
    }

    public boolean canPurchaseUnit(int unitType, ArrayList<Integer> plannedPurchases, int productionYear) {
        //Check if unit can be placed
        //Naval Squad must have a port available
        if (unitType == MilitaryUnit.NAVAL_SQUADRON && getPlaceablePortRegions().size() == 0) {
            boolean canPlaceNavalSquad = false;

            for (Region p : getPlaceablePortRegions())
                if ( ((Port)p).canPlaceNavalSquadron() ) {
                    canPlaceNavalSquad = true;
                    break;
                }

            if (!canPlaceNavalSquad)
                return false;
        }

        //Admiral must have an available port occupied by a friendly Naval Squadron.
        if (unitType == MilitaryUnit.ADMIRAL) {
            boolean canPlaceAdmiral = false;

            for (Region p : getPlaceablePortRegions())
                for (MilitaryUnit ns : p.getOccupyingUnits() )
                    if ( ns.getOwningNation() == nationNumber && ((NavalSquadron)ns).getAdmiral() == null ) {
                        canPlaceAdmiral = true;
                        break;
                    }

            if (!canPlaceAdmiral)
                return false;
        }

        //If unitType is not Admiral, Naval Squadron or PAP, then must have Land Region available
        if (unitType != MilitaryUnit.ADMIRAL && unitType != MilitaryUnit.NAVAL_SQUADRON && unitType != MilitaryUnit.PAP
                && getPlaceableLandRegions().size() == 0)
            return false;

        //Check money situation
        int availablePP = productionPoints;

        //Subtract cost for currently planned purchases
        for (int type : plannedPurchases)
            availablePP -= MilitaryUnit.getCost(type, nationNumber);
        //Subtract cost of next unit to purchase
        availablePP -= MilitaryUnit.getCost(unitType, nationNumber);
        //If less than zero than not enough pp remains
        if (availablePP < 0)
            return false;

        //Check if legal purchase
        switch (unitType) {
            case MilitaryUnit.GENERAL:
                //Spain can only have one General, everyone else can have unlimited.
                return !(nationNumber == Nation.SPAIN && (containsGeneral() || plannedPurchases.contains(MilitaryUnit.GENERAL)));

            case MilitaryUnit.INFANTRY:
                //Ottoman's can not buy Infantry.
                return nationNumber != Nation.OTTOMANS;

            case MilitaryUnit.ELITE_INFANTRY:
                //Spain and Ottoman's can not purchase Elite Infantry, only one can be purchased per round for every other nation.
                return nationNumber != Nation.SPAIN && nationNumber != Nation.OTTOMANS && !plannedPurchases.contains(MilitaryUnit.ELITE_INFANTRY);

            case MilitaryUnit.CAVALRY:
                //Ottoman can not buy Cavalry.
                return nationNumber != Nation.OTTOMANS;

            case MilitaryUnit.HEAVY_CAVALRY:
                //Spain and Ottoman's can not purchase Heavy Cavalry, only one can be purchased per round for every other nation.
                return nationNumber != Nation.SPAIN && nationNumber != Nation.OTTOMANS && !plannedPurchases.contains(MilitaryUnit.HEAVY_CAVALRY);

            case MilitaryUnit.IRREGULAR_CAVALRY:
                //Only Russia and Ottoman can purchase Irregular Cavalry.
                return nationNumber == Nation.RUSSIA || nationNumber == Nation.OTTOMANS;

            case MilitaryUnit.ARTILLERY:
                //Ottomans can only have one artillery, everyone else can have unlimited.
                return !( nationNumber == Nation.OTTOMANS && (containsArtillery() || plannedPurchases.contains(MilitaryUnit.ARTILLERY)) );

            case MilitaryUnit.HORSE_ARTILLERY:
                //Ottomans can not buy horse artillery, everyone else can.
                return nationNumber != Nation.OTTOMANS;

            case MilitaryUnit.MILITIA:
                //Only Ottomans at any time and Russians after 1812
                return nationNumber == Nation.OTTOMANS || (nationNumber == Nation.PRUSSIA && productionYear > 1812);

            case MilitaryUnit.ADMIRAL:
                //Only one naval unit can be purchased per round
                return !plannedPurchases.contains(MilitaryUnit.ADMIRAL) && !plannedPurchases.contains(MilitaryUnit.NAVAL_SQUADRON);

            case MilitaryUnit.NAVAL_SQUADRON:
                //Only one naval unit can be purchased per round
                return !plannedPurchases.contains(MilitaryUnit.ADMIRAL) && !plannedPurchases.contains(MilitaryUnit.NAVAL_SQUADRON);

            case MilitaryUnit.NATIONAL_HERO:
                if (scenario == LobbyConstants.X)
                    return false; //ScenarioX no heros
                else  // based on scenario and other stuff todo
                    return false;

            case MilitaryUnit.PAP:
                return true;

            default: return false;
        }
    }

    public void setPaps(int paps) { this.PAPs = paps; }
    public void addPap() { addPaps(1); }
    public void addPaps(int paps) { this.PAPs += paps; }
    public void minusPaps(int paps) { this.PAPs -= paps; }
    public void setProductionPoints(int points) { productionPoints = points; }
    public void addProductionPoints(int points) { productionPoints += points; }
    public void minusProductionPoints(int points) { productionPoints -= points; }

    private int nationNumber;
    private String controllingUser;
    private int PAPs;
    private MapInstance map;
    private int scenario;
    private int productionPoints;
    private boolean isPlayerNation;
    private boolean hasBeenControlled;
    private boolean isControlledNation;
    private boolean hasAnnexedHomelandThisCongress;
    private int lastNationToControl;
    private ArrayList<MilitaryUnit> military;
    private ArrayList<LandRegion> territory;
    private ArrayList<Integer> alliedNations;
    private ArrayList<Integer> enemyNations;
    private ArrayList<Integer> passageRight;
    private ArrayList<Integer> passageVoluntary;
    private ArrayList<Integer> startingWarNations;
                  //Nation  //  Month // Year
    private HashMap<Integer, int[]> gracePeriods;
    private HashMap<Integer, int[]> reverseGracePeriods;
    private int[] diplomaticRatings;
    private int commitmentRating;
    private boolean isCapitalCaptured;
    private boolean hasCapitalBeenCaptured;
    private boolean isCapitalAnnexed;
}