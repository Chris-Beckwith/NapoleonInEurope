package shared.commCards;

import javax.swing.*;

/**
 * SpanishCommCard.java  Date Created: Dec 7, 2012
 *
 * Purpose: To display the commander info for Spain.
 *
 * Description:  This panel shall display all info relative to the specific nation.
 *
 * @author Chrisb
 */
public class SpanishCommCard extends CommCard {

    public SpanishCommCard() {
        super();
        createComponents();
        layoutComponents();
        setFonts();
    }

    private void createComponents() {
        commTitle = new JLabel("Spanish Commander Info");
        nativeIncome = new JLabel("13");
        skirmishAvail = new JLabel("NA");
        eInfCost = new JLabel("NA");
        milCost = new JLabel("NA");
        hCavCost = new JLabel("NA");
        iCavCost = new JLabel("NA");

        advLabels = new JLabel[7];
        advLabels[0] = new JLabel("Empire Victory Conditions:");
        advLabels[1] = new JLabel("        Non-capital regions outside Spanish homeland count as 2");
        advLabels[2] = new JLabel("        Regions in Africa count as 2");
        advLabels[3] = new JLabel("No Reverse Grace Period");
        advLabels[4] = new JLabel("Difficult to invade, due to Harsh Campaigns");
        advLabels[5] = new JLabel("Hard to Suppress Uprisings");
        advLabels[6] = new JLabel("Colonial Trade");

        dAdvLabels = new JLabel[7];
        dAdvLabels[0] = new JLabel("Very low native income");
        dAdvLabels[1] = new JLabel("Unfavorable diplomatic rating");
        dAdvLabels[2] = new JLabel("Cannot buy Elite Infantry, Heavy Cavalry");
        dAdvLabels[3] = new JLabel("Can only buy one General besides Castaños");
        dAdvLabels[4] = new JLabel("Can only buy Castaños if Spain is invaded");
        dAdvLabels[5] = new JLabel("All homelands can be annexed in one turn");
        dAdvLabels[6] = new JLabel("Madrid can be annexed before others");

        nationalHero = new JLabel("Castaños");
        nicknameLabel = new JLabel("Francisco Castaños,  Victor of Bailen,  General Castaños");

        genLabels = new JLabel[1];
        genLabels[0] = new JLabel("+1 Restore Region in Spain");

        tacLabels = new JLabel[1];
        tacLabels[0] = new JLabel("None");

        qikLabels = new JLabel[1];
        qikLabels[0] = new JLabel("None");
    }
}