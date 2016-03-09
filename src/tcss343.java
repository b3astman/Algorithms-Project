/*
 * TCSS343 Algos HW4 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Program finds a solution that computes the cheapest sequence of rentals
 * taking you from post 1 all the way down to post n.
 * 
 * @author Antonio Alvillar, Gabriel Houle, Bethany Eastman
 * @version 03/01/2016
 */
public class tcss343 {

	/**
	 * Reads in rental costs and finds the minimum costs.
	 * 
	 * @param args - the text file of rental costs.
	 */
	public static void main(String[] args) {
		Scanner input = null;
		Scanner input2 = null;

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
		int[][] test = (dynamic(rentals));
		for (int[] arr : test) {
			System.out.println(Arrays.toString(arr));
		}
		
//		System.out.printf("Dynamic min: %d\n", dynamic(rentals));
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
	 * Creates a set of all possible combinations of rentals.
	 * 
	 * @param theList
	 *            - a list of indexes
	 * @return - the set.
	 */
	public static ArrayList<ArrayList<Integer>> powerset(
			ArrayList<Integer> theList) {
		ArrayList<ArrayList<Integer>> ps = new ArrayList<ArrayList<Integer>>();
		ps.add(new ArrayList<Integer>());
		for (Integer item : theList) {
			ArrayList<ArrayList<Integer>> newPs = new ArrayList<ArrayList<Integer>>();
			for (ArrayList<Integer> subset : ps) {
				newPs.add(subset);
				ArrayList<Integer> newSubset = new ArrayList<Integer>(subset);
				newSubset.add(item);
				newPs.add(newSubset);
			}
			ps = newPs;
		}
		return ps;
	}

	/**
	 * The brute force approach to solving the rentals problem. This solution
	 * runs in O(2^n). This solution generates all possible permutations of
	 * paths that are possible and finds the minimum cost of all possible paths.
	 * Returns -1 if the list will take too long to calculate. (n > 29)
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
		if (rentals.length >= 14) {
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

	/**
	 * The dynamic programming approach to solving the rentals problem. This
	 * solution runs in O(x).
	 * 
	 * @param rentals
	 *            - array of rental costs.
	 * @return the minimum cost to finish a trip from start to finish.
	 */

	public static int[][] dynamic(int[][] rentals) {
		int n = rentals.length;
		int[][] b = new int[n][n];
		
		for (int i = 1; i < n; i++) {
			for (int j = i - 1; j >= 0; j--) {
				b[i][j] = Integer.MAX_VALUE;
				if (i == j) {
					b[i][j] = 0;
				} else if (j - 1 < 0) {
					b[i][j] = Math.min(rentals[i][j], b[i][j]);
				} else {
					b[i][j] = Math.min(rentals[j][i] + b[i - 1][j - 1], rentals[i][j] + b[j][0]);

				}
			}
		}
		return b;
		
		
		
		
//		int n = rentals.length;
//		int[] b = new int[n];
//		for (int i = 1; i < n; i++) {
//			b[i] = Integer.MAX_VALUE;
//			for (int j = i - 1; j >= 0; j--) {
//				b[i] = Math.min(rentals[j][i] + b[j], b[i]);
//			}
//		}
//		return b[n - 1];
	}
}