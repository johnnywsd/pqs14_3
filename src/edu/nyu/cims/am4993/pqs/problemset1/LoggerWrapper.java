package edu.nyu.cims.am4993.pqs.problemset1;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class wraps the Logger object so that only one log file is created for
 * multiple classes.
 * 
 * @author ashishmanral
 * 
 */
public class LoggerWrapper {

  public static final Logger LOGGER = Logger.getLogger("AddressBook");

  static {
    FileHandler myFileHandler = null;
    try {
      myFileHandler = new FileHandler("log_" + System.currentTimeMillis()
              + ".txt");
    } catch (SecurityException | IOException e) {
      e.printStackTrace();
    }
    myFileHandler.setFormatter(new SimpleFormatter());
    LOGGER.addHandler(myFileHandler);
    LOGGER.setUseParentHandlers(false);
    LOGGER.setLevel(Level.INFO);
  }
}