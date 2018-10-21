package lobby.gui;

import util.Messages;
import lobby.controller.NapoleonController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * LoginMenu.java  Date Created: Mar 21, 2012
 *
 * Purpose: Displays the loginButton menu.
 *
 * Description: When a user starts the application,
 * this is the first menu they will see
 *
 * @author Chrisb
 */
public class LoginMenu extends JPanel implements MouseListener, KeyListener {

    private JButton loginButton;
    private JTextField userName;
    private JCheckBox defaultBox;
    private JLabel errorMsg;

    private NapoleonController controller;

    public LoginMenu(NapoleonController controller) {
//        super(new GridBagLayout());
        super();
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Create components
        createComponents();

        //Layout components
        layoutComponents();

        setMinimumSize(new Dimension(275,150));
        setPreferredSize(new Dimension(275,150));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    private void createComponents() {
        errorMsg = new JLabel();
        errorMsg.setHorizontalTextPosition(JLabel.CENTER);
        errorMsg.setHorizontalAlignment(JLabel.CENTER);
        errorMsg.setForeground(Color.RED);
        errorMsg.setMinimumSize(new Dimension(235,20));
        errorMsg.setPreferredSize(new Dimension(235,20));

        userName = new JTextField();
        userName.setName(Messages.getString("userName.name"));
        userName.setText(Messages.getString("userName.defaultUser"));
        userName.setPreferredSize(new Dimension(235,22));
        userName.setMaximumSize(new Dimension(235,22));
        userName.setColumns(15);
        userName.addKeyListener(this);
        userName.addMouseListener(this);

        defaultBox = new JCheckBox(Messages.getString("default.title"));
        defaultBox.setName(Messages.getString("default.name"));
        defaultBox.addKeyListener(this);
        defaultBox.addMouseListener(this);
        defaultBox.setSelected(Messages.getString("userName.defaultUser") != null);

        loginButton = new JButton("Enter");
        loginButton.setName(Messages.getString("login.name"));
        loginButton.addKeyListener(this);
        loginButton.addMouseListener(this);
    }

    private void layoutComponents() {
        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.X_AXIS));
        errorPanel.add(Box.createHorizontalGlue());
        errorPanel.add(errorMsg);
        errorPanel.add(Box.createHorizontalGlue());

        JPanel userNamePanel = new JPanel();
        userNamePanel.setLayout(new BoxLayout(userNamePanel, BoxLayout.X_AXIS));
        userNamePanel.add(Box.createHorizontalGlue());
        userNamePanel.add(userName);
        userNamePanel.add(Box.createHorizontalGlue());

        JPanel defaultPanel = new JPanel();
        defaultPanel.setLayout(new BoxLayout(defaultPanel, BoxLayout.X_AXIS));
        defaultPanel.add(Box.createHorizontalGlue());
        defaultPanel.add(defaultBox);
        defaultPanel.add(Box.createHorizontalGlue());

        JPanel loginButtonPanel = new JPanel();
        loginButtonPanel.setLayout(new BoxLayout(loginButtonPanel, BoxLayout.X_AXIS));
        loginButtonPanel.add(Box.createHorizontalGlue());
        loginButtonPanel.add(loginButton);
        loginButtonPanel.add(Box.createHorizontalGlue());

        add(Box.createVerticalGlue());
        add(errorPanel);
        add(Box.createVerticalStrut(8));
        add(userNamePanel);
        add(Box.createVerticalStrut(4));
        add(defaultPanel);
        add(Box.createVerticalStrut(4));
        add(loginButtonPanel);
        add(Box.createVerticalGlue());

//        GridBagConstraints c = new GridBagConstraints();

//        c.fill = GridBagConstraints.CENTER;
//        c.gridx = 0;
//        c.gridy = 0;
//        add(errorMsg, c);

//        c.gridy = 1;
//        add(userName, c);

//        c.gridy = 2;
//        add(defaultBox, c);

//        c.gridy = 3;
//        add(loginButton, c);
    }

    public void mouseClicked(MouseEvent e) {
        if ( e.getComponent().getName().equals(Messages.getString("login.name")) )
            attemptLogin();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if ( e.getKeyCode() == KeyEvent.VK_ENTER || (e.getKeyCode() == KeyEvent.VK_SPACE && loginButton.hasFocus()) )
            attemptLogin();
        if (userName.hasFocus() && !userName.getText().equals(Messages.getString("userName.defaultUser")))
            defaultBox.setSelected(false);        
    }

    public String getUserName() {
        return userName.getText();
    }

    public boolean isDefaultOn() {
        return defaultBox.isSelected();
    }

    private void attemptLogin() {
        setErrorMsg("");
        if (userName.getText().length() > 0)
            controller.login(getUserName());
        else
            setErrorMsg(Messages.getString("errorMsg.emptyUser"));
    }

    public void setErrorMsg(String msg) {
        errorMsg.setText(msg);
//        controller.refreshDisplay();
    }
}
