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
		System.out.printf("Dynamic min: %d\n", dynamic(rentals));//runs the dynamic solution and prints answer	

		int n = rentals.length;
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		for (int i = 1; i < n-1; i++) {
			indexList.add(i);
		}
		
		int brute = bruteForce(indexList, rentals);
		if (brute != -1) {
			System.out.printf("Brute Force min: %d\n", brute);
		} else {
			System.out.println("Table is too large! Brute Force takes too long!");
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
	 * The brute force approach to solving the rentals problem. This 
	 * solution runs in O(2^n). This solution generates all possible permutations of
	 * paths that are possible and finds the minimum cost of all possible paths.
	 * Returns -1 if the list will take too long to calculate. (n > 29)
	 * 
	 * @param rentals - array of rental costs.
	 * @param indexList - a List of indexes that is used to create the permutations.
	 * @return the minimum cost of a trip from start to finish.
	 */
	public static int bruteForce(ArrayList<Integer> indexList, int[][] rentals) {
		
		/**
		 * Stops the brute force method from running if there are 
		 * too many elements in the array.
		 */
		if (indexList.size() > 29) {
			return -1;
		}
		
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
		      
//		      System.out.println(newSubset);
		      
			  int sum = 0;
			  int j = 0;
		      
		      for (int i = 0; i < newSubset.size(); i++) {//this calculates the 
		    	  sum += rentals[j][newSubset.get(i)];	  //cost of this permutation
		    	  j = newSubset.get(i);
		      }
		      sum += rentals[j][rentals.length - 1];
		      min = Math.min(sum, min);
		      
		    }
		    ps = newPs;
		  }
		  
//   		  for (ArrayList<Integer> num : ps) {
//			System.out.println(num);
//		  }
		  
		  return min;
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
	 * @return the minimum cost to finish a trip from start to finish.
	 */

	private static int dynamic(int[][] rentals) {
		
//		for (int i = 0; i < rentals.length; i++) {//prints array passed to this function
//			System.out.println(Arrays.toString(rentals[i]));
//		}
//		System.out.println();
		
		int n = rentals.length;
		int[] b = new int[n];
		for (int i = 1; i < n; i++) {
			b[i] = Integer.MAX_VALUE;
			for (int j = i - 1; j >= 0; j--) {
				b[i] = Math.min(rentals[j][i] + b[j], b[i]);
				//System.out.println(b[i]);
			}
		}
		//System.out.println();
		
//		for (int i = 0; i < b.length; i++) {
//			System.out.printf("%d, ", b[i]);
//		}	
//		System.out.println();
		return b[n - 1];
	}
}