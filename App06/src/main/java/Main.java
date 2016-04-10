import java.sql.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.math.RoundingMode;

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

    String message = doPSP2Exercise04();

    get("/hello", (req, res) -> message);

    get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("message", "Hello World!");

            return new ModelAndView(attributes, "index.ftl");
        }, new FreeMarkerEngine());

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
          output.add( "Read from DB: " + rs.getTimestamp("tick"));
        }

        attributes.put("results", output);
        return new ModelAndView(attributes, "db.ftl");
      } catch (Exception e) {
        attributes.put("message", "There was an error: " + e);
        return new ModelAndView(attributes, "error.ftl");
      } finally {
        if (connection != null) try{connection.close();} catch(SQLException e){}
      }
    }, new FreeMarkerEngine());

  }

  /** doPSP2Exercise04: exercise 6 finding root
   * @return string with a table of results (expected vs actual)
   */
  public static String doPSP2Exercise04() {
		
      double result1 = 0.0;
      
      DecimalFormat df = new DecimalFormat("0.00000");
      df.setRoundingMode(RoundingMode.DOWN);
      
      TableReport summary = new TableReport("Inverse t-distribution");
      summary.addRow("p \t dof \t x:expected \t x:actual");
      
      double[] params = new double[1];
      params[0] = 6.0; // dof
      
      result1 = StatisticalFunctions.tDistributionCDFInverse(0.20, params);
      
      String strResult = "0.20 \t 6 \t 0.55338 \t" + df.format(result1);
      
      summary.addRow(strResult);
      
      params[0] = 15.0; // dof
      
      result1 = StatisticalFunctions.tDistributionCDFInverse(0.45, params);
      
      strResult = "0.45 \t 15 \t 1.75305 \t" + df.format(result1);
      
      summary.addRow(strResult);
      
      params[0] = 4.0; // dof
      
      result1 = StatisticalFunctions.tDistributionCDFInverse(0.495, params);
      
      strResult = "0.495 \t 4 \t 4.60409 \t" + df.format(result1);
      
      summary.addRow(strResult);
      
      return summary.toHTML();		
      
  }

}
