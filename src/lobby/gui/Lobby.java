package lobby.gui;

import util.Messages;
import lobby.controller.NapoleonController;
import shared.controller.LobbyConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Lobby.java  Date Created: Mar 26, 2012
 *
 * Purpose: Display the lobby menu.
 *
 * Description:
 *
 * @author Chrisb
 */
public class Lobby extends JPanel implements ActionListener {

    private NapoleonController controller;
    private int userPosition;
    private boolean host;
    private boolean starting;

    public Lobby(NapoleonController controller) {
        super(new GridBagLayout());

        gameOptions = new GameOptions(controller);
        loadGame = new LoadGame(controller);
        starting = false;

        this.controller = controller;

        //Create components
        createComponents();

        //Layout components
        layoutComponents();

        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    private void layoutComponents() {
        GridBagConstraints c = new GridBagConstraints();

        //Add players
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,10,0,0);
        c.gridx = 0;
        c.gridy = 0;        
        add(player1Name,c);
        c.gridy = 1;
        add(player2Name,c);
        c.gridy = 2;
        add(player3Name,c);
        c.gridy = 3;
        add(player4Name,c);
        c.gridy = 4;
        add(player5Name,c);
        c.gridy = 5;
        add(player6Name,c);
        c.gridy = 6;
        add(player7Name,c);

        //Add countryLists
        c.gridx = 1;
        c.gridy = 0;
        add(player1Nation,c);
        c.gridy = 1;
        add(player2Nation,c);
        c.gridy = 2;
        add(player3Nation,c);
        c.gridy = 3;
        add(player4Nation,c);
        c.gridy = 4;
        add(player5Nation,c);
        c.gridy = 5;
        add(player6Nation,c);
        c.gridy = 6;
        add(player7Nation,c);

        //Add leaderLabels
        c.gridx = 2;
        c.gridy = 0;
        add(leader1Label,c);
        c.gridy = 1;
        add(leader2Label,c);
        c.gridy = 2;
        add(leader3Label,c);
        c.gridy = 3;
        add(leader4Label,c);
        c.gridy = 4;
        add(leader5Label,c);
        c.gridy = 5;
        add(leader6Label,c);
        c.gridy = 6;
        add(leader7Label,c);

        //Add leaderTextField
        c.gridx = 3;
        c.gridy = 0;
        add(leader1NameField,c);
        c.gridy = 1;
        add(leader2NameField,c);
        c.gridy = 2;
        add(leader3NameField,c);
        c.gridy = 3;
        add(leader4NameField,c);
        c.gridy = 4;
        add(leader5NameField,c);
        c.gridy = 5;
        add(leader6NameField,c);
        c.gridy = 6;
        add(leader7NameField,c);

        //Add Edit buttons
        c.gridx = 4;
        c.gridy = 0;
        add(editPlayer1Desc, c);
        c.gridy = 1;
        add(editPlayer2Desc, c);
        c.gridy = 2;
        add(editPlayer3Desc, c);
        c.gridy = 3;
        add(editPlayer4Desc, c);
        c.gridy = 4;
        add(editPlayer5Desc, c);
        c.gridy = 5;
        add(editPlayer6Desc, c);
        c.gridy = 6;
        add(editPlayer7Desc, c);

        //Add Make Ready checkboxes
        c.gridx = 5;
        c.gridy = 0;
        add(player1ReadyBox, c);
        c.gridy = 1;
        add(player2ReadyBox, c);
        c.gridy = 2;
        add(player3ReadyBox, c);
        c.gridy = 3;
        add(player4ReadyBox, c);
        c.gridy = 4;
        add(player5ReadyBox, c);
        c.gridy = 5;
        add(player6ReadyBox, c);
        c.gridy = 6;
        add(player7ReadyBox, c);

        //Add Make Ready buttons
        c.gridx = 6;
        c.gridy = 0;
        add(p1MakeReadyButton, c);
        c.gridy = 1;
        add(p2MakeReadyButton, c);
        c.gridy = 2;
        add(p3MakeReadyButton, c);
        c.gridy = 3;
        add(p4MakeReadyButton, c);
        c.gridy = 4;
        add(p5MakeReadyButton, c);
        c.gridy = 5;
        add(p6MakeReadyButton, c);
        c.gridy = 6;
        add(p7MakeReadyButton, c);

        //Add Start buttons
        c.gridx = 7;
        c.gridy = 0;
        add(p1StartButton, c);
        c.gridy = 1;
        add(p2StartButton, c);
        c.gridy = 2;
        add(p3StartButton, c);
        c.gridy = 3;
        add(p4StartButton, c);
        c.gridy = 4;
        add(p5StartButton, c);
        c.gridy = 5;
        add(p6StartButton, c);
        c.gridy = 6;
        add(p7StartButton, c);

        //Chat area
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 14;
        c.ipady = 80;
        add(chatScrollPane, c);
        c.gridy = 8;
        c.gridwidth = 10;
        c.ipady = 0;
        add(chatMessage, c);
        c.gridx = 10;
        c.gridwidth = 4;
        add(chatSend, c);

        c.gridx = 0;
        c.gridwidth = 14;
        c.gridheight = 10;
        c.gridy = 9;
        add(gameOptions, c);

        c.gridx = 0;
        c.gridy = 19;
        c.gridheight = 1;
        add(loadGame, c);
    }

