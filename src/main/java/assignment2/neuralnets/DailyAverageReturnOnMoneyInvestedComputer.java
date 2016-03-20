package assignment2.neuralnets;

import assignment2.data.RecordReader;

public class DailyAverageReturnOnMoneyInvestedComputer extends NeuralTestErrorComputer {

	public DailyAverageReturnOnMoneyInvestedComputer(NeuralNetwork neuralNetwork) {
		super(neuralNetwork);
	}

	@Override
	public double computeError(double[] actualOutput, double[] predictedOutput) {
		RecordReader recordReader = this.neuralNetwork.getRecordReader();
		double actualValue = Double
				.parseDouble(recordReader.convert(recordReader.getNumAttributes() - 1, actualOutput[0]));
		double predictedValue = Double
				.parseDouble(recordReader.convert(recordReader.getNumAttributes() - 1, predictedOutput[0]));
		if (predictedValue < 0 && actualValue < 0 || predictedValue > 0 && actualValue > 0) {
			return Math.abs(actualValue);
		}
		return -Math.abs(actualValue);

	}

	@Override
	public String toString() {
		return "DAILY AVERAGE RETURN ON MONEY INVESTED (HIGHER IS BETTER)";
	}

}
