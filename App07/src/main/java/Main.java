/** Copyright or License
 *
 */
import java.sql.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;
import java.math.RoundingMode;
import org.jfree.chart.plot.XYPlot;
import java.io.FileNotFoundException;

import java.net.URI;
import java.net.URISyntaxException;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import static spark.Spark.get;

import com.heroku.sdk.jdbc.DatabaseUrl;

/**
 * Package:
 *
 * Class: Main Main.java
 * 
 * Original Author: @author AOSORIO
 * 
 * Description: Main class for PSP2Exercise05
 * 
 * Implementation: Heroku deployment
 *
 * Created: Apr 21, 2016 8:44:21 PM
 * 
 */

public class Main {

  public static void main(String[] args) {

    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");

    String message = doPSP2Exercise04();

    get("/hello", (req, res) -> message);

    String report = doPSP2Exercise05();

    get("/final", (req, res) -> report);

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

    /**
     * doPSP2Exercise05: exercise 7 Combining all tools
     * 
     * @return string with a table of results (expected vs actual)
     */
    public static String doPSP2Exercise05() {

	CSVReader testFile    = new CSVReader("data/Table-1.csv");
	CSVReader aosorioFile = new CSVReader("data/AOsorio-Table-2.csv");
	CSVReader expectedOne = new CSVReader("data/expected-1.csv");
	CSVReader expectedTwo = new CSVReader("data/expected-2.csv");
	
	ArrayList<Analyzer> allAnalysis = new ArrayList<Analyzer>();
	
	Analyzer an1 = new Analyzer(Analyzer.OPTIONONE, testFile, "Test 1");
	Analyzer an2 = new Analyzer(Analyzer.OPTIONTWO, testFile, "Test 2");
	Analyzer an3 = new Analyzer(Analyzer.OPTIONONE, aosorioFile, "AOsorio A1");
	Analyzer an4 = new Analyzer(Analyzer.OPTIONTWO, aosorioFile, "AOsorio A2");
	
	an1.setProxySize(386);
	an2.setProxySize(386);
	an3.setProxySize(169);
	an4.setProxySize(169);
	
	allAnalysis.add(an1);
	allAnalysis.add(an2);
	allAnalysis.add(an3);
	allAnalysis.add(an4);
	
	Iterator<Analyzer> itr = allAnalysis.iterator();
	
	while (itr.hasNext()) {
	    
	    Analyzer current = (Analyzer) itr.next();
	    
	    try {
		current.beginJob();
		current.analyze();
		current.endJob();
	    } catch (FileNotFoundException e) {
		System.out.println("File not found, please check");
	    }
	    
	}
	
	//
	
	try {
	    expectedOne.readFile();
	    expectedTwo.readFile();
	} catch (FileNotFoundException e) {
	    System.out.println("File not found, please check");
	}
	
	an1.getReport().addColumn(expectedOne.getSingleColumn(0));
	an2.getReport().addColumn(expectedTwo.getSingleColumn(0));
	
	System.out.println(an1.getReport().toString());
	System.out.println(an2.getReport().toString());
	System.out.println(an3.getReport().toString());
	System.out.println(an4.getReport().toString());
	
	String fullMessage = "";
	fullMessage += an1.getReport().toHTML();
	fullMessage += an2.getReport().toHTML();
	fullMessage += an3.getReport().toHTML();
	fullMessage += an4.getReport().toHTML();
	
	return fullMessage;
	
    }

}
