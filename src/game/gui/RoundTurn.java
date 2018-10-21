package game.gui;

import game.controller.DisplayController;
import game.controller.GameController;
import game.controller.GameInstance;
import game.util.GameMsg;
import game.util.EventLogger;

import javax.swing.*;
import java.awt.*;

/**
 * RoundTurn.java  Date Created: Oct 24, 2012
 *
 * Purpose: To display the current round, turn, production, step, actionOn.
 *
 * Description: This panel shall display the current round and
 * whose turn it is.  It shall display when the next production
 * round will occur.  It shall display what step the game is on
 * and whos action it is on.
 *
 * @author Chrisb
 */
public class RoundTurn extends JPanel {

    public RoundTurn(GameController controller, DisplayController display) {
        super();
        this.controller = controller;
        this.display = display;

        createComponents();
        layoutComponents();

        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(225,175));
        setMaximumSize(new Dimension(225,175));
        setMinimumSize(new Dimension(225,175));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4), BorderFactory.createEmptyBorder(10,10,10,10)));
    }

    private void createComponents() {
        turnLabel = new JLabel(GameMsg.getString("rt.turnLabel"));
        roundLabel = new JLabel(GameMsg.getString("rt.roundLabel"));
        productionRoundLabel = new JLabel(GameMsg.getString("rt.productionRoundLabel"));
        stepLabel = new JLabel(GameMsg.getString("rt.stepLabel"));
        actionOnLabel = new JLabel(GameMsg.getString("rt.actionOnLabel"));

        turn = new JLabel(GameMsg.getString("nation." + controller.game.getTurn()));
        round = new JLabel(controller.game.getRoundYearAsString());
        productionRound = new JLabel(controller.game.getProductionRoundAsString());
        step = new JLabel(GameMsg.getString("rt.step." + controller.game.getStep()));
        actionOn = new JLabel(GameMsg.getString("nation." + controller.game.getTurn()));

        turn.setForeground(Color.LIGHT_GRAY);
        round.setForeground(Color.LIGHT_GRAY);
        productionRound.setForeground(Color.LIGHT_GRAY);
        step.setForeground(Color.LIGHT_GRAY);
        actionOn.setForeground(Color.LIGHT_GRAY);
    }

    private void layoutComponents() {
        JPanel roundPanel = new JPanel();
        roundPanel.setOpaque(false);
        roundPanel.setLayout(new BoxLayout(roundPanel, BoxLayout.LINE_AXIS));

        JPanel productionPanel = new JPanel();
        productionPanel.setOpaque(false);
        productionPanel.setLayout(new BoxLayout(productionPanel, BoxLayout.LINE_AXIS));

        JPanel turnPanel = new JPanel();
        turnPanel.setOpaque(false);
        turnPanel.setLayout(new BoxLayout(turnPanel, BoxLayout.LINE_AXIS));

        JPanel stepPanel = new JPanel();
        stepPanel.setOpaque(false);
        stepPanel.setLayout(new BoxLayout(stepPanel, BoxLayout.LINE_AXIS));

        JPanel actionOnPanel = new JPanel();
        actionOnPanel.setOpaque(false);
        actionOnPanel.setLayout(new BoxLayout(actionOnPanel, BoxLayout.LINE_AXIS));

        Dimension colSpacing = new Dimension(15,0);
        Dimension rowSpacing = new Dimension(0,15);

        turnPanel.add(Box.createHorizontalGlue());
        turnPanel.add(turnLabel);
        turnPanel.add(Box.createRigidArea(colSpacing));
        turnPanel.add(turn);
        turnPanel.add(Box.createHorizontalGlue());

        roundPanel.add(Box.createHorizontalGlue());
        roundPanel.add(roundLabel);
        roundPanel.add(Box.createRigidArea(colSpacing));
        roundPanel.add(round);
        roundPanel.add(Box.createHorizontalGlue());

        productionPanel.add(Box.createHorizontalGlue());
        productionPanel.add(productionRoundLabel);
        productionPanel.add(Box.createRigidArea(colSpacing));
        productionPanel.add(productionRound);
        productionPanel.add(Box.createHorizontalGlue());

        stepPanel.add(Box.createHorizontalGlue());
        stepPanel.add(stepLabel);
        stepPanel.add(Box.createRigidArea(colSpacing));
        stepPanel.add(step);
        stepPanel.add(Box.createHorizontalGlue());

        actionOnPanel.add(Box.createHorizontalGlue());
        actionOnPanel.add(actionOnLabel);
        actionOnPanel.add(Box.createRigidArea(colSpacing));
        actionOnPanel.add(actionOn);
        actionOnPanel.add(Box.createHorizontalGlue());

        add(turnPanel);
        add(Box.createRigidArea(rowSpacing));
        add(roundPanel);
        add(Box.createRigidArea(rowSpacing));
        add(productionPanel);
        add(Box.createRigidArea(rowSpacing));
        add(stepPanel);
        add(Box.createRigidArea(rowSpacing));
        add(actionOnPanel);
        add(Box.createRigidArea(rowSpacing));
    }

    public void setRoundYear(String roundYear) { round.setText(roundYear); }
    public void setTurn(String nation) { turn.setText(nation); }
    public void setProductionRound(String round) { productionRound.setText(round); }

    public void refreshGameState() {
        turn.setText(GameMsg.getString("nation." + controller.game.getTurn()));
        round.setText(controller.game.getRoundYearAsString());
        productionRound.setText(controller.game.getProductionRoundAsString());

        if (controller.game.getStep() == GameInstance.DIPLOMATIC_ROUND) {
            step.setText(GameMsg.getString("title.political." + controller.game.getDiplomaticActionType()));
            actionOn.setText(GameMsg.getString("nation." + controller.game.getDiplomaticNationTurn()));
        } else if (controller.game.getStep() == GameInstance.PRODUCTION_ROUND) {
            step.setText(GameMsg.getString("rt.step." + controller.game.getProductionStep()));
            actionOn.setText(GameMsg.getString("nation." + controller.game.getTurn()));
        } else {
            step.setText(GameMsg.getString("rt.step." + controller.game.getStep()));
            actionOn.setText(GameMsg.getString("nation." + controller.game.getTurn()));
        }
    }

    private JLabel turnLabel;
    private JLabel roundLabel;
    private JLabel productionRoundLabel;
    private JLabel stepLabel;
    private JLabel actionOnLabel;

    private JLabel turn;
    private JLabel round;
    private JLabel productionRound;
    private JLabel step;
    private JLabel actionOn;

    private GameController controller;
    private DisplayController display;
}