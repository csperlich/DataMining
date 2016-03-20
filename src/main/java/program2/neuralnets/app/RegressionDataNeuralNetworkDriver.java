package program2.neuralnets.app;

import static program2.neuralnets.app.NeuralNetworkDriverTools.runTests;

import java.io.IOException;

import program2.neuralnets.NeuralNetwork;
import program2.neuralnets.testerrorcomputers.NeuralTestErrorComputer;

public class RegressionDataNeuralNetworkDriver {
	public static void main(String[] args) throws IOException {
		System.out.println("Running driver program for Program2-Part3-Subpart1: "
				+ "\n\tNeural Network Regression on Various Function Data Inputs\n");

		String folder = "program2_data/part3/";

		int bestHiddenNodes = 12;
		int bestIterations = 10000;
		int bestSeedValue = 4539;
		double bestLearningRate = .5;

		buildAndRun(bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate, folder + "train1",
				folder + "validate1", folder + "test1", folder + "output1");

		buildAndRun(bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate, folder + "train2",
				folder + "validate2", folder + "test2", folder + "output2");

		buildAndRun(bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate, folder + "train3",
				folder + "validate3", folder + "test3", folder + "output3");

		buildAndRun(bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate, folder + "train4",
				folder + "validate4", folder + "test4", folder + "output4");

		buildAndRun(bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate, folder + "train5",
				folder + "validate5", folder + "test5", folder + "output5");

	}

	private static void buildAndRun(int hiddenNodes, int iterations, int seedValue, double learningRate,
			String trainingFile, String validationFile, String testFile, String outFile) throws IOException {
		NeuralNetwork network = new NeuralNetwork(NeuralTestErrorComputer.ComputerType.RootMeanSquareErrorComputer);
		network.setValidationTrace(true);
		network.loadTrainingData(trainingFile);
		runTests(network, hiddenNodes, iterations, seedValue, learningRate, outFile, validationFile, testFile);
	}

}
