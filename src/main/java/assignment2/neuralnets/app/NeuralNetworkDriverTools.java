package assignment2.neuralnets.app;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import assignment2.neuralnets.NeuralNetwork;

public class NeuralNetworkDriverTools {

	/*
	 * Runs training error and validation error tests on a neural network.
	 * Prints network parameters and test results to outFile.
	 */
	public static void runTests(NeuralNetwork network, int hiddenNodes, int iterations, int seedValue,
			double learningRate, String outFile, String validationFile, String testFile) throws IOException {

		network.setParameters(hiddenNodes, iterations, seedValue, learningRate);
		network.train();

		System.out.println("PRINTING CLASSIFICATIONS OF " + testFile + " to " + outFile);
		network.testData(testFile, outFile);

		PrintWriter fileOut = new PrintWriter(new FileWriter(outFile, true));
		fileOut.println("-----------------");

		System.out.println("PRINTING NETWORK PARAMETERS TO " + outFile);
		fileOut.println(network.getParamString());

		System.out.println("PRININTG TRAINING ERROR AND VALIDATION ERROR TO " + outFile);
		fileOut.println("TRAINING ERROR USING " + network.getTestErrorString() + " -> "
				+ String.format("%.2f%%", (network.trainingError() * 100)));
		fileOut.println("VALIDATION ERROR USING " + network.getTestErrorString() + " -> "
				+ String.format("%.2f%%", (network.validate(validationFile) * 100)));

		fileOut.close();
		System.out.println();
	}
}
