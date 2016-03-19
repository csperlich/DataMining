package maniccam.neuralnets.app;

import static maniccam.neuralnets.app.NeuralDriverTools.runSimulation;

import java.io.IOException;

import maniccam.data.SAndPNeuralNetworkDataConverter;
import maniccam.neuralnets.NeuralNetwork;

public class SAndPDataNeuralNetworkDriver {
	public static void main(String[] args) throws IOException {
		NeuralNetwork network = new NeuralNetwork(new SAndPNeuralNetworkDataConverter());

		String trainingFile = "program2_data/s&p/training_jan2014-dec2014";
		String outFile = "program2_data/s&p/output";
		String validationFile = "program2_data/s&p/validate_jan2015-mar2015";
		String testFile = "program2_data/s&p/test_apr2014";

		network.loadTrainingData(trainingFile);

		runSimulation(network, 6, 10000, 3126, .8, outFile, validationFile, testFile);
	}
}
