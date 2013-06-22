/**
 * File - MainClass.java
 * This is the projects parent class. 
 * It consists of the main function and is responsible for executing, monitoring and logging processes.
 * 
 * @author Atul
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This is the projects parent class. It consists of the main function and is
 * responsible for executing, monitoring and logging processes.
 * 
 * @author Atul
 * 
 */
public class MainClass {
	private static int P_NO = 0;
	private static int P_MAX = 100;
	private static int P_LIMIT = 900;
	private static int P_TOTAL = 0;
	private static float AVG_PT = 200;
	private static ArrayList<Integer> avgs = new ArrayList<Integer>();
	private static Runnable runnable;

	public static void main(String[] args) throws IOException {

		// Create a runnable object which initiates worker processes. It also
		// updates few counters such as total number of processes alive,
		// calculation of average time for batch processes,etc.
		runnable = new Runnable() {
			@Override
			public void run() {
				try {

					// These commands will be run by Java's Process builder
					// task.
					String[] command = { "CMD", "/C", "execText.bat " + P_TOTAL };
					P_NO++;
					P_TOTAL++;

					// Start new process
					update();
					ProcessBuilder probuilder = new ProcessBuilder(command);

					// Setting up work directory to be the current (project)
					// directory
					File directory = new File(".");
					probuilder.directory(new File(directory.getCanonicalPath()
							+ File.separator));

					// Starting up the process
					Process process = probuilder.start();

					// Read out directory output
					InputStream is = process.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					String line;

					// Child process will output the results in a
					// comma-separated (CSV) format. Read the format and extract
					// information.
					while ((line = br.readLine()) != null) {
						if (line.contains("OptHackathon,")) {
							String[] data = line.split(",");
							MyLogger.logUtil
									.log("\n\n\n Process "
											+ data[1]
											+ " started at "
											+ data[2]
											+ " and completed at "
											+ data[3]
											+ ".Time for comm. with Google Places API:: "
											+ data[4] + " .Other processes - "
											+ P_NO + "\n\n\n");
							avgs.add(Integer.parseInt(data[4]));
						}
					}

					try {
						// After completing a process, sleep for a small random
						// number. This is necessary to limit the number of
						// processes which overshoot the maximum process limit.
						Thread.sleep((long) (Math.random() * 1000));
						P_NO--;

						// Start new process
						update();
						if (P_NO == 0) {
							float tempSum = 0;
							// Write average to log for process batches
							for (int i = 0; i < avgs.size(); i++) {
								tempSum += avgs.get(i);

								if (i == 0) {
									continue;
									// Because 0 % anything will always be 0
								}
								if (i % AVG_PT == 0) {
									MyLogger.logUtil.log("Average for "
											+ AVG_PT + " process batch - "
											+ tempSum / AVG_PT);
									tempSum = 0;
								}
							}
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		// Start new process
		update();
	}

	/**
	 * This method starts up a new process in a java thread based on counters
	 * such as number of process executing currently, number of maximum process
	 * that can stay alive, number of total process limit.
	 */
	private static synchronized void update() {
		if (P_NO < P_MAX && P_TOTAL < P_LIMIT) {
			Thread t = new Thread(runnable);
			t.start();
		}
	}
}
