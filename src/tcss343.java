import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Program finds a solution that computes the cheapest sequence of rentals taking you from post
 * 1 all the way down to post n.
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
//		Scanner input = null;
//		
//		try {
//			input = new Scanner(new File(args[0]));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		while (input.hasNextLine()) {
//			while (input.hasNextInt()) {
//				System.out.print(input.nextInt() + ", ");
//			}
//			System.out.println();
//			while (input.hasNext() && !input.hasNextInt()){
//				System.out.print(input.next() + ", ");
//			}
//		}
		
		int[][] rentals = { {0,2,3,7},//test case #1
							{Integer.MAX_VALUE,0,2,4},
							{Integer.MAX_VALUE,Integer.MAX_VALUE,0,2},
							{Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE, 0} };
		
//		int[][] rentals = { {0,15,39,45,48},//test case #2
//				{Integer.MAX_VALUE,0,1,11,23},
//				{Integer.MAX_VALUE,Integer.MAX_VALUE,0,21,34},
//				{Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE, 0, 7},
//				{Integer.MAX_VALUE, Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE, 0} };
		
		System.out.printf("dynamic min: %d\n", dynamic(rentals));//runs the dynamic solution and prints answer
		
		
		int n = rentals.length;
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		for (int i = 1; i < n-1; i++) {
			indexList.add(i);
		}
		System.out.printf("brute Force min: %d\n", bruteForce(indexList, rentals));
	}
	
	/**
	 * The brute force approach to solving the rentals problem. This 
	 * solution runs in O(2^n). This solution generates all possible permutations of
	 * paths that are possible and finds the minimum cost of all possible paths.
	 * 
	 * @param rentals - array of rental costs.
	 * @param indexList - a List of indexes that is used to create the permutations.
	 * @return the minimum cost of a trip from start to finish.
	 */
	public static int bruteForce(ArrayList<Integer> indexList, int[][] rentals) {
		
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
			for (int j = i-1; j >= 0; j--) {
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