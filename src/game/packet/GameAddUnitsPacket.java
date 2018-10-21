package game.packet;

import game.controller.Unit.MilitaryUnit;
import game.controller.Unit.NavalSquadron;

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
public class GameAddUnitsPacket extends GamePacket {

    public GameAddUnitsPacket(byte[] packet) {
        this.packet = packet;
    }

    public GameAddUnitsPacket(byte[] gameId, int nation, ArrayList<Integer> units) {
        packet = new byte[256];
        packet[0] = GameOps.ADD_UNITS.valueOf();
        packet[1] = gameId[0];
        packet[2] = gameId[1];
        packet[onIndex] = (byte)nation;

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
        packet[heroIndex] = (byte)nationalHero;
        packet[papIndex] = (byte)paps;
    }

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
    public int getNumNationalHero() { return packet[heroIndex] & 0xff; }
    public int getNumPaps() { return packet[papIndex] & 0xff; }

    private void processUnits(ArrayList<Integer> units) {
        for (Integer u : units) {
            switch (u) {
                case MilitaryUnit.GENERAL: generals++; break;
                case MilitaryUnit.ADMIRAL: admirals++; break;
                case MilitaryUnit.INFANTRY: infantry++; break;
                case MilitaryUnit.ELITE_INFANTRY: eInfantry++; break;
                case MilitaryUnit.CAVALRY: cavalry++; break;
                case MilitaryUnit.HEAVY_CAVALRY: hCavalry++; break;
                case MilitaryUnit.IRREGULAR_CAVALRY: iCavalry++; break;
                case MilitaryUnit.ARTILLERY: artillery++; break;
                case MilitaryUnit.HORSE_ARTILLERY: hArtillery++; break;
                case MilitaryUnit.MILITIA: militia++; break;
                case MilitaryUnit.NAVAL_SQUADRON: navalSquads++; break;
                case MilitaryUnit.NATIONAL_HERO: nationalHero++; break;
                case MilitaryUnit.PAP: paps++; break;
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
    private int nationalHero = 0;
    private int paps = 0;

    private static int onIndex = 3;
    private static int genIndex = 4;
    private static int admIndex = 5;
    private static int infIndex = 6;
    private static int eInfIndex = 7;
    private static int cavIndex = 8;
    private static int hCavIndex = 9;
    private static int iCavIndex = 10;
    private static int artIndex = 11;
    private static int hArtIndex = 12;
    private static int milIndex = 13;
    private static int navIndex = 14;
    private static int heroIndex = 15;
    private static int papIndex = 16;
}