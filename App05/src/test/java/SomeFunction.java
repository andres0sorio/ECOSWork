/** Copyright or License
 *
 */

/**
 * Package: 
 *
 * Class: SqrtFunction SqrtFunction.java
 * 
 * Original Author: @author AOSORIO
 * 
 * Description: This is a simple fucntion (Sqrt(x)) for testing purposes
 * 
 * Implementation: Implements interface IOneDimFunction
 *
 * Created: Mar 21, 2016 7:28:43 PM
 * 
 */
public class SomeFunction implements IOneDimFunction {

	double [] params;

	/** Constructor method
	 * @param params parameters of this function
	 */
	public SomeFunction(double[] params) {
		super();
		this.params = params;
	}

	/* (non-Javadoc)
	 * @see IOneDimFunction#doEval(double)
	 */
	@Override
	public double doEval(double x) {	
		double a = params[0];
		double y = a * Math.sqrt(x);
		return y;
	}

	/* (non-Javadoc)
	 * @see IOneDimFunction#setParams(double[])
	 */
	@Override
	public void setParams(double[] params) {
		this.params = params;		
	}
	
}
