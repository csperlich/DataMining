package maniccam.neuralnets.app;

import static maniccam.neuralnets.app.NeuralDriverTools.runSimulation;

import java.io.IOException;

import maniccam.data2.RecordReader;
import maniccam.neuralnets.NeuralNetwork;

public class RegressionDataNeuralNetworkDriver {
	public static void main(String[] args) throws IOException {
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
		RecordReader recordReader = new RecordReader(true);
		NeuralNetwork network = new NeuralNetwork(recordReader);
		network.loadTrainingData(trainingFile);
		runSimulation(network, hiddenNodes, iterations, seedValue, learningRate, outFile, validationFile, testFile);
	}

}
