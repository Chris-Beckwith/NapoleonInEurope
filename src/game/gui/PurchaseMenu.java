package game.gui;

import game.controller.GameController;
import game.controller.NationInstance;
import game.controller.Unit.MilitaryUnit;
import game.util.GameMsg;
import game.gui.listeners.UndecoratedMouseListener;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;

import lobby.controller.Nation;

/**
 * PurchaseMenu.java  Date Created: Oct 11, 2013
 *
 * Purpose: To display how many production points (pp) a nation has, what they can purchase
 * and what they will have remaining after their purchase
 *
 * Description:
 *
 *   --- Nation Purchase Window -------------
 *  |                                        |
 *  |  Production Points Available: 21       |
 *  |                                        |
 *  |  Purchasable Units:                    |
 *  |    Infantry (Cost: 6)   x[ 1 ] [-][+]  |
 *  |    Artillery (Cost: 10) x[ 1 ] [-][+]  |
 *  |    ~~~~~~~~~ ~~~~~~~~~  ~~~~~~  ~~~~   |
 *  |    ~~~~~~~~~ ~~~~~~~~~  ~~~~~~  ~~~~   |
 *  |    ~~~~~~~~~ ~~~~~~~~~  ~~~~~~  ~~~~   |
 *  |                                        |
 *  |  Buy PAPs (Cost: 10)    x[ 0 ] [-][+]  |
 *  |                                        |
 *  |  Production Points Remaining: 5        |
 *  |                                        |
 *  |     [ Finalize ]      [ Reset ]        |
 *  |________________________________________|
 *
 * @author Chrisb
 */
public class PurchaseMenu extends JDialog implements KeyListener {

