package shared.commCards;

import javax.swing.*;

/**
 * OttomanCommCard.java  Date Created: Dec 7, 2012
 *
 * Purpose: To display the commander info for the Ottoman Empire.
 *
 * Description:  This panel shall display all info relative to the specific nation.
 *
 * @author Chrisb
 */
public class OttomanCommCard extends CommCard {

    public OttomanCommCard() {
        super();
        createComponents();
        layoutComponents();
        setFonts();
    }

    private void createComponents() {
        commTitle = new JLabel("Ottoman Commander Info");
        nativeIncome = new JLabel("9");
        skirmishAvail = new JLabel("NA");
        infCost = new JLabel("NA");
        eInfCost = new JLabel("NA");
        cavCost = new JLabel("NA");
        hCavCost = new JLabel("NA");
        artCost = new JLabel("18");

        advLabels = new JLabel[5];
        advLabels[0] = new JLabel("Large Initial Empire (16)");
        advLabels[1] = new JLabel("Can buy Militia and IC");
        advLabels[2] = new JLabel("Difficult to invade, due to Harsh Campaigns");
        advLabels[3] = new JLabel("Troops do not suffer Harsh Campaigns in North Afirca");
        advLabels[4] = new JLabel("Controls passage between Aegean and Black Seas");

        dAdvLabels = new JLabel[6];
        dAdvLabels[0] = new JLabel("Cannot buy Infantry, Elite Infantry, Cavalry, Heavy Cavalry, or Horse Artillery");
        dAdvLabels[1] = new JLabel("Can only have one (1) Artillery unit on the map at a time");
        dAdvLabels[2] = new JLabel("-1 in Naval battles");
        dAdvLabels[3] = new JLabel("Less income from Regions and Capitals");
        dAdvLabels[4] = new JLabel("Unfavorable diplomatic rating");
        dAdvLabels[5] = new JLabel("Arabian uprisings, cannot Train Militia");

        nationalHero = new JLabel("Mohammed Ali");
        nicknameLabel = new JLabel("Ottoman Pasha of Egypt");

        genLabels = new JLabel[3];
        genLabels[0] = new JLabel("+1 Supress Uprising");
        genLabels[1] = new JLabel("+1 Annex Minor Nation");
        genLabels[2] = new JLabel("+1 Restore Region in North Africa");

        tacLabels = new JLabel[1];
        tacLabels[0] = new JLabel("+1 Ottoman charge in same battle area");

        qikLabels = new JLabel[2];
        qikLabels[0] = new JLabel("1 extra assisted attack");
        qikLabels[1] = new JLabel("+1 each assisted attack");
    }
}