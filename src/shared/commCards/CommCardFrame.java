package shared.commCards;

import lobby.controller.Nation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.gui.listeners.UndecoratedMouseListener;

/**
 * CommCardFrame.java  Date Created: Dec 08, 2012
 *
 * Purpose: To display the Comm Card information.
 *
 * Description:
 *
 * @author Chrisb
 */

public class CommCardFrame extends JDialog implements KeyListener {
    private int commNation;

    public CommCardFrame (int nation) {
        commNation = nation;

        setupCommWindow();

        UndecoratedMouseListener listener = new UndecoratedMouseListener(this);
        addMouseListener(listener);
        addMouseMotionListener(listener);

        setVisible(true);
        setResizable(false);
        addKeyListener(this);
        setSize(new Dimension(800,650));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void setupCommWindow() {
        switch (commNation) {
            case Nation.FRANCE: setTitle("French Commander Info"); this.setContentPane(new FrenchCommCard()); break;
            case Nation.GREAT_BRITAIN: setTitle("British Commander Info"); this.setContentPane(new BritishCommCard()); break;
            case Nation.PRUSSIA: setTitle("Prussian Commander Info"); this.setContentPane(new PrussianCommCard()); break;
            case Nation.RUSSIA: setTitle("Russian Commander Info"); this.setContentPane(new RussianCommCard()); break;
            case Nation.OTTOMANS: setTitle("Ottoman Commander Info"); this.setContentPane(new OttomanCommCard()); break;
            case Nation.AUSTRIA_HUNGARY: setTitle("Austrian Commander Info"); this.setContentPane(new AustrianCommCard()); break;
            case Nation.SPAIN: setTitle("Spanish Commander Info"); this.setContentPane(new SpanishCommCard()); break;
        }
    }

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if ( e.getKeyCode() == KeyEvent.VK_ESCAPE )
            dispose();
    }
    public void keyReleased(KeyEvent e) {}
}
