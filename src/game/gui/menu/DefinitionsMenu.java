package game.gui.menu;

import game.controller.DisplayController;
import game.controller.GameController;
import game.gui.listeners.UndecoratedMouseListener;
import game.util.GameMsg;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * DefinitionsMenu.java  Date Created: Sep 24, 2013
 *
 * Purpose: To display the definition of terms.
 *
 * Description: This menu shall display all the terms and their definitions
 * related to the game.
 *
 * @author Chrisb
 */
public class DefinitionsMenu extends JDialog implements KeyListener {
    public DefinitionsMenu(GameController controller, DisplayController display) {
        super();
        UndecoratedMouseListener listener = new UndecoratedMouseListener(this);
        setTitle(GameMsg.getString("def.title"));
        setVisible(true);
        addKeyListener(this);
        addMouseListener(listener);
        addMouseMotionListener(listener);
        setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setContentPane(new DefinitionsPanel());
    }

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if ( e.getKeyCode() == KeyEvent.VK_ESCAPE )
            dispose();
    }
    public void keyReleased(KeyEvent e) {}

    private GameController controller;
    private DisplayController display;

    private class DefinitionsPanel extends JPanel {
        private DefinitionsPanel() {
            setBackground(Color.GRAY);
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            setBorder( new CompoundBorder((new CompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4), BorderFactory.createEmptyBorder(5,5,5,5))),
                new CompoundBorder(new TitledBorder(GameMsg.getString("def.title")), BorderFactory.createEmptyBorder(0,5,5,5))) );

            JPanel defRow;
            JLabel term;
            JLabel definition;

            int numOfTerms = Integer.parseInt(GameMsg.getString("terms.number"));

            add(Box.createVerticalStrut(5));

            for (int i = 0; i < numOfTerms; i++) {
                defRow = new JPanel();
                term = new JLabel(GameMsg.getString("term." + i));
                definition = new JLabel(GameMsg.getString("def." + i));

                defRow.setLayout(new BoxLayout(defRow, BoxLayout.LINE_AXIS));
                defRow.setOpaque(false);
                term.setFont(new Font("Times New Roman", Font.BOLD, 14));
                definition.setFont(new Font("Times New Roman", Font.PLAIN, 14));

                defRow.add(term);
                defRow.add(Box.createHorizontalStrut(2));
                defRow.add(definition);
                defRow.add(Box.createHorizontalGlue());

                add(defRow);
                add(Box.createVerticalStrut(5));
            }

        }
//        private int numOfTerms = 2;
    }
}