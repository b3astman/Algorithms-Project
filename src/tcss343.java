/*
 * TCSS343 Algos HW4 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Program finds a solution that computes the cheapest sequence of rentals
 * taking you from post 1 all the way down to post n.
 * 
 * @author Antonio Alvillar, Gabriel Houle, Bethany Eastman
 * @version 03/01/2016
 */
public class tcss343 {
	
	private static Random myRand;

	/**
	 * Reads in rental costs and finds the minimum costs.
	 * There is a line that can be uncommented that will run the test file creation method.
	 * 
	 * @param args - the text file of rental costs.
	 */
	public static void main(String[] args) {
		Scanner input = null;
		Scanner input2 = null;
		
//		testing(); // uncomment this to run the test array creation functions.

		try {
			input = new Scanner(new File(args[0]));
			input2 = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int numCols = 0;
		while (input.hasNextLine()) { // get number of columns / rows in file
			input.nextLine();
			numCols++;
		}

		int[][] rentals = new int[numCols][numCols];

		// initialize rentals array with values from file
		for (int row = 0; row < numCols; row++) {
			for (int col = 0; col < numCols; col++) {
				if (input2.hasNextInt()) {
					rentals[row][col] = input2.nextInt();
				} else {
					input2.next();
					rentals[row][col] = Integer.MAX_VALUE;
				}
			}
		}

		long start = System.currentTimeMillis();
		System.out.printf("Dynamic min: %d\n", dynamic(rentals));
		// runs the dynamic solution and prints answer
																
		long end = System.currentTimeMillis();
		System.out.println("Runtime to complete Dynamic: " + (end - start)
				+ " in Milliseconds.");
		System.out.println();

		start = System.currentTimeMillis();
		ArrayList<Integer> brute = bruteForce(rentals);
		if (brute.get(0) != -1) {
			System.out.println("Brute Force min: "
					+ calculateCost(brute, rentals));
			end = System.currentTimeMillis();
			System.out.println("Brute Force Path Taken: " + 0 + brute
					+ (rentals.length - 1));
			System.out.println("Runtime to complete Brute Force: "
					+ (end - start) + " in Milliseconds.");
			System.out.println();
		} else {
			System.out
					.println("Table is too large! Brute Force takes too long!");
		}

		start = System.currentTimeMillis();
		ArrayList<Integer> result = divideAndConquer(rentals);
		if (result.get(0) != -1) {
			end = System.currentTimeMillis();
			System.out.printf("Divide and Conquer min: %d\n",
					calculateCost(result, rentals));
			System.out.println("Divide and Conquer calculated path: " + 0
					+ result + (rentals.length - 1));
			System.out.println("Runtime to complete Divide and Conquer: "
					+ (end - start) + " in Milliseconds.");
			System.out.println();
		} else {
			System.out
					.println("Table is too large! Divide and Conquer takes too long!");
		}
	}
	
	/**
	 * Problem 3.5
	 * 
	 * This class will create 5 files input1-5.txt that will contains 2D arrays that
	 * represent the canoe problem.
	 */
	public static void testing() {
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
		
		for(int i = 0; i < 5; i++) {//set i < 5 to create all five files, less than 5 will save time creating.
			String file = String.format("input%d.txt", i + 1);
			files[i] = new File(file);
			
			outputArray(tests[i], files[i]);
		}
		
		// This is to create small test files. 
		outputArray(createArray(14), new File("smallInput.txt"));
		outputArray(createArray(10), new File("smallerInput.txt"));
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
					file.printf("%4s", "NA");
				} else {
					file.printf("%4d", theArr[i][0]);
				}
				for (int j = 1; j < theArr.length - 1; j++) {
					if (theArr[i][j] == -1) {
						file.print("   NA");
					} else {
						file.printf(" %4d", theArr[i][j]);
					}
				}
				file.println("");
			}
			file.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}

	/**
	 * The brute force approach to solving the rentals problem. This solution
	 * runs in O(2^n). This solution generates all possible permutations of
	 * paths that are possible and finds the minimum cost of all possible paths.
	 * Returns -1 if the list will take too long to calculate. (n > 20)
	 * 
	 * @param rentals
	 *            - array of rental costs.
	 * @param indexList
	 *            - a List of indexes that is used to create the permutations.
	 * @return the minimum cost of a trip from start to finish.
	 */
	public static ArrayList<Integer> bruteForce(int[][] rentals) {
		ArrayList<Integer> returnSub = new ArrayList<Integer>();

		/**
		 * Stops the brute force method from running if there are too many
		 * elements in the array.
		 */
		if (rentals.length >= 20) {
			returnSub.add(-1);
			return returnSub;
		}

		ArrayList<Integer> indexList = indexSet(rentals);

		int min = rentals[0][rentals.length - 1];

		ArrayList<ArrayList<Integer>> ps = new ArrayList<ArrayList<Integer>>();
		ps.add(new ArrayList<Integer>());
		for (Integer item : indexList) {
			ArrayList<ArrayList<Integer>> newPs = new ArrayList<ArrayList<Integer>>();
			for (ArrayList<Integer> subset : ps) {
				newPs.add(subset);
				ArrayList<Integer> newSubset = new ArrayList<Integer>(subset);
				newSubset.add(item);
				newPs.add(newSubset);

				int sum = calculateCost(newSubset, rentals);
				if (sum < min) {
					returnSub = newSubset;
				}
				min = Math.min(sum, min);

			}
			ps = newPs;
		}
		return returnSub;
	}

	/**
	 * The Divide and Conquer approach to solving the rental problem. This
	 * solution runs in O(X).
	 * 
	 * @param rentals - array of rental costs.
	 */
	public static ArrayList<Integer> divideAndConquer(int[][] rentals) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		if (rentals.length >= 14) {
			result.add(-1);
			return result;
		}

		result = recursion(indexSet(rentals), Integer.MAX_VALUE, rentals);

		if (calculateCost(result, rentals) > calculateCost(indexSet(rentals),
				rentals)) {
			result = indexSet(rentals);
		}

		return result;

	}
	
	/**
	 * The dynamic programming approach to solving the rentals problem. This
	 * solution runs in O(x).
	 * 
	 * @param rentals
	 *            - array of rental costs.
	 * @return the minimum cost to finish a trip from start to finish.
	 */
	public static int dynamic(int[][] rentals) {
		int n = rentals.length;
		int[] b = new int[n];
		for (int i = 1; i < n; i++) {
			b[i] = Integer.MAX_VALUE;
			for (int j = i - 1; j >= 0; j--) {
				b[i] = Math.min(rentals[j][i] + b[j], b[i]);
			}
		}
		return b[n - 1];
	}

	private static ArrayList<Integer> recursion(ArrayList<Integer> theSet,
			int min, int[][] theRentals) {
		ArrayList<Integer> result = new ArrayList<>();
		ArrayList<Integer> minSet = new ArrayList<>();
		int minsetCost = Integer.MAX_VALUE;

		for (int i = 0; i < theSet.size(); i++) { // recursion stops with empty set.
			result = new ArrayList<>();
			result.addAll(theSet);
			result.remove(i);
			int cost = calculateCost(result, theRentals);
			ArrayList<Integer> temp = recursion(result, cost, theRentals);
			if (calculateCost(temp, theRentals) < minsetCost) {
				minSet = temp;
			}
			minsetCost = calculateCost(minSet, theRentals);

			if (minsetCost <= min && minsetCost <= cost) {
				result = minSet;
				min = minsetCost;
			} else if (cost >= min) {
				result.clear();
				result.addAll(theSet);
			} else {
				min = cost;
			}
		}
		return result;
	}

	/**
	 * Returns a set of all indexes in the given array.
	 * 
	 * @param theRentals
	 *            Array being measured.
	 * @return ArrayList with values i(1...n) such that i is an index in set
	 *         theRentals
	 */
	private static ArrayList<Integer> indexSet(int[][] theRentals) {

		int n = theRentals.length;
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		for (int i = 1; i < n - 1; i++) {
			indexList.add(i);
		}

		return indexList;
	}

	/**
	 * This method will calculate the cost of a given path for a given 2d array
	 * of costs.
	 * 
	 * @param theSet
	 *            the path being considered.
	 * @param theRentals
	 *            the costs of canoe rentals.
	 * @return the cost of the given route.
	 */
	private static int calculateCost(ArrayList<Integer> theSet,
			int[][] theRentals) {
		int sum = 0;
		int j = 0;

		for (int i = 0; i < theSet.size(); i++) {
			sum += theRentals[j][theSet.get(i)];
			j = theSet.get(i);
		}
		sum += theRentals[j][theRentals.length - 1];
		return sum;
	}


}