/** Copyright or License
 *
 */

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Package: 
 *
 * Class: TableReport TableReport.java
 * 
 * Original Author: @author AOSORIO
 * 
 * Description: Simple table reported
 * 
 * Implementation: Throws a formated string
 *
 * Created: Feb 15, 2016 8:41:25 AM
 * 
 */
public class TableReport {

	String name;
	ArrayList<String> rows;

	/**
	 * Constructor - takes a title for this table
	 * 
	 * @param name give a name for this Table
	 */
	public TableReport(String name) {
		super();
		this.name = name;
		rows = new ArrayList<String>();
	}

	/**
	 * Add Row based on a pair string int value
	 * 
	 * @param description usually the leftmost column is a description
	 * @param value corresponding value for this column (integet)
	 */
	public void addRow(String description, Integer value) {

		String add_row = description + "," + value + "\n";
		rows.add(add_row);
	}

	/**
	 * Add Row based on a pair string double value
	 * 
	 * @param description usually the leftmost column is a description
	 * @param x corresponding value for this column
	 */
	public void addRow(String description, Double x) {
		
		DecimalFormat df = new DecimalFormat("0.000000000");
		df.setRoundingMode(RoundingMode.DOWN);				
		String add_row = description + "," + df.format(x) + "\n";
		rows.add(add_row);
	}

	/**
	 * Add Row based on a string
	 * 
	 * @param string entire row in a single string
	 */
	public void addRow(String string) {
		String add_row = string + "\n";
		rows.add(add_row);
	}

	/**
	 * Generate a nice HTML table
	 * 
	 * @return string that encodes HTML table
	 */
	public String toHTML() {

		String table_report = "<style>#customers th {background-color:#4CAF50;color:white;}";
		table_report += "#customers td, #customers th {border: 1px solid #ddd;}</style>";
		table_report += "<table id=\"customers\"><tr>";
		table_report += "<th> TableReport " + name + "</th></tr><tr>";
		for (int nrow = 0; nrow < rows.size(); ++nrow) {
			String item = rows.get(nrow).replace("\n", "");
			String[] items = item.split(",");
			for (String td : items) {
				table_report += "<td>" + td + "</td>";
			}
			table_report += "</tr><tr>";
		}
		table_report += "</table>";
		return table_report;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		String table_report = "\n";
		table_report += "TableReport " + name + "\n";
		for (int nrow = 0; nrow < rows.size(); ++nrow) {
			table_report += rows.get(nrow);
		}
		return table_report;
	}

	/** Add column to current table
	 * @param arrayList the column to add at the right most of the table
	 */
	public void addColumn( ArrayList<Double> arrayList ) {
		
		DecimalFormat df = new DecimalFormat("0.000000000");
		df.setRoundingMode(RoundingMode.DOWN);	
		
		if ( rows.size() != arrayList.size() )
			return;
		
		Iterator<String> itr = rows.iterator();
		int index = 0;
		while( itr.hasNext()) {
			String extra = "," + df.format(arrayList.get(index)) + "\n";
			String extended = itr.next().replace("\n", "").concat(extra);
			rows.set(index,extended);
			index += 1;
		}
		
	}

}
