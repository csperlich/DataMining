package maniccam.neuralnets.app;

import static maniccam.neuralnets.app.NeuralDriverTools.runSimulation;

import java.io.IOException;

import maniccam.data.LoanDataNeuralNetworkDataConverter;
import maniccam.neuralnets.NeuralNetwork;

public class LoanDataNeuralNetworkDriver {
	public static void main(String[] args) throws IOException {
		NeuralNetwork network = new NeuralNetwork(new LoanDataNeuralNetworkDataConverter());

		String trainingFile = "program2_data/part2/train2";
		String outFile = "program2_data/part2/output2";
		String validationFile = "program2_data/part2/validate2";
		String testFile = "program2_data/part2/test2";

		network.loadTrainingData(trainingFile);
		// 4539
		runSimulation(network, 7, 10000, 4539, .5, outFile, validationFile, testFile);
	}
}
