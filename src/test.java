import java.util.Scanner;

@SuppressWarnings("unused")
public class test {

	public static void main(String[] args) {
//		Scanner wywy = new Scanner(System.in);
//		while (wywy.nextLine() != "no") {
//			System.out.println(System.nanoTime() - System.nanoTime());
//		}
//		
//		wywy.close();
		
		int[][] inputs = {{1,1}, {1,2}, {1,3}, {2,1}, {2,2}, {2,3}, {3,1}, {3,2}, {3,3}};
		String output = "";
		for (int[] i : inputs) {
			output += ", " + noComparisonRPS(i);
		}
		
		System.out.println(output.substring(2));
		
	}
	
	public static long noComparisonRPS(int[] inputs) {
		/*
		 * Polynomial equation that satisfies the following points:
		 * (1,1,0) (2,2,0) (3,3,0)
		 * (1,3,1) (2,1,1) (3,2,1)
		 * (1,2,2) (2,3,2) (3,1,2)
		 */
		float x = inputs[0];
		float z = inputs[1];

		return Math.round((x - z) *(x + -2.66069 * z) * (x + -0.753293 * z) * (x + -2.96691 * z) * (x + -0.751818 * z) * (x + -1.48948 * z) * (x + -0.33313 * z) * (x + -0.751626 * z) * (x + -1.05309 * z));
	}

}
