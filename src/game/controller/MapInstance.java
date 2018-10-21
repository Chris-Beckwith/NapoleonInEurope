package game.controller;

import game.controller.Region.*;
import game.util.GameMsg;
import game.util.GameLogger;
import lobby.controller.Nation;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * MapInstance.java  Date Created: Nov 8, 2012
 *
 * Purpose: To keep track of the details of the map.
 *
 * Description: A map object will be created for each game.  It will contain
 * the array of regions on the map, the borders of those regions, and which
 * regions are controlled by which nations.
 *
 * @author Chrisb
 */
public class MapInstance {

    /**
     * How to move a unit
     *
     * Nation selects a Region.  If there are units owned by Nation, allow Nation to move them.
     * Selecting Flag Bearer will move entire army(MilitaryUnit[]) in the Region.
     */

    /**
     * MapInstance
     *
     * Map < R,B > - R = Region (vertex); B = Border (edge)
     *
     * Shortest Path Algorithm
     * Do I need to double up the borders (edges)??  Is my algorithm presuming my borders are one way?? 
     */


    public MapInstance() {
        initRegions();
        initBorders();
    }

    /**
     * Create Regions
     */
    private void initRegions() {
        landRegions = new LandRegion[95];

        //Create Capitals
        landRegions[0] = new Paris();
        landRegions[1] = new London();
        landRegions[2] = new Berlin();
        landRegions[3] = new Petersburg();
        landRegions[4] = new Moscow();
        landRegions[5] = new Constantinople();
        landRegions[6] = new Vienna();
        landRegions[7] = new Madrid();

        //Create Homelands - regionName, isHomeland, controllingNation, onCoast, hasPort, isAfrica

        //French homelands
        landRegions[8]  = new LandRegion(GameMsg.getString("Burgundy"), Nation.FRANCE, false, false, false);
        landRegions[9]  = new LandRegion(GameMsg.getString("CentralFrance"), Nation.FRANCE, false, false, false);
        landRegions[10] = new LandRegion(GameMsg.getString("Provence"), Nation.FRANCE, true, true, false);
        landRegions[11] = new LandRegion(GameMsg.getString("Gascony"), Nation.FRANCE, true, false, false);
        landRegions[12] = new LandRegion(GameMsg.getString("Vendee"), Nation.FRANCE, true, true, false);
        landRegions[13] = new LandRegion(GameMsg.getString("Brittany"), Nation.FRANCE, true, false, false);
        landRegions[14] = new LandRegion(GameMsg.getString("Normandy"), Nation.FRANCE, true, false, false);
        landRegions[15] = new LandRegion(GameMsg.getString("Picardy"), Nation.FRANCE, true, true, false);
        landRegions[16] = new LandRegion(GameMsg.getString("Lorraine"), Nation.FRANCE, false, false, false);

        //British homelands
        landRegions[17] = new LandRegion(GameMsg.getString("Cornwall"), Nation.GREAT_BRITAIN, true, true, false);
        landRegions[18] = new LandRegion(GameMsg.getString("Wales"), Nation.GREAT_BRITAIN, true, false, false);
        landRegions[19] = new LandRegion(GameMsg.getString("Midlands"), Nation.GREAT_BRITAIN, true, true, false);
        landRegions[20] = new LandRegion(GameMsg.getString("Scotland"), Nation.GREAT_BRITAIN, true, false, false);
        landRegions[21] = new LandRegion(GameMsg.getString("Highlands"), Nation.GREAT_BRITAIN, true, false, false);
        landRegions[22] = new LandRegion(GameMsg.getString("Ulster"), Nation.GREAT_BRITAIN, true, false, false);
        landRegions[23] = new LandRegion(GameMsg.getString("Ireland"), Nation.GREAT_BRITAIN, true, true, false);

        //Prussian homelands
        landRegions[24] = new LandRegion(GameMsg.getString("Silesia"), Nation.PRUSSIA, false, false, false);
        landRegions[25] = new LandRegion(GameMsg.getString("Pomerania"), Nation.PRUSSIA, true, false, false);
        landRegions[26] = new LandRegion(GameMsg.getString("EastPrussia"), Nation.PRUSSIA, true, true, false);

        //Russian homelands
        landRegions[27] = new LandRegion(GameMsg.getString("Estonia"), Nation.RUSSIA, true, false, false);
        landRegions[28] = new LandRegion(GameMsg.getString("Courland"), Nation.RUSSIA, true, false, false);
        landRegions[29] = new LandRegion(GameMsg.getString("Lithuania"), Nation.RUSSIA, false, false, false);
        landRegions[30] = new LandRegion(GameMsg.getString("Volhynia"), Nation.RUSSIA, false, false, false);
        landRegions[31] = new LandRegion(GameMsg.getString("Ukraine"), Nation.RUSSIA, true, false, false);
        landRegions[32] = new LandRegion(GameMsg.getString("Crimea"), Nation.RUSSIA, true, true, false);
        landRegions[33] = new LandRegion(GameMsg.getString("DonBasin"), Nation.RUSSIA, true, false, false);
        landRegions[34] = new LandRegion(GameMsg.getString("Caucasus"), Nation.RUSSIA, true, false, false);
        landRegions[35] = new LandRegion(GameMsg.getString("NorthernRussia"), Nation.RUSSIA, false, false, false);
        landRegions[36] = new LandRegion(GameMsg.getString("Novgorod"), Nation.RUSSIA, false, false, false);
        landRegions[37] = new LandRegion(GameMsg.getString("MinskSmolensk"), Nation.RUSSIA, false, false, false);

        //Ottoman homelands
        landRegions[38] = new LandRegion(GameMsg.getString("Anatolia"), Nation.OTTOMANS, true, true, false);
        landRegions[39] = new LandRegion(GameMsg.getString("Ankara"), Nation.OTTOMANS, true, false, false);
        landRegions[40] = new LandRegion(GameMsg.getString("SouthernTurkey"), Nation.OTTOMANS, true, false, false);
        landRegions[41] = new LandRegion(GameMsg.getString("Armenia"), Nation.OTTOMANS, true, false, false);
        landRegions[42] = new LandRegion(GameMsg.getString("Syria"), Nation.OTTOMANS, true, false, false);
        landRegions[43] = new LandRegion(GameMsg.getString("Arabia"), Nation.OTTOMANS, false, false, false);

        //Austrian homelands
        landRegions[44] = new LandRegion(GameMsg.getString("Tyrol"), Nation.AUSTRIA_HUNGARY, false, false, false);
        landRegions[45] = new LandRegion(GameMsg.getString("Illyria"), Nation.AUSTRIA_HUNGARY, true, true, false);
        landRegions[46] = new LandRegion(GameMsg.getString("Hungary"), Nation.AUSTRIA_HUNGARY, false, false, false);
        landRegions[47] = new LandRegion(GameMsg.getString("Transylvania"), Nation.AUSTRIA_HUNGARY, false, false, false);
        landRegions[48] = new LandRegion(GameMsg.getString("Galicia"), Nation.AUSTRIA_HUNGARY, false, false, false);
        landRegions[49] = new LandRegion(GameMsg.getString("Bohemia"), Nation.AUSTRIA_HUNGARY, false, false, false);

        //Spanish homelands
        landRegions[50] = new LandRegion(GameMsg.getString("Asturias"), Nation.SPAIN, true, true, false);
        landRegions[51] = new LandRegion(GameMsg.getString("Navarre"), Nation.SPAIN, true, false, false);
        landRegions[52] = new LandRegion(GameMsg.getString("Catalonia"), Nation.SPAIN, true, false, false);
        landRegions[53] = new LandRegion(GameMsg.getString("Andalusia"), Nation.SPAIN, true, true, false);
        landRegions[54] = new LandRegion(GameMsg.getString("WesternSpain"), Nation.SPAIN, true, true, false);

        //Create Non-Homelands - regionName, isHomeland, onCoast, hasPort, isAfrica

        //Spanish Misc
        landRegions[55] = new LandRegion(GameMsg.getString("Portugal"), true, true, false);
        landRegions[56] = new LandRegion(GameMsg.getString("Gibraltar"), true, true, false);

        //Central Europe
        landRegions[57] = new LandRegion(GameMsg.getString("Switzerland"), false, false, false);
        landRegions[58] = new LandRegion(GameMsg.getString("BadenWurtt"), false, false, false);
        landRegions[59] = new LandRegion(GameMsg.getString("Bavaria"), false, false, false);
        landRegions[60] = new LandRegion(GameMsg.getString("Belgium"), true, true, false);
        landRegions[61] = new LandRegion(GameMsg.getString("HesseBerg"), false, false, false);
        landRegions[62] = new LandRegion(GameMsg.getString("Thuringia"), false, false, false);
        landRegions[63] = new LandRegion(GameMsg.getString("Saxony"), false, false, false);
        landRegions[64] = new LandRegion(GameMsg.getString("Westphalia"), false, false, false);
        landRegions[65] = new LandRegion(GameMsg.getString("Holland"), true, true, false);

        //Northern Europe
        landRegions[66] = new LandRegion(GameMsg.getString("Hanover"), true, false, false);
        landRegions[67] = new LandRegion(GameMsg.getString("Mecklenburg"), true, false, false);
        landRegions[68] = new LandRegion(GameMsg.getString("Denmark"), true, true, false);
        landRegions[69] = new LandRegion(GameMsg.getString("Norway"), true, false, false);
        landRegions[70] = new LandRegion(GameMsg.getString("Sweden"), true, true, false);
        landRegions[71] = new LandRegion(GameMsg.getString("Finland"), true, false, false);

        //Grand Duchy of Warsaw
        landRegions[72] = new LandRegion(GameMsg.getString("GrandDuchyWarsaw"), false, false, false);

        //Eastern Europe
        landRegions[73] = new LandRegion(GameMsg.getString("Moldavia"), true, false, false);
        landRegions[74] = new LandRegion(GameMsg.getString("Wallachia"), true, false, false);
        landRegions[75] = new LandRegion(GameMsg.getString("Bulgaria"), true, false, false);
        landRegions[76] = new LandRegion(GameMsg.getString("Serbia"), false, false, false);
        landRegions[77] = new LandRegion(GameMsg.getString("Bosnia"), false, false, false);
        landRegions[78] = new LandRegion(GameMsg.getString("Albania"), true, false, false);
        landRegions[79] = new LandRegion(GameMsg.getString("Macedonia"), true, false, false);
        landRegions[80] = new LandRegion(GameMsg.getString("Greece"), true, true, false);

        //Southern Europe - Italy
        landRegions[81] = new LandRegion(GameMsg.getString("Sicily"), true, true, false);
        landRegions[82] = new LandRegion(GameMsg.getString("Naples"), true, true, false);
        landRegions[83] = new LandRegion(GameMsg.getString("PapalStates"), true, false, false);
        landRegions[84] = new LandRegion(GameMsg.getString("Tuscany"), true, true, false);
        landRegions[85] = new LandRegion(GameMsg.getString("Venice"), true, false, false);
        landRegions[86] = new LandRegion(GameMsg.getString("Milan"), true, false, false);
        landRegions[87] = new LandRegion(GameMsg.getString("Piedmont"), true, false, false);

        //Africa
        landRegions[88] = new LandRegion(GameMsg.getString("Morocco"), true, false, false);
        landRegions[89] = new LandRegion(GameMsg.getString("Oran"), true, false, false);
        landRegions[90] = new LandRegion(GameMsg.getString("Algeria"), true, true, false);
        landRegions[91] = new LandRegion(GameMsg.getString("Tunisia"), true, true, false);
        landRegions[92] = new LandRegion(GameMsg.getString("Tripolitania"), true, false, false);
        landRegions[93] = new LandRegion(GameMsg.getString("Cyrenaica"), true, false, false);
        landRegions[94] = new LandRegion(GameMsg.getString("Egypt"), true, false, false);

        //Create Sea Regions
//        seaRegions = new SeaRegion[Integer.getInteger(GameMsg.getString("numOfSeaRegions"))];
        seaRegions = new SeaRegion[16];

        seaRegions[0] = new SeaRegion(GameMsg.getString("GulfOfBothnia"));
        seaRegions[1] = new SeaRegion(GameMsg.getString("BalticSea"));
        seaRegions[2] = new SeaRegion(GameMsg.getString("NorthSea"));
        seaRegions[3] = new SeaRegion(GameMsg.getString("NorthAtlantic"));
        seaRegions[4] = new SeaRegion(GameMsg.getString("IrishSea"));
        seaRegions[5] = new SeaRegion(GameMsg.getString("EnglishChannel"));
        seaRegions[6] = new SeaRegion(GameMsg.getString("BayOfBiscay"));
        seaRegions[7] = new SeaRegion(GameMsg.getString("MidAtlantic"));
        seaRegions[8] = new SeaRegion(GameMsg.getString("BarbaryCoast"));
        seaRegions[9] = new SeaRegion(GameMsg.getString("GulfOfMarseilles"));
        seaRegions[10] = new SeaRegion(GameMsg.getString("TyrrhenianSea"));
        seaRegions[11] = new SeaRegion(GameMsg.getString("IonianSea"));
        seaRegions[12] = new SeaRegion(GameMsg.getString("AdriaticSea"));
        seaRegions[13] = new SeaRegion(GameMsg.getString("EasternMediterranean"));
        seaRegions[14] = new SeaRegion(GameMsg.getString("AegeanSea"));
        seaRegions[15] = new SeaRegion(GameMsg.getString("BlackSea"));

        //Create Ports
//        ports = new Ports[Integer.getInteger(GameMsg.getString("numOfPorts"))];
        ports = new Port[35];

        ports[0] = new Port(GameMsg.getString("port.petersburg"), landRegions[3], new SeaRegion[]{seaRegions[0]});
        ports[1] = new Port(GameMsg.getString("port.swedenNorth"), landRegions[70], new SeaRegion[]{seaRegions[0]}, Port.NORTH);
        ports[2] = new Port(GameMsg.getString("port.swedenSouth"), landRegions[70], new SeaRegion[]{seaRegions[1]}, Port.SOUTH);
        ports[3] = new Port(GameMsg.getString("port.pomerania"), landRegions[25], new SeaRegion[]{seaRegions[1]});
        ports[4] = new Port(GameMsg.getString("port.denmark"), landRegions[68], new SeaRegion[]{seaRegions[1]});
        ports[5] = new Port(GameMsg.getString("port.holland"), landRegions[65], new SeaRegion[]{seaRegions[2]});
        ports[6] = new Port(GameMsg.getString("port.belgium"), landRegions[60], new SeaRegion[]{seaRegions[2]});
        ports[7] = new Port(GameMsg.getString("port.londonEast"), landRegions[1], new SeaRegion[]{seaRegions[2]}, Port.EAST);
        ports[8] = new Port(GameMsg.getString("port.londonWest"), landRegions[1], new SeaRegion[]{seaRegions[3]}, Port.WEST);
        ports[9] = new Port(GameMsg.getString("port.londonSouth"), landRegions[1], new SeaRegion[]{seaRegions[5]}, Port.SOUTH);
        ports[10] = new Port(GameMsg.getString("port.cornwall"), landRegions[17], new SeaRegion[]{seaRegions[5]});
        ports[11] = new Port(GameMsg.getString("port.midlands"), landRegions[19], new SeaRegion[]{seaRegions[4]});
        ports[12] = new Port(GameMsg.getString("port.ireland"), landRegions[23], new SeaRegion[]{seaRegions[3]});
        ports[13] = new Port(GameMsg.getString("port.picardy"), landRegions[15], new SeaRegion[]{seaRegions[5]});
        ports[14] = new Port(GameMsg.getString("port.brittany"), landRegions[13], new SeaRegion[]{seaRegions[6]});
        ports[15] = new Port(GameMsg.getString("port.vendee"), landRegions[12], new SeaRegion[]{seaRegions[6]});
        ports[16] = new Port(GameMsg.getString("port.asturias"), landRegions[50], new SeaRegion[]{seaRegions[6]});
        ports[17] = new Port(GameMsg.getString("port.portugal"), landRegions[55], new SeaRegion[]{seaRegions[7]});
        ports[18] = new Port(GameMsg.getString("port.westSpain"), landRegions[54], new SeaRegion[]{seaRegions[7]});
        ports[19] = new Port(GameMsg.getString("port.gibraltar"), landRegions[56], new SeaRegion[]{seaRegions[7], seaRegions[8]});
        ports[20] = new Port(GameMsg.getString("port.andalusia"), landRegions[53], new SeaRegion[]{seaRegions[8]});
        ports[21] = new Port(GameMsg.getString("port.provence"), landRegions[10], new SeaRegion[]{seaRegions[9]});
        ports[22] = new Port(GameMsg.getString("port.milan"), landRegions[86], new SeaRegion[]{seaRegions[10]});
        ports[23] = new Port(GameMsg.getString("port.naples"), landRegions[82], new SeaRegion[]{seaRegions[10]});
        ports[24] = new Port(GameMsg.getString("port.sicily"), landRegions[81], new SeaRegion[]{seaRegions[10], seaRegions[11]});
        ports[25] = new Port(GameMsg.getString("port.venice"), landRegions[85], new SeaRegion[]{seaRegions[12]});
        ports[26] = new Port(GameMsg.getString("port.illyria"), landRegions[45], new SeaRegion[]{seaRegions[12]});
        ports[27] = new Port(GameMsg.getString("port.macedonia"), landRegions[79], new SeaRegion[]{seaRegions[11]});
        ports[28] = new Port(GameMsg.getString("port.greece"), landRegions[80], new SeaRegion[]{seaRegions[14]});
        ports[29] = new Port(GameMsg.getString("port.constantinople"), landRegions[5], new SeaRegion[]{seaRegions[14], seaRegions[15]});
        ports[30] = new Port(GameMsg.getString("port.crimea"), landRegions[32], new SeaRegion[]{seaRegions[15]});
        ports[31] = new Port(GameMsg.getString("port.anatoliaNorth"), landRegions[38], new SeaRegion[]{seaRegions[14]}, Port.NORTH);
        ports[32] = new Port(GameMsg.getString("port.anatoliaSouth"), landRegions[38], new SeaRegion[]{seaRegions[13], seaRegions[14]}, Port.SOUTH);
        ports[33] = new Port(GameMsg.getString("port.egypt"), landRegions[94], new SeaRegion[]{seaRegions[13]});
        ports[34] = new Port(GameMsg.getString("port.algiers"), landRegions[90], new SeaRegion[]{seaRegions[8]});
    }

