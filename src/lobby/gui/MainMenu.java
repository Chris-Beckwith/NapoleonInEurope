package lobby.gui;

import lobby.controller.NapoleonController;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

import util.Messages;

/**
 * MainMenu.java  Date Created: Apr 03, 2012
 *
 * Purpose: Display the main menu.
 *
 * Description: This is the first menu the user sees upon
 * successfully logging in.
 *
 * @author Chrisb
 */
public class MainMenu extends JPanel implements ActionListener {

    private NapoleonController controller;
    private JComponent parent;

    public MainMenu(NapoleonController controller, JComponent parent) {
        super(new GridBagLayout());

        this.controller = controller;
        this.parent = parent;

        //Create components
        createComponents();

        //Layout components
        layoutComponents();

        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    private void layoutComponents() {
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,10,0,0);
        c.gridx = 0;
        c.gridy = 0;
        add(create, c);

        c.gridy = 1;
        add(join, c);

    }

    private void createComponents() {
        create = new JButton();
        create.setText(Messages.getString("create.button"));
        create.setActionCommand(Messages.getString("create.action"));
        create.addActionListener(this);

        join = new JButton();
        join.setText(Messages.getString("join.button"));
        join.setActionCommand(Messages.getString("join.action"));
        join.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.compareTo(Messages.getString("create.action")) == 0)
            controller.showCreateLobby();
        else if (cmd.compareTo(Messages.getString("join.action")) == 0)
            controller.showJoinLobby();
    }

    private JButton create;
    private JButton join;
}
