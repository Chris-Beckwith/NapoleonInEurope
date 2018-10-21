package game.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * EventOverlay.java  Date Created: Oct 2, 2013
 *
 * Purpose: To display event descriptions when they occur overlayed on the map just above the chat window.
 *
 * Description:
 *
 * @author Chrisb
 */
public class EventOverlay extends JPanel {
    public EventOverlay() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setOpaque(false);

//        add(new SingleEventLabel(this, "This is a test event label"));
//        add(new SingleEventLabel(this, "Test test test event stuf here", 6000));
//        add(new SingleEventLabel(this, "France has just blown up, later France", 7000));
//        add(new SingleEventLabel(this, "This is a longer test event label for long testing", 8000));
//        add(new SingleEventLabel(this, "This is a longer test event label for long testing", 8000));
    }

    public void showEvent(String eventString) {
        add(new SingleEventLabel(this, eventString));
        revalidate();
    }

    private class SingleEventLabel extends JLabel implements ActionListener {
        private SingleEventLabel(JComponent parent, String eventString, int delay) {
            super(eventString);
            this.parent = parent;

            setPreferredSize(new Dimension(800,20));
            setMinimumSize(new Dimension(800,20));
            setMaximumSize(new Dimension(800,20));
            setHorizontalAlignment(JLabel.CENTER);
            setOpaque(true);
            setBackground(new Color(0,0,0,150));
            setForeground(new Color(RED,GREEN,BLUE,alpha));

            eventTimer = new Timer(DELAY, this);
            eventTimer.setInitialDelay(delay);
            eventTimer.start();
        }

        private SingleEventLabel(JComponent parent, String eventString) {
            this(parent, eventString, INIT_DELAY);
        }

        private void remove() {
            parent.remove(this);
            parent.revalidate();
            parent.repaint();
        }

        public void actionPerformed(ActionEvent e) {
            alpha -= 5;
            if (alpha < 0) {
                remove();
                eventTimer.stop();
            } else {
                parent.repaint();
                setForeground(new Color(RED,GREEN,BLUE,alpha));
            }
        }

        private int alpha = 255;
        private final static int DELAY = 20;
        private final static int INIT_DELAY = 8000;
        private final int RED = 102;
        private final int GREEN = 178;
        private final int BLUE = 255;

        private JComponent parent;
        private Timer eventTimer;
    }
}