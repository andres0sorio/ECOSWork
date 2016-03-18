import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import static spark.Spark.get;

import com.heroku.sdk.jdbc.DatabaseUrl;

public class Main {

	public static void main(String[] args) {

		port(Integer.valueOf(System.getenv("PORT")));
		staticFileLocation("/public");

		final String message = doPSP2Exercise02();

		get("/hello", (req, res) -> message );
		
		get("/", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("message", "Hello World!");

			return new ModelAndView(attributes, "index.ftl");
		} , new FreeMarkerEngine());

		get("/db", (req, res) -> {
			Connection connection = null;
			Map<String, Object> attributes = new HashMap<>();
			try {
				connection = DatabaseUrl.extract().getConnection();

				Statement stmt = connection.createStatement();
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
				stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
				ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

				ArrayList<String> output = new ArrayList<String>();
				while (rs.next()) {
					output.add("Read from DB: " + rs.getTimestamp("tick"));
				}

				attributes.put("results", output);
				return new ModelAndView(attributes, "db.ftl");
			} catch (Exception e) {
				attributes.put("message", "There was an error: " + e);
				return new ModelAndView(attributes, "error.ftl");
			} finally {
				if (connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
					}
			}
		} , new FreeMarkerEngine());

	}


    public static String doPSP2Exercise02() {
		
		String message = "";
		
		final String dir = System.getProperty("user.dir");

		String path = dir + "/data/";
		String infile = path + "Tabla-1.csv";

		RelSizeClassifier sd1 = new RelSizeClassifier("LOC/Method");
		sd1.runClassification(path, infile, true);

		infile = path + "Tabla-2.csv";
		RelSizeClassifier sd2 = new RelSizeClassifier("Pages/Chapter");
		sd2.runClassification(path, infile, false);

		sd1.printSummary();
		sd2.printSummary();

		TableReport expected = new TableReport("Expected values");

		try {
			CSVReader data = new CSVReader(path + "Tabla-3.csv");
			data.readFile();
			expected = data.getTable("Expected results");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(expected);

		String table1 = sd1.output.toHTML();
		String table2 = sd2.output.toHTML();
		String table3 = expected.toHTML();
				
		return (table1+table2+table3);

    }
    
}
