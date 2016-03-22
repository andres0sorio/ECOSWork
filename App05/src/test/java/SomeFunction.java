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
 * Implementation: Implements interface OneDimFunction
 *
 * Created: Mar 21, 2016 7:28:43 PM
 * 
 */
public class SomeFunction implements OneDimFunction {

	double [] params;

	/** Constructor method
	 * @param params
	 */
	public SomeFunction(double[] params) {
		super();
		this.params = params;
	}

	/* (non-Javadoc)
	 * @see OneDimFunction#doEval(double)
	 */
	@Override
	public double doEval(double x) {	
		double a = params[0];
		double y = a * Math.sqrt(x);
		return y;
	}

	/* (non-Javadoc)
	 * @see OneDimFunction#setParams(double[])
	 */
	@Override
	public void setParams(double[] params) {
		this.params = params;		
	}
	
}