    public PurchaseMenu(GameController controller, NationInstance nation, boolean hasBeenControlled) {
        super();
        this.controller = controller;
        this.nation = nation;
        this.hasBeenControlled = hasBeenControlled;
        UndecoratedMouseListener listener = new UndecoratedMouseListener(this);
        addMouseListener(listener);
        addMouseMotionListener(listener);
        addKeyListener(this);
        setUndecorated(true);
        setVisible(true);
        setResizable(false);
        setAlwaysOnTop(true);
        setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setContentPane(new PurchasePanel());
        setTitle(GameMsg.getString("nation.pos." + nation.getNationNumber()) + " " + GameMsg.getString("bp.title"));
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            //please buy your units.
            JOptionPane.showMessageDialog(this, "Please finalize your unit purchases.", "Purchase Units", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    private GameController controller;
    private NationInstance nation;
    private boolean hasBeenControlled;

    private class PurchasePanel extends JPanel implements MouseListener, ActionListener {
        private PurchasePanel() {
            setBackground(Color.GRAY);
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            setBorder( new CompoundBorder((new CompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4), BorderFactory.createEmptyBorder(5,5,5,5))),
                new CompoundBorder(new TitledBorder(GameMsg.getString("nation.pos." + nation.getNationNumber()) + " " + GameMsg.getString("bp.title")), BorderFactory.createEmptyBorder(0,5,5,5))) );

            plannedPurchases = new ArrayList<Integer>();

            createComponents();
            layoutComponents();
            updateButtons();
        }

        private void createComponents() {
            String costText = " (Cost: ";
            Font plusMinusFont = new Font("Comic Sans MS", Font.PLAIN, 16);
            pointsAvailableLabel = new JLabel(GameMsg.getString("bp.pointsAvailable"));
            pointsAvailable = new JLabel(String.valueOf(nation.getProductionPoints()));

            purchasableUnits = new JLabel(GameMsg.getString("bp.purchasableUnits"));

            generalLabel = new JLabel(MilitaryUnit.getUnitName(MilitaryUnit.GENERAL) + costText
                    + MilitaryUnit.getCost(MilitaryUnit.GENERAL, nation.getNationNumber()) + ")");
            generalCountField = new JTextField("0",2);
            generalCountField.setEnabled(false);
            generalCountField.setDisabledTextColor(Color.BLACK);
            generalCountField.setOpaque(false);
            generalCountField.setHorizontalAlignment(JTextField.CENTER);
            generalCountField.setMaximumSize(new Dimension(20,20));
            generalMinusButton = new JLabel("[ - ]");
            generalPlusButton = new JLabel("[ + ]");
            generalMinusButton.setFont(plusMinusFont);
            generalPlusButton.setFont(plusMinusFont);
            generalPlusButton.addMouseListener(this);
            generalMinusButton.addMouseListener(this);

            infantryLabel = new JLabel(MilitaryUnit.getUnitName(MilitaryUnit.INFANTRY) + costText
                    + MilitaryUnit.getCost(MilitaryUnit.INFANTRY, nation.getNationNumber()) + ")");
            infantryCountField = new JTextField("0",2);
            infantryCountField.setEnabled(false);
            infantryCountField.setDisabledTextColor(Color.BLACK);
            infantryCountField.setOpaque(false);
            infantryCountField.setHorizontalAlignment(JTextField.CENTER);
            infantryCountField.setMaximumSize(new Dimension(20,20));
            infantryMinusButton = new JLabel("[ - ]");
            infantryPlusButton = new JLabel("[ + ]");
            infantryMinusButton.setFont(plusMinusFont);
            infantryPlusButton.setFont(plusMinusFont);
            infantryPlusButton.addMouseListener(this);
            infantryMinusButton.addMouseListener(this);

            eInfantryLabel = new JLabel(MilitaryUnit.getUnitName(MilitaryUnit.ELITE_INFANTRY) + costText
                    + MilitaryUnit.getCost(MilitaryUnit.ELITE_INFANTRY, nation.getNationNumber()) + ")");
            eInfantryCountField = new JTextField("0",2);
            eInfantryCountField.setEnabled(false);
            eInfantryCountField.setDisabledTextColor(Color.BLACK);
            eInfantryCountField.setOpaque(false);
            eInfantryCountField.setHorizontalAlignment(JTextField.CENTER);
            eInfantryCountField.setMaximumSize(new Dimension(20,20));
            eInfantryMinusButton = new JLabel("[ - ]");
            eInfantryPlusButton = new JLabel("[ + ]");
            eInfantryMinusButton.setFont(plusMinusFont);
            eInfantryPlusButton.setFont(plusMinusFont);
            eInfantryPlusButton.addMouseListener(this);
            eInfantryMinusButton.addMouseListener(this);

            cavalryLabel = new JLabel(MilitaryUnit.getUnitName(MilitaryUnit.CAVALRY) + costText
                    + MilitaryUnit.getCost(MilitaryUnit.CAVALRY, nation.getNationNumber()) + ")");
            cavalryCountField = new JTextField("0",2);
            cavalryCountField.setEnabled(false);
            cavalryCountField.setDisabledTextColor(Color.BLACK);
            cavalryCountField.setOpaque(false);
            cavalryCountField.setHorizontalAlignment(JTextField.CENTER);
            cavalryCountField.setMaximumSize(new Dimension(20,20));
            cavalryMinusButton = new JLabel("[ - ]");
            cavalryPlusButton = new JLabel("[ + ]");
            cavalryMinusButton.setFont(plusMinusFont);
            cavalryPlusButton.setFont(plusMinusFont);
            cavalryPlusButton.addMouseListener(this);
            cavalryMinusButton.addMouseListener(this);

            hCavalryLabel = new JLabel(MilitaryUnit.getUnitName(MilitaryUnit.HEAVY_CAVALRY) + costText
                    + MilitaryUnit.getCost(MilitaryUnit.HEAVY_CAVALRY, nation.getNationNumber()) + ")");
            hCavalryCountField = new JTextField("0",2);
            hCavalryCountField.setEnabled(false);
            hCavalryCountField.setDisabledTextColor(Color.BLACK);
            hCavalryCountField.setOpaque(false);
            hCavalryCountField.setHorizontalAlignment(JTextField.CENTER);
            hCavalryCountField.setMaximumSize(new Dimension(20,20));
            hCavalryMinusButton = new JLabel("[ - ]");
            hCavalryPlusButton = new JLabel("[ + ]");
            hCavalryMinusButton.setFont(plusMinusFont);
            hCavalryPlusButton.setFont(plusMinusFont);
            hCavalryPlusButton.addMouseListener(this);
            hCavalryMinusButton.addMouseListener(this);

            iCavalryLabel = new JLabel(MilitaryUnit.getUnitName(MilitaryUnit.IRREGULAR_CAVALRY) + costText
                    + MilitaryUnit.getCost(MilitaryUnit.IRREGULAR_CAVALRY, nation.getNationNumber()) + ")");
            iCavalryCountField = new JTextField("0",2);
            iCavalryCountField.setEnabled(false);
            iCavalryCountField.setDisabledTextColor(Color.BLACK);
            iCavalryCountField.setOpaque(false);
            iCavalryCountField.setHorizontalAlignment(JTextField.CENTER);
            iCavalryCountField.setMaximumSize(new Dimension(20,20));
            iCavalryMinusButton = new JLabel("[ - ]");
            iCavalryPlusButton = new JLabel("[ + ]");
            iCavalryMinusButton.setFont(plusMinusFont);
            iCavalryPlusButton.setFont(plusMinusFont);
            iCavalryPlusButton.addMouseListener(this);
            iCavalryMinusButton.addMouseListener(this);

            artilleryLabel = new JLabel(MilitaryUnit.getUnitName(MilitaryUnit.ARTILLERY) + costText
                    + MilitaryUnit.getCost(MilitaryUnit.ARTILLERY, nation.getNationNumber()) + ")");
            artilleryCountField = new JTextField("0",2);
            artilleryCountField.setEnabled(false);
            artilleryCountField.setDisabledTextColor(Color.BLACK);
            artilleryCountField.setOpaque(false);
            artilleryCountField.setHorizontalAlignment(JTextField.CENTER);
            artilleryCountField.setMaximumSize(new Dimension(20,20));
            artilleryMinusButton = new JLabel("[ - ]");
            artilleryPlusButton = new JLabel("[ + ]");
            artilleryMinusButton.setFont(plusMinusFont);
            artilleryPlusButton.setFont(plusMinusFont);
            artilleryPlusButton.addMouseListener(this);
            artilleryMinusButton.addMouseListener(this);

            hArtilleryLabel = new JLabel(MilitaryUnit.getUnitName(MilitaryUnit.HEAVY_CAVALRY) + costText
                    + MilitaryUnit.getCost(MilitaryUnit.HEAVY_CAVALRY, nation.getNationNumber()) + ")");
            hArtilleryCountField = new JTextField("0",2);
            hArtilleryCountField.setEnabled(false);
            hArtilleryCountField.setDisabledTextColor(Color.BLACK);
            hArtilleryCountField.setOpaque(false);
            hArtilleryCountField.setHorizontalAlignment(JTextField.CENTER);
            hArtilleryCountField.setMaximumSize(new Dimension(20,20));
            hArtilleryMinusButton = new JLabel("[ - ]");
            hArtilleryPlusButton = new JLabel("[ + ]");
            hArtilleryMinusButton.setFont(plusMinusFont);
            hArtilleryPlusButton.setFont(plusMinusFont);
            hArtilleryPlusButton.addMouseListener(this);
            hArtilleryMinusButton.addMouseListener(this);

            militiaLabel = new JLabel(MilitaryUnit.getUnitName(MilitaryUnit.MILITIA) + costText
                    + MilitaryUnit.getCost(MilitaryUnit.MILITIA, nation.getNationNumber()) + ")");
            militiaCountField = new JTextField("0",2);
            militiaCountField.setEnabled(false);
            militiaCountField.setDisabledTextColor(Color.BLACK);
            militiaCountField.setOpaque(false);
            militiaCountField.setHorizontalAlignment(JTextField.CENTER);
            militiaCountField.setMaximumSize(new Dimension(20,20));
            militiaMinusButton = new JLabel("[ - ]");
            militiaPlusButton = new JLabel("[ + ]");
            militiaMinusButton.setFont(plusMinusFont);
            militiaPlusButton.setFont(plusMinusFont);
            militiaPlusButton.addMouseListener(this);
            militiaMinusButton.addMouseListener(this);

            admiralLabel = new JLabel(MilitaryUnit.getUnitName(MilitaryUnit.ADMIRAL) + costText
                    + MilitaryUnit.getCost(MilitaryUnit.ADMIRAL, nation.getNationNumber()) + ")");
            admiralCountField = new JTextField("0",2);
            admiralCountField.setEnabled(false);
            admiralCountField.setDisabledTextColor(Color.BLACK);
            admiralCountField.setOpaque(false);
            admiralCountField.setHorizontalAlignment(JTextField.CENTER);
            admiralCountField.setMaximumSize(new Dimension(20,20));
            admiralMinusButton = new JLabel("[ - ]");
            admiralPlusButton = new JLabel("[ + ]");
            admiralMinusButton.setFont(plusMinusFont);
            admiralPlusButton.setFont(plusMinusFont);
            admiralPlusButton.addMouseListener(this);
            admiralMinusButton.addMouseListener(this);

            navalSquadLabel = new JLabel(MilitaryUnit.getUnitName(MilitaryUnit.NAVAL_SQUADRON) + costText
                    + MilitaryUnit.getCost(MilitaryUnit.NAVAL_SQUADRON, nation.getNationNumber()) + ")");
            navalSquadCountField = new JTextField("0",2);
            navalSquadCountField.setEnabled(false);
            navalSquadCountField.setDisabledTextColor(Color.BLACK);
            navalSquadCountField.setOpaque(false);
            navalSquadCountField.setHorizontalAlignment(JTextField.CENTER);
            navalSquadCountField.setMaximumSize(new Dimension(20,20));
            navalSquadMinusButton = new JLabel("[ - ]");
            navalSquadPlusButton = new JLabel("[ + ]");
            navalSquadMinusButton.setFont(plusMinusFont);
            navalSquadPlusButton.setFont(plusMinusFont);
            navalSquadPlusButton.addMouseListener(this);
            navalSquadMinusButton.addMouseListener(this);

//            nationalHeroLabel = new JLabel(MilitaryUnit.getUnitName(MilitaryUnit.NATIONAL_HERO) + costText
//                    + MilitaryUnit.getCost(MilitaryUnit.NATIONAL_HERO, nation.getNationNumber()) + ")");
//            nationalHeroCountField = new JTextField("0",2);
//            nationalHeroCountField.setEnabled(false);
//            nationalHeroCountField.setDisabledTextColor(Color.BLACK);
//            nationalHeroCountField.setOpaque(false);
//            nationalHeroCountField.setHorizontalAlignment(JTextField.CENTER);
//            nationalHeroCountField.setMaximumSize(new Dimension(20,20));
//            nationalHeroMinusButton = new JLabel("[ - ]");
//            nationalHeroPlusButton = new JLabel("[ + ]");
//            nationalHeroMinusButton.setFont(plusMinusFont);
//            nationalHeroPlusButton.setFont(plusMinusFont);
//            nationalHeroPlusButton.addMouseListener(this);
//            nationalHeroMinusButton.addMouseListener(this);

            PAPLabel = new JLabel(GameMsg.getString("bp.papLabel") + costText
                    + MilitaryUnit.getCost(MilitaryUnit.PAP, nation.getNationNumber()) + ")");
            PAPCountField = new JTextField("0",2);
            PAPCountField.setEnabled(false);
            PAPCountField.setDisabledTextColor(Color.BLACK);
            PAPCountField.setOpaque(false);
            PAPCountField.setHorizontalAlignment(JTextField.CENTER);
            PAPCountField.setMaximumSize(new Dimension(20,20));
            PAPMinusButton = new JLabel("[ - ]");
            PAPPlusButton = new JLabel("[ + ]");
            PAPMinusButton.setFont(plusMinusFont);
            PAPPlusButton.setFont(plusMinusFont);

            pointsRemainingLabel = new JLabel(GameMsg.getString("bp.pointsRemaining"));
            pointsRemaining = new JLabel(String.valueOf(nation.getProductionPoints()));

            finalizeButton = new JButton(GameMsg.getString("bp.finalize"));
            finalizeButton.setActionCommand(GameMsg.getString("bp.final.action"));
            finalizeButton.addActionListener(this);
            resetButton = new JButton(GameMsg.getString("bp.reset"));
            resetButton.setActionCommand(GameMsg.getString("bp.reset.action"));
            resetButton.addActionListener(this);
        }

        private void layoutComponents() {
            int labelSpace = 4;
            int minusGap = 4;
            int colGap = 25;
            int countGap = 10;
            int indent = 15;
            int rowGap = 5;
            int rowLargeGap = 15;

            JPanel ppAvailRow = new JPanel();
            ppAvailRow.setOpaque(false);
            ppAvailRow.setLayout(new BoxLayout(ppAvailRow, BoxLayout.LINE_AXIS));
            ppAvailRow.add(pointsAvailableLabel);
            ppAvailRow.add(Box.createHorizontalStrut(labelSpace));
            ppAvailRow.add(pointsAvailable);
            ppAvailRow.add(Box.createHorizontalGlue());

            add(ppAvailRow);
            add(Box.createVerticalStrut(rowLargeGap));

            JPanel pUnitsRow = new JPanel();
            pUnitsRow.setOpaque(false);
            pUnitsRow.setLayout(new BoxLayout(pUnitsRow, BoxLayout.LINE_AXIS));
            pUnitsRow.add(purchasableUnits);
            pUnitsRow.add(Box.createHorizontalGlue());

            add(pUnitsRow);
            add(Box.createVerticalStrut(rowGap));

            //Everyone
            JPanel generalRow = new JPanel();
            generalRow.setOpaque(false);
            generalRow.setLayout(new BoxLayout(generalRow, BoxLayout.LINE_AXIS));
            generalRow.add(Box.createHorizontalStrut(indent));
            generalRow.add(generalLabel);
            generalRow.add(Box.createHorizontalStrut(colGap));
            generalRow.add(Box.createHorizontalGlue());
            generalRow.add(generalCountField);
            generalRow.add(Box.createHorizontalStrut(countGap));
            generalRow.add(generalMinusButton);
            generalRow.add(Box.createHorizontalStrut(minusGap));
            generalRow.add(generalPlusButton);

            add(generalRow);

            //Everyone except Ottoman
            if (nation.getNationNumber() != Nation.OTTOMANS) {
                JPanel infantryRow = new JPanel();
                infantryRow.setOpaque(false);
                infantryRow.setLayout(new BoxLayout(infantryRow, BoxLayout.LINE_AXIS));
                infantryRow.add(Box.createHorizontalStrut(indent));
                infantryRow.add(infantryLabel);
                infantryRow.add(Box.createHorizontalStrut(colGap));
                infantryRow.add(Box.createHorizontalGlue());
                infantryRow.add(infantryCountField);
                infantryRow.add(Box.createHorizontalStrut(countGap));
                infantryRow.add(infantryMinusButton);
                infantryRow.add(Box.createHorizontalStrut(minusGap));
                infantryRow.add(infantryPlusButton);

                add(infantryRow);
            }

            //Everyone except Ottoman and Spain
            if (nation.getNationNumber() != Nation.OTTOMANS && nation.getNationNumber() != Nation.SPAIN) {
                JPanel eInfantryRow = new JPanel();
                eInfantryRow.setOpaque(false);
                eInfantryRow.setLayout(new BoxLayout(eInfantryRow, BoxLayout.LINE_AXIS));
                eInfantryRow.add(Box.createHorizontalStrut(indent));
                eInfantryRow.add(eInfantryLabel);
                eInfantryRow.add(Box.createHorizontalStrut(colGap));
                eInfantryRow.add(Box.createHorizontalGlue());
                eInfantryRow.add(eInfantryCountField);
                eInfantryRow.add(Box.createHorizontalStrut(countGap));
                eInfantryRow.add(eInfantryMinusButton);
                eInfantryRow.add(Box.createHorizontalStrut(minusGap));
                eInfantryRow.add(eInfantryPlusButton);

                add(eInfantryRow);
            }

            //Everyone except Ottoman
            if (nation.getNationNumber() != Nation.OTTOMANS) {
                JPanel cavalryRow = new JPanel();
                cavalryRow.setOpaque(false);
                cavalryRow.setLayout(new BoxLayout(cavalryRow, BoxLayout.LINE_AXIS));
                cavalryRow.add(Box.createHorizontalStrut(indent));
                cavalryRow.add(cavalryLabel);
                cavalryRow.add(Box.createHorizontalStrut(colGap));
                cavalryRow.add(Box.createHorizontalGlue());
                cavalryRow.add(cavalryCountField);
                cavalryRow.add(Box.createHorizontalStrut(countGap));
                cavalryRow.add(cavalryMinusButton);
                cavalryRow.add(Box.createHorizontalStrut(minusGap));
                cavalryRow.add(cavalryPlusButton);

                add(cavalryRow);
            }

            //Everyone except Ottoman and Spain
            if (nation.getNationNumber() != Nation.OTTOMANS && nation.getNationNumber() != Nation.SPAIN) {
                JPanel hCavalryRow = new JPanel();
                hCavalryRow.setOpaque(false);
                hCavalryRow.setLayout(new BoxLayout(hCavalryRow, BoxLayout.LINE_AXIS));
                hCavalryRow.add(Box.createHorizontalStrut(indent));
                hCavalryRow.add(hCavalryLabel);
                hCavalryRow.add(Box.createHorizontalStrut(colGap));
                hCavalryRow.add(Box.createHorizontalGlue());
                hCavalryRow.add(hCavalryCountField);
                hCavalryRow.add(Box.createHorizontalStrut(countGap));
                hCavalryRow.add(hCavalryMinusButton);
                hCavalryRow.add(Box.createHorizontalStrut(minusGap));
                hCavalryRow.add(hCavalryPlusButton);

                add(hCavalryRow);
            }

            //Only Russia and Ottoman
            if (nation.getNationNumber() == Nation.RUSSIA || nation.getNationNumber() == Nation.OTTOMANS) {
                JPanel iCavalryRow = new JPanel();
                iCavalryRow.setOpaque(false);
                iCavalryRow.setLayout(new BoxLayout(iCavalryRow, BoxLayout.LINE_AXIS));
                iCavalryRow.add(Box.createHorizontalStrut(indent));
                iCavalryRow.add(iCavalryLabel);
                iCavalryRow.add(Box.createHorizontalStrut(colGap));
                iCavalryRow.add(Box.createHorizontalGlue());
                iCavalryRow.add(iCavalryCountField);
                iCavalryRow.add(Box.createHorizontalStrut(countGap));
                iCavalryRow.add(iCavalryMinusButton);
                iCavalryRow.add(Box.createHorizontalStrut(minusGap));
                iCavalryRow.add(iCavalryPlusButton);

                add(iCavalryRow);
            }

            //Everyone
            JPanel artilleryRow = new JPanel();
            artilleryRow.setOpaque(false);
            artilleryRow.setLayout(new BoxLayout(artilleryRow, BoxLayout.LINE_AXIS));
            artilleryRow.add(Box.createHorizontalStrut(indent));
            artilleryRow.add(artilleryLabel);
            artilleryRow.add(Box.createHorizontalStrut(colGap));
            artilleryRow.add(Box.createHorizontalGlue());
            artilleryRow.add(artilleryCountField);
            artilleryRow.add(Box.createHorizontalStrut(countGap));
            artilleryRow.add(artilleryMinusButton);
            artilleryRow.add(Box.createHorizontalStrut(minusGap));
            artilleryRow.add(artilleryPlusButton);

            add(artilleryRow);

            //Everyone except Ottoman
            if (nation.getNationNumber() != Nation.OTTOMANS) {
                JPanel hArtilleryRow = new JPanel();
                hArtilleryRow.setOpaque(false);
                hArtilleryRow.setLayout(new BoxLayout(hArtilleryRow, BoxLayout.LINE_AXIS));
                hArtilleryRow.add(Box.createHorizontalStrut(indent));
                hArtilleryRow.add(hArtilleryLabel);
                hArtilleryRow.add(Box.createHorizontalStrut(colGap));
                hArtilleryRow.add(Box.createHorizontalGlue());
                hArtilleryRow.add(hArtilleryCountField);
                hArtilleryRow.add(Box.createHorizontalStrut(countGap));
                hArtilleryRow.add(hArtilleryMinusButton);
                hArtilleryRow.add(Box.createHorizontalStrut(minusGap));
                hArtilleryRow.add(hArtilleryPlusButton);

                add(hArtilleryRow);
            }


            //Only Ottoman at any time and Russia after 1812.
            if (nation.getNationNumber() == Nation.OTTOMANS || (nation.getNationNumber() == Nation.RUSSIA && controller.game.getYear() > 1812) ) {
                JPanel militiaRow = new JPanel();
                militiaRow.setOpaque(false);
                militiaRow.setLayout(new BoxLayout(militiaRow, BoxLayout.LINE_AXIS));
                militiaRow.add(Box.createHorizontalStrut(indent));
                militiaRow.add(militiaLabel);
                militiaRow.add(Box.createHorizontalStrut(colGap));
                militiaRow.add(Box.createHorizontalGlue());
                militiaRow.add(militiaCountField);
                militiaRow.add(Box.createHorizontalStrut(countGap));
                militiaRow.add(militiaMinusButton);
                militiaRow.add(Box.createHorizontalStrut(minusGap));
                militiaRow.add(militiaPlusButton);

                add(militiaRow);
            }

            //Everyone
            JPanel admiralRow = new JPanel();
            admiralRow.setOpaque(false);
            admiralRow.setLayout(new BoxLayout(admiralRow, BoxLayout.LINE_AXIS));
            admiralRow.add(Box.createHorizontalStrut(indent));
            admiralRow.add(admiralLabel);
            admiralRow.add(Box.createHorizontalStrut(colGap));
            admiralRow.add(Box.createHorizontalGlue());
            admiralRow.add(admiralCountField);
            admiralRow.add(Box.createHorizontalStrut(countGap));
            admiralRow.add(admiralMinusButton);
            admiralRow.add(Box.createHorizontalStrut(minusGap));
            admiralRow.add(admiralPlusButton);

            add(admiralRow);

            //Everyone
            JPanel navalSquadRow = new JPanel();
            navalSquadRow.setOpaque(false);
            navalSquadRow.setLayout(new BoxLayout(navalSquadRow, BoxLayout.LINE_AXIS));
            navalSquadRow.add(Box.createHorizontalStrut(indent));
            navalSquadRow.add(navalSquadLabel);
            navalSquadRow.add(Box.createHorizontalStrut(colGap));
            navalSquadRow.add(Box.createHorizontalGlue());
            navalSquadRow.add(navalSquadCountField);
            navalSquadRow.add(Box.createHorizontalStrut(countGap));
            navalSquadRow.add(navalSquadMinusButton);
            navalSquadRow.add(Box.createHorizontalStrut(minusGap));
            navalSquadRow.add(navalSquadPlusButton);

            add(navalSquadRow);

            //Based on Scenario
//            JPanel nationalHeroRow = new JPanel();
//            nationalHeroRow.setOpaque(false);
//            nationalHeroRow.setLayout(new BoxLayout(nationalHeroRow, BoxLayout.LINE_AXIS));
//            nationalHeroRow.add(Box.createHorizontalStrut(indent));
//            nationalHeroRow.add(nationalHeroLabel);
//            nationalHeroRow.add(Box.createHorizontalStrut(colGap));
//            nationalHeroRow.add(Box.createHorizontalGlue());
//            nationalHeroRow.add(nationalHeroCountField);
//            nationalHeroRow.add(Box.createHorizontalStrut(countGap));
//            nationalHeroRow.add(nationalHeroMinusButton);
//            nationalHeroRow.add(Box.createHorizontalStrut(minusGap));
//            nationalHeroRow.add(nationalHeroPlusButton);
//
//            add(nationalHeroRow);

            JPanel papRow = new JPanel();
            papRow.setOpaque(false);
            papRow.setLayout(new BoxLayout(papRow, BoxLayout.LINE_AXIS));
            papRow.add(PAPLabel);
            papRow.add(Box.createHorizontalStrut(indent));
            papRow.add(Box.createHorizontalStrut(colGap));
            papRow.add(Box.createHorizontalGlue());
            papRow.add(PAPCountField);
            papRow.add(Box.createHorizontalStrut(countGap));
            papRow.add(PAPMinusButton);
            papRow.add(Box.createHorizontalStrut(minusGap));
            papRow.add(PAPPlusButton);

            add(Box.createVerticalStrut(rowGap));
            add(Box.createVerticalStrut(rowGap));
            add(papRow);
            add(Box.createVerticalStrut(rowLargeGap));

            JPanel ppRemainRow = new JPanel();
            ppRemainRow.setOpaque(false);
            ppRemainRow.setLayout(new BoxLayout(ppRemainRow, BoxLayout.LINE_AXIS));
            ppRemainRow.add(pointsRemainingLabel);
            ppRemainRow.add(Box.createHorizontalStrut(labelSpace));
            ppRemainRow.add(pointsRemaining);
            ppRemainRow.add(Box.createHorizontalGlue());

            add(ppRemainRow);

            JPanel finalizeRow = new JPanel();
            finalizeRow.setOpaque(false);
            finalizeRow.setLayout(new BoxLayout(finalizeRow, BoxLayout.LINE_AXIS));
            finalizeRow.add(Box.createHorizontalGlue());
            finalizeRow.add(finalizeButton);
            finalizeRow.add(Box.createHorizontalGlue());
            finalizeRow.add(resetButton);
            finalizeRow.add(Box.createHorizontalGlue());

            add(Box.createVerticalStrut(rowLargeGap));
            add(finalizeRow);
        }

        private void updateButtons() {
            int year = controller.game.getYear();
            boolean canPurchaseUnit = nation.canPurchaseUnit(MilitaryUnit.GENERAL, plannedPurchases, year);
            generalLabel.setEnabled(canPurchaseUnit);
            generalMinusButton.setEnabled(canPurchaseUnit);
            generalPlusButton.setEnabled(canPurchaseUnit);

            canPurchaseUnit = nation.canPurchaseUnit(MilitaryUnit.INFANTRY, plannedPurchases, year);
            infantryLabel.setEnabled(canPurchaseUnit);
            infantryMinusButton.setEnabled(canPurchaseUnit);
            infantryPlusButton.setEnabled(canPurchaseUnit);

            canPurchaseUnit = nation.canPurchaseUnit(MilitaryUnit.ELITE_INFANTRY, plannedPurchases, year);
            eInfantryLabel.setEnabled(canPurchaseUnit);
            eInfantryMinusButton.setEnabled(canPurchaseUnit);
            eInfantryPlusButton.setEnabled(canPurchaseUnit);

            canPurchaseUnit = nation.canPurchaseUnit(MilitaryUnit.CAVALRY, plannedPurchases, year);
            cavalryLabel.setEnabled(canPurchaseUnit);
            cavalryMinusButton.setEnabled(canPurchaseUnit);
            cavalryPlusButton.setEnabled(canPurchaseUnit);

            canPurchaseUnit = nation.canPurchaseUnit(MilitaryUnit.HEAVY_CAVALRY, plannedPurchases, year);
            hCavalryLabel.setEnabled(canPurchaseUnit);
            hCavalryMinusButton.setEnabled(canPurchaseUnit);
            hCavalryPlusButton.setEnabled(canPurchaseUnit);

            canPurchaseUnit = nation.canPurchaseUnit(MilitaryUnit.IRREGULAR_CAVALRY, plannedPurchases, year);
            iCavalryLabel.setEnabled(canPurchaseUnit);
            iCavalryMinusButton.setEnabled(canPurchaseUnit);
            iCavalryPlusButton.setEnabled(canPurchaseUnit);

            canPurchaseUnit = nation.canPurchaseUnit(MilitaryUnit.ARTILLERY, plannedPurchases, year);
            artilleryLabel.setEnabled(canPurchaseUnit);
            artilleryMinusButton.setEnabled(canPurchaseUnit);
            artilleryPlusButton.setEnabled(canPurchaseUnit);

            canPurchaseUnit = nation.canPurchaseUnit(MilitaryUnit.HORSE_ARTILLERY, plannedPurchases, year);
            hArtilleryLabel.setEnabled(canPurchaseUnit);
            hArtilleryMinusButton.setEnabled(canPurchaseUnit);
            hArtilleryPlusButton.setEnabled(canPurchaseUnit);

            canPurchaseUnit = nation.canPurchaseUnit(MilitaryUnit.MILITIA, plannedPurchases, year);
            militiaLabel.setEnabled(canPurchaseUnit);
            militiaMinusButton.setEnabled(canPurchaseUnit);
            militiaPlusButton.setEnabled(canPurchaseUnit);

            canPurchaseUnit = nation.canPurchaseUnit(MilitaryUnit.ADMIRAL, plannedPurchases, year);
            admiralLabel.setEnabled(canPurchaseUnit);
            admiralMinusButton.setEnabled(canPurchaseUnit);
            admiralPlusButton.setEnabled(canPurchaseUnit);

            canPurchaseUnit = nation.canPurchaseUnit(MilitaryUnit.NAVAL_SQUADRON, plannedPurchases, year);
            navalSquadLabel.setEnabled(canPurchaseUnit);
            navalSquadMinusButton.setEnabled(canPurchaseUnit);
            navalSquadPlusButton.setEnabled(canPurchaseUnit);

//            canPurchaseUnit = nation.canPurchaseUnit(MilitaryUnit.NATIONAL_HERO, plannedPurchases, year);
//            nationalHeroLabel.setEnabled(canPurchaseUnit);
//            nationalHeroMinusButton.setEnabled(canPurchaseUnit);
//            nationalHeroPlusButton.setEnabled(canPurchaseUnit);

            canPurchaseUnit = nation.canPurchaseUnit(MilitaryUnit.PAP, plannedPurchases, year);
            PAPLabel.setEnabled(canPurchaseUnit);
            PAPMinusButton.setEnabled(canPurchaseUnit);
            PAPPlusButton.setEnabled(canPurchaseUnit);

            //Disable Minus Button if there are no planned purchases for unit type.
            if (!plannedPurchases.contains(MilitaryUnit.GENERAL))
                generalMinusButton.setEnabled(false);
            if (!plannedPurchases.contains(MilitaryUnit.INFANTRY))
                infantryMinusButton.setEnabled(false);
            if (!plannedPurchases.contains(MilitaryUnit.ELITE_INFANTRY))
                eInfantryMinusButton.setEnabled(false);
            if (!plannedPurchases.contains(MilitaryUnit.CAVALRY))
                cavalryMinusButton.setEnabled(false);
            if (!plannedPurchases.contains(MilitaryUnit.HEAVY_CAVALRY))
                hCavalryMinusButton.setEnabled(false);
            if (!plannedPurchases.contains(MilitaryUnit.IRREGULAR_CAVALRY))
                iCavalryMinusButton.setEnabled(false);
            if (!plannedPurchases.contains(MilitaryUnit.ARTILLERY))
                artilleryMinusButton.setEnabled(false);
            if (!plannedPurchases.contains(MilitaryUnit.HORSE_ARTILLERY))
                hArtilleryMinusButton.setEnabled(false);
            if (!plannedPurchases.contains(MilitaryUnit.MILITIA))
                militiaMinusButton.setEnabled(false);
            if (!plannedPurchases.contains(MilitaryUnit.ADMIRAL))
                admiralMinusButton.setEnabled(false);
            if (!plannedPurchases.contains(MilitaryUnit.NAVAL_SQUADRON))
                navalSquadMinusButton.setEnabled(false);
//            if (!plannedPurchases.contains(MilitaryUnit.NATIONAL_HERO))
//                nationalHeroMinusButton.setEnabled(false);
            if (!plannedPurchases.contains(MilitaryUnit.PAP))
                PAPMinusButton.setEnabled(false);

            //Update Remaining Production Points
            int pointsUsed = 0;
            for (Integer u : plannedPurchases)
                pointsUsed += MilitaryUnit.getCost(u, nation.getNationNumber());

            pointsRemaining.setText(String.valueOf(nation.getProductionPoints() - pointsUsed));
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals(GameMsg.getString("bp.final.action"))) {

                Object[] options = {GameMsg.getString("bp.yes"), GameMsg.getString("bp.no")};
                int choice = JOptionPane.showOptionDialog(this, GameMsg.getString("bp.question"),
                                                   GameMsg.getString("bp.question.title"),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if ( choice == JOptionPane.YES_OPTION ) {
                    controller.finalizeUnitPurchases(plannedPurchases, nation.getNationNumber(), hasBeenControlled);
                    dispose();
                }

            } else if (e.getActionCommand().equals(GameMsg.getString("bp.reset.action"))) {
                plannedPurchases.clear();
                generalCountField.setText("0");
                infantryCountField.setText("0");
                eInfantryCountField.setText("0");
                cavalryCountField.setText("0");
                hCavalryCountField.setText("0");
                iCavalryCountField.setText("0");
                artilleryCountField.setText("0");
                hArtilleryCountField.setText("0");
                militiaCountField.setText("0");
                admiralCountField.setText("0");
                navalSquadCountField.setText("0");
//                nationalHeroCountField.setText("0");
                PAPCountField.setText("0");
            }
        }

        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 && e.getComponent().isEnabled()) {
                Component comp = e.getComponent();

                if (comp == generalMinusButton) {
                    plannedPurchases.remove(new Integer(MilitaryUnit.GENERAL));
                    generalCountField.setText( String.valueOf(Integer.parseInt(generalCountField.getText()) - 1) );
                } else if (comp == generalPlusButton) {
                    plannedPurchases.add(MilitaryUnit.GENERAL);
                    generalCountField.setText( String.valueOf(Integer.parseInt(generalCountField.getText()) + 1) );
                } else if (comp == infantryMinusButton) {
                    plannedPurchases.remove(new Integer(MilitaryUnit.INFANTRY));
                    infantryCountField.setText( String.valueOf(Integer.parseInt(infantryCountField.getText()) - 1) );
                } else if (comp == infantryPlusButton) {
                    plannedPurchases.add(MilitaryUnit.INFANTRY);
                    infantryCountField.setText( String.valueOf(Integer.parseInt(infantryCountField.getText()) + 1) );
                } else if (comp == eInfantryMinusButton) {
                    plannedPurchases.remove(new Integer(MilitaryUnit.ELITE_INFANTRY));
                    eInfantryCountField.setText( String.valueOf(Integer.parseInt(eInfantryCountField.getText()) - 1) );
                } else if (comp == eInfantryPlusButton) {
                    plannedPurchases.add(MilitaryUnit.ELITE_INFANTRY);
                    eInfantryCountField.setText( String.valueOf(Integer.parseInt(eInfantryCountField.getText()) + 1) );
                } else if (comp == cavalryMinusButton) {
                    plannedPurchases.remove(new Integer(MilitaryUnit.CAVALRY));
                    cavalryCountField.setText( String.valueOf(Integer.parseInt(cavalryCountField.getText()) - 1) );
                } else if (comp == cavalryPlusButton) {
                    plannedPurchases.add(MilitaryUnit.CAVALRY);
                    cavalryCountField.setText( String.valueOf(Integer.parseInt(cavalryCountField.getText()) + 1) );
                } else if (comp == hCavalryMinusButton) {
                    plannedPurchases.remove(new Integer(MilitaryUnit.HEAVY_CAVALRY));
                    hCavalryCountField.setText( String.valueOf(Integer.parseInt(hCavalryCountField.getText()) - 1) );
                } else if (comp == hCavalryPlusButton) {
                    plannedPurchases.add(MilitaryUnit.HEAVY_CAVALRY);
                    hCavalryCountField.setText( String.valueOf(Integer.parseInt(hCavalryCountField.getText()) + 1) );
                } else if (comp == iCavalryMinusButton) {
                    plannedPurchases.remove(new Integer(MilitaryUnit.IRREGULAR_CAVALRY));
                    iCavalryCountField.setText( String.valueOf(Integer.parseInt(iCavalryCountField.getText()) - 1) );
                } else if (comp == iCavalryPlusButton) {
                    plannedPurchases.add(MilitaryUnit.IRREGULAR_CAVALRY);
                    iCavalryCountField.setText( String.valueOf(Integer.parseInt(iCavalryCountField.getText()) + 1) );
                } else if (comp == artilleryMinusButton) {
                    plannedPurchases.remove(new Integer(MilitaryUnit.ARTILLERY));
                    artilleryCountField.setText( String.valueOf(Integer.parseInt(artilleryCountField.getText()) - 1) );
                } else if (comp == artilleryPlusButton) {
                    plannedPurchases.add(MilitaryUnit.ARTILLERY);
                    artilleryCountField.setText( String.valueOf(Integer.parseInt(artilleryCountField.getText()) + 1) );
                } else if (comp == hArtilleryMinusButton) {
                    plannedPurchases.remove(new Integer(MilitaryUnit.HORSE_ARTILLERY));
                    hArtilleryCountField.setText( String.valueOf(Integer.parseInt(hArtilleryCountField.getText()) - 1) );
                } else if (comp == hArtilleryPlusButton) {
                    plannedPurchases.add(MilitaryUnit.HORSE_ARTILLERY);
                    hArtilleryCountField.setText( String.valueOf(Integer.parseInt(hArtilleryCountField.getText()) + 1) );
                } else if (comp == militiaMinusButton) {
                    plannedPurchases.remove(new Integer(MilitaryUnit.MILITIA));
                    militiaCountField.setText( String.valueOf(Integer.parseInt(militiaCountField.getText()) - 1) );
                } else if (comp == militiaPlusButton) {
                    plannedPurchases.add(MilitaryUnit.MILITIA);
                    militiaCountField.setText( String.valueOf(Integer.parseInt(militiaCountField.getText()) + 1) );
                } else if (comp == admiralMinusButton) {
                    plannedPurchases.remove(new Integer(MilitaryUnit.ADMIRAL));
                    admiralCountField.setText( String.valueOf(Integer.parseInt(admiralCountField.getText()) - 1) );
                } else if (comp == admiralPlusButton) {
                    plannedPurchases.add(MilitaryUnit.ADMIRAL);
                    admiralCountField.setText( String.valueOf(Integer.parseInt(admiralCountField.getText()) + 1) );
                } else if (comp == navalSquadMinusButton) {
                    plannedPurchases.remove(new Integer(MilitaryUnit.NAVAL_SQUADRON));
                    navalSquadCountField.setText( String.valueOf(Integer.parseInt(navalSquadCountField.getText()) - 1) );
                } else if (comp == navalSquadPlusButton) {
                    plannedPurchases.add(MilitaryUnit.NAVAL_SQUADRON);
                    navalSquadCountField.setText( String.valueOf(Integer.parseInt(navalSquadCountField.getText()) + 1) );
//                } else if (comp == nationalHeroMinusButton) {
//                    plannedPurchases.remove(new Integer(MilitaryUnit.NATIONAL_HERO));
//                    nationalHeroCountField.setText( String.valueOf(Integer.parseInt(nationalHeroCountField.getText()) - 1) );
//                } else if (comp == nationalHeroPlusButton) {
//                    plannedPurchases.add(MilitaryUnit.NATIONAL_HERO);
//                    nationalHeroCountField.setText( String.valueOf(Integer.parseInt(nationalHeroCountField.getText()) + 1) );
                } else if (comp == PAPMinusButton) {
                    plannedPurchases.remove(new Integer(MilitaryUnit.PAP));
                    PAPCountField.setText( String.valueOf(Integer.parseInt(PAPCountField.getText()) - 1) );
                } else if (comp == PAPPlusButton) {
                    plannedPurchases.add(MilitaryUnit.PAP);
                    PAPCountField.setText( String.valueOf(Integer.parseInt(PAPCountField.getText()) + 1) );
                }

                updateButtons();
            }
        }

        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}

        private ArrayList<Integer> plannedPurchases;

        private JLabel pointsAvailableLabel;
        private JLabel pointsAvailable;
        private JLabel purchasableUnits;

        private JLabel generalLabel;
        private JLabel infantryLabel;
        private JLabel eInfantryLabel;
        private JLabel cavalryLabel;
        private JLabel hCavalryLabel;
        private JLabel iCavalryLabel;
        private JLabel artilleryLabel;
        private JLabel hArtilleryLabel;
        private JLabel militiaLabel;
        private JLabel admiralLabel;
        private JLabel navalSquadLabel;
