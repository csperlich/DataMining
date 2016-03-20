package assignment2.neuralnets.testerrorcomputers;

import assignment2.neuralnets.NeuralNetwork;

public class RootMeanSquareErrorComputer extends NeuralTestErrorComputer {

	public RootMeanSquareErrorComputer(NeuralNetwork neuralNetwork) {
		super(neuralNetwork);
	}

	@Override
	public double computeError(double[] actualOutput, double[] predictedOutput) {

		double error = 0;

		for (int i = 0; i < actualOutput.length; i++) {
			error += Math.pow(actualOutput[i] - predictedOutput[i], 2);
		}

		return Math.sqrt(error / actualOutput.length);
	}

	@Override
	public String toString() {
		return "ROOT MEAN SQUARED ERROR (LOWER IS BETTER)";
	}

}
