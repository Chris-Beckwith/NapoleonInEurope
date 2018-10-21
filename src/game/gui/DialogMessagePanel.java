package game.gui;

import game.util.MessageDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * DialogMessagePanel.java  Date Created: Jan 7, 2014
 *
 * Purpose: To setup a panel to add to JOptionPane Dialogs
 *
 * Description: This class will create a series of panels based on the number of strings
 * passed to the contructor.  It will LEFT/RIGHT/CENTER align the text in the panels according
 * to a contructor argument.
 *
 * @author Chrisb
 */
public class DialogMessagePanel extends JPanel {

    //Default Constructor - CENTER ALIGN
    public DialogMessagePanel(String[] args) {
        this(args, MessageDialog.CENTER_ALIGN);
    }

    public DialogMessagePanel(String[] args, int alignment) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new EmptyBorder(5,5,5,5));
        setOpaque(false);

        for (String line : args) {
            JLabel lineLabel = new JLabel(line);
            JPanel linePanel = new JPanel();
            linePanel.setLayout(new BoxLayout(linePanel, BoxLayout.LINE_AXIS));
            linePanel.setOpaque(false);

            if (alignment == MessageDialog.CENTER_ALIGN || alignment == MessageDialog.RIGHT_ALIGN)
                linePanel.add(Box.createHorizontalGlue());

            linePanel.add(lineLabel);

            if (alignment == MessageDialog.CENTER_ALIGN || alignment == MessageDialog.LEFT_ALIGN)
                linePanel.add(Box.createHorizontalGlue());

            add(linePanel);
        }
    }

}