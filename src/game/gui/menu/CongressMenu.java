package game.gui.menu;

import game.controller.GameController;
import game.controller.NationInstance;
import game.controller.Region.*;
import game.util.GameMsg;
import game.util.PoliticalUtilities;
import game.util.MessageDialog;
import game.gui.listeners.UndecoratedMouseListener;
import game.client.political.CongressActionThread;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * CongressMenu.java  Date Created: Dec 26, 2013
 *
 * Purpose:
 *
 * Description:
 *
 * @author Chrisb
 */
public class CongressMenu extends JDialog implements MouseListener, KeyListener {
    private GameController controller;
    private int sueingNation, congressNation, turnIndex;

    public CongressMenu(GameController controller, int sueingNation, int congressNation, int turnIndex) {
        this.controller = controller;
        this.sueingNation = sueingNation;
        this.congressNation = congressNation;
        this.turnIndex = turnIndex;
        requestFocusInWindow();
        UndecoratedMouseListener listener = new UndecoratedMouseListener(this);
        addMouseListener(listener);
        addMouseMotionListener(listener);
        addKeyListener(this);
        setUndecorated(true);
        setVisible(true);
        setResizable(false);
        setAlwaysOnTop(true);
        setBackground(Color.DARK_GRAY);
        setMinimumSize(new Dimension(320, 50));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        String title = GameMsg.getString("nation.pos." + sueingNation) + " " + GameMsg.getString("congressMenu.title");
        setTitle(title);
        setContentPane(new CongressPanel(title));
    }

    //TESTING ONLY METHOD
    public CongressMenu (int sueingNation, int congressNation, int turnIndex) {
        this(null, sueingNation, congressNation, turnIndex);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    private class CongressPanel extends JPanel implements ActionListener {
        private CongressPanel(String title) {
            setBackground(Color.GRAY);
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            setBorder( new CompoundBorder((new CompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4), BorderFactory.createEmptyBorder(5,5,5,5))),
                new CompoundBorder(new TitledBorder(title), BorderFactory.createEmptyBorder(0,5,5,5))) );

            createComponents();
            layoutComponents();

            //One of the four actions MUST be taken
            //If congressNation can (restore) one of its own homeland regions from sueing nation.  It must do that first.
            //congressNation cannot annex one of its allies (or team members) homeland region, it may restore.

            //1. Annex Region - CN may annex one region (other than ally or team member homeland) owned by sueingNation.  2 PAP for homeland (CN can
            //      only annex one (1) homeland per congress, unless sueingNation is Spain).  1 PAP for region outside sueingNations homeland.
            //      When Spanish homeland region is annexed it goes into uprising, if not already.
            //      All regions outside sueingNations homeland must be first. Then all regions before any capital regions must be first (Except for Madrid)
            //   If sueingNation's last region is lost, it is extinct.  Lose all pieces, production points, PAP, and PAP debt.  Heros are not captured or killed.
            //      Extinct nations must be neutral to all nations and still remains a player nation if it was.  But wont have much to do.. (leave and reconnect??)

            //2. Restore 1 or 2 Region(s) - CN may up to 2 regions outside the sueingNations homelands.  Removes sueingNation ownership and uprising.
            //      If another major nations homeland return region to that nations control.  Costs 1 PAP.
            //   This action may recreate a nation whose government has gone into exile, per Annex Region.  Even if regions do not include capital
            //   Without Capital nation cannot roll for PAP, still can gain production, build, commit rolls, etc..

            //3. Free Russian Serfs - If sueingNation == Nation.RUSSIA. CN maybe cause uprisings in up to 3 Russian Homeland,
            //      owned by Russia, occupied by CN, and not un uprising.  Costs 1 PAP.

            //4. Pass - No Action, Voluntary or Forced.  Once Passed you pass all rounds following.
        }