//        private JLabel nationalHeroLabel;

        private JTextField generalCountField;
        private JTextField infantryCountField;
        private JTextField eInfantryCountField;
        private JTextField cavalryCountField;
        private JTextField hCavalryCountField;
        private JTextField iCavalryCountField;
        private JTextField artilleryCountField;
        private JTextField hArtilleryCountField;
        private JTextField militiaCountField;
        private JTextField admiralCountField;
        private JTextField navalSquadCountField;
//        private JTextField nationalHeroCountField;

        private JLabel generalMinusButton;
        private JLabel infantryMinusButton;
        private JLabel eInfantryMinusButton;
        private JLabel cavalryMinusButton;
        private JLabel hCavalryMinusButton;
        private JLabel iCavalryMinusButton;
        private JLabel artilleryMinusButton;
        private JLabel hArtilleryMinusButton;
        private JLabel militiaMinusButton;
        private JLabel admiralMinusButton;
        private JLabel navalSquadMinusButton;
//        private JLabel nationalHeroMinusButton;

        private JLabel generalPlusButton;
        private JLabel infantryPlusButton;
        private JLabel eInfantryPlusButton;
        private JLabel cavalryPlusButton;
        private JLabel hCavalryPlusButton;
        private JLabel iCavalryPlusButton;
        private JLabel artilleryPlusButton;
        private JLabel hArtilleryPlusButton;
        private JLabel militiaPlusButton;
        private JLabel admiralPlusButton;
        private JLabel navalSquadPlusButton;
//        private JLabel nationalHeroPlusButton;

        private JLabel PAPLabel;
        private JTextField PAPCountField;
        private JLabel PAPMinusButton;
        private JLabel PAPPlusButton;

        private JLabel pointsRemainingLabel;
        private JLabel pointsRemaining;

        private JButton finalizeButton;
        private JButton resetButton;
    }
}