/**
 * File - MyLogger.java
 * This class is responsible for logging output of processes to Processes.log file contained in the current (project) directory.
 */
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class is responsible for logging output of processes to Processes.log
 * file contained in the current (project) directory.
 * 
 * @author Atul
 * 
 */
public class MyLogger {
	// Get Java Logger
	private static Logger logger = Logger.getLogger("Optimus Hackathon Log");
	public static MyLogger logUtil = new MyLogger();

	public MyLogger() {
		FileHandler fh;
		try {
			// This block configure the logger with handler and formatter
			fh = new FileHandler("Processes.log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Synchronized method to log message to the log file
	 * 
	 * @param argMessage
	 *            Message to be logged
	 */
	public synchronized void log(String argMessage) {
		synchronized (logUtil) {
			logger.info(argMessage);
		}
	}
}
