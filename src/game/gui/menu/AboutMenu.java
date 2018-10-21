package game.gui.menu;

import game.util.GameMsg;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * AboutMenu.java  Date Created: Sep 18, 2013
 *
 * Purpose: To display the about menu.
 *
 * Description: This menu will display some general information.
 * Creator, Contributers, Artists, CTG Credit, Version, etc.
 *
 * @author Chrisb
 */
public class AboutMenu extends JDialog implements MouseListener, FocusListener, KeyListener {
    public AboutMenu() {
        super();
        requestFocusInWindow();
        addKeyListener(this);
        addFocusListener(this);
        addMouseListener(this);
        setUndecorated(true);
        setVisible(true);
        setResizable(false);
        setAlwaysOnTop(true);
        setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setTitle(GameMsg.getString("about.title"));
        setContentPane(new AboutPanel());
    }

    public void mouseClicked(MouseEvent e) { dispose(); }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void focusGained(FocusEvent e) {}
    public void focusLost(FocusEvent e) { dispose(); }
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            dispose();
    }
    public void keyReleased(KeyEvent e) {}

    private class AboutPanel extends JPanel {
        private AboutPanel() {
            setBackground(Color.GRAY);
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            setBorder( new CompoundBorder((new CompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4), BorderFactory.createEmptyBorder(5,5,5,5))),
                new CompoundBorder(new TitledBorder(GameMsg.getString("about.title")), BorderFactory.createEmptyBorder(0,5,5,5))) );
            //Creator Row
            JPanel creatorRow = new JPanel();
            creatorRow.setLayout(new BoxLayout(creatorRow, BoxLayout.LINE_AXIS));
            creatorRow.setOpaque(false);
            creatorRow.add(Box.createHorizontalGlue());
            creatorRow.add(new JLabel("Created By: Chris Beckwith"));
            creatorRow.add(Box.createHorizontalGlue());
            //Contributor Rows
            JPanel contributorRow = new JPanel();
            contributorRow.setLayout(new BoxLayout(contributorRow, BoxLayout.LINE_AXIS));
            contributorRow.setOpaque(false);
            contributorRow.add(Box.createHorizontalGlue());
            contributorRow.add(new JLabel("Contributions By: Daniel Penso"));
            contributorRow.add(Box.createHorizontalGlue());
            //Artists Row
            //CTG Credit Row
            JPanel ctgCreditRow = new JPanel();
            ctgCreditRow.setLayout(new BoxLayout(ctgCreditRow, BoxLayout.LINE_AXIS));
            ctgCreditRow.setOpaque(false);
            ctgCreditRow.add(Box.createHorizontalGlue());
            ctgCreditRow.add(new JLabel("Based on the CTG variant of Napolean In Europe"));
            ctgCreditRow.add(Box.createHorizontalGlue());
            //Version Row
            JPanel versionRow = new JPanel();
            versionRow.setLayout(new BoxLayout(versionRow, BoxLayout.LINE_AXIS));
            versionRow.setOpaque(false);
            versionRow.add(Box.createHorizontalGlue());
            versionRow.add(new JLabel("Version: Alpha v0.1"));
            versionRow.add(Box.createHorizontalGlue());

            add(Box.createVerticalStrut(8));
            add(creatorRow);
            add(Box.createVerticalStrut(8));
            add(contributorRow);
            add(Box.createVerticalStrut(8));
            add(ctgCreditRow);
            add(Box.createVerticalStrut(8));
            add(versionRow);
            add(Box.createVerticalStrut(8));

        }
    }

}