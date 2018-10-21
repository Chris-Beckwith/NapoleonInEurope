package game.gui.info;

import game.controller.DisplayController;
import game.controller.Unit.MilitaryUnit;
import game.controller.Unit.NavalSquadron;
import game.gui.placement.UnitPanel;
import game.util.GameMsg;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * NationUnitInfo.java  Date Created: Sep 9, 2013
 *
 * Purpose: To display the unit list of a region for a nation.
 *
 * Description:  Eight (8) of these panels will be created and added to
 * TerritoryInfo panel, one for each nation and one for a neutral nation.
 *
 * @author Chrisb
 */
public class NationUnitInfo extends JPanel {
    public NationUnitInfo(int nation) {
        this.nation = nation;
        units = new ArrayList<MilitaryUnit>();

        createComponents();
        layoutComponents();

        setBackground(Color.GRAY);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    private void createComponents() {
        nationLabel = new JLabel(GameMsg.getString("nation.pos." + nation) + " Forces:");
        nationLabel.setForeground(DisplayController.getNationColor(nation));

        general = new UnitPanel(MilitaryUnit.GENERAL);
        admiral = new UnitPanel(MilitaryUnit.ADMIRAL);
        infantry = new UnitPanel(MilitaryUnit.INFANTRY);
        eliteInf = new UnitPanel(MilitaryUnit.ELITE_INFANTRY);
        cavalry = new UnitPanel(MilitaryUnit.CAVALRY);
        heavyCav = new UnitPanel(MilitaryUnit.HEAVY_CAVALRY);
        irregularCav = new UnitPanel(MilitaryUnit.IRREGULAR_CAVALRY);
        artillery = new UnitPanel(MilitaryUnit.ARTILLERY);
        horseArt = new UnitPanel(MilitaryUnit.HORSE_ARTILLERY);
        militia = new UnitPanel(MilitaryUnit.MILITIA);
        navalSquad = new UnitPanel(MilitaryUnit.NAVAL_SQUADRON);
    }

    private void layoutComponents() {
        JPanel headerRow = new JPanel();
        headerRow.setLayout(new BoxLayout(headerRow, BoxLayout.LINE_AXIS));
        headerRow.setOpaque(false);
        headerRow.add(Box.createHorizontalStrut(5));
        headerRow.add(nationLabel);
        headerRow.add(Box.createHorizontalGlue());

        add(Box.createVerticalStrut(5));
        add(headerRow);
        add(Box.createVerticalStrut(5));
        add(general);
        add(admiral);
        add(infantry);
        add(eliteInf);
        add(cavalry);
        add(heavyCav);
        add(irregularCav);
        add(artillery);
        add(horseArt);
        add(militia);
        add(navalSquad);
    }

    public void addUnit(MilitaryUnit u) { units.add(u); }

    public void setUnits(ArrayList<MilitaryUnit> units) {
        this.units.clear();
        this.units.addAll(units);
        updateUnits();
    }

    public void clearUnits() {
        units.clear();
        updateUnits();
    }

    protected void updateUnits() {
        general.clearUnits();
        admiral.clearUnits();
        infantry.clearUnits();
        eliteInf.clearUnits();
        cavalry.clearUnits();
        heavyCav.clearUnits();
        irregularCav.clearUnits();
        artillery.clearUnits();
        horseArt.clearUnits();
        militia.clearUnits();
        navalSquad.clearUnits();

        for (MilitaryUnit unit : units) {
            switch(unit.getType()) {
                case MilitaryUnit.GENERAL: general.addUnit(unit); break;
                case MilitaryUnit.ADMIRAL: admiral.addUnit(unit); break;
                case MilitaryUnit.INFANTRY: infantry.addUnit(unit); break;
                case MilitaryUnit.ELITE_INFANTRY: eliteInf.addUnit(unit); break;
                case MilitaryUnit.CAVALRY: cavalry.addUnit(unit); break;
                case MilitaryUnit.HEAVY_CAVALRY: heavyCav.addUnit(unit); break;
                case MilitaryUnit.IRREGULAR_CAVALRY: irregularCav.addUnit(unit); break;
                case MilitaryUnit.ARTILLERY: artillery.addUnit(unit); break;
                case MilitaryUnit.HORSE_ARTILLERY: horseArt.addUnit(unit); break;
                case MilitaryUnit.MILITIA: militia.addUnit(unit); break;
                case MilitaryUnit.NAVAL_SQUADRON: navalSquad.addUnit(unit);
                    if ( ((NavalSquadron)unit).getAdmiral() != null )
                        admiral.addUnit( ((NavalSquadron)unit).getAdmiral() );
                    break;
            }
        }
        if (units.size() > 0)
            setVisible(true);
        else
            setVisible(false);
        revalidate();
        repaint();
    }

    private int nation;
    private ArrayList<MilitaryUnit> units;

    private JLabel nationLabel;
    private UnitPanel general;
    private UnitPanel admiral;
    private UnitPanel infantry;
    private UnitPanel eliteInf;
    private UnitPanel cavalry;
    private UnitPanel heavyCav;
    private UnitPanel irregularCav;
    private UnitPanel artillery;
    private UnitPanel horseArt;
    private UnitPanel militia;
    private UnitPanel navalSquad;
}