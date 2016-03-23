
/** Copyright or License
 *
 */

import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.math3.special.Gamma;

/**
 * Package: uniandes.ecos.psp
 *
 * Class: StatisticalFunctions StatisticalFunctions.java
 * 
 * Original Author: @author AOSORIO
 * 
 * Description: Use this class for Statistical methods implementations
 * 
 * Implementation: static methods are implemented
 * 
 * Created: Feb 15, 2016 3:47:08 PM
 * 
 */
public class StatisticalFunctions {

	/** Calculate the mean or average of input data
	 * @param input data - encaptusaled in a linkedList of double
	 * @return double value with the mean-average
	 */
	public static double evalMean(LinkedList<Double> input) {

		double result = 0.0;
		double sum = 0.0;

		Iterator<Double> itr = input.iterator();

		while (itr.hasNext()) {
			double value = (double) itr.next();
			sum += value;
		}

		int nValues = input.size();

		if (nValues != 0)
			result = sum / (double) nValues;

		return result;

	}

	/** Evaluate the Std dev. of input data
	 * @param input data - encaptusaled in a linkedList of double
	 * @return stddev of data
	 */
	public static double evalStdDev(LinkedList<Double> input) {

		double result = 0.0;
		double average = evalMean(input);
		double sum = 0.0;

		Iterator<Double> itr = input.iterator();

		while (itr.hasNext()) {
			double value = (double) itr.next();
			sum += (value - average) * (value - average);
		}

		int nValues = input.size();

		if (nValues > 1)
			result = Math.sqrt(sum / ((double) nValues - 1));
		else if (nValues == 1)
			System.out.println("Note: Std deviation of 1 number. List should have at least 2 elements");

		return result;

	}

	/** Evaluate the sum of input data
	 * @param input data - encaptusaled in a linkedList of double
	 * @return sum of data x_i
	 */
	public static double evalSum(LinkedList<Double> input) {

		double sum = 0.0;

		Iterator<Double> itr = input.iterator();

		while (itr.hasNext()) {
			double value = (double) itr.next();
			sum += value;
		}
		return sum;
	}

	/** Evaluate the sum of the squares of input data
	 * @param input data - encaptusaled in a linkedList of double
	 * @return sum of squares
	 */
	public static double evalSumSqr(LinkedList<Double> input) {

		double sum = 0.0;

		Iterator<Double> itr = input.iterator();

		while (itr.hasNext()) {
			double value = (double) itr.next();
			sum += value * value;
		}
		return sum;
	}

	/** Evaluate the sum of X_i * Y_i
	 * @param input data - encaptusaled in a linkedList pair of double
	 * @return sum of X_i*Y_i
	 */
	public static double evalSumXY(LinkedList<PairValues<Double, Double>> input) {

		double sumXY = 0.0;
		Iterator<PairValues<Double, Double>> itr = input.iterator();

		while (itr.hasNext()) {
			PairValues<Double, Double> xy = (PairValues<Double, Double>) itr.next();
			sumXY += xy.getX() * xy.getY();
		}

		return sumXY;

	}

	/** Evaluate the linear regression of pairs of data &lt; X_i, Y_i &gt;
	 * @param input data - encaptusaled in a linkedList pair of double
	 * @return the linear regression coefficients of data
	 */
	public static PairValues<Double, Double> evalLinearRegression(LinkedList<PairValues<Double, Double>> input) {

		double beta0 = 0.0;
		double beta1 = 0.0;

		LinkedList<Double> xValues = extractPair(input, 0);
		LinkedList<Double> yValues = extractPair(input, 1);

		int nPoints = input.size();

		double sumXY = evalSumXY(input);
		double xAvg = evalMean(xValues);
		double yAvg = evalMean(yValues);
		double nXavgYavg = nPoints * xAvg * yAvg;
		double sumX2 = evalSumSqr(xValues);
		double nXavg2 = nPoints * xAvg * xAvg;

		beta1 = (sumXY - nXavgYavg) / (sumX2 - nXavg2);
		beta0 = yAvg - (beta1 * xAvg);

		PairValues<Double, Double> coefficients = new PairValues<Double, Double>(0.0, 0.0);

		coefficients.setX(beta0);
		coefficients.setY(beta1);

		return coefficients;

	}

	/** Evaluates the Correlation between XY data
	 * @param input data - encaptusaled in a linkedList of pair double
	 * @return Correlation coefficient
	 */
	public static double evalCorrXY(LinkedList<PairValues<Double, Double>> input) {

		int nPoints = input.size();

		LinkedList<Double> xValues = extractPair(input, 0);
		LinkedList<Double> yValues = extractPair(input, 1);

		double sumXY = evalSumXY(input);
		double sumX = evalSum(xValues);
		double sumY = evalSum(yValues);
		double sumX2 = evalSumSqr(xValues);
		double sumY2 = evalSumSqr(yValues);

		double denominator = Math.sqrt(((nPoints * sumX2) - (sumX * sumX)) * ((nPoints * sumY2) - (sumY * sumY)));

		double Rxy = ((nPoints * sumXY) - (sumX * sumY)) / denominator;

		return Rxy;

	}

	/** Evaluate the correlation coefficient squared R2
	 * @param input data - encaptusaled in a linkedList pair of double
	 * @return R2 correlation coefficient squared
	 */
	public static double evalCorr2(LinkedList<PairValues<Double, Double>> input) {

		double Rxy = evalCorrXY(input);
		return Rxy * Rxy;

	}

	/** Auxiliary function : extract one of the pairs ( X_i or Y_i)
	 * @param input data - encaptusaled in a linkedList of double
	 * @param pair selected pair 0 or 1
	 * @return extract one of the pairs
	 */
	public static LinkedList<Double> extractPair(LinkedList<PairValues<Double, Double>> input, int pair) {

		LinkedList<Double> result = new LinkedList<Double>();

		Iterator<PairValues<Double, Double>> itr = input.iterator();

		while (itr.hasNext()) {
			PairValues<Double, Double> xy = (PairValues<Double, Double>) itr.next();
			if (pair == 0)
				result.add(xy.getX());
			else if (pair == 1)
				result.add(xy.getY());
			else {
			}
		}

		return result;
	}

	/** Evaluates the variance of a given set of data
	 * @param input data - encaptusaled in a linkedList of double
	 * @return var variance of data
	 */
	public static double evalVariance(LinkedList<Double> input) {

		double sum = 0.0;
		double avg = evalMean(input);
		double max = input.size();

		Iterator<Double> itr = input.iterator();

		while (itr.hasNext()) {
			double xi = itr.next();
			double value = (xi - avg) * (xi - avg);
			sum += value;
		}

		max = (double) max - 1.0;
		return sum / max;
	}

	/** Student t - distribution
	 * @param x running x
	 * @param args (only one argument d.o.f.)
	 * @return F(x)
	 */
	public static double tDistribution(double x, double [] args) {

		double dof = args[0];
		double numerator = Gamma.gamma( (dof+1.0)/2.0 );
		double denominator = Math.sqrt((dof*Math.PI))*Gamma.gamma(dof/2.0);		
		return (numerator/denominator)*Math.pow((1.0+(x*x/dof)),-(dof+1.0)/2.0);

	}

	/** Cummulative Student t - distribution (to be implemented)
	 * @param x running x
	 * @param args arguments of this fucntion
	 * @return I ( F(x) )
	 */
	public static double tDistributionCDF(double x, double [] args ) {

		double dof = args[0];
		double x0 = args[1];
		double numerator = Gamma.gamma(1);

		return 0.0;

	}

}
