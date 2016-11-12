/** Copyright or License
 *
 */

package com.aosorio.app;
/**
 * Package: com.aosorio.app
 *
 * Class: Main Main.java
 * 
 * Original Author: @author AOSORIO
 * 
 * Description: Main class for microservice
 * 
 * Implementation: Uses spark java micro framework
 *
 * Created: Oct 24, 2016 6:23:13 AM
 * 
 */

import static spark.Spark.*;
import spark.Request;
import spark.Response;

import com.google.gson.Gson;

import spark.ResponseTransformer;

public class Main {

	public static int JETTY_SERVER_PORT = 4568;
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		port(JETTY_SERVER_PORT);
		
		get("/hello", (req, res) -> "Hello World");
		
		get("/serialnumbers", Main::getSerialNumber, json());
				
	}

	public static Object getSerialNumber(Request pRequest, Response pResponse ) {
		
		Object response = null;
		System.out.println( pRequest.body() );
		
		try {

			IBarcodeGenMockSvc barcodeGen = new Barcode4JSvc();
		    barcodeGen.generate();
		    response = barcodeGen.getCode();
		    pResponse.status(200);
		    
		} catch (Exception e) {
			System.out.println("We got an exception");
			pResponse.status(400);
			response = "{ errorCode : \"exception caught\"}";
		}

		return response;
	
	}
	
	public static String toJson(Object pObject) {

		return new Gson().toJson(pObject);
	}

	public static ResponseTransformer json() {

		return Main::toJson;
	}

}
