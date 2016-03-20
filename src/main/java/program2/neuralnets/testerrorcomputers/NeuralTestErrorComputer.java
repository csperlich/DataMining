package program2.neuralnets.testerrorcomputers;

import program2.neuralnets.NeuralNetwork;

/**
 * Abstract class for computing errors used in training error and validation
 * error testing for a Neural Network.
 */
public abstract class NeuralTestErrorComputer {

	public static enum ComputerType {
		RootMeanSquareErrorComputer, SingleOutputClassificationTestComputer, DailyAverageReturnOnMoneyInvestedComputer
	}

	protected NeuralNetwork neuralNetwork;

	public NeuralTestErrorComputer(NeuralNetwork neuralNetwork) {
		this.neuralNetwork = neuralNetwork;
	}

	public abstract double computeError(double[] actualOutput, double[] predictedOutput);

	@Override
	public abstract String toString();

	/**
	 * Factory method for test error computers
	 */
	public static NeuralTestErrorComputer getNeuralTestErrorComputer(ComputerType type, NeuralNetwork neuralNetwork) {
		if (type.equals(ComputerType.RootMeanSquareErrorComputer)) {
			return new RootMeanSquareErrorComputer(neuralNetwork);
		} else if (type.equals(ComputerType.SingleOutputClassificationTestComputer)) {
			return new SingleOutputClassificationErrorComputer(neuralNetwork);
		} else if (type.equals(ComputerType.DailyAverageReturnOnMoneyInvestedComputer)) {
			return new DailyAverageReturnOnMoneyInvestedComputer(neuralNetwork);
		}
		return null;
	}
}
