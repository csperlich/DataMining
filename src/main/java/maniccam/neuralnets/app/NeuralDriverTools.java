package maniccam.neuralnets.app;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import maniccam.neuralnets.NeuralNetwork;

public class NeuralDriverTools {
	public static void runSimulation(NeuralNetwork network, int hiddenNodes, int iterations, int seedValue,
			double learningRate, String outFile, String validationFile, String testFile) throws IOException {

		PrintWriter fileOut = new PrintWriter(new FileWriter(outFile, true));

		network.setParameters(hiddenNodes, iterations, seedValue, learningRate);
		network.train();

		fileOut.println("-----------------");
		System.out.println("PRINTING CLASSIFICATIONS OF \"program2_data/part2/test1\" to " + outFile);
		network.testData(testFile, outFile);

		System.out.println("PRINTING NETWORK PARAMETERS TO " + outFile);
		fileOut.println(network.getParamString());

		System.out.println("PRININTG TRAINING ERROR AND VALIDATION ERROR TO " + outFile);
		fileOut.println(
				"TRAINING ERROR USING ROOT MEAN SQUARED ERROR -> " + String.format("%8.4f", network.trainingError()));
		fileOut.println("VALIDATION ERROR USING MEAN ROOT MEAN SQUARED ERROR -> "
				+ String.format("%8.4f", network.validate(validationFile)));

		fileOut.close();
		System.out.println();

	}
}
