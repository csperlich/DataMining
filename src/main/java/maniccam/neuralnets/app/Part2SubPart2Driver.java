package maniccam.neuralnets.app;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import maniccam.data.Part2StudentDataConverter;
import maniccam.neuralnets.NeuralNetwork;

public class Part2SubPart2Driver {
	public static void main(String[] args) throws IOException {

		NeuralNetwork network = new NeuralNetwork(new Part2StudentDataConverter());
		network.loadTrainingData("program2_data/part2/train1");

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

		String outFile = "program2_data/part2/output1";
		runSimulation(network, bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate, outFile + "_best");

		runSimulation(network, bestHiddenNodes * 2, bestIterations, bestSeedValue, bestLearningRate,
				outFile + "_hiddenNodesDoubled");

		runSimulation(network, bestHiddenNodes / 2, bestIterations, bestSeedValue, bestLearningRate,
				outFile + "_hiddenNodesHalved");

		runSimulation(network, bestHiddenNodes, bestIterations * 2, bestSeedValue, bestLearningRate,
				outFile + "_iterationsDoubled");

		runSimulation(network, bestHiddenNodes, bestIterations / 2, bestSeedValue, bestLearningRate,
				outFile + "_iterationsHalved");

		runSimulation(network, bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate / 2,
				outFile + "_learningRateHalved");

	}

	public static void runSimulation(NeuralNetwork network, int hiddenNodes, int iterations, int seedValue,
			double learningRate, String outFile) throws IOException {

		PrintWriter fileOut = new PrintWriter(new FileWriter(outFile, true));

		network.setParameters(hiddenNodes, iterations, seedValue, learningRate);
		network.train();

		fileOut.println("-----------------");
		System.out.println("PRINTING CLASSIFICATIONS OF \"program2_data/part2/test1\" to " + outFile);
		network.testData("program2_data/part2/test1", outFile);

		System.out.println("PRINTING NETWORK PARAMETERS TO " + outFile);
		fileOut.println(network.getParamString());

		System.out.println("PRININTG TRAINING ERROR AND VALIDATION ERROR TO " + outFile);
		fileOut.println(
				"TRAINING ERROR USING ROOT MEAN SQUARED ERROR -> " + String.format("%8.4f", network.trainingError()));
		fileOut.println("VALIDATION ERROR USING MEAN ROOT MEAN SQUARED ERROR -> "
				+ String.format("%8.4f", network.validate("program2_data/part2/validate1")));

		fileOut.close();
		System.out.println();

	}
}
