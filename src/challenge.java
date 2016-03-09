import java.util.Arrays;
import java.util.Random;

/*
 * TCSS343 Algos HW4 
 */


public class challenge {
	
	private static Random rand = new Random();
	private static boolean[][] field;

	public static void main(String[] args) {
		createMatrix(6);
		for (boolean [] theArr : field) {
			System.out.println(Arrays.toString(theArr));
		}
		//System.out.println(Arrays.deepToString(field));
		bruteForce();
	}
	
	public static void createMatrix(int n) {
		field = new boolean[n][n];
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				field[row][col] = rand.nextBoolean();
			}
		}
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
		System.out.println("I: " + maxI + " J: " + maxJ + " maxSize: " + maxSize);
	}
}
