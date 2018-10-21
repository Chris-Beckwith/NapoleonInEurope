package game.gui;

import game.controller.DisplayController;
import game.controller.GameController;
import game.gui.info.TerritoryInfo;

import javax.swing.*;
import java.awt.*;

/**
 * SidePanel.java  Date Created: Mar 13, 2013
 *
 * Purpose: To handle the display of the side panel.  This panel shall hide/display panels as needed.
 *
 * Description: This panel will always display the TerritoryInfo panel (but may not under certain circumstances).
 * This panel shall display the PlacementPanel when a nation is placing pieces.
 *
 * @author Chrisb
 */
public class SidePanel extends JPanel {
    public SidePanel(GameController controller, DisplayController display) {
        super(new GridBagLayout());
        this.controller = controller;
        this.display = display;

        createComponents();
        layoutComponents();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBackground(Color.DARK_GRAY);
//        setPreferredSize(new Dimension(225,363));
        setMaximumSize(new Dimension(225,1675));
        setMinimumSize(new Dimension(225,363));
//        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4), BorderFactory.createEmptyBorder(10,10,10,10)));
    }

    private void createComponents() {
        tiPanel = new TerritoryInfo(controller, display);
//        pPanel = new PlacementPanel(controller, display, controller.getUnplacedUnits());
    }

    private void layoutComponents() {
//        add(pPanel);
//        add(Box.createVerticalStrut(10));
        add(tiPanel);
    }
    
    private GameController controller;
    private DisplayController display;

    public TerritoryInfo tiPanel;
//    public PlacementPanel pPanel;
}