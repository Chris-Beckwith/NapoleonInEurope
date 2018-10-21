package game.gui.placement;

import game.controller.DisplayController;
import game.controller.GameController;
import game.controller.Region.Region;
import game.controller.Unit.MilitaryUnit;
import game.gui.listeners.UndecoratedMouseListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * PlacementFrame.java  Date Created: Sep 16, 2013
 *
 * Purpose: To contain the PlacementPanel.
 *
 * Description:
 *
 * @author Chrisb
 */
public class PlacementFrame extends JDialog {
    public PlacementFrame(GameController controller, DisplayController display, ArrayList<MilitaryUnit> unitsToPlace, ArrayList<Region> regionsToPlace) {
        super();
        UndecoratedMouseListener listener = new UndecoratedMouseListener(this);
        addMouseListener(listener);
        addMouseMotionListener(listener);
        setUndecorated(true);
        setVisible(true);
        setResizable(false);
        setAlwaysOnTop(true);
        setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setTitle("Unit Placement");
        setContentPane(new PlacementPanel(controller, display, this, unitsToPlace, regionsToPlace));
    }
}