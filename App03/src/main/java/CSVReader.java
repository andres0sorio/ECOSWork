/** Copyright or License
 *
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Package: uniandes.ecos.psp
 *
 * Class: PairValues PairValues.java
 * 
 * Original Author: @author AOSORIO
 * 
 * Description: This is a simple CSV reader. Input must be in the form of columns, each
 * column separated by a , (comma). Given we have to work out data in columns in
 * terms of Linked Lists there is a method to provide the data in such form.
 * 
 * Implementation: [Notes on implementation]
 *
 * Created: Feb 29, 2016 4:11:30 AM
 * 
 */

public class CSVReader {

	private String inputFile = "";

	BufferedReader buffer = null;

	String cvsSplitBy = ",";
	int nrow = 0;
	int ncol = 0;

	ArrayList<ArrayList<Double>> table = new ArrayList<ArrayList<Double>>();

	/** Constructor - gets the input file string
	 * @param infile
	 */
	public CSVReader(String infile) {
		inputFile = infile;
	}

	/** This is the core of the class: opens and reads the file
	 *  data is stored and converted in a matrix
	 * @throws FileNotFoundException
	 */
	public void readFile() throws FileNotFoundException {

		try {

			buffer = new BufferedReader(new FileReader(inputFile));

			String line;

			while ((line = buffer.readLine()) != null) {

				String[] data = line.split(cvsSplitBy);

				ncol = data.length;

				for (int col = 0; col < ncol; ++col) {

					if (nrow == 0) {
						ArrayList<Double> column = new ArrayList<Double>();
						table.add(column);
					}
					try {
						Double value = Double.parseDouble(data[col]);
						table.get(col).add(value);
					} catch (NumberFormatException e) {
						System.out.println("Not a valid number! skipping.");
					}
				}

				nrow++;

			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found, please check!");
			throw new FileNotFoundException();
		} catch (IOException e) {
			System.out.println("IO error, please check!");
		} finally {
			if (buffer != null) {
				try {
					if (nrow == 0)
						System.out.println("Current file is empty. Nothing to do.");
					else
						System.out.println("End of file reached, success.");
					buffer.close();
				} catch (IOException e) {
					System.out.println("IO error at closing file.");
				}
			}
		}
	}

	/**
	 * @return table: 
	 */
	public ArrayList<ArrayList<Double>> getTable() {
		return table;
	}

	/**
	 * @param title ;
	 * @return formated Data (separated by tab)
	 */
	public TableReport getTable(String title) {
		TableReport formatedData = new TableReport(title);
		for( int n=0; n < table.size(); ++n) {
			String currentRow = "";
			for( int m=0; m < table.get(n).size(); ++m){
				currentRow += table.get(n).get(m) + "\t";
			}
			formatedData.addRow(currentRow);
		}
		return formatedData;
	}
	
	/**
	 * @param column
	 * @return
	 */
	public ArrayList<Double> getSingleColumn(int column) {
		ArrayList<Double> selectedColumn = table.get(column);
		return selectedColumn;
	}

	/**
	 * @param column
	 * @return
	 */
	public LinkedList<Double> getLinkedList(int column) {

		LinkedList<Double> singleColumn = new LinkedList<Double>();
		ArrayList<Double> data = this.getSingleColumn(column);

		// Convert the arrays list to a linked list
		Iterator<Double> itr = data.iterator();

		while (itr.hasNext()) {
			double value = itr.next();
			singleColumn.add(value);
		}

		return singleColumn;

	}

	/**
	 * @param column1
	 * @param column2
	 * @return
	 */
	public LinkedList<PairValues<Double,Double>> getLinkedList(int column1, int column2) {

		LinkedList<PairValues<Double,Double>> pairOfColumns = new LinkedList<PairValues<Double,Double>>();
		
		ArrayList<Double> data1 = this.getSingleColumn(column1);
		ArrayList<Double> data2 = this.getSingleColumn(column2);

		// Convert the arrays list to a linked list
		int maxRows = data1.size();

		for(int iVal = 0; iVal < maxRows; ++iVal ) {
			
			PairValues<Double,Double> xy = new PairValues<Double,Double>(0.0,0.0);
			xy.setX(data1.get(iVal));
			xy.setY(data2.get(iVal));
			pairOfColumns.add( xy );
			
		}
		return pairOfColumns;
	}
	
}
