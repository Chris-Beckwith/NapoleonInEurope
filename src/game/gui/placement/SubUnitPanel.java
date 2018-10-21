package game.gui.placement;

import game.controller.Unit.Admiral;
import game.controller.Unit.MilitaryUnit;
import game.controller.Unit.NavalSquadron;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * SubUnitPanel.java  Date Created: Mar 12, 2013
 *
 * Purpose:
 *
 * Description:
 *
 * @author Chrisb
 */
public class SubUnitPanel extends UnitPanel implements MouseListener {

    public SubUnitPanel(int type, PlacementPanel parent) {
        super(type);
        this.parent = parent;
    }

    protected void createComponents() {
        super.createComponents();
        typeLabel.addMouseListener(this);
        countLabel.addMouseListener(this);
        upArrowLabel = new JLabel(createImageIcon("rsrc/upArrow.gif", "Remove"));
        upArrowLabel.addMouseListener(this);
    }

    protected void layoutComponents() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBorder(new EmptyBorder(5,5,5,5));
//        setBackground(Color.GRAY);
        setMaximumSize(new Dimension(150,25));
        add(Box.createHorizontalStrut(12));
        add(upArrowLabel);
        add(Box.createHorizontalStrut(2));
        add(countLabel);
        add(typeLabel);
        add(Box.createHorizontalGlue());
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getComponent().equals(upArrowLabel) || e.getComponent().equals(countLabel) || e.getComponent().equals(typeLabel) ) {
            if (type == MilitaryUnit.NAVAL_SQUADRON) {
                MilitaryUnit u = removeUnit();
                Admiral adm = ((NavalSquadron)u).getAdmiral();
                if (adm != null)
                    parent.subUnitFromRegion(adm);
                parent.subUnitFromRegion(u);
            } else {
                if (e.isShiftDown()) {
                    parent.subUnitsFromRegion(units);
                    clearUnits();
                    parent.refresh();
                } else {
                    MilitaryUnit u = removeUnit();
                    parent.subUnitFromRegion(u);
                }
            }
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    private JLabel upArrowLabel;
    private PlacementPanel parent;
}