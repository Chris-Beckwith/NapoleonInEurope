package shared.commCards;

import javax.swing.*;
import java.awt.*;

/**
 * FrenchCommCard.java  Date Created: Dec 3, 2012
 *
 * Purpose: To display the commander info for France.
 *
 * Description:  This panel shall display all info relative to the specific nation.
 *
 * @author Chrisb
 */
public class FrenchCommCard extends CommCard {

    public FrenchCommCard() {
        super();
        createComponents();
        layoutComponents();
        setFonts();
    }

    private void createComponents() {
        commTitle = new JLabel("French Commander Info");
        nativeIncome = new JLabel("30");
        skirmishAvail = new JLabel("1796");
        milCost = new JLabel("NA");
        iCavCost = new JLabel("NA");

        advLabels = new JLabel[5];
        advLabels[0] = new JLabel("Enforce Continental System");
        advLabels[1] = new JLabel("Initiative Bonus (1800-1812)");
        advLabels[2] = new JLabel("Forced March Bonus (1800-1812)");
        advLabels[3] = new JLabel("Charge bonus after skirmishers fire");
        advLabels[4] = new JLabel("Colonial Trade");

        dAdvLabels = new JLabel[5];
        dAdvLabels[0] = new JLabel("Extra Commitment roll upon losing Napoleon");
        dAdvLabels[1] = new JLabel("Commitment Rating goes up to 8 upon losing Napoleon");
        dAdvLabels[2] = new JLabel("Subtract 6 regions when calculating prestige in empire game");
        dAdvLabels[3] = new JLabel("Reverse grace period lasts for 12 months");
        dAdvLabels[4] = new JLabel("The region of Vendee is subject to uprising");

        nationalHero = new JLabel("Napol�on");
        nicknameLabel = new JLabel("Napol�on Bonaparte,  French Emperor,  The Little Corporal,  \"Corsican Orge\"");

        genLabels = new JLabel[3];
        genLabels[0] = new JLabel("+2 Initiative (+4 with standard General bonus)");
        genLabels[1] = new JLabel("+1 Forced March (+2 with standard General bonus)");
        genLabels[2] = new JLabel("+1 PAP if in Paris (and Paris is liberated) during any Production Round");

        tacLabels = new JLabel[3];
        tacLabels[0] = new JLabel("Gives +1 charge and rally bonus to other French Generals in same region");
        tacLabels[1] = new JLabel("+1 attacks by French artillery or Elite Infantry in the same battle area");
        tacLabels[2] = new JLabel("+2 each rally attempt");

        qikLabels = new JLabel[5];
        qikLabels[0] = new JLabel("+1 assisted attack and rally of other French Generals in same region");
        qikLabels[1] = new JLabel("2 extra assisted attacks (3 including standard General attack)");
        qikLabels[2] = new JLabel("+1 each assisted attack");
        qikLabels[3] = new JLabel("1 extra rally attempt");
        qikLabels[4] = new JLabel("+1 each rally attempt");
    }
}