package game.gui;

import game.controller.DisplayController;
import game.controller.GameController;
import game.gui.menu.NationPopupMenu;
import game.util.GameMsg;
import lobby.controller.Nation;
import shared.controller.LobbyConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * ChatChooser.java  Date Created: Oct 24, 2012
 *
 * Purpose: To display the different chat windows to choose from.
 *
 * Description: This panel shall display the selectable chat windows.
 * The currently selected chat window will be highlighted with a box
 * around it.  A chat window that receives a message will flash twice.
 * A chat window that has an unread message will remain a specific color.
 * Only player-controlled nations can be selected.
 *
 * The available chat windows may vary based on Game mode and scenario.
 *
 * @author Chrisb
 */
public class ChatChooser extends JPanel implements MouseListener {

    public ChatChooser(GameController controller, DisplayController display) {
        super();
        this.controller = controller;
        this.display = display;
        nation = this.controller.getUserNation();
        nationPopup = new NationPopupMenu(controller, display);
        nationPopup.addMouseListener(this);

        createComponents();
        layoutComponents();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(225,175));
        setMaximumSize(new Dimension(225,175));
        setMinimumSize(new Dimension(225,175));
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4), BorderFactory.createEmptyBorder(10,10,10,10)));
    }

    private void createComponents() {
        //global
        global = new FlashAnimationLabel(GameMsg.getString("cc.global.label"));
        global.addMouseListener(this);
        global.setCursor(new Cursor(Cursor.HAND_CURSOR));
        global.setPreferredSize(new Dimension(60,28));
        global.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        global.setHorizontalAlignment(SwingConstants.CENTER);

        if (controller.game.getGameMode() == LobbyConstants.EMPIRE) {
            //Initalize
            france = new FlashAnimationLabel(GameMsg.getString("cc.france.label"));
            greatBritain = new FlashAnimationLabel(GameMsg.getString("cc.greatBritain.label"));
            prussia = new FlashAnimationLabel(GameMsg.getString("cc.prussia.label"));
            russia = new FlashAnimationLabel(GameMsg.getString("cc.russia.label"));
            ottoman = new FlashAnimationLabel(GameMsg.getString("cc.ottoman.label"));
            austria = new FlashAnimationLabel(GameMsg.getString("cc.austria.label"));
            spain = new FlashAnimationLabel(GameMsg.getString("cc.spain.label"));

            //add actions
            france.addMouseListener(this);
            greatBritain.addMouseListener(this);
            prussia.addMouseListener(this);
            russia.addMouseListener(this);
            ottoman.addMouseListener(this);
            austria.addMouseListener(this);
            spain.addMouseListener(this);

            france.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
            greatBritain.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
            prussia.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
            russia.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
            ottoman.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
            austria.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
            spain.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

            france.setHorizontalAlignment(SwingConstants.CENTER);
            greatBritain.setHorizontalAlignment(SwingConstants.CENTER);
            prussia.setHorizontalAlignment(SwingConstants.CENTER);
            russia.setHorizontalAlignment(SwingConstants.CENTER);
            ottoman.setHorizontalAlignment(SwingConstants.CENTER);
            austria.setHorizontalAlignment(SwingConstants.CENTER);
            spain.setHorizontalAlignment(SwingConstants.CENTER);

        } else if (controller.game.getGameMode() == LobbyConstants.TEAM) {
            team = new FlashAnimationLabel(GameMsg.getString("cc.team.label"));
            team.addMouseListener(this);
            team.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
            team.setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    private void layoutComponents() {
        if (controller.game.getGameMode() == LobbyConstants.EMPIRE) {

            JPanel globalRow = new JPanel();
            globalRow.setBackground(Color.GRAY);
            globalRow.setLayout(new BoxLayout(globalRow, BoxLayout.LINE_AXIS));
            globalRow.add(Box.createHorizontalGlue());
            globalRow.add(global);
            globalRow.add(Box.createHorizontalGlue());

            add(globalRow);

            JPanel row1 = new JPanel();
            row1.setBackground(Color.GRAY);
            row1.setLayout(new BoxLayout(row1, BoxLayout.LINE_AXIS));
            JPanel row2 = new JPanel();
            row2.setBackground(Color.GRAY);
            row2.setLayout(new BoxLayout(row2, BoxLayout.LINE_AXIS));
            JPanel row3 = new JPanel();
            row3.setBackground(Color.GRAY);
            row3.setLayout(new BoxLayout(row3, BoxLayout.LINE_AXIS));
            Dimension rowSpacing = new Dimension(20,0);
            Dimension colSpacing = new Dimension(0,20);

            switch(nation) {
                case Nation.FRANCE:
                    row1.add(greatBritain);
                    row1.add(Box.createRigidArea(rowSpacing));
                    row1.add(ottoman);
                    row2.add(prussia);
                    row2.add(Box.createRigidArea(rowSpacing));
                    row2.add(austria);
                    row3.add(russia);
                    row3.add(Box.createRigidArea(rowSpacing));
                    row3.add(spain);
                    break;
                case Nation.GREAT_BRITAIN:
                    row1.add(france);
                    row1.add(Box.createRigidArea(rowSpacing));
                    row1.add(ottoman);
                    row2.add(prussia);
                    row2.add(Box.createRigidArea(rowSpacing));
                    row2.add(austria);
                    row3.add(russia);
                    row3.add(Box.createRigidArea(rowSpacing));
                    row3.add(spain);
                    break;
                case Nation.PRUSSIA:
                    row1.add(france);
                    row1.add(Box.createRigidArea(rowSpacing));
                    row1.add(ottoman);
                    row2.add(greatBritain);
                    row2.add(Box.createRigidArea(rowSpacing));
                    row2.add(austria);
                    row3.add(russia);
                    row3.add(Box.createRigidArea(rowSpacing));
                    row3.add(spain);
                    break;
                case Nation.RUSSIA:
                    row1.add(france);
                    row1.add(Box.createRigidArea(rowSpacing));
                    row1.add(ottoman);
                    row2.add(greatBritain);
                    row2.add(Box.createRigidArea(rowSpacing));
                    row2.add(austria);
                    row3.add(prussia);
                    row3.add(Box.createRigidArea(rowSpacing));
                    row3.add(spain);
                    break;
                case Nation.OTTOMANS:
                    row1.add(france);
                    row1.add(Box.createRigidArea(rowSpacing));
                    row1.add(russia);
                    row2.add(greatBritain);
                    row2.add(Box.createRigidArea(rowSpacing));
                    row2.add(austria);
                    row3.add(prussia);
                    row3.add(Box.createRigidArea(rowSpacing));
                    row3.add(spain);
                    break;
                case Nation.AUSTRIA_HUNGARY:
                    row1.add(france);
                    row1.add(Box.createRigidArea(rowSpacing));
                    row1.add(russia);
                    row2.add(greatBritain);
                    row2.add(Box.createRigidArea(rowSpacing));
                    row2.add(ottoman);
                    row3.add(prussia);
                    row3.add(Box.createRigidArea(rowSpacing));
                    row3.add(spain);
                    break;
                case Nation.SPAIN:
                    row1.add(france);
                    row1.add(Box.createRigidArea(rowSpacing));
                    row1.add(russia);
                    row2.add(greatBritain);
                    row2.add(Box.createRigidArea(rowSpacing));
                    row2.add(ottoman);
                    row3.add(prussia);
                    row3.add(Box.createRigidArea(rowSpacing));
                    row3.add(austria);
                    break;
            }

            add(Box.createRigidArea(colSpacing));
            add(row1);
            add(Box.createRigidArea(colSpacing));
            add(row2);
            add(Box.createRigidArea(colSpacing));
            add(row3);

        } else if (controller.game.getGameMode() == LobbyConstants.TEAM) {
            //todo layout team..
        }
    }

    public void setupCursors() {
        if (controller.game.isControlledNation(Nation.FRANCE))
            france.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (controller.game.isControlledNation(Nation.GREAT_BRITAIN))
            greatBritain.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (controller.game.isControlledNation(Nation.PRUSSIA))
            prussia.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (controller.game.isControlledNation(Nation.RUSSIA))
            russia.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (controller.game.isControlledNation(Nation.OTTOMANS))
            ottoman.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (controller.game.isControlledNation(Nation.AUSTRIA_HUNGARY))
            austria.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (controller.game.isControlledNation(Nation.SPAIN))
            spain.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /*
     * A new nation chat window has been opened.
     * Remove border from old nation and add one
     * to the new nations.  set nation to new nation.
     */
    public void setChatNation(int oldNation, int newNation) {        
        switch (oldNation) {
            case Nation.GLOBAL: global.setBorder(BorderFactory.createEmptyBorder(2,2,2,2)); break;
            case Nation.FRANCE: france.setBorder(BorderFactory.createEmptyBorder(2,2,2,2)); break;
            case Nation.GREAT_BRITAIN: greatBritain.setBorder(BorderFactory.createEmptyBorder(2,2,2,2)); break;
            case Nation.PRUSSIA: prussia.setBorder(BorderFactory.createEmptyBorder(2,2,2,2)); break;
            case Nation.RUSSIA: russia.setBorder(BorderFactory.createEmptyBorder(2,2,2,2)); break;
            case Nation.OTTOMANS: ottoman.setBorder(BorderFactory.createEmptyBorder(2,2,2,2)); break;
            case Nation.AUSTRIA_HUNGARY: austria.setBorder(BorderFactory.createEmptyBorder(2,2,2,2)); break;
            case Nation.SPAIN: spain.setBorder(BorderFactory.createEmptyBorder(2,2,2,2)); break;
            case Nation.TEAM: team.setBorder(BorderFactory.createEmptyBorder(2,2,2,2)); break;
        }

        switch (newNation) {
            case Nation.GLOBAL: ((FlashAnimationLabel)global).stop();
                global.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                break;
            case Nation.FRANCE: ((FlashAnimationLabel)france).stop();
                france.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                break;
            case Nation.GREAT_BRITAIN: ((FlashAnimationLabel)greatBritain).stop();
                greatBritain.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                break;
            case Nation.PRUSSIA: ((FlashAnimationLabel)prussia).stop();
                prussia.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                break;
            case Nation.RUSSIA: ((FlashAnimationLabel)russia).stop();
                russia.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                break;
            case Nation.OTTOMANS: ((FlashAnimationLabel)ottoman).stop();
                ottoman.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                break;
            case Nation.AUSTRIA_HUNGARY: ((FlashAnimationLabel)austria).stop();
                austria.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                break;
            case Nation.SPAIN: ((FlashAnimationLabel)spain).stop();
                spain.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                break;
            case Nation.TEAM: ((FlashAnimationLabel)team).stop();
                team.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                break;
        }
    }

    public void messageReceived(int nation) {        
        if (nation != controller.getChatNation()) {
            switch (nation) {
                case Nation.GLOBAL: ((FlashAnimationLabel)global).start(); break;
                case Nation.FRANCE: ((FlashAnimationLabel)france).start(); break;
                case Nation.GREAT_BRITAIN: ((FlashAnimationLabel)greatBritain).start(); break;
                case Nation.PRUSSIA: ((FlashAnimationLabel)prussia).start(); break;
                case Nation.RUSSIA: ((FlashAnimationLabel)russia).start(); break;
                case Nation.OTTOMANS: ((FlashAnimationLabel)ottoman).start(); break;
                case Nation.AUSTRIA_HUNGARY: ((FlashAnimationLabel)austria).start(); break;
                case Nation.SPAIN: ((FlashAnimationLabel)spain).start(); break;
                case Nation.TEAM: ((FlashAnimationLabel)team).start(); break;
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
//        System.out.println("ChatNation: " + controller.getChatNation());
        if (e.getComponent().equals(global) )
            if (controller.getChatNation() != Nation.GLOBAL)
                controller.changeChatNation(Nation.GLOBAL);
        if (e.getComponent().equals(france) )
            if (e.getButton() == MouseEvent.BUTTON3 && controller.game.isControlledNation(Nation.FRANCE)) {
                ((NationPopupMenu)nationPopup).setPopupNation(Nation.FRANCE);
                nationPopup.show(e.getComponent(), e.getX(), e.getY());
            }
            else if (e.getButton() == MouseEvent.BUTTON1)
                if (controller.getChatNation() != Nation.FRANCE)
                    controller.changeChatNation(Nation.FRANCE);
        if (e.getComponent().equals(greatBritain) )
            if (e.getButton() == MouseEvent.BUTTON3 && controller.game.isControlledNation(Nation.GREAT_BRITAIN)) {
                ((NationPopupMenu)nationPopup).setPopupNation(Nation.GREAT_BRITAIN);
                nationPopup.show(e.getComponent(), e.getX(), e.getY());
            }
            else if (e.getButton() == MouseEvent.BUTTON1)
                if (controller.getChatNation() != Nation.GREAT_BRITAIN)
                    controller.changeChatNation(Nation.GREAT_BRITAIN);
        if (e.getComponent().equals(prussia) )
            if (e.getButton() == MouseEvent.BUTTON3 && controller.game.isControlledNation(Nation.PRUSSIA)) {
                ((NationPopupMenu)nationPopup).setPopupNation(Nation.PRUSSIA);
                nationPopup.show(e.getComponent(), e.getX(), e.getY());
            }
            else if (e.getButton() == MouseEvent.BUTTON1)
                if (controller.getChatNation() != Nation.PRUSSIA)
                    controller.changeChatNation(Nation.PRUSSIA);
        if (e.getComponent().equals(russia) )
            if (e.getButton() == MouseEvent.BUTTON3 && controller.game.isControlledNation(Nation.RUSSIA)) {
                ((NationPopupMenu)nationPopup).setPopupNation(Nation.RUSSIA);
                nationPopup.show(e.getComponent(), e.getX(), e.getY());
            }
            else if (e.getButton() == MouseEvent.BUTTON1)
                if (controller.getChatNation() != Nation.RUSSIA)
                    controller.changeChatNation(Nation.RUSSIA);
        if (e.getComponent().equals(ottoman) )
            if (e.getButton() == MouseEvent.BUTTON3 && controller.game.isControlledNation(Nation.OTTOMANS)) {
                ((NationPopupMenu)nationPopup).setPopupNation(Nation.OTTOMANS);
                nationPopup.show(e.getComponent(), e.getX(), e.getY());
            }
            else if (e.getButton() == MouseEvent.BUTTON1)
                if (controller.getChatNation() != Nation.OTTOMANS)
                    controller.changeChatNation(Nation.OTTOMANS);
        if (e.getComponent().equals(austria) )
            if (e.getButton() == MouseEvent.BUTTON3 && controller.game.isControlledNation(Nation.AUSTRIA_HUNGARY)) {
                ((NationPopupMenu)nationPopup).setPopupNation(Nation.AUSTRIA_HUNGARY);
                nationPopup.show(e.getComponent(), e.getX(), e.getY());
            }
            else if (e.getButton() == MouseEvent.BUTTON1)
                if (controller.getChatNation() != Nation.AUSTRIA_HUNGARY)
                    controller.changeChatNation(Nation.AUSTRIA_HUNGARY);
        if (e.getComponent().equals(spain) )
            if (e.getButton() == MouseEvent.BUTTON3 && controller.game.isControlledNation(Nation.SPAIN)) {
                ((NationPopupMenu)nationPopup).setPopupNation(Nation.SPAIN);
                nationPopup.show(e.getComponent(), e.getX(), e.getY());
            }
            else if (e.getButton() == MouseEvent.BUTTON1)
                if (controller.getChatNation() != Nation.SPAIN)
                    controller.changeChatNation(Nation.SPAIN);
        if (e.getComponent().equals(team) )
            if (controller.getChatNation() != Nation.TEAM)
                controller.changeChatNation(Nation.TEAM);
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    private GameController controller;
    private DisplayController display;

    private JLabel global;
    private JLabel france;
    private JLabel greatBritain;
    private JLabel prussia;
    private JLabel russia;
    private JLabel ottoman;
    private JLabel austria;
    private JLabel spain;

    private JPopupMenu nationPopup;
    private int nation;

    private JLabel team;
}