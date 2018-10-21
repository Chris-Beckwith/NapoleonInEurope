package lobby.gui;

import lobby.controller.NapoleonController;
import shared.controller.LobbyConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import util.Messages;
import util.Logger;

/**
 * GameOptions.java  Date Created: Apr 24, 2012
 *
 * Purpose: Displays the game options for a game.
 *
 * Description: The user will select the game options they want before starting the game.
 * including the game mode, scenario and duration.
 *
 * @author Chrisb
 */
public class GameOptions extends JPanel implements ActionListener, MouseListener {
    private NapoleonController controller;
    private boolean isHost;
    private boolean fromServer;

    public GameOptions(NapoleonController controller) {
        super(new GridBagLayout());

        isHost = false;
        fromServer = false;

        this.controller = controller;

        //Create components
        createComponents();

        //Layout components
        layoutComponents();

        setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
    }

    private void createComponents() {
        //JLabels
        gameModeLabel = new JLabel(Messages.getString("gameMode.label"));
        gameSceneLabel = new JLabel(Messages.getString("gameScene.label"));
        gameDurationLabel = new JLabel(Messages.getString("gameDuration.label"));

        quickBattleLabel = new JLabel(Messages.getString("quickBattle.label"));
        heroesLabel = new JLabel(Messages.getString("heroes.label"));
        forceMarchLabel = new JLabel(Messages.getString("forceMarch.label"));
        skirmishLabel = new JLabel(Messages.getString("skirmish.label"));
        counterChargeLabel = new JLabel(Messages.getString("counterCharge.label"));
        eliteLabel = new JLabel(Messages.getString("elite.label"));
        generalsLabel = new JLabel(Messages.getString("generals.label"));
        admiralsLabel = new JLabel(Messages.getString("admirals.label"));
        rightOfPassageLabel = new JLabel(Messages.getString("rightOfPassage.label"));
        uprisingLabel = new JLabel(Messages.getString("uprising.label"));
        voluntaryPAPLabel = new JLabel(Messages.getString("voluntaryPAP.label"));
        controlNPNLabel = new JLabel(Messages.getString("controlNPN.label"));
        annexLabel = new JLabel(Messages.getString("annex.label"));
        recruitLabel = new JLabel(Messages.getString("recruit.label"));
        restoreLabel = new JLabel(Messages.getString("restore.label"));
        continentalSystemLabel = new JLabel(Messages.getString("continentalSystem.label"));
        raiseMilitiaBySeaLabel = new JLabel(Messages.getString("raiseMilitiaBySea.label"));
        raiseMilitiaByLandLabel = new JLabel(Messages.getString("raiseMilitiaByLand.label"));
        harshCampaignLabel = new JLabel(Messages.getString("harshCampaign.label"));
        blindSetupLabel = new JLabel(Messages.getString("blindSetup.label"));

        //JBoxes
        gameModeBox = new JComboBox(new String[]{Messages.getString("gameMode.Empire"), Messages.getString("gameMode.Team")});
        gameSceneBox = new JComboBox(new String[]{Messages.getString("gameScene.X")});  //TODO
        gameDurationBox = new JComboBox(new String[]{Messages.getString("gameDuration.6"),
                                                     Messages.getString("gameDuration.12"),
                                                     Messages.getString("gameDuration.18"),
                                                     Messages.getString("gameDuration.24"),
                                                     Messages.getString("gameDuration.36"),
                                                     Messages.getString("gameDuration.48"), });

        quickBattleBox = new JCheckBox();
        heroesBox = new JCheckBox();
        forceMarchBox = new JCheckBox();
        skirmishBox = new JCheckBox();
        counterChargeBox = new JCheckBox();
        eliteBox = new JCheckBox();
        generalsBox = new JCheckBox();
        admiralsBox = new JCheckBox();
        rightOfPassageBox = new JCheckBox();
        uprisingBox = new JCheckBox();
        voluntaryPAPBox = new JCheckBox();
        controlNPNBox = new JCheckBox();
        annexBox = new JCheckBox();
        recruitBox = new JCheckBox();
        restoreBox = new JCheckBox();
        continentalSystemBox = new JCheckBox();
        raiseMilitiaBySeaBox = new JCheckBox();
        raiseMilitiaByLandBox = new JCheckBox();
        harshCampaignBox = new JCheckBox();
        blindSetupBox = new JCheckBox();

        quickBattleLabel.addMouseListener(this);
        heroesLabel.addMouseListener(this);
        forceMarchLabel.addMouseListener(this);
        skirmishLabel.addMouseListener(this);
        counterChargeLabel.addMouseListener(this);
        eliteLabel.addMouseListener(this);
        generalsLabel.addMouseListener(this);
        admiralsLabel.addMouseListener(this);
        rightOfPassageLabel.addMouseListener(this);
        uprisingLabel.addMouseListener(this);
        voluntaryPAPLabel.addMouseListener(this);
        controlNPNLabel.addMouseListener(this);
        annexLabel.addMouseListener(this);
        recruitLabel.addMouseListener(this);
        restoreLabel.addMouseListener(this);
        continentalSystemLabel.addMouseListener(this);
        raiseMilitiaBySeaLabel.addMouseListener(this);
        raiseMilitiaByLandLabel.addMouseListener(this);
        harshCampaignLabel.addMouseListener(this);
        blindSetupLabel.addMouseListener(this);

        gameModeBox.setActionCommand(Messages.getString("gameMode.action"));
        gameSceneBox.setActionCommand(Messages.getString("gameScene.action"));
        gameDurationBox.setActionCommand(Messages.getString("gameDuration.action"));

        gameModeBox.addActionListener(this);
        gameSceneBox.addActionListener(this);
        gameDurationBox.addActionListener(this);


        quickBattleBox.setActionCommand(Messages.getString("quickBattle.action"));
        heroesBox.setActionCommand(Messages.getString("heroes.action"));
        forceMarchBox.setActionCommand(Messages.getString("forceMarch.action"));
        skirmishBox.setActionCommand(Messages.getString("skirmish.action"));
        counterChargeBox.setActionCommand(Messages.getString("counterCharge.action"));
        eliteBox.setActionCommand(Messages.getString("elite.action"));
        generalsBox.setActionCommand(Messages.getString("generals.action"));
        admiralsBox.setActionCommand(Messages.getString("admirals.action"));
        rightOfPassageBox.setActionCommand(Messages.getString("rightOfPassage.action"));
        uprisingBox.setActionCommand(Messages.getString("uprising.action"));
        voluntaryPAPBox.setActionCommand(Messages.getString("voluntaryPAP.action"));
        controlNPNBox.setActionCommand(Messages.getString("controlNPN.action"));
        annexBox.setActionCommand(Messages.getString("annex.action"));
        recruitBox.setActionCommand(Messages.getString("recruit.action"));
        restoreBox.setActionCommand(Messages.getString("restore.action"));
        continentalSystemBox.setActionCommand(Messages.getString("continentalSystem.action"));
        raiseMilitiaBySeaBox.setActionCommand(Messages.getString("raiseMilitiaBySea.action"));
        raiseMilitiaByLandBox.setActionCommand(Messages.getString("raiseMilitiaByLand.action"));
        harshCampaignBox.setActionCommand(Messages.getString("harshCampaign.action"));
        blindSetupBox.setActionCommand(Messages.getString("blindSetup.action"));

        quickBattleBox.addActionListener(this);
        heroesBox.addActionListener(this);
        forceMarchBox.addActionListener(this);
        skirmishBox.addActionListener(this);
        counterChargeBox.addActionListener(this);
        eliteBox.addActionListener(this);
        generalsBox.addActionListener(this);
        admiralsBox.addActionListener(this);
        rightOfPassageBox.addActionListener(this);
        uprisingBox.addActionListener(this);
        voluntaryPAPBox.addActionListener(this);
        controlNPNBox.addActionListener(this);
        annexBox.addActionListener(this);
        recruitBox.addActionListener(this);
        restoreBox.addActionListener(this);
        continentalSystemBox.addActionListener(this);
        raiseMilitiaBySeaBox.addActionListener(this);
        raiseMilitiaByLandBox.addActionListener(this);
        harshCampaignBox.addActionListener(this);
        blindSetupBox.addActionListener(this);

        quickBattleLabel.setToolTipText(Messages.getString("quickBattle.tip"));
        heroesLabel.setToolTipText(Messages.getString("heroes.tip"));
        forceMarchLabel.setToolTipText(Messages.getString("forceMarch.tip"));
        skirmishLabel.setToolTipText(Messages.getString("skirmish.tip"));
        counterChargeLabel.setToolTipText(Messages.getString("counterCharge.tip"));
        eliteLabel.setToolTipText(Messages.getString("elite.tip"));
        generalsLabel.setToolTipText(Messages.getString("generals.tip"));
        admiralsLabel.setToolTipText(Messages.getString("admirals.tip"));
        rightOfPassageLabel.setToolTipText(Messages.getString("rightOfPassage.tip"));
        uprisingLabel.setToolTipText(Messages.getString("uprising.tip"));
        voluntaryPAPLabel.setToolTipText(Messages.getString("voluntaryPAP.tip"));
        controlNPNLabel.setToolTipText(Messages.getString("controlNPN.tip"));
        annexLabel.setToolTipText(Messages.getString("annex.tip"));
        recruitLabel.setToolTipText(Messages.getString("recruit.tip"));
        restoreLabel.setToolTipText(Messages.getString("restore.tip"));
        continentalSystemLabel.setToolTipText(Messages.getString("continentalSystem.tip"));
        raiseMilitiaBySeaLabel.setToolTipText(Messages.getString("raiseMilitiaBySea.tip"));
        raiseMilitiaByLandLabel.setToolTipText(Messages.getString("raiseMilitiaByLand.tip"));
        harshCampaignLabel.setToolTipText(Messages.getString("harshCampaign.tip"));
        blindSetupLabel.setToolTipText(Messages.getString("blindSetup.tip"));
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0,0,0,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        add(gameModeLabel, gbc);
        gbc.gridy = 1;
        add(gameModeBox, gbc);
        gbc.gridy = 3;
        add(gameSceneLabel, gbc);
        gbc.gridy = 4;
        add(gameSceneBox, gbc);
        gbc.gridy = 6;
        add(gameDurationLabel, gbc);
        gbc.gridy = 7;
        add(gameDurationBox, gbc);

        gbc.insets = new Insets(0,0,0,0);
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(quickBattleBox, gbc);
        gbc.gridy = 1;
        add(heroesBox, gbc);
        gbc.gridy = 2;
        add(forceMarchBox, gbc);
        gbc.gridy = 3;
        add(skirmishBox, gbc);
        gbc.gridy = 4;
        add(counterChargeBox, gbc);
        gbc.gridy = 5;
        add(eliteBox, gbc);
        gbc.gridy = 6;
        add(generalsBox, gbc);
        gbc.gridy = 7;
        add(admiralsBox, gbc);
        gbc.gridy = 8;
        add(rightOfPassageBox, gbc);
        gbc.gridy = 9;
        add(uprisingBox, gbc);

        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        add(quickBattleLabel, gbc);
        gbc.gridy = 1;
        add(heroesLabel, gbc);
        gbc.gridy = 2;
        add(forceMarchLabel, gbc);
        gbc.gridy = 3;
        add(skirmishLabel, gbc);
        gbc.gridy = 4;
        add(counterChargeLabel, gbc);
        gbc.gridy = 5;
        add(eliteLabel, gbc);
        gbc.gridy = 6;
        add(generalsLabel, gbc);
        gbc.gridy = 7;
        add(admiralsLabel, gbc);
        gbc.gridy = 8;
        add(rightOfPassageLabel, gbc);
        gbc.gridy = 9;
        add(uprisingLabel, gbc);

        gbc.gridx = 9;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(voluntaryPAPBox, gbc);
        gbc.gridy = 1;
        add(controlNPNBox, gbc);
        gbc.gridy = 2;
        add(annexBox, gbc);
        gbc.gridy = 3;
        add(recruitBox, gbc);
        gbc.gridy = 4;
        add(restoreBox, gbc);
        gbc.gridy = 5;
        add(continentalSystemBox, gbc);
        gbc.gridy = 6;
        add(raiseMilitiaBySeaBox, gbc);
        gbc.gridy = 7;
        add(raiseMilitiaByLandBox, gbc);
        gbc.gridy = 8;
        add(harshCampaignBox, gbc);
        gbc.gridy = 9;
        add(blindSetupBox, gbc);

        gbc.gridx = 10;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        add(voluntaryPAPLabel, gbc);
        gbc.gridy = 1;
        add(controlNPNLabel, gbc);
        gbc.gridy = 2;
        add(annexLabel, gbc);
        gbc.gridy = 3;
        add(recruitLabel, gbc);
        gbc.gridy = 4;
        add(restoreLabel, gbc);
        gbc.gridy = 5;
        add(continentalSystemLabel, gbc);
        gbc.gridy = 6;
        add(raiseMilitiaBySeaLabel, gbc);
        gbc.gridy = 7;
        add(raiseMilitiaByLandLabel, gbc);
        gbc.gridy = 8;
        add(harshCampaignLabel, gbc);        
        gbc.gridy = 9;
        add(blindSetupLabel, gbc);
    }

