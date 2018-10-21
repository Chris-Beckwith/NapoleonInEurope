package game.gui.info;

import game.util.GameMsg;

import javax.swing.*;
import java.awt.*;

/**
 * NationProductInfo.java  Date Created: 09 14, 2013
 *
 * Purpose: To display the nation's production information.
 *
 * Description: This class will display all information related
 * to a nation's production.
 *
 * @author Chris
 */
public class NationProductInfo extends JPanel {

    public NationProductInfo() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBackground(Color.GRAY);
        createComponents();
        layoutComponents();
    }

    public void createComponents() {
        int fontSize = 16;
        JLabel productionValueTitle = new JLabel(GameMsg.getString("ns.productionValue"));
        productionValueLabel = new JLabel();
        productionValueTitle.setFont(new Font("Times New Roman", Font.BOLD, fontSize));
        productionValueLabel.setFont(new Font("Times New Roman", Font.BOLD, fontSize));
        JLabel productionSavedTitle = new JLabel(GameMsg.getString("ns.productionSaved"));
        productionSavedLabel = new JLabel();
        productionSavedTitle.setFont(new Font("Times New Roman", Font.BOLD, fontSize));
        productionSavedLabel.setFont(new Font("Times New Roman", Font.BOLD, fontSize));
        JLabel papTitle = new JLabel(GameMsg.getString("ns.paps"));
        papLabel = new JLabel();
        papTitle.setFont(new Font("Times New Roman", Font.BOLD, fontSize));
        papLabel.setFont(new Font("Times New Roman", Font.BOLD, fontSize));

        valuePanel = new JPanel();
        valuePanel.setOpaque(false);
        valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.LINE_AXIS));
        valuePanel.add(Box.createHorizontalGlue());
        valuePanel.add(productionValueTitle);
        valuePanel.add(Box.createHorizontalStrut(5));
        valuePanel.add(productionValueLabel);
        valuePanel.add(Box.createHorizontalGlue());

        savedPanel = new JPanel();
        savedPanel.setOpaque(false);
        savedPanel.setLayout(new BoxLayout(savedPanel, BoxLayout.LINE_AXIS));
        savedPanel.add(Box.createHorizontalGlue());
        savedPanel.add(productionSavedTitle);
        savedPanel.add(Box.createHorizontalStrut(5));
        savedPanel.add(productionSavedLabel);
        savedPanel.add(Box.createHorizontalGlue());

        papPanel = new JPanel();
        papPanel.setOpaque(false);
        papPanel.setLayout(new BoxLayout(papPanel, BoxLayout.LINE_AXIS));
        papPanel.add(Box.createHorizontalGlue());
        papPanel.add(papTitle);
        papPanel.add(Box.createHorizontalStrut(5));
        papPanel.add(papLabel);
        papPanel.add(Box.createHorizontalGlue());
    }

    public void layoutComponents() {
        removeAll();
        add(Box.createVerticalStrut(15));
        add(valuePanel);
        add(savedPanel);
        add(papPanel);
        add(Box.createVerticalStrut(5));
    }

    public void setProducts(int value, int saved, int paps) {
        productionValueLabel.setText("" + value);
        productionSavedLabel.setText("" + saved);
        papLabel.setText("" + paps);
        layoutComponents();
        revalidate();
    }

    private JPanel valuePanel;
    private JPanel savedPanel;
    private JPanel papPanel;
    private JLabel productionValueLabel;
    private JLabel productionSavedLabel;
    private JLabel papLabel;
}
