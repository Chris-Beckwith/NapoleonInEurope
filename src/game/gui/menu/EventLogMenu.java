package game.gui.menu;

import game.gui.listeners.UndecoratedMouseListener;
import game.util.GameMsg;
import game.util.EventLogger;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * EventLogMenu.java  Date Created: Sep 26, 2013
 *
 * Purpose: To display the Event log.
 *
 * Description:
 *
 * [ July, 1814 ] - France declares war on Prussia.
 *
 * @author Chrisb
 */
public class EventLogMenu extends JDialog implements KeyListener {
    public EventLogMenu() {
        super();
        eventLogPanel = new EventLogPanel();
        UndecoratedMouseListener listener = new UndecoratedMouseListener(this);
        setTitle(GameMsg.getString("eventLog.title"));
        setVisible(true);
//        setResizable(false);
        addKeyListener(this);
        addMouseListener(listener);
        addMouseMotionListener(listener);
        setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setContentPane(eventLogPanel);
        updateLog();
    }

    public void updateLog() { eventLogPanel.updateLog(); }

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if ( e.getKeyCode() == KeyEvent.VK_ESCAPE )
            dispose();
    }
    public void keyReleased(KeyEvent e) {}

    private EventLogPanel eventLogPanel;

    private class EventLogPanel extends JPanel {
        JTextArea logWindow;
        private EventLogPanel() {
            setBackground(Color.GRAY);
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            setBorder( new CompoundBorder((new CompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4), BorderFactory.createEmptyBorder(5,5,5,5))),
                new CompoundBorder(new TitledBorder(GameMsg.getString("eventLog.title")), BorderFactory.createEmptyBorder(0,5,5,5))) );

            Dimension d = new Dimension(175,100);
            logWindow = new JTextArea(0,50);
            logWindow.setEnabled(false);
            logWindow.setEditable(false);
            logWindow.setLineWrap(true);
            logWindow.setWrapStyleWord(true);
            logWindow.setBackground(Color.GRAY);
            logWindow.setDisabledTextColor(Color.BLACK);
            logWindow.setPreferredSize(d);
            logWindow.setMinimumSize(d);
            logWindow.setMaximumSize(d);

            //Test
//            logWindow.append("[ July, 1814 ] - France declares war on Prussia.");
//            logWindow.append("\n[ August, 1814 ] - France sues for peace from Prussia and Russia.");
//            logWindow.append("\n[ September, 1814 ] - France and Spain agree to form an alliance.");
//            logWindow.append("\n[ November, 1814 ] - Austria recruits the minor nation of Blahbibity-blah Land.");
//            logWindow.append("\n[ December, 1814 ] - France fails to make their commitment check.");
//            logWindow.append("\n[ December, 1814 ] - France fails to make their commitment check.");
//            logWindow.append("\n[ December, 1814 ] - France fails to make their commitment check.");
//            logWindow.append("\n[ December, 1814 ] - France fails to make their commitment check.");
//            logWindow.append("\n[ December, 1814 ] - France fails to make their commitment check.");
//            logWindow.append("\n[ December, 1814 ] - France fails to make their commitment check.");
//            logWindow.append("\n[ December, 1814 ] - France fails to make their commitment check.");
//            logWindow.append("\n[ December, 1814 ] - France fails to make their commitment check.");
//            logWindow.append("\n[ December, 1814 ] - France fails to make their commitment check.");
//            logWindow.append("\n[ December, 1814 ] - France fails to make their commitment check.");
//            logWindow.append("\n[ December, 1814 ] - France fails to make their commitment check.");

            JScrollPane eventScrollPane = new JScrollPane(logWindow);
            eventScrollPane.setBorder(BorderFactory.createEmptyBorder());
            eventScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            eventScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            add(eventScrollPane);
        }

        public void updateLog() { logWindow.setText(EventLogger.getEvents(EventLogger.getEventFileName())); pack(); }
    }
}