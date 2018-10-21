package game.gui.menu;

import game.controller.DisplayController;
import game.util.GameMsg;
import lobby.controller.Nation;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * PickANationMenu.java  Date Created: Sep 18, 2013
 *
 * Purpose: To display a list of nations to choose from.
 *
 * Description: When an action is taken that requires a nation to be specified,
 * display this menu with the list of currently controlled nations.
 *
 * @author Chrisb
 */
public class PickANationMenu extends JDialog implements MouseListener, FocusListener, KeyListener {
    private DisplayController display;
    private int type;
    private boolean dblPick = false;
    private int doNation = -1;

    //Pick a nation to do action to, nation picked will be toNation.
    public PickANationMenu(ArrayList<Integer> nations, DisplayController display, int type, String title) {
        super();
        this.display = display;
        this.type = type;
        requestFocusInWindow();
        addKeyListener(this);
        addFocusListener(this);
        addMouseListener(this);
        setUndecorated(true);
        setVisible(true);
        setResizable(false);
        setAlwaysOnTop(true);
        setBackground(Color.DARK_GRAY);
        setMinimumSize(new Dimension(185, 50));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setTitle(title);
        setContentPane(new PickANationPanel(nations, this, title));
    }

    public PickANationMenu(ArrayList<Integer> nations, DisplayController display, int type) {
        this(nations, display, type, GameMsg.getString("pickANation.title"));
    }

    //User has picked doNation, now have user pick toNation
    public PickANationMenu(int doNation, ArrayList<Integer> toNations, DisplayController display, int type) {
        this(toNations, display, type, GameMsg.getString("pickANation.title"));
        this.doNation = doNation;
    }

    //User controls NPNs, have user pick nation (s)he wishes to take action with, this will be doNation.
    public PickANationMenu(int userNation, ArrayList<Integer> controlledNations, DisplayController display, int type, String title) {
        this(controlledNations, display, type, title);
        dblPick = true;
        setContentPane(new PickANationPanel(userNation, controlledNations, this, title));
    }

    //User has picked an action that can be taken on controlledNations as well as uncontrolledNPNs
    public PickANationMenu(ArrayList<Integer> controlledNations, ArrayList<Integer> uncontrolledNPNs, DisplayController display, int type, String title) {
        this(controlledNations, display, type, title);
        setContentPane(new PickANationPanel(controlledNations, uncontrolledNPNs, this, title));
    }

    public void mouseClicked(MouseEvent e) {
        try {
            String labelText = ((JLabel)e.getComponent()).getText();
            int toNation = -1;
            if (labelText.equals(GameMsg.getString("nation.1")))
                toNation = Nation.FRANCE;
            else if (labelText.equals(GameMsg.getString("nation.2")))
                toNation = Nation.GREAT_BRITAIN;
            else if (labelText.equals(GameMsg.getString("nation.3")))
                toNation = Nation.PRUSSIA;
            else if (labelText.equals(GameMsg.getString("nation.4")))
                toNation = Nation.RUSSIA;
            else if (labelText.equals(GameMsg.getString("nation.5")))
                toNation = Nation.OTTOMANS;
            else if (labelText.equals(GameMsg.getString("nation.6")))
                toNation = Nation.AUSTRIA_HUNGARY;
            else if (labelText.equals(GameMsg.getString("nation.7")))
                toNation = Nation.SPAIN;

            dispose();

            if (dblPick) //In the case of a dblPick, toNation is actually doNation
                display.showPickANationPanel(type, toNation);
            else if (doNation > 0)
                display.nationPicked(type, doNation, toNation);
            else
                display.nationPicked(type, toNation);
        } catch (ClassCastException e1) {
            //do nothing
        }
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void focusGained(FocusEvent e) {}
    public void focusLost(FocusEvent e) { dispose(); }
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dispose();
            display.nationPicked(type, doNation, -1);
        }
    }
    public void keyReleased(KeyEvent e) {}

    private class PickANationPanel extends JPanel {
        //Used to display a list of nations controlled by users
        //Used to allow user to choose the nation they wish to take action with.
        private PickANationPanel(ArrayList<Integer> controlledNations, MouseListener listener, String title) {
            setupPanel(title);
            addNations(controlledNations, listener);
        }

        //Used to display the 'userNation' in the nations color followed by the list of nations controlled by that user.
        //Used when user needs to pick which nation they wish to take action with.
        private PickANationPanel(int userNation, ArrayList<Integer> controlledNations, MouseListener listener, String title) {
            setupPanel(title);

            JPanel row1 = new JPanel();
            row1.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
            row1.setLayout(new BoxLayout(row1, BoxLayout.LINE_AXIS));
            row1.setOpaque(false);

            JPanel row2 = new JPanel();
            row2.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
            row2.setLayout(new BoxLayout(row2, BoxLayout.LINE_AXIS));
            row2.setOpaque(false);

            row1.add(Box.createHorizontalGlue());
            row1.add(new JLabel(GameMsg.getString("pickANation.controlNPN.1")));
            row1.add(Box.createHorizontalGlue());

            row2.add(Box.createHorizontalGlue());
            row2.add(new JLabel(GameMsg.getString("pickANation.controlNPN.2")));
            row2.add(Box.createHorizontalGlue());

            add(row1);
            add(row2);

            addUserNation(userNation, listener);
            addNations(controlledNations, listener);
        }

        private PickANationPanel(ArrayList<Integer> controlledNations, ArrayList<Integer> uncontrolledNPNs, MouseListener listener, String title) {
            setupPanel(title);
            addNations(controlledNations, listener);

            JLabel uncontrolledLabel = new JLabel(GameMsg.getString("pickANation.uncontrolledNPNs"));
            uncontrolledLabel.setBorder(BorderFactory.createEmptyBorder(15, 5, 10, 0));

            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.LINE_AXIS));
            row.setOpaque(false);

            row.add(uncontrolledLabel);
            row.add(Box.createHorizontalGlue());
            add(row);

            addNations(uncontrolledNPNs, listener);
        }

        private void setupPanel(String title) {
            setBackground(Color.GRAY);
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            setBorder( new CompoundBorder((new CompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4), BorderFactory.createEmptyBorder(5,5,5,5))),
                new CompoundBorder(new TitledBorder(title), BorderFactory.createEmptyBorder(0,5,5,5))) );
        }

        private void addNations(ArrayList<Integer> nations, MouseListener listener) {
            addNations(nations, listener, false);
        }

        private void addUserNation(int userNation, MouseListener listener) {
            ArrayList<Integer> userNationArray = new ArrayList<Integer>();
            userNationArray.add(userNation);
            addNations(userNationArray, listener, true);
        }

        //If isUserNation, then set foreground to nation color.
        private void addNations(ArrayList<Integer> nations, MouseListener listener, boolean isUserNation) {
            for (Integer n : nations) {
                JPanel row = new JPanel();
                row.setLayout(new BoxLayout(row, BoxLayout.LINE_AXIS));
                row.setOpaque(false);

                JLabel nation = new JLabel(GameMsg.getString("nation." + n));
                nation.setBorder( new CompoundBorder( new CompoundBorder(BorderFactory.createEmptyBorder(5,8,5,8), BorderFactory.createLineBorder(Color.BLACK, 2)), BorderFactory.createEmptyBorder(2,5,2,5) ) );
                nation.addMouseListener(listener);

                if (isUserNation)
                    nation.setForeground(DisplayController.getNationColor(n));

                row.add(Box.createHorizontalGlue());
                row.add(nation);
                row.add(Box.createHorizontalGlue());
                add(row);
            }
        }
    }
}