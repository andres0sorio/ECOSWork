/** Copyright or License
 *
 */

package edu.uniandes.ecos.codeaholics.main;

import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.file.*;
import static spark.Spark.*;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.MultipartConfigElement;
import static spark.debug.DebugScreen.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	/**
	 * @param args
	 */

	public static String LOCAL_TMP_PATH = "D:/Temp/MyDocuments/";
	public static final String LOCAL_TMP_PATH_ENV = "LOCAL_TMP_PATH_ENV";
	public static FileUtilities fileUtilities;
	private final static Logger log = LogManager.getLogger(Main.class);

	public static void main(String[] args) {

		enableDebugScreen();

		// Get the TMP_PATH from an environment variable
		String value = System.getenv(LOCAL_TMP_PATH_ENV);
		if (value != null) {
			log.info(LOCAL_TMP_PATH_ENV + " " + value);
			LOCAL_TMP_PATH = value;
		} else {
			log.info(LOCAL_TMP_PATH_ENV + " not assigned.");
		}

		fileUtilities = new FileUtilities(LOCAL_TMP_PATH);

		// 1. GET
		File uploadDir = new File(Main.LOCAL_TMP_PATH);

		uploadDir.mkdir();

		get("/procedures/documents/upload",
				(req, res) -> "<form method='post' enctype='multipart/form-data'>"
						+ "    <input type='file' name='uploaded_file' accept='image/*,.pdf,.PDF'>"
						+ "    <button>Upload picture</button>" + "</form>");

		// 2. POST

		post("/procedures/documents/upload", (req, response) -> {

			Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");

			req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

			Collection<Part> parts = req.raw().getParts();
			Part fPart = parts.iterator().next();

			try (InputStream input = fPart.getInputStream()) {

				Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
				RequiredDocument rDoc;

				rDoc = new RequiredDocument();
				rDoc.setFilePath(LOCAL_TMP_PATH);
				rDoc.setTmpName(tempFile.getFileName().toString());
				rDoc.setOriginalName(fPart.getSubmittedFileName());
				rDoc.setFileSize(fPart.getSize());
				rDoc.setRadicado(123456);
				rDoc.setTimestamp(System.currentTimeMillis());

				Gson gson = new Gson();
				String json = gson.toJson(rDoc);
				System.out.println(json);

				//logInfo(req, tempFile);
				return json;
			}

		});

		// 3. GET list of JSONs
		get("/procedures/documents/list", (req, res) -> getList());

	}

	private static String getList() {

		ArrayList<String> files = listDownloadedFiles(LOCAL_TMP_PATH);
		Iterator<String> itrFiles = files.iterator();
		String allFiles = "[";

		while (itrFiles.hasNext()) {
			String jsonStr = "{\"file" + "\": \"" + itrFiles.next().replace("\\", "/") +"\"}";
			JsonParser parser = new JsonParser();
			JsonObject obj = parser.parse(jsonStr).getAsJsonObject();
			allFiles += obj.toString() + ",";
		}
		allFiles = removeLastChar(allFiles) + "]";
		return allFiles;
	}

	// methods used for logging
	//private static void logInfo(Request req, Path tempFile) throws IOException, ServletException {
	//	System.out.println("Uploaded file '" + getFileName(req.raw().getPart("uploaded_file")) + "' saved as '"
	//			+ tempFile.toAbsolutePath() + "'");
	//}

	@SuppressWarnings("unused")
	private static String getFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	public static ArrayList<String> listDownloadedFiles(String pPath) {

		ArrayList<String> currentFiles = new ArrayList<String>();
		fileUtilities.processRoot();
		currentFiles = fileUtilities.getAllFiles();
		return currentFiles;
	}
	
	private static String removeLastChar(String str) {
        return str.substring(0,str.length()-1);
    }

}
