package game.gui.placement;

import game.controller.Unit.MilitaryUnit;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * UnitPanel.java  Date Created: Mar 13, 2013
 *
 * Purpose:
 *
 * Description:
 *
 * @author Chrisb
 */
public class UnitPanel extends JPanel {

    public UnitPanel(int type) {
        this(type, new ArrayList<MilitaryUnit>());
    }

    public UnitPanel(int type, ArrayList<MilitaryUnit> units) {
        this.type = type;
        this.units = units;
        setBackground(Color.GRAY);
        createComponents();
        layoutComponents();
        setVisible();
    }

    protected void createComponents() {
        typeLabel = new JLabel("x " + MilitaryUnit.getUnitName(type));
        countLabel = new JLabel();
        setCountLabel();
    }

    protected void layoutComponents() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBorder(new EmptyBorder(0,0,5,0));
        add(Box.createHorizontalStrut(25));
        add(countLabel);
        add(typeLabel);
        add(Box.createHorizontalGlue());
    }

    public void setVisible() {
        if (units.size() > 0)
            setVisible(true);
        else
            setVisible(false);
        revalidate();
        repaint();
    }

    public void addUnit(MilitaryUnit unit) {
        units.add(unit);
        setCountLabel();
    }

    public MilitaryUnit removeUnit() {
        MilitaryUnit u = units.remove(0);
        setCountLabel();
        return u;
    }

    public void addUnits(ArrayList<MilitaryUnit> units) {
        units.addAll(units);
        setCountLabel();
    }

    public void removeUnit(MilitaryUnit u) {
        boolean i = units.remove(u);
        setCountLabel();
    }

    public void clearUnits() {
        units.clear();
        setCountLabel();
    }

    public void setUnits(ArrayList<MilitaryUnit> units) {
        this.units = units;
        setCountLabel();
    }

    public ArrayList<MilitaryUnit> getUnits() {
        return units;
    }

    public void setCountLabel() {
        countLabel.setText(Integer.toString(units.size()));
        setVisible();
    }

    public boolean isEmpty() {
        return units.size() == 0;
    }

    protected ImageIcon createImageIcon(String path, String description) {
        try {
            Image img = ImageIO.read(new File(path));
            return new ImageIcon(img, description);
        } catch (IOException e) {
            System.err.println("Couldnt find path: " + path);
            e.printStackTrace();
        }
        return null;
    }

    protected int type;
    protected ArrayList<MilitaryUnit> units;
    protected JLabel countLabel;
    protected JLabel typeLabel;
}