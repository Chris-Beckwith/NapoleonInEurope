package game.gui;

import game.util.GameLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * FlashAnimationLabel.java  Date Created: Sep 19, 2013
 *
 * Purpose: Flash a label between to colors.
 *
 * Description:
 *
 * @author Chrisb
 */
public class FlashAnimationLabel extends JLabel implements ActionListener {

    public FlashAnimationLabel() {
        this("");
    }

    public FlashAnimationLabel(String text) {
        this(text, DEFAULT_COUNT, DEFAULT_DELAY);
    }

    public FlashAnimationLabel(String text, int flashCount, int flashDelayMillis) {
        super(text);
        timesToFlash = flashCount;
        flashTimer = new Timer(flashDelayMillis, this);
    }

    public void start() {
        foregroundColor = this.getForeground();
        flashCount = timesToFlash;
        flashTimer.start();
    }

    public void stop() {
        setForeground(foregroundColor);
        flashTimer.stop();
    }

    public void actionPerformed(ActionEvent e) {
        if (flashCount < 0)
            flashTimer.stop();
        if (isFlashRed) {
            setForeground(flashRed);
            isFlashRed = false;
        } else {
            setForeground(darkRed);
            isFlashRed = true;
            flashCount--;
        }
    }

    private boolean isFlashRed = false;
    private Color foregroundColor;
    private int timesToFlash;
    private int flashCount;
    private Timer flashTimer;

    private static int DEFAULT_DELAY = 500;
    private static int DEFAULT_COUNT = 4;

    private final Color flashRed = new Color(178,34,34); // bright red flash
    private final Color darkRed = new Color(139,0,0); // dark red received
}