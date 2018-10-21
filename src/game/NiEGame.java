package game;

import game.controller.DisplayController;
import game.controller.GameController;
import game.util.GameLogger;
import game.util.GameMsg;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;

import lobby.controller.User;

/**
 * NiEGame.java  Date Created: Oct 22, 2012
 *
 * Purpose: To run the client GUI for the in game graphics.
 *
 * Description:  This class will create the game controllers and GUIs
 * that are needed to display and run the game. 
 *
 * @author Chrisb
 */
public class NiEGame implements WindowListener {
    private JFrame frame;
    private DisplayController displayController;

    /*
     * The Main Method
     */
    public static void main(String[] args) throws IOException {
        (new NiEGame(new User("testUser", 0, 2, "testLeader"), new byte[]{0,0})).start();
	}

    /**
     * Constructor
     * @param user - User creating the instance of NiEGame.
     * @param lobbyId - the lobbyId 'user' came from.
     */
    public NiEGame(User user, byte[] lobbyId) {
        GameLogger.createLogFile();
        GameMsg.load(); 
        //Create the window with the given title
//        frame = new JFrame(Messages.getString("frame.title"));
        frame = new JFrame("Napoleon In Europe");
//        frame.setUndecorated(true);
        frame.addWindowListener(this);
        //Allows JOptionPane to display dialogs without blocking input to the main frame.
        frame.setModalExclusionType(Dialog.ModalExclusionType.TOOLKIT_EXCLUDE);

        GameController controller = new GameController(user, lobbyId);
        displayController = new DisplayController(frame, controller);
        controller.setDisplayController(displayController);
        if (user.isHost())
            controller.requestGameInfo();

        while (!controller.isGameReady()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        controller.game.setupGame();
        displayController.init();
    }

	public void start() {
		SwingUtilities.invokeLater(new UIStarter());
	}

    public void windowOpened(WindowEvent e) {
        //todo add quicksave here
    }

    public void windowClosing(WindowEvent e) {
        System.out.println("closing");
        //todo put quicksave here
        displayController.quit();
    }

    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}

    private class UIStarter implements Runnable {
		public void run() {
			//Initial window size, in pixels
			frame.pack();

            //Initial window location, in pixels
			frame.setLocation(10,10);

            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            //Add window listener to do clean up when application is closed.
//            frame.addWindowListener(new WindowAdapter() {
//                public void windowClosing(WindowEvent we) {
//                  //controller.cleanUp();
//                    frame.dispose();
//                }
//            });

            //Sets the window to maximized
//		    frame.setExtendedState(Frame.MAXIMIZED_BOTH);
//			int height = (int)(Toolkit.getDefaultToolkit().getScreenSize().height*.9);
//			int width = (int)(Toolkit.getDefaultToolkit().getScreenSize().width*.9);
//			frame.setSize(1920/2,1200/2);

//            frame.setExtendedState(Frame.MAXIMIZED_BOTH);

            //Prevents the frame from being resized
			frame.setResizable(false);

            //Display the window
            frame.setVisible(true);
        }
    }
}