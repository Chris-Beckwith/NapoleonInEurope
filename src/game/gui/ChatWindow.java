package game.gui;

import game.controller.DisplayController;
import game.controller.GameController;
import game.util.GameMsg;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ChatWindow.java  Date Created: Oct 24, 2012
 *
 * Purpose: To display and send chat messages.
 *
 * Description: This panel shall display all messages sent to and from
 * the user related to a specific person or globally.  Which window is
 * displayed is deteremined by this panel's counterpart, chatChooser.
 *
 * ChatWindow will display only the last 100 lines.
 *
 * ChatWindow may not save messages from previous session.
 *
 * @author Chrisb
 */
public class ChatWindow extends JPanel implements ActionListener {

    public ChatWindow(GameController controller, DisplayController dControl) {
        super();
        this.controller = controller;
        this.display = dControl;

        createActions();
        createComponents();
        layoutComponents();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBackground(Color.DARK_GRAY);
        setBorder(BorderFactory.createEmptyBorder(10,25,10,25));
    }

    private void createComponents() {
        messageWindow = new JTextArea(5,50);
        messageWindow.setEditable(false);
        messageWindow.setLineWrap(true);
        messageWindow.setWrapStyleWord(true);

        windowScrollPane = new JScrollPane(messageWindow);
        windowScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        windowScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        windowScrollPane.setMinimumSize(new Dimension(492,150));
//        windowScrollPane.setMaximumSize(new Dimension(1000,150));
        windowScrollPane.setPreferredSize(new Dimension(492,150));

        chatMsg = new JTextField(40);
        chatMsg.setBorder(BorderFactory.createEmptyBorder());
        chatMsg.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), GameMsg.getString("cw.enterAction"));
        chatMsg.getActionMap().put(GameMsg.getString("cw.enterAction"), enterAction);

        send = new JButton(GameMsg.getString("cw.send"));
        send.setActionCommand(GameMsg.getString("cw.send.action"));
        send.addActionListener(this);

        chatPreface = new JLabel(" " + GameMsg.getString("cw.nation.8") + " ");
        chatPreface.setBackground(Color.WHITE);
        chatPreface.setOpaque(true);
    }

    private void layoutComponents() {
        add(windowScrollPane);

        add(Box.createRigidArea(new Dimension(0,2)));

        JPanel chatMsgPanel = new JPanel();
        chatMsgPanel.setLayout(new BoxLayout(chatMsgPanel, BoxLayout.LINE_AXIS));
        chatMsgPanel.setBackground(Color.WHITE);
        chatMsgPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        chatMsgPanel.add(chatPreface);
        chatMsgPanel.add(chatMsg);

        JPanel msgSendPanel = new JPanel();
        msgSendPanel.setLayout(new BoxLayout(msgSendPanel, BoxLayout.LINE_AXIS));
        msgSendPanel.setBackground(Color.DARK_GRAY);
        msgSendPanel.add(chatMsgPanel);
        msgSendPanel.add(Box.createRigidArea(new Dimension(20,0)));
        msgSendPanel.add(send);
        msgSendPanel.setMinimumSize(new Dimension(492,25));
//        msgSendPanel.setMaximumSize(new Dimension(1000,25));
        msgSendPanel.setPreferredSize(new Dimension(492,25));

        add(msgSendPanel);
    }

    private void createActions() {
        enterAction = new AbstractAction(GameMsg.getString("cw.enterAction")) {
            public void actionPerformed(ActionEvent e) {
                sendChatMsg(chatMsg.getText());
            }
        };
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals(GameMsg.getString("cw.send.action")))
            sendChatMsg(chatMsg.getText());
    }

    /**
     * Set the chatPreface, reset the chatMsg, save the chatArea, and finally
     * change the chatWindow to display the previous dialog between these two nations.
     * @param newNation - The nation messages will be sent to.
     */
    public void changeChatNation(int newNation) {
        chatPreface.setText(" " + GameMsg.getString("cw.nation." + newNation) + " ");
        display.saveChatWindow(controller.getChatNation(), messageWindow.getText());
        messageWindow.setText(display.loadChatWindow(newNation));
    }

    public void messageReceived(int nation) {
        if (nation == controller.getChatNation()) {
            messageWindow.setText(display.loadChatWindow(controller.getChatNation()));
            messageWindow.setCaretPosition(messageWindow.getText().length());
            chatMsg.setText("");
            display.refresh();
        }
    }

    public void sendChatMsg(String msg) {
        msg = msg.replaceAll("\\s+$", "");
        if (msg.length() > 0 && msg.length() < GameController.MAX_CHAT_LENGTH)
            controller.sendChatMessage(msg);
    }

    private GameController controller;
    private DisplayController display;

    private JTextArea messageWindow;
    private JScrollPane windowScrollPane;
    private JTextField chatMsg;
    private JButton send;
    private JLabel chatPreface;

    private AbstractAction enterAction;
}