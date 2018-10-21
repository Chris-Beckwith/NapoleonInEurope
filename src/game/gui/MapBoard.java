package game.gui;

import game.controller.DisplayController;
import game.controller.GameController;
import game.util.GameLogger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * MapBoard.java  Date Created: 02 10, 2013
 *
 * Purpose:
 *
 * Description:
 *
 * @author Chris
 */
public class MapBoard extends JPanel {

    public MapBoard(GameController controller, DisplayController display) {
        this.controller = controller;
        this.display = display;

        try {
            img = ImageIO.read(new File("rsrc/NIE_Board_1600_1200.jpg"));
            img.getWidth();
            img.getHeight();
        } catch (IOException e) {
            GameLogger.log(e.getMessage());
            e.printStackTrace();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(),
                0, 0, img.getWidth(), img.getHeight(), this);
    }

    private GameController controller;
    private DisplayController display;
    private BufferedImage img;
}
