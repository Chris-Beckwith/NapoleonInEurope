package game.gui.listeners;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * UndecoratedMouseListener.java  Date Created: Sep 18, 2013
 *
 * Purpose: The basic mouse listener for undecorated dialogs
 *
 * Description:
 *
 * @author Chrisb
 */
public class UndecoratedMouseListener implements MouseListener, MouseMotionListener {
    public UndecoratedMouseListener(Component component) {
        super();
        this.component = component;
    }

    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {
        mouseDownCompCoords = e.getPoint();
    }

    public void mouseReleased(MouseEvent e) {
        mouseDownCompCoords = null;
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void mouseDragged(MouseEvent e) {
        Point currCoords = e.getLocationOnScreen();
        component.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
    }

    public void mouseMoved(MouseEvent e) {}

    private Point mouseDownCompCoords;
    private Component component;
}