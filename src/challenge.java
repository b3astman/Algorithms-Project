/*
 * TCSS343 Algos HW4 
 */
import java.util.Random;

/**
 * Program finds a solution to the Field and Stones problem.
 * 
 * @author Antonio Alvillar, Gabriel Houle, Bethany Eastman
 * @version 03/08/2016
 */
public class challenge {
	
	private static Random rand = new Random();
	private static boolean[][] field;

	public static void main(String[] args) {
		createMatrix(8);
//		System.out.println(Arrays.deepToString(field));
		dynamic();
	}
	
	public static void createMatrix(int n) {
		field = new boolean[n][n];
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				if (rand.nextBoolean()) {
					field[row][col] = rand.nextBoolean();
				}
			}
		}
	}
	
	/**
	 * The dynamic programming solution to the Field and Stones problem.
	 * This solution runs in O(n^2).
	 */
	public static void dynamic() {
		int largestSquare = 0;
		int maxI = 0;
		int maxJ = 0;
		int n = field.length; // size of n
		int max[][] = new int[n][n];
		
		// get maximum squares that are free for each index
		for (int i = n - 1; i >= 0; i--) {
			for (int j = n - 1; j >= 0; j--) {
				// get maximum at a[i][j]
				if (field[i][j] == true) {
					max[i][j] = 0;
				} else if (i == n - 1 || j == n - 1) {
					max[i][j] = 1;
				} else {
					if (max[i + 1][j] == 0 || max[i][j + 1] == 0 || max[i + 1][j + 1] == 0) {
						// if any adjacent are zero, set max to one
						max[i][j] = 1;
					} else {
						// if adjacent are not zero, set max to minimum plus one
						max[i][j] = Math.min(max[i + 1][j], Math.min(max[i][j + 1], max[i + 1][j + 1])) + 1;
					}
				}
			}
		}
		
		// get index and maximum at that index
		for (int i = n - 1; i >= 0; i--) {
			for (int j = n - 1; j >= 0; j--) {
				if (max[i][j] > largestSquare) {
					largestSquare = max[i][j];
					maxI = i;
					maxJ = j;
				}
			}
		}
		
		// print array for debug
		for (int i = 0; i <= n - 1; i++) {
			for (int j = 0; j <= n - 1; j++) {
				System.out.print(max[i][j] + " ");
				
			}
			System.out.println();
		}
		
		System.out.println("Largest square " + largestSquare);
		System.out.println("Max index: (" + maxI + ", " + maxJ + ")");
	}

}