    private void createComponents() {

        player1Name = new JLabel("Player 1");
        player2Name = new JLabel("Player 2");
        player3Name = new JLabel("Player 3");
        player4Name = new JLabel("Player 4");
        player5Name = new JLabel("Player 5");
        player6Name = new JLabel("Player 6");
        player7Name = new JLabel("Player 7");

        String[] countryList = {
            Messages.getString("nation.0"),
            Messages.getString("nation.1"),
            Messages.getString("nation.2"),
            Messages.getString("nation.3"),
            Messages.getString("nation.4"),
            Messages.getString("nation.5"),
            Messages.getString("nation.6"),
            Messages.getString("nation.7")
        };

        player1Nation = new JComboBox(countryList);
        player1Nation.setActionCommand("player1Nation");
        player1Nation.addActionListener(this);

        player2Nation = new JComboBox(countryList);
        player2Nation.setActionCommand("player2Nation");
        player2Nation.addActionListener(this);

        player3Nation = new JComboBox(countryList);
        player3Nation.setActionCommand("player3Nation");
        player3Nation.addActionListener(this);

        player4Nation = new JComboBox(countryList);
        player4Nation.setActionCommand("player4Nation");
        player4Nation.addActionListener(this);

        player5Nation = new JComboBox(countryList);
        player5Nation.setActionCommand("player5Nation");
        player5Nation.addActionListener(this);

        player6Nation = new JComboBox(countryList);
        player6Nation.setActionCommand("player6Nation");
        player6Nation.addActionListener(this);

        player7Nation = new JComboBox(countryList);
        player7Nation.setActionCommand("player7Nation");
        player7Nation.addActionListener(this);

        leader1Label = new JLabel(/*Messages.getString("player1") + "'s*/ "Leader:");
        leader2Label = new JLabel(/*Messages.getString("player2") + "'s */"Leader:");
        leader3Label = new JLabel(/*Messages.getString("player3") + "'s */"Leader:");
        leader4Label = new JLabel(/*Messages.getString("player4") + "'s */"Leader:");
        leader5Label = new JLabel(/*Messages.getString("player5") + "'s */"Leader:");
        leader6Label = new JLabel(/*Messages.getString("player6") + "'s */"Leader:");
        leader7Label = new JLabel(/*Messages.getString("player7") + "'s */"Leader:");

        leader1NameField = new JLabel(Messages.getString("nation.0.leader"));
        leader2NameField = new JLabel(Messages.getString("nation.0.leader"));
        leader3NameField = new JLabel(Messages.getString("nation.0.leader"));
        leader4NameField = new JLabel(Messages.getString("nation.0.leader"));
        leader5NameField = new JLabel(Messages.getString("nation.0.leader"));
        leader6NameField = new JLabel(Messages.getString("nation.0.leader"));
        leader7NameField = new JLabel(Messages.getString("nation.0.leader"));

        editPlayer1Desc = new JButton(Messages.getString("editDesc.button"));
        editPlayer1Desc.setName(Messages.getString("editDesc.name"));
        editPlayer1Desc.setActionCommand(Messages.getString("editPlayer1Desc.action"));
        editPlayer1Desc.addActionListener(this);
        editPlayer1Desc.setEnabled(false);

        editPlayer2Desc = new JButton(Messages.getString("editDesc.button"));
        editPlayer2Desc.setActionCommand(Messages.getString("editPlayer2Desc.action"));
        editPlayer2Desc.addActionListener(this);
        editPlayer2Desc.setEnabled(false);

        editPlayer3Desc = new JButton(Messages.getString("editDesc.button"));
        editPlayer3Desc.setActionCommand(Messages.getString("editPlayer3Desc.action"));
        editPlayer3Desc.addActionListener(this);
        editPlayer3Desc.setEnabled(false);

        editPlayer4Desc = new JButton(Messages.getString("editDesc.button"));
        editPlayer4Desc.setActionCommand(Messages.getString("editPlayer4Desc.action"));
        editPlayer4Desc.addActionListener(this);
        editPlayer4Desc.setEnabled(false);

        editPlayer5Desc = new JButton(Messages.getString("editDesc.button"));
        editPlayer5Desc.setActionCommand(Messages.getString("editPlayer5Desc.action"));
        editPlayer5Desc.addActionListener(this);
        editPlayer5Desc.setEnabled(false);

        editPlayer6Desc = new JButton(Messages.getString("editDesc.button"));
        editPlayer6Desc.setActionCommand(Messages.getString("editPlayer6Desc.action"));
        editPlayer6Desc.addActionListener(this);
        editPlayer6Desc.setEnabled(false);

        editPlayer7Desc = new JButton(Messages.getString("editDesc.button"));
        editPlayer7Desc.setActionCommand(Messages.getString("editPlayer7Desc.action"));
        editPlayer7Desc.addActionListener(this);
        editPlayer7Desc.setEnabled(false);

        player1ReadyBox = new JCheckBox();
        player1ReadyBox.setEnabled(false);
        player1ReadyBox.setVisible(false);

        player2ReadyBox = new JCheckBox();
        player2ReadyBox.setEnabled(false);
        player2ReadyBox.setVisible(false);

        player3ReadyBox = new JCheckBox();
        player3ReadyBox.setEnabled(false);
        player3ReadyBox.setVisible(false);

        player4ReadyBox = new JCheckBox();
        player4ReadyBox.setEnabled(false);
        player4ReadyBox.setVisible(false);

        player5ReadyBox = new JCheckBox();
        player5ReadyBox.setEnabled(false);
        player5ReadyBox.setVisible(false);

        player6ReadyBox = new JCheckBox();
        player6ReadyBox.setEnabled(false);
        player6ReadyBox.setVisible(false);

        player7ReadyBox = new JCheckBox();
        player7ReadyBox.setEnabled(false);
        player7ReadyBox.setVisible(false);

        p1MakeReadyButton = new JButton(Messages.getString("makeReady.button"));
        p1MakeReadyButton.setActionCommand(Messages.getString("makeReady.action"));
        p1MakeReadyButton.addActionListener(this);
        p1MakeReadyButton.setVisible(false);

        p2MakeReadyButton = new JButton(Messages.getString("makeReady.button"));
        p2MakeReadyButton.setActionCommand(Messages.getString("makeReady.action"));
        p2MakeReadyButton.addActionListener(this);
        p2MakeReadyButton.setVisible(false);

        p3MakeReadyButton = new JButton(Messages.getString("makeReady.button"));
        p3MakeReadyButton.setActionCommand(Messages.getString("makeReady.action"));
        p3MakeReadyButton.addActionListener(this);
        p3MakeReadyButton.setVisible(false);

        p4MakeReadyButton = new JButton(Messages.getString("makeReady.button"));
        p4MakeReadyButton.setActionCommand(Messages.getString("makeReady.action"));
        p4MakeReadyButton.addActionListener(this);
        p4MakeReadyButton.setVisible(false);

        p5MakeReadyButton = new JButton(Messages.getString("makeReady.button"));
        p5MakeReadyButton.setActionCommand(Messages.getString("makeReady.action"));
        p5MakeReadyButton.addActionListener(this);
        p5MakeReadyButton.setVisible(false);

        p6MakeReadyButton = new JButton(Messages.getString("makeReady.button"));
        p6MakeReadyButton.setActionCommand(Messages.getString("makeReady.action"));
        p6MakeReadyButton.addActionListener(this);
        p6MakeReadyButton.setVisible(false);

        p7MakeReadyButton = new JButton(Messages.getString("makeReady.button"));
        p7MakeReadyButton.setActionCommand(Messages.getString("makeReady.action"));
        p7MakeReadyButton.addActionListener(this);
        p7MakeReadyButton.setVisible(false);

        p1StartButton = new JButton(Messages.getString("start.button"));
        p1StartButton.setActionCommand(Messages.getString("start.action"));
        p1StartButton.addActionListener(this);
        p1StartButton.setVisible(false);

        p2StartButton = new JButton(Messages.getString("start.button"));
        p2StartButton.setActionCommand(Messages.getString("start.action"));
        p2StartButton.addActionListener(this);
        p2StartButton.setVisible(false);

        p3StartButton = new JButton(Messages.getString("start.button"));
        p3StartButton.setActionCommand(Messages.getString("start.action"));
        p3StartButton.addActionListener(this);
        p3StartButton.setVisible(false);

        p4StartButton = new JButton(Messages.getString("start.button"));
        p4StartButton.setActionCommand(Messages.getString("start.action"));
        p4StartButton.addActionListener(this);
        p4StartButton.setVisible(false);

        p5StartButton = new JButton(Messages.getString("start.button"));
        p5StartButton.setActionCommand(Messages.getString("start.action"));
        p5StartButton.addActionListener(this);
        p5StartButton.setVisible(false);

        p6StartButton = new JButton(Messages.getString("start.button"));
        p6StartButton.setActionCommand(Messages.getString("start.action"));
        p6StartButton.addActionListener(this);
        p6StartButton.setVisible(false);

        p7StartButton = new JButton(Messages.getString("start.button"));
        p7StartButton.setActionCommand(Messages.getString("start.action"));
        p7StartButton.addActionListener(this);
        p7StartButton.setVisible(false);


        chatWindow = new JTextArea(0,50);
        chatWindow.setName(Messages.getString("chatWindow.name"));
        chatWindow.setEditable(false);
        chatWindow.setLineWrap(true);
        chatWindow.setWrapStyleWord(true);

        chatScrollPane = new JScrollPane(chatWindow);
        chatScrollPane.setName(Messages.getString("chatPane.name"));
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        chatScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        chatMessage = new JTextField(40);
        chatMessage.setName(Messages.getString("chatMessage.name"));
        chatMessage.setActionCommand(Messages.getString("chatSend.action"));
        chatMessage.addActionListener(this);

        chatSend = new JButton(Messages.getString("chatSend.button"));
        chatSend.setActionCommand(Messages.getString("chatSend.action"));
        chatSend.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        int i;

        //Drop down actions
        if (cmd.equals("player1Nation")) {
            if (userPosition == PLAYER1) {
                i = player1Nation.getSelectedIndex();
                controller.setNation(i, Messages.getString("nation." + i + ".leader"));
            }
        } else if (cmd.equals("player2Nation")) {
            if (userPosition == PLAYER2) {
                i = player2Nation.getSelectedIndex();
                controller.setNation(i, Messages.getString("nation." + i + ".leader"));
            }
        } else if (cmd.equals("player3Nation")) {
            if (userPosition == PLAYER3) {
                i = player3Nation.getSelectedIndex();
                controller.setNation(i, Messages.getString("nation." + i + ".leader"));
            }
        } else if (cmd.equals("player4Nation")) {
            if (userPosition == PLAYER4) {
                i = player4Nation.getSelectedIndex();
                controller.setNation(i, Messages.getString("nation." + i + ".leader"));
            }
        } else if (cmd.equals("player5Nation")) {
            if (userPosition == PLAYER5) {
                i = player5Nation.getSelectedIndex();
                controller.setNation(i, Messages.getString("nation." + i + ".leader"));
            }
        } else if (cmd.equals("player6Nation")) {
            if (userPosition == PLAYER6) {
                i = player6Nation.getSelectedIndex();
                controller.setNation(i, Messages.getString("nation." + i + ".leader"));
            }
        } else if (cmd.equals("player7Nation")) {
            if (userPosition == PLAYER7) {
                i = player7Nation.getSelectedIndex();
                controller.setNation(i, Messages.getString("nation." + i + ".leader"));
            }
        }

        //Edit button Actions
        if (cmd.equals(Messages.getString("editPlayer1Desc.action")))
            controller.showSubPanel(MenuResources.NATIONDESC, player1Nation.getSelectedIndex());
        else if (cmd.equals(Messages.getString("editPlayer2Desc.action")))
            controller.showSubPanel(MenuResources.NATIONDESC, player2Nation.getSelectedIndex());
        else if (cmd.equals(Messages.getString("editPlayer3Desc.action")))
            controller.showSubPanel(MenuResources.NATIONDESC, player3Nation.getSelectedIndex());
        else if (cmd.equals(Messages.getString("editPlayer4Desc.action")))
            controller.showSubPanel(MenuResources.NATIONDESC, player4Nation.getSelectedIndex());
        else if (cmd.equals(Messages.getString("editPlayer5Desc.action")))
            controller.showSubPanel(MenuResources.NATIONDESC, player5Nation.getSelectedIndex());
        else if (cmd.equals(Messages.getString("editPlayer6Desc.action")))
            controller.showSubPanel(MenuResources.NATIONDESC, player6Nation.getSelectedIndex());
        else if (cmd.equals(Messages.getString("editPlayer7Desc.action")))
            controller.showSubPanel(MenuResources.NATIONDESC, player7Nation.getSelectedIndex());

        //Make Ready Actions
        if (cmd.equals(Messages.getString("makeReady.action"))) {
            switch(userPosition) {
                case 0: controller.makeReady(userPosition, !player1ReadyBox.isSelected()); break;
                case 1: controller.makeReady(userPosition, !player2ReadyBox.isSelected()); break;
                case 2: controller.makeReady(userPosition, !player3ReadyBox.isSelected()); break;
                case 3: controller.makeReady(userPosition, !player4ReadyBox.isSelected()); break;
                case 4: controller.makeReady(userPosition, !player5ReadyBox.isSelected()); break;
                case 5: controller.makeReady(userPosition, !player6ReadyBox.isSelected()); break;
                case 6: controller.makeReady(userPosition, !player7ReadyBox.isSelected()); break;
            }
        }

        //Start Action
        if (cmd.equals(Messages.getString("start.action")))
            if (host)
                if (controller.isLobbyReady())
                    controller.startCountdown();
                else
                    forceStartDialog();

        //Abort Action
        if (cmd.equals(Messages.getString("abort.action")))
            if (host)
                    controller.stopCountdown();
        

        //Chat Send action
        if (cmd.equals(Messages.getString("chatSend.action")) && chatMessage.getText().length() > 0 && chatMessage.getText().length() < 230) {
            controller.sendChat(chatMessage.getText());
            chatMessage.setText("");
        }

    }

