import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * This class serves as a GUI for my ultimate RPS program! It allows you to play
 * RPS normally AND track the runtime of a *couple* different algorithms
 * 
 * @author Eric Zhu & Wywy
 */
public class GUI extends javax.swing.JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton mapButton = new JButton("Play Rock Paper Scissors using a map.");
	private JButton standardButton = new JButton("Play Rock Paper Scissors using a basic algorithm.");
	private JButton optimizedButton = new JButton("Play Rock Paper Scissors using a 2-comparison algorithm.");
	private JButton noComparisonButton = new JButton("Play Rock Paper Scissors without comparisons");
	private JButton[] testButtons = { mapButton, standardButton, optimizedButton, noComparisonButton };
	private JLabel testPrompt1 = new JLabel("Try out some different algorithms for Rock Paper Scissors!");
	private JLabel testPrompt2 = new JLabel();
	private JLabel testPrompt3 = new JLabel();
	private JLabel testPrompt4 = new JLabel();
	private JLabel testPrompt5 = new JLabel();
	private JLabel[] testPrompts = { testPrompt1, testPrompt2, testPrompt3, testPrompt4, testPrompt5 };

	private JLabel rpsPrompt = new JLabel("Play some RPS with the buttons above!");
	private JButton rockButton = new JButton("Rock");
	private JButton paperButton = new JButton("Paper");
	private JButton scissorsButton = new JButton("Scissors");
	private JButton[] playButtons = { rockButton, paperButton, scissorsButton };
	private ArrayList<Integer> lastRPSInputs = new ArrayList<Integer>();

	private JTextField timesInput = new JTextField("100");
	private JButton clearFileOutput = new JButton("Clear file outputs");

	public static final int DEFAULTTIMES = 100;

	public GUI() {
		// setup variables
		lastRPSInputs.add(-1);
		lastRPSInputs.add(-1);

		// configure window
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setTitle("The ULTIMATE - 1 RPS program.");
		this.setPreferredSize(new java.awt.Dimension(1280, 720));

		// add buttons & labels
		mapButton.setActionCommand("map");
		standardButton.setActionCommand("standard");
		optimizedButton.setActionCommand("optimized");
		noComparisonButton.setActionCommand("noComparisons");

		rockButton.setActionCommand("rock");
		paperButton.setActionCommand("paper");
		scissorsButton.setActionCommand("scissors");

		for (int i = 0; i < playButtons.length; i++) {
			testButtons[i].setBounds(0, 100 * i, 1280 / 2, 100);
			playButtons[i].setBounds(1280 - (100 * (1 + i)) - 80, 0, 100, 100);
			testButtons[i].addActionListener(this);
			playButtons[i].addActionListener(this);
			testButtons[i].setVisible(true);
			playButtons[i].setVisible(true);
			this.add(testButtons[i]);
			this.add(playButtons[i]);
		}
		for (int i = 3; i < testButtons.length; i++) {
			testButtons[i].setBounds(0, 100 * 3, 1280 / 2, 100);
			testButtons[i].addActionListener(this);
			testButtons[i].setVisible(true);
			this.add(testButtons[i]);
		}

		for (int i = 0; i < testPrompts.length; i++) {
			testPrompts[i].setBounds(0, testButtons.length * 100 + 50 * i, 500, 50);
			testPrompts[i].setVisible(true);
			this.add(testPrompts[i]);
		}

		rpsPrompt.setBounds(1280 - 80 - 300, 100, 300, 50);
		rpsPrompt.setVisible(true);
		this.add(this.rpsPrompt);

		// configure the "timesInput" text field
		timesInput.setBounds(1280 - 80 - 500, 720 - 100 - 25, 500, 100);
		timesInput.setVisible(true);
		this.add(timesInput);

		// configure the "clearFileOutput" button
		clearFileOutput.setActionCommand("clearOutput");
		clearFileOutput.addActionListener(this);
		clearFileOutput.setBounds(1280 - 80 - 500 - 100, 720 - 100 - 25, 100, 100);
		clearFileOutput.setVisible(true);
		this.add(clearFileOutput);

		// push changes & make this window visible.
		this.setVisible(true);
		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent inputAction) {
		long[][] results;
		long normalRPSResults;
		String command = inputAction.getActionCommand().toLowerCase();
		switch (command) {
		case "map":
			System.out.println("Playing Map.");
			testPrompts[0].setText("Playing Map.");
			results = Main.playAllMapTestCases(getTimes());
			break;
		case "standard":
			System.out.println("Playing Standard");
			testPrompts[0].setText("Playing Standard.");
			results = Main.playAllStandardTestCases(getTimes());
			break;
		case "optimized":
			System.out.println("Playing Optimized");
			testPrompts[0].setText("Playing Optimized.");
			results = Main.playAllOptimizedTestCases(getTimes());
			break;
		case "nocomparisons":
			System.out.println("Playing No Comparisons");
			testPrompts[0].setText("Playing No Comparisons");
			results = Main.playAllNoComparisonsTestCases(getTimes());
			break;
		case "rock", "paper", "scissors":
			if (lastRPSInputs.get(0) <= 0) {
				normalRPSUserInput(command, 0);
			} else {
				normalRPSUserInput(command, 1);
				normalRPSResults = Main.playStandard(lastRPSInputs, 1)[0];
				switch ((int) normalRPSResults) {
				case 0:
					rpsPrompt.setText(rpsPrompt.getText() + " | TIE!!!");
					break;
				case 1:
					rpsPrompt.setText(rpsPrompt.getText() + " | P1 wins!!!");
					break;
				case 2:
					rpsPrompt.setText(rpsPrompt.getText() + " | P2 wins!!!");
					break;
				}

				lastRPSInputs.set(0, -1);
				lastRPSInputs.set(1, -1);
			}
			return;
		case "clearoutput":
			try {
				FileWriter clearMapOutput = new FileWriter("map" + Main.RUNTIMEFILEPATHPREFIX + ".txt", false);
				clearMapOutput.write("");
				clearMapOutput.close();
			} catch (IOException e) {
				System.out.println("Failed to clear map output.");
			}

			try {
				FileWriter clearStandardOutput = new FileWriter("standard" + Main.RUNTIMEFILEPATHPREFIX + ".txt",
						false);
				clearStandardOutput.write("");
				clearStandardOutput.close();
			} catch (IOException e) {
				System.out.println("Failed to clear standard output.");
			}

			try {
				FileWriter clearOptimizedOutput = new FileWriter("optimized" + Main.RUNTIMEFILEPATHPREFIX + ".txt",
						false);
				clearOptimizedOutput.write("");
				clearOptimizedOutput.close();
			} catch (IOException e) {
				System.out.println("Failed to clear optimized output.");
			}

			try {
				FileWriter clearNoComparisonsOutput = new FileWriter("nocomparisons" + Main.RUNTIMEFILEPATHPREFIX + ".txt",
						false);
				clearNoComparisonsOutput.write("");
				clearNoComparisonsOutput.close();
			} catch (IOException e) {
				System.out.println("Failed to clear no comparisons output.");
			}
			return;
		default:
			System.out.println(command + " input was not recognized as a valid input.");
			return;
		}

		// calculate average run time (per "times" cases) and output
		long avgruntime = 0;
		String fileoutput = "Execution Results: ";
		for (int i = 0; i < results.length - 1; i++) {
			fileoutput += results[i][0] + ", ";
			avgruntime += results[i][1];
		}
		fileoutput += results[results.length - 1][0];
		testPrompts[1].setText(fileoutput);

		fileoutput += "\nExpected Results: 0, 2, 1, 1, 0, 2, 2, 1, 0\n\n";
		testPrompts[2].setText("\nExpected Results: 0, 2, 1, 1, 0, 2, 2, 1, 0\n\n");

		avgruntime += results[results.length - 1][1];
		avgruntime /= results.length;
		// avgruntime /= 9;

		fileoutput += "Average runtime per " + getTimes() + " cases: " + avgruntime;
		testPrompts[3].setText("Average runtime per " + getTimes() + " cases: " + avgruntime);

		System.out.println(fileoutput);

		try {
			FileWriter runtimeFile = new FileWriter(command + Main.RUNTIMEFILEPATHPREFIX + ".txt", true);

			runtimeFile.write("\n" + avgruntime);
			runtimeFile.close();
			System.out.println("Runtime successfully stored.\n\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function parses normal RPS input (top right of the screen) and updates
	 * the GUI to reflect that
	 * 
	 * @param command
	 * @param index
	 */
	private void normalRPSUserInput(String command, int index) {
		int output;
		switch (command) {
		case "rock":
			output = 1;
			break;
		case "paper":
			output = 2;
			break;
		case "scissors":
			output = 3;
			break;
		default:
			output = -1;
			break;
		}

		lastRPSInputs.set(index, output);

		if (index == 0) {
			rpsPrompt.setText("Player 1: " + command);
		} else {
			rpsPrompt.setText(rpsPrompt.getText() + " | Player 2: " + command);
		}
	}

	/**
	 * Get the number of times the user wants to run each test case.
	 * 
	 * @return Defaults to 100.
	 */
	private int getTimes() {
		try {
			return Integer.parseInt(timesInput.getText());
		} catch (Exception e) { // invalid input is given:
			// e.printStackTrace();
			return DEFAULTTIMES;
		}
	}

//	private String createNewFile(String name) {
//		File output = new File(name + ".txt");
//		int counter = 0;
//		try {
//			while (!output.createNewFile()) {
//				output = new File(name + ++counter + ".txt");
//			}
//		} catch (IOException e) {
//			return null;
//		}
//		return name + counter + ".txt";
//	}

}
