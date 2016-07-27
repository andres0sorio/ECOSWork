
/** Copyright or License
 *
 */

package edu.uniandes.ecos.codeaholics.main;

import java.io.File;
import java.util.ArrayList;

/**
 * Package: uniandes.ecos.psp
 *
 * Class: FileChaser FileChaser.java
 * 
 * Original Author: @author AOSORIO
 * 
 * Description: Given a directory PATH, return all files that are in this
 * directory according to some criteria
 * 
 * Implementation: Returns an ArrayList of string, containing file paths
 * 
 * Created: Feb 15, 2016 3:47:08 PM
 * 
 */
public class FileUtilities {

	private String root;
	private ArrayList<String> allFiles;
	File[] files;
	
	public FileUtilities(String given_root) {
		super();
		this.root = given_root;
		allFiles = new ArrayList<String>();
	}

	public String getRoot() {
		return root;
	}

	public ArrayList<String> getAllFiles() {
		return allFiles;
	}

	public void processRoot() {

		//Add here an exeption
		allFiles.clear();
		files = new File(root).listFiles();
		getSourceFiles(files, allFiles, "");
		
		for(int i=0; i<allFiles.size();++i){
			System.out.println(allFiles.get(i));
		}

	}

	public void getSourceFiles(File[] current_files, ArrayList<String> output, String fileExt) {
				
		for (File file : current_files) {
			if (file.isDirectory()) {
				//System.out.println("Directory: " + file.getName());
				getSourceFiles(file.listFiles(), output, fileExt); // Calls same
																	// method
																	// again.
			} else {
				//if (file.getName()) {
					//System.out.println("File: " + file.getName());
					output.add(file.getAbsolutePath());
				//}
			}
		}
	}
	
	
}
