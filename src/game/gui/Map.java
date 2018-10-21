package game.gui;

import game.controller.DisplayController;
import game.controller.GameController;
import game.util.GameLogger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Map.java  Date Created: Oct 24, 2012
 *
 * Purpose: To display the map and all relavent information related to the map.
 *
 * Description: This panel shall display the following - The map, army markers,
 * fleet markers, territory markers, ports, capitals, ..
 *
 * @author Chrisb
 */
public class Map extends JLayeredPane implements MouseWheelListener, MouseListener, MouseMotionListener {

    public Map(GameController controller, DisplayController display) {
        this.controller = controller;
        this.display = display;

        createComponents();
        layoutComponents();
    }

    private void createComponents() {
        eventOverlay = new EventOverlay();

        try {
            img = ImageIO.read(new File("rsrc/NIE_Board_1600_1200.jpg"));
            imgWidth = img.getWidth();
            imgHeight = img.getHeight();
            shiftX = 0;
            shiftY = 0;
            cornerX1 = 0;
            cornerY1 = 0;
            cornerX2 = imgWidth;
            cornerY2 = imgHeight;
            zoomStates = new int[3][4];
            //Europe Level
            zoomStates[0][0] = cornerX1;
            zoomStates[0][1] = cornerY1;
            zoomStates[0][2] = cornerX2;
            zoomStates[0][3] = cornerY2;
            //Nation Level
            zoomStates[1][0] = (cornerX2)/5;
            zoomStates[1][1] = (cornerY2)/5;
            zoomStates[1][2] = (4 * cornerX2)/5;
            zoomStates[1][3] = (4 * cornerY2)/5;
            //Region Level
            zoomStates[2][0] = (3 * cornerX2)/10;
            zoomStates[2][1] = (3 * cornerY2)/10;
            zoomStates[2][2] = (7 * cornerX2)/10;
            zoomStates[2][3] = (7 * cornerY2)/10;
        } catch (IOException e) {
            GameLogger.log(e.getMessage());
            e.printStackTrace();
        }

        int x = 1600;
        int y = 1200;

        x = (14 * x)/20;
        y = (14 * y)/20;

        setPreferredSize(new Dimension(x,y));
        setMinimumSize(new Dimension(x,y));
        setMaximumSize(new Dimension(x,y));
        addMouseListener(this);
        addMouseWheelListener(this);
        addMouseMotionListener(this);
    }

    private void layoutComponents() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JPanel eventPanel = new JPanel();
        eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.LINE_AXIS));
        eventPanel.setOpaque(false);

        eventPanel.add(Box.createHorizontalGlue());
        eventPanel.add(eventOverlay);
        eventPanel.add(Box.createHorizontalGlue());

        add(Box.createVerticalGlue());
        add(eventPanel);
        add(Box.createVerticalStrut(20));
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();

        if (notches < 0) {
            switch (zoomLevel) {
                case EUROPE_LEVEL: zoomLevel = NATION_LEVEL; break;
                case NATION_LEVEL:
                case REGION_LEVEL: zoomLevel = REGION_LEVEL; break;
            }
        } else {
            switch (zoomLevel) {
                case REGION_LEVEL: zoomLevel = NATION_LEVEL; break;
                case NATION_LEVEL:
                case EUROPE_LEVEL: zoomLevel = EUROPE_LEVEL; break;
            }
        }

        cornerX1 = zoomStates[zoomLevel][0] + shiftX;
        cornerY1 = zoomStates[zoomLevel][1] + shiftY;
        cornerX2 = zoomStates[zoomLevel][2] + shiftX;
        cornerY2 = zoomStates[zoomLevel][3] + shiftY;

        while (cornerX1 < 0) {
            cornerX1++;
            cornerX2++;
        }
        while (cornerY1 < 0) {
            cornerY1++;
            cornerY2++;
        }
        while (cornerX2 > imgWidth) {
            cornerX1--;
            cornerX2--;
        }
        while (cornerY2 > imgHeight) {
            cornerY1--;
            cornerY2--;
        }

        display.refresh();
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(),
                cornerX1, cornerY1, cornerX2, cornerY2, this);
    }

    public void mouseDragged(MouseEvent e) {
        nextX = e.getX() > 0 ? e.getX() : 0;
        nextY = e.getY() > 0 ? e.getY() : 0;

        newX = prevX - nextX;
        newY = prevY - nextY;

        prevX = nextX;
        prevY = nextY;

        if (cornerX1 + newX >= 0 && cornerY1 + newY >= 0 && cornerX2 + newX <= imgWidth && cornerY2 + newY <= imgHeight && Math.abs(newX) < 10 && Math.abs(newY) < 10) {
            cornerX1 += newX;
            cornerY1 += newY;
            cornerX2 += newX;
            cornerY2 += newY;
            shiftX += newX;
            shiftY += newY;
        }

        display.refresh();
    }

    public void mouseMoved(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
        System.out.println("CLICKED");
    }

    public void mousePressed(MouseEvent e) {
        prevX = -1;
        prevY = -1;
        System.out.println("PRESSED");
    }

    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    private GameController controller;
    private DisplayController display;
    private BufferedImage img;
    public EventOverlay eventOverlay;

    private int prevX;
    private int prevY;
    private int nextX;
    private int nextY;
    private int newX;
    private int newY;
    private int shiftX;
    private int shiftY;

    private int imgWidth;
    private int imgHeight;
    private int cornerX1;
    private int cornerY1;
    private int cornerX2;
    private int cornerY2;
    private int[][] zoomStates;
    private int zoomLevel;
    private static final int EUROPE_LEVEL = 0;
    private static final int NATION_LEVEL = 1;
    private static final int REGION_LEVEL = 2;
}