        private void createComponents() {
            introRow = new JPanel();
            introRow.setOpaque(false);
            introRow.setLayout(new BoxLayout(introRow, BoxLayout.LINE_AXIS));
            intro = new JLabel(GameMsg.getString("congressMenu.intro"));
            intro.setForeground(LABEL_COLOR);

            actionRow = new JPanel();
            actionRow.setOpaque(false);
            actionRow.setLayout(new BoxLayout(actionRow, BoxLayout.LINE_AXIS));
            setupActions();
            actionSelector = new JComboBox(actions.toArray());
            actionSelector.addActionListener(this);
            actionSelector.setActionCommand(GameMsg.getString("actionSelector.action"));

            informationRow = new JPanel();
            informationRow.setVisible(false);
            informationRow.setOpaque(false);
            informationRow.setLayout(new BoxLayout(informationRow, BoxLayout.LINE_AXIS));
            informationRow.setBorder(BorderFactory.createEmptyBorder(ROW_GAP,0,0,0));
            informationRowTwo = new JPanel();
            informationRowTwo.setVisible(false);
            informationRowTwo.setOpaque(false);
            informationRowTwo.setLayout(new BoxLayout(informationRowTwo, BoxLayout.LINE_AXIS));
            informationLabel = new JLabel();
            informationLabelTwo = new JLabel();

            annexRow = new JPanel();
            annexRow.setVisible(false);
            annexRow.setOpaque(false);
            annexRow.setLayout(new BoxLayout(annexRow, BoxLayout.LINE_AXIS));
            annexRow.setBorder(BorderFactory.createEmptyBorder(ROW_GAP,0,0,0));
            annexSelector = new JComboBox();
            annexSelector.addActionListener(this);
            annexSelector.setActionCommand(GameMsg.getString("annexSelector.action"));

            restoreOneRow = new JPanel();
            restoreOneRow.setVisible(false);
            restoreOneRow.setOpaque(false);
            restoreOneRow.setLayout(new BoxLayout(restoreOneRow, BoxLayout.LINE_AXIS));
            restoreOneRow.setBorder(BorderFactory.createEmptyBorder(ROW_GAP,0,0,0));
            restoreOneSelector = new JComboBox();
            restoreOneSelector.addActionListener(this);
            restoreOneSelector.setActionCommand(GameMsg.getString("restoreOneSelector.action"));

            restoreTwoRow = new JPanel();
            restoreTwoRow.setVisible(false);
            restoreTwoRow.setOpaque(false);
            restoreTwoRow.setLayout(new BoxLayout(restoreTwoRow, BoxLayout.LINE_AXIS));
            restoreTwoRow.setBorder(BorderFactory.createEmptyBorder(ROW_GAP,0,0,0));
            restoreTwoSelector = new JComboBox();
            restoreTwoSelector.addActionListener(this);
            restoreTwoSelector.setActionCommand(GameMsg.getString("restoreTwoSelector.action"));

            freeSerfOneRow = new JPanel();
            freeSerfOneRow.setVisible(false);
            freeSerfOneRow.setOpaque(false);
            freeSerfOneRow.setLayout(new BoxLayout(freeSerfOneRow, BoxLayout.LINE_AXIS));
            freeSerfOneRow.setBorder(BorderFactory.createEmptyBorder(ROW_GAP,0,0,0));
            freeSerfOneSelector = new JComboBox();
            freeSerfOneSelector.addActionListener(this);
            freeSerfOneSelector.setActionCommand(GameMsg.getString("freeSerfOneSelector.action"));

            freeSerfTwoRow = new JPanel();
            freeSerfTwoRow.setVisible(false);
            freeSerfTwoRow.setOpaque(false);
            freeSerfTwoRow.setLayout(new BoxLayout(freeSerfTwoRow, BoxLayout.LINE_AXIS));
            freeSerfTwoRow.setBorder(BorderFactory.createEmptyBorder(ROW_GAP,0,0,0));
            freeSerfTwoSelector = new JComboBox();
            freeSerfTwoSelector.addActionListener(this);
            freeSerfTwoSelector.setActionCommand(GameMsg.getString("freeSerfTwoSelector.action"));

            freeSerfThreeRow = new JPanel();
            freeSerfThreeRow.setVisible(false);
            freeSerfThreeRow.setOpaque(false);
            freeSerfThreeRow.setLayout(new BoxLayout(freeSerfThreeRow, BoxLayout.LINE_AXIS));
            freeSerfThreeRow.setBorder(BorderFactory.createEmptyBorder(ROW_GAP,0,0,0));
            freeSerfThreeSelector = new JComboBox();
            freeSerfThreeSelector.addActionListener(this);
            freeSerfThreeSelector.setActionCommand(GameMsg.getString("freeSerfThreeSelector.action"));

            takeActionRow = new JPanel();
            takeActionRow.setOpaque(false);
            takeActionRow.setLayout(new BoxLayout(takeActionRow, BoxLayout.LINE_AXIS));
            takeActionRow.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
            takeActionButton = new JButton(GameMsg.getString("congress.takeAction.button"));
            takeActionButton.addActionListener(this);
            takeActionButton.setActionCommand(GameMsg.getString("congress.takeAction.action"));
        }