    private void initBorders() {
        initLandBorders();
        initStraitBorders();
        initSeaBorders();
        initLandSeaBorders();
    }

    private void initLandBorders() {
        landBorders = new Border[185];

        landBorders[0] = new Border(landRegions[69], landRegions[70]);   //Norway -- Sweden
        landBorders[1] = new Border(landRegions[70], landRegions[71]);   //Sweden -- Finland
        //Russia
        landBorders[2] = new Border(landRegions[71], landRegions[3]);    //Finland -- St. Petersburg
        landBorders[3] = new Border(landRegions[3], landRegions[27]);    //St. Petersburg -- Estonia
        landBorders[4] = new Border(landRegions[3], landRegions[36]);    //St. Petersburg -- Novgorod
        landBorders[5] = new Border(landRegions[27], landRegions[28]);   //Estonia -- Courland
        landBorders[6] = new Border(landRegions[27], landRegions[36]);   //Estonia -- Novgorod
        landBorders[7] = new Border(landRegions[28], landRegions[29]);   //Courland -- Lithuania
        landBorders[8] = new Border(landRegions[28], landRegions[36]);   //Courland -- Novgorod
        landBorders[9] = new Border(landRegions[28], landRegions[37]);   //Courland -- Minsk-Smolensk
        landBorders[10] = new Border(landRegions[28], landRegions[26]);  //Courland -- East Prussia
        landBorders[11] = new Border(landRegions[28], landRegions[72]);  //Courland -- Warsaw
        landBorders[12] = new Border(landRegions[29], landRegions[37]);  //Lithuania -- Minsk-Smolensk
        landBorders[13] = new Border(landRegions[29], landRegions[30]);  //Lithuania -- Volhynia
        landBorders[14] = new Border(landRegions[29], landRegions[72]);  //Lithuania -- Warsaw
        landBorders[15] = new Border(landRegions[30], landRegions[31]);  //Valhynia -- Ukraine
        landBorders[16] = new Border(landRegions[30], landRegions[37]);  //Valhynia -- Minsk-Smolensk
        landBorders[17] = new Border(landRegions[30], landRegions[72]);  //Valhynia -- Warsaw
        landBorders[18] = new Border(landRegions[30], landRegions[48]);  //Valhynia -- Galicia
        landBorders[19] = new Border(landRegions[30], landRegions[73]);  //Valhynis -- Moldavia
        landBorders[20] = new Border(landRegions[31], landRegions[32]);  //Ukraine -- Crimea
        landBorders[21] = new Border(landRegions[31], landRegions[33]);  //Ukraine -- Don Basin
        landBorders[22] = new Border(landRegions[31], landRegions[4]);   //Ukraine -- Moscow
        landBorders[23] = new Border(landRegions[31], landRegions[37]);  //Ukraine -- Minsk-Smolensk
        landBorders[24] = new Border(landRegions[31], landRegions[73]);  //Ukraine -- Moldavia
        landBorders[25] = new Border(landRegions[32], landRegions[33]);  //Crimea -- Don Basin
        landBorders[26] = new Border(landRegions[33], landRegions[34]);  //Don Basin -- Caucasus
        landBorders[27] = new Border(landRegions[33], landRegions[4]);   //Don Basin -- Moscow
        landBorders[28] = new Border(landRegions[34], landRegions[4]);   //Caucasus -- Moscow
        landBorders[29] = new Border(landRegions[34], landRegions[41]);  //Caucasus -- Armenia
        landBorders[30] = new Border(landRegions[35], landRegions[4]);   //Northern Russia -- Moscow
        landBorders[31] = new Border(landRegions[35], landRegions[36]);  //Northern Russia -- Novgorod
        landBorders[32] = new Border(landRegions[35], landRegions[37]);  //Northern Russia -- Minsk-Smolensk
        landBorders[33] = new Border(landRegions[36], landRegions[37]);  //Novgorod -- Minsk-Smolensk
        landBorders[34] = new Border(landRegions[37], landRegions[4]);   //Minsk-Smolensk -- Moscow
        //Prussia
        landBorders[35] = new Border(landRegions[26], landRegions[25]);  //East Prussia -- Pomerania
        landBorders[36] = new Border(landRegions[26], landRegions[72]);  //East Prussia -- Warsaw
        landBorders[37] = new Border(landRegions[25], landRegions[24]);  //Pomerania -- Silesia
        landBorders[38] = new Border(landRegions[25], landRegions[2]);   //Pomerania -- Berlin
        landBorders[39] = new Border(landRegions[25], landRegions[72]);  //Pomerania -- Warsaw
        landBorders[40] = new Border(landRegions[25], landRegions[67]);  //Pomerania -- Mecklenburg
        landBorders[41] = new Border(landRegions[2], landRegions[67]);   //Berlin -- Mecklenburg
        landBorders[42] = new Border(landRegions[2], landRegions[66]);   //Berlin -- Hanover
        landBorders[43] = new Border(landRegions[2], landRegions[64]);   //Berlin -- Westphalia
        landBorders[44] = new Border(landRegions[2], landRegions[63]);   //Berlin -- Saxony
        landBorders[45] = new Border(landRegions[2], landRegions[24]);   //Berlin -- Silesia
        landBorders[46] = new Border(landRegions[24], landRegions[63]);  //Silesia -- Saxony
        landBorders[47] = new Border(landRegions[24], landRegions[72]);  //Silesia -- Warsaw
        landBorders[48] = new Border(landRegions[24], landRegions[49]);  //Silesia -- Bohemia
        landBorders[49] = new Border(landRegions[24], landRegions[48]);  //Silesia -- Galicia
        //Austria-Hungary
        landBorders[50] = new Border(landRegions[49], landRegions[59]);  //Bohemia -- Bavaria
        landBorders[51] = new Border(landRegions[49], landRegions[62]);  //Bohemia -- Thuringia
        landBorders[52] = new Border(landRegions[49], landRegions[63]);  //Bohemia -- Saxony
        landBorders[53] = new Border(landRegions[49], landRegions[48]);  //Bohemia -- Galicia
        landBorders[54] = new Border(landRegions[49], landRegions[46]);  //Bohemia -- Hungary
        landBorders[55] = new Border(landRegions[49], landRegions[6]);   //Bohemia -- Vienna
        landBorders[56] = new Border(landRegions[48], landRegions[47]);  //Galicia -- Transylvania
        landBorders[57] = new Border(landRegions[48], landRegions[46]);  //Galicia -- Hungary
        landBorders[58] = new Border(landRegions[48], landRegions[72]);  //Galicia -- Warsaw
        landBorders[59] = new Border(landRegions[48], landRegions[73]);  //Galicia -- Moldavia
        landBorders[60] = new Border(landRegions[47], landRegions[46]);  //Transylvania -- Hungary
        landBorders[61] = new Border(landRegions[47], landRegions[73]);  //Transylvania -- Moldavia
        landBorders[62] = new Border(landRegions[47], landRegions[74]);  //Transylvania -- Wallachia
        landBorders[63] = new Border(landRegions[46], landRegions[6]);   //Hungary -- Vienna
        landBorders[64] = new Border(landRegions[46], landRegions[45]);  //Hungary -- Illyria
        landBorders[65] = new Border(landRegions[46], landRegions[77]);  //Hungary -- Bosnia
        landBorders[66] = new Border(landRegions[46], landRegions[76]);  //Hungary -- Serbia
        landBorders[67] = new Border(landRegions[46], landRegions[74]);  //Hungary -- Wallachia
        landBorders[68] = new Border(landRegions[45], landRegions[44]);  //Illyria -- Tyrol
        landBorders[69] = new Border(landRegions[45], landRegions[6]);   //Illyria -- Vienna
        landBorders[70] = new Border(landRegions[45], landRegions[77]);  //Illyria -- Bosnia
        landBorders[71] = new Border(landRegions[45], landRegions[78]);  //Illyria -- Albania
        landBorders[72] = new Border(landRegions[45], landRegions[85]);  //Illyria -- Venice
        landBorders[73] = new Border(landRegions[44], landRegions[6]);   //Tyrol -- Vienna
        landBorders[74] = new Border(landRegions[44], landRegions[59]);  //Tyrol -- Bavaria
        landBorders[75] = new Border(landRegions[44], landRegions[57]);  //Tyrol -- Switzerland
        landBorders[76] = new Border(landRegions[44], landRegions[86]);  //Tyrol -- Milan
        landBorders[77] = new Border(landRegions[44], landRegions[85]);  //Tyrol -- Venice
        landBorders[78] = new Border(landRegions[6], landRegions[59]);   //Vienna -- Bavaria
        //Ottoman
        landBorders[79] = new Border(landRegions[5], landRegions[75]);   //Constantinople -- Bulgaria
        landBorders[80] = new Border(landRegions[5], landRegions[79]);   //Constantinople -- Macedonia
        landBorders[81] = new Border(landRegions[38], landRegions[39]);  //Anatolia -- Ankara
        landBorders[82] = new Border(landRegions[38], landRegions[40]);  //Anatolia -- Southern Turkey
        landBorders[83] = new Border(landRegions[39], landRegions[40]);  //Ankara -- Southern Turkey
        landBorders[84] = new Border(landRegions[39], landRegions[41]);  //Ankara -- Armenia
        landBorders[85] = new Border(landRegions[40], landRegions[41]);  //Southern Turkey -- Armenia
        landBorders[86] = new Border(landRegions[40], landRegions[42]);  //Southern Turkey -- Syria
        landBorders[87] = new Border(landRegions[42], landRegions[41]);  //Syria -- Armenia
        landBorders[88] = new Border(landRegions[42], landRegions[43]);  //Syria -- Arabia
        landBorders[89] = new Border(landRegions[42], landRegions[94]);  //Syria -- Egypt
        landBorders[90] = new Border(landRegions[43], landRegions[41]);  //Arabia -- Armenia
        //Spain
        landBorders[91] = new Border(landRegions[50], landRegions[51]);  //Asturias -- Navarre
        landBorders[92] = new Border(landRegions[50], landRegions[54]);  //Asturias -- Western Spain
        landBorders[93] = new Border(landRegions[50], landRegions[7]);   //Asturias -- Madrid
        landBorders[94] = new Border(landRegions[50], landRegions[55]);  //Asturias -- Portugal
        landBorders[95] = new Border(landRegions[51], landRegions[52]);  //Navarre -- Catalonia
        landBorders[96] = new Border(landRegions[51], landRegions[11]);  //Navarre -- Gascony
        landBorders[97] = new Border(landRegions[51], landRegions[7]);   //Navarre -- Madrid
        landBorders[98] = new Border(landRegions[52], landRegions[53]);  //Navarre -- Andalusia
        landBorders[99] = new Border(landRegions[52], landRegions[7]);   //Navarre -- Madrid
        landBorders[100] = new Border(landRegions[52], landRegions[11]); //Navarre -- Gascony
        landBorders[101] = new Border(landRegions[52], landRegions[10]); //Navarre -- Provence
        landBorders[102] = new Border(landRegions[53], landRegions[54]); //Andalusia -- Western Spain
        landBorders[103] = new Border(landRegions[53], landRegions[7]);  //Andalusia -- Madrid
        landBorders[104] = new Border(landRegions[54], landRegions[7]);  //Western Spain -- Madrid
        landBorders[105] = new Border(landRegions[54], landRegions[55]); //Western Spain -- Portugal
        //France
        landBorders[106] = new Border(landRegions[8], landRegions[9]);   //Burgundy -- Central France
        landBorders[107] = new Border(landRegions[8], landRegions[10]);  //Burgundy -- Provence
        landBorders[108] = new Border(landRegions[8], landRegions[0]);   //Burgundy -- Paris
        landBorders[109] = new Border(landRegions[8], landRegions[15]);  //Burgundy -- Picardy
        landBorders[110] = new Border(landRegions[8], landRegions[16]);  //Burgundy -- Lorraine
        landBorders[111] = new Border(landRegions[8], landRegions[57]);  //Burgundy -- Switzerland
        landBorders[112] = new Border(landRegions[9], landRegions[10]);  //Central France -- Provence
        landBorders[113] = new Border(landRegions[9], landRegions[11]);  //Central France -- Gascony
        landBorders[114] = new Border(landRegions[9], landRegions[12]);  //Central France -- Vendee
        landBorders[115] = new Border(landRegions[9], landRegions[14]);  //Central France -- Normandy
        landBorders[116] = new Border(landRegions[9], landRegions[0]);   //Central France -- Paris
        landBorders[117] = new Border(landRegions[10], landRegions[11]); //Provence -- Gascony
        landBorders[118] = new Border(landRegions[10], landRegions[57]); //Provence -- Switzerland
        landBorders[119] = new Border(landRegions[10], landRegions[87]); //Provence -- Piedmont
        landBorders[120] = new Border(landRegions[11], landRegions[12]); //Gascony -- Vendee
        landBorders[121] = new Border(landRegions[12], landRegions[13]); //Vendee -- Brittany
        landBorders[122] = new Border(landRegions[12], landRegions[14]); //Vendee -- Normandy
        landBorders[123] = new Border(landRegions[13], landRegions[14]); //Brittany -- Normandy
        landBorders[124] = new Border(landRegions[14], landRegions[15]); //Normandy -- Picardy
        landBorders[125] = new Border(landRegions[14], landRegions[0]);  //Normandy -- Paris
        landBorders[126] = new Border(landRegions[15], landRegions[16]); //Picardy -- Lorraine
        landBorders[127] = new Border(landRegions[15], landRegions[0]);  //Picardy -- Paris
        landBorders[128] = new Border(landRegions[15], landRegions[60]); //Picardy -- Belgium
        landBorders[129] = new Border(landRegions[16], landRegions[60]); //Lorraine -- Belgium
        landBorders[130] = new Border(landRegions[16], landRegions[58]); //Lorraine -- Baden-Wurtt
        landBorders[131] = new Border(landRegions[16], landRegions[57]); //Lorraine -- Switzerland
        //Great Britain
        landBorders[132] = new Border(landRegions[17], landRegions[1]);  //Cornwall -- London
        landBorders[133] = new Border(landRegions[18], landRegions[1]);  //Wales -- London
        landBorders[134] = new Border(landRegions[18], landRegions[19]); //Wales -- Midlands
        landBorders[135] = new Border(landRegions[19], landRegions[20]); //Midlands -- Scotland
        landBorders[136] = new Border(landRegions[19], landRegions[1]);  //Midlands -- London
        landBorders[137] = new Border(landRegions[20], landRegions[21]); //Scotland -- Highlands
        landBorders[138] = new Border(landRegions[22], landRegions[23]); //Ulster -- Ireland
        //Central Europe
        landBorders[139] = new Border(landRegions[57], landRegions[58]); //Switzerland -- Baden-Wurtt
        landBorders[140] = new Border(landRegions[57], landRegions[59]); //Switzerland -- Bavaria
        landBorders[141] = new Border(landRegions[57], landRegions[86]); //Switzerland -- Milan
        landBorders[142] = new Border(landRegions[57], landRegions[87]); //Switzerland -- Piedmont
        landBorders[143] = new Border(landRegions[58], landRegions[59]); //Baden-Wurtt -- Bavaria
        landBorders[144] = new Border(landRegions[58], landRegions[60]); //Baden-Wurtt -- Belgium
        landBorders[145] = new Border(landRegions[58], landRegions[61]); //Baden-Wurtt -- Hesse-Berg
        landBorders[146] = new Border(landRegions[59], landRegions[62]); //Bavaria -- Thuringia
        landBorders[147] = new Border(landRegions[59], landRegions[61]); //Bavaria -- Hesse-Berg
        landBorders[148] = new Border(landRegions[60], landRegions[61]); //Belgium -- Hesse-Berg
        landBorders[149] = new Border(landRegions[60], landRegions[64]); //Belgium -- Westphalia
        landBorders[150] = new Border(landRegions[60], landRegions[65]); //Belgium -- Holland
        landBorders[151] = new Border(landRegions[61], landRegions[62]); //Hesse-Berg -- Thuringia
        landBorders[152] = new Border(landRegions[61], landRegions[64]); //Hesse-Berg -- Westphalia
        landBorders[153] = new Border(landRegions[62], landRegions[63]); //Thuringia -- Saxony
        landBorders[154] = new Border(landRegions[62], landRegions[64]); //Thuringia -- Westphalia
        landBorders[155] = new Border(landRegions[63], landRegions[64]); //Saxony -- Westphalia
        landBorders[156] = new Border(landRegions[64], landRegions[65]); //Westphalia -- Holland
        landBorders[157] = new Border(landRegions[64], landRegions[66]); //Westphalia -- Hanover
        landBorders[158] = new Border(landRegions[65], landRegions[66]); //Holland -- Hanover
        //Northern Europe
        landBorders[159] = new Border(landRegions[66], landRegions[67]); //Hanover -- Mecklenburg
        landBorders[160] = new Border(landRegions[66], landRegions[68]); //Hanover -- Denmark
        //Eastern Europe
        landBorders[161] = new Border(landRegions[73], landRegions[74]); //Moldavia -- Wallachia
        landBorders[162] = new Border(landRegions[74], landRegions[75]); //Wallachia -- Bulgaria
        landBorders[163] = new Border(landRegions[74], landRegions[76]); //Wallachia -- Serbia
        landBorders[164] = new Border(landRegions[75], landRegions[76]); //Bulgaria -- Serbia
        landBorders[165] = new Border(landRegions[75], landRegions[79]); //Bulgaria -- Macedonia
        landBorders[166] = new Border(landRegions[76], landRegions[77]); //Serbia -- Bosnia
        landBorders[167] = new Border(landRegions[76], landRegions[78]); //Serbia -- Albania
        landBorders[168] = new Border(landRegions[76], landRegions[79]); //Serbia -- Macedonia
        landBorders[169] = new Border(landRegions[77], landRegions[78]); //Bosnia -- Albania
        landBorders[170] = new Border(landRegions[78], landRegions[79]); //Albania -- Macedonia
        landBorders[171] = new Border(landRegions[79], landRegions[80]); //Macedonia -- Greece
        //Southern Europe - Italy
        landBorders[172] = new Border(landRegions[82], landRegions[83]); //Naples -- Papel-States
        landBorders[173] = new Border(landRegions[83], landRegions[84]); //Papel-States -- Tuscany
        landBorders[174] = new Border(landRegions[83], landRegions[85]); //Papel-States -- Venice
        landBorders[175] = new Border(landRegions[83], landRegions[86]); //Papel-States -- Milan
        landBorders[176] = new Border(landRegions[84], landRegions[86]); //Tuscany -- Milan
        landBorders[177] = new Border(landRegions[85], landRegions[86]); //Venice -- Milan
        landBorders[178] = new Border(landRegions[86], landRegions[87]); //Milan -- Piedmont
        //Africa
        landBorders[179] = new Border(landRegions[88], landRegions[89]); //Morocco -- Oran
        landBorders[180] = new Border(landRegions[89], landRegions[90]); //Oran -- Algeria
        landBorders[181] = new Border(landRegions[90], landRegions[91]); //Algeria -- Tunisia
        landBorders[182] = new Border(landRegions[91], landRegions[92]); //Tunisia -- Tripolitania
        landBorders[183] = new Border(landRegions[92], landRegions[93]); //Tripolitania -- Cyrenaica
        landBorders[184] = new Border(landRegions[93], landRegions[94]); //Cyrenaica -- Egypt
    }

