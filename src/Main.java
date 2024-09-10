import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import map.Map;

/**
 * Last year, David and I figured out how to make rock paper scissors work with
 * two comparisons (there are three different cases---a win, a loss, and a
 * draw). This time I'm making a program to generalize this strategy, which,
 * although not efficient, completely optimizes for the amount of if statements
 * the computer uses. This program in specific tests for runtime of all rock
 * paper scissors test cases.
 * 
 * rock = 1, paper = 2, scissors = 3.
 * 
 * @author Eric Zhu & Wywy
 */
public class Main {
	public static Map rockPaperScissors;
	//public static final String FILEPATHPREFIX = "output";
	// filename: type + "FILEPATHPREFIX" + .txt
	public static final String RUNTIMEFILEPATHPREFIX = "runtime";
	// filename: type + "RUNTIMEFILEPATHPREFIX" + .txt
	
	
	@SuppressWarnings("unused")
	private static final GUI gui = new GUI();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// rock = 1, paper = 2, scissors = 3
		// please note that this algorithm is severely limited by the 32-bit integer
		// limit.
		rockPaperScissors = new Map(2, 3, 3);
		// 0 --- tie
		// 1 --- win by p1
		// 2 --- win by p2

		// we are only required to map when p1 or p2 wins.
		// thanks to
		// https://stackoverflow.com/questions/21696784/how-to-declare-an-arraylist-with-values,
		// I know how to make an ArrayList.
		// possible inputs that lead to p1 winning:
		rockPaperScissors.connect(new ArrayList<Integer>(Arrays.asList(2, 1)), 1);
		rockPaperScissors.connect(new ArrayList<Integer>(Arrays.asList(3, 2)), 1);
		rockPaperScissors.connect(new ArrayList<Integer>(Arrays.asList(1, 3)), 1);

		// possible inputs that lead to p2 winning:
		rockPaperScissors.connect(new ArrayList<Integer>(Arrays.asList(1, 2)), 2);
		rockPaperScissors.connect(new ArrayList<Integer>(Arrays.asList(2, 3)), 2);
		rockPaperScissors.connect(new ArrayList<Integer>(Arrays.asList(3, 1)), 2);

