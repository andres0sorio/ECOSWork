import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

/** Copyright or License
 *
 */

/**
 * Package: 
 *
 * Class: RelSizeClassifier RelSizeClassifier.java
 * 
 * Original Author: @author AOSORIO
 * 
 * Description: Classifier
 * 
 * Implementation: -
 *
 * Created: Mar 16, 2016 3:25:48 PM
 * 
 */
public class RelSizeClassifier {

	TableReport output;
	StdDevRanges RelSizeRanges;
	ArrayList<String> RelSize;
	
	public RelSizeClassifier(String option) {
		super();	
		output = new TableReport("Standard Deviation results " + option);
		RelSize = new ArrayList<String>();
		
		RelSize.add("VS");
		RelSize.add("S");
		RelSize.add("M");
		RelSize.add("L");
		RelSize.add("VL");
			
	}
	
	public void runClassification(String path, String infile, boolean preprocess) {
		
		try {
			RelSizeRanges = new StdDevRanges(path, infile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		RelSizeRanges.processData(preprocess);
		RelSizeRanges.setLog();
		RelSizeRanges.setLogRanges();
		
		Iterator<String> itr = RelSize.iterator();

		while (itr.hasNext()) {
			String relsize = itr.next();
			double value = RelSizeRanges.getRange(relsize);
			output.addRow(relsize, value);
		}
		
	}
	
	
	
	/**
	 * @return the relSizeRanges
	 */
	public StdDevRanges getRelSizeRanges() {
		return RelSizeRanges;
	}

	public void printSummary() {	
		System.out.println(output);
	}
		
}
