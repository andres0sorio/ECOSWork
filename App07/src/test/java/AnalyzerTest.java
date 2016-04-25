import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Iterator;

import org.junit.Test;

/** Copyright or License
 *
 */

/**
 * Package: 
 *
 * Class: AnalyzerTest AnalyzerTest.java
 * 
 * Original Author: @author AOSORIO
 * 
 * Description: Tests for the Analyzer - Performs the requested tests
 * 
 * Implementation: One test method per test
 *
 * Created: Apr 24, 2016 4:15:36 PM
 * 
 */
public class AnalyzerTest {

	@Test
	public void TestOne() {
		CSVReader testFile = new CSVReader("data/Table-1.csv");
		CSVReader expectedOne = new CSVReader("data/expected-1.csv");
		CSVReader expectedTwo = new CSVReader("data/expected-2.csv");
		
		Analyzer an1 = new Analyzer(Analyzer.OPTIONONE, testFile, "Test 1");

		an1.setProxySize(386);

		try {
			an1.beginJob();
			an1.analyze();
			an1.endJob();
		} catch (FileNotFoundException e) {
			System.out.println("File not found, please check");
		}

		try {
			expectedOne.readFile();
		} catch (FileNotFoundException e) {
			System.out.println("File not found, please check");
		}

		Iterator<String> itr = Analyzer.VARNAMES.iterator();

		int index = 0;
		while (itr.hasNext()) {
			double expected = expectedOne.getSingleColumn(0).get(index);
			double actual = an1.getResult( itr.next());
			assertEquals(expected, actual, 0.0000001);
			index += 1;
		}
	}

	@Test
	public void TestTwo() {
		CSVReader testFile = new CSVReader("data/Table-1.csv");
		CSVReader expectedTwo = new CSVReader("data/expected-2.csv");
		
		Analyzer an1 = new Analyzer(Analyzer.OPTIONTWO, testFile, "Test 1");

		an1.setProxySize(386);

		try {
			an1.beginJob();
			an1.analyze();
			an1.endJob();
		} catch (FileNotFoundException e) {
			System.out.println("File not found, please check");
		}

		try {
			expectedTwo.readFile();
		} catch (FileNotFoundException e) {
			System.out.println("File not found, please check");
		}

		Iterator<String> itr = Analyzer.VARNAMES.iterator();

		int index = 0;
		while (itr.hasNext()) {
			double expected = expectedTwo.getSingleColumn(0).get(index);
			double actual = an1.getResult( itr.next());
			assertEquals(expected, actual, 0.0000001);
			index += 1;
		}
	}
	
}
