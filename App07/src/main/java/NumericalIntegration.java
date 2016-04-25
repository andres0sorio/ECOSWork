/** Copyright or License
 *
 */

/**
 * Package:
 *
 * Class: NumericalIntegration NumericalIntegration.java
 * 
 * Original Author: @author AOSORIO
 * 
 * Description: Numerical Integration for 1D functions using Simpson's rule
 * 
 * Implementation: We only implement here Simpson's rule - can be extended to other methods
 * 
 *
 * Created: Mar 21, 2016 2:03:06 AM
 * 
 */
public class NumericalIntegration {

	public final static double ERROR = 0.000000000001;

	private IOneDimFunction integrand = null;
	private double lowerLimit = Double.NaN;
	private double upperLimit = Double.NaN;
	private double deltaX = 0.0;
	private double numSegs = 10.0;
	private double integral = 0.0;
	private boolean isEvaluable;

	/** Constructor method
	 * @param integrand : a IOneDimFunction (previously defined)
	 * @param lowerLimit limit  integration a
	 * @param upperLimit limit  integration b
	 */
	public NumericalIntegration(IOneDimFunction integrand, double lowerLimit, double upperLimit) {
		super();
		this.isEvaluable = false;
		this.integrand = integrand;
		try {
			this.setLimits(lowerLimit, upperLimit);
			this.isEvaluable = true;
		} catch (Exception ex) {
			System.out.println("IllegalArgumentException: Check limits (a>b)!");
		}

	}

	/** Getter for integrand
	 * @return the integrand function
	 */
	public IOneDimFunction getIntegrand() {
		return integrand;
	}

	/** Setter for integrand
	 * @param integrand pass the integrand, should be an implementation of IOneDimFunction
	 */
	public void setIntegrand(IOneDimFunction integrand) {
		this.integrand = integrand;
	}

	/** Getter for lower limit
	 * @return the lowerLimit
	 */
	public double getLowerLimit() {
		return lowerLimit;
	}

	/** Getter for upper limit
	 * @return the upperLimit
	 */
	public double getUpperLimit() {
		return upperLimit;
	}

	/** Setter for both limits
	 * @param lowerLimit
	 *            the lowerLimit to set
	 * @param upperLimit
	 *            the upperLimit to set
	 */
	public void setLimits(double lowerLimit, double upperLimit) {

		if (lowerLimit >= upperLimit)
			throw new IllegalArgumentException("Check limits (a<b)");
		if (!Double.isNaN(lowerLimit))
			this.lowerLimit = lowerLimit;
		if (!Double.isNaN(upperLimit))
			this.upperLimit = upperLimit;

	}

	/** do Integration : implements Simmson's rule for numerical integration
	 * @return result of this integral
	 */
	public double doIntegral() {

		double prevResult = 1.0;
		double currentError = 1.0;

		if( !isEvaluable ) return -1.0;
		
		while (currentError > ERROR) {

			double fOdd = 0.0;
			double fEven = 0.0;

			deltaX = (upperLimit - lowerLimit) / numSegs;

			double f0 = integrand.doEval(lowerLimit);

			for (int i = 1; i <= numSegs - 1; i += 2) {
				fOdd += 4.0 * integrand.doEval(i * deltaX + lowerLimit);
			}

			for (int i = 2; i <= numSegs - 2; i += 2) {
				fEven += 2.0 * integrand.doEval(i * deltaX + lowerLimit);
			}

			double fx = integrand.doEval(upperLimit);

			integral = (deltaX / 3.0) * (f0 + fOdd + fEven + fx);

			currentError = Math.abs(integral - prevResult);

			prevResult = integral;

			// System.out.println("I: " + integral + " " + currentError);

			numSegs = numSegs * 2.0;

		}

		return integral;

	}

}
