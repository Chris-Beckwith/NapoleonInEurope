package shared.commCards;

import javax.swing.*;

/**
 * AustrianCommCard.java  Date Created: Dec 7, 2012
 *
 * Purpose: To display the commander info for Russia.
 *
 * Description:  This panel shall display all info relative to the specific nation.
 *
 * @author Chrisb
 */
public class AustrianCommCard extends CommCard {

    public AustrianCommCard() {
        super();
        createComponents();
        layoutComponents();
        setFonts();
    }

    private void createComponents() {
        commTitle = new JLabel("Austrian Commander Info");
        nativeIncome = new JLabel("22");
        skirmishAvail = new JLabel("NA");
        infCost = new JLabel("5");
        milCost = new JLabel("NA");
        iCavCost = new JLabel("NA");

        advLabels = new JLabel[2];
        advLabels[0] = new JLabel("Reduced cost of Infantry (5)");
        advLabels[1] = new JLabel("Empire Victory Condition - Add 2 Austrian regions");

        dAdvLabels = new JLabel[3];
        dAdvLabels[0] = new JLabel("Skirmishing never available");
        dAdvLabels[1] = new JLabel("Close proximity to France");
        dAdvLabels[2] = new JLabel("Center of Europe");

        nationalHero = new JLabel("Charles");
        nicknameLabel = new JLabel("Archduke Charles,  brother to Emperor Francis I of Austria");

        genLabels = new JLabel[2];
        genLabels[0] = new JLabel("+1 Initiative (+3 with standard General bonus)");
        genLabels[1] = new JLabel("+3 Production Points if in Austrian native-owned homeland region during Production round");

        tacLabels = new JLabel[1];
        tacLabels[0] = new JLabel("-1 all Pursuit attacks against Austrian pieces in the same region");

        qikLabels = new JLabel[1];
        qikLabels[0] = new JLabel("+1 each rally attempt");
    }
}