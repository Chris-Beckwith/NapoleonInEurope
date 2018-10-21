package game.controller.Battle;


import lobby.controller.User;

import game.controller.GameController;
import game.controller.NationInstance;
import game.controller.MapInstance;
import game.controller.Battle.*;
import game.controller.Region.*;
import game.controller.Unit.*;

import java.util.ArrayList;


/**
 * BattleInstance.java  Time Created: 9/24/13 10:00 AM
 *
 * Purpose: Test Battle Instance
 *
 * Description:
 *
 * @author DanP
 */

public class BattleTest {

    public static void main() {


    }

    public BattleTest () {

        User user = new User( "test" );
        byte [] gameId;

        gameId = new byte[1];

        GameController game = new GameController( user , gameId );

        MapInstance map = new MapInstance();

        ArrayList<NationInstance> nations = new ArrayList<NationInstance>();


        NationInstance france = new NationInstance(1, 1, 1, map);
        NationInstance britain = new NationInstance(2, 1, 1, map);

        //make france and britain at war with each other

        nations.add(france);
        nations.add(britain);

        Region region = new Region("Fart");

        Infantry franceInf1 = new Infantry(1);
        Infantry franceInf2 = new Infantry(1);
        Infantry franceInf3 = new Infantry(1);
        Infantry franceInf4 = new Infantry(1);
        Infantry franceInf5 = new Infantry(1);

        Cavalry franceCav1 = new Cavalry(1);
        Cavalry franceCav2 = new Cavalry(1);
        Cavalry franceCav3 = new Cavalry(1);

        Infantry britInf1 = new Infantry(2);
        Infantry britInf2 = new Infantry(2);
        Infantry britInf3 = new Infantry(2);
        Infantry britInf4 = new Infantry(2);

        Cavalry britCav1 = new Cavalry(2);
        Cavalry britCav2 = new Cavalry(2);


        /**THIS FUNCTIONALITY HAS CHANGED**/
        //Units now know where they are located [ unit.getLocation() ]
        //Use the UnitController to move units [ UnitController.moveLandUnit(unit, region) ]
//        region.addUnit( franceInf1 );
//        region.addUnit( franceInf2 );
//        region.addUnit( franceInf3 );
//        region.addUnit( franceInf4 );
//        region.addUnit( franceInf5 );

//        region.addUnit( franceCav1 );
//        region.addUnit( franceCav2 );
//        region.addUnit( franceCav3 );

//        region.addUnit ( britInf1 );
//        region.addUnit ( britInf2 );
//        region.addUnit ( britInf3 );
//        region.addUnit ( britInf4 );

//        region.addUnit( britCav1 );
//        region.addUnit( britCav2 );


       // BattleInstance battle = new BattleInstance(game, nations, region);


       // BattleController battleControl = new BattleController( battle );


        //System.out.println("Hello");



    }
}

