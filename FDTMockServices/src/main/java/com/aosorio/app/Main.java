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
 * Description: [one line class summary]
 * 
 * Implementation: [Notes on implementation]
 *
 * Created: Nov 18, 2016 7:12:06 PM
 * 
 */

import static spark.Spark.*;

import com.google.gson.Gson;

import spark.ResponseTransformer;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		get("/hello", (req, res) -> "Hello World");

		// 1. Expose an ID verification service
		post("/cedulas/verificaciones", BasicExposedServices::idVerificationService, json());
		
		// 2. Expose a Pay button service
		
		
		

	}

	public static String toJson(Object pObject) {

		return new Gson().toJson(pObject);
	}

	public static ResponseTransformer json() {

		return Main::toJson;
	}

}
