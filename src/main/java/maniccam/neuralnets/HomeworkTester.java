package maniccam.neuralnets;

import java.io.IOException;

public class HomeworkTester {
	public static void main(String[] args) throws IOException {
		NeuralNetwork network = new NeuralNetwork();

		network.loadTrainingData("neural_data/homeworkinput");

		network.setParameters(3, 1, 4539, 0.5);
		double[][] startMiddleWeights = new double[][] { { .4, .7, -.3 }, { -.2, -.5, .8 } };
		double[] startMiddleBiases = new double[] { -.2, .6, .1 };

		double[][] startOutWeights = new double[][] { { .2, -.7 }, { -.1, .6 }, { .5, .4 } };
		double[] startOutBiases = new double[] { .1, .3 };

		network.setWeights(startMiddleWeights, startOutWeights);
		network.setBiases(startMiddleBiases, startOutBiases);

		network.printNetwork();

		network.train();

		System.out.println("\n===== after training ====\n");
		network.printNetwork();
		network.testData("neural_data/homeworkinput_test", "neural_data/homeworkinput_test_result");
		// network.testData("inputfile", "outputfile");
	}
}