    private void initStraitBorders() {
        straitBorders = new Border[5];

        straitBorders[0] = new Border(landRegions[68], landRegions[69]); //Denmark -- Sweden
        straitBorders[1] = new Border(landRegions[5], landRegions[38]);  //Constantinople -- Anatolia
        straitBorders[2] = new Border(landRegions[81], landRegions[82]); //Sicily -- Naples
        straitBorders[3] = new Border(landRegions[56], landRegions[54]); //Gibraltar -- Western Spain
        straitBorders[4] = new Border(landRegions[56], landRegions[53]); //Gibraltar -- Andalusia
    }

    //todo figure out movement cost for sea travel.
    private void initSeaBorders() {
        seaBorders = new Border[18];

        seaBorders[0] = new Border(seaRegions[0], seaRegions[1]);  //Gulf of Bothnia -- Baltic Sea
        seaBorders[1] = new Border(seaRegions[1], seaRegions[2]);  //Baltic Sea -- North Sea
        seaBorders[2] = new Border(seaRegions[2], seaRegions[3]);  //North Sea -- North Atlantic
        seaBorders[3] = new Border(seaRegions[2], seaRegions[5]);  //North Sea -- English Channel
        seaBorders[4] = new Border(seaRegions[3], seaRegions[4]);  //North Atlantic -- Irish Sea
        seaBorders[5] = new Border(seaRegions[3], seaRegions[5]);  //North Atlantic -- English Channel
        seaBorders[6] = new Border(seaRegions[3], seaRegions[6]);  //North Atlantic -- Bay of Biscay
        seaBorders[7] = new Border(seaRegions[6], seaRegions[7]);  //Bay of Biscay -- Mid Atlantic
        seaBorders[8] = new Border(seaRegions[7], seaRegions[8]);  //Mid Atlantic -- Barbary Coast
        seaBorders[9] = new Border(seaRegions[8], seaRegions[9]);  //Barbary Coast -- Gulf of Marseilles
        seaBorders[10] = new Border(seaRegions[8], seaRegions[10]); //Barbary Coast -- Tyrrhenian Sea
        seaBorders[11] = new Border(seaRegions[9], seaRegions[10]); //Gulf of Marseilles -- Tyrrhenian Sea
        seaBorders[12] = new Border(seaRegions[10], seaRegions[11]); //Tyrrhenian Sea -- Ionian Sea
        seaBorders[13] = new Border(seaRegions[11], seaRegions[12]); //Ionian Sea -- Adriatic Sea
        seaBorders[14] = new Border(seaRegions[11], seaRegions[13]); //Ionian Sea -- Eastern Mediterranean
        seaBorders[15] = new Border(seaRegions[11], seaRegions[14]); //Ionian Sea -- Aegean Sea
        seaBorders[16] = new Border(seaRegions[13], seaRegions[14]); //Eastern Mediterranean -- Aegean Sea
        seaBorders[17] = new Border(seaRegions[14], seaRegions[15]); //Aegean Sea -- Black Sea
    }

