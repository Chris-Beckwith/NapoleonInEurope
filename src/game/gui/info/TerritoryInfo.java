package game.gui.info;

import game.controller.DisplayController;
import game.controller.GameController;
import game.controller.Region.LandRegion;
import game.controller.Region.Port;
import game.controller.Region.Region;
import game.controller.Region.SeaRegion;
import game.controller.Unit.MilitaryUnit;
import game.util.GameMsg;
import lobby.controller.Nation;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * TerritoryInfo.java  Date Created: Oct 24, 2012
 *
 * Purpose: To display the information related to a selected territory.
 *
 * Description: This panel shall display the name of the territory selected,
 * the current nation controlling the territory, the units currently occupying the territory,
 * who controls said units.
 *
 * Temp Colors: French  - Color.BLUE
 *              British - Color.RED
 *              Prussia - Color.PINK
 *              Russia  - Color.ORANGE
 *              Ottoman - Color.YELLOW
 *              Austria - Color.GREEN
 *              Spain   - Color.CYAN
 *              Neutral - Color.BLACK; Color.LIGHT_GRAY; Color.DARK_GRAY (Naval Squads and Temp Annex Armies)
 *
 * Units:   Militia
 *          Infantry
 *          Elite Infantry
 *          Irregular Cavalry
 *          Cavalry
 *          Heavy Cavalry
 *          Artillery
 *          Horse Artillery
 *          General
 *          Admiral
 *          Naval Squadron
 *
 * 21 pixels per line/title todo add scrollPane function and max size
 *
 * @author Chrisb
 */
public class TerritoryInfo extends JPanel implements ActionListener {

