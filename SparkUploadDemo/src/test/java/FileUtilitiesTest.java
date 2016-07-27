import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edu.uniandes.ecos.codeaholics.main.FileUtilities;

public class FileUtilitiesTest {

	public static final String TMP_TEST_PATH = "D:/Test/Temp/";
	public static FileUtilities fileUtilities;

	@Test
	public void test() {

		fileUtilities = new FileUtilities(TMP_TEST_PATH);

		ArrayList<String> currentFiles = new ArrayList<String>();
		fileUtilities.processRoot();
		currentFiles = fileUtilities.getAllFiles();
		System.out.println("current files in dir: " + currentFiles.size());
		assertEquals(1, currentFiles.size());
		
	}

}
