package util;

import game.controller.Unit.*;
import game.packet.GamePlaceUnitsPacket;

import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.io.IOException;
import java.util.ArrayList;

import lobby.packet.SetNationPacket;

/**
 * Utilities.java  Date Created: May 3, 2012
 *
 * Purpose: Contains utility methods.
 *
 * Description: Holds static methods that are not specific
 * to any one class.
 *
 * @author Chrisb
 */
public class Utilities {
    /**
     * Compares a pair of size two(2) byte arrays.
     * @param lobbyIdOne - byte array one.
     * @param lobbyIdTwo - byte array two.
     * @return - true if they are equal, false otherwise.
     */
    public static boolean compareLobbyIds(byte[] lobbyIdOne, byte[] lobbyIdTwo) {
        return (new Byte(lobbyIdOne[0]).compareTo( lobbyIdTwo[0]  ) == 0
             && new Byte(lobbyIdOne[1]).compareTo( lobbyIdTwo[1]  ) == 0);
    }

    public static InetAddress idAsInetAddress(byte[] id) {
        try {
            return InetAddress.getByName("224." + (id[0] & 0xff) + ".1." + (id[1] & 0xff) );
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("Utilities:idAsInetAddress - " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<MilitaryUnit> getUnitsFromPacket(GamePlaceUnitsPacket placeUnits) {
        int owningNation = placeUnits.getOwningNation();
        ArrayList<MilitaryUnit> units = new ArrayList<MilitaryUnit>();

        //Generals
        int num = placeUnits.getNumGenerals();
        int i = 0;
        while ( i++ < num )
            units.add(new General(owningNation));

        //Admiral
        num = placeUnits.getNumAdmirals();
        i = 0;
        while ( i++ < num )
            units.add(new Admiral(owningNation));

        //Infantry
        num = placeUnits.getNumInfantry();
        i = 0;
        while ( i++ < num )
            units.add(new Infantry(owningNation));

        //Elite Infantry
        num = placeUnits.getNumEliteInf();
        i = 0;
        while ( i++ < num )
            units.add(new EliteInfantry(owningNation));

        //Cavalry
        num = placeUnits.getNumCavalry();
        i = 0;
        while ( i++ < num )
            units.add(new Cavalry(owningNation));

        //Heavy Cavalry
        num = placeUnits.getNumHeavyCav();
        i = 0;
        while ( i++ < num )
            units.add(new HeavyCavalry(owningNation));

        //Irregular Cavalry
        num = placeUnits.getNumIrregularCav();
        i = 0;
        while ( i++ < num )
            units.add(new IrregularCavalry(owningNation));

        //Artillery
        num = placeUnits.getNumArtillery();
        i = 0;
        while ( i++ < num )
            units.add(new Artillery(owningNation));

        //Horse Artillery
        num = placeUnits.getNumHorseArt();
        i = 0;
        while ( i++ < num )
            units.add(new HorseArtillery(owningNation));

        //Militia
        num = placeUnits.getNumMilitia();
        i = 0;
        while ( i++ < num )
            units.add(new Militia(owningNation));

        //Naval Squadron
        num = placeUnits.getNumNavalSquads();
        i = 0;
        while ( i++ < num )
            units.add(new NavalSquadron(owningNation));

        return units;
    }

    public static void setNation(byte[] lobbyId, int userPosition, int nation, String leaderName, InetAddress address, int port) {
        try {
            byte[] outgoing;
            DatagramSocket socket = new DatagramSocket();

            System.out.println("Utilities:setNation before");

            outgoing = new SetNationPacket(lobbyId, userPosition, nation, leaderName).getPacket();
            DatagramPacket packet = new DatagramPacket(outgoing, outgoing.length, address, port);
            socket.send(packet);

            System.out.println("Utilities:setNation sent");

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("Utilities:setNation - " + e.getMessage());
        }
    }
}
