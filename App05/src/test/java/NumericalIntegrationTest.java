import static org.junit.Assert.*;

import org.junit.Test;

/** Copyright or License
 *
 */

/**
 * Package:
 *
 * Class: NumericalIntegrationTest NumericalIntegrationTest.java
 * 
 * Original Author: @author AOSORIO
 * 
 * Description: [one line class summary]
 * 
 * Implementation: [Notes on implementation]
 *
 * Created: Mar 21, 2016 8:23:34 PM
 * 
 */
public class NumericalIntegrationTest {

	/**
	 * Test integration method againts a known integral I = Integrate[Sqrt[x],{x,1,10}=20.4152]
	 */
	@Test
	public void TestIntegral() {

		double[] params = new double[1];
		params[0] = 1.0;

		OneDimFunction sqrtFunct = (OneDimFunction) new SomeFunction(params);
		NumericalIntegration numIntegration = new NumericalIntegration(sqrtFunct, 1.0, 10.0);
		double result = numIntegration.doIntegral();
		assertEquals(20.4152, result, 0.0001);
	}

	/**
	 * Test the case when the integration limits are inverted so a > b
	 * Implementation is only for a < b
	 */
	@Test
	public void TestLimits() {

		Exception expected = null;

		try {
			double[] params = new double[1];
			params[0] = 1.0;
			OneDimFunction sqrtFunct = (OneDimFunction) new SomeFunction(params);
			NumericalIntegration numIntegration = new NumericalIntegration(sqrtFunct, 100.0, 10.0);
			numIntegration.setLimits(10.0, 5.0);
			numIntegration.doIntegral();
		} catch (Exception  ex) {
			expected = ex;
			
		}

		assertTrue(expected instanceof IllegalArgumentException);

	}

	/**
	 * Test agains the provided test (using a t-distribution function)
	 * Expected value number
	 */
	@Test
	public void TestOne() {

		double[] params = new double[1];
		params[0] = 9.0;

		OneDimFunction tdistFunction = (OneDimFunction) new tDistributionFunction(params);
		NumericalIntegration numIntegration = new NumericalIntegration(tdistFunction, 0.0, 1.1);
		double result = numIntegration.doIntegral();
		assertEquals(0.35006, result, 0.00001);
	}

	/**
	 * Test agains the provided test (using a t-distribution function)
	 * Expected value number two 
	 */
	@Test
	public void TestTwo() {

		double[] params = new double[1];
		params[0] = 10.0;

		OneDimFunction tdistFunction = (OneDimFunction) new tDistributionFunction(params);
		NumericalIntegration numIntegration = new NumericalIntegration(tdistFunction, 0.0, 1.1812);
		double result = numIntegration.doIntegral();
		assertEquals(0.36757, result, 0.00001);
	}

	/**
	 * Test agains the provided test (using a t-distribution function)
	 * Expected value number three
	 */
	@Test
	public void TestThree() {

		double[] params = new double[1];
		params[0] = 30.0;

		OneDimFunction tdistFunction = (OneDimFunction) new tDistributionFunction(params);
		NumericalIntegration numIntegration = new NumericalIntegration(tdistFunction, 0.0, 2.750);
		double result = numIntegration.doIntegral();
		assertEquals(0.49500, result, 0.00001);
	}

}
