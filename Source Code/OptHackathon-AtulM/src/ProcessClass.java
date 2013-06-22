/**
 * File - ProcessClass.java
 * This is the class which is started by the MainClass in a new process. 
 * The output of this class is continually scanned by the MainClass. The response is sent to MainClass in a comma-separated (CSV) format
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * This is the class which is started by the MainClass in a new process. The
 * output of this class is continually scanned by the MainClass. The response is
 * sent to MainClass in a comma-separated (CSV) format
 * 
 * @author Atul
 * 
 */
public class ProcessClass {

	/**
	 * Main method of the ProcessClass
	 * 
	 * @param args
	 *            ProcessNumber coming in as input arguments
	 */
	public static void main(String[] args) {
		Date startTime = new Date();
		// Query Google Places API for Places of type 'food' within a 500m
		// radius of a point in Sydney, Australia, containing the word 'harbour'
		// in their name
		getHTML("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&types=food&name=harbour&sensor=false&key=AIzaSyBeI8hYWm7GyZMdqx3aeCj_zvOzRRD4gGI");
		Date endTime = new Date();
		long timeDiff = endTime.getTime() - startTime.getTime();

		// Send response for MainClass
		System.out.println("OptHackathon," + args[0] + ","
				+ startTime.getTime() + "," + endTime.getTime() + ","
				+ timeDiff);
	}

	/**
	 * Utility function to get response from a URL
	 * 
	 * @param argURL
	 *            URL to read response from
	 * @return Returns the response in String format
	 */
	public static String getHTML(String argURL) {
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String result = "";
		try {
			url = new URL(argURL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
