package assignment2.neuralnets.app;

import static assignment2.neuralnets.app.NeuralDriverTools.runSimulation;

import java.io.IOException;

import assignment2.neuralnets.NeuralNetwork;
import assignment2.neuralnets.NeuralTestErrorComputer;

public class StudentDataNeuralNetworkDriver {
	public static void main(String[] args) throws IOException {

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

		runSimulation(network, bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate, outFile + "_best",
				validationFile, testFile);

		runSimulation(network, bestHiddenNodes * 2, bestIterations, bestSeedValue, bestLearningRate,
				outFile + "_hiddenNodesDoubled", validationFile, testFile);

		runSimulation(network, bestHiddenNodes / 2, bestIterations, bestSeedValue, bestLearningRate,
				outFile + "_hiddenNodesHalved", validationFile, testFile);

		runSimulation(network, bestHiddenNodes, bestIterations * 2, bestSeedValue, bestLearningRate,
				outFile + "_iterationsDoubled", validationFile, testFile);

		runSimulation(network, bestHiddenNodes, bestIterations / 2, bestSeedValue, bestLearningRate,
				outFile + "_iterationsHalved", validationFile, testFile);

		runSimulation(network, bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate / 2,
				outFile + "_learningRateHalved", validationFile, testFile);

	}

}
