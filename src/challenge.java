/*
 * TCSS343 Algos HW4 
 */

/**
 * Program finds a solution to the Field and Stones problem.
 * 
 * @author Antonio Alvillar, Gabriel Houle, Bethany Eastman
 * @version 03/08/2016
 */
public class challenge {
	
	private static boolean[][] field;

	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int m = Integer.parseInt(args[1]);
		field = new boolean[n][n];
		for (int i = 1; i <= m * 2; i += 2) {
			int x = Integer.parseInt(args[i + 1]);
			int y = Integer.parseInt(args[i + 2]);
			field[x][y] = true;
		}
		printArray();
		bruteForce();
		dynamic();
	}
	
	public static void printArray() {
		for (int i = 0; i <= field.length - 1; i++) {
			for (int j = 0; j <= field.length - 1; j++) {
				if (field[i][j] == false) {
					System.out.print("F ");
				} else {
					System.out.print("T ");
				}
			}
			System.out.println();
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
				if (max[i][j] >= largestSquare) {
					largestSquare = max[i][j];
					maxI = i;
					maxJ = j;
				}
			}
		}
		
		System.out.println();
		System.out.println("Dynamic Programming Solution:");
		System.out.println("Size: " + largestSquare);
		System.out.println("Position: (" + maxI + ", " + maxJ + ")");
	}
	
	public static boolean checkTarget(int x, int y, int size) {
		boolean result = true;
		
		if (y + size > field.length || x + size > field.length) {
			result = false;
		}
		
		for (int i = x; (i < size + x) && result; i++) {
			for (int j = y; j < size + y; j++) {
				if (field[i][j]) {
					result = false;
				}
			}
		}
		return result;
	}
	
	/**
	 * The brute force solution to the Field and Stones problem.
	 * This solution runs in O(n^3).
	 */
	public static void bruteForce() {
		int maxI = 0;
		int maxJ = 0;
		int maxSize = 0;
		boolean result = true;
		
		for (int i = 0; i <= field.length; i++) {
			for (int j = 0; j <= field.length; j++) {
				result = true;
				for (int s = 0; (s <= field.length) && result; s++) {
					if (checkTarget(i, j, s)) {
						if (s > maxSize) {
							maxI = i;
							maxJ = j;
							maxSize = s;
						}
					} else {
						result = false;
					}
				}
			}
		}
		
		System.out.println();
		System.out.println("Brute Force Solution:");
		System.out.println("Size: " + maxSize);
		System.out.println("Position: (" + maxI + ", " + maxJ + ")");
	}
}
