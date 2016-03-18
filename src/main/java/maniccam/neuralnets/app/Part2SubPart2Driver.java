package maniccam.neuralnets.app;

import java.io.IOException;

import maniccam.data.Part2StudentDataConverter;
import maniccam.neuralnets.NeuralNetwork;

public class Part2SubPart2Driver {
	public static void main(String[] args) throws IOException {
		NeuralNetwork network = new NeuralNetwork(new Part2StudentDataConverter());

		network.loadTrainingData("program2_data/part2/train1");

		network.setParameters(7, 10000, 4539, 0.5);

		network.printNetwork();

		network.train();

		System.out.println("\n===== after training ====\n");
		network.printNetwork();
		network.testData("program2_data/part2/test1", "program2_data/part2/output1");

		network.validate("program2_data/part2/validate1");
	}
}
