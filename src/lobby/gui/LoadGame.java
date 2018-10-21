package lobby.gui;

import lobby.controller.NapoleonController;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import util.Messages;

/**
 * LoadGame.java  Date Created: Nov 20, 2012
 *
 * Purpose:
 *
 * Description:
 *
 * @author Chrisb
 */
public class LoadGame extends JPanel implements ActionListener {
    private NapoleonController controller;

    public LoadGame(NapoleonController controller) {
        super(new GridBagLayout());

        this.controller = controller;

        //Create components
        createComponents();

        //Layout components
        layoutComponents();

        setBorder(BorderFactory.createEmptyBorder(2,2,20,2));
    }

    public void createComponents() {
        load = new JButton(Messages.getString("load.name"));
        load.setActionCommand(Messages.getString("load.action"));
        load.addActionListener(this);

        unload = new JButton(Messages.getString("unload.name"));
        unload.setActionCommand(Messages.getString("unload.action"));
        unload.addActionListener(this);

        saveFileName = new JTextField(16);
        saveFileName.setEditable(false);        

        fileChooser = new JFileChooser("saves");
        fileChooser.addChoosableFileFilter(new SaveFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setAccessory(new SavePreview(fileChooser));
    }

    public void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 10;
        add(saveFileName, gbc);

        gbc.gridx = 10;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0,10,0,0);
        add(load, gbc);

        gbc.gridx = 12;
        add(unload, gbc);
    }


    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if ( cmd.equals(Messages.getString("load.action")) ) {
            int returnVal = fileChooser.showDialog(this, "Load");

            if (returnVal == JFileChooser.APPROVE_OPTION) {

            }

        } else if ( cmd.equals(Messages.getString("unload.action")) ) {

        }
    }

    private class SaveFilter extends FileFilter {

        public boolean accept(File f) {
            if (f.isDirectory())
                return true;

            String extension = getExtension(f);
            if ( extension != null && extension.equals("save") )
                return true;
            return false;
        }

        public String getDescription() {
            return "Save Files";
        }

        private String getExtension(File f) {
            String ext = null;
            String s = f.getName();
            int i = s.lastIndexOf('.');

            if (i > 0 && i < s.length() - 1)
                ext =  s.substring(i+1).toLowerCase();
            return ext;
        }
    }


    public void disableMenu() {
        load.setEnabled(false);
        unload.setEnabled(false);
    }

    public void enableMenu(boolean isHost) {
        load.setEnabled(isHost);
        unload.setEnabled(isHost);
    }

    private class SavePreview extends JPanel implements PropertyChangeListener {
        File file = null;
        JLabel currentRoundYear;
        JLabel roundYear;
        JLabel currentTurn;
        JLabel Turn;

        public SavePreview(JFileChooser fc) {
            super(new GridBagLayout());
            setPreferredSize(new Dimension(400,100));
            fc.addPropertyChangeListener(this);
            createAndLayout();
        }

        private void createAndLayout() {
            GridBagConstraints gbc = new GridBagConstraints();

            //todo
            //      Current Nation's Turn:
            //            France
            //      Current Round, Year:
            //        September, 1824
            //       Users last in game:
            //         2Blue - Austria
            //       Kekekeke - Prussia
            //       Vgamer1 - France
            // JefeTheCheese - The Ottoman Empire
            //  QQTheDestroyer - Great Britain
            //    TheLoneWolf - Russia
            currentRoundYear = new JLabel(Messages.getString("currentRoundYear"));
            add(currentRoundYear, gbc);

        }

        public void loadSavePreview() {

        }

        public void propertyChange(PropertyChangeEvent evt) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    private JButton load;
    private JButton unload;
    private JTextField saveFileName;

    private JFileChooser fileChooser;
}