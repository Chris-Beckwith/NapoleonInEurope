package game.gui.placement;

import game.controller.DisplayController;
import game.controller.GameController;
import game.controller.Region.Region;
import game.controller.Unit.*;
import game.util.GameMsg;
import game.util.MessageDialog;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * PlacementPanel.java  Date Created: Mar 8, 2013
 *
 * Purpose: To display which pieces need to be placed and where to place them.
 *
 * Description:
 *
 * @author Chrisb
 */
public class PlacementPanel extends JPanel implements ActionListener {
    //todo add NationHero

    public PlacementPanel(GameController controller, DisplayController display, JDialog dialog, ArrayList<MilitaryUnit> unitsToPlace, ArrayList<Region> regionsToPlace) {
        super();
        this.controller = controller;
        this.display = display;
        this.dialog = dialog;
        unplacedUnits = unitsToPlace;
        placingNation = unitsToPlace.get(0).getOwningNation();
        if (regionsToPlace == null)
            placeableRegions = controller.getPlaceableRegions(placingNation);
        else
            placeableRegions = regionsToPlace;
        placedMap = new HashMap<Region, ArrayList<MilitaryUnit>>();

        setBackground(Color.GRAY);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder( new CompoundBorder((new CompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4), BorderFactory.createEmptyBorder(5,5,5,5))),
                new CompoundBorder(new TitledBorder(GameMsg.getString("nation.pos." + placingNation) + " Unit Placement"), BorderFactory.createEmptyBorder(0,5,5,5))) );

        initUnplacedCount();
        createComponents();
        layoutComponents();
    }

    //TESTING ONLY CONSTRUCTOR
