package program2.neuralnets.app;

import static program2.neuralnets.app.NeuralNetworkDriverTools.runTests;

import java.io.IOException;

import program2.neuralnets.NeuralNetwork;
import program2.neuralnets.testerrorcomputers.NeuralTestErrorComputer;

public class SAndPDataNeuralNetworkDriver {
	public static void main(String[] args) throws IOException {
		System.out.println("Running driver program for Program2-Part3-Subpart2: "
				+ "\n\tNeural Network Regression On S&P Daily Percentage Change Stock Price Data\n");

		NeuralNetwork network = new NeuralNetwork(
				NeuralTestErrorComputer.ComputerType.DailyAverageReturnOnMoneyInvestedComputer);

		String trainingFile = "program2_data/s&p/training_jan2014-dec2014_bounded";
		String outFile = "program2_data/s&p/output";
		String validationFile = "program2_data/s&p/validate_jan2015-mar2015_bounded";
		String testFile = "program2_data/s&p/test_apr2015_bounded";

		network.loadTrainingData(trainingFile);
		network.setValidationTrace(true);

		runTests(network, 6, 10000, 3126, .8, outFile, validationFile, testFile);
	}
}
