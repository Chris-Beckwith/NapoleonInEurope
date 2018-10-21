package game.gui;

import game.controller.GameController;
import game.controller.NationInstance;
import game.util.GameMsg;
import game.gui.listeners.UndecoratedMouseListener;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * PurchasePanel.java  Date Created: Oct 11, 2013
 *
 * Purpose: To display how many production points (pp) a nation has, what they can purchase
 * and what they will have remaining after their purchase
 *
 * Description:
 *
 *   --- Nation Purchase Window ------------
 *  |                                       |
 *  |  Production Points Available: 21      |
 *  |                                       |
 *  |  Purchasable Units:                   |
 *  |    Infantry (Cost: 6)   x[ 1 ] [-][+] |
 *  |    Artillery (Cost: 10) x[ 1 ] [-][+] |
 *  |    ~~~~~~~~~ ~~~~~~~~~  ~~~~~~  ~~~~  |
 *  |    ~~~~~~~~~ ~~~~~~~~~  ~~~~~~  ~~~~  |
 *  |    ~~~~~~~~~ ~~~~~~~~~  ~~~~~~  ~~~~  |
 *  |                                       |
 *  |  Buy PAPs (Cost: 10)    x[ 0 ] [-][+] |
 *  |                                       |
 *  |  Production Points Remaining: 5       |
 *  |                                       |
 *  |     [ Finalize ]      [ Reset ]       |
 *  |_______________________________________|
 *
 * @author Chrisb
 */
public class PurchasePanel extends JDialog implements KeyListener {

    public PurchasePanel(GameController controller, NationInstance nation) {
        this.controller = controller;
        this.nation = nation;
        UndecoratedMouseListener listener = new UndecoratedMouseListener(this);
//        nationInfoPanel = new NationInfoPanel(controller, nation); todo
        addMouseListener(listener);
        addMouseMotionListener(listener);
        addKeyListener(this);
        setVisible(true);
        setResizable(false);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setContentPane(nationInfoPanel);  todo
        setTitle(GameMsg.getString("nation.pos." + nation.getNationNumber()) + " Purchase Window");
    }


    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }

    GameController controller;
    NationInstance nation;
}