    public void setDefaultOptions() {
        //Set Defaults
        forceMarchBox.setSelected(true);
        skirmishBox.setSelected(true);
        counterChargeBox.setSelected(true);
        eliteBox.setSelected(true);
        generalsBox.setSelected(true);
        admiralsBox.setSelected(true);
        rightOfPassageBox.setSelected(true);
        uprisingBox.setSelected(true);
        voluntaryPAPBox.setSelected(true);
        controlNPNBox.setSelected(true);
        annexBox.setSelected(true);
        recruitBox.setSelected(true);
        restoreBox.setSelected(true);
        raiseMilitiaBySeaBox.setSelected(true);
        raiseMilitiaByLandBox.setSelected(true);
        harshCampaignBox.setSelected(true);

        System.out.println("GameOptions: setDefaultOptions");

        //Tell controller of the enabled options.
        controller.setLobbyOption(LobbyConstants.FORCE_MARCH, forceMarchBox.isSelected());
        controller.setLobbyOption(LobbyConstants.SKIRMISH, skirmishBox.isSelected());
        controller.setLobbyOption(LobbyConstants.COUNTER_CHARGE, counterChargeBox.isSelected());
        controller.setLobbyOption(LobbyConstants.ELITE, eliteBox.isSelected());
        controller.setLobbyOption(LobbyConstants.GENERALS, generalsBox.isSelected());
        controller.setLobbyOption(LobbyConstants.ADMIRALS, admiralsBox.isSelected());
        controller.setLobbyOption(LobbyConstants.RIGHT_OF_PASSAGE, rightOfPassageBox.isSelected());
        controller.setLobbyOption(LobbyConstants.UPRISING, uprisingBox.isSelected());
        controller.setLobbyOption(LobbyConstants.VOLUNTARY_PAP, voluntaryPAPBox.isSelected());
        controller.setLobbyOption(LobbyConstants.CONTROL_NPN, controlNPNBox.isSelected());
        controller.setLobbyOption(LobbyConstants.ANNEX, annexBox.isSelected());
        controller.setLobbyOption(LobbyConstants.RECRUIT, recruitBox.isSelected());
        controller.setLobbyOption(LobbyConstants.RESTORE, restoreBox.isSelected());
        controller.setLobbyOption(LobbyConstants.RAISE_MILITIA_BY_SEA, raiseMilitiaBySeaBox.isSelected());
        controller.setLobbyOption(LobbyConstants.RAISE_MILITIA_BY_LAND, raiseMilitiaByLandBox.isSelected());
        controller.setLobbyOption(LobbyConstants.HARSH_CAMPAIGNS, harshCampaignBox.isSelected());

        //Game mode: Empire / (Team)
        setGameMode(LobbyConstants.EMPIRE);

        //Scenario: X / (I - IX)
        setScenario(LobbyConstants.X);

        //Duration: 4 Game years (48 Rounds) / (.5 - 3(6) Game years / Total Victory)
        setDuration(LobbyConstants.ROUNDS_48);
    }

