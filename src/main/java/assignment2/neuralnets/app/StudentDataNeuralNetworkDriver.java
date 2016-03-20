package assignment2.neuralnets.app;

import static assignment2.neuralnets.app.NeuralNetworkDriverTools.runTests;

import java.io.IOException;

import assignment2.neuralnets.NeuralNetwork;
import assignment2.neuralnets.testerrorcomputers.NeuralTestErrorComputer;

public class StudentDataNeuralNetworkDriver {
	public static void main(String[] args) throws IOException {
		System.out.println("Running driver program for Assignment2-Part2-Subpart3: "
				+ "\n\tNeural Network Student Data Ranking Classification With Multiple Network Parameters\n");

		String testFile = "program2_data/part2/test1";
		String validationFile = "program2_data/part2/validate1";
		String trainingFile = "program2_data/part2/train1";
		String outFile = "program2_data/part2/output1";

		NeuralNetwork network = new NeuralNetwork(
				NeuralTestErrorComputer.ComputerType.SingleOutputClassificationTestComputer);
		network.loadTrainingData(trainingFile);

		int bestHiddenNodes = 12;
		int bestIterations = 10000;
		int bestSeedValue = 4539;
		double bestLearningRate = .5;

		network.setParameters(bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate);

		System.out.println("---BEST NETWORK PARAMETERS---");
		System.out.println(network.getParamString());

		System.out.println("\n--- NETWORK INFO AFTER TRAINING ---");
		network.train();
		network.printNetwork();

		network.setValidationTrace(true);

		System.out.println("RUNNING TESTS WITH BEST NETWORK PARAMETERS");
		runTests(network, bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate, outFile + "_best",
				validationFile, testFile);

		System.out.println("RUNNING TESTS WITH TWICE AS MANY HIDDEN NODES");
		runTests(network, bestHiddenNodes * 2, bestIterations, bestSeedValue, bestLearningRate,
				outFile + "_hiddenNodesDoubled", validationFile, testFile);

		System.out.println("RUNNING TESTS WITH HALF AS MANY HIDDEN NODES");
		runTests(network, bestHiddenNodes / 2, bestIterations, bestSeedValue, bestLearningRate,
				outFile + "_hiddenNodesHalved", validationFile, testFile);

		System.out.println("RUNNING TESTS WITH TWICE AS MANY ITERATIONS");
		runTests(network, bestHiddenNodes, bestIterations * 2, bestSeedValue, bestLearningRate,
				outFile + "_iterationsDoubled", validationFile, testFile);

		System.out.println("RUNNING TESTS WITH HALF AS MANY ITERATIONS");
		runTests(network, bestHiddenNodes, bestIterations / 2, bestSeedValue, bestLearningRate,
				outFile + "_iterationsHalved", validationFile, testFile);

		System.out.println("RUNNING TESTS WITH LEARNING RATE HALVED");
		runTests(network, bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate / 2,
				outFile + "_learningRateHalved", validationFile, testFile);

	}

}
