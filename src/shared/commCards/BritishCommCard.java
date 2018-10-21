package shared.commCards;

import javax.swing.*;
import java.awt.*;

/**
 * BritishCommCard.java  Date Created: Dec 6, 2012
 *
 * Purpose: To display the commander info for Britain.
 *
 * Description:  This panel shall display all info relative to the specific nation.
 *
 * @author Chrisb
 */
public class BritishCommCard extends CommCard {

    public BritishCommCard() {
        super();
        createComponents();
        layoutComponents();
        setFonts();
    }

    private void createComponents() {
        commTitle = new JLabel("British Commander Info");
        nativeIncome = new JLabel("26");
        skirmishAvail = new JLabel("1806");
        milCost = new JLabel("NA");
        iCavCost = new JLabel("NA");
        admCost = new JLabel("18");

        advLabels = new JLabel[8];
        advLabels[0] = new JLabel("Two National Heros (after June, 1808)");
        advLabels[1] = new JLabel("Island Home and Gibraltar");
        advLabels[2] = new JLabel("Separate Victory Conditions");
        advLabels[3] = new JLabel("+1 in Naval Battles");
        advLabels[4] = new JLabel("Reduced cost of Admirals (18)");
        advLabels[5] = new JLabel("+1 on fire rolls after skirmishers fire");
        advLabels[6] = new JLabel("Colonial Trade");
        advLabels[7] = new JLabel("Can give away production points");

        dAdvLabels = new JLabel[5];
        dAdvLabels[0] = new JLabel("Limited land piece production (max 2 per turn)");
        dAdvLabels[1] = new JLabel("Enforce Continental System");
        dAdvLabels[2] = new JLabel("Naval Battle commitment roll");
        dAdvLabels[3] = new JLabel("Ireland subject to uprisings");
        dAdvLabels[4] = new JLabel("Cavalry countercharge / rally");

        heroLabel = new JLabel("National Heroes:");
        nationalHero = new JLabel("Wellington and Nelson");
        nicknameLabel = new JLabel("Arthur Wellesley,  Duke of Wellington,  \"The Iron Duke\",  Horatio Nelson,  Viscount of the Nile");
        tacticalLabel = new JLabel("Tactical Battles (Wellington only)");
        quickLabel = new JLabel("Quick Battles (Wellington only)");

        genLabels = new JLabel[2];
        genLabels[0] = new JLabel("+1 Initiative (+3 with standard General bonus)(Wellington)");
        genLabels[1] = new JLabel("+1 Naval Battles and Port attack (Nelson)");

        tacLabels = new JLabel[3];
        tacLabels[0] = new JLabel("-1 on all artillery attacks against Wellington's side in the same region");
        tacLabels[1] = new JLabel("+1 attacks by British Elite Infantry in the same battle area");
        tacLabels[2] = new JLabel("+2 each rally attempt");

        qikLabels = new JLabel[4];
        qikLabels[0] = new JLabel("-2 all artillery attacks against Wellington's side in the same region");
        qikLabels[1] = new JLabel("+1 each assisted attack");
        qikLabels[2] = new JLabel("2 extra rally attempts");
        qikLabels[3] = new JLabel("+1 each rally attempt");
    }

}