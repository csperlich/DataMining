package assignment2.neuralnets.testerrorcomputers;

import assignment2.data.RecordReader;
import assignment2.neuralnets.NeuralNetwork;

/**
 * The error for this class is simple. If the classification is correct, it
 * returns 0.0 error, otherwise it returns 1.0 for the error value.
 */
public class SingleOutputClassificationErrorComputer extends NeuralTestErrorComputer {

	public SingleOutputClassificationErrorComputer(NeuralNetwork neuralNetwork) {
		super(neuralNetwork);
	}

	@Override
	public double computeError(double[] actualOutput, double[] predictedOutput) {
		RecordReader recordReader = this.neuralNetwork.getRecordReader();
		String actualClass = recordReader.convert(recordReader.getNumAttributes() - 1, actualOutput[0]);
		String predictedClass = recordReader.convert(recordReader.getNumAttributes() - 1, predictedOutput[0]);
		return actualClass.equals(predictedClass) ? 0.0 : 1.0;
	}

	@Override
	public String toString() {
		return "CORRECT CLASSIFICATION ERROR (LOWER IS BETTER)";
	}

}
