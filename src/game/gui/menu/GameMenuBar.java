package game.gui.menu;

import game.controller.DisplayController;
import game.controller.GameController;
import game.util.EventLogger;
import game.util.GameMsg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * GameMenuBar.java  Date Created: Sep 17, 2013
 *
 * Purpose: The menu bar for the in-game window.
 *
 * Description:
 *
 * @author Chrisb
 */
public class GameMenuBar extends JMenuBar implements ActionListener {
    public GameMenuBar(GameController controller, DisplayController display) {
        super();
        this.controller = controller;
        this.display = display;
        createMenus();
    }

    public void createMenus() {
        //Game
        JMenu menu = new JMenu("Game");
        menu.setMnemonic(KeyEvent.VK_G);
        menu.getAccessibleContext().setAccessibleDescription("Basic Game Menu");
        add(menu);

        endStepItem = new JMenuItem("End 'Current Step'", KeyEvent.VK_E);
        endStepItem.addActionListener(this);
        endStepItem.setActionCommand(GameMsg.getString("endStep.action"));
        endStepItem.getAccessibleContext().setAccessibleDescription("End the current step");
        menu.add(endStepItem);

        JMenuItem menuItem = new JMenuItem("My National Summary", KeyEvent.VK_N);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("mySummary.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menu.add(menuItem);

        menuItem = new JMenuItem("My Commander Info", KeyEvent.VK_C);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("myCommander.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menu.add(menuItem);

        battleWindowItem = new JMenuItem("Battle Window", KeyEvent.VK_B);
        battleWindowItem.addActionListener(this);
        battleWindowItem.setActionCommand(GameMsg.getString("battleWindow.action"));
        battleWindowItem.getAccessibleContext().setAccessibleDescription("Display the battle window");
        battleWindowItem.setEnabled(false);
        menu.add(battleWindowItem);

        menuItem = new JMenuItem("Save", KeyEvent.VK_S);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("save.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menu.add(menuItem);

        menuItem = new JMenuItem("Quit", KeyEvent.VK_Q);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("quit.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menu.add(menuItem);

        //Info
        menu = new JMenu("Info");
        menu.setMnemonic(KeyEvent.VK_I);
        menu.getAccessibleContext().setAccessibleDescription("Information Menu");
        add(menu);

        menuItem = new JMenuItem("Events Log", KeyEvent.VK_E);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("eventLog.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("View the events log");
        menu.add(menuItem);

        menuItem = new JMenuItem("Political Status", KeyEvent.VK_P);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("politicalStatus.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menu.add(menuItem);

        menuItem = new JMenuItem("Nation Summary", KeyEvent.VK_N);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("nationSummary.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("View summary of a nation");
        menu.add(menuItem);

        menuItem = new JMenuItem("Commander Card", KeyEvent.VK_C);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("commanderInfo.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menu.add(menuItem);

        //Actions
        menu = new JMenu("Political Actions");
        menu.setMnemonic(KeyEvent.VK_P);
        menu.getAccessibleContext().setAccessibleDescription("Political Action Menu");
        add(menu);

        menuItem = new JMenuItem("Declare War", KeyEvent.VK_W);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("declare.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Sue for Peace", KeyEvent.VK_P);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("suePeace.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Conclude Armistice", KeyEvent.VK_C);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("armistice.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Form Alliance", KeyEvent.VK_F);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("formAlly.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Break Alliance", KeyEvent.VK_B);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("breakAlly.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Recruit Minor Nation", KeyEvent.VK_M);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("recruit.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Annex Minor Nation", KeyEvent.VK_A);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("annex.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Restore Region", KeyEvent.VK_R);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("restore.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Abandon Region", KeyEvent.VK_D);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("abandon.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Foment Uprising", KeyEvent.VK_U);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("foment.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Suppress Uprising", KeyEvent.VK_S);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("suppress.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Grant Right of Passage", KeyEvent.VK_G);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("grantRights.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Rescind Right of Passage", KeyEvent.VK_P);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("rescindRights.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Control Non-Player Nation", KeyEvent.VK_N);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("controlNPN.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menu.add(menuItem);

        //Help
        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);
        menu.getAccessibleContext().setAccessibleDescription("Get your help here");
        add(menu);

        menuItem = new JMenuItem("Definitions", KeyEvent.VK_D);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("def.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("Game Term Definitions");
        menu.add(menuItem);

        menuItem = new JMenuItem("About", KeyEvent.VK_A);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("about.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("About the game");
        menu.add(menuItem);

        menuItem = new JMenuItem("Help", KeyEvent.VK_H);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(GameMsg.getString("help.action"));
        menuItem.getAccessibleContext().setAccessibleDescription("Help Doc");
        menu.add(menuItem);
    }

    public void setEndStepText(String text) { endStepItem.setText(text); }
    public void setBattleWindowEnable(boolean isEnable) { battleWindowItem.setEnabled(isEnable); }

    private GameController controller;
    private DisplayController display;

    private JMenuItem endStepItem;
    private JMenuItem battleWindowItem;

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        //Game Actions
        if (cmd.equals(GameMsg.getString("endStep.action"))) {
            display.endStepDialog();
        } else if (cmd.equals(GameMsg.getString("mySummary.action"))) {
            display.showNationSummary(controller.getUserNation());
        } else if (cmd.equals(GameMsg.getString("myCommander.action"))) {
            display.showCommCard(controller.getUserNation());
        } else if (cmd.equals(GameMsg.getString("battleWindow.action"))) {
            //todo display battle window
        } else if (cmd.equals(GameMsg.getString("save.action"))) {
            controller.save();
        } else if (cmd.equals(GameMsg.getString("quit.action"))) {
            display.quit();
        //Info Actions
        } else if (cmd.equals(GameMsg.getString("eventLog.action"))) {
            display.showEventLog();
        } else if (cmd.equals(GameMsg.getString("politicalStatus.action"))) {
            display.showPoliticalStatus();
        } else if (cmd.equals(GameMsg.getString("nationSummary.action"))) {
            display.showPickANationPanel(display.SUMMARY, -1);
        } else if (cmd.equals(GameMsg.getString("commanderInfo.action"))) {
            display.showPickANationPanel(display.COMMANDER, -1);
        //Political Actions
        } else if (cmd.equals(GameMsg.getString("declare.action"))) {
            display.showPickANationPanel(EventLogger.DECLARE_WAR, -1);
        } else if (cmd.equals(GameMsg.getString("suePeace.action"))) {
            display.showPickANationPanel(EventLogger.SUE_FOR_PEACE, -1);
        } else if (cmd.equals(GameMsg.getString("armistice.action"))) {
            display.showPickANationPanel(EventLogger.CONCLUDE_ARMISTICE, -1);
        } else if (cmd.equals(GameMsg.getString("formAlly.action"))) {
            display.showPickANationPanel(EventLogger.FORM_ALLIANCE, -1);
        } else if (cmd.equals(GameMsg.getString("breakAlly.action"))) {
            display.showPickANationPanel(EventLogger.BREAK_ALLIANCE, -1);
        } else if (cmd.equals(GameMsg.getString("recruit.action"))) {
//            display.showPickRegionPanel(EventLogger.RECRUIT_MINOR);
        } else if (cmd.equals(GameMsg.getString("annex.action"))) {
//            display.showPickRegionPanel(EventLogger.ANNEX_MINOR);
        } else if (cmd.equals(GameMsg.getString("restore.action"))) {
//            display.showPickRegionPanel(EventLogger.RESTORE_REGION);
        } else if (cmd.equals(GameMsg.getString("abandon.action"))) {
//            display.showPickRegionPanel(EventLogger.ABANDON_REGION);
        } else if (cmd.equals(GameMsg.getString("foment.action"))) {
//            display.showPickRegionPanel(EventLogger.FOMENT_UPRISING);
        } else if (cmd.equals(GameMsg.getString("suppress.action"))) {
//            display.showPickRegionPanel(EventLogger.SUPPRESS_UPRISING);
        } else if (cmd.equals(GameMsg.getString("grantRights.action"))) {
            display.showPickANationPanel(EventLogger.GRANT_PASSAGE, -1);
        } else if (cmd.equals(GameMsg.getString("rescindRights.action"))) {
            display.showPickANationPanel(EventLogger.RESCIND_PASSAGE, -1);
        } else if (cmd.equals(GameMsg.getString("controlNPN.action"))) {
            display.showPickANationPanel(EventLogger.CONTROL_NPN, controller.getUserNation());
        //Help Actions
        } else if (cmd.equals(GameMsg.getString("def.action"))) {
            display.showDefinitionsPanel();
        } else if (cmd.equals(GameMsg.getString("about.action"))) {
            display.showAboutPanel();
        } else if (cmd.equals(GameMsg.getString("help.action"))) {
            display.showHelpDoc();
        }
    }
}