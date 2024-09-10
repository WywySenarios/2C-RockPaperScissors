package map;

import java.util.ArrayList;

/**
 * The Map learns how to output certain results given certain inputs!
 * 
 * @author Eric Zhu & Wywy
 */
public class Map {
	private boolean mapped = false;
	private int no_simultaneous_inputs;
	private int no_inputs;
	private int no_outcomes;
	/*
	 * Here is a list of connections this map has. Each index contains an array of
	 * different possible inputs that lead to an outcome ID of the index. e.g. index
	 * 2 contains an array that contains [2,4,8], the inputs required to get an
	 * output of 2. index 2 will look like this: [[2,4,8]]
	 */
	private ArrayList<ArrayList<ArrayList<Integer>>> connections = new ArrayList<ArrayList<ArrayList<Integer>>>();
	private ArrayList<ArrayList<Integer>> mappedConnections = new ArrayList<ArrayList<Integer>>();
	private int multiplicationFactor; // this variables ensures UIDs when no_simultaneous_inputs is greater than 10.

	/**
	 * @param no_simultaneous_inputs_ The number of inputs given at the same time
	 *                                for a result to appear.
	 * @param no_inputs,              The number of unique inputs this game may
	 *                                receive
	 * @param no_outcomes             The number of outcomes that this game can
	 *                                result in
	 */
	public Map(int no_simultaneous_inputs_, int no_inputs_, int no_outcomes_) {
		no_inputs = no_inputs_;
		no_outcomes = no_outcomes_;
		no_simultaneous_inputs = no_simultaneous_inputs_;
		for (int i = 0; i < no_outcomes; i++) {
			connections.add(new ArrayList<ArrayList<Integer>>());
		}

		multiplicationFactor = (int) Math.pow(10, no_inputs / 10 + 1);
	}

	/**
	 * Create a connection between inputs and an outcome.
	 * 
	 * @param inputs
	 * @param outcome
	 * @return Returns true if it successfully connects inputs to a result.
	 */
	public boolean connect(ArrayList<Integer> inputs, int outcome) {
		if (inputs.size() != no_simultaneous_inputs) { // ensure valid input
			return false;
		}

		try { // register input
			connections.get(outcome).add(inputs);
		} catch (Exception e) { // catch any errors (IDK what might happen)
			e.printStackTrace();
			return false;
		}

		return true; // success!!!
	}

	/**
	 * This function prepares the "Map" for playing!
	 * 
	 * @return Returns true if the connections are possible to map with the lowest
	 *         number of if statements or not. map() will successfully run even if
	 *         playing the map will exceed the 32-bit integer limit.
	 */
	public boolean map() {
		/*
		 * The premise of a "Map" is that an array of inputs can be merged together in a
		 * giant integer. For example, [10,9,3] turns into 100903. This ensures each
		 * different array of inputs gets a different number---a UID! These giant
		 * numbers can then be used as zeroes in a polynomial function---whenever a
		 * valid input comes in, we should get 0! Unfortunately, this method is
		 * vulnerable to the 32-bit integer limit. Very sad. "Map"s can also be improved
		 * by using bit chains instead of using integers.
		 */
		// temporary variables:
		int currentInteger;
		@SuppressWarnings("unused")
		int temp;
		boolean dupe = false;
		ArrayList<Integer> checked = new ArrayList<Integer>();

		for (ArrayList<ArrayList<Integer>> a : connections) { // "a": each outcome
			checked = new ArrayList<Integer>();
			for (ArrayList<Integer> b : a) { // "b": each valid input combination
				dupe = false;
				currentInteger = 0;
				temp = 0;
				for (int c : b) { // "c": each valid input
					currentInteger += c;
					currentInteger *= multiplicationFactor;
					temp++;
				}
				currentInteger /= multiplicationFactor;

				for (int i : checked) {
					if (currentInteger == i) {
						dupe = true;
						break;
					}
				}

				if (dupe) {
					break;
				} else {
					checked.add(currentInteger);
				}
			}

			// add in all the zeroes needed

			mappedConnections.add(checked);
		}

		return mapped = true; // success!!!
	}

	/**
	 * This function returns the outcome ID corresponding with the inputs, as mapped
	 * by the connect() and map() functions. In the case that map() has not been
	 * successfully run yet, this will return -1. This function returns a default
	 * value of 0. This function only returns -1 if it is unable to find the correct
	 * answer. This function also returns -2 if the input given does not have enough
	 * arguments.
	 * 
	 * @param input
	 * @return
	 */
	public int play(ArrayList<Integer> input) {
		int convertedInput = 0; // convert
		for (int i = 0; i < no_simultaneous_inputs; i++) {
			try {
				convertedInput += input.get(i);
			} catch (Exception e) {
				return -2;
			}
			convertedInput *= multiplicationFactor;
		}

		convertedInput /= multiplicationFactor;

		double currentNumber;

		if (mapped) { // if the map has been set up,
			for (int a = 1; a < no_outcomes; a++) { // loop through every possible input
				currentNumber = 1.0;
				for (int b : mappedConnections.get(a)) {
					try { // multiply all factors to find if there is a match (zeroes of a polynomial
							// equation)
						currentNumber *= (convertedInput - b);
					} catch (Exception e) { // catch integer limit exceeded errors
						System.out.println("Calculation failed---Integer limit exceeded.");
						return -1;
					}
				}

				if (currentNumber == 0) { // any matches will result in a 0
					return a;
				}
			}
			return 0; // assume the default outcome if an outcome has not been found yet.
		} else { // the Map has not been set up yet and therefore cannot find an answer.
			return -1;
		}
	}
}
