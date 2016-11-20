package com.aosorio.app;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import spark.Spark;

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
		String serverPath = "http://localhost:4568";

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
			urlConnection.setRequestMethod("GET");

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
			
			if( json.getAsJsonObject().has("barcodeImg")) {
				convertStringFile(json.get("barcodeImg").getAsString());
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(200, httpResult);
		assertEquals("OK", httpMessage);
		
	}
	
	@Test
	public void getBarcodeTest() {

		int httpResult = 0;
		String httpMessage = "";

		StringBuilder result = new StringBuilder();

		String route = "/serialnumbers";
		String serverPath = "http://localhost:4568";

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
			urlConnection.setRequestMethod("GET");

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
			
			assertTrue(json.getAsJsonObject().has("barcodeImg"));
			
			if( json.getAsJsonObject().has("barcodeImg")) {
				
				convertStringFile(json.get("barcodeImg").getAsString());
				String barcodeImg = json.get("barcodeImg").getAsString();				
				logger.info(barcodeImg);
				
				byte[] fileOne = Files.readAllBytes(new File("output.png").toPath());
				byte[] fileTwo = Files.readAllBytes(new File("barcode.png").toPath());
				
				assertEquals(fileOne[0], fileTwo[0]);
				
			}
			
			
			
			
			
								
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(200, httpResult);
		assertEquals("OK", httpMessage);
		
	}

    private void convertStringFile(String barcodeStr) throws IOException{
    	
    	byte[] bytes = Base64.getDecoder().decode(barcodeStr);	
		FileOutputStream fos = new FileOutputStream("barcode.png");
		fos.write(bytes);
		fos.close();
		
    }
    
}