    private void initLandSeaBorders() {
        landSeaBorders = new Border[100];

        landSeaBorders[0] = new Border(seaRegions[0], landRegions[3]);     //Gulf of Bothnia -- St. Petersburg
        landSeaBorders[1] = new Border(seaRegions[0], landRegions[27]);    //Gulf of Bothnia -- Estonia
        landSeaBorders[2] = new Border(seaRegions[0], landRegions[28]);    //Gulf of Bothnia -- Courland
        landSeaBorders[3] = new Border(seaRegions[0], landRegions[71]);    //Gulf of Bothnia -- Finland
        landSeaBorders[4] = new Border(seaRegions[0], landRegions[70]);    //Gulf of Bothnia -- Sweden
        landSeaBorders[5] = new Border(seaRegions[1], landRegions[28]);    //Baltic Sea -- Courland
        landSeaBorders[6] = new Border(seaRegions[1], landRegions[26]);    //Baltic Sea -- East Prussia
        landSeaBorders[7] = new Border(seaRegions[1], landRegions[25]);    //Baltic Sea -- Pomerania
        landSeaBorders[8] = new Border(seaRegions[1], landRegions[67]);    //Baltic Sea -- Mecklenburg
        landSeaBorders[9] = new Border(seaRegions[1], landRegions[66]);    //Baltic Sea -- Hanover
        landSeaBorders[10] = new Border(seaRegions[1], landRegions[68]);   //Baltic Sea -- Denmark
        landSeaBorders[11] = new Border(seaRegions[1], landRegions[70]);   //Baltic Sea -- Sweden
        landSeaBorders[12] = new Border(seaRegions[2], landRegions[21]);   //North Sea -- Highlands
        landSeaBorders[13] = new Border(seaRegions[2], landRegions[20]);   //North Sea -- Scotland
        landSeaBorders[14] = new Border(seaRegions[2], landRegions[19]);   //North Sea -- Midlands
        landSeaBorders[15] = new Border(seaRegions[2], landRegions[1]);    //North Sea -- London
        landSeaBorders[16] = new Border(seaRegions[2], landRegions[15]);   //North Sea -- Picardy
        landSeaBorders[17] = new Border(seaRegions[2], landRegions[60]);   //North Sea -- Belgium
        landSeaBorders[18] = new Border(seaRegions[2], landRegions[65]);   //North Sea -- Holland
        landSeaBorders[19] = new Border(seaRegions[2], landRegions[66]);   //North Sea -- Hanover
        landSeaBorders[20] = new Border(seaRegions[2], landRegions[68]);   //North Sea -- Denmark
        landSeaBorders[21] = new Border(seaRegions[2], landRegions[70]);   //North Sea -- Sweden
        landSeaBorders[22] = new Border(seaRegions[2], landRegions[69]);   //North Sea -- Norway
        landSeaBorders[23] = new Border(seaRegions[3], landRegions[21]);   //North Atlantic -- Highlands
        landSeaBorders[24] = new Border(seaRegions[3], landRegions[22]);   //North Atlantic -- Ulster
        landSeaBorders[25] = new Border(seaRegions[3], landRegions[23]);   //North Atlantic -- Ireland
        landSeaBorders[26] = new Border(seaRegions[3], landRegions[18]);   //North Atlantic -- Wales
        landSeaBorders[27] = new Border(seaRegions[3], landRegions[1]);    //North Atlantic -- London
        landSeaBorders[28] = new Border(seaRegions[3], landRegions[17]);   //North Atlantic -- Cornwall
        landSeaBorders[29] = new Border(seaRegions[3], landRegions[13]);   //North Atlantic -- Brittany
        landSeaBorders[30] = new Border(seaRegions[4], landRegions[18]);   //Irish Sea -- Wales
        landSeaBorders[31] = new Border(seaRegions[4], landRegions[19]);   //Irish Sea -- Midlands
        landSeaBorders[32] = new Border(seaRegions[4], landRegions[20]);   //Irish Sea -- Scotland
        landSeaBorders[33] = new Border(seaRegions[4], landRegions[21]);   //Irish Sea -- Highlands
        landSeaBorders[34] = new Border(seaRegions[4], landRegions[22]);   //Irish Sea -- Ulster
        landSeaBorders[35] = new Border(seaRegions[4], landRegions[23]);   //Irish Sea -- Ireland
        landSeaBorders[36] = new Border(seaRegions[5], landRegions[13]);   //English Channel -- Brittany
        landSeaBorders[37] = new Border(seaRegions[5], landRegions[14]);   //English Channel -- Normandy
        landSeaBorders[38] = new Border(seaRegions[5], landRegions[15]);   //English Channel -- Picardy
        landSeaBorders[39] = new Border(seaRegions[5], landRegions[17]);   //English Channel -- Cornwall
        landSeaBorders[40] = new Border(seaRegions[5], landRegions[1]);    //English Channel -- London
        landSeaBorders[41] = new Border(seaRegions[6], landRegions[13]);   //Bay of Biscay -- Brittany
        landSeaBorders[42] = new Border(seaRegions[6], landRegions[12]);   //Bay of Biscay -- Vendee
        landSeaBorders[43] = new Border(seaRegions[6], landRegions[11]);   //Bay of Biscay -- Gascony
        landSeaBorders[44] = new Border(seaRegions[6], landRegions[51]);   //Bay of Biscay -- Navarre
        landSeaBorders[45] = new Border(seaRegions[6], landRegions[50]);   //Bay of Biscay -- Asturias
        landSeaBorders[46] = new Border(seaRegions[7], landRegions[50]);   //Mid Atlantic -- Asturias
        landSeaBorders[47] = new Border(seaRegions[7], landRegions[55]);   //Mid Atlantic -- Portugal
        landSeaBorders[48] = new Border(seaRegions[7], landRegions[54]);   //Mid Atlantic -- Western Spain
        landSeaBorders[49] = new Border(seaRegions[7], landRegions[56]);   //Mid Atlantic -- Gibraltar
        landSeaBorders[50] = new Border(seaRegions[7], landRegions[88]);   //Mid Atlantic -- Morocco
        landSeaBorders[51] = new Border(seaRegions[8], landRegions[56]);   //Barbary Coast -- Gibraltar
        landSeaBorders[52] = new Border(seaRegions[8], landRegions[53]);   //Barbary Coast -- Andalusia
        landSeaBorders[53] = new Border(seaRegions[8], landRegions[88]);   //Barbary Coast -- Morocco
        landSeaBorders[54] = new Border(seaRegions[8], landRegions[89]);   //Barbary Coast -- Oran
        landSeaBorders[55] = new Border(seaRegions[8], landRegions[90]);   //Barbary Coast -- Algeria
        landSeaBorders[56] = new Border(seaRegions[8], landRegions[91]);   //Barbary Coast -- Tunisia
        landSeaBorders[57] = new Border(seaRegions[9], landRegions[53]);   //Gulf of Marseilles -- Andalusia
        landSeaBorders[58] = new Border(seaRegions[9], landRegions[52]);   //Gulf of Marseilles -- Catalonia
        landSeaBorders[59] = new Border(seaRegions[9], landRegions[10]);   //Gulf of Marseilles -- Provence
        landSeaBorders[60] = new Border(seaRegions[9], landRegions[87]);   //Gulf of Marseilles -- Piedmont
        landSeaBorders[61] = new Border(seaRegions[9], landRegions[86]);   //Gulf of Marseilles -- Milan
        landSeaBorders[62] = new Border(seaRegions[10], landRegions[86]);  //Tyrrhenian Sea -- Milan
        landSeaBorders[63] = new Border(seaRegions[10], landRegions[84]);  //Tyrrhenian Sea -- Tuscany
        landSeaBorders[64] = new Border(seaRegions[10], landRegions[83]);  //Tyrrhenian Sea -- Papel-States
        landSeaBorders[65] = new Border(seaRegions[10], landRegions[82]);  //Tyrrhenian Sea -- Naples
        landSeaBorders[66] = new Border(seaRegions[10], landRegions[81]);  //Tyrrhenian Sea -- Sicily
        landSeaBorders[67] = new Border(seaRegions[10], landRegions[91]);  //Tyrrhenian Sea -- Tunisia
        landSeaBorders[68] = new Border(seaRegions[11], landRegions[81]);  //Ionian Sea -- Sicily
        landSeaBorders[69] = new Border(seaRegions[11], landRegions[82]);  //Ionian Sea -- Naples
        landSeaBorders[70] = new Border(seaRegions[11], landRegions[78]);  //Ionian Sea -- Albania
        landSeaBorders[71] = new Border(seaRegions[11], landRegions[79]);  //Ionian Sea -- Macedonia
        landSeaBorders[72] = new Border(seaRegions[11], landRegions[80]);  //Ionian Sea -- Greece
        landSeaBorders[73] = new Border(seaRegions[11], landRegions[92]);  //Ionian Sea -- Tripolitania
        landSeaBorders[74] = new Border(seaRegions[11], landRegions[93]);  //Ionian Sea -- Cyrenaica
        landSeaBorders[75] = new Border(seaRegions[11], landRegions[94]);  //Ionian Sea -- Egypt
        landSeaBorders[76] = new Border(seaRegions[12], landRegions[82]);  //Adriatic Sea -- Naples
        landSeaBorders[77] = new Border(seaRegions[12], landRegions[83]);  //Adriatic Sea -- Papel-States
        landSeaBorders[78] = new Border(seaRegions[12], landRegions[85]);  //Adriatic Sea -- Venice
        landSeaBorders[79] = new Border(seaRegions[12], landRegions[45]);  //Adriatic Sea -- Illyria
        landSeaBorders[80] = new Border(seaRegions[12], landRegions[78]);  //Adriatic Sea -- Albania
        landSeaBorders[81] = new Border(seaRegions[13], landRegions[94]);  //Eastern Mediterranean -- Egypt
        landSeaBorders[82] = new Border(seaRegions[13], landRegions[42]);  //Eastern Mediterranean -- Syria
        landSeaBorders[83] = new Border(seaRegions[13], landRegions[40]);  //Eastern Mediterranean -- Southern Turkey
        landSeaBorders[84] = new Border(seaRegions[13], landRegions[38]);  //Eastern Mediterranean -- Anatolia
        landSeaBorders[85] = new Border(seaRegions[14], landRegions[80]);  //Aegean Sea -- Greece
        landSeaBorders[86] = new Border(seaRegions[14], landRegions[79]);  //Aegean Sea -- Macedonia
        landSeaBorders[87] = new Border(seaRegions[14], landRegions[5]);   //Aegean Sea -- Constantinople
        landSeaBorders[88] = new Border(seaRegions[14], landRegions[38]);  //Aegean Sea -- Anatolia
        landSeaBorders[89] = new Border(seaRegions[15], landRegions[5]);   //Black Sea -- Constantinople
        landSeaBorders[90] = new Border(seaRegions[15], landRegions[75]);  //Black Sea -- Bulgaria
        landSeaBorders[91] = new Border(seaRegions[15], landRegions[74]);  //Black Sea -- Wallachia
        landSeaBorders[92] = new Border(seaRegions[15], landRegions[73]);  //Black Sea -- Moldavia
        landSeaBorders[93] = new Border(seaRegions[15], landRegions[31]);  //Black Sea -- Ukraine
        landSeaBorders[94] = new Border(seaRegions[15], landRegions[32]);  //Black Sea -- Crimea
        landSeaBorders[95] = new Border(seaRegions[15], landRegions[33]);  //Black Sea -- Don Basin
        landSeaBorders[96] = new Border(seaRegions[15], landRegions[34]);  //Black Sea -- Caucasus
        landSeaBorders[97] = new Border(seaRegions[15], landRegions[41]);  //Black Sea -- Armenia
        landSeaBorders[98] = new Border(seaRegions[15], landRegions[39]);  //Black Sea -- Ankara
        landSeaBorders[99] = new Border(seaRegions[15], landRegions[38]);  //Black Sea -- Anatolia
    }

