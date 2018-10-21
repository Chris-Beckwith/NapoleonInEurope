package lobby.gui;

import lobby.controller.NapoleonController;

import javax.swing.*;
import java.awt.*;

/**
 * GameLobby.java  Date Created: Mar 20, 2012
 *
 * Purpose: Displays the menus needed to display a lobby.
 *
 * Description: This is a layered panel, where the main lobby is one panel,
 * and the nation description is another panel that displays on top of
 * the lobby panel.
 *
 * @author Chrisb
 */
public class GameLobby extends JLayeredPane {

    private NapoleonController controller;
    private Lobby lobby;
    private NationDescription nationDesc;
    private JComponent parent;

    public GameLobby(NapoleonController controller, JPanel lobby, JPanel countryDesc, JComponent parent) {
        this.controller = controller;
        this.lobby = (Lobby)lobby;
        this.nationDesc = (NationDescription)countryDesc;
        this.parent = parent;

        //TODO
        setPreferredSize(new Dimension(800,725));

        //Create components
        createComponents();

        //Layout components
        layoutComponents();

        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    private void layoutComponents() {
        add(lobby, new Integer(0));
        add(nationDesc, new Integer(1));
    }

    private void createComponents() {
        lobby.setBounds(0,0,800,725);

        nationDesc.setBounds(125,125,250,250);
        nationDesc.setVisible(false);
    }

    public JComponent getParentMenu() {
        return parent;
    }
}
