/** Copyright or License
 *
 */

/**
 * Package: 
 *
 * Class: IOneDimFunction IOneDimFunction.java
 * 
 * Original Author: @author AOSORIO
 * 
 * Description: Interface needed for numerical integration class
 * 
 * Implementation: For the moment only dealing with 1D functions
 *
 * Created: Mar 21, 2016 1:56:17 AM
 * 
 */
public interface IOneDimFunction {

	/**
	 * @param x the variable
	 * @return function evaluated at x 
	 */
	double doEval(double x);
	/**
	 * @param params set the paramaters (passed as an array)
	 */
	void setParams(double[] params);
	
}