		if (rockPaperScissors.map()) {
			
		} else {
			// tell the user that the Map did not successfully initialize and set up, and
			// abort the program.
			System.out.println("Unsuccessfully mapped.");
		}
	}

	/**
	 * This is a standard rock paper scissors function.
	 * 
	 * @param args "args" is a size two array that contains player one and player
	 *             two's inputs.
	 * @return This function returns the outcome of the game.
	 */
	private static int standardRockPaperScissors(ArrayList<Integer> args) {
		if (args.get(0) == args.get(1)) { // tie
			return 0;
		} else if (args.get(0) == 1) { // p1 rock
			if (args.get(1) == 2) { // p2 paper
				return 2;
			} else {
				return 1;
			}
		} else if (args.get(0) == 2) { // p1 paper
			if (args.get(1) == 1) { // p2 rock
				return 1;
			} else {
				return 2;
			}
		} else { // p1 scissors
			if (args.get(1) == 2) { // p2 paper
				return 1;
			} else {
				return 2;
			}
		}
	}

	/**
	 * This rock paper scissors function is optimized using a quadratic equation
	 * which cuts down on the number of comparisons needed to be done.
	 * 
	 * @param args "args" is a size two array that contains player one and player
	 *             two's inputs.
	 * @return This function returns the outcome of the game.
	 */
	private static int optimizedRockPaperScissors(ArrayList<Integer> args) {
		int p2 = args.get(1);

		if (args.get(0) == p2) { // tie
			return 0;
		} else if (args.get(0) == (-3 * p2 * p2 + 11 * p2 - 4) / 2) { // P1 wins
			// the above quadratic equation converts p2 into a value equal to p1 when P1 wins.
			// this means that the quadratic equation has points (1,3), (2,1), and (3,2)
			return 1;
		} else {
			return 2;
		}
	}
	
	/**
	 * This rock paper scissors function does not use any if statements.
	 * 
	 * @param args "args" is a size two array that contains player one and player
	 *             two's inputs.
	 * @return This function returns the outcome of the game.
	 */
	private static int noComparisonRPS(ArrayList<Integer> args) {
		/*
		 * Polynomial equation that satisfies the following points:
		 * (1,1,0) (2,2,0) (3,3,0)
		 * (1,3,1) (2,1,1) (3,2,1)
		 * (1,2,2) (2,3,2) (3,1,2)
		 */
		float x = args.get(0);
		float z = args.get(1);
		
		return (int) Math.round((x - z) *(x + -2.66069 * z) * (x + -0.753293 * z) * (x + -2.96691 * z) * (x + -0.751818 * z) * (x + -1.48948 * z) * (x + -0.33313 * z) * (x + -0.751626 * z) * (x + -1.05309 * z));
	}

	/**
	 * This function plays rock paper scissors using a "map"
	 * 
	 * @param args  Player inputs
	 * @param times The amount of times rock paper scissors should be played
	 * @return Returns an array with [expected execution result, runtime]
	 */
	private static long[] playMap(ArrayList<Integer> args, int times) {
		long[] output = { rockPaperScissors.play(args), 0 };
		
		// avoid dead-code optimization
		double run;
		if (times > 1000) {
			Random random = new Random();
			run = times * random.nextDouble(1, 1 + 0.001);
		} else {
			run = times;
		}

		long startTime = System.nanoTime();
		for (int i = 0; i < run; i++) {
			rockPaperScissors.play(args);
		}

		long endTime = System.nanoTime();
		output[1] = endTime - startTime;
		return output;
	}

	/**
	 * This function plays rock paper scissors using a normal algorithm.
	 * 
	 * @param args  Player inputs
	 * @param times The amount of times rock paper scissors should be played
	 * @return Returns an array with [expected execution result, runtime]
	 */
	public static long[] playStandard(ArrayList<Integer> args, int times) {
		long[] output = { standardRockPaperScissors(args), 0 };
		
		// avoid dead-code optimization
		double run;
		if (times > 1000) {
			Random random = new Random();
			run = times * random.nextDouble(1, 1 + 0.001);
		} else {
			run = times;
		}

		long startTime = System.nanoTime();
		for (int i = 0; i < run; i++) {
			if (args.get(0) == args.get(1)) { // tie
				continue;
			} else if (args.get(0) == 1) { // p1 rock
				if (args.get(1) == 2) { // p2 paper
					continue;
				} else {
					continue;
				}
			} else if (args.get(0) == 2) { // p1 paper
				if (args.get(1) == 1) { // p2 rock
					continue;
				} else {
					continue;
				}
			} else { // p1 scissors
				if (args.get(1) == 2) { // p2 paper
					continue;
				} else {
					continue;
				}
			}
		}

		long endTime = System.nanoTime();
		output[1] = endTime - startTime;
		return output;
	}

	/**
	 * This function plays rock paper scissors using an optimized algorithm.
	 * 
	 * @param args  Player inputs
	 * @param times The amount of times rock paper scissors should be played
	 * @return Returns an array with [expected execution result, runtime]
	 */
	private static long[] playOptimized(ArrayList<Integer> args, int times) {
		long[] output = { optimizedRockPaperScissors(args), 0 };
		
		// avoid dead-code optimization
		double run;
		if (times > 1000) {
			Random random = new Random();
			run = times * random.nextDouble(1, 1 + 0.001);
		} else {
			run = times;
		}
		
		long startTime = System.nanoTime();

		for (int i = 0; i < run; i++) {
			int p2 = args.get(1);

			if (args.get(0) == p2) { // tie
				continue;
			} else if (args.get(0) == (-3 * p2 * p2 + 11 * p2 - 4) / 2) { // P1 wins
				// the above quadratic equation converts p2 into a value equal to p1 when P1 wins.
				// this means that the quadratic equation has points (1,3), (2,1), and (3,2)
				continue;
			} else {
				continue;
			}
		}

		long endTime = System.nanoTime();
		output[1] = endTime - startTime;
		// System.out.println(startTime + ", " + endTime + ", " + output[1] + ", " + times); // DEBUG
		return output;
	}
	
	/**
	 * This function plays rock paper scissors using an optimized algorithm.
	 * 
	 * @param args  Player inputs
	 * @param times The amount of times rock paper scissors should be played
	 * @return Returns an array with [expected execution result, runtime]
	 */
	private static long[] playNoComparisons(ArrayList<Integer> args, int times) {
		long[] output = { noComparisonRPS(args), 0 };
		
		// avoid dead-code optimization
		double run;
		if (times > 1000) {
			Random random = new Random();
			run = times * random.nextDouble(1, 1 + 0.001);
		} else {
			run = times;
		}
		
		long startTime = System.nanoTime();

		for (int i = 0; i < run; i++) {
			float x = args.get(0);
			float z = args.get(1);
			
			Math.round((x - z) *(x + -2.66069 * z) * (x + -0.753293 * z) * (x + -2.96691 * z) * (x + -0.751818 * z) * (x + -1.48948 * z) * (x + -0.33313 * z) * (x + -0.751626 * z) * (x + -1.05309 * z));
		}

		long endTime = System.nanoTime();
		output[1] = endTime - startTime;
		// System.out.println(startTime + ", " + endTime + ", " + output[1] + ", " + times); // DEBUG
		return output;
	}

	/**
	 * This function plays rock paper scissors using a map "times" times.
	 * 
	 * @param times
	 * @return This function returns a array of [execution result, runtime] for each
	 *         test case (chronological per x coordinate)
	 */
	public static long[][] playAllMapTestCases(int times) {
		long[][] output = { playMap(new ArrayList<Integer>(Arrays.asList(1, 1)), times),
				playMap(new ArrayList<Integer>(Arrays.asList(1, 2)), times),
				playMap(new ArrayList<Integer>(Arrays.asList(1, 3)), times),
				playMap(new ArrayList<Integer>(Arrays.asList(2, 1)), times),
				playMap(new ArrayList<Integer>(Arrays.asList(2, 2)), times),
				playMap(new ArrayList<Integer>(Arrays.asList(2, 3)), times),
				playMap(new ArrayList<Integer>(Arrays.asList(3, 1)), times),
				playMap(new ArrayList<Integer>(Arrays.asList(3, 2)), times),
				playMap(new ArrayList<Integer>(Arrays.asList(3, 3)), times) };
		return output;
	}

	/**
	 * This function plays rock paper scissors using a normal algorithm "times"
	 * times.
	 * 
	 * @param times
	 * @return This function returns a array of [execution result, runtime] for each
	 *         test case (chronological per x coordinate)
	 */
	public static long[][] playAllStandardTestCases(int times) {
		long[][] output = { playStandard(new ArrayList<Integer>(Arrays.asList(1, 1)), times),
				playStandard(new ArrayList<Integer>(Arrays.asList(1, 2)), times),
				playStandard(new ArrayList<Integer>(Arrays.asList(1, 3)), times),
				playStandard(new ArrayList<Integer>(Arrays.asList(2, 1)), times),
				playStandard(new ArrayList<Integer>(Arrays.asList(2, 2)), times),
				playStandard(new ArrayList<Integer>(Arrays.asList(2, 3)), times),
				playStandard(new ArrayList<Integer>(Arrays.asList(3, 1)), times),
				playStandard(new ArrayList<Integer>(Arrays.asList(3, 2)), times),
				playStandard(new ArrayList<Integer>(Arrays.asList(3, 3)), times) };
		return output;
	}

	/**
	 * This function plays rock paper scissors using an optimized algorithm "times"
	 * times.
	 * 
	 * @param times
	 * @return This function returns a array of [execution result, runtime] for each
	 *         test case (chronological per x coordinate)
	 */
	public static long[][] playAllOptimizedTestCases(int times) {
		long[][] output = { playOptimized(new ArrayList<Integer>(Arrays.asList(1, 1)), times),
				playOptimized(new ArrayList<Integer>(Arrays.asList(1, 2)), times),
				playOptimized(new ArrayList<Integer>(Arrays.asList(1, 3)), times),
				playOptimized(new ArrayList<Integer>(Arrays.asList(2, 1)), times),
				playOptimized(new ArrayList<Integer>(Arrays.asList(2, 2)), times),
				playOptimized(new ArrayList<Integer>(Arrays.asList(2, 3)), times),
				playOptimized(new ArrayList<Integer>(Arrays.asList(3, 1)), times),
				playOptimized(new ArrayList<Integer>(Arrays.asList(3, 2)), times),
				playOptimized(new ArrayList<Integer>(Arrays.asList(3, 3)), times) };
		return output;
	}
	
	/**
	 * This function plays rock paper scissors using no comparisons "times"
	 * times.
	 * 
	 * @param times
	 * @return This function returns a array of [execution result, runtime] for each
	 *         test case (chronological per x coordinate)
	 */
	public static long[][] playAllNoComparisonsTestCases(int times) {
		long[][] output = { playNoComparisons(new ArrayList<Integer>(Arrays.asList(1, 1)), times),
				playNoComparisons(new ArrayList<Integer>(Arrays.asList(1, 2)), times),
				playNoComparisons(new ArrayList<Integer>(Arrays.asList(1, 3)), times),
				playNoComparisons(new ArrayList<Integer>(Arrays.asList(2, 1)), times),
				playNoComparisons(new ArrayList<Integer>(Arrays.asList(2, 2)), times),
				playNoComparisons(new ArrayList<Integer>(Arrays.asList(2, 3)), times),
				playNoComparisons(new ArrayList<Integer>(Arrays.asList(3, 1)), times),
				playNoComparisons(new ArrayList<Integer>(Arrays.asList(3, 2)), times),
				playNoComparisons(new ArrayList<Integer>(Arrays.asList(3, 3)), times) };
		return output;
	}
}
