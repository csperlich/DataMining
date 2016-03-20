package assignment2.neuralnets;

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
