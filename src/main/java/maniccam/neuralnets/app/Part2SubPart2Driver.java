package maniccam.neuralnets.app;

import java.io.IOException;

import maniccam.data.Part2StudentDataConverter;
import maniccam.neuralnets.NeuralNetwork;

public class Part2SubPart2Driver {
	public static void main(String[] args) throws IOException {
		NeuralNetwork network = new NeuralNetwork(new Part2StudentDataConverter());

		network.loadTrainingData("program2_data/part2/train1");

		network.setParameters(12, 10000, 4539, 0.5);

		System.out.println("---NETWORK PARAMETERS---");
		network.printParams();
		System.out.println("\n---NETWORK INFO BEFORE TRAINING---");
		network.printNetwork();

		network.train();

		System.out.println("\n--- NETWORK INFO AFTER TRAINING ---");
		network.printNetwork();

		System.out.println("-----------------");
		System.out.println(
				"PRINTING CLASSIFICATIONS OF \"program2_data/part2/test1\" to \"program2_data/part2/output1\"");
		network.testData("program2_data/part2/test1", "program2_data/part2/output1");

		System.out.println("TRAINING ERROR USING ROOT MEAN SQUARED ERROR -> " + network.trainingError());

		System.out.println("VALIDATION ERROR USING MEAN ROOT MEAN SQUARED ERROR -> "
				+ network.validate("program2_data/part2/validate1"));

	}
}
