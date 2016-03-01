import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class tcss343 {

	public static void main(String[] args) {
		Scanner input = null;
		
		try {
			input = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (input.hasNextLine()) {
			while (input.hasNextInt()) {
				System.out.print(input.nextInt() + ", ");
			}
			System.out.println();
			while (input.hasNext() && !input.hasNextInt()){
				System.out.print(input.next() + ", ");
			}
		}
		
		
		int[][] rentals = { {0,2,3,7},
							{Integer.MAX_VALUE,0,2,4},
							{Integer.MAX_VALUE,Integer.MAX_VALUE,0,2},
							{Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE, 0} };
		System.out.println();
		dynamic(rentals);
	}

	private static void dynamic(int[][] rentals) {
		for (int i = 0; i < rentals.length; i++) {
			System.out.println(Arrays.toString(rentals[i]));
		}
		System.out.println();
		
		int n = 4;
		int[] b = new int[n];
		for (int i = 1; i < n; i++) {
			b[i] = Integer.MAX_VALUE;
			for (int j = i-1; j >= 0; j--) {
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