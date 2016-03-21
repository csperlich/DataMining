package program2.neuralnets.app;

import static program2.neuralnets.app.NeuralNetworkDriverTools.runTests;

import java.io.IOException;

import program2.neuralnets.NeuralNetwork;
import program2.neuralnets.testerrorcomputers.NeuralTestErrorComputer;

public class LoanDataNeuralNetworkDriver {
	public static void main(String[] args) throws IOException {
		System.out.println("Running driver program for Program2-Part2-Subpart3: "
				+ "\n\tNeural Network Loan Data Risk Classification\n");

		NeuralNetwork network = new NeuralNetwork(
				NeuralTestErrorComputer.ComputerType.SingleOutputClassificationTestComputer);

		String trainingFile = "program2_data/part2/train2";
		String outFile = "program2_data/part2/output2";
		String validationFile = "program2_data/part2/validate2";
		String testFile = "program2_data/part2/test2";

		network.loadTrainingData(trainingFile);
		network.setValidationTrace(true);

		runTests(network, 12, 15000, 4539, .5, outFile, validationFile, testFile);
	}
}
