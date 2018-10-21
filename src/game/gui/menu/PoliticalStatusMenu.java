package game.gui.menu;

import game.controller.DisplayController;
import game.controller.GameController;
import game.controller.NationInstance;
import game.controller.Region.CapitalRegion;
import game.gui.listeners.UndecoratedMouseListener;
import game.util.GameMsg;
import lobby.controller.Nation;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * PoliticalStatusMenu.java  Date Created: Sep 24, 2013
 *
 * Purpose: To display the political status of all the nations.
 *
 * Description: Each Nation -- Nation relationship has 3 states:  War -- Neutral -- Allied
 * Capital States:  Liberated - Default, Captured - enemy occupied, Annexed - enemy owned, Liberated* (has been captured this war)
 * Grace/Reverse Grace Periods:
 *
 * Political Status:
 * ~~~~~~~~~~~~~~~~
 * France -- Neutral
 * Great Britain -- Allied to -- Prussia
 * Prussia -- Allied to -- Great Britain and Russia
 * Russia -- Allied to -- Prussia
 * Ottoman -- At War with -- Austria
 * Austria -- At War wint -- Ottoman
 * Spain -- Neutral
 *
 * Right of Passage:
 * ~~~~~~~~~~~~~~~~
 * France granted right of passage to Spain.
 * Prussia granted right of passage to Great Britain.
 *
 * Grace Periods:
 * ~~~~~~~~~~~~~
 * France -- Grace from -- Great Britain -- Until July, 1814
 * France -- Grace from -- Prussia -- Until July, 1814
 * France -- Grace from -- Russia -- Until July, 1814
 * France -- has Reverse Grace -- Great Britain -- Until January, 1814
 * France -- has Reverse Grace -- Prussia -- Until January, 1814
 * France -- has Reverse Grace -- Russia -- Until January, 1814
 *
 * Capital Status:
 * ~~~~~~~~~~~~~~
 * Paris (France) - Liberated
 * London (Great Britain) - Liberated
 * Berlin (Prussia) - Captured
 * St. Petersburg (Russia) - Liberated*
 * Moscow (Russia) - Annexed
 * Constantinople (Ottoman) - Liberated
 * Vienna (Austria/Hungary) - Liberated
 * Madrid (Spain) - Liberated
 *
 * *Has been Captured already during current war, no PAP will be gained if Captured again during current war.
 *
 * @author Chrisb
 */
public class PoliticalStatusMenu extends JDialog implements KeyListener {
    public PoliticalStatusMenu(GameController controller, DisplayController display) {
        super();
        this.controller = controller;
        this.display = display;
        psp = new PoliticalStatusPanel();
        UndecoratedMouseListener listener = new UndecoratedMouseListener(this);
        setTitle(GameMsg.getString("politicalStatus.title"));
        setVisible(true);
        setResizable(false);
        addKeyListener(this);
        addMouseListener(listener);
        addMouseMotionListener(listener);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setContentPane(psp);
        setMinimumSize(new Dimension(200,100));
    }

