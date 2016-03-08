import java.util.Arrays;
import java.util.Random;

/*
 * TCSS343 Algos HW4 
 */


public class challenge {
	
	private static Random rand = new Random();
	private static boolean[][] field;

	public static void main(String[] args) {
		createMatrix(4);
		System.out.println(Arrays.deepToString(field));
	}
	
	public static void createMatrix(int n) {
		field = new boolean[n][n];
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				field[row][col] = rand.nextBoolean();
			}
		}
	}
	
	/**
	 * The dynamic programming solution to the Field and Stones problem.
	 * This solution runs in O(n^2).
	 */
	public static void dynamic() {
		int largestSquare = 0;
		
	}

}