        private void layoutComponents() {
            introRow.add(intro);
            introRow.add(Box.createHorizontalGlue());
            add(introRow);

            add(Box.createVerticalStrut(ROW_GAP));

            actionRow.add(Box.createHorizontalGlue());
            actionRow.add(actionSelector);
            actionRow.add(Box.createHorizontalGlue());
            add(actionRow);

            informationRow.add(Box.createHorizontalGlue());
            informationRow.add(informationLabel);
            informationRow.add(Box.createHorizontalGlue());
            add(informationRow);
            add(Box.createVerticalStrut(5));
            informationRowTwo.add(Box.createHorizontalGlue());
            informationRowTwo.add(informationLabelTwo);
            informationRowTwo.add(Box.createHorizontalGlue());
            add(informationRowTwo);

            //Only Going to display 1 of the 4 sets below.
            //Annex
            annexRow.add(Box.createHorizontalGlue());
            annexRow.add(annexSelector);
            annexRow.add(Box.createHorizontalGlue());
            add(annexRow);

            //Restore
            restoreOneRow.add(Box.createHorizontalGlue());
            restoreOneRow.add(restoreOneSelector);
            restoreOneRow.add(Box.createHorizontalGlue());
            add(restoreOneRow);

            restoreTwoRow.add(Box.createHorizontalGlue());
            restoreTwoRow.add(restoreTwoSelector);
            restoreTwoRow.add(Box.createHorizontalGlue());
            add(restoreTwoRow);

            //Free Serfs
            freeSerfOneRow.add(Box.createHorizontalGlue());
            freeSerfOneRow.add(freeSerfOneSelector);
            freeSerfOneRow.add(Box.createHorizontalGlue());
            add(freeSerfOneRow);

            freeSerfTwoRow.add(Box.createHorizontalGlue());
            freeSerfTwoRow.add(freeSerfTwoSelector);
            freeSerfTwoRow.add(Box.createHorizontalGlue());
            add(freeSerfTwoRow);

            freeSerfThreeRow.add(Box.createHorizontalGlue());
            freeSerfThreeRow.add(freeSerfThreeSelector);
            freeSerfThreeRow.add(Box.createHorizontalGlue());
            add(freeSerfThreeRow);

            //Take Action Button
            add(Box.createVerticalStrut(ROW_GAP));
            takeActionRow.add(Box.createHorizontalGlue());
            takeActionRow.add(takeActionButton);
            takeActionRow.add(Box.createHorizontalGlue());
            add(takeActionRow);
        }

