import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);

		// Ask users
		System.out.println("Player 1 (r = 1, p = 2, s = 3): ");
		int p1 = console.nextInt();

		System.out.println("Player 2 (r = 1, p = 2, s = 3): ");
		int p2 = console.nextInt();

		/*
		 * Rock = 1 Paper = 2 Scissors = 3
		 * 
		 * Here is the diagram: (the tiles are defined by equation [P1 - P2])
		 *     R  P  S P2
		 *     1  2  3
		 * R 1 0 -1 -2
		 * P 2 1  0 -1
		 * S 3 2  1  0
		 * P1
		 * 
		 * All ties are 0 and all 0's are ties (IMPORTANT!) All 1's are wins for P1 All
		 * -2's are wins for P1
		 * 
		 * Assume P1 won unless they did not.
		 * 
		 * Cases in which p1 did not win: Tie, P2 win
		 * 
		 * CASE: Tie
		 * 
		 * Ties happen when the same thing are selected, so P1 = P2.
		 * 
		 * We just check to see if they are the same (0's can ONLY be ties).
		 * 
		 * CASE: P2 win
		 * 
		 * If, for player one, rock = 1, paper = 2, scissors = 3, and for player two,
		 * rock = 2, paper = 3, scissors = 1, P1 - P2 = 0 is when P1 would win.
		 * 
		 * This is why it works:
		 * 
		 * Here is the original diagram
		 * 
		 *     R  P  S P2
		 *     1  2  3
		 * R 1 0 -1 -2
		 * P 2 1  0 -1
		 * S 3 2  1  0
		 * P1
		 * 
		 * Here is a modified diagram, where R = 2, P = 3, and S = 1 for P2:
		 * 
		 *      R  P  S P2
		 *      2  3  1
		 * R 1 -1 -2  0
		 * P 2  0 -1  1
		 * S 3  1  0  2
		 * P1
		 * 
		 * All P1 wins are 0's and all 0's are P1 wins!
		 * 
		 * If P = 1, S = 2, and R = 3 for P2, then we can assume that if P1 = P2, P1
		 * wins.
		 * 
		 * This means that we can account for ties in one comparison (if P1 = P2), and
		 * P1 wins in one comparison (P1 = modified P2).
		 * 
		 * However, the user can only input 1 for rock, 2 for paper, and 3 for scissors
		 * (it'd be awkward if the numbers weren't consistent between players).
		 * 
		 * Fortunately, we can use a special quadratic equation to convert 1 to 2, 2 to 3, and 3
		 * to 1.
		 * 
		 * 
		 * This is the most efficient way of doing rock paper scissors, as there are three cases to account for.
		 * One case can be accounted for if the others aren't,
		 * the other two have to be checked.
		 * 
		 * That means that 2 comparisons is the least amount you can get. GG!
		 */

		// Assume p2 wins;
		String msg = "P2 wins!";

		// here is the special quadratic equation
		int modifiedp2 = (-3 * p2 * p2 + 11 * p2 - 4) / 2;

		if (p1 == p2) { // comparison one; tie
			msg = "It is a tie!";
		} else if (p1 == modifiedp2) { // comparison two; P1 wins
			msg = "P1 won!";
		}
		
		// print out what case it is
		System.out.println(msg);
	}

}
