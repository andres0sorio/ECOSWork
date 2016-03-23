import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import java.net.URI;
import java.net.URISyntaxException;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import static spark.Spark.get;

import com.heroku.sdk.jdbc.DatabaseUrl;

import java.text.DecimalFormat;

public class Main {

  public static void main(String[] args) {

    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");

    final String psp2ex3 = doPSP2Exercise03();
    
    get("/hello", (req, res) -> psp2ex3);

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

    public static String doPSP2Exercise03() {

		double result1 = 0.0;
		double result2 = 0.0;
		double result3 = 0.0;

		DecimalFormat df=new DecimalFormat("0.00000");
 		
		TableReport summary = new TableReport("Numerical Integration with Simpson's Rule");
		summary.addRow("Range \t dof \t expected \t result");
		
		double[] params = new double[2];
		params[0] = 9.0; // dof

		IOneDimFunction tDistFunction = (IOneDimFunction) new tDistributionFunction(params);

		NumericalIntegration numIntegration = new NumericalIntegration(tDistFunction, 0.0, 1.1);

		result1 = numIntegration.doIntegral();

		String strResult = "[0.0,1.1] \t 9 \t 0.35006 \t" + df.format(result1);

		summary.addRow(strResult);
		
		params[0] = 10.0; // dof
		tDistFunction.setParams(params);
		numIntegration.setLimits(0.0, 1.1812);
		result2 = numIntegration.doIntegral();

		strResult = "[0.0,1.1812] \t 10 \t 0.36757 \t" + df.format(result2);
		summary.addRow(strResult);
		
		params[0] = 30.0; // dof
		tDistFunction.setParams(params);
		numIntegration.setLimits(0.0, 2.750);
		result3 = numIntegration.doIntegral();

		strResult = "[0.0,2.750] \t 30 \t 0.49500  \t" + df.format(result3);
		summary.addRow(strResult);
		
		return summary.toHTML();

	}


}
