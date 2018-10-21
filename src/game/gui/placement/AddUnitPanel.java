package game.gui.placement;

import game.controller.Unit.MilitaryUnit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * AddUnitPanel.java  Date Created: Mar 12, 2013
 *
 * Purpose:
 *
 * Description:
 *
 * @author Chrisb
 */
public class AddUnitPanel extends UnitPanel implements MouseListener {

    public AddUnitPanel(int type, ArrayList<MilitaryUnit> units, PlacementPanel parent) {
        super(type, units);
        this.parent = parent;
    }

    protected void createComponents() {
        super.createComponents();
        typeLabel.addMouseListener(this);
        countLabel.addMouseListener(this);
        downArrowLabel = new JLabel(createImageIcon("rsrc/downArrow.gif", "Add"));
        downArrowLabel.addMouseListener(this);
    }

    protected void layoutComponents() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBorder(new EmptyBorder(5,5,5,5));
//        setBackground(Color.GRAY);
        setMaximumSize(new Dimension(150,25));
        add(Box.createHorizontalStrut(12));
        add(downArrowLabel);
        add(Box.createHorizontalStrut(2));
        add(countLabel);
        add(typeLabel);
        add(Box.createHorizontalGlue());
    }

    public void mouseClicked(MouseEvent e) {
        if ( (e.getComponent().equals(downArrowLabel) || e.getComponent().equals(countLabel)
                || e.getComponent().equals(typeLabel)) && parent.isUnitPlaceable(type)) {
            if ( e.isShiftDown() ) {
                parent.addUnitsToRegion(units);
                clearUnits();
                parent.refresh();
            } else {
                MilitaryUnit u = removeUnit();
                parent.addUnitToRegion(u);
            }
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    private JLabel downArrowLabel;
    private PlacementPanel parent;
}