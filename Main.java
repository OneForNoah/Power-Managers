import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;


public class Main {
	private static final DecimalFormat two = new DecimalFormat("00");
	private static final DecimalFormat three = new DecimalFormat("000");
	private static long lastModified = 0;

	private static final String THE_URL = "http://egauge21244.egaug.es/cgi-bin/egauge?v1";
	private static final int INTERVAL = 10;

	private static void pause(int duration) {
		try {
			Thread.sleep(duration*1000);
		} catch (InterruptedException ie) {
			// do nothing
		}
	}

	private static String generateName(long time) {
		Calendar cal = Calendar.getInstance();
		if (time != 0)
			cal.setTime(new Date(time));
		String fileName = cal.get(Calendar.YEAR) +
		"." + three.format(cal.get(Calendar.DAY_OF_YEAR)) +
		"." + two.format(cal.get(Calendar.HOUR_OF_DAY))+
		"." + two.format(cal.get(Calendar.MINUTE)) +
		"." + two.format(cal.get(Calendar.SECOND));
		return fileName;
	}

	/**
	 * Uses POST if data isn't empty or null.
	 */

	public static void grabFile(URL source) throws IOException {
		try {
			URLConnection conn = source.openConnection();
			HttpURLConnection httpCon = (HttpURLConnection) conn;
			httpCon.setIfModifiedSince(lastModified);
			InputStream inStream = httpCon.getInputStream();
			int b = inStream.read();
			if (b != -1) {
				lastModified = httpCon.getLastModified();
				// System.out.println("Mod is now "+lastModified);
				String fileName = generateName(lastModified) + ".xml";
				FileOutputStream outFile = new FileOutputStream(new File(fileName));
				do {
					outFile.write(b);
				} while ((b = inStream.read()) != -1);
				outFile.close();
				System.out.println("Wrote "+fileName);
			}
			inStream.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException {
		System.out.println("Checking "+THE_URL+" every "+INTERVAL+" seconds.");

		URL source = new URL(THE_URL);
		for(;;) {
			grabFile(source);
			pause(INTERVAL);
		}
	}
}