    public int getGameMode() {
        return gameModeBox.getSelectedIndex();
    }

    public int getScenario() {
        return gameSceneBox.getSelectedIndex();
    }

    public int getDuration() {
        return gameDurationBox.getSelectedIndex();
    }

    private void setGameMode(int mode) {
        switch(mode) {
            case LobbyConstants.EMPIRE:
                gameModeBox.setSelectedIndex(EMPIRE_INDEX);
                continentalSystemBox.setSelected(false);
                break;
            case LobbyConstants.TEAM:
                gameModeBox.setSelectedIndex(TEAM_INDEX);
                continentalSystemBox.setSelected(true);
                break;
        }
    }

    private void setScenario(int scenario) {
        switch(scenario) {
            case LobbyConstants.I:
                break;
            case LobbyConstants.II:
                break;
            case LobbyConstants.III:
                break;
            case LobbyConstants.IV:
                break;
            case LobbyConstants.V:
                break;
            case LobbyConstants.V_V:
                break;
            case LobbyConstants.VI:
                break;
            case LobbyConstants.VII:
                break;
            case LobbyConstants.VIII:
                break;
            case LobbyConstants.IX:
                break;
            case LobbyConstants.X:
                gameSceneBox.setSelectedIndex(X_INDEX);
                heroesBox.setSelected(false);
                heroesBox.setEnabled(false);
                heroesLabel.setEnabled(false);
                break;
        }
    }