        private void setupActions() {
            NationInstance sueNation = controller.getNationInstance(sueingNation);
            NationInstance congNation = controller.getNationInstance(congressNation);

            annexRegions = PoliticalUtilities.getCongressAnnexRegions(sueNation, congNation);
            restoreRegions = PoliticalUtilities.getCongressRestoreRegions(sueNation);
            freeSerfRegions = PoliticalUtilities.getCongressFreeSerfRegions(sueNation, congNation);
            forcedRestoreRegions = PoliticalUtilities.getForcedRestoreRegions(sueNation, congNation);

            if (sueNation.getHomelands().contains(annexRegions.get(0)))
                annexPapCost = 2;
            else
                annexPapCost = 1;

//            testRestoreList = new ArrayList<LandRegion>();
//            testRestoreList.add(new Paris());
//            testRestoreList.add(new London());
//            testRestoreList.add(new Berlin());
//            testRestoreList.add(new Petersburg());
//            testRestoreList.add(new Vienna());

            actions = new ArrayList<String>();
            actions.add(GameMsg.getString("actionSelector.default"));

//            actions.add(GameMsg.getString("actionSelector.annex"));
//            actions.add(GameMsg.getString("actionSelector.restore"));
//            actions.add(GameMsg.getString("actionSelector.freeSerf"));

            if (forcedRestoreRegions.size() > 0) {
                actions.add(GameMsg.getString("actionSelector.restore"));
            } else {
                if (annexRegions.size() > 0)
                    actions.add(GameMsg.getString("actionSelector.annex"));
                if (restoreRegions.size() > 0)
                    actions.add(GameMsg.getString("actionSelector.restore"));
                if (freeSerfRegions.size() > 0)
                    actions.add(GameMsg.getString("actionSelector.freeSerf"));
            }
            actions.add(GameMsg.getString("actionSelector.pass"));
        }

        private void setupAnnexItems() {
            hideAll();
            informationLabel.setText(GameMsg.getString("congress.annex.info"));
            informationLabel.setForeground(LABEL_COLOR);
            informationRow.setVisible(true);
            annexSelector.removeAllItems();
            annexSelector.addItem(GameMsg.getString("congress.regionDefault"));
            for (LandRegion region : annexRegions)
                annexSelector.addItem(region);

//            annexSelector.addItem("TestRegion 2");
//            annexSelector.addItem("TestRegion 3");
//            annexSelector.addItem("TestRegion 4");
//            annexSelector.addItem("TestRegion 5");

            annexRow.setVisible(true);
            refresh();
        }

        private void setupRestoreRowOne() {
            hideAll();
            informationLabel.setText(GameMsg.getString("congress.restore.info"));
            informationLabel.setForeground(LABEL_COLOR);
            informationRow.setVisible(true);
            restoreOneSelector.removeAllItems();
            restoreOneSelector.addItem(GameMsg.getString("congress.regionDefault"));

            if (forcedRestoreRegions.size() > 0)
                for (LandRegion region : forcedRestoreRegions)
                    restoreOneSelector.addItem(region);
            else
                for (LandRegion region : restoreRegions)
                    restoreOneSelector.addItem(region);

//            for (LandRegion r : testRestoreList)
//                restoreOneSelector.addItem(r);

            restoreOneRow.setVisible(true);
            refresh();
        }

        private void setupRestoreRowTwo() {
            if (restoreOneRow.isVisible()) {
                restoreTwoSelector.removeAllItems();
                restoreTwoSelector.addItem(GameMsg.getString("congress.regionDefault"));

                if (forcedRestoreRegions.size() > 1) {
                    for (LandRegion region : forcedRestoreRegions)
                        if ( !restoreOneSelector.getSelectedItem().equals(region) )
                            restoreTwoSelector.addItem(region);
                } else {
                    for (LandRegion region : restoreRegions)
                        if ( !restoreOneSelector.getSelectedItem().equals(region) )
                            restoreTwoSelector.addItem(region);
                }

//                for (LandRegion r : testRestoreList)
//                    if ( !restoreOneSelector.getSelectedItem().equals(r) )
//                        restoreTwoSelector.addItem(r);

                restoreTwoRow.setVisible(true);
                refresh();
            }
        }

