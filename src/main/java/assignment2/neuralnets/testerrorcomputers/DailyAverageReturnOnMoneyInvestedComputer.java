package assignment2.neuralnets.testerrorcomputers;

import assignment2.data.RecordReader;
import assignment2.neuralnets.NeuralNetwork;

/**
 * Class to compute training and validation test error for a Neural Network
 * using S&P 500 data. The error it computes is the average return on a daily
 * investment if the investor bought stock at open and sold the same stock at
 * close for a given day. The computation does not take into account taxes or
 * trading fees.
 *
 * It is important to note that high errors are better than low errors in this
 * case, since the error represents return on investment.
 *
 */
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
