package lobby.gui;

import lobby.controller.NapoleonController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import util.Messages;

/**
 * JoinLobby.java  Date Created: Apr 19, 2012
 *
 * Purpose: Display a list of the lobbys available to join.
 *
 * Description: This class will display the lobbys available to join.
 * There will be a button to refresh the list.
 *
 * @author Chrisb
 */
public class JoinLobby extends JPanel implements ActionListener, MouseListener {
    private NapoleonController controller;

    public JoinLobby(NapoleonController controller, JComponent parent) {
        super(new GridBagLayout());

        this.controller = controller;
        this.parent = parent;

        //Create components
        createComponents();

        //Layout components
        layoutComponents();

        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    private void createComponents() {
        selectLobby = new JLabel(Messages.getString("jl.selectLobby"));
        errorMsg = new JLabel();
        errorMsg.setForeground(Color.RED);
        errorMsg.setVisible(false);

        refresh = new JButton(Messages.getString("jl.refresh.button"));
        refresh.setActionCommand(Messages.getString("jl.refresh.action"));
        refresh.addActionListener(this);

        join = new JButton(Messages.getString("jl.join.button"));
        join.setActionCommand(Messages.getString("jl.join.action"));
        join.addActionListener(this);

        back = new JButton(Messages.getString("jl.back.button"));
        back.setActionCommand(Messages.getString("jl.back.action"));
        back.addActionListener(this);

        listModel = new DefaultListModel();

        lobbyList = new JList(listModel);
        lobbyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lobbyList.setLayoutOrientation(JList.VERTICAL);
        lobbyList.setName(Messages.getString("jl.lobbyList.name"));
        lobbyList.addMouseListener(this);

        listScroller = new JScrollPane(lobbyList);
        listScroller.setPreferredSize(new Dimension(200,200));
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        add(selectLobby, gbc);

        gbc.gridy = 1;
        add(listScroller, gbc);

        gbc.gridy = 2;
        add(errorMsg, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 4;
        add(back, gbc);

        gbc.gridx = 1;
        add(refresh, gbc);

        gbc.gridx = 2;
        add(join, gbc);
    }

    public void refreshLobbyList(String[] lobbys) {
        System.out.println("refreshLobbyList");

        listModel.removeAllElements();

        for (String s: lobbys)
            listModel.addElement(s);
                
    }

    public void addToLobbyList(String[] lobbys) {
        for (String s: lobbys)
            listModel.addElement(s);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals(Messages.getString("jl.refresh.action")))
            controller.refreshLobbyList();
        else if (cmd.equals(Messages.getString("jl.join.action")) && lobbyList.getSelectedIndex() >= 0) {
            controller.joinLobby(lobbyList.getSelectedIndex());
            hideErrorMsg();
        } else if (cmd.equals(Messages.getString("jl.back.action")))
            controller.showParentMenu(parent);
    }

    public void displayFullLobbyMsg() {
        errorMsg.setText(Messages.getString("jl.fullLobby"));
        errorMsg.setVisible(true);
    }

    public void hideErrorMsg() {
        errorMsg.setText("");
        errorMsg.setVisible(false);
    }

    public void displayStartedLobbyMsg() {
        errorMsg.setText(Messages.getString("jl.startedLobby"));
        errorMsg.setVisible(true);
    }

    public JComponent getParentMenu() {
        return parent;
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2)
            controller.joinLobby(lobbyList.getSelectedIndex());
    }

    public void mousePressed(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseReleased(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private JLabel selectLobby;
    private JLabel errorMsg;
    private JButton refresh;
    private JButton join;
    private JButton back;
    private DefaultListModel listModel;
    private JList lobbyList;
    private JScrollPane listScroller;
    private JComponent parent;
}
