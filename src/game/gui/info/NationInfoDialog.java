package game.gui.info;

import game.controller.GameController;
import game.controller.NationInstance;
import game.controller.DisplayController;
import game.gui.listeners.UndecoratedMouseListener;
import game.util.GameMsg;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import lobby.controller.User;

/**
 * NationInfoDialog.java  Date Created: 09 14, 2013
 *
 * Purpose: To display a nation's asset info.
 *
 * Description:  This class is the frame that will hold the panel.
 *
 * @author Chris
 */
public class NationInfoDialog extends JDialog implements KeyListener {
    public NationInfoDialog(NationInstance nation, GameController controller) {
        super();
        UndecoratedMouseListener listener = new UndecoratedMouseListener(this);
        nationInfoPanel = new NationInfoPanel(nation, controller);
        addMouseListener(listener);
        addMouseMotionListener(listener);
        addKeyListener(this);
        setVisible(true);
        setResizable(false);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(nationInfoPanel);
        setTitle(GameMsg.getString("nation.pos." + nation.getNationNumber()) + " Summary");
    }

    public void updateSummary() { nationInfoPanel.updateSummary(); }

    public NationInfoPanel nationInfoPanel;

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if ( e.getKeyCode() == KeyEvent.VK_ESCAPE )
            dispose();
    }
    public void keyReleased(KeyEvent e) {}

    /**
     * NationInfoPanel.java  Date Created: 09 14, 2013
     *
     * Purpose: To display a nation's asset info.
     *
     * Description:  This class will display all information relavent to a nation.
     * Including: Military, Territory, Production Value, Production Saved, Paps,
     * Maybe: War Dec, Alliances, Grace, Reverse Grace
     *
     * @author Chris
     */
    private class NationInfoPanel extends JPanel {
        private NationInstance nation;
        private GameController controller;

        private NationInfoPanel(NationInstance nation, GameController controller) {//todo if user is Null...not a user controlled nation
            this.nation = nation;
            this.controller = controller;
            int nationNumber = nation.getNationNumber();
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            setBackground(Color.GRAY);
            setBorder( new CompoundBorder((new CompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4), BorderFactory.createEmptyBorder(5,5,5,5))),
                new CompoundBorder(new TitledBorder(GameMsg.getString("nation.pos." + nationNumber) + " " + GameMsg.getString("nationSummary")), BorderFactory.createEmptyBorder(0,5,5,5))) );

            //Create Components
            userInfo = new NationUserInfo();
            unitInfo = new NationUnitInfo(nationNumber);
            regionInfo = new NationRegionInfo(nationNumber);
            productionInfo = new NationProductInfo();
            //Layout Components
            add(userInfo);
            add(unitInfo);
            add(regionInfo);
            add(productionInfo);
            updateSummary();
        }

        public void updateSummary() {
            //Controlling User
            userInfo.updateUserInfo(nation, controller);
            //Military
            unitInfo.setUnits(nation.getMilitary());
            //Territory
            regionInfo.setRegions(nation.getTerritory());
            //Production
            productionInfo.setProducts(nation.getProductionValue(), nation.getProductionPoints(), nation.getPaps());
        }

        private NationUserInfo userInfo;
        private NationUnitInfo unitInfo;
        private NationRegionInfo regionInfo;
        private NationProductInfo productionInfo;
    }

    private class NationUserInfo extends JPanel {
        private JLabel controllingUser;
        private JPanel controlledNPNPanel;

        private NationUserInfo() {
            setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            setOpaque(false);

            controllingUser = new JLabel();
            controllingUser.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
            controllingUser.setForeground(Color.LIGHT_GRAY);

            JPanel controllingUserPanel = new JPanel();
            controllingUserPanel.setLayout(new BoxLayout(controllingUserPanel, BoxLayout.LINE_AXIS));
            controllingUserPanel.setOpaque(false);

            controllingUserPanel.add(Box.createHorizontalStrut(5));
            controllingUserPanel.add(new JLabel(GameMsg.getString("ns.controllingUser")));
            controllingUserPanel.add(Box.createHorizontalStrut(5));
            controllingUserPanel.add(controllingUser);
            controllingUserPanel.add(Box.createHorizontalGlue());

            add(controllingUserPanel);

            //ControlledNPNs
            controlledNPNPanel = new JPanel();
            controlledNPNPanel.setLayout(new BoxLayout(controlledNPNPanel, BoxLayout.PAGE_AXIS));
            controlledNPNPanel.setOpaque(false);
            controlledNPNPanel.setVisible(false);

            add(controlledNPNPanel);
        }

        private void updateUserInfo(NationInstance nation, GameController controller) {
            controllingUser.setText(nation.getControllingUser());

            User user = controller.game.getUserByNation(nation.getNationNumber());
            if (user != null && user.getControlledNPNs().size() > 0) {

                controlledNPNPanel.removeAll();
                controlledNPNPanel.setVisible(true);
                JPanel headerRow = new JPanel();
                headerRow.setLayout(new BoxLayout(headerRow, BoxLayout.LINE_AXIS));
                headerRow.setOpaque(false);
                headerRow.add(Box.createHorizontalStrut(5));
                headerRow.add(new JLabel(GameMsg.getString("ns.controlledNPNs")));
                headerRow.add(Box.createHorizontalGlue());
                controlledNPNPanel.add(Box.createVerticalStrut(5));
                controlledNPNPanel.add(headerRow);
                controlledNPNPanel.add(Box.createVerticalStrut(5));

                for (Integer i : user.getControlledNPNs()) {
                    JPanel row = new JPanel();
                    row.setLayout(new BoxLayout(row, BoxLayout.LINE_AXIS));
                    row.setOpaque(false);
                    row.add(Box.createHorizontalStrut(25));
                    JLabel nationLabel = new JLabel(GameMsg.getString("nation." + i));
                    nationLabel.setForeground(DisplayController.getNationColor(i));
                    row.add(nationLabel);
                    row.add(Box.createHorizontalGlue());
                    controlledNPNPanel.add(row);
                }
            } else
                controlledNPNPanel.setVisible(false);
        }
    }
}
