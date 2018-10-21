package util;

import java.util.Properties;
import java.io.*;

/**
 * Messages.java  Date Created: Mar 21, 2012
 *
 * Purpose: To retrieve 'values' from property files that are associated with a 'key'
 *
 * Description: Each 'value' has a unique 'key' to be used for reference when accessing
 * or storing values.
 *
 * @author Chrisb
 */
public final class Messages {

    //The property file objects
    private static Properties defaults = new Properties();
    private static Properties client = new Properties();
    private static Properties user = new Properties();
    private static Properties error = new Properties();

    private final static String propPath = "src/lobby/resources/";
    private final static String extension = ".prp";
    private final static String defaultFile = propPath + "defaults" + extension;
    private final static String clientFile = propPath + "client" + extension;
    private final static String errorFile = "src/shared/resources/error" + extension;

    private static String userName;
    private static String userFile;

    //Empty constructor
    private Messages() {
	}

    /**
     * Gets the value associated with 'key'.
     * First checks if the value is in the client property file, if not gets from the defaults property file.
     * @param key - The unique identifier to look up.
     * @return value
     */
    public static String getString(String key) {
        if (user.getProperty(key) != null)
            return user.getProperty(key);
        else if (client.getProperty(key) != null)
            return client.getProperty(key);
        else if (error.getProperty(key) != null)
            return error.getProperty(key);
        else
            return defaults.getProperty(key);
    }

    /**
     * Loads the property files
     */
    public static void load() {
         try {
             defaults.load(new FileInputStream(defaultFile));
             client.load(new FileInputStream(clientFile));
             error.load(new FileInputStream(errorFile));
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("Messages:load - " + e.getMessage());
        }
    }

    public static void loadUser(String userName) {
        try {
            Messages.userName = userName.toLowerCase();
            Messages.userFile = propPath + Messages.userName + extension;
            File userFile = new File(Messages.userFile);
            if (!userFile.exists())
                userFile.createNewFile();

            user.load(new FileInputStream(Messages.userFile));            
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("Messages:loadUser - " + e.getMessage());
        }
    }

    /**
     * Used to set the user property file whenever the user changes any default setting.
     * @param key - The unique identifier to save the 'value' under.
     * @param value - property to be saved.
     */
    public static void setUserProperty(String key, String value) {
        try {
            if (!value.equals(defaults.getProperty(key)))
                user.setProperty(key, value);
                user.store(new FileOutputStream(userFile), "User Properties");
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log("Messages:setClientProperty - " + e.getMessage());
        }
    }

    /**
     * Used to set the client property file whenever the client changes any default setting.
     * @param key - The unique identifier to save the 'value' under.
     * @param value - property to be saved.
     */
    public static void setClientProperty(String key, String value) {
        try {
            client.setProperty(key, value);
            client.store(new FileOutputStream(clientFile), "Client Properties");
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log("Messages:setClientProperty - " + e.getMessage());
        }
    }
}