        private void setupFreeSerfRowOne() {
            hideAll();
            informationLabel.setText(GameMsg.getString("congress.freeSerf.info.1"));
            informationLabelTwo.setText(GameMsg.getString("congress.freeSerf.info.2"));
            informationLabel.setForeground(LABEL_COLOR);
            informationLabelTwo.setForeground(LABEL_COLOR);
            informationRowTwo.setVisible(true);
            informationRow.setVisible(true);
            freeSerfOneSelector.removeAllItems();
            freeSerfOneSelector.addItem(GameMsg.getString("congress.regionDefault"));

            for (LandRegion region : freeSerfRegions)
                freeSerfOneSelector.addItem(region);

//            freeSerfOneSelector.addItem("TestRegion 2");
//            freeSerfOneSelector.addItem("TestRegion 3");
//            freeSerfOneSelector.addItem("TestRegion 4");
//            freeSerfOneSelector.addItem("TestRegion 5");
//            freeSerfOneSelector.addItem("TestRegion 6");
//            freeSerfOneSelector.addItem("TestRegion 7");
//            freeSerfOneSelector.addItem("TestRegion 8");
            freeSerfOneRow.setVisible(true);
            refresh();
        }

        private void setupFreeSerfRowTwo() {
            if (freeSerfOneRow.isVisible()) {
                freeSerfTwoSelector.removeAllItems();
                freeSerfTwoSelector.addItem(GameMsg.getString("congress.regionDefault"));

                for (LandRegion region : freeSerfRegions)
                    if ( !freeSerfOneSelector.getSelectedItem().equals(region) )
                        freeSerfTwoSelector.addItem(region);

//                freeSerfTwoSelector.addItem("TestRegion 2");
//                freeSerfTwoSelector.addItem("TestRegion 3");
//                freeSerfTwoSelector.addItem("TestRegion 4");
//                freeSerfTwoSelector.addItem("TestRegion 5");
//                freeSerfTwoSelector.addItem("TestRegion 6");
//                freeSerfTwoSelector.addItem("TestRegion 7");
//                freeSerfTwoSelector.addItem("TestRegion 8");
                freeSerfTwoRow.setVisible(true);
                refresh();
            }
        }

        private void setupFreeSerfRowThree() {
            if (freeSerfTwoRow.isVisible()) {
                freeSerfThreeSelector.removeAllItems();
                freeSerfThreeSelector.addItem(GameMsg.getString("congress.regionDefault"));

                for (LandRegion region : freeSerfRegions)
                    if ( !freeSerfOneSelector.getSelectedItem().equals(region) && !freeSerfTwoSelector.getSelectedItem().equals(region) )
                        freeSerfThreeSelector.addItem(region);
//                freeSerfThreeSelector.addItem("TestRegion 2");
//                freeSerfThreeSelector.addItem("TestRegion 3");
//                freeSerfThreeSelector.addItem("TestRegion 4");
//                freeSerfThreeSelector.addItem("TestRegion 5");
//                freeSerfThreeSelector.addItem("TestRegion 6");
//                freeSerfThreeSelector.addItem("TestRegion 7");
//                freeSerfThreeSelector.addItem("TestRegion 8");
                freeSerfThreeRow.setVisible(true);
                refresh();
            }
        }

        private void hideFreeSerfRowThree() {
            freeSerfThreeRow.setVisible(false);
            refresh();
        }

        private void setupPassLabel() {
            hideAll();
            informationLabel.setText(GameMsg.getString("congress.passWarning.1"));
            informationLabelTwo.setText(GameMsg.getString("congress.passWarning.2"));
            informationLabel.setForeground(RED_COLOR);
            informationLabelTwo.setForeground(RED_COLOR);
            informationRowTwo.setVisible(true);
            informationRow.setVisible(true);
            refresh();
        }

