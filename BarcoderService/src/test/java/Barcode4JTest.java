import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.aosorio.app.Main;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import spark.Spark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Barcode4JTest {
	
	Logger logger = LoggerFactory.getLogger(Barcode4JTest.class);

	@BeforeClass
	public static void beforeClass() {
		Main.main(null);
	}

	@AfterClass
	public static void afterClass() {
		Spark.stop();
	}

	@Test
	public void serialTest() {

		int httpResult = 0;
		String httpMessage = "";

		StringBuilder result = new StringBuilder();

		String route = "/serialnumbers";
		String serverPath = "http://localhost:4567";

		try {
			URL appUrl = new URL(serverPath + route);

			// TODO ... study and understand why this fixes the Connection
			// refused error
			System.out.println("===== 0. ");
			InputStream response = new URL("http://stackoverflow.com").openStream();
			response.close();
			System.out.println("===== 0. =====");

			HttpURLConnection urlConnection = (HttpURLConnection) appUrl.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);
			urlConnection.setRequestProperty("Content-type", "application/json");
			urlConnection.setRequestMethod("POST");

			String loginData = "{ alcaldia : \"anapoima\"}";

			Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
			writer.write(loginData);
			writer.flush();
			httpResult = urlConnection.getResponseCode();
			httpMessage = urlConnection.getResponseMessage();

			InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
			BufferedReader reader = new BufferedReader(in);

			String text = "";
			while ((text = reader.readLine()) != null) {
				result.append(text);
			}

			reader.close();
			in.close();

			// TODO: move this outside of the try catch once the Connection
			// error is understood
			JsonParser parser = new JsonParser();
			JsonObject json = parser.parse(result.toString()).getAsJsonObject();

			logger.info(json.toString());
			
			assertTrue(json.getAsJsonObject().has("code"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(200, httpResult);
		assertEquals("OK", httpMessage);
		
	}

}
