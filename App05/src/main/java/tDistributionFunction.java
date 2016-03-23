/** Copyright or License
 *
 */

/**
 * Package: 
 *
 * Class: tDistributionFunction tDistributionFunction.java
 * 
 * Original Author: @author AOSORIO
 * 
 * Description: Student t-distribution
 * 
 * Implementation: Implements interface IOneDimFunction
 *
 * Created: Mar 21, 2016 7:13:08 PM
 * 
 */
public class tDistributionFunction implements IOneDimFunction {

	double [] params;
		
	/** Constructor method
	 * @param params the parameters of this function
	 */
	public tDistributionFunction(double[] params) {
		super();
		this.params = params;
	}
	
	/** Get the array of parameter
	 * @return the params
	 */
	public double[] getParams() {
		return params;
	}

	/* (non-Javadoc)
	 * @see IOneDimFunction#setParams(double[])
	 */
	@Override
	public void setParams(double[] params) {
		this.params = params;	
	}

	/* (non-Javadoc)
	 * @see IOneDimFunction#doEval(double)
	 */
	@Override
	public double doEval(double x) {
		double y = StatisticalFunctions.tDistribution(x,this.params);
		return y;
		
	}
	
}

