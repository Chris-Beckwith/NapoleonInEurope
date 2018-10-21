package game.gui.info;

import game.controller.DisplayController;
import game.controller.Region.LandRegion;
import game.util.GameMsg;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * NationRegionInfo.java Date Created: 09 14, 2013
 *
 * Purpose: To display the region info of a nation.
 *
 * Description: This panel will display all the region related
 * information for a specific nation.
 *
 * @author Chris
 */
public class NationRegionInfo extends JPanel {
    private int nation;
    public NationRegionInfo(int nation) {
        this.nation = nation;
        setBackground(Color.GRAY);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        createComponents();
        layoutComponents();
    }

    public void createComponents() {
        regionTitle = new JLabel(GameMsg.getString("ns.regionsTitle"));
//        regionTitle.setFont(new Font("Times New Roman", Font.BOLD, 16));
        regionLabels = new ArrayList<JPanel>();
        regionTitle.setForeground(DisplayController.getNationColor(nation));
    }

    public void layoutComponents() {
        removeAll();
        JPanel headerRow = new JPanel();
        headerRow.setBackground(Color.GRAY);
        headerRow.setLayout(new BoxLayout(headerRow, BoxLayout.LINE_AXIS));
        headerRow.add(Box.createHorizontalStrut(5));
        headerRow.add(regionTitle);
        headerRow.add(Box.createHorizontalGlue());

        add(headerRow);
        add(Box.createVerticalStrut(5));
        for (JPanel p : regionLabels)
            add(p);
    }

    public void setRegions(ArrayList<LandRegion> regions) {
        regionLabels.clear();
        for (LandRegion r : regions)
            regionLabels.add(new RegionRowPanel(r.toString()));
        layoutComponents();
    }

    public class RegionRowPanel extends JPanel {
        public RegionRowPanel(String text) {
            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            setOpaque(false);
            //Create and Layout Components
            add(Box.createHorizontalStrut(25));
            add(new JLabel(text));
            add(Box.createHorizontalGlue());
        }
    }

    private JLabel regionTitle;
    private ArrayList<JPanel> regionLabels;
}