    public Region[] getLandRegions() { return landRegions; }
    public Region[] getSeaRegions() { return seaRegions; }
    public Port[] getPorts() { return ports; }
    public Border[] getLandBorders() { return landBorders; }
    public Border[] getStraitBorders() { return straitBorders; }
    public Border[] getSeaBorders() { return seaBorders; }
    public Border[] getLandSeaBorders() { return landSeaBorders; }

    public Border[] getLandAndStraitBorders() {
        int i = 0;
        Border[] borders = new Border[landBorders.length + straitBorders.length];

        for (Border b : landBorders)
            borders[i++] = b;
        for (Border b : straitBorders)
            borders[i++] = b;

        return borders;
    }

    public Border[] getAllBorders() {
        int i = 0;
        Border[] borders = new Border[landBorders.length + straitBorders.length + seaBorders.length + landSeaBorders.length];

        for (Border b : landBorders)
            borders[i++] = b;
        for (Border b : straitBorders)
            borders[i++] = b;
        for (Border b : seaBorders)
            borders[i++] = b;
        for (Border b : landSeaBorders)
            borders[i++] = b;

        return borders;
    }

    public Border[] getAllLandSeaBorders_noStraits() {
        int i = 0;
        Border[] borders = new Border[landBorders.length + seaBorders.length + landSeaBorders.length];

        for (Border b : landBorders)
            borders[i++] = b;
        for (Border b : seaBorders)
            borders[i++] = b;
        for (Border b : landSeaBorders)
            borders[i++] = b;

        return borders;
    }

