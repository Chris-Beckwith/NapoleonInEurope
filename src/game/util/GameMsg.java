package game.util;

import util.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * GameMsg.java  Date Created: Oct 29, 2012
 *
 * Purpose: To retrieve 'values' from property files that are associated with a 'key'
 *
 * Description: Each 'value' has a unique 'key' to be used for reference when accessing
 * or storing values.
 *
 * @author Chrisb
 */
public final class GameMsg {

    private static Properties game = new Properties();
    private static Properties error = new Properties();

    private final static String propPath = "src/game/resources/";
    private final static String extension = ".prp";
    private final static String gameFile = propPath + "game" + extension;
    private final static String errorFile = "src/shared/resources/error" + extension;

    //Empty constructor
    private GameMsg() {
    }

    /**
     * Gets the value associated with 'key'.
     * First checks if the value is in the client property file, if not gets from the defaults property file.
     * @param key - The unique identifier to look up.
     * @return value
     */
    public static String getString(String key) {
        if ( error.getProperty(key) != null)
            return error.getProperty(key);
        return game.getProperty(key);
    }

    /**
     * Loads the property files
     */
    public static void load() {
         try {
             game.load(new FileInputStream(gameFile));
             error.load(new FileInputStream(errorFile));
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("ERROR Messages:load - " + e.getMessage());
        }
    }
}