import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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
 * Class: Analyzer Analyzer.java
 * 
 * Original Author: @author AOSORIO
 * 
 * Description: Class for running full analysis
 * 
 * Implementation: Implements three methods: beginJob, analyze, endJob
 *
 * Created: Apr 17, 2016 8:44:21 PM
 * 
 */
public class Analyzer {

	CSVReader inputFile;
	PairValues<Integer, Integer> option;
	LinkedList<PairValues<Double, Double>> data;
	Map<String, Double> results;
	TableReport table;
	Double xk;
	private int proxySize;
	String name;
	
	public final static PairValues<Integer, Integer> OPTIONONE = new PairValues<Integer, Integer>(1, 3);
	public final static PairValues<Integer, Integer> OPTIONTWO = new PairValues<Integer, Integer>(1, 4);
	public final static ArrayList<String> VARNAMES = new ArrayList<String>(
			Arrays.asList("rXY", "r2", "significance", "beta0", "beta1", "yk", "Range", "upi", "lpi"));

	/**
	 * Main constructor method - sets everything from the start
	 * 
	 * @param option
	 *            the option to use for analyzing our data (select pair of
	 *            columns)
	 * @param inputFile
	 *            this is our object that contains the pointer to our data
	 * @param name
	 * 	          Give a name to this Analyzer instance 
	 */
	public Analyzer(PairValues<Integer, Integer> option, CSVReader inputFile, String name) {
		super();
		this.option = option;
		this.inputFile = inputFile;
		this.name = name;
		results = new HashMap<String, Double>();
	}

	/** beginJob: setup here input data and output data
	 * @throws FileNotFoundException in case there in no input file
	 */
	public void beginJob() throws FileNotFoundException {

		// Get the data and store in into xi, yi
		try {
			if (!inputFile.isFileRead())
				inputFile.readFile();
			data = inputFile.getLinkedList(option.getX(), option.getY());
		} catch (FileNotFoundException e) {
			throw e;
		}

	}

	/**
	 * run the analysis on the data - save results
	 */
	@SuppressWarnings("unused")
	public void analyze() {

		// R1. calculate correlation
		double corrXY = StatisticalFunctions.evalCorrXY(data);
		results.put(Analyzer.VARNAMES.get(0), corrXY);
		double corr2 = StatisticalFunctions.evalCorr2(data);
		results.put(Analyzer.VARNAMES.get(1), corr2);

		// R2. calculate the significance of such correlation
		double significance = StatisticalFunctions.evalSignificance(data);
		results.put(Analyzer.VARNAMES.get(2), significance);

		// R3. Perform the linear regression of data
		PairValues<Double, Double> betas = StatisticalFunctions.evalLinearRegression(data);
		double beta0 = betas.getX();
		double beta1 = betas.getY();		
		results.put(Analyzer.VARNAMES.get(3), beta0);
		results.put(Analyzer.VARNAMES.get(4), beta1);
		double yk = betas.getX() + betas.getY() * proxySize;
		results.put(Analyzer.VARNAMES.get(5), yk);

		// R4. Get the 70% prediction interval
		double interval = StatisticalFunctions.evalPredictionIntervale(data, proxySize);
		results.put(Analyzer.VARNAMES.get(6), interval);
		double upi = yk + interval;
		double lpi = yk - interval;
		results.put(Analyzer.VARNAMES.get(7), upi);
		results.put(Analyzer.VARNAMES.get(8), lpi);
	
		if (false) {
			System.out.println("* " + corrXY);
			System.out.println("* " + corr2);
			System.out.println("* " + significance);
			System.out.println("* " + beta0);
			System.out.println("* " + beta1);
			System.out.println("* " + yk);
			System.out.println("* " + interval);
			System.out.println("* " + upi);
			System.out.println("+ " + lpi);
		}
	}

	/**
	 * process reports based on analysis - export output if needed
	 */
	public void endJob() {

		// Produce reports
		table = new TableReport("*Analyzer " + name + "*");
		Iterator<String> itr = Analyzer.VARNAMES.iterator();

		while (itr.hasNext()) {
			String name = itr.next();
			table.addRow(name, results.get(name));
		}

	}

	/**
	 * Set the proxy size
	 * @param xk given proxy size
	 */
	public void setProxySize(int xk) {
		proxySize = xk;
	}

	/**
	 * Getter for TableReport
	 * @return report - in tableform
	 */
	public TableReport getReport() {
		return table;
	}

	/** Get the result described by "key"
	 * @param next this is the key value we want the result for
	 * @return result associated with key
	 */
	public double getResult(String next) {
		double value = results.get(next);
		return value;
	}

}
