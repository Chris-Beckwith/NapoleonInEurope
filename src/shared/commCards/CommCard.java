package shared.commCards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * CommCard.java  Date Created: Dec 4, 2012
 *
 * Purpose: Generic Comm Card all other comm cards extend.
 *
 * Description:
 *
 * @author Chrisb
 */
public abstract class CommCard extends JPanel {

    public CommCard() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(800,650));
//        setMaximumSize();
//        setMinimumSize();
    }

    protected void setFonts() {
        commTitle.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        advantagesLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        disadvantagesLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        heroLabel.setFont(new Font("Times New Roman", Font.BOLD, 17));
        nationalHero.setFont(new Font("Times New Roman", Font.BOLD, 16));
        genericLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        tacticalLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        quickLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        pieceCostLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
    }

    protected void layoutComponents() {
        layoutPiecePanel();
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
        titlePanel.setBackground(Color.LIGHT_GRAY);
        titlePanel.add(Box.createHorizontalGlue());
        titlePanel.add(commTitle);
        titlePanel.add(Box.createHorizontalGlue());
        add(Box.createRigidArea(dblLineGap));
        add(titlePanel);

        JPanel incomeSkirmish = new JPanel();
        incomeSkirmish.setLayout(new BoxLayout(incomeSkirmish, BoxLayout.LINE_AXIS));
        incomeSkirmish.setBackground(Color.LIGHT_GRAY);
        incomeSkirmish.add(Box.createHorizontalGlue());
        incomeSkirmish.add(incomeLabel);
        incomeSkirmish.add(Box.createRigidArea(new Dimension(5,0)));
        incomeSkirmish.add(nativeIncome);
        incomeSkirmish.add(Box.createHorizontalGlue());
        incomeSkirmish.add(skirmishLabel);
        incomeSkirmish.add(Box.createRigidArea(new Dimension(5,0)));
        incomeSkirmish.add(skirmishAvail);
        incomeSkirmish.add(Box.createHorizontalGlue());
        add(Box.createRigidArea(new Dimension(0,5)));
        add(incomeSkirmish);

        JPanel midPanel = new JPanel();
        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.LINE_AXIS));
        midPanel.setBackground(Color.LIGHT_GRAY);


        midPanel.add(Box.createRigidArea(midSpacing));

        //Adv
        JPanel advantages = new JPanel();
        advantages.setLayout(new BoxLayout(advantages, BoxLayout.PAGE_AXIS));
        advantages.setBackground(Color.LIGHT_GRAY);
        advantages.add(advantagesLabel);

        for (JLabel label : advLabels) {
            advantages.add(Box.createRigidArea(lineGap));
            advantages.add(label);
        }
        advantages.add(Box.createVerticalGlue());

        midPanel.add(Box.createHorizontalGlue());
        midPanel.add(advantages);

        midPanel.add(Box.createHorizontalGlue());

        //dAdv
        JPanel disadvantages = new JPanel();
        disadvantages.setLayout(new BoxLayout(disadvantages, BoxLayout.PAGE_AXIS));
        disadvantages.setBackground(Color.LIGHT_GRAY);
        disadvantages.add(disadvantagesLabel);

        for(JLabel label : dAdvLabels) {
            disadvantages.add(Box.createRigidArea(lineGap));
            disadvantages.add(label);
        }
        disadvantages.add(Box.createVerticalGlue());

        midPanel.add(disadvantages);
        midPanel.add(Box.createHorizontalGlue());

        add(Box.createRigidArea(new Dimension(0,15)));
        add(midPanel);

        JPanel heroLabelPanel = new JPanel();
        heroLabelPanel.setLayout(new BoxLayout(heroLabelPanel, BoxLayout.LINE_AXIS));
        heroLabelPanel.setBackground(Color.LIGHT_GRAY);
        heroLabelPanel.add(Box.createHorizontalGlue());
        heroLabelPanel.add(heroLabel);
        heroLabelPanel.add(Box.createRigidArea(new Dimension(5,0)));
        heroLabelPanel.add(nationalHero);
        heroLabelPanel.add(Box.createHorizontalGlue());

        add(Box.createRigidArea(new Dimension(0,25)));
        add(heroLabelPanel);

        JPanel nickPanel = new JPanel();
        nickPanel.setLayout(new BoxLayout(nickPanel, BoxLayout.LINE_AXIS));
        nickPanel.setBackground(Color.LIGHT_GRAY);
        nickPanel.add(Box.createHorizontalGlue());
        nickPanel.add(nicknameLabel);
        nickPanel.add(Box.createHorizontalGlue());

        add(nickPanel);

        JPanel discPanel = new JPanel();
        discPanel.setLayout(new BoxLayout(discPanel, BoxLayout.PAGE_AXIS));
        discPanel.setBackground(Color.LIGHT_GRAY);

        JPanel genPanel = new JPanel();
        genPanel.setLayout(new BoxLayout(genPanel, BoxLayout.PAGE_AXIS));
        genPanel.setBackground(Color.LIGHT_GRAY);
        genPanel.add(genericLabel);

        for (JLabel label : genLabels) {
            genPanel.add(Box.createRigidArea(lineGap));
            genPanel.add(label);
        }

        JPanel tacPanel = new JPanel();
        tacPanel.setLayout(new BoxLayout(tacPanel, BoxLayout.PAGE_AXIS));
        tacPanel.setBackground(Color.LIGHT_GRAY);
        tacPanel.add(tacticalLabel);

        for (JLabel label : tacLabels) {
            tacPanel.add(Box.createRigidArea(lineGap));
            tacPanel.add(label);
        }

        JPanel qikPanel = new JPanel();
        qikPanel.setLayout(new BoxLayout(qikPanel, BoxLayout.PAGE_AXIS));
        qikPanel.setBackground(Color.LIGHT_GRAY);
        qikPanel.add(quickLabel);

        for (JLabel label : qikLabels) {
            qikPanel.add(Box.createRigidArea(lineGap));
            qikPanel.add(label);
        }

        discPanel.add(Box.createRigidArea(new Dimension(0,10)));
        discPanel.add(genPanel);
        discPanel.add(Box.createRigidArea(new Dimension(0,10)));
        discPanel.add(tacPanel);
        discPanel.add(Box.createRigidArea(new Dimension(0,10)));
        discPanel.add(qikPanel);

        JPanel discGluePanel = new JPanel();
        discGluePanel.setLayout(new BoxLayout(discGluePanel, BoxLayout.LINE_AXIS));
        discGluePanel.setBackground(Color.LIGHT_GRAY);
        discGluePanel.add(Box.createHorizontalGlue());
        discGluePanel.add(discPanel);
        discGluePanel.add(Box.createHorizontalGlue());
        discGluePanel.add(piecePanel);
        discGluePanel.add(Box.createHorizontalGlue());

        add(Box.createRigidArea(lineGap));
        add(discGluePanel);
        add(Box.createRigidArea(dblLineGap));
        add(Box.createVerticalGlue());
    }

    protected void layoutPiecePanel() {
        piecePanel = new JPanel();
        piecePanel.setLayout(new BoxLayout(piecePanel, BoxLayout.PAGE_AXIS));
        piecePanel.setBackground(Color.LIGHT_GRAY);


        JPanel lineItem = new JPanel();
        lineItem.setLayout(new BoxLayout(lineItem, BoxLayout.LINE_AXIS));
        lineItem.setBackground(Color.LIGHT_GRAY);
        lineItem.add(pieceCostLabel);

        piecePanel.add(lineItem);

        lineItem = new JPanel();
        lineItem.setLayout(new BoxLayout(lineItem, BoxLayout.LINE_AXIS));
        lineItem.setBackground(Color.LIGHT_GRAY);
        lineItem.add(infantry);
        lineItem.add(Box.createRigidArea(piecePanelGap));
        lineItem.add(infCost);

        piecePanel.add(lineItem);

        lineItem = new JPanel();
        lineItem.setLayout(new BoxLayout(lineItem, BoxLayout.LINE_AXIS));
        lineItem.setBackground(Color.LIGHT_GRAY);
        lineItem.add(eliteInf);
        lineItem.add(Box.createRigidArea(piecePanelGap));
        lineItem.add(eInfCost);

        piecePanel.add(lineItem);

        lineItem = new JPanel();
        lineItem.setLayout(new BoxLayout(lineItem, BoxLayout.LINE_AXIS));
        lineItem.setBackground(Color.LIGHT_GRAY);
        lineItem.add(militia);
        lineItem.add(Box.createRigidArea(piecePanelGap));
        lineItem.add(milCost);

        piecePanel.add(lineItem);

        lineItem = new JPanel();
        lineItem.setLayout(new BoxLayout(lineItem, BoxLayout.LINE_AXIS));
        lineItem.setBackground(Color.LIGHT_GRAY);
        lineItem.add(cavalry);
        lineItem.add(Box.createRigidArea(piecePanelGap));
        lineItem.add(cavCost);

        piecePanel.add(lineItem);

        lineItem = new JPanel();
        lineItem.setLayout(new BoxLayout(lineItem, BoxLayout.LINE_AXIS));
        lineItem.setBackground(Color.LIGHT_GRAY);
        lineItem.add(hCavalry);
        lineItem.add(Box.createRigidArea(piecePanelGap));
        lineItem.add(hCavCost);

        piecePanel.add(lineItem);

        lineItem = new JPanel();
        lineItem.setLayout(new BoxLayout(lineItem, BoxLayout.LINE_AXIS));
        lineItem.setBackground(Color.LIGHT_GRAY);
        lineItem.add(iCavalry);
        lineItem.add(Box.createRigidArea(piecePanelGap));
        lineItem.add(iCavCost);

        piecePanel.add(lineItem);

        lineItem = new JPanel();
        lineItem.setLayout(new BoxLayout(lineItem, BoxLayout.LINE_AXIS));
        lineItem.setBackground(Color.LIGHT_GRAY);
        lineItem.add(artillery);
        lineItem.add(Box.createRigidArea(piecePanelGap));
        lineItem.add(artCost);

        piecePanel.add(lineItem);

        lineItem = new JPanel();
        lineItem.setLayout(new BoxLayout(lineItem, BoxLayout.LINE_AXIS));
        lineItem.setBackground(Color.LIGHT_GRAY);
        lineItem.add(hArtillery);
        lineItem.add(Box.createRigidArea(piecePanelGap));
        lineItem.add(hArtCost);

        piecePanel.add(lineItem);

        lineItem = new JPanel();
        lineItem.setLayout(new BoxLayout(lineItem, BoxLayout.LINE_AXIS));
        lineItem.setBackground(Color.LIGHT_GRAY);
        lineItem.add(general);
        lineItem.add(Box.createRigidArea(piecePanelGap));
        lineItem.add(genCost);

        piecePanel.add(lineItem);

        lineItem = new JPanel();
        lineItem.setLayout(new BoxLayout(lineItem, BoxLayout.LINE_AXIS));
        lineItem.setBackground(Color.LIGHT_GRAY);
        lineItem.add(navalSquad);
        lineItem.add(Box.createRigidArea(piecePanelGap));
        lineItem.add(navCost);

        piecePanel.add(lineItem);

        lineItem = new JPanel();
        lineItem.setLayout(new BoxLayout(lineItem, BoxLayout.LINE_AXIS));
        lineItem.setBackground(Color.LIGHT_GRAY);
        lineItem.add(admiral);
        lineItem.add(Box.createRigidArea(piecePanelGap));
        lineItem.add(admCost);

        piecePanel.add(lineItem);
    }

    public void showMe() {
        setVisible(true);
    }

    public void hideMe() {
        setVisible(false);
    }

    protected JLabel commTitle;
    protected JLabel incomeLabel = new JLabel("Native Income:");
    protected JLabel nativeIncome;
    protected JLabel skirmishLabel = new JLabel("Skirmishing Available:");
    protected JLabel skirmishAvail;
    protected JLabel pieceCostLabel = new JLabel("Piece Costs:");
    protected JLabel advantagesLabel = new JLabel("Advantages:");
    protected JLabel disadvantagesLabel = new JLabel("Disadvantages:");
    protected JLabel heroLabel = new JLabel("National Hero:");
    protected JLabel nationalHero;
    protected JLabel nicknameLabel;
    protected JLabel genericLabel = new JLabel("Generic:");
    protected JLabel tacticalLabel = new JLabel("Tactical Battles:");
    protected JLabel quickLabel = new JLabel("Quick Battles:");

    protected JLabel infantry = new JLabel("Infantry");
    protected JLabel eliteInf = new JLabel("Elite Infantry");
    protected JLabel militia = new JLabel("Militia");
    protected JLabel cavalry = new JLabel("Cavalry");
    protected JLabel hCavalry = new JLabel("Heavy Cavalry");
    protected JLabel iCavalry = new JLabel("Irregular Cavalry");
    protected JLabel artillery = new JLabel("Artillery");
    protected JLabel hArtillery = new JLabel("Horse Artillery");
    protected JLabel general = new JLabel("General");
    protected JLabel navalSquad = new JLabel("Naval Squadron");
    protected JLabel admiral = new JLabel("Admiral");

    protected JLabel infCost = new JLabel("6");
    protected JLabel eInfCost = new JLabel("8");
    protected JLabel milCost = new JLabel("3");
    protected JLabel cavCost = new JLabel("9");
    protected JLabel hCavCost = new JLabel("11");
    protected JLabel iCavCost = new JLabel("6");
    protected JLabel artCost = new JLabel("10");
    protected JLabel hArtCost = new JLabel("13");
    protected JLabel genCost = new JLabel("12");
    protected JLabel navCost = new JLabel("15");
    protected JLabel admCost = new JLabel("25");

    protected JLabel[] advLabels;
    protected JLabel[] dAdvLabels;
    protected JLabel[] genLabels;
    protected JLabel[] tacLabels;
    protected JLabel[] qikLabels;

    protected JPanel piecePanel;

    protected Dimension midSpacing = new Dimension(10,0);
    protected Dimension lineGap = new Dimension(0,5);
    protected Dimension dblLineGap = new Dimension(0,10);
    protected Dimension piecePanelGap = new Dimension(5,0);
}