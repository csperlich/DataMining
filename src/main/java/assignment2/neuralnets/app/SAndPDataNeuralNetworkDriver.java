package assignment2.neuralnets.app;

import static assignment2.neuralnets.app.NeuralDriverTools.runSimulation;

import java.io.IOException;

import assignment2.data.RecordReader;
import assignment2.neuralnets.NeuralNetwork;

public class SAndPDataNeuralNetworkDriver {
	public static void main(String[] args) throws IOException {
		NeuralNetwork network = new NeuralNetwork(new RecordReader(true));

		String trainingFile = "program2_data/s&p/training_jan2014-dec2014_bounded";
		String outFile = "program2_data/s&p/output";
		String validationFile = "program2_data/s&p/validate_jan2015-mar2015_bounded";
		String testFile = "program2_data/s&p/test_apr2014_bounded";

		network.loadTrainingData(trainingFile);

		runSimulation(network, 6, 10000, 3126, .8, outFile, validationFile, testFile);
	}
}