    public Border[] getAllWinterBorders_noStraits() {
        int i = 0;
        Border[] borders = new Border[landBorders.length + seaBorders.length - 2 + landSeaBorders.length - 12];
        for (Border b : landBorders)
            borders[i++] = b;
        for (int n = 2; n < seaBorders.length; n++)
            borders[i++] = seaBorders[n];
        for (int n = 12; n < landSeaBorders.length; n++)
            borders[i++] = landSeaBorders[n];

        return borders;
    }

    public Border[] getAllWinterBorders() {
        int i = 0;
        Border[] borders = new Border[landBorders.length + straitBorders.length + seaBorders.length - 2 + landSeaBorders.length - 12];
        for (Border b : landBorders)
            borders[i++] = b;
        for (Border b : straitBorders)
            borders[i++] = b;
        for (int n = 2; n < seaBorders.length; n++)
            borders[i++] = seaBorders[n];
        for (int n = 12; n < landSeaBorders.length; n++)
            borders[i++] = landSeaBorders[n];

        return borders;
    }

    public Border[] getWinterSeaBorders() {
        int i = 0;
        Border[] borders = new Border[seaBorders.length - 2];

        for (int n = 2; n < seaBorders.length; i++)
            borders[i++] = seaBorders[n];

        return borders;
    }

