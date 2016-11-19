/** Copyright or License
 *
 */

package com.aosorio.app;

import com.aosorio.app.IMessageSvc;
import com.aosorio.app.ResponseMessage;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import spark.Request;
import spark.Response;

/**
 * Package: com.aosorio.app
 *
 * Class: BasicExposedServices BasicExposedServices.java
 * 
 * Original Author: @author AOSORIO
 * 
 * Description: [one line class summary]
 * 
 * Implementation: [Notes on implementation]
 *
 * Created: Nov 18, 2016 7:19:31 PM
 * 
 */
public class BasicExposedServices {

	private static IMessageSvc messager = new ResponseMessage();
	
	public static Object idVerificationService(Request pRequest, Response pResponse) {

		Object response = null;
		
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(pRequest.body()).getAsJsonObject();
		
		int citizenId = json.get("cedula").getAsInt();
		String issueDate = json.get("fechaExp").getAsString();
	
		try {
			
			verificationService(citizenId,issueDate);		
			response = messager.getOkMessage("Cedula vigente");
			
		} catch( Exception ex ) {
			
			response = messager.getNotOkMessage("Cedula no certificada");
		}
			
		return response;

	}

	public static Object payService(Request pRequest, Response pResponse) {

		Object response = null;
		
		response = messager.getOkMessage("Proceso Exitoso");
		
		return response;
		
	}

	private static void verificationService( int id, String issueDate) throws Exception {
		
			if ( id > 0) {
				System.out.println("Cedula existe en nuestra base de datos");
			} else {
				throw new Exception("Cedula no esta en nuestra base de datos");				
			}
		
	}
	
}