    private void setDuration(int duration) {
        switch(duration) {
            case LobbyConstants.ROUNDS_6:
                gameDurationBox.setSelectedIndex(ROUNDS_6_INDEX);
                break;
            case LobbyConstants.ROUNDS_12:
                gameDurationBox.setSelectedIndex(ROUNDS_12_INDEX);
                break;
            case LobbyConstants.ROUNDS_18:
                gameDurationBox.setSelectedIndex(ROUNDS_18_INDEX);
                break;
            case LobbyConstants.ROUNDS_24:
                gameDurationBox.setSelectedIndex(ROUNDS_24_INDEX);
                break;
            case LobbyConstants.ROUNDS_36:
                gameDurationBox.setSelectedIndex(ROUNDS_36_INDEX);
                break;
            case LobbyConstants.ROUNDS_48:
                gameDurationBox.setSelectedIndex(ROUNDS_48_INDEX);
                break;
        }
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (isHost && !fromServer) {
            if ( cmd.compareTo(Messages.getString("quickBattle.action")) == 0 ) {
                controller.setLobbyOption(LobbyConstants.QUICK_BATTLES, quickBattleBox.isSelected());
            } else if ( cmd.compareTo(Messages.getString("heroes.action")) == 0 ) {
                controller.setLobbyOption(LobbyConstants.HEROES, heroesBox.isSelected());
            } else if ( cmd.compareTo(Messages.getString("forceMarch.action")) == 0 ) {
                controller.setLobbyOption(LobbyConstants.FORCE_MARCH, forceMarchBox.isSelected());
            } else if ( cmd.compareTo(Messages.getString("skirmish.action")) == 0 ) {
                controller.setLobbyOption(LobbyConstants.SKIRMISH, skirmishBox.isSelected());
            } else if ( cmd.compareTo(Messages.getString("counterCharge.action")) == 0 ) {
                controller.setLobbyOption(LobbyConstants.COUNTER_CHARGE, counterChargeBox.isSelected());
            } else if ( cmd.compareTo(Messages.getString("elite.action")) == 0 ) {
                controller.setLobbyOption(LobbyConstants.ELITE, eliteBox.isSelected());
            } else if ( cmd.compareTo(Messages.getString("generals.action")) == 0 ) {
                controller.setLobbyOption(LobbyConstants.GENERALS, generalsBox.isSelected());
            } else if ( cmd.compareTo(Messages.getString("admirals.action")) == 0 ) {
                controller.setLobbyOption(LobbyConstants.ADMIRALS, admiralsBox.isSelected());
            } else if ( cmd.compareTo(Messages.getString("rightOfPassage.action")) == 0 ) {
                controller.setLobbyOption(LobbyConstants.RIGHT_OF_PASSAGE, rightOfPassageBox.isSelected());
            } else if ( cmd.compareTo(Messages.getString("uprising.action")) == 0 ) {
                controller.setLobbyOption(LobbyConstants.UPRISING, uprisingBox.isSelected());
            } else if ( cmd.compareTo(Messages.getString("voluntaryPAP.action")) == 0 ) {
                controller.setLobbyOption(LobbyConstants.VOLUNTARY_PAP, voluntaryPAPBox.isSelected());
            } else if ( cmd.compareTo(Messages.getString("controlNPN.action")) == 0 ) {
                controller.setLobbyOption(LobbyConstants.CONTROL_NPN, controlNPNBox.isSelected());
            } else if ( cmd.compareTo(Messages.getString("annex.action")) == 0 ) {
                controller.setLobbyOption(LobbyConstants.ANNEX, annexBox.isSelected());
            } else if ( cmd.compareTo(Messages.getString("recruit.action")) == 0 ) {
                controller.setLobbyOption(LobbyConstants.RECRUIT, recruitBox.isSelected());
            } else if ( cmd.compareTo(Messages.getString("restore.action")) == 0 ) {
                controller.setLobbyOption(LobbyConstants.RESTORE, restoreBox.isSelected());
            } else if ( cmd.compareTo(Messages.getString("continentalSystem.action")) == 0 ) {
                controller.setLobbyOption(LobbyConstants.CONTINENTAL_SYSTEM, continentalSystemBox.isSelected());
            } else if (cmd.compareTo(Messages.getString("raiseMilitiaBySea.action")) == 0) {
                if (!raiseMilitiaBySeaBox.isSelected()) {
                    raiseMilitiaByLandBox.setSelected(false);
                    controller.setLobbyOption(LobbyConstants.RAISE_MILITIA_BY_LAND, raiseMilitiaByLandBox.isSelected());
                }
                controller.setLobbyOption(LobbyConstants.RAISE_MILITIA_BY_SEA, raiseMilitiaBySeaBox.isSelected());
            } else if ( cmd.compareTo(Messages.getString("raiseMilitiaByLand.action")) == 0 ) {
                controller.setLobbyOption(LobbyConstants.RAISE_MILITIA_BY_LAND, raiseMilitiaByLandBox.isSelected());
            } else if ( cmd.compareTo(Messages.getString("harshCampaign.action")) == 0 ) {
                controller.setLobbyOption(LobbyConstants.HARSH_CAMPAIGNS, harshCampaignBox.isSelected());
            } else if ( cmd.compareTo(Messages.getString("blindSetup.action")) == 0 ) {
                controller.setLobbyOption(LobbyConstants.BLIND_SETUP, blindSetupBox.isSelected());
            } else if ( cmd.compareTo(Messages.getString("gameMode.action")) == 0) {
                int instance = modeIndexToInstance(gameModeBox.getSelectedIndex());
                controller.setLobbyOption(instance, true);
                setGameMode(instance);
            } else if ( cmd.compareTo(Messages.getString("gameScene.action")) == 0) {
                int instance = sceneIndexToInstance(gameSceneBox.getSelectedIndex());
                controller.setLobbyOption(instance, true);
                setScenario(instance);
            } else if ( cmd.compareTo(Messages.getString("gameDuration.action")) == 0) {
                int instance = durationIndexToInstance(gameDurationBox.getSelectedIndex());
                controller.setLobbyOption(instance, true);
                setDuration(instance);
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (isHost && e.getComponent().isEnabled()) {
            System.out.println("GameOptions mouseClicked");
            if ( e.getComponent().equals(quickBattleLabel) ) {
                quickBattleBox.setSelected(!quickBattleBox.isSelected());
                controller.setLobbyOption(LobbyConstants.QUICK_BATTLES, quickBattleBox.isSelected());
            } else if ( e.getComponent().equals(heroesLabel) ) {
                heroesBox.setSelected(!heroesBox.isSelected());
                controller.setLobbyOption(LobbyConstants.HEROES, heroesBox.isSelected());
            } else if ( e.getComponent().equals(forceMarchLabel) ) {
                forceMarchBox.setSelected(!forceMarchBox.isSelected());
                controller.setLobbyOption(LobbyConstants.FORCE_MARCH, forceMarchBox.isSelected());
            } else if ( e.getComponent().equals(skirmishLabel) ) {
                skirmishBox.setSelected(!skirmishBox.isSelected());
                controller.setLobbyOption(LobbyConstants.SKIRMISH, skirmishBox.isSelected());
            } else if ( e.getComponent().equals(counterChargeLabel) ) {
                counterChargeBox.setSelected(!counterChargeBox.isSelected());
                controller.setLobbyOption(LobbyConstants.COUNTER_CHARGE, counterChargeBox.isSelected());
            } else if ( e.getComponent().equals(eliteLabel) ) {
                eliteBox.setSelected(!eliteBox.isSelected());
                controller.setLobbyOption(LobbyConstants.ELITE, eliteBox.isSelected());
            } else if ( e.getComponent().equals(generalsLabel) ) {
                generalsBox.setSelected(!generalsBox.isSelected());
                controller.setLobbyOption(LobbyConstants.GENERALS, generalsBox.isSelected());
            } else if ( e.getComponent().equals(admiralsLabel) ) {
                admiralsBox.setSelected(!admiralsBox.isSelected());
                controller.setLobbyOption(LobbyConstants.ADMIRALS, admiralsBox.isSelected());
            } else if ( e.getComponent().equals(rightOfPassageLabel) ) {
                rightOfPassageBox.setSelected(!rightOfPassageBox.isSelected());
                controller.setLobbyOption(LobbyConstants.RIGHT_OF_PASSAGE, rightOfPassageBox.isSelected());
            } else if ( e.getComponent().equals(uprisingLabel) ) {
                uprisingBox.setSelected(!uprisingBox.isSelected());
                controller.setLobbyOption(LobbyConstants.UPRISING, uprisingBox.isSelected());
            } else if ( e.getComponent().equals(voluntaryPAPLabel) ) {
                voluntaryPAPBox.setSelected(!voluntaryPAPBox.isSelected());
                controller.setLobbyOption(LobbyConstants.VOLUNTARY_PAP, voluntaryPAPBox.isSelected());
            } else if ( e.getComponent().equals(controlNPNLabel) ) {
                controlNPNBox.setSelected(!controlNPNBox.isSelected());
                controller.setLobbyOption(LobbyConstants.CONTROL_NPN, controlNPNBox.isSelected());
            } else if ( e.getComponent().equals(annexLabel) ) {
                annexBox.setSelected(!annexBox.isSelected());
                controller.setLobbyOption(LobbyConstants.ANNEX, annexBox.isSelected());
            } else if ( e.getComponent().equals(recruitLabel) ) {
                recruitBox.setSelected(!recruitBox.isSelected());
                controller.setLobbyOption(LobbyConstants.RECRUIT, recruitBox.isSelected());
            } else if ( e.getComponent().equals(restoreLabel) ) {
                restoreBox.setSelected(!restoreBox.isSelected());
                controller.setLobbyOption(LobbyConstants.RESTORE, restoreBox.isSelected());
            } else if ( e.getComponent().equals(continentalSystemLabel) ) {
                continentalSystemBox.setSelected(!continentalSystemBox.isSelected());
                controller.setLobbyOption(LobbyConstants.CONTINENTAL_SYSTEM, continentalSystemBox.isSelected());
            } else if ( e.getComponent().equals(raiseMilitiaBySeaLabel) ) {
                raiseMilitiaBySeaBox.setSelected(!raiseMilitiaBySeaBox.isSelected());
                if (!raiseMilitiaBySeaBox.isSelected()) {
                    raiseMilitiaByLandBox.setSelected(false);
                    controller.setLobbyOption(LobbyConstants.RAISE_MILITIA_BY_LAND, raiseMilitiaByLandBox.isSelected());
                }
                controller.setLobbyOption(LobbyConstants.RAISE_MILITIA_BY_SEA, raiseMilitiaBySeaBox.isSelected());
            } else if ( e.getComponent().equals(raiseMilitiaByLandLabel) ) {
                raiseMilitiaByLandBox.setSelected(!raiseMilitiaByLandBox.isSelected());
                controller.setLobbyOption(LobbyConstants.RAISE_MILITIA_BY_LAND, raiseMilitiaByLandBox.isSelected());
            } else if ( e.getComponent().equals(harshCampaignLabel) ) {
                harshCampaignBox.setSelected(!harshCampaignBox.isSelected());
                controller.setLobbyOption(LobbyConstants.HARSH_CAMPAIGNS, harshCampaignBox.isSelected());
            } else if ( e.getComponent().equals(blindSetupLabel) ) {
                blindSetupBox.setSelected(!blindSetupBox.isSelected());
                controller.setLobbyOption(LobbyConstants.BLIND_SETUP, blindSetupBox.isSelected());
            }
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    private int modeIndexToInstance(int index) {
        switch(index) {
            case EMPIRE_INDEX:
                return LobbyConstants.EMPIRE;
            case TEAM_INDEX:
                //TODO Add Team Game Mode
                return LobbyConstants.EMPIRE;
            default:
                Logger.log("GameOptions:modeIndexToInstance Invalid index - " + index);
                return -1;
        }
    }

    private int sceneIndexToInstance(int index) {
        switch(index) {
            case I_INDEX:
                return LobbyConstants.I;
            case II_INDEX:
                return LobbyConstants.II;
            case III_INDEX:
                return LobbyConstants.III;
            case IV_INDEX:
                return LobbyConstants.IV;
            case V_INDEX:
                return LobbyConstants.V;
            case V_V_INDEX:
                return LobbyConstants.V_V;
            case VI_INDEX:
                return LobbyConstants.VI;
            case VII_INDEX:
                return LobbyConstants.VII;
            case VIII_INDEX:
                return LobbyConstants.VIII;
            case IX_INDEX:
                return LobbyConstants.IX;
            case X_INDEX:
                return LobbyConstants.X;
            default:
                Logger.log("GameOptions:sceneIndexToInstance Invalid index - " + index);
                return -1;
        }
    }

    private int durationIndexToInstance(int index) {
        switch (index) {
            case ROUNDS_6_INDEX:
                return LobbyConstants.ROUNDS_6;
            case ROUNDS_12_INDEX:
                return LobbyConstants.ROUNDS_12;
            case ROUNDS_18_INDEX:
                return LobbyConstants.ROUNDS_18;
            case ROUNDS_24_INDEX:
                return LobbyConstants.ROUNDS_24;
            case ROUNDS_36_INDEX:
                return LobbyConstants.ROUNDS_36;
            case ROUNDS_48_INDEX:
                return LobbyConstants.ROUNDS_48;
            default:
                Logger.log("GameOptions:durationIndexToInstance Inavlid index - " + index);
                return -1;
        }
    }

    public void disableAllOptions() {
        for (Component c: getComponents())
            c.setEnabled(false);
    }

    public void setHost(boolean isHost) {
        this.isHost = isHost;
        for (Component c: getComponents()) {
            c.setEnabled(this.isHost);
        }
        setGameMode(modeIndexToInstance(gameModeBox.getSelectedIndex()));
        setScenario(sceneIndexToInstance(gameSceneBox.getSelectedIndex()));
        setDuration(durationIndexToInstance(gameDurationBox.getSelectedIndex()));
    }

    public void setGameOption(int option, boolean isOn) {
        fromServer = true;
        switch(option) {
            case LobbyConstants.QUICK_BATTLES         : quickBattleBox.setSelected(isOn); break;
            case LobbyConstants.HEROES                : heroesBox.setSelected(isOn); break;
            case LobbyConstants.FORCE_MARCH           : forceMarchBox.setSelected(isOn); break;
            case LobbyConstants.SKIRMISH              : skirmishBox.setSelected(isOn); break;
            case LobbyConstants.COUNTER_CHARGE        : counterChargeBox.setSelected(isOn); break;
            case LobbyConstants.ELITE                 : eliteBox.setSelected(isOn); break;
            case LobbyConstants.GENERALS              : generalsBox.setSelected(isOn); break;
            case LobbyConstants.ADMIRALS              : admiralsBox.setSelected(isOn); break;
            case LobbyConstants.RIGHT_OF_PASSAGE      : rightOfPassageBox.setSelected(isOn); break;
            case LobbyConstants.UPRISING              : uprisingBox.setSelected(isOn); break;
            case LobbyConstants.VOLUNTARY_PAP         : voluntaryPAPBox.setSelected(isOn); break;
            case LobbyConstants.CONTROL_NPN           : controlNPNBox.setSelected(isOn); break;
            case LobbyConstants.ANNEX                 : annexBox.setSelected(isOn); break;
            case LobbyConstants.RECRUIT               : recruitBox.setSelected(isOn); break;
            case LobbyConstants.RESTORE               : restoreBox.setSelected(isOn); break;
            case LobbyConstants.CONTINENTAL_SYSTEM    : continentalSystemBox.setSelected(isOn); break;
            case LobbyConstants.RAISE_MILITIA_BY_SEA  : raiseMilitiaBySeaBox.setSelected(isOn); break;
            case LobbyConstants.RAISE_MILITIA_BY_LAND : raiseMilitiaByLandBox.setSelected(isOn); break;
            case LobbyConstants.HARSH_CAMPAIGNS       : harshCampaignBox.setSelected(isOn); break;
            case LobbyConstants.BLIND_SETUP           : blindSetupBox.setSelected(isOn); break;

            case LobbyConstants.EMPIRE                : gameModeBox.setSelectedIndex(EMPIRE_INDEX); break;
            case LobbyConstants.TEAM                  : gameModeBox.setSelectedIndex(TEAM_INDEX); break;

            case LobbyConstants.I                     : gameSceneBox.setSelectedIndex(I_INDEX); break;
            case LobbyConstants.II                    : gameSceneBox.setSelectedIndex(II_INDEX); break;
            case LobbyConstants.III                   : gameSceneBox.setSelectedIndex(III_INDEX); break;
            case LobbyConstants.IV                    : gameSceneBox.setSelectedIndex(IV_INDEX); break;
            case LobbyConstants.V                     : gameSceneBox.setSelectedIndex(V_INDEX); break;
            case LobbyConstants.V_V                   : gameSceneBox.setSelectedIndex(V_V_INDEX); break;
            case LobbyConstants.VI                    : gameSceneBox.setSelectedIndex(VI_INDEX); break;
            case LobbyConstants.VII                   : gameSceneBox.setSelectedIndex(VII_INDEX); break;
            case LobbyConstants.VIII                  : gameSceneBox.setSelectedIndex(VIII_INDEX); break;
            case LobbyConstants.IX                    : gameSceneBox.setSelectedIndex(IX_INDEX); break;
            case LobbyConstants.X                     : gameSceneBox.setSelectedIndex(X_INDEX); break;

            case LobbyConstants.ROUNDS_6              : gameDurationBox.setSelectedIndex(ROUNDS_6_INDEX); break;
            case LobbyConstants.ROUNDS_12             : gameDurationBox.setSelectedIndex(ROUNDS_12_INDEX); break;
            case LobbyConstants.ROUNDS_18             : gameDurationBox.setSelectedIndex(ROUNDS_18_INDEX); break;
            case LobbyConstants.ROUNDS_24             : gameDurationBox.setSelectedIndex(ROUNDS_24_INDEX); break;
            case LobbyConstants.ROUNDS_36             : gameDurationBox.setSelectedIndex(ROUNDS_36_INDEX); break;
            case LobbyConstants.ROUNDS_48             : gameDurationBox.setSelectedIndex(ROUNDS_48_INDEX); break;

        }
        fromServer = false;
    }

    //JLabels
    private JLabel gameModeLabel;
    private JLabel gameSceneLabel;
    private JLabel gameDurationLabel;

    private JLabel quickBattleLabel;
    private JLabel heroesLabel;
    private JLabel forceMarchLabel;
    private JLabel skirmishLabel;
    private JLabel counterChargeLabel;
    private JLabel eliteLabel;
    private JLabel generalsLabel;
    private JLabel admiralsLabel;
    private JLabel rightOfPassageLabel;
    private JLabel uprisingLabel;
    private JLabel voluntaryPAPLabel;
    private JLabel controlNPNLabel;
    private JLabel annexLabel;
    private JLabel recruitLabel;
    private JLabel restoreLabel;
    private JLabel continentalSystemLabel;
    private JLabel raiseMilitiaBySeaLabel;
    private JLabel raiseMilitiaByLandLabel;
    private JLabel harshCampaignLabel;
    private JLabel blindSetupLabel;

    //JBoxes
    private JComboBox gameModeBox;
    private JComboBox gameSceneBox;
    private JComboBox gameDurationBox;

    private JCheckBox quickBattleBox;
    private JCheckBox heroesBox;
    private JCheckBox forceMarchBox;
    private JCheckBox skirmishBox;
    private JCheckBox counterChargeBox;
    private JCheckBox eliteBox;
    private JCheckBox generalsBox;
    private JCheckBox admiralsBox;
    private JCheckBox rightOfPassageBox;
    private JCheckBox uprisingBox;
    private JCheckBox voluntaryPAPBox;
    private JCheckBox controlNPNBox;
    private JCheckBox annexBox;
    private JCheckBox recruitBox;
    private JCheckBox restoreBox;
    private JCheckBox continentalSystemBox;
    private JCheckBox raiseMilitiaBySeaBox;
    private JCheckBox raiseMilitiaByLandBox;
    private JCheckBox harshCampaignBox;
    private JCheckBox blindSetupBox;

    private final int EMPIRE_INDEX = 0;
    private final int TEAM_INDEX = 1;

    private final int I_INDEX = 10;
    private final int II_INDEX = 1;
    private final int III_INDEX = 2;
    private final int IV_INDEX = 3;
    private final int V_INDEX = 4;
    private final int V_V_INDEX = 5;
    private final int VI_INDEX = 6;
    private final int VII_INDEX = 7;
    private final int VIII_INDEX = 8;
    private final int IX_INDEX = 9;
    //TODO
    private final int X_INDEX = 0;

    private final int ROUNDS_6_INDEX = 0;
    private final int ROUNDS_12_INDEX = 1;
    private final int ROUNDS_18_INDEX = 2;
    private final int ROUNDS_24_INDEX = 3;
    private final int ROUNDS_36_INDEX = 4;
    private final int ROUNDS_48_INDEX = 5;
}
