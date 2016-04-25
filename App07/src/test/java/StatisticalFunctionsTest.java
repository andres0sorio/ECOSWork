/** Copyright or License
*
*/
import static org.junit.Assert.*;

import org.junit.Test;

/**
* Package: 
*
* Class: StatisticalFunctions StatisticalFunctionsTest.java
* 
* Original Author: @author AOSORIO
* 
* Description: StatisticalTests
* 
* Implementation: One test per method
*
* Created: Apr 24, 2016 4:15:36 PM
* 
*/

public class StatisticalFunctionsTest {

	/**
	 * Test against the provided test (using the CDF t-distribution function)
	 * Expected value number
	 */
	@Test
	public void TestOne() {

		double[] params = new double[1];
		params[0] = 9.0;
		double result = StatisticalFunctions.tDistributionCDF(1.1, params);
		assertEquals(0.35006, result, 0.00001);
	}
	
	/**
	 * Test against the provided test (using the CDF t-distribution function)
	 * Expected value number
	 */
	@Test
	public void TestTwo() {

		double[] params = new double[1];
		params[0] = 10.0;
		double result = StatisticalFunctions.tDistributionCDF(1.1812, params);
		assertEquals(0.36757, result, 0.00001);
	}
	
	/**
	 * Test against the provided test (using the CDF t-distribution function)
	 * Expected value number
	 */
	@Test
	public void TestThree() {

		double[] params = new double[1];
		params[0] = 30.0;
		double result = StatisticalFunctions.tDistributionCDF(2.750, params);
		assertEquals(0.49500, result, 0.00001);
	}

	/**
	 * Test against the provided test (using the CDF t-distribution inverse function)
	 * Expected value number
	 */
	@Test
	public void TestRootFinderOne() {

		double[] params = new double[1];
		params[0] = 6.0;
		double result = StatisticalFunctions.tDistributionCDFInverse(0.20, params);
		assertEquals(0.55338, result, 0.00001);
	}
	
	/**
	 * Test against the provided test (using the CDF t-distribution inverse function)
	 * Expected value number
	 */
	@Test
	public void TestRootFinderTwo() {

		double[] params = new double[1];
		params[0] = 15.0;
		double result = StatisticalFunctions.tDistributionCDFInverse(0.45, params);
		assertEquals(1.75305, result, 0.00001);
	}
	
	/**
	 * Test against the provided test (using the CDF t-distribution inverse function)
	 * Expected value number
	 */
	@Test
	public void TestRootFinderThree() {

		double[] params = new double[1];
		params[0] = 4.0;
		double result = StatisticalFunctions.tDistributionCDFInverse(0.495, params);
		assertEquals(4.60409, result, 0.00001);
	}
	
}
