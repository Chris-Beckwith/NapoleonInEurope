package lobby;

import lobby.gui.MenuResources_Eng;
import lobby.gui.MenuResources;

import javax.swing.*;

import util.Messages;
import util.Logger;
import lobby.controller.NapoleonController;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * NapoleonInEurope.java  Date Created: Mar 20, 2012
 *
 * Purpose: Run the client, this is where is all starts.
 *
 * Description: This class creates the GUI and Controller objects
 * that will control what is displayed and the actions that will
 * take place.
 *
 * @author Chrisb
 */
public class NapoleonInEurope {
	private JFrame frame;
    private MenuResources menuResources;
    private NapoleonController controller;

    /**
     * The Main Method
     */
    public static void main(String[] args) throws IOException {
        (new NapoleonInEurope()).start();
	}

    /**
     * Constructor
     */
    public NapoleonInEurope() {
        Logger.createLogFile();
        Messages.load();
        //Create the window with the given title
        frame = new JFrame(Messages.getString("frame.title"));

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        controller = new NapoleonController();
        menuResources = new MenuResources_Eng(frame, controller);
        menuResources.setInitialContentPane();
        controller.setMenuResource(menuResources);
        
    }
	
	public void start() {
		SwingUtilities.invokeLater(new UIStarter());
	}

	private class UIStarter implements Runnable {
		public void run() {
			//Initial window size, in pixels
			frame.pack();

            //Initial window location, in pixels
			frame.setLocation(50,50);

            //Add window listener to do clean up when application is closed.
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    controller.cleanUp();
                    frame.dispose();
                }
            });

            //Sets the window to maximized
//		    frame.setExtendedState(Frame.MAXIMIZED_BOTH);
//			int height = (int)(Toolkit.getDefaultToolkit().getScreenSize().height*.9);
//			int width = (int)(Toolkit.getDefaultToolkit().getScreenSize().width*.9);
//			frame.setSize(825,725);

		    //Prevents the frame from being resized
			frame.setResizable(false);

		    //Display the window
			frame.setVisible(true);
        }
    }
}

