package lobby.gui;

import util.Messages;
import lobby.controller.NapoleonController;
import _Test.JTextFieldLimit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

/**
 * NationDescription.java  Date Created: Mar 23, 2012
 *
 * Purpose: Displays the description of the selected nation.
 *
 * Description: The user may edit the description of the nation
 * from this menu.  It is accessed from the lobby menu and when
 * the user is done, user is returned to the lobby menu.
 *
 * @author Chrisb
 */
public class NationDescription extends JPanel implements MouseListener, KeyListener {

    private JTextField leaderName;
    private JButton save;
    private NapoleonController controller;
    private int country;

    public NationDescription(NapoleonController controller) {
        super(new GridBagLayout());

        this.controller = controller;
//        country = 0;

        setName(Messages.getString("NationDesc.name"));
        addMouseListener(this);
        addKeyListener(this);

        //Create components
        createComponents();

        //Layout components
        layoutComponents();

        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    private void createComponents() {
        leaderName = new JTextField(Messages.getString("nation.1.leader"));
        leaderName.setName(Messages.getString("desc.name"));
        leaderName.setColumns(10);
        leaderName.setDocument(new JTextFieldLimit(15));
        leaderName.setEnabled(false);
        leaderName.addMouseListener(this);
        leaderName.addKeyListener(this);

        save = new JButton(Messages.getString("save.button"));
        save.setName(Messages.getString("save.name"));
        save.addMouseListener(this);
        save.addKeyListener(this);
    }

    private void layoutComponents() {
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.CENTER;
        c.insets = new Insets(10,10,0,0);
        c.gridx = 0;
        c.gridy = 0;
        add(leaderName, c);

        c.gridy = 1;
        add(save, c);
    }

    private String getLeaderName() { return leaderName.getText(); }

    public void showMe(int country) {
        this.country = country;
        String prefix = "nation." + country + ".";
        leaderName.setText(Messages.getString(prefix + "leader"));
        setVisible(true);
    }

    public void hideMe() {
        String prefix = "nation." + country + ".";
        Messages.setUserProperty(prefix + "leader", leaderName.getText());
        setVisible(false);
    }

    private void setEnableDesc(boolean isEnabled) {
        leaderName.setEnabled(isEnabled);
        revalidate();
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("NationDesc:MouseClicked");
        //If clicked on save button, close the edit description window
        if (e.getComponent().getName().equals(Messages.getString("save.name"))) {
            controller.sendNationDescription(getLeaderName());
            controller.hideSubPanel(MenuResources.NATIONDESC);
        }
        //If clicked on description field, enabled for editing.
        else if (e.getComponent().getName().equals(Messages.getString("desc.name")))
            setEnableDesc(true);
        else {
            //If clicked somewhere else and description box is enabled, disable it.
            if (leaderName.isEnabled())
                setEnableDesc(false);
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

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        //If ENTER was released on the description field, disable it again.
        if (e.getComponent().getName().equals(Messages.getString("desc.name")))
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                setEnableDesc(false);
        //If SPACE or ENTER was released on the save button, close the description window.
        else if (e.getComponent().getName().equals(Messages.getString("save.name")))
            if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
                controller.sendNationDescription(getLeaderName());                                                                       
                controller.hideSubPanel(MenuResources.NATIONDESC);
            }
    }
}