        private void refresh() {
            pack();
            repaint();
        }

        private void hideAll() {
            informationRow.setVisible(false);
            informationRowTwo.setVisible(false);
            annexRow.setVisible(false);
            restoreOneRow.setVisible(false);
            restoreTwoRow.setVisible(false);
            freeSerfOneRow.setVisible(false);
            freeSerfTwoRow.setVisible(false);
            freeSerfThreeRow.setVisible(false);
            refresh();
        }

        private void takeAction() {
            //First, check which action
            //Check to make sure at least one region is selected.

            //If Annex, show confirm dialog
            //If Restore, check if two regions choosen
            //  If only one region choosen, add that in confirm dialog
            //If Free, check if three regions choosen
            //  If only one or two choosen, add that in confirm dialog
            //If Pass, reiterate the removal from congress
            String actionType = (String)actionSelector.getSelectedItem();

            if ( actionType.equals(GameMsg.getString("actionSelector.annex")) ) {
                if ( annexSelector.getSelectedIndex() > 0 ) {
                    LandRegion one = (LandRegion)annexSelector.getSelectedItem();

                    int choice = MessageDialog.confirmCongressAnnex(controller.getDisplay().getMap(), sueingNation, one, annexPapCost);
                    if ( choice == JOptionPane.YES_OPTION )
                        new CongressActionThread(controller.getGameId(), sueingNation, turnIndex, congressNation,
                                controller.game.getMap().getRegionIndex(one)).start();

                } else {
                    MessageDialog.congressSelectRegion(controller.getDisplay().getMap());
                }
            } else if ( actionType.equals(GameMsg.getString("actionSelector.restore")) ) {
                if ( restoreOneSelector.getSelectedIndex() > 0 ) {
                    LandRegion one = (LandRegion)restoreOneSelector.getSelectedItem();
                    LandRegion two = (restoreTwoSelector.getSelectedIndex() > 0) ? (LandRegion)restoreTwoSelector.getSelectedItem() : null;

                    int choice = MessageDialog.confirmCongressRestore(controller.getDisplay().getMap(), sueingNation, one, two);

                    if ( choice == JOptionPane.YES_OPTION ) {
                        int twoIndex = (two == null) ? -1 : controller.game.getMap().getRegionIndex(two);
                        new CongressActionThread(controller.getGameId(), sueingNation, turnIndex, congressNation,
                                controller.game.getMap().getRegionIndex(one), twoIndex).start();
                    }

                } else {
                    MessageDialog.congressSelectRegion(controller.getDisplay().getMap());
                }
            } else if ( actionType.equals(GameMsg.getString("actionSelector.freeSerf")) ) {
                if ( freeSerfOneSelector.getSelectedIndex() > 0 ) {
                    LandRegion one   = (LandRegion)freeSerfOneSelector.getSelectedItem();
                    LandRegion two   = (freeSerfTwoSelector.getSelectedIndex() > 0) ? (LandRegion)freeSerfTwoSelector.getSelectedItem() : null;
                    LandRegion three = (freeSerfThreeSelector.getSelectedIndex() > 0) ? (LandRegion)freeSerfThreeSelector.getSelectedItem() : null;

                    int choice = MessageDialog.confirmCongressFreeSerfs(controller.getDisplay().getMap(), one, two, three);

                    if ( choice == JOptionPane.YES_OPTION ) {
                        int twoIndex = (two == null) ? -1 : controller.game.getMap().getRegionIndex(two);
                        int threeIndex = (three == null) ? -1 : controller.game.getMap().getRegionIndex(three);
                        new CongressActionThread(controller.getGameId(), sueingNation, turnIndex, congressNation,
                                controller.game.getMap().getRegionIndex(one), twoIndex, threeIndex).start();
                    }

                } else {
                    MessageDialog.congressSelectRegion(controller.getDisplay().getMap());
                }
            } else if ( actionType.equals(GameMsg.getString("actionSelector.pass")) ) {
                int choice = MessageDialog.confirmCongressPass(controller.getDisplay().getMap());
                if ( choice == JOptionPane.YES_OPTION )
                    new CongressActionThread(controller.getGameId(), sueingNation, turnIndex, congressNation).start();
            }
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals(GameMsg.getString("congress.takeAction.action"))) {
                takeAction();
            } else {
                JComboBox cb = (JComboBox)e.getSource();

                if ( e.getActionCommand().equals(GameMsg.getString("actionSelector.action")) ) {
                    fromActionSelector = true;
                    if (cb.getSelectedItem().equals(GameMsg.getString("actionSelector.annex"))) {
                        setupAnnexItems();
                    } else if (cb.getSelectedItem().equals(GameMsg.getString("actionSelector.restore"))) {
                        setupRestoreRowOne();
                    } else if (cb.getSelectedItem().equals(GameMsg.getString("actionSelector.freeSerf"))) {
                        setupFreeSerfRowOne();
                    } else if (cb.getSelectedItem().equals(GameMsg.getString("actionSelector.pass"))) {
                        setupPassLabel();
                    } else
                        hideAll();
                    fromActionSelector = false;
                } else if ( e.getActionCommand().equals(GameMsg.getString("annexSelector.action")) && !fromActionSelector ) {
                    //Nothing
                } else if ( e.getActionCommand().equals(GameMsg.getString("restoreOneSelector.action")) && !fromActionSelector ) {
                    if (cb.getSelectedIndex() == 0)
                        setupRestoreRowOne();
                    else
                        setupRestoreRowTwo();
                } else if ( e.getActionCommand().equals(GameMsg.getString("restoreTwoSelector.action")) ) {
                    //Nothing
                } else if ( e.getActionCommand().equals(GameMsg.getString("freeSerfOneSelector.action")) && !fromActionSelector ) {
                    if (cb.getSelectedIndex() == 0)
                        setupFreeSerfRowOne();
                    else
                        setupFreeSerfRowTwo();
                } else if ( e.getActionCommand().equals(GameMsg.getString("freeSerfTwoSelector.action")) ) {
                    if (cb.getSelectedIndex() == 0)
                        hideFreeSerfRowThree();
                    else
                        setupFreeSerfRowThree();
                } else if ( e.getActionCommand().equals(GameMsg.getString("freeSerfThreeSelector.action")) ) {
                    //Nothing
                }
            }
        }

        private JPanel introRow;
        private JLabel intro;

        private JPanel actionRow;
        private JComboBox actionSelector;
        private ArrayList<String> actions;

        private JPanel informationRow;
        private JLabel informationLabel;
        private JPanel informationRowTwo;
        private JLabel informationLabelTwo;

        private JPanel annexRow;
        private JComboBox annexSelector;
        private ArrayList<LandRegion> annexRegions;

        private JPanel restoreOneRow;
        private JPanel restoreTwoRow;
        private JComboBox restoreOneSelector;
        private JComboBox restoreTwoSelector;
//        private ArrayList<LandRegion> testRestoreList;
        private ArrayList<LandRegion> restoreRegions;
        private ArrayList<LandRegion> forcedRestoreRegions;

        private JPanel freeSerfOneRow;
        private JPanel freeSerfTwoRow;
        private JPanel freeSerfThreeRow;
        private JComboBox freeSerfOneSelector;
        private JComboBox freeSerfTwoSelector;
        private JComboBox freeSerfThreeSelector;
        private ArrayList<LandRegion> freeSerfRegions;

        private JPanel takeActionRow;
        private JButton takeActionButton;

        private int annexPapCost;
        private boolean fromActionSelector = false;

        private final Color LABEL_COLOR = new Color(51,51,51);
        private final Color RED_COLOR = new Color(150,20,20);
        private final int ROW_GAP = 15;
    }
}