    public void forceStartDialog() {
        Object[] options = {Messages.getString("start.yes"), Messages.getString("start.no")};
        int choice = JOptionPane.showOptionDialog(this, Messages.getString("start.question"),
                                                   Messages.getString("start.title"),
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

        if ( choice == JOptionPane.YES_OPTION )
            controller.startCountdown();
    }

    public void makeReady(int position, boolean isReady) {
        switch(position) {
            case 0: player1ReadyBox.setSelected(isReady); break;
            case 1: player2ReadyBox.setSelected(isReady); break;
            case 2: player3ReadyBox.setSelected(isReady); break;
            case 3: player4ReadyBox.setSelected(isReady); break;
            case 4: player5ReadyBox.setSelected(isReady); break;
            case 5: player6ReadyBox.setSelected(isReady); break;
            case 6: player7ReadyBox.setSelected(isReady); break;
        }

        if (userPosition == position) {
            switch(position) {
                case 0: player1Nation.setEnabled(!isReady); if (player1Nation.getSelectedIndex() > 0) editPlayer1Desc.setEnabled(!isReady); break;
                case 1: player2Nation.setEnabled(!isReady); if (player2Nation.getSelectedIndex() > 0) editPlayer2Desc.setEnabled(!isReady); break;
                case 2: player3Nation.setEnabled(!isReady); if (player3Nation.getSelectedIndex() > 0) editPlayer3Desc.setEnabled(!isReady); break;
                case 3: player4Nation.setEnabled(!isReady); if (player4Nation.getSelectedIndex() > 0) editPlayer4Desc.setEnabled(!isReady); break;
                case 4: player5Nation.setEnabled(!isReady); if (player5Nation.getSelectedIndex() > 0) editPlayer5Desc.setEnabled(!isReady); break;
                case 5: player6Nation.setEnabled(!isReady); if (player6Nation.getSelectedIndex() > 0) editPlayer6Desc.setEnabled(!isReady); break;
                case 6: player7Nation.setEnabled(!isReady); if (player7Nation.getSelectedIndex() > 0) editPlayer7Desc.setEnabled(!isReady); break;
            }
        }
    }

    public void chatMessage(String userName, String chatMessage) {
        newLine();
        chatWindow.append(userName + ": " + chatMessage);
        scrollToBottom();
    }

    public void joinMessage(String userName) {
        newLine();
        chatWindow.append(userName + " has joined the lobby.");
        scrollToBottom();
    }

    public void leaveMessage(String userName) {
        newLine();
        chatWindow.append(userName + " has left the lobby.");
        scrollToBottom();
    }

    public void countdownMsg(int seconds) {
        if (seconds > LobbyConstants.COUNTDOWN_DONE && seconds <= LobbyConstants.COUNTDOWN_SECONDS) {
            newLine();
            chatWindow.append(Messages.getString("countdown.msg.first"));
            chatWindow.append(" " + seconds + " ");
            chatWindow.append(Messages.getString("countdown.msg.last"));
            scrollToBottom();
        }
        if (seconds == LobbyConstants.COUNTDOWN_ABORT) {
            starting = false;
            newLine();
            chatWindow.append(Messages.getString("countdown.msg.abort"));
            controller.makeReady(userPosition, false);
            scrollToBottom();
            startButton();
            setHost(host);
            setUserPosition(userPosition);
        }
    }

    public void abortButton() {
        if (host) {
            switch (userPosition) {
                case 0: p1StartButton.setActionCommand(Messages.getString("abort.action"));
                    p1StartButton.setText(Messages.getString("abort.button"));
                    break;
                case 1: p2StartButton.setActionCommand(Messages.getString("abort.action"));
                    p2StartButton.setText(Messages.getString("abort.button"));
                    break;
                case 2: p3StartButton.setActionCommand(Messages.getString("abort.action"));
                    p3StartButton.setText(Messages.getString("abort.button"));
                    break;
                case 3: p4StartButton.setActionCommand(Messages.getString("abort.action"));
                    p4StartButton.setText(Messages.getString("abort.button"));
                    break;
                case 4: p5StartButton.setActionCommand(Messages.getString("abort.action"));
                    p5StartButton.setText(Messages.getString("abort.button"));
                    break;
                case 5: p6StartButton.setActionCommand(Messages.getString("abort.action"));
                    p6StartButton.setText(Messages.getString("abort.button"));
                    break;
                case 6: p7StartButton.setActionCommand(Messages.getString("abort.action"));
                    p7StartButton.setText(Messages.getString("abort.button"));
                    break;
            }
        }
    }

    public void startButton() {
        if (host) {
            switch (userPosition) {
                case 0: p1StartButton.setActionCommand(Messages.getString("start.action"));
                    p1StartButton.setText(Messages.getString("start.button"));
                    break;
                case 1: p2StartButton.setActionCommand(Messages.getString("start.action"));
                    p2StartButton.setText(Messages.getString("start.button"));
                    break;
                case 2: p3StartButton.setActionCommand(Messages.getString("start.action"));
                    p3StartButton.setText(Messages.getString("start.button"));
                    break;
                case 3: p4StartButton.setActionCommand(Messages.getString("start.action"));
                    p4StartButton.setText(Messages.getString("start.button"));
                    break;
                case 4: p5StartButton.setActionCommand(Messages.getString("start.action"));
                    p5StartButton.setText(Messages.getString("start.button"));
                    break;
                case 5: p6StartButton.setActionCommand(Messages.getString("start.action"));
                    p6StartButton.setText(Messages.getString("start.button"));
                    break;
                case 6: p7StartButton.setActionCommand(Messages.getString("start.action"));
                    p7StartButton.setText(Messages.getString("start.button"));
                    break;
            }
        }
    }
    
    public void setHost(boolean isHost) {
        host = isHost;
        if (host) {
            switch(userPosition) {
                case 0: p1StartButton.setVisible(true); break;
                case 1: p2StartButton.setVisible(true); break;
                case 2: p3StartButton.setVisible(true); break;
                case 3: p4StartButton.setVisible(true); break;
                case 4: p5StartButton.setVisible(true); break;
                case 5: p6StartButton.setVisible(true); break;
                case 6: p7StartButton.setVisible(true); break;
            }
        }
        loadGame.enableMenu(isHost);
        gameOptions.setHost(isHost);
    }

    private void scrollToBottom() {
        chatWindow.setCaretPosition(chatWindow.getText().length());
    }

    public void setNation(int position, int nation, String leaderName) {
        System.out.println("userPosition: " + userPosition + " position: " + position + " nation: " + nation);
        switch(position) {
            case 0:
                if (player1Nation.getSelectedIndex() != nation) {
                    //Disable/Enable comboBox because if you don't a bug occurs where it will sometimes select the wrong item.
                    player1Nation.setEnabled(!player1Nation.isEnabled());
                    player1Nation.setSelectedIndex(nation);
                    player1Nation.setEnabled(!player1Nation.isEnabled());
                }
                if ( nation != 0 && userPosition == position && !starting ) editPlayer1Desc.setEnabled(true); else editPlayer1Desc.setEnabled(false);
                leader1NameField.setText(leaderName);
                break;
            case 1:
                if (player2Nation.getSelectedIndex() != nation) {
                    player2Nation.setEnabled(!player2Nation.isEnabled());
                    player2Nation.setSelectedIndex(nation);
                    player2Nation.setEnabled(!player2Nation.isEnabled());
                }
                if ( nation != 0 && userPosition == position && !starting ) editPlayer2Desc.setEnabled(true); else editPlayer2Desc.setEnabled(false);
                leader2NameField.setText(leaderName);
                break;
            case 2:
                if (player3Nation.getSelectedIndex() != nation) {
                    player3Nation.setEnabled(!player3Nation.isEnabled());
                    player3Nation.setSelectedIndex(nation);
                    player3Nation.setEnabled(!player3Nation.isEnabled());
                }
                if ( nation != 0 && userPosition == position && !starting ) editPlayer3Desc.setEnabled(true); else editPlayer3Desc.setEnabled(false);
                leader3NameField.setText(leaderName);
                break;
            case 3:
                if (player4Nation.getSelectedIndex() != nation) {
                    player4Nation.setEnabled(!player4Nation.isEnabled());
                    player4Nation.setSelectedIndex(nation);
                    player4Nation.setEnabled(!player4Nation.isEnabled());
                }
                if ( nation != 0 && userPosition == position && !starting ) editPlayer4Desc.setEnabled(true); else editPlayer4Desc.setEnabled(false);
                leader4NameField.setText(leaderName);
                break;
            case 4:
                if (player5Nation.getSelectedIndex() != nation) {
                    player5Nation.setEnabled(!player5Nation.isEnabled());
                    player5Nation.setSelectedIndex(nation);
                    player5Nation.setEnabled(!player5Nation.isEnabled());
                }
                if ( nation != 0 && userPosition == position && !starting ) editPlayer5Desc.setEnabled(true); else editPlayer5Desc.setEnabled(false);
                leader5NameField.setText(leaderName);
                break;
            case 5:
                if (player6Nation.getSelectedIndex() != nation) {
                    player6Nation.setEnabled(!player6Nation.isEnabled());
                    player6Nation.setSelectedIndex(nation);
                    player6Nation.setEnabled(!player6Nation.isEnabled());
                }
                if ( nation != 0 && userPosition == position && !starting ) editPlayer6Desc.setEnabled(true); else editPlayer6Desc.setEnabled(false);
                leader6NameField.setText(leaderName);
                break;
            case 6:
                if (player7Nation.getSelectedIndex() != nation) {
                    player7Nation.setEnabled(!player7Nation.isEnabled());
                    player7Nation.setSelectedIndex(nation);
                    player7Nation.setEnabled(!player7Nation.isEnabled());
                }
                if ( nation != 0 && userPosition == position && !starting ) editPlayer7Desc.setEnabled(true); else editPlayer7Desc.setEnabled(false);
                leader7NameField.setText(leaderName);
                break;
        }

    }

    public void setLeader(int position, String leaderName) {
        switch(position) {
            case 0: leader1NameField.setText(leaderName); break;
            case 1: leader2NameField.setText(leaderName); break;
            case 2: leader3NameField.setText(leaderName); break;
            case 3: leader4NameField.setText(leaderName); break;
            case 4: leader5NameField.setText(leaderName); break;
            case 5: leader6NameField.setText(leaderName); break;
            case 6: leader7NameField.setText(leaderName); break;
        }
    }

    public void setLabels(String userName, int position, int nation, String leaderName, boolean isPC) {
        switch(position) {
            case 0: player1Name.setText(userName);
                player1Nation.setSelectedIndex(nation);
                leader1NameField.setText(leaderName);
                player1ReadyBox.setVisible(isPC);
                break;
            case 1: player2Name.setText(userName);
                player2Nation.setSelectedIndex(nation);
                leader2NameField.setText(leaderName);
                player2ReadyBox.setVisible(isPC);
                break;
            case 2: player3Name.setText(userName);
                player3Nation.setSelectedIndex(nation);
                leader3NameField.setText(leaderName);
                player3ReadyBox.setVisible(isPC);
                break;
            case 3: player4Name.setText(userName);
                player4Nation.setSelectedIndex(nation);
                leader4NameField.setText(leaderName);
                player4ReadyBox.setVisible(isPC);
                break;
            case 4: player5Name.setText(userName);
                player5Nation.setSelectedIndex(nation);
                leader5NameField.setText(leaderName);
                player5ReadyBox.setVisible(isPC);
                break;
            case 5: player6Name.setText(userName);
                player6Nation.setSelectedIndex(nation);
                leader6NameField.setText(leaderName);
                player6ReadyBox.setVisible(isPC);
                break;
            case 6: player7Name.setText(userName);
                player7Nation.setSelectedIndex(nation);
                leader7NameField.setText(leaderName);
                player7ReadyBox.setVisible(isPC);
                break;
        }
    }

    /**
     * Disables all nation select dropdowns and edit buttons
     * not at 'position'.
     * @param position - position user sits in lobby
     */
    public void setUserPosition(int position) {
        userPosition = position;
        switch(position) {
            case 0:
                player1Nation.setEnabled(true);
                p1MakeReadyButton.setVisible(true);
                p1MakeReadyButton.setEnabled(true);
                player2Nation.setEnabled(false);
                player3Nation.setEnabled(false);
                player4Nation.setEnabled(false);
                player5Nation.setEnabled(false);
                player6Nation.setEnabled(false);
                player7Nation.setEnabled(false);
                break;
            case 1:
                player1Nation.setEnabled(false);
                player2Nation.setEnabled(true);
                p2MakeReadyButton.setVisible(true);
                p2MakeReadyButton.setEnabled(true);
                player3Nation.setEnabled(false);
                player4Nation.setEnabled(false);
                player5Nation.setEnabled(false);
                player6Nation.setEnabled(false);
                player7Nation.setEnabled(false);
                break;
            case 2:
                player1Nation.setEnabled(false);
                player2Nation.setEnabled(false);
                player3Nation.setEnabled(true);
                p3MakeReadyButton.setVisible(true);
                p3MakeReadyButton.setEnabled(true);
                player4Nation.setEnabled(false);
                player5Nation.setEnabled(false);
                player6Nation.setEnabled(false);
                player7Nation.setEnabled(false);
                break;
            case 3:
                player1Nation.setEnabled(false);
                player2Nation.setEnabled(false);
                player3Nation.setEnabled(false);
                player4Nation.setEnabled(true);
                p4MakeReadyButton.setVisible(true);
                p4MakeReadyButton.setEnabled(true);
                player5Nation.setEnabled(false);
                player6Nation.setEnabled(false);
                player7Nation.setEnabled(false);
                break;
            case 4:
                player1Nation.setEnabled(false);
                player2Nation.setEnabled(false);
                player3Nation.setEnabled(false);
                player4Nation.setEnabled(false);
                player5Nation.setEnabled(true);
                p5MakeReadyButton.setVisible(true);
                p5MakeReadyButton.setEnabled(true);
                player6Nation.setEnabled(false);
                player7Nation.setEnabled(false);
                break;
            case 5:
                player1Nation.setEnabled(false);
                player2Nation.setEnabled(false);
                player3Nation.setEnabled(false);
                player4Nation.setEnabled(false);
                player5Nation.setEnabled(false);
                player6Nation.setEnabled(true);
                p6MakeReadyButton.setVisible(true);
                p6MakeReadyButton.setEnabled(true);
                player7Nation.setEnabled(false);
                break;
            case 6:
                player1Nation.setEnabled(false);
                player2Nation.setEnabled(false);
                player3Nation.setEnabled(false);
                player4Nation.setEnabled(false);
                player5Nation.setEnabled(false);
                player6Nation.setEnabled(false);
                player7Nation.setEnabled(true);
                p7MakeReadyButton.setVisible(true);
                p7MakeReadyButton.setEnabled(true);
                break;
        }
    }

    public void toggleEnabled() {
        switch(userPosition) {
            case 0: player1Nation.setEnabled(!player1Nation.isEnabled()); break;
            case 1: player2Nation.setEnabled(!player2Nation.isEnabled()); break;
            case 2: player3Nation.setEnabled(!player3Nation.isEnabled()); break;
            case 3: player4Nation.setEnabled(!player4Nation.isEnabled()); break;
            case 4: player5Nation.setEnabled(!player5Nation.isEnabled()); break;
            case 5: player6Nation.setEnabled(!player6Nation.isEnabled()); break;
            case 6: player7Nation.setEnabled(!player7Nation.isEnabled()); break;
        }
    }

    public void disableMenus() {
        starting = true;
        abortButton();
        if (host) {
            gameOptions.disableAllOptions();
            loadGame.disableMenu();
        }

        switch (userPosition) {
            case 0: player1Nation.setEnabled(false); editPlayer1Desc.setEnabled(false); p1MakeReadyButton.setEnabled(false); break;
            case 1: player2Nation.setEnabled(false); editPlayer2Desc.setEnabled(false); p2MakeReadyButton.setEnabled(false); break;
            case 2: player3Nation.setEnabled(false); editPlayer3Desc.setEnabled(false); p3MakeReadyButton.setEnabled(false); break;
            case 3: player4Nation.setEnabled(false); editPlayer4Desc.setEnabled(false); p4MakeReadyButton.setEnabled(false); break;
            case 4: player5Nation.setEnabled(false); editPlayer5Desc.setEnabled(false); p5MakeReadyButton.setEnabled(false); break;
            case 5: player6Nation.setEnabled(false); editPlayer6Desc.setEnabled(false); p6MakeReadyButton.setEnabled(false); break;
            case 6: player7Nation.setEnabled(false); editPlayer7Desc.setEnabled(false); p7MakeReadyButton.setEnabled(false); break;
        }
    }

    public void setGameOption(int option, boolean isOn) {
        gameOptions.setGameOption(option, isOn);
    }

    public void initialize() {
        gameOptions.setDefaultOptions();
    }

    public void newLine() {
        if (chatWindow.getText().length() > 0)
                chatWindow.append("\n");
    }

    private JLabel player1Name;
    private JLabel player2Name;
    private JLabel player3Name;
    private JLabel player4Name;
    private JLabel player5Name;
    private JLabel player6Name;
    private JLabel player7Name;

    private JComboBox player1Nation;
    private JComboBox player2Nation;
    private JComboBox player3Nation;
    private JComboBox player4Nation;
    private JComboBox player5Nation;
    private JComboBox player6Nation;
    private JComboBox player7Nation;

    private JLabel leader1Label;
    private JLabel leader2Label;
    private JLabel leader3Label;
    private JLabel leader4Label;
    private JLabel leader5Label;
    private JLabel leader6Label;
    private JLabel leader7Label;

    private JLabel leader1NameField;
    private JLabel leader2NameField;
    private JLabel leader3NameField;
    private JLabel leader4NameField;
    private JLabel leader5NameField;
    private JLabel leader6NameField;
    private JLabel leader7NameField;

    private JButton editPlayer1Desc;
    private JButton editPlayer2Desc;
    private JButton editPlayer3Desc;
    private JButton editPlayer4Desc;
    private JButton editPlayer5Desc;
    private JButton editPlayer6Desc;
    private JButton editPlayer7Desc;

    private JCheckBox player1ReadyBox;
    private JCheckBox player2ReadyBox;
    private JCheckBox player3ReadyBox;
    private JCheckBox player4ReadyBox;
    private JCheckBox player5ReadyBox;
    private JCheckBox player6ReadyBox;
    private JCheckBox player7ReadyBox;

    private JButton p1MakeReadyButton;
    private JButton p2MakeReadyButton;
    private JButton p3MakeReadyButton;
    private JButton p4MakeReadyButton;
    private JButton p5MakeReadyButton;
    private JButton p6MakeReadyButton;
    private JButton p7MakeReadyButton;

    private JButton p1StartButton;
    private JButton p2StartButton;
    private JButton p3StartButton;
    private JButton p4StartButton;
    private JButton p5StartButton;
    private JButton p6StartButton;
    private JButton p7StartButton;

    private JScrollPane chatScrollPane;
    private JTextArea chatWindow;
    private JTextField chatMessage;
    private JButton chatSend;

    private GameOptions gameOptions;
    private LoadGame loadGame;

    private static int PLAYER1 = 0;
    private static int PLAYER2 = 1;
    private static int PLAYER3 = 2;
    private static int PLAYER4 = 3;
    private static int PLAYER5 = 4;
    private static int PLAYER6 = 5;
    private static int PLAYER7 = 6;

}
