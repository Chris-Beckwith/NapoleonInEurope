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
 * MiniMap.java  Date Created: Oct 24, 2012
 *
 * Purpose: To display the miniMap.
 *
 * Description: This panel shall display a small picture of the entire map,
 * have a zoom bar, and an outline of viewspace if zoomed.
 *
 * @author Chrisb
 */
public class MiniMap extends JPanel {

    public MiniMap(GameController controller, DisplayController display) {
        super();
        this.controller = controller;
        this.display = display;

        createComponents();
        layoutComponents();

        setPreferredSize(new Dimension(223,175));
        setMaximumSize(new Dimension(223,175));
        setMinimumSize(new Dimension(223,175));
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 4), BorderFactory.createEmptyBorder(10,10,10,10)));
    }

    private void createComponents() {
        try {
            img = ImageIO.read(new File("rsrc/NIE_Board_mini.jpg"));
        } catch (IOException e) {
            GameLogger.log(e.getMessage());
            e.printStackTrace();
        }
    }

    private void layoutComponents() {

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