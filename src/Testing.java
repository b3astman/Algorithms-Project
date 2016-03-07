import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;

/**
 * This method will generate random arrays necessary to test the boat problem.
 * 
 * @author Gabriel Houle
 */

public class Testing {
	
	private static Random myRand;

	/**
	 * Driver method to create the random arrays and output them to files.
	 * 
	 * @param theArgs Command line input.
	 */
	public static void main(String[] theArgs) {
		int[][][] tests = new int[5][][];
		int[][] test = createArray(100);
		
		tests[0] = test;
		test = createArray(200);
		tests[1] = test;
		test = createArray(400);
		tests[2] = test;
		test = createArray(600);
		tests[3] = test;
		test = createArray(800);
		tests[4] = test;

		File[] files = new File[5];
		
		for(int i = 0; i < 1; i++) {
			String file = String.format("input%d.txt", i + 1);
			files[i] = new File(file);
			
			outputArray(tests[i], files[i]);
		}
	}
	
	/**
	 * Creates the array with random elements, with increasing values as
	 * the table goes from left to right.
	 * 
	 * @param n size of the generate price matrix.
	 * @return the Generated array.
	 */
	public static int[][] createArray(int n) {
		myRand = new Random();
		int[][] result = new int[n][n];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					result[i][j] = 0;
				} else if (i > j) {
					result[i][j] = -1;
				} else {
					result[i][j] = (myRand.nextInt(25) + 1) + result[i][j - 1];
				} 
			}
		}
		return result;
	}
	
	/**
	 * Outputs the array to a given text file.
	 * 
	 * @param theArr The array being printed to the file.
	 * @param outputFile The file being written to.
	 */
	public static void outputArray(int[][] theArr, File outputFile) {
		PrintStream file;
		try {
			file = new PrintStream(outputFile);
			
			for (int i = 0; i < theArr.length - 1; i++) {
				if (theArr[i][0] == -1) {
					file.printf("%3s", "NA");
				} else {
					file.printf("%3d", theArr[i][0]);
				}
				for (int j = 1; j < theArr.length - 1; j++) {
					if (theArr[i][j] == -1) {
						file.print("  NA");
					} else {
						file.printf(" %3d", theArr[i][j]);
					}
				}
				file.println("");
			}
			file.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}
}