//    public PlacementPanel(ArrayList<MilitaryUnit> unitsToPlace) {
//        super();
////        this.controller = controller;
////        this.display = display;
//        unplacedUnits = unitsToPlace;
//        placedMap = new HashMap<String, ArrayList<MilitaryUnit>>();
//
//        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
//        setBorder(new CompoundBorder(new EmptyBorder(5,5,5,5), new TitledBorder("French Unit Placement")));
//
//        initUnplacedCount();
//        createComponents();
//        layoutComponents();
//    }

    private void createComponents() {
        Border rowBorder = new EmptyBorder(0,0,2,0);
        Border hedBorder = new EmptyBorder(0,6,0,0);
        Border finBorder = new EmptyBorder(0,0,0,0);

        //Header Row
        headerRow = new JPanel();
        headerRow.setBorder(hedBorder);
        headerRow.setBackground(Color.GRAY);
        headerRow.setLayout(new BoxLayout(headerRow, BoxLayout.LINE_AXIS));
        //Unplaced Rows
        unplacedRows = new JPanel();
        unplacedRows.setBorder(rowBorder);
        unplacedRows.setBackground(Color.GRAY);
        unplacedRows.setLayout(new BoxLayout(unplacedRows, BoxLayout.PAGE_AXIS));
        //Region Select Row
        regionsToPlace = new JComboBox(placeableRegions.toArray());
        regionsToPlace.addActionListener(this);
        //Placed Rows
        placedRows = new JPanel();
        placedRows.setBorder(rowBorder);
        placedRows.setBackground(Color.GRAY);
        placedRows.setLayout(new BoxLayout(placedRows, BoxLayout.PAGE_AXIS));
        //Occupied Rows
        occupiedRows = new JPanel();
        occupiedRows.setBorder(rowBorder);
        occupiedRows.setBackground(Color.GRAY);
        occupiedRows.setLayout(new BoxLayout(occupiedRows, BoxLayout.PAGE_AXIS));
        //finalize Row
        finalizeRow = new JPanel();
        finalizeRow.setBorder(finBorder);
        finalizeRow.setBackground(Color.GRAY);
        finalizeRow.setLayout(new BoxLayout(finalizeRow, BoxLayout.LINE_AXIS));
        //add/sub/old
        addGeneral = new AddUnitPanel(MilitaryUnit.GENERAL, unplacedGen, this);
        addAdmiral = new AddUnitPanel(MilitaryUnit.ADMIRAL, unplacedAdm, this);
        addInfantry = new AddUnitPanel(MilitaryUnit.INFANTRY, unplacedInf, this);
        addEliteInf = new AddUnitPanel(MilitaryUnit.ELITE_INFANTRY, unplacedEInf, this);
        addCavalry = new AddUnitPanel(MilitaryUnit.CAVALRY, unplacedCav, this);
        addHeavyCav = new AddUnitPanel(MilitaryUnit.HEAVY_CAVALRY, unplacedHCav, this);
        addIrregularCav = new AddUnitPanel(MilitaryUnit.IRREGULAR_CAVALRY, unplacedICav, this);
        addArtillery = new AddUnitPanel(MilitaryUnit.ARTILLERY, unplacedArt, this);
        addHorseArt = new AddUnitPanel(MilitaryUnit.HORSE_ARTILLERY, unplacedHArt, this);
        addMilitia = new AddUnitPanel(MilitaryUnit.MILITIA, unplacedMil, this);
        addNavalSquad = new AddUnitPanel(MilitaryUnit.NAVAL_SQUADRON, unplacedNav, this);

        subGeneral = new SubUnitPanel(MilitaryUnit.GENERAL, this);
        subAdmiral = new SubUnitPanel(MilitaryUnit.ADMIRAL, this);
        subInfantry = new SubUnitPanel(MilitaryUnit.INFANTRY, this);
        subEliteInf = new SubUnitPanel(MilitaryUnit.ELITE_INFANTRY, this);
        subCavalry = new SubUnitPanel(MilitaryUnit.CAVALRY, this);
        subHeavyCav = new SubUnitPanel(MilitaryUnit.HEAVY_CAVALRY, this);
        subIrregularCav = new SubUnitPanel(MilitaryUnit.IRREGULAR_CAVALRY, this);
        subArtillery = new SubUnitPanel(MilitaryUnit.ARTILLERY, this);
        subHorseArt = new SubUnitPanel(MilitaryUnit.HORSE_ARTILLERY, this);
        subMilitia = new SubUnitPanel(MilitaryUnit.MILITIA, this);
        subNavalSquad = new SubUnitPanel(MilitaryUnit.NAVAL_SQUADRON, this);

        general = new UnitPanel(MilitaryUnit.GENERAL);
        admiral = new UnitPanel(MilitaryUnit.ADMIRAL);
        infantry = new UnitPanel(MilitaryUnit.INFANTRY);
        eliteInf = new UnitPanel(MilitaryUnit.ELITE_INFANTRY);
        cavalry = new UnitPanel(MilitaryUnit.CAVALRY);
        heavyCav = new UnitPanel(MilitaryUnit.HEAVY_CAVALRY);
        irregularCav = new UnitPanel(MilitaryUnit.IRREGULAR_CAVALRY);
        artillery = new UnitPanel(MilitaryUnit.ARTILLERY);
        horseArt = new UnitPanel(MilitaryUnit.HORSE_ARTILLERY);
        Militia = new UnitPanel(MilitaryUnit.MILITIA);
        navalSquad = new UnitPanel(MilitaryUnit.NAVAL_SQUADRON);

        general.setLayout(new BoxLayout(general, BoxLayout.LINE_AXIS));
        admiral.setLayout(new BoxLayout(admiral, BoxLayout.LINE_AXIS));
        infantry.setLayout(new BoxLayout(infantry, BoxLayout.LINE_AXIS));
        eliteInf.setLayout(new BoxLayout(eliteInf, BoxLayout.LINE_AXIS));
        cavalry.setLayout(new BoxLayout(cavalry, BoxLayout.LINE_AXIS));
        heavyCav.setLayout(new BoxLayout(heavyCav, BoxLayout.LINE_AXIS));
        irregularCav.setLayout(new BoxLayout(irregularCav, BoxLayout.LINE_AXIS));
        artillery.setLayout(new BoxLayout(artillery, BoxLayout.LINE_AXIS));
        horseArt.setLayout(new BoxLayout(horseArt, BoxLayout.LINE_AXIS));
        Militia.setLayout(new BoxLayout(Militia, BoxLayout.LINE_AXIS));
        navalSquad.setLayout(new BoxLayout(navalSquad, BoxLayout.LINE_AXIS));

        setRegion((Region)regionsToPlace.getSelectedItem());
    }

    private void layoutComponents() {
//        JLabel title = new JLabel(GameMsg.getString("nation.pos." + nation) + " " + GameMsg.getString("pp.title"));
        JLabel header = new JLabel(GameMsg.getString("pp.header"));

        //TESTING ONLY
//        JLabel title = new JLabel(GameMsg.getString("nation.pos.1") + " " + GameMsg.getString("pp.title"));
//        JLabel header = new JLabel(GameMsg.getString("nation.pos.1") + GameMsg.getString("pp.header"));

//        titleRow.add(Box.createHorizontalGlue());
//        titleRow.add(title);
//        titleRow.add(Box.createHorizontalGlue());
//        add(titleRow);

        headerRow.add(header);
        headerRow.add(Box.createHorizontalGlue());
        headerRow.setMaximumSize(new Dimension(200, 25));
        add(headerRow);

        //unplacedRows
        unplacedRows.add(addGeneral);
        unplacedRows.add(addAdmiral);
        unplacedRows.add(addInfantry);
        unplacedRows.add(addEliteInf);
        unplacedRows.add(addCavalry);
        unplacedRows.add(addHeavyCav);
        unplacedRows.add(addIrregularCav);
        unplacedRows.add(addArtillery);
        unplacedRows.add(addHorseArt);
        unplacedRows.add(addMilitia);
        unplacedRows.add(addNavalSquad);
        add(unplacedRows);

        //regionSelectRow
        //Select Region to Place Units  [   Paris   ] [ \/ ]
//        regionSelectRow.add(new JLabel(GameMsg.getString("pp.regionSelect")));
//        regionSelectRow.add(regionsToPlace);
//        regionsToPlace = new JComboBox(new Region[]{new Paris(), new London(),
//                new Ports(GameMsg.getString("port.picardy"),
//                        new LandRegion(GameMsg.getString("Picardy"), true, Nation.FRANCE, true, true, false),
//                        new SeaRegion[]{new SeaRegion(GameMsg.getString("EnglishChannel"))}) });
//        regionsToPlace.addActionListener(this);
//        regionSelectRow.add(Box.createHorizontalStrut(5));
//        regionSelectRow.add(regionsToPlace);
//        add(regionSelectRow);

        add(Box.createVerticalStrut(5));
        add(new JLabel(GameMsg.getString("pp.regionSelect")));
        add(Box.createVerticalStrut(5));
        add(regionsToPlace);

        //placedRows
        placedRows.add(subGeneral);
        placedRows.add(subAdmiral);
        placedRows.add(subInfantry);
        placedRows.add(subEliteInf);
        placedRows.add(subCavalry);
        placedRows.add(subHeavyCav);
        placedRows.add(subIrregularCav);
        placedRows.add(subArtillery);
        placedRows.add(subHorseArt);
        placedRows.add(subMilitia);
        placedRows.add(subNavalSquad);
        add(placedRows);

        //occupiedRows
        occupiedRows.add(general);
        occupiedRows.add(admiral);
        occupiedRows.add(infantry);
        occupiedRows.add(eliteInf);
        occupiedRows.add(cavalry);
        occupiedRows.add(heavyCav);
        occupiedRows.add(irregularCav);
        occupiedRows.add(artillery);
        occupiedRows.add(horseArt);
        occupiedRows.add(Militia);
        occupiedRows.add(navalSquad);
        add(occupiedRows);

        //finalizeRow
        finalizeRow.add(Box.createHorizontalGlue());
        JButton finalizeButton = new JButton(GameMsg.getString("pp.finalize"));
        finalizeButton.setActionCommand(GameMsg.getString("pp.finalizeAction"));
        finalizeButton.addActionListener(this);
        finalizeRow.add(finalizeButton);
        finalizeRow.setMaximumSize(new Dimension(200, 25));
        add(finalizeRow);
    }

    public void addUnitToRegion(MilitaryUnit unit) {
        unitList = placedMap.remove(controller.getRegion(regionsToPlace.getSelectedItem().toString()));
        if (unitList == null)
            unitList = new ArrayList<MilitaryUnit>();

        if (unit.getType() == MilitaryUnit.ADMIRAL) {
            for (MilitaryUnit u : unitList) {
                if ( ((NavalSquadron)u).getAdmiral() == null ) {
                    ((NavalSquadron)u).setAdmiral((Admiral)unit);
                    break;
                }
            }
        } else
            unitList.add(unit);

        placedMap.put(((Region)regionsToPlace.getSelectedItem()), unitList);
        switch (unit.getType()) {
            case MilitaryUnit.GENERAL: subGeneral.addUnit(unit); break;
            case MilitaryUnit.ADMIRAL: subAdmiral.addUnit(unit); break;
            case MilitaryUnit.INFANTRY: subInfantry.addUnit(unit); break;
            case MilitaryUnit.ELITE_INFANTRY: subEliteInf.addUnit(unit); break;
            case MilitaryUnit.CAVALRY: subCavalry.addUnit(unit); break;
            case MilitaryUnit.HEAVY_CAVALRY: subHeavyCav.addUnit(unit); break;
            case MilitaryUnit.IRREGULAR_CAVALRY: subIrregularCav.addUnit(unit); break;
            case MilitaryUnit.ARTILLERY: subArtillery.addUnit(unit); break;
            case MilitaryUnit.HORSE_ARTILLERY: subHorseArt.addUnit(unit); break;
            case MilitaryUnit.MILITIA: subMilitia.addUnit(unit); break;
            case MilitaryUnit.NAVAL_SQUADRON: subNavalSquad.addUnit(unit); break;
        }
        refresh();
    }

    public void addUnitsToRegion(ArrayList<MilitaryUnit> units) {
        for (MilitaryUnit unit : units)
            addUnitToRegion(unit);
    }

    public void subUnitFromRegion(MilitaryUnit unit) {
        unitList = placedMap.remove(controller.getRegion(regionsToPlace.getSelectedItem().toString()));
        if (unit.getType() == MilitaryUnit.ADMIRAL) {
            for (MilitaryUnit u : unitList) {
                if ( ((NavalSquadron)u).getAdmiral().equals(unit)) {
                    ((NavalSquadron)u).setAdmiral(null);
                    subAdmiral.removeUnit(unit);
                    break;
                }
            }
        } else
            unitList.remove(unit);

        if (!unitList.isEmpty())
            placedMap.put(((Region)regionsToPlace.getSelectedItem()), unitList);
        switch (unit.getType()) {
            case MilitaryUnit.GENERAL: addGeneral.addUnit(unit); break;
            case MilitaryUnit.ADMIRAL: addAdmiral.addUnit(unit); break;
            case MilitaryUnit.INFANTRY: addInfantry.addUnit(unit); break;
            case MilitaryUnit.ELITE_INFANTRY: addEliteInf.addUnit(unit); break;
            case MilitaryUnit.CAVALRY: addCavalry.addUnit(unit); break;
            case MilitaryUnit.HEAVY_CAVALRY: addHeavyCav.addUnit(unit); break;
            case MilitaryUnit.IRREGULAR_CAVALRY: addIrregularCav.addUnit(unit); break;
            case MilitaryUnit.ARTILLERY: addArtillery.addUnit(unit); break;
            case MilitaryUnit.HORSE_ARTILLERY: addHorseArt.addUnit(unit); break;
            case MilitaryUnit.MILITIA: addMilitia.addUnit(unit); break;
            case MilitaryUnit.NAVAL_SQUADRON: addNavalSquad.addUnit(unit); break;
        }
        refresh();
    }

    public void subUnitsFromRegion(ArrayList<MilitaryUnit> units) {
        for (MilitaryUnit unit : units)
            subUnitFromRegion(unit);
    }

    public boolean isUnitPlaceable(int unitType) {
        //make placement controller class todo

        Region region = ((Region)regionsToPlace.getSelectedItem());

        if (region.getType() == Region.LAND_REGION) {
            switch(unitType) {
                case MilitaryUnit.GENERAL:
                case MilitaryUnit.INFANTRY:
                case MilitaryUnit.ELITE_INFANTRY:
                case MilitaryUnit.CAVALRY:
                case MilitaryUnit.HEAVY_CAVALRY:
                case MilitaryUnit.IRREGULAR_CAVALRY:
                case MilitaryUnit.ARTILLERY:
                case MilitaryUnit.HORSE_ARTILLERY:
                case MilitaryUnit.MILITIA:
                case MilitaryUnit.NATIONAL_HERO: return true;
                default: return false;
            }
        } else if (region.getType() == Region.SEA_REGION) {
            //cant place units in a sea region
            //can only move Naval Squadrons into a sea region
            return false;
        } else if (region.getType() == Region.PORT_REGION) {
            switch (unitType) {
                case MilitaryUnit.ADMIRAL:
                    for (MilitaryUnit ns : subNavalSquad.getUnits() )
                        if (((NavalSquadron)ns).getAdmiral() == null)
                            return true;
                    for (MilitaryUnit ns : navalSquad.getUnits() )
                        if (((NavalSquadron)ns).getAdmiral() == null)
                            return true;
                    break;
                case MilitaryUnit.NAVAL_SQUADRON: return true;
                default: return false;
            }
        }
        return false;
    }

    private void initUnplacedCount() {
        unplacedGen = new ArrayList<MilitaryUnit>();
        unplacedAdm = new ArrayList<MilitaryUnit>();
        unplacedInf = new ArrayList<MilitaryUnit>();
        unplacedEInf = new ArrayList<MilitaryUnit>();
        unplacedCav = new ArrayList<MilitaryUnit>();
        unplacedHCav = new ArrayList<MilitaryUnit>();
        unplacedICav = new ArrayList<MilitaryUnit>();
        unplacedArt = new ArrayList<MilitaryUnit>();
        unplacedHArt = new ArrayList<MilitaryUnit>();
        unplacedMil = new ArrayList<MilitaryUnit>();
        unplacedNav = new ArrayList<MilitaryUnit>();
        for (MilitaryUnit u : unplacedUnits) {
            if (u.getType() == MilitaryUnit.GENERAL)
                unplacedGen.add(u);
            else if (u.getType() == MilitaryUnit.ADMIRAL)
                unplacedAdm.add(u);
            else if (u.getType() == MilitaryUnit.INFANTRY)
                unplacedInf.add(u);
            else if (u.getType() == MilitaryUnit.ELITE_INFANTRY)
                unplacedEInf.add(u);
            else if (u.getType() == MilitaryUnit.CAVALRY)
                unplacedCav.add(u);
            else if (u.getType() == MilitaryUnit.HEAVY_CAVALRY)
                unplacedHCav.add(u);
            else if (u.getType() == MilitaryUnit.IRREGULAR_CAVALRY)
                unplacedICav.add(u);
            else if (u.getType() == MilitaryUnit.ARTILLERY)
                unplacedArt.add(u);
            else if (u.getType() == MilitaryUnit.HORSE_ARTILLERY)
                unplacedHArt.add(u);
            else if (u.getType() == MilitaryUnit.MILITIA)
                unplacedMil.add(u);
            else if (u.getType() == MilitaryUnit.NAVAL_SQUADRON)
                unplacedNav.add(u);
        }
    }

    private void setRegion(Region region) {
        //get already placed units
        unitList = placedMap.get(controller.getRegion(region.toString()));

        subGeneral.setUnits(getUnitsByType(MilitaryUnit.GENERAL, unitList));
        subAdmiral.setUnits(getUnitsByType(MilitaryUnit.ADMIRAL, unitList));
        subInfantry.setUnits(getUnitsByType(MilitaryUnit.INFANTRY, unitList));
        subEliteInf.setUnits(getUnitsByType(MilitaryUnit.ELITE_INFANTRY, unitList));
        subCavalry.setUnits(getUnitsByType(MilitaryUnit.CAVALRY, unitList));
        subHeavyCav.setUnits(getUnitsByType(MilitaryUnit.HEAVY_CAVALRY, unitList));
        subIrregularCav.setUnits(getUnitsByType(MilitaryUnit.IRREGULAR_CAVALRY, unitList));
        subArtillery.setUnits(getUnitsByType(MilitaryUnit.ARTILLERY, unitList));
        subHorseArt.setUnits(getUnitsByType(MilitaryUnit.HORSE_ARTILLERY, unitList));
        subMilitia.setUnits(getUnitsByType(MilitaryUnit.MILITIA, unitList));
        subNavalSquad.setUnits(getUnitsByType(MilitaryUnit.NAVAL_SQUADRON, unitList));

        //get units previously occupying
        unitList = controller.getUnitsInRegion(region.toString());
//        unitList = new ArrayList<MilitaryUnit>();

        general.setUnits(getUnitsByType(MilitaryUnit.GENERAL, unitList));
        admiral.setUnits(getUnitsByType(MilitaryUnit.ADMIRAL, unitList));
        infantry.setUnits(getUnitsByType(MilitaryUnit.INFANTRY, unitList));
        eliteInf.setUnits(getUnitsByType(MilitaryUnit.ELITE_INFANTRY, unitList));
        cavalry.setUnits(getUnitsByType(MilitaryUnit.CAVALRY, unitList));
        heavyCav.setUnits(getUnitsByType(MilitaryUnit.HEAVY_CAVALRY, unitList));
        irregularCav.setUnits(getUnitsByType(MilitaryUnit.IRREGULAR_CAVALRY, unitList));
        artillery.setUnits(getUnitsByType(MilitaryUnit.ARTILLERY, unitList));
        horseArt.setUnits(getUnitsByType(MilitaryUnit.HORSE_ARTILLERY, unitList));
        Militia.setUnits(getUnitsByType(MilitaryUnit.MILITIA, unitList));
        navalSquad.setUnits(getUnitsByType(MilitaryUnit.NAVAL_SQUADRON, unitList));

        refresh();
    }

    private ArrayList<MilitaryUnit> getUnitsByType(int type, ArrayList<MilitaryUnit> units) {
        ArrayList<MilitaryUnit> typeUnits = new ArrayList<MilitaryUnit>();

        if (units != null) {
            for (MilitaryUnit u : units) {
                if (u.getType() == type)
                    typeUnits.add(u);
                if (type == MilitaryUnit.ADMIRAL && u.getType() == MilitaryUnit.NAVAL_SQUADRON)
                    if ( ((NavalSquadron)u).getAdmiral() != null )
                        typeUnits.add(((NavalSquadron)u).getAdmiral());
            }
        }
        return typeUnits;
    }

    private boolean allUnitsArePlaced() {
        if (!addGeneral.isEmpty())
            return false;
        else if (!addAdmiral.isEmpty())
            return false;
        else if (!addInfantry.isEmpty())
            return false;
        else if (!addEliteInf.isEmpty())
            return false;
        else if (!addCavalry.isEmpty())
            return false;
        else if (!addHeavyCav.isEmpty())
            return false;
        else if (!addIrregularCav.isEmpty())
            return false;
        else if (!addArtillery.isEmpty())
            return false;
        else if (!addHorseArt.isEmpty())
            return false;
        else if (!addMilitia.isEmpty())
            return false;
        else if (!addNavalSquad.isEmpty())
            return false;

        return true;
    }

    public void refresh() {
        dialog.pack();
        dialog.repaint();
//        getPanelSize();
    }

    //THIS IS JUST FOR SYSTEM OUT... todo remove
    public void getPanelSize() {
        System.out.println("PlacementPanel Size: (" + this.getWidth() + "," + this.getHeight() + ")" );
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JComboBox) {
            JComboBox cb = (JComboBox)e.getSource();
            if (cb.getSelectedItem() instanceof Region) {
                Region region = (Region)cb.getSelectedItem();
                setRegion(region);
            }
        }

        if (e.getActionCommand().equals(GameMsg.getString("pp.finalizeAction"))) {
            System.out.println("FINALIZE ACTION CLICKED");
            if (allUnitsArePlaced()) {
                if (controller.isTurnToPlaceUnits(placingNation)) {
                    Object[] options = {GameMsg.getString("pp.yes"), GameMsg.getString("pp.no")};
                    int choice = JOptionPane.showOptionDialog(display.getMap(), GameMsg.getString("pp.question"),
                                                       GameMsg.getString("start.title"),
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

                    if ( choice == JOptionPane.YES_OPTION ) {
                        System.out.println("YES OPTION CLICKED");
                        controller.processUnitPlacement(placedMap);
                        dialog.dispose();
                    }
                } else {
                    //Please wait your turn to place units.  This should only happen when !blindSetup
                    MessageDialog.displayMessage(display.getMap(), GameMsg.getString("pp.waitTurn"));
                }
            } else
                JOptionPane.showMessageDialog(display.getMap(), GameMsg.getString("pp.unitsNotPlaced"), GameMsg.getString("pp.notPlacedTitle"), JOptionPane.ERROR_MESSAGE);
        }
    }

    private GameController controller;
    private DisplayController display;
    private JDialog dialog;
    private int placingNation;

    private ArrayList<MilitaryUnit> unplacedUnits;
    private ArrayList<Region> placeableRegions;

    //Region Name has set of Integers
    private HashMap<Region, ArrayList<MilitaryUnit>> placedMap;
    private ArrayList<MilitaryUnit> unitList;

    private JPanel headerRow;
    private JPanel unplacedRows;
    private JPanel placedRows;
    private JPanel occupiedRows;
    private JPanel finalizeRow;

    private JComboBox regionsToPlace;

    private ArrayList<MilitaryUnit> unplacedGen;
    private ArrayList<MilitaryUnit> unplacedAdm;
    private ArrayList<MilitaryUnit> unplacedInf;
    private ArrayList<MilitaryUnit> unplacedEInf;
    private ArrayList<MilitaryUnit> unplacedCav;
    private ArrayList<MilitaryUnit> unplacedHCav;
    private ArrayList<MilitaryUnit> unplacedICav;
    private ArrayList<MilitaryUnit> unplacedArt;
    private ArrayList<MilitaryUnit> unplacedHArt;
    private ArrayList<MilitaryUnit> unplacedMil;
    private ArrayList<MilitaryUnit> unplacedNav;

    private AddUnitPanel addGeneral;
    private AddUnitPanel addAdmiral;
    private AddUnitPanel addInfantry;
    private AddUnitPanel addEliteInf;
    private AddUnitPanel addCavalry;
    private AddUnitPanel addHeavyCav;
    private AddUnitPanel addIrregularCav;
    private AddUnitPanel addArtillery;
    private AddUnitPanel addHorseArt;
    private AddUnitPanel addMilitia;
    private AddUnitPanel addNavalSquad;

    private SubUnitPanel subGeneral;
    private SubUnitPanel subAdmiral;
    private SubUnitPanel subInfantry;
    private SubUnitPanel subEliteInf;
    private SubUnitPanel subCavalry;
    private SubUnitPanel subHeavyCav;
    private SubUnitPanel subIrregularCav;
    private SubUnitPanel subArtillery;
    private SubUnitPanel subHorseArt;
    private SubUnitPanel subMilitia;
    private SubUnitPanel subNavalSquad;

    private UnitPanel general;
    private UnitPanel admiral;
    private UnitPanel infantry;
    private UnitPanel eliteInf;
    private UnitPanel cavalry;
    private UnitPanel heavyCav;
    private UnitPanel irregularCav;
    private UnitPanel artillery;
    private UnitPanel horseArt;
    private UnitPanel Militia;
    private UnitPanel navalSquad;
}