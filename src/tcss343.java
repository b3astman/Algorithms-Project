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
		
		System.out.println();
//		dynamic(rentals);
		
		// get a set of the possible rental combinations
		int n = rentals.length;
		ArrayList<Integer> theList = new ArrayList<Integer>();
		for (int i = 1; i < n-1; i++) {
			theList.add(i);
		}
		ArrayList<ArrayList<Integer>> list = powerset(theList);
		for (ArrayList<Integer> num : list) {
			System.out.println(num);
		}
	}

	/**
	 * Creates a set of all possible combinations of rentals.
	 * 
	 * @param theList - a list of indexes
	 * @return - the set.
	 */
	public static ArrayList<ArrayList<Integer>> powerset(ArrayList<Integer> theList) {
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
	 * runs in O(X).
	 * 
	 * @param rentals - array of rental costs.
	 */
	private static void bruteFore(int[][] rentals) {

	}

	/**
	 * The Divide and Conquer approach to solving the rental problem. This
	 * solution runs in O(X).
	 * 
	 * @param rentals - array of rental costs.
	 */
	private static void divideAndConquer(int[][] rentals) {

	}

	/**
	 * The dynamic programming approach to solving the rentals problem. This
	 * solution runs in O(x).
	 * 
	 * @param rentals - array of rental costs.
	 */
	private static void dynamic(int[][] rentals) {
		for (int i = 0; i < rentals.length; i++) {
			System.out.println(Arrays.toString(rentals[i]));
		}
		System.out.println();

		int n = 4;
		int[] b = new int[n];
		for (int i = 1; i < n; i++) {
			b[i] = Integer.MAX_VALUE;
			for (int j = i - 1; j >= 0; j--) {
				b[i] = Math.min(rentals[j][i] + b[j], b[i]);
				System.out.println(b[i]);
			}
		}
		System.out.println();

		for (int i = 0; i < b.length; i++) {
			System.out.print(b[i]);
		}
	}
}