    public ArrayList<LandRegion> getFrenchHomelands() {
        ArrayList<LandRegion> land = new ArrayList<LandRegion>();
        //Paris
        land.add(landRegions[0]);
        //French Homelands
        land.addAll(Arrays.asList(landRegions).subList(8, 16 + 1));
        return land;
    }

    public ArrayList<LandRegion> getBritishHomelands() {
        ArrayList<LandRegion> land = new ArrayList<LandRegion>();
        //London
        land.add(landRegions[1]);
        //British Homelands
        land.addAll(Arrays.asList(landRegions).subList(17, 23 + 1));
        return land;
    }

    public ArrayList<LandRegion> getPrussianHomelands() {
        ArrayList<LandRegion> land = new ArrayList<LandRegion>();
        //Berlin
        land.add(landRegions[2]);
        //Prussian Homelands
        land.addAll(Arrays.asList(landRegions).subList(24, 26 + 1));
        return land;
    }

    public ArrayList<LandRegion> getRussianHomelands() {
        ArrayList<LandRegion> land = new ArrayList<LandRegion>();
        //St. Petersburg || Moscow
        land.add(landRegions[3]);
        land.add(landRegions[4]);
        //Russian Homelands
        land.addAll(Arrays.asList(landRegions).subList(27, 37 + 1));
        return land;
    }

