import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/** Copyright or License
 *
 */

/**
 * Package:
 *
 * Class: StdDevEvaluator StdDevEvaluator.java
 * 
 * Original Author: @author AOSORIO
 * 
 * Description: StdDev almacena el contenedos con las disitn clase
 * 
 * Implementation: -
 *
 * Created: Mar 16, 2016 1:14:42 AM
 * 
 */
public class StdDevRanges {

	String path;
	String infile;
	CSVReader data;

	LinkedList<Double> inputValues;
	LinkedList<Double> logValues;

	Map<String, Double> Ranges = null;

	/** Constructor
	 * @param path
	 * @param infile
	 * @throws FileNotFoundException
	 */
	public StdDevRanges(String path, String infile) throws FileNotFoundException {
		super();
		this.path = path;
		this.infile = infile;

		logValues = new LinkedList<Double>();
		inputValues = new LinkedList<Double>();

		Ranges = new HashMap<String, Double>();

		try {
			data = new CSVReader(this.infile);
			data.readFile();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}
	}

	/** Some data need pre-processing 
	 * @param preprocess
	 */
	public void processData(boolean preprocess) {

		LinkedList<Double> column1 = data.getLinkedList(0);

		Iterator<Double> itr = column1.iterator();

		if (preprocess) {
			LinkedList<Double> column2 = data.getLinkedList(1);

			Iterator<Double> itr2 = column2.iterator();

			while (itr.hasNext()) {
				double val1 = itr.next();
				double val2 = itr2.next();
				inputValues.add(val1 / val2);
			}
		} else {
			while (itr.hasNext()) {
				double val1 = itr.next();
				inputValues.add(val1);
			}

		}

	}

	/**
	 * Takes the log of the input values
	 */
	public void setLog() {

		Iterator<Double> itr = inputValues.iterator();

		while (itr.hasNext()) {
			double value = (double) itr.next();
			logValues.add(Math.log(value));
			//System.out.println("Val " + value + " " + Math.log(value));
		}

	}

	/**
	 * Get the input and create the relative size based on sigmas
	 */
	public void setLogRanges() {

		double sigma = StatisticalFunctions.evalStdDev(logValues);
		double avg = StatisticalFunctions.evalMean(logValues);

		Ranges.put("VS", Math.exp(avg - 2.0 * sigma));
		Ranges.put("S",  Math.exp(avg - 1.0 * sigma));
		Ranges.put("M",  Math.exp(avg));
		Ranges.put("L",  Math.exp(avg + 1.0 * sigma));
		Ranges.put("VL", Math.exp(avg + 2.0 * sigma));

	}

	/**
	 * @param key
	 * @return value
	 */
	public double getRange(String key) {
		double value = Ranges.get(key);
		return value;
	}

}
