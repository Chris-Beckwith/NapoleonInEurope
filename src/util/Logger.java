package util;

import org.joda.time.DateTime;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Logger.java  Date Created: Oct 4, 2012
 *
 * Purpose: To create log files and log messages in them.
 *
 * Description: This class will be used throughout the lobby to log errors in an external file.
 * It may also log warnings and generic messeges.
 *
 * @author Chrisb
 */
public final class Logger {

    private static BufferedWriter logOut;
    private static String fileName = "logs/" + getDateTimeAsString() + ".log";


    //Empty Constructor
    public Logger() {
    }

    /*
     * Creates the initial log file.  Called as soon as the program starts.
     */
    public static void createLogFile() {
        try {
            logOut = new BufferedWriter(new FileWriter(fileName));
            logOut.write("Log File Created..");
            logOut.close();
        } catch (IOException e) {
            System.err.println("Error setting up log file");
            e.printStackTrace();
        }
    }

    /*
     * Append log message to current log file.
     */
    public static void log(String msg) {
        try {
            logOut = new BufferedWriter(new FileWriter(fileName, true));
            logOut.write(System.getProperty("line.separator") + msg);
            logOut.close();
        } catch (IOException e) {
            System.err.println("Error writing to log file");
            e.printStackTrace();
        }
    }

    /*
     * Convert DateTime into accepted fileName format.
     */
    private static String getDateTimeAsString() {
        return DateTime.now().toString().replace(':','.');
    }
}