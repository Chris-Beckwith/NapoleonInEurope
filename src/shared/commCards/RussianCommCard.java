package shared.commCards;

import javax.swing.*;

/**
 * RussianCommCard.java  Date Created: Dec 7, 2012
 *
 * Purpose: To display the commander info for Russia.
 *
 * Description:  This panel shall display all info relative to the specific nation.
 *
 * @author Chrisb
 */
public class RussianCommCard extends CommCard {

    public RussianCommCard() {
        super();
        createComponents();
        layoutComponents();
        setFonts();
    }

    private void createComponents() {
        commTitle = new JLabel("Russian Commander Info");
        nativeIncome = new JLabel("28");
        skirmishAvail = new JLabel("1811");
        milCost = new JLabel("NA");

        advLabels = new JLabel[4];
        advLabels[0] = new JLabel("Difficult to invade, due to Harsh Campaigns");
        advLabels[1] = new JLabel("Distance from France");
        advLabels[2] = new JLabel("Can buy Irregular Cavalry");
        advLabels[3] = new JLabel("Irregular Cavalry immune to Harsh Campaigns");

        dAdvLabels = new JLabel[4];
        dAdvLabels[0] = new JLabel("Empire Victory Conditions count 1/2");
        dAdvLabels[1] = new JLabel("Two Capital regions");
        dAdvLabels[2] = new JLabel("Serf Uprisings if Russia Sues for Peace");
        dAdvLabels[3] = new JLabel("Don Basin subject to uprisings");

        nationalHero = new JLabel("Kutuzov");
        nicknameLabel = new JLabel("Mikhail Kutuzov,  Prince of Smolensk,  Barclay de Tolly,  Legendary Suvorov");

        genLabels = new JLabel[1];
        genLabels[0] = new JLabel("+1 Initiative before 1800 (+3 with standard General bonus)");

        tacLabels = new JLabel[3];
        tacLabels[0] = new JLabel("+1 Russian infantry charge in same area pre 1800");
        tacLabels[1] = new JLabel("-1 all Pursuit attacks against Russian pieces in the same region");
        tacLabels[2] = new JLabel("+1 each rally attempt");

        qikLabels = new JLabel[3];
        qikLabels[0] = new JLabel("+1 each assisted attack before 1800");
        qikLabels[1] = new JLabel("1 extra rally");
        qikLabels[2] = new JLabel("+1 each rally attempt");
    }
}