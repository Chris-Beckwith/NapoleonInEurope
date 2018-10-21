package game.gui;

import game.controller.DisplayController;
import game.controller.GameController;

import javax.swing.*;
import java.awt.*;

/**
 * MainScreen.java  Date Created: Oct 24, 2012
 *
 * Purpose: To display all panels on the main screen; map, chat, etc.
 *
 * Description: This screen will contain everything the user will see while playing.
 * The Map, Territory Info, MiniMap, Chat, RoundTurn.
 *
 * @author Chrisb
 */
public class MainScreen extends JPanel {

    /**
     * Constructor
     */
    public MainScreen(GameController controller, DisplayController dControl) {
        super(new BorderLayout());
        this.controller = controller;
        this.dControl = dControl;

        createComponents();
        layoutComponents();

        setBackground(Color.DARK_GRAY);
    }

    private void createComponents() {
        map = new Map(controller, dControl);
        sidePanel = new SidePanel(controller, dControl);
        miniMap = new MiniMap(controller, dControl);
        chat = new Chat(controller, dControl);
        roundTurn = new RoundTurn(controller, dControl);
    }

    private void layoutComponents() {
        pageEndPanel = new JPanel();
        pageEndPanel.setLayout(new BoxLayout(pageEndPanel, BoxLayout.LINE_AXIS));
        pageEndPanel.setBackground(Color.DARK_GRAY);
        pageEndPanel.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));

        pageEndPanel.add(miniMap);
        pageEndPanel.add(chat);
        pageEndPanel.add(Box.createRigidArea(new Dimension(25,0)));
        pageEndPanel.add(roundTurn);

        lineEndPanel = new JPanel();
        lineEndPanel.setBackground(Color.DARK_GRAY);
        lineEndPanel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
        lineEndPanel.add(sidePanel);

        add(map, BorderLayout.CENTER);
        add(pageEndPanel, BorderLayout.PAGE_END);
        add(lineEndPanel, BorderLayout.LINE_END);
    }

    public void getPanelHeightWidths() {
        System.out.println("pageEnd Height: " +  pageEndPanel.getHeight());
        System.out.println("pageEnd width: " + pageEndPanel.getWidth());

        System.out.println("lineEnd Height: " + lineEndPanel.getHeight());
        System.out.println("lineEnd Width: " + lineEndPanel.getWidth());
    }

    private GameController controller;
    private DisplayController dControl;

    public Map map;
    public SidePanel sidePanel;
    public MiniMap miniMap;
    public Chat chat;
    public RoundTurn roundTurn;

    private JPanel pageEndPanel;
    private JPanel lineEndPanel;

}