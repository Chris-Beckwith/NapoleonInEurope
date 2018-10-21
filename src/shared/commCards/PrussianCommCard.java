package shared.commCards;

import javax.swing.*;

/**
 * PrussianCommCard.java  Date Created: Dec 6, 2012
 *
 * Purpose: To display the commander info for Prussia.
 *
 * Description:  This panel shall display all info relative to the specific nation.
 *
 * @author Chrisb
 */
public class PrussianCommCard extends CommCard {

    public PrussianCommCard() {
        super();
        createComponents();
        layoutComponents();
        setFonts();
    }

    private void createComponents() {
        commTitle = new JLabel("Prussian Commander Info");
        nativeIncome = new JLabel("18");
        skirmishAvail = new JLabel("1813");
        iCavCost = new JLabel("NA");

        advLabels = new JLabel[5];
        advLabels[0] = new JLabel("Empire Victory Conditions:");
        advLabels[1] = new JLabel("      Non-capital regions outside Prussian homeland count as 2");
        advLabels[2] = new JLabel("      African regions count as 2");
        advLabels[3] = new JLabel("Can buy Militia after 1812 (these cannot enter Africa)");
        advLabels[4] = new JLabel("Capital Commitment Rolls");

        dAdvLabels = new JLabel[3];
        dAdvLabels[0] = new JLabel("Low native income");
        dAdvLabels[1] = new JLabel("Close proximity to France");
        dAdvLabels[2] = new JLabel("Center of Europe");

        nationalHero = new JLabel("Blücher");
        nicknameLabel = new JLabel("Gebhard Leberecht von Blücher,  Prince of Wahlstadt,  \"Marshal Forward\"");

        genLabels = new JLabel[1];
        genLabels[0] = new JLabel("+1 Initiative starting August 1813 (+3 with standard General bonus)");

        tacLabels = new JLabel[2];
        tacLabels[0] = new JLabel("+1 all Pursuit attacks made by Prussian pieces in the same region");
        tacLabels[1] = new JLabel("+1 each rally attempt");

        qikLabels = new JLabel[2];
        qikLabels[0] = new JLabel("+1 each assisted attack");
        qikLabels[1] = new JLabel("+1 each rally attempt");
    }

}