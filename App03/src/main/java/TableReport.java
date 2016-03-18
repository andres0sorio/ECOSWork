/** Copyright or License
 *
 */


import java.util.ArrayList;

/**
 * Package: uniandes.ecos.psp1
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

	/** Constructor - takes a title for this table
	 * @param name
	 */
	public TableReport(String name) {
		super();
		this.name = name;
		rows = new ArrayList<String>();
	}

	/**  Add Row based on a pair string int value
	 * @param description
	 * @param value
	 */
	public void addRow(String description, Integer value) {

		String add_row = description + " " + value + "\n";
		rows.add(add_row);
	}

	/** Add Row based on a pair string double value
	 * @param description
	 * @param x
	 */
	public void addRow(String description, Double x) {
		String value = String.format("%.4f", x);
		String add_row = description + " " + value + "\n";
		rows.add(add_row);
	}

	/** Add Row based on a string
	 * @param string
	 */
	public void addRow(String string) {
		String add_row = string + "\n";
		rows.add(add_row);
	}

	/** Generate a nice HTML table
	 * @return
	 */
	public String toHTML() {

		String table_report = "<table><tr>";
		table_report += "<td> TableReport " + name + "</td></tr><tr>";
		for (int nrow = 0; nrow < rows.size(); ++nrow) {
			String item = rows.get(nrow).replace("\n", "");
			table_report += "<td>" + item + "</td>";
			table_report += "</tr><tr>";
		}
		table_report += "</table>";
		return table_report;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		String table_report = "\n";
		table_report += "TableReport " + name + '\n';
		for (int nrow = 0; nrow < rows.size(); ++nrow) {
			table_report += rows.get(nrow);
		}
		return table_report;
	}

}
