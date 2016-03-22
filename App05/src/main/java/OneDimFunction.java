/** Copyright or License
 *
 */

/**
 * Package: 
 *
 * Class: OneDimFunction OneDimFunction.java
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
public interface OneDimFunction {

	double doEval(double x);
	void setParams(double[] params);
	
}

