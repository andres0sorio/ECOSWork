import static org.junit.Assert.*;

import org.junit.Test;

/** Copyright or License
 *
 */

/**
 * Package: 
 *
 * Class: StdDevTest StdDevTest.java
 * 
 * Original Author: @author AOSORIO
 * 
 * Description: Test class for the StdDev calculation
 * 
 * Implementation: -
 *
 * Created: Mar 17, 2016 12:47:53 PM
 * 
 */
public class StdDevTest {

	/**
	 * Test based on the expected values on Table 1 col 1
	 */
	@Test
	public void testTable1() {
		
		final String dir = System.getProperty("user.dir");

		String path = dir + "/data/";
		String infile = path + "Tabla-1.csv";

		RelSizeClassifier sd1 = new RelSizeClassifier("LOC/Method");
		sd1.runClassification(path, infile, true);

		double output = (double)sd1.getRelSizeRanges().getRange("VS");
		assertEquals(4.3953, output , 0.0001 );
		output = (double)sd1.getRelSizeRanges().getRange("S");
		assertEquals(8.5081, output, 0.0001 );
		output = (double)sd1.getRelSizeRanges().getRange("M");
		assertEquals(16.4696, output, 0.0001 );
		output = (double)sd1.getRelSizeRanges().getRange("L");
		assertEquals(31.8811, output, 0.0001 );
		output = (double)sd1.getRelSizeRanges().getRange("VL");
		assertEquals(61.7137, output, 0.0001 );
	}

	/**
	 * Test based on the expected values on Table 1 col 2
	 */
	@Test
	public void testTable2() {
		
		final String dir = System.getProperty("user.dir");

		String path = dir + "/data/";
		String infile = path + "Tabla-2.csv";

		RelSizeClassifier sd2 = new RelSizeClassifier("Pages/Chapter");
		sd2.runClassification(path, infile, false);

		double output = (double)sd2.getRelSizeRanges().getRange("VS");
		assertEquals(6.3375, output , 0.0001 );
		output = (double)sd2.getRelSizeRanges().getRange("S");
		assertEquals(8.4393, output, 0.0001 );
		output = (double)sd2.getRelSizeRanges().getRange("M");
		assertEquals(11.2381, output, 0.0001 );
		output = (double)sd2.getRelSizeRanges().getRange("L");
		assertEquals(14.9650, output, 0.0001 );
		output = (double)sd2.getRelSizeRanges().getRange("VL");
		assertEquals(19.9280, output, 0.0001 );
		
	}
}