    public void updateStatus() { psp.updateStatus(); }

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            dispose();
    }
    public void keyReleased(KeyEvent e) {}

    private GameController controller;
    private DisplayController display;
    private PoliticalStatusPanel psp;

    private class PoliticalStatusPanel extends JPanel {
        private PoliticalStatusPanel() {
            setBackground(Color.GRAY);
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            createComponents();
            updateStatus();
        }

        private void createComponents() {
            relationshipPanel = new JPanel();
            relationshipPanel.setOpaque(false);
            relationshipPanel.setLayout(new BoxLayout(relationshipPanel, BoxLayout.PAGE_AXIS));
            relationshipPanel.setBorder( new CompoundBorder((new CompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4), BorderFactory.createEmptyBorder(5,5,5,5))),
                new CompoundBorder(new TitledBorder(GameMsg.getString("politicalStatus.title")), BorderFactory.createEmptyBorder(0,5,5,5))) );

            franceLabel = new JLabel(GameMsg.getString("nation.1"));
            gBritainLabel = new JLabel(GameMsg.getString("nation.2"));
            prussiaLabel = new JLabel(GameMsg.getString("nation.3"));
            russiaLabel = new JLabel(GameMsg.getString("nation.4"));
            ottomanLabel = new JLabel(GameMsg.getString("nation.5"));
            austriaLabel = new JLabel(GameMsg.getString("nation.6"));
            spainLabel = new JLabel(GameMsg.getString("nation.7"));

            Font nationLabelFont = new Font("Times New Roman", Font.BOLD, 15);
            franceLabel.setFont(nationLabelFont);
            gBritainLabel.setFont(nationLabelFont);
            prussiaLabel.setFont(nationLabelFont);
            russiaLabel.setFont(nationLabelFont);
            ottomanLabel.setFont(nationLabelFont);
            austriaLabel.setFont(nationLabelFont);
            spainLabel.setFont(nationLabelFont);

            franceLabel.setForeground(DisplayController.FRENCH_COLOR);
            gBritainLabel.setForeground(DisplayController.BRITISH_COLOR);
            prussiaLabel.setForeground(DisplayController.PRUSSIAN_COLOR);
            russiaLabel.setForeground(DisplayController.RUSSIAN_COLOR);
            ottomanLabel.setForeground(DisplayController.OTTOMAN_COLOR);
            austriaLabel.setForeground(DisplayController.AUSTRIAN_COLOR);
            spainLabel.setForeground(DisplayController.SPANISH_COLOR);

            france = controller.getNationInstance(Nation.FRANCE);
            gBritain = controller.getNationInstance(Nation.GREAT_BRITAIN);
            prussia = controller.getNationInstance(Nation.PRUSSIA);
            russia = controller.getNationInstance(Nation.RUSSIA);
            ottoman = controller.getNationInstance(Nation.OTTOMANS);
            austria = controller.getNationInstance(Nation.AUSTRIA_HUNGARY);
            spain = controller.getNationInstance(Nation.SPAIN);
        }


        public void updateStatus() {
            franceAllies = france.getAllies();
            franceEnemies = france.getEnemies();
            gBritainAllies = gBritain.getAllies();
            gBritainEnemies = gBritain.getEnemies();
            prussiaAllies = prussia.getAllies();
            prussiaEnemies = prussia.getEnemies();
            russiaAllies = russia.getAllies();
            russiaEnemies = russia.getEnemies();
            ottomanAllies = ottoman.getAllies();
            ottomanEnemies = ottoman.getEnemies();
            austriaAllies = austria.getAllies();
            austriaEnemies = austria.getEnemies();
            spainAllies = spain.getAllies();
            spainEnemies = spain.getEnemies();

            frenchCapitalStatus = ((CapitalRegion)controller.getRegion(GameMsg.getString("region.Paris"))).getCaptialStatus();
            gBritainCapitalStatus = ((CapitalRegion)controller.getRegion(GameMsg.getString("region.London"))).getCaptialStatus();
            prussiaCapitalStatus = ((CapitalRegion)controller.getRegion(GameMsg.getString("region.Berlin"))).getCaptialStatus();
            russia1CapitalStatus = ((CapitalRegion)controller.getRegion(GameMsg.getString("region.Petersburg"))).getCaptialStatus();
            russia2CapitalStatus = ((CapitalRegion)controller.getRegion(GameMsg.getString("region.Moscow"))).getCaptialStatus();
            ottomanCapitalStatus = ((CapitalRegion)controller.getRegion(GameMsg.getString("region.Constantinople"))).getCaptialStatus();
            austriaCapitalStatus = ((CapitalRegion)controller.getRegion(GameMsg.getString("region.Vienna"))).getCaptialStatus();
            spainCapitalStatus = ((CapitalRegion)controller.getRegion(GameMsg.getString("region.Madrid"))).getCaptialStatus();

            franceRightOfPassage = france.getRightOfPassages();
            gBritainRightOfPassage = gBritain.getRightOfPassages();
            prussiaRightOfPassage = prussia.getRightOfPassages();
            russiaRightOfPassage = russia.getRightOfPassages();
            ottomanRightOfPassage = ottoman.getRightOfPassages();
            austriaRightOfPassage = austria.getRightOfPassages();
            spainRightOfPassage = spain.getRightOfPassages();

            System.out.println("french passage size: " + franceRightOfPassage.size());
            System.out.println("gb passage size: " + gBritainRightOfPassage.size());
            System.out.println("prussia passage size: " + prussiaRightOfPassage.size());
            System.out.println("russia passage size: " + russiaRightOfPassage.size());
            System.out.println("ottoman passage size: " + ottomanRightOfPassage.size());
            System.out.println("austria passage size: " + austriaRightOfPassage.size());
            System.out.println("spain passage size: " + spainRightOfPassage.size());

            layoutComponents();
            revalidate();
            repaint();
        }

        public void layoutComponents() {
            relationshipPanel.removeAll();
            removeAll();

            /*
             * France
             */
            JPanel titlePanel = new JPanel();
            titlePanel.setOpaque(false);
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
            titlePanel.add(franceLabel);
            titlePanel.add(Box.createHorizontalGlue());
            relationshipPanel.add(titlePanel);
            relationshipPanel.add(Box.createVerticalStrut(titleSpacing));
            //Neutral
            if (franceAllies.size() == 0 && franceEnemies.size() == 0) {
                JPanel neutralPanel = new JPanel();
                neutralPanel.setOpaque(false);
                neutralPanel.setLayout(new BoxLayout(neutralPanel, BoxLayout.LINE_AXIS));
                neutralLabel = new JLabel(GameMsg.getString("politicalStatus.neutral"));
                neutralPanel.add(Box.createHorizontalStrut(indent));
                neutralPanel.add(neutralLabel);
                neutralPanel.add(Box.createHorizontalGlue());
                relationshipPanel.add(neutralPanel);
            }
            //Allied
            if (franceAllies.size() > 0) {
                JPanel alliedPanel = new JPanel();
                alliedPanel.setOpaque(false);
                alliedPanel.setLayout(new BoxLayout(alliedPanel, BoxLayout.LINE_AXIS));
                alliedLabel = new JLabel(GameMsg.getString("politicalStatus.allied"));
                alliedPanel.add(Box.createHorizontalStrut(indent));
                alliedPanel.add(alliedLabel);
                alliedPanel.add(Box.createHorizontalStrut(labelGap));
                for (int i = 0; i < franceAllies.size(); i++ ) {
                    alliedPanel.add( new JLabel( GameMsg.getString("nation." + franceAllies.get(i) )) );
                    if (i < franceAllies.size() - 1)
                        alliedPanel.add(new JLabel(", "));
                }
                relationshipPanel.add(alliedPanel);
            }
            //War
            if (franceEnemies.size() > 0) {
                JPanel enemiesPanel = new JPanel();
                enemiesPanel.setOpaque(false);
                enemiesPanel.setLayout(new BoxLayout(enemiesPanel, BoxLayout.LINE_AXIS));
                atWarLabel = new JLabel(GameMsg.getString("politicalStatus.atWar"));
                enemiesPanel.add(Box.createHorizontalStrut(indent));
                enemiesPanel.add(atWarLabel);
                enemiesPanel.add(Box.createHorizontalStrut(labelGap));
                for (int i = 0; i < franceEnemies.size(); i++ ) {
                    enemiesPanel.add( new JLabel( GameMsg.getString("nation." + franceEnemies.get(i) )) );
                    if (i < franceEnemies.size() - 1)
                        enemiesPanel.add(new JLabel(", "));
                }
                relationshipPanel.add(enemiesPanel);
            }
            //Passage
            if (franceRightOfPassage.size() > 0) {
                for (Integer nation : franceRightOfPassage) {
                    JPanel passagePanel = new JPanel();
                    passagePanel.setOpaque(false);
                    passagePanel.setLayout(new BoxLayout(passagePanel, BoxLayout.LINE_AXIS));
                    passageLabel = new JLabel(GameMsg.getString("politicalStatus.rightPassage"));
                    passagePanel.add(Box.createHorizontalStrut(indent));
                    passagePanel.add(passageLabel);
                    passagePanel.add(Box.createHorizontalStrut(labelGap));
                    passagePanel.add(new JLabel(GameMsg.getString("nation." + nation)));
                    passagePanel.add(Box.createHorizontalGlue());
                    relationshipPanel.add(passagePanel);
                }
            }
            //Capital
            JPanel capitalPanel = new JPanel();
            capitalPanel.setOpaque(false);
            capitalPanel.setLayout(new BoxLayout(capitalPanel, BoxLayout.LINE_AXIS));
            capitalPanel.add(Box.createHorizontalStrut(indent));
            capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.capital.france")));
            capitalPanel.add(Box.createHorizontalStrut(labelGap));
            switch (frenchCapitalStatus) {
                case CapitalRegion.CAPTURED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.captured"))); break;
                case CapitalRegion.ANNEXED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.annexed"))); break;
                case CapitalRegion.LIBERATED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.liberated"))); break;
            }
            capitalPanel.add(Box.createHorizontalGlue());
            relationshipPanel.add(capitalPanel);

            /*
             * Great Britain
             */
            relationshipPanel.add(Box.createVerticalStrut(nationSpacing));
            titlePanel = new JPanel();
            titlePanel.setOpaque(false);
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
            titlePanel.add(gBritainLabel);
            titlePanel.add(Box.createHorizontalGlue());
            relationshipPanel.add(titlePanel);
            relationshipPanel.add(Box.createVerticalStrut(titleSpacing));
            //Neutral
            if (gBritainAllies.size() == 0 && gBritainEnemies.size() == 0) {
                JPanel neutralPanel = new JPanel();
                neutralPanel.setOpaque(false);
                neutralPanel.setLayout(new BoxLayout(neutralPanel, BoxLayout.LINE_AXIS));
                neutralLabel = new JLabel(GameMsg.getString("politicalStatus.neutral"));
                neutralPanel.add(Box.createHorizontalStrut(indent));
                neutralPanel.add(neutralLabel);
                neutralPanel.add(Box.createHorizontalGlue());
                relationshipPanel.add(neutralPanel);
            }
            //Allied
            if (gBritainAllies.size() > 0) {
                JPanel alliedPanel = new JPanel();
                alliedPanel.setOpaque(false);
                alliedPanel.setLayout(new BoxLayout(alliedPanel, BoxLayout.LINE_AXIS));
                alliedLabel = new JLabel(GameMsg.getString("politicalStatus.allied"));
                alliedPanel.add(Box.createHorizontalStrut(indent));
                alliedPanel.add(alliedLabel);
                alliedPanel.add(Box.createHorizontalStrut(labelGap));
                for (int i = 0; i < gBritainAllies.size(); i++ ) {
                    alliedPanel.add( new JLabel( GameMsg.getString("nation." + gBritainAllies.get(i) )) );
                    if (i < gBritainAllies.size() - 1)
                        alliedPanel.add(new JLabel(", "));
                }
                relationshipPanel.add(alliedPanel);
            }
            //War
            if (gBritainEnemies.size() > 0) {
                JPanel enemiesPanel = new JPanel();
                enemiesPanel.setOpaque(false);
                enemiesPanel.setLayout(new BoxLayout(enemiesPanel, BoxLayout.LINE_AXIS));
                atWarLabel = new JLabel(GameMsg.getString("politicalStatus.atWar"));
                enemiesPanel.add(Box.createHorizontalStrut(indent));
                enemiesPanel.add(atWarLabel);
                enemiesPanel.add(Box.createHorizontalStrut(labelGap));
                for (int i = 0; i < gBritainEnemies.size(); i++ ) {
                    enemiesPanel.add( new JLabel( GameMsg.getString("nation." + gBritainEnemies.get(i) )) );
                    if (i < gBritainEnemies.size() - 1)
                        enemiesPanel.add(new JLabel(", "));
                }
                relationshipPanel.add(enemiesPanel);
            }
            //Passage
            if (gBritainRightOfPassage.size() > 0) {
                for (Integer nation : gBritainRightOfPassage) {
                    JPanel passagePanel = new JPanel();
                    passagePanel.setOpaque(false);
                    passagePanel.setLayout(new BoxLayout(passagePanel, BoxLayout.LINE_AXIS));
                    passageLabel = new JLabel(GameMsg.getString("politicalStatus.rightPassage"));
                    passagePanel.add(Box.createHorizontalStrut(indent));
                    passagePanel.add(passageLabel);
                    passagePanel.add(Box.createHorizontalStrut(labelGap));
                    passagePanel.add(new JLabel(GameMsg.getString("nation." + nation)));
                    passagePanel.add(Box.createHorizontalGlue());
                    relationshipPanel.add(passagePanel);
                }
            }
            //Capital
            capitalPanel = new JPanel();
            capitalPanel.setOpaque(false);
            capitalPanel.setLayout(new BoxLayout(capitalPanel, BoxLayout.LINE_AXIS));
            capitalPanel.add(Box.createHorizontalStrut(indent));
            capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.capital.britain")));
            capitalPanel.add(Box.createHorizontalStrut(labelGap));
            switch (gBritainCapitalStatus) {
                case CapitalRegion.CAPTURED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.captured"))); break;
                case CapitalRegion.ANNEXED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.annexed"))); break;
                case CapitalRegion.LIBERATED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.liberated"))); break;
            }
            capitalPanel.add(Box.createHorizontalGlue());
            relationshipPanel.add(capitalPanel);

            /*
             * Prussia
             */
            relationshipPanel.add(Box.createVerticalStrut(nationSpacing));
            titlePanel = new JPanel();
            titlePanel.setOpaque(false);
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
            titlePanel.add(prussiaLabel);
            titlePanel.add(Box.createHorizontalGlue());
            relationshipPanel.add(titlePanel);
            relationshipPanel.add(Box.createVerticalStrut(titleSpacing));
            //Neutral
            if (prussiaAllies.size() == 0 && prussiaEnemies.size() == 0) {
                JPanel neutralPanel = new JPanel();
                neutralPanel.setOpaque(false);
                neutralPanel.setLayout(new BoxLayout(neutralPanel, BoxLayout.LINE_AXIS));
                neutralLabel = new JLabel(GameMsg.getString("politicalStatus.neutral"));
                neutralPanel.add(Box.createHorizontalStrut(indent));
                neutralPanel.add(neutralLabel);
                neutralPanel.add(Box.createHorizontalGlue());
                relationshipPanel.add(neutralPanel);
            }
            //Allied
            if (prussiaAllies.size() > 0) {
                JPanel alliedPanel = new JPanel();
                alliedPanel.setOpaque(false);
                alliedPanel.setLayout(new BoxLayout(alliedPanel, BoxLayout.LINE_AXIS));
                alliedLabel = new JLabel(GameMsg.getString("politicalStatus.allied"));
                alliedPanel.add(Box.createHorizontalStrut(indent));
                alliedPanel.add(alliedLabel);
                alliedPanel.add(Box.createHorizontalStrut(labelGap));
                for (int i = 0; i < prussiaAllies.size(); i++ ) {
                    alliedPanel.add( new JLabel( GameMsg.getString("nation." + prussiaAllies.get(i) )) );
                    if (i < prussiaAllies.size() - 1)
                        alliedPanel.add(new JLabel(", "));
                }
                relationshipPanel.add(alliedPanel);
            }
            //War
            if (prussiaEnemies.size() > 0) {
                JPanel enemiesPanel = new JPanel();
                enemiesPanel.setOpaque(false);
                enemiesPanel.setLayout(new BoxLayout(enemiesPanel, BoxLayout.LINE_AXIS));
                atWarLabel = new JLabel(GameMsg.getString("politicalStatus.atWar"));
                enemiesPanel.add(Box.createHorizontalStrut(indent));
                enemiesPanel.add(atWarLabel);
                enemiesPanel.add(Box.createHorizontalStrut(labelGap));
                for (int i = 0; i < prussiaEnemies.size(); i++ ) {
                    enemiesPanel.add( new JLabel( GameMsg.getString("nation." + prussiaEnemies.get(i) )) );
                    if (i < prussiaEnemies.size() - 1)
                        enemiesPanel.add(new JLabel(", "));
                }
                relationshipPanel.add(enemiesPanel);
            }
            //Passage
            if (prussiaRightOfPassage.size() > 0) {
                for (Integer nation : prussiaRightOfPassage) {
                    JPanel passagePanel = new JPanel();
                    passagePanel.setOpaque(false);
                    passagePanel.setLayout(new BoxLayout(passagePanel, BoxLayout.LINE_AXIS));
                    passageLabel = new JLabel(GameMsg.getString("politicalStatus.rightPassage"));
                    passagePanel.add(Box.createHorizontalStrut(indent));
                    passagePanel.add(passageLabel);
                    passagePanel.add(Box.createHorizontalStrut(labelGap));
                    passagePanel.add(new JLabel(GameMsg.getString("nation." + nation)));
                    passagePanel.add(Box.createHorizontalGlue());
                    relationshipPanel.add(passagePanel);
                }
            }
            //Capital
            capitalPanel = new JPanel();
            capitalPanel.setOpaque(false);
            capitalPanel.setLayout(new BoxLayout(capitalPanel, BoxLayout.LINE_AXIS));
            capitalPanel.add(Box.createHorizontalStrut(indent));
            capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.capital.prussia")));
            capitalPanel.add(Box.createHorizontalStrut(labelGap));
            switch (prussiaCapitalStatus) {
                case CapitalRegion.CAPTURED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.captured"))); break;
                case CapitalRegion.ANNEXED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.annexed"))); break;
                case CapitalRegion.LIBERATED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.liberated"))); break;
            }
            capitalPanel.add(Box.createHorizontalGlue());
            relationshipPanel.add(capitalPanel);

            /*
             * Russia
             */
            relationshipPanel.add(Box.createVerticalStrut(nationSpacing));
            titlePanel = new JPanel();
            titlePanel.setOpaque(false);
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
            titlePanel.add(russiaLabel);
            titlePanel.add(Box.createHorizontalGlue());
            relationshipPanel.add(titlePanel);
            relationshipPanel.add(Box.createVerticalStrut(titleSpacing));
            //Neutral
            if (russiaAllies.size() == 0 && russiaEnemies.size() == 0) {
                JPanel neutralPanel = new JPanel();
                neutralPanel.setOpaque(false);
                neutralPanel.setLayout(new BoxLayout(neutralPanel, BoxLayout.LINE_AXIS));
                neutralLabel = new JLabel(GameMsg.getString("politicalStatus.neutral"));
                neutralPanel.add(Box.createHorizontalStrut(indent));
                neutralPanel.add(neutralLabel);
                neutralPanel.add(Box.createHorizontalGlue());
                relationshipPanel.add(neutralPanel);
            }
            //Allied
            if (russiaAllies.size() > 0) {
                JPanel alliedPanel = new JPanel();
                alliedPanel.setOpaque(false);
                alliedPanel.setLayout(new BoxLayout(alliedPanel, BoxLayout.LINE_AXIS));
                alliedLabel = new JLabel(GameMsg.getString("politicalStatus.allied"));
                alliedPanel.add(Box.createHorizontalStrut(indent));
                alliedPanel.add(alliedLabel);
                alliedPanel.add(Box.createHorizontalStrut(labelGap));
                for (int i = 0; i < russiaAllies.size(); i++ ) {
                    alliedPanel.add( new JLabel( GameMsg.getString("nation." + russiaAllies.get(i) )) );
                    if (i < russiaAllies.size() - 1)
                        alliedPanel.add(new JLabel(", "));
                }
                relationshipPanel.add(alliedPanel);
            }
            //War
            if (russiaEnemies.size() > 0) {
                JPanel enemiesPanel = new JPanel();
                enemiesPanel.setOpaque(false);
                enemiesPanel.setLayout(new BoxLayout(enemiesPanel, BoxLayout.LINE_AXIS));
                atWarLabel = new JLabel(GameMsg.getString("politicalStatus.atWar"));
                enemiesPanel.add(Box.createHorizontalStrut(indent));
                enemiesPanel.add(atWarLabel);
                enemiesPanel.add(Box.createHorizontalStrut(labelGap));
                for (int i = 0; i < russiaEnemies.size(); i++ ) {
                    enemiesPanel.add( new JLabel( GameMsg.getString("nation." + russiaEnemies.get(i) )) );
                    if (i < russiaEnemies.size() - 1)
                        enemiesPanel.add(new JLabel(", "));
                }
                relationshipPanel.add(enemiesPanel);
            }
            //Passage
            if (russiaRightOfPassage.size() > 0) {
                for (Integer nation : russiaRightOfPassage) {
                    JPanel passagePanel = new JPanel();
                    passagePanel.setOpaque(false);
                    passagePanel.setLayout(new BoxLayout(passagePanel, BoxLayout.LINE_AXIS));
                    passageLabel = new JLabel(GameMsg.getString("politicalStatus.rightPassage"));
                    passagePanel.add(Box.createHorizontalStrut(indent));
                    passagePanel.add(passageLabel);
                    passagePanel.add(Box.createHorizontalStrut(labelGap));
                    passagePanel.add(new JLabel(GameMsg.getString("nation." + nation)));
                    passagePanel.add(Box.createHorizontalGlue());
                    relationshipPanel.add(passagePanel);
                }
            }
            //Capital
            capitalPanel = new JPanel();
            capitalPanel.setOpaque(false);
            capitalPanel.setLayout(new BoxLayout(capitalPanel, BoxLayout.LINE_AXIS));
            capitalPanel.add(Box.createHorizontalStrut(indent));
            capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.capital.russia.1")));
            capitalPanel.add(Box.createHorizontalStrut(labelGap));
            switch (russia1CapitalStatus) {
                case CapitalRegion.CAPTURED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.captured"))); break;
                case CapitalRegion.ANNEXED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.annexed"))); break;
                case CapitalRegion.LIBERATED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.liberated"))); break;
            }
            capitalPanel.add(Box.createHorizontalGlue());
            relationshipPanel.add(capitalPanel);

            capitalPanel = new JPanel();
            capitalPanel.setOpaque(false);
            capitalPanel.setLayout(new BoxLayout(capitalPanel, BoxLayout.LINE_AXIS));
            capitalPanel.add(Box.createHorizontalStrut(indent));
            capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.capital.russia.2")));
            capitalPanel.add(Box.createHorizontalStrut(labelGap));
            switch (russia2CapitalStatus) {
                case CapitalRegion.CAPTURED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.captured"))); break;
                case CapitalRegion.ANNEXED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.annexed"))); break;
                case CapitalRegion.LIBERATED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.liberated"))); break;
            }
            capitalPanel.add(Box.createHorizontalGlue());
            relationshipPanel.add(capitalPanel);

            /*
             * Ottoman Empire
             */
            relationshipPanel.add(Box.createVerticalStrut(nationSpacing));
            titlePanel = new JPanel();
            titlePanel.setOpaque(false);
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
            titlePanel.add(ottomanLabel);
            titlePanel.add(Box.createHorizontalGlue());
            relationshipPanel.add(titlePanel);
            relationshipPanel.add(Box.createVerticalStrut(titleSpacing));
            //Neutral
            if (ottomanAllies.size() == 0 && ottomanEnemies.size() == 0) {
                JPanel neutralPanel = new JPanel();
                neutralPanel.setOpaque(false);
                neutralPanel.setLayout(new BoxLayout(neutralPanel, BoxLayout.LINE_AXIS));
                neutralLabel = new JLabel(GameMsg.getString("politicalStatus.neutral"));
                neutralPanel.add(Box.createHorizontalStrut(indent));
                neutralPanel.add(neutralLabel);
                neutralPanel.add(Box.createHorizontalGlue());
                relationshipPanel.add(neutralPanel);
            }
            //Allied
            if (ottomanAllies.size() > 0) {
                JPanel alliedPanel = new JPanel();
                alliedPanel.setOpaque(false);
                alliedPanel.setLayout(new BoxLayout(alliedPanel, BoxLayout.LINE_AXIS));
                alliedLabel = new JLabel(GameMsg.getString("politicalStatus.allied"));
                alliedPanel.add(Box.createHorizontalStrut(indent));
                alliedPanel.add(alliedLabel);
                alliedPanel.add(Box.createHorizontalStrut(labelGap));
                for (int i = 0; i < ottomanAllies.size(); i++ ) {
                    alliedPanel.add( new JLabel( GameMsg.getString("nation." + ottomanAllies.get(i) )) );
                    if (i < ottomanAllies.size() - 1)
                        alliedPanel.add(new JLabel(", "));
                }
                relationshipPanel.add(alliedPanel);
            }
            //War
            if (ottomanEnemies.size() > 0) {
                JPanel enemiesPanel = new JPanel();
                enemiesPanel.setOpaque(false);
                enemiesPanel.setLayout(new BoxLayout(enemiesPanel, BoxLayout.LINE_AXIS));
                atWarLabel = new JLabel(GameMsg.getString("politicalStatus.atWar"));
                enemiesPanel.add(Box.createHorizontalStrut(indent));
                enemiesPanel.add(atWarLabel);
                enemiesPanel.add(Box.createHorizontalStrut(labelGap));
                for (int i = 0; i < ottomanEnemies.size(); i++ ) {
                    enemiesPanel.add( new JLabel( GameMsg.getString("nation." + ottomanEnemies.get(i) )) );
                    if (i < ottomanEnemies.size() - 1)
                        enemiesPanel.add(new JLabel(", "));
                }
                relationshipPanel.add(enemiesPanel);
            }
            //Passage
            if (ottomanRightOfPassage.size() > 0) {
                for (Integer nation : ottomanRightOfPassage) {
                    JPanel passagePanel = new JPanel();
                    passagePanel.setOpaque(false);
                    passagePanel.setLayout(new BoxLayout(passagePanel, BoxLayout.LINE_AXIS));
                    passageLabel = new JLabel(GameMsg.getString("politicalStatus.rightPassage"));
                    passagePanel.add(Box.createHorizontalStrut(indent));
                    passagePanel.add(passageLabel);
                    passagePanel.add(Box.createHorizontalStrut(labelGap));
                    passagePanel.add(new JLabel(GameMsg.getString("nation." + nation)));
                    passagePanel.add(Box.createHorizontalGlue());
                    relationshipPanel.add(passagePanel);
                }
            }
            //Capital
            capitalPanel = new JPanel();
            capitalPanel.setOpaque(false);
            capitalPanel.setLayout(new BoxLayout(capitalPanel, BoxLayout.LINE_AXIS));
            capitalPanel.add(Box.createHorizontalStrut(indent));
            capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.capital.ottoman")));
            capitalPanel.add(Box.createHorizontalStrut(labelGap));
            switch (ottomanCapitalStatus) {
                case CapitalRegion.CAPTURED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.captured"))); break;
                case CapitalRegion.ANNEXED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.annexed"))); break;
                case CapitalRegion.LIBERATED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.liberated"))); break;
            }
            capitalPanel.add(Box.createHorizontalGlue());
            relationshipPanel.add(capitalPanel);

            /*
             * Austria-Hungary
             */
            relationshipPanel.add(Box.createVerticalStrut(nationSpacing));
            titlePanel = new JPanel();
            titlePanel.setOpaque(false);
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
            titlePanel.add(austriaLabel);
            titlePanel.add(Box.createHorizontalGlue());
            relationshipPanel.add(titlePanel);
            relationshipPanel.add(Box.createVerticalStrut(titleSpacing));
            //Neutral
            if (austriaAllies.size() == 0 && austriaEnemies.size() == 0) {
                JPanel neutralPanel = new JPanel();
                neutralPanel.setOpaque(false);
                neutralPanel.setLayout(new BoxLayout(neutralPanel, BoxLayout.LINE_AXIS));
                neutralLabel = new JLabel(GameMsg.getString("politicalStatus.neutral"));
                neutralPanel.add(Box.createHorizontalStrut(indent));
                neutralPanel.add(neutralLabel);
                neutralPanel.add(Box.createHorizontalGlue());
                relationshipPanel.add(neutralPanel);
            }
            //Allied
            if (austriaAllies.size() > 0) {
                JPanel alliedPanel = new JPanel();
                alliedPanel.setOpaque(false);
                alliedPanel.setLayout(new BoxLayout(alliedPanel, BoxLayout.LINE_AXIS));
                alliedLabel = new JLabel(GameMsg.getString("politicalStatus.allied"));
                alliedPanel.add(Box.createHorizontalStrut(indent));
                alliedPanel.add(alliedLabel);
                alliedPanel.add(Box.createHorizontalStrut(labelGap));
                for (int i = 0; i < austriaAllies.size(); i++ ) {
                    alliedPanel.add( new JLabel( GameMsg.getString("nation." + austriaAllies.get(i) )) );
                    if (i < austriaAllies.size() - 1)
                        alliedPanel.add(new JLabel(", "));
                }
                relationshipPanel.add(alliedPanel);
            }
            //War
            if (austriaEnemies.size() > 0) {
                JPanel enemiesPanel = new JPanel();
                enemiesPanel.setOpaque(false);
                enemiesPanel.setLayout(new BoxLayout(enemiesPanel, BoxLayout.LINE_AXIS));
                atWarLabel = new JLabel(GameMsg.getString("politicalStatus.atWar"));
                enemiesPanel.add(Box.createHorizontalStrut(indent));
                enemiesPanel.add(atWarLabel);
                enemiesPanel.add(Box.createHorizontalStrut(labelGap));
                for (int i = 0; i < austriaEnemies.size(); i++ ) {
                    enemiesPanel.add( new JLabel( GameMsg.getString("nation." + austriaEnemies.get(i) )) );
                    if (i < austriaEnemies.size() - 1)
                        enemiesPanel.add(new JLabel(", "));
                }
                relationshipPanel.add(enemiesPanel);
            }
            //Passage
            if (austriaRightOfPassage.size() > 0) {
                for (Integer nation : austriaRightOfPassage) {
                    JPanel passagePanel = new JPanel();
                    passagePanel.setOpaque(false);
                    passagePanel.setLayout(new BoxLayout(passagePanel, BoxLayout.LINE_AXIS));
                    passageLabel = new JLabel(GameMsg.getString("politicalStatus.rightPassage"));
                    passagePanel.add(Box.createHorizontalStrut(indent));
                    passagePanel.add(passageLabel);
                    passagePanel.add(Box.createHorizontalStrut(labelGap));
                    passagePanel.add(new JLabel(GameMsg.getString("nation." + nation)));
                    passagePanel.add(Box.createHorizontalGlue());
                    relationshipPanel.add(passagePanel);
                }
            }
            //Capital
            capitalPanel = new JPanel();
            capitalPanel.setOpaque(false);
            capitalPanel.setLayout(new BoxLayout(capitalPanel, BoxLayout.LINE_AXIS));
            capitalPanel.add(Box.createHorizontalStrut(indent));
            capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.capital.austria")));
            capitalPanel.add(Box.createHorizontalStrut(labelGap));
            switch (austriaCapitalStatus) {
                case CapitalRegion.CAPTURED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.captured"))); break;
                case CapitalRegion.ANNEXED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.annexed"))); break;
                case CapitalRegion.LIBERATED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.liberated"))); break;
            }
            capitalPanel.add(Box.createHorizontalGlue());
            relationshipPanel.add(capitalPanel);

            /*
             * Spain
             */
            relationshipPanel.add(Box.createVerticalStrut(nationSpacing));
            titlePanel = new JPanel();
            titlePanel.setOpaque(false);
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
            titlePanel.add(spainLabel);
            titlePanel.add(Box.createHorizontalGlue());
            relationshipPanel.add(titlePanel);
            relationshipPanel.add(Box.createVerticalStrut(titleSpacing));
            //Neutral
            if (spainAllies.size() == 0 && spainEnemies.size() == 0) {
                JPanel neutralPanel = new JPanel();
                neutralPanel.setOpaque(false);
                neutralPanel.setLayout(new BoxLayout(neutralPanel, BoxLayout.LINE_AXIS));
                neutralLabel = new JLabel(GameMsg.getString("politicalStatus.neutral"));
                neutralPanel.add(Box.createHorizontalStrut(indent));
                neutralPanel.add(neutralLabel);
                neutralPanel.add(Box.createHorizontalGlue());
                relationshipPanel.add(neutralPanel);
            }
            //Allied
            if (spainAllies.size() > 0) {
                JPanel alliedPanel = new JPanel();
                alliedPanel.setOpaque(false);
                alliedPanel.setLayout(new BoxLayout(alliedPanel, BoxLayout.LINE_AXIS));
                alliedLabel = new JLabel(GameMsg.getString("politicalStatus.allied"));
                alliedPanel.add(Box.createHorizontalStrut(indent));
                alliedPanel.add(alliedLabel);
                alliedPanel.add(Box.createHorizontalStrut(labelGap));
                for (int i = 0; i < spainAllies.size(); i++ ) {
                    alliedPanel.add( new JLabel( GameMsg.getString("nation." + spainAllies.get(i) )) );
                    if (i < spainAllies.size() - 1)
                        alliedPanel.add(new JLabel(", "));
                }
                relationshipPanel.add(alliedPanel);
            }
            //War
            if (spainEnemies.size() > 0) {
                JPanel enemiesPanel = new JPanel();
                enemiesPanel.setOpaque(false);
                enemiesPanel.setLayout(new BoxLayout(enemiesPanel, BoxLayout.LINE_AXIS));
                atWarLabel = new JLabel(GameMsg.getString("politicalStatus.atWar"));
                enemiesPanel.add(Box.createHorizontalStrut(indent));
                enemiesPanel.add(atWarLabel);
                enemiesPanel.add(Box.createHorizontalStrut(labelGap));
                for (int i = 0; i < spainEnemies.size(); i++ ) {
                    enemiesPanel.add( new JLabel( GameMsg.getString("nation." + spainEnemies.get(i) )) );
                    if (i < spainEnemies.size() - 1)
                        enemiesPanel.add(new JLabel(", "));
                }
                relationshipPanel.add(enemiesPanel);
            }
            //Passage
            if (spainRightOfPassage.size() > 0) {
                for (Integer nation : spainRightOfPassage) {
                    JPanel passagePanel = new JPanel();
                    passagePanel.setOpaque(false);
                    passagePanel.setLayout(new BoxLayout(passagePanel, BoxLayout.LINE_AXIS));
                    passageLabel = new JLabel(GameMsg.getString("politicalStatus.rightPassage"));
                    passagePanel.add(Box.createHorizontalStrut(indent));
                    passagePanel.add(passageLabel);
                    passagePanel.add(Box.createHorizontalStrut(labelGap));
                    passagePanel.add(new JLabel(GameMsg.getString("nation." + nation)));
                    passagePanel.add(Box.createHorizontalGlue());
                    relationshipPanel.add(passagePanel);
                }
            }
            //Capital
            capitalPanel = new JPanel();
            capitalPanel.setOpaque(false);
            capitalPanel.setLayout(new BoxLayout(capitalPanel, BoxLayout.LINE_AXIS));
            capitalPanel.add(Box.createHorizontalStrut(indent));
            capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.capital.spain")));
            capitalPanel.add(Box.createHorizontalStrut(labelGap));
            switch (spainCapitalStatus) {
                case CapitalRegion.CAPTURED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.captured"))); break;
                case CapitalRegion.ANNEXED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.annexed"))); break;
                case CapitalRegion.LIBERATED:
                    capitalPanel.add(new JLabel(GameMsg.getString("politicalStatus.liberated"))); break;
            }
            capitalPanel.add(Box.createHorizontalGlue());
            relationshipPanel.add(capitalPanel);

            add(relationshipPanel);

            //Passage Right?
        }

        private NationInstance france;
        private NationInstance gBritain;
        private NationInstance prussia;
        private NationInstance russia;
        private NationInstance ottoman;
        private NationInstance austria;
        private NationInstance spain;

        private JPanel relationshipPanel;
        private JLabel franceLabel;
        private JLabel gBritainLabel;
        private JLabel prussiaLabel;
        private JLabel russiaLabel;
        private JLabel ottomanLabel;
        private JLabel austriaLabel;
        private JLabel spainLabel;
        private JLabel neutralLabel;
        private JLabel alliedLabel;
        private JLabel atWarLabel;
        private JLabel passageLabel;

        protected ArrayList<Integer> franceAllies;
        protected ArrayList<Integer> franceEnemies;
        protected ArrayList<Integer> gBritainAllies;
        protected ArrayList<Integer> gBritainEnemies;
        protected ArrayList<Integer> prussiaAllies;
        protected ArrayList<Integer> prussiaEnemies;
        protected ArrayList<Integer> russiaAllies;
        protected ArrayList<Integer> russiaEnemies;
        protected ArrayList<Integer> ottomanAllies;
        protected ArrayList<Integer> ottomanEnemies;
        protected ArrayList<Integer> austriaAllies;
        protected ArrayList<Integer> austriaEnemies;
        protected ArrayList<Integer> spainAllies;
        protected ArrayList<Integer> spainEnemies;

        protected ArrayList<Integer> franceRightOfPassage;
        protected ArrayList<Integer> gBritainRightOfPassage;
        protected ArrayList<Integer> prussiaRightOfPassage;
        protected ArrayList<Integer> russiaRightOfPassage;
        protected ArrayList<Integer> ottomanRightOfPassage;
        protected ArrayList<Integer> austriaRightOfPassage;
        protected ArrayList<Integer> spainRightOfPassage;

        private int frenchCapitalStatus;
        private int gBritainCapitalStatus;
        private int prussiaCapitalStatus;
        private int russia1CapitalStatus;
        private int russia2CapitalStatus;
        private int ottomanCapitalStatus;
        private int austriaCapitalStatus;
        private int spainCapitalStatus;

        private static final int nationSpacing = 15;
        private static final int titleSpacing = 4;
        private static final int labelGap = 5;
        private static final int indent = 10;
    }
}