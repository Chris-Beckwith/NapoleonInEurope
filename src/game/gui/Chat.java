package game.gui;

import game.controller.DisplayController;
import game.controller.GameController;

import javax.swing.*;
import java.awt.*;

/**
 * Chat.java  Date Created: Oct 24, 2012
 *
 * Purpose: To display and send chat messages throughout the game.
 *
 * Description: This panel shall contain two (2) panels.  One will be used
 * to display chat messages and have a field to send chat messages.  Second,
 * will be to select where the user would like to send the chat messages.
 *
 * @author Chrisb
 */
public class Chat extends JPanel {

    /**
     * Constructor
     */
    public Chat(GameController controller, DisplayController dControl) {
        super();
        this.controller = controller;
        this.dControl = dControl;

        createComponents();
        layoutComponents();

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBackground(Color.DARK_GRAY);
        setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
    }

    private void createComponents() {
        chatWindow = new ChatWindow(controller, dControl);
        chatChooser = new ChatChooser(controller, dControl);
    }

    private void layoutComponents() {
        add(chatWindow);
        add(chatChooser);
    }

    private GameController controller;
    private DisplayController dControl;

    public ChatWindow chatWindow;
    public ChatChooser chatChooser;
}