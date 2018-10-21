package game.gui.menu;

import game.controller.DisplayController;
import game.controller.GameController;
import game.util.GameMsg;
import game.util.EventLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * NationPopupMenu.java  Date Created: Sep 17, 2013
 *
 * Purpose: To display the nation popup menu.
 *
 * Description: This menu will show the actions available to take related to each nation.
 * This menu will show when right clicking on a nation from the chat chooser.
 *
 * @author Chrisb
 */
public class NationPopupMenu extends JPopupMenu implements ActionListener {
    private GameController controller;
    private DisplayController display;
    private int popupNation;

    public NationPopupMenu(GameController controller, DisplayController display) {
        super();
        this.controller = controller;
        this.display = display;

        JMenuItem item = new JMenuItem(GameMsg.getString("menu.declare"));
        item.addActionListener(this);
        item.setActionCommand(GameMsg.getString("declare.action"));
        add(item);

        item = new JMenuItem(GameMsg.getString("menu.armistice"));
        item.addActionListener(this);
        item.setActionCommand(GameMsg.getString("armistice.action"));
        add(item);

        item = new JMenuItem(GameMsg.getString("menu.formAlly"));
        item.addActionListener(this);
        item.setActionCommand(GameMsg.getString("formAlly.action"));
        add(item);

        item = new JMenuItem(GameMsg.getString("menu.breakAlly"));
        item.addActionListener(this);
        item.setActionCommand(GameMsg.getString("breakAlly.action"));
        add(item);

        item = new JMenuItem(GameMsg.getString("menu.grantRights"));
        item.addActionListener(this);
        item.setActionCommand(GameMsg.getString("grantRights.action"));
        add(item);

        item = new JMenuItem(GameMsg.getString("menu.rescindRights"));
        item.addActionListener(this);
        item.setActionCommand(GameMsg.getString("rescindRights.action"));
        add(item);

        item = new JMenuItem(GameMsg.getString("menu.nationSummary"));
        item.addActionListener(this);
        item.setActionCommand(GameMsg.getString("nationSummary.action"));
        add(item);

        item = new JMenuItem(GameMsg.getString("menu.commanderInfo"));
        item.addActionListener(this);
        item.setActionCommand(GameMsg.getString("commanderInfo.action"));
        add(item);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals(GameMsg.getString("declare.action"))) {
            controller.political.takePoliticalAction(EventLogger.DECLARE_WAR, controller.getUserNation(), popupNation);

        } else if (cmd.equals(GameMsg.getString("armistice.action"))) {
            Object[] options = {GameMsg.getString("yes.political.2"), GameMsg.getString("no.political")};
            int armChoice = JOptionPane.showOptionDialog(display.getMap(), GameMsg.getString("question.political.2") + " "
                    + GameMsg.getString("nation." + popupNation) + "?",
                    GameMsg.getString("title.political.2"), JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);
            if (armChoice == JOptionPane.YES_OPTION) {
                //todo request armistice
            }
        } else if (cmd.equals(GameMsg.getString("formAlly.action"))) {
            Object[] options = {GameMsg.getString("yes.political.3"), GameMsg.getString("no.political")};
            int formChoice = JOptionPane.showOptionDialog(display.getMap(), GameMsg.getString("question.political.3") + " "
                    + GameMsg.getString("nation." + popupNation) + "?",
                    GameMsg.getString("title.political.3"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);
            if (formChoice == JOptionPane.YES_OPTION) {
                //todo request alliance
            }
        } else if (cmd.equals(GameMsg.getString("breakAlly.action"))) {
            Object[] options = {GameMsg.getString("yes.political.4"), GameMsg.getString("no.political")};
            int breakChoice = JOptionPane.showOptionDialog(display.getMap(), GameMsg.getString("question.political.4") + " "
                    + GameMsg.getString("nation." + popupNation) + "?",
                    GameMsg.getString("title.political.4"), JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE,
                    null, options, options[1]);
            if (breakChoice == JOptionPane.YES_OPTION) {
                //todo break alliance
            }
        } else if (cmd.equals(GameMsg.getString("grantRights.action"))) {
            Object[] options = {GameMsg.getString("yes.political.11"), GameMsg.getString("no.political")};
            int grantChoice = JOptionPane.showOptionDialog(display.getMap(), GameMsg.getString("question.political.11") + " "
                    + GameMsg.getString("nation." + popupNation) + "?",
                    GameMsg.getString("title.political.11"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);
            if (grantChoice == JOptionPane.YES_OPTION) {
                //todo grant rights
            }
        } else if (cmd.equals(GameMsg.getString("rescindRights.action"))) {
            Object[] options = {GameMsg.getString("yes.political.12"), GameMsg.getString("no.political")};
            int breakChoice = JOptionPane.showOptionDialog(display.getMap(), GameMsg.getString("question.political.12") + " "
                    + GameMsg.getString("nation." + popupNation) + "?",
                    GameMsg.getString("title.political.12"), JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE,
                    null, options, options[1]);
            if (breakChoice == JOptionPane.YES_OPTION) {
                //todo rescind rights
            }
        } else if (cmd.equals(GameMsg.getString("nationSummary.action")))
            display.showNationSummary(popupNation);
        else if (cmd.equals(GameMsg.getString("commanderInfo.action")))
            display.showCommCard(popupNation);
    }

    public void setPopupNation(int nation) { popupNation = nation; }
}