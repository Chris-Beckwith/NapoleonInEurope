package game.packet;

import game.controller.Unit.*;

import java.util.ArrayList;

/**
 * GamePlaceUnitsPacket.java  Date Created: Jun 27, 2013
 *
 * Purpose: To delivery a packet containing units to be added to the Map.
 *
 * Description:  This packet will contain a list of units to be added to a specific region.
 *
 * ** MAX OF ONE TYPE OF UNIT 255 **
 *  __ ____  __   __   __  __  __  __  __  __  __  __  __  __  __  __
 *  1   2    1    1    1   1   1   1   1   1   1   1   1   1   1   1
 * GameOp  rIndex   nation    adm    eInf     hCav    art     mil
 *    GameId    rType     gen     inf     cav     iCav   hArt    nav
 *
 * @author Chrisb
 */
public class GamePlaceUnitsPacket extends GamePacket {

    public GamePlaceUnitsPacket(byte[] packet) {
        this.packet = packet;
    }

    public GamePlaceUnitsPacket(byte[] gameId, int regionIndex, int regionType, ArrayList<MilitaryUnit> units) {
        packet = new byte[256];
        packet[0] = GameOps.PLACE_UNITS.valueOf();
        packet[1] = gameId[0];
        packet[2] = gameId[1];
        packet[riIndex] = (byte)regionIndex;
        packet[rtIndex] = (byte)regionType;
        packet[onIndex] = (byte)units.get(0).getOwningNation();

        processUnits(units);
        packet[genIndex] = (byte)generals;
        packet[admIndex] = (byte)admirals;
        packet[infIndex] = (byte)infantry;
        packet[eInfIndex] = (byte)eInfantry;
        packet[cavIndex] = (byte)cavalry;
        packet[hCavIndex] = (byte)hCavalry;
        packet[iCavIndex] = (byte)iCavalry;
        packet[artIndex] = (byte)artillery;
        packet[hArtIndex] = (byte)hArtillery;
        packet[milIndex] = (byte)militia;
        packet[navIndex] = (byte)navalSquads;
    }

    public int getRegionIndex() { return packet[riIndex] & 0xff; }
    public int getRegionType() { return packet[rtIndex] & 0xff; }
    public int getOwningNation() { return packet[onIndex] & 0xff; }

    public int getNumGenerals() { return packet[genIndex] & 0xff; }
    public int getNumAdmirals() { return packet[admIndex] & 0xff; }
    public int getNumInfantry() { return packet[infIndex] & 0xff; }
    public int getNumEliteInf() { return packet[eInfIndex] & 0xff; }
    public int getNumCavalry() { return packet[cavIndex] & 0xff; }
    public int getNumHeavyCav() { return packet[hCavIndex] & 0xff; }
    public int getNumIrregularCav() { return packet[iCavIndex] & 0xff; }
    public int getNumArtillery() { return packet[artIndex] & 0xff; }
    public int getNumHorseArt() { return packet[hArtIndex] & 0xff; }
    public int getNumMilitia() { return packet[milIndex] & 0xff; }
    public int getNumNavalSquads() { return packet[navIndex] & 0xff; }

    private void processUnits(ArrayList<MilitaryUnit> units) {
        for (MilitaryUnit u : units) {
            if ( u.getType() == MilitaryUnit.GENERAL )
                generals++;
            else if ( u.getType() == MilitaryUnit.ADMIRAL )
                admirals++;
            else if ( u.getType() == MilitaryUnit.INFANTRY )
                infantry++;
            else if ( u.getType() == MilitaryUnit.ELITE_INFANTRY )
                eInfantry++;
            else if ( u.getType() == MilitaryUnit.CAVALRY )
                cavalry++;
            else if ( u.getType() == MilitaryUnit.HEAVY_CAVALRY )
                hCavalry++;
            else if ( u.getType() == MilitaryUnit.IRREGULAR_CAVALRY )
                iCavalry++;
            else if ( u.getType() == MilitaryUnit.ARTILLERY )
                artillery++;
            else if ( u.getType() == MilitaryUnit.HORSE_ARTILLERY )
                hArtillery++;
            else if ( u.getType() == MilitaryUnit.MILITIA )
                militia++;
            else if ( u.getType() == MilitaryUnit.NAVAL_SQUADRON ) {
                navalSquads++;
                if (((NavalSquadron) u).getAdmiral() != null)
                    admirals++;
            }
        }
    }

    private int generals = 0;
    private int admirals = 0;
    private int infantry = 0;
    private int eInfantry = 0;
    private int cavalry = 0;
    private int hCavalry = 0;
    private int iCavalry = 0;
    private int artillery = 0;
    private int hArtillery = 0;
    private int militia = 0;
    private int navalSquads = 0;

    private static int riIndex = 3;
    private static int rtIndex = 4;
    private static int onIndex = 5;
    private static int genIndex = 6;
    private static int admIndex = 7;
    private static int infIndex = 8;
    private static int eInfIndex = 9;
    private static int cavIndex = 10;
    private static int hCavIndex = 11;
    private static int iCavIndex = 12;
    private static int artIndex = 13;
    private static int hArtIndex = 14;
    private static int milIndex = 15;
    private static int navIndex = 16;
}