    public ArrayList<LandRegion> getOttomanHomelands() {
        ArrayList<LandRegion> land = new ArrayList<LandRegion>();
        //Constantinople
        land.add(landRegions[5]);
        //Ottoman Homelands
        land.addAll(Arrays.asList(landRegions).subList(38, 43 + 1));
        return land;
    }

    public ArrayList<LandRegion> getAustrianHomelands() {
        ArrayList<LandRegion> land = new ArrayList<LandRegion>();
        //Vienna
        land.add(landRegions[6]);
        //Austrian Homelands
        land.addAll(Arrays.asList(landRegions).subList(44, 49 + 1));
        return land;
    }

    public ArrayList<LandRegion> getSpanishHomelands() {
        ArrayList<LandRegion> land = new ArrayList<LandRegion>();
        //Madrid
        land.add(landRegions[7]);
        //Spanish Homelands
        land.addAll(Arrays.asList(landRegions).subList(50, 54 + 1));
        return land;
    }

    public LandRegion getLandRegion(String landName) {
        for (LandRegion r : landRegions)
            if (r.toString().equals(landName))
                return r;
        return null;
    }

    public SeaRegion getSeaRegion(String seaName) {
        for (SeaRegion s : seaRegions)
            if (s.toString().equals(seaName))
                return s;
        return null;
    }

    public Port getPort(String portName) {
        for (Port p : ports)
            if (p.toString().equals(portName))
                return p;
        return null;
    }

    public Region getRegion(String name) {
        Region r;
        r = getLandRegion(name);
        if (r == null)
            r = getSeaRegion(name);
        if (r == null)
            r = getPort(name);

        return r;
    }

    public int getRegionIndex(Region r) {
        try {
            int index = 0;

            switch ( r.getType() ) {
                case Region.LAND_REGION:
                    while (index < landRegions.length)
                        if ( r.equals(landRegions[index++]) )
                            return index - 1;
                    break;
                case Region.PORT_REGION:
                    while (index < ports.length)
                        if ( r.equals(ports[index++]) )
                            return index - 1;
                    break;
                case Region.SEA_REGION:
                    while (index < seaRegions.length)
                        if ( r.equals(seaRegions[index++]) )
                            return index - 1;
                    break;
            }

            throw new Exception();
        } catch (Exception e) {
            GameLogger.log("MapInstance.getRegionIndex - Region Index not found");
            GameLogger.log(e.getMessage());
            return -1;
        }
    }

    public Region getRegionFromIndex(int index, int type) {
        try {
            switch (type) {
                case Region.LAND_REGION:
                    if ( index >= 0 && index < landRegions.length )
                        return landRegions[index];
                case Region.PORT_REGION:
                    if ( index >= 0 && index < ports.length )
                        return ports[index];
                case Region.SEA_REGION:
                    if ( index >= 0 && index < seaRegions.length )
                        return seaRegions[index];
            }

            throw new Exception();
        } catch (Exception e) {
            GameLogger.log("MapInstance.getRegionFromIndex - index or type do not match.. index = " + index + " type = " + type);
            GameLogger.log(e.getMessage());
            return null;
        }
    }

    public ArrayList<Region> getAllRegions() {
        ArrayList<Region> regions = new ArrayList<Region>();
        regions.addAll(Arrays.asList(landRegions));
        regions.addAll(Arrays.asList(seaRegions));
        regions.addAll(Arrays.asList(ports));
        return regions;
    }

    private LandRegion[] landRegions;
    private SeaRegion[] seaRegions;
    private Port[] ports;

    private Border[] landBorders;
    private Border[] straitBorders;
    private Border[] seaBorders;
    private Border[] landSeaBorders;
}