    public TerritoryInfo(GameController controller, DisplayController display) {
        super();
        this.controller = controller;
        this.display = display;

        setBackground(Color.GRAY);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder( new CompoundBorder((new CompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4), BorderFactory.createEmptyBorder(5,5,5,5))),
                new CompoundBorder(new TitledBorder(GameMsg.getString("ti.title")), BorderFactory.createEmptyBorder(5,5,5,5))) );

        createComponents();
        layoutComponents();
    }

    private void createComponents() {
        Border rowBorder = new EmptyBorder(2,0,2,0);
        //Region Select Row
        selectRegion = new JLabel(GameMsg.getString("ti.regionSelect"));
        regions = new JComboBox(controller.getAllRegions().toArray());
        regions.addActionListener(this);

        //Region Owner Row
        regionOwnerRow = new JPanel();
        regionOwnerRow.setBorder(rowBorder);
        regionOwnerRow.setBackground(Color.GRAY);
        regionOwnerRow.setLayout(new BoxLayout(regionOwnerRow, BoxLayout.LINE_AXIS));

        JLabel ownerLabel = new JLabel(GameMsg.getString("ti.ownerLabel"));
        owner = new JLabel();

        regionOwnerRow.add(ownerLabel);
        regionOwnerRow.add(Box.createHorizontalStrut(10));
        regionOwnerRow.add(owner);

        frenchUnitInfo = new NationUnitInfo(Nation.FRANCE);
        britishUnitInfo = new NationUnitInfo(Nation.GREAT_BRITAIN);
        prussianUnitInfo = new NationUnitInfo(Nation.PRUSSIA);
        russianUnitInfo = new NationUnitInfo(Nation.RUSSIA);
        ottomanUnitInfo = new NationUnitInfo(Nation.OTTOMANS);
        austrianUnitInfo = new NationUnitInfo(Nation.AUSTRIA_HUNGARY);
        spanishUnitInfo = new NationUnitInfo(Nation.SPAIN);
        neutralUnitInfo = new NationUnitInfo(Nation.NEUTRAL);

        setRegion((Region) regions.getSelectedItem());
    }

    private void layoutComponents() {
        //regionSelectRow
        add(selectRegion);
        add(Box.createVerticalStrut(5));
        add(regions);
        add(Box.createVerticalStrut(10));

        //regionOwnerRow
        add(regionOwnerRow);
        add(Box.createVerticalStrut(5));
        add(frenchUnitInfo);
        add(britishUnitInfo);
        add(prussianUnitInfo);
        add(russianUnitInfo);
        add(ottomanUnitInfo);
        add(austrianUnitInfo);
        add(spanishUnitInfo);
        add(neutralUnitInfo);
    }

    private void setRegion(Region region) {
        //get units occupying region
        ArrayList<MilitaryUnit> unitList = controller.getUnitsInRegion(region.toString());

        frenchUnitInfo.clearUnits();
        britishUnitInfo.clearUnits();
        prussianUnitInfo.clearUnits();
        russianUnitInfo.clearUnits();
        ottomanUnitInfo.clearUnits();
        austrianUnitInfo.clearUnits();
        spanishUnitInfo.clearUnits();
        neutralUnitInfo.clearUnits();

        for (MilitaryUnit unit : unitList) {
            switch (unit.getOwningNation()) {
                case Nation.FRANCE: frenchUnitInfo.addUnit(unit); break;
                case Nation.GREAT_BRITAIN: britishUnitInfo.addUnit(unit); break;
                case Nation.PRUSSIA: prussianUnitInfo.addUnit(unit); break;
                case Nation.RUSSIA: russianUnitInfo.addUnit(unit); break;
                case Nation.OTTOMANS: ottomanUnitInfo.addUnit(unit); break;
                case Nation.AUSTRIA_HUNGARY: austrianUnitInfo.addUnit(unit); break;
                case Nation.SPAIN: spanishUnitInfo.addUnit(unit); break;
                case Nation.NEUTRAL: neutralUnitInfo.addUnit(unit); break;
            }
        }

        frenchUnitInfo.updateUnits();
        britishUnitInfo.updateUnits();
        prussianUnitInfo.updateUnits();
        russianUnitInfo.updateUnits();
        ottomanUnitInfo.updateUnits();
        austrianUnitInfo.updateUnits();
        spanishUnitInfo.updateUnits();
        neutralUnitInfo.updateUnits();

        setOwner(region);
        getPanelSize();
    }

    private void setOwner(Region region) {
        int ownerString = -1;
        if ( region.getType() == Region.LAND_REGION )
            ownerString = ((LandRegion)region).getControllingNation();
        else if ( region.getType() == Region.PORT_REGION )
            ownerString = ((Port)region).getPortRegion().getControllingNation();
        else if ( region.getType() == Region.SEA_REGION )
            ownerString = Nation.GLOBAL;

        owner.setText(GameMsg.getString("nation." + ownerString) );

        switch (ownerString) {
            case 0: owner.setForeground(DisplayController.NEUTRAL_COLOR); break;
            case 1: owner.setForeground(DisplayController.FRENCH_COLOR); break;
            case 2: owner.setForeground(DisplayController.BRITISH_COLOR); break;
            case 3: owner.setForeground(DisplayController.PRUSSIAN_COLOR); break;
            case 4: owner.setForeground(DisplayController.RUSSIAN_COLOR); break;
            case 5: owner.setForeground(DisplayController.OTTOMAN_COLOR); break;
            case 6: owner.setForeground(DisplayController.AUSTRIAN_COLOR); break;
            case 7: owner.setForeground(DisplayController.SPANISH_COLOR); break;
            case 8: owner.setForeground(DisplayController.NEUTRAL_COLOR); break;
        }
    }

    /*
     * When updates are received from the server about unit placement/movement,
     * call this method to update the selected/currently viewed territory.
     */
    public void refreshTerritoryInfo() {
        System.out.println("RefreshTerritoryInfo");
        regions.setSelectedIndex(regions.getSelectedIndex());
        getPanelSize();
    }

    public Dimension getPanelSize() {
        System.out.println("Territory Info Size: (" + this.getWidth() + "," + this.getHeight() + ")");
        return new Dimension(this.getWidth(), this.getHeight());
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JComboBox) {
            JComboBox cb = (JComboBox)e.getSource();
            if (cb.getSelectedItem() instanceof Region)
                setRegion((Region)cb.getSelectedItem());
        }
    }

    //drop downs with headers
    private GameController controller;
    private DisplayController display;

    private JLabel selectRegion;
    private JComboBox regions;

    private JPanel regionOwnerRow;
    private JLabel owner;

    private NationUnitInfo frenchUnitInfo;
    private NationUnitInfo britishUnitInfo;
    private NationUnitInfo prussianUnitInfo;
    private NationUnitInfo russianUnitInfo;
    private NationUnitInfo ottomanUnitInfo;
    private NationUnitInfo austrianUnitInfo;
    private NationUnitInfo spanishUnitInfo;
    private NationUnitInfo neutralUnitInfo;
}