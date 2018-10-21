package _Test;/*
* Hello World
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import game.util.GameMsg;

public class helloWorld extends JPanel implements PropertyChangeListener, MouseListener {
    public helloWorld() {
        System.err.println("This is a test err\n");

        /**
         * Testing area
         */

        GameMsg.load();
//        setOpaque(false);


//        HeadersComboBox box = new HeadersComboBox(new Object[]{"One", "Two", "Three"});
//        box.setHeaders(new int[]{2});
//        box.setHeaders(new int[]{2,3,4});
//        MapInstance map = new MapInstance();
//        PathFinder pathing = new PathFinder(map);
//
//        NationInstance france = new NationInstance(Nation.FRANCE, 0, LobbyConstants.X, map);
//        NationInstance gb = new NationInstance(Nation.GREAT_BRITAIN, 0, LobbyConstants.X, map);
//        NationInstance prussia = new NationInstance(Nation.PRUSSIA, 0, LobbyConstants.X, map);
//        NationInstance russia = new NationInstance(Nation.RUSSIA, 0, LobbyConstants.X, map);
//        NationInstance ottoman = new NationInstance(Nation.OTTOMANS, 0, LobbyConstants.X, map);
//        NationInstance hungary = new NationInstance(Nation.AUSTRIA_HUNGARY, 0, LobbyConstants.X, map);
//        NationInstance spain = new NationInstance(Nation.SPAIN, 0, LobbyConstants.X, map);
//
////        france.startAlliance(prussia.getNation());
//        france.startAlliance(russia.getNation());
//
//        gb.addEnemy(france.getNation());
//        gb.startAlliance(hungary.getNation());
//        gb.startAlliance(ottoman.getNation());
//
//        map.getSeaRegion(GameMsg.getString("AdriaticSea")).addAllNavalUnits(france.getNavy());
//
//        //nation, useStraits, useSea, isWinter, observeRightsOfPassage, avoidIntercept
//        pathing.findDynamicPaths(gb, true, true, false, true, true);
//
//        LinkedList<Region> pathD = pathing.getDynamicPath(map.getLandRegions()[1], map.getLandRegions()[45]);
//
//        System.out.print("\n\n");
//
//        if (pathD == null)
//            System.out.println("No Path possible");
//        else {
//            System.out.println("Path:");
//            while (pathD.size() > 0 ) {
//                Region r = pathD.pop();
//
//                System.out.print(r.toString());
//                if (pathD.size() > 0)
//                    System.out.print(" ---- ");
//            }
//        }
//        System.out.println();

//        ArrayList<MilitaryUnit> test = new ArrayList<MilitaryUnit>();
//
//        for (MilitaryUnit u : test) {
//            System.out.println("TESTING");
//        }

        //todo TEST GamePlaceUnitsPacket TEST

//        CongressMenu test = new CongressMenu(1,1,1);
//        test.setLocation(25,25);
//        test.pack();
    }

    public void mouseClicked(MouseEvent e) {
        //todo
        if (e.getButton() == MouseEvent.BUTTON1 ) {
            Component comp = e.getComponent();

            System.out.println(comp.isEnabled());
            System.out.println( ((JLabel)comp).getText() );
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void propertyChange(PropertyChangeEvent e) {
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame ("HelloWorld!!!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new helloWorld());

//        frame.setMinimumSize(new Dimension(200,200));
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("Hello World!!!");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
//                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}
