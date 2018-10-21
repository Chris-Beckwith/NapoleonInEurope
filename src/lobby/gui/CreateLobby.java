package lobby.gui;

import lobby.controller.NapoleonController;
import lobby.controller.LobbyInstance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import util.Messages;

/**
 * CreateLobby.java  Date Created: Apr 18, 2012
 *
 * Purpose: Display the create lobby screen.
 *
 * Description: This panel will display the screen used
 * to create a new lobby, basically only the game name.
 *
 * @author Chrisb
 */
public class CreateLobby extends JPanel implements ActionListener, KeyListener {

    private JButton create;
    private JButton cancel;
    private JTextField lobbyName;
    private JLabel description;
    private JLabel errorMsg;
    private JComponent parent;

    private NapoleonController controller;

    public CreateLobby(NapoleonController controller, JComponent parent) {
        super(new GridBagLayout());       

        this.controller = controller;
        this.parent = parent;

        //Create components
        createComponents();

        //Layout components
        layoutComponents();

        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    private void createComponents() {
        create = new JButton(Messages.getString("cl.create.button"));
        create.setActionCommand(Messages.getString("cl.create.action"));
        create.addActionListener(this);

        cancel = new JButton(Messages.getString("cl.cancel.button"));
        cancel.setActionCommand(Messages.getString("cl.cancel.action"));
        cancel.addActionListener(this);

        lobbyName = new JTextField();
        lobbyName.setColumns(20);
        lobbyName.setName(Messages.getString("cl.lobbyName.name"));
        lobbyName.addKeyListener(this);

        description = new JLabel();
        description.setText(Messages.getString("cl.description.msg"));

        errorMsg = new JLabel();
        errorMsg.setForeground(Color.RED);
        errorMsg.setVisible(false);
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(description, gbc);

        gbc.gridy = 1;
        add(lobbyName, gbc);

        gbc.gridy = 2;
        add(errorMsg, gbc);

        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(cancel, gbc);

        gbc.gridx = 1;
        add(create, gbc);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals(Messages.getString("cl.create.action"))) {
            //Check that lobby name is not too long and that one was entered.
            if (lobbyName.getText().length() > LobbyInstance.MAX_NAME_LENGTH) {
                errorMsg.setText(Messages.getString("cl.errorMsg.toLong"));
                errorMsg.setVisible(true);
                controller.refreshDisplay();
            } else if (lobbyName.getText().length() == 0) {
                errorMsg.setText(Messages.getString("cl.errorMsg.enterName"));
                errorMsg.setVisible(true);
                controller.refreshDisplay();
            } else {
                errorMsg.setText("");
                errorMsg.setVisible(false);
                controller.createLobby(lobbyName.getText());
            }
        } else if (cmd.equals(Messages.getString("cl.cancel.action"))) {
            errorMsg.setText("");
            errorMsg.setVisible(false);
            controller.showParentMenu(parent);
        }
    }

    public void lobbyExists() {
        errorMsg.setText(Messages.getString("cl.errorMsg.lobbyExists"));
        errorMsg.setVisible(true);
    }

    public JComponent getParentMenu() {
        return parent;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if ( e.getComponent().getName().compareTo(Messages.getString("cl.lobbyName.name")) == 0 && e.getKeyCode() == KeyEvent.VK_ENTER ) {
            errorMsg.setText("");
            errorMsg.setVisible(false);
            controller.createLobby(lobbyName.getText());
        }
    }
}
