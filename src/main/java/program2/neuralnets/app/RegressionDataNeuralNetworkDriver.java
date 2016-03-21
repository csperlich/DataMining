package program2.neuralnets.app;

import static program2.neuralnets.app.NeuralNetworkDriverTools.runTests;

import java.io.IOException;

import program2.neuralnets.NeuralNetwork;
import program2.neuralnets.testerrorcomputers.NeuralTestErrorComputer;

public class RegressionDataNeuralNetworkDriver {
	public static void main(String[] args) throws IOException {
		System.out.println("Running driver program for Program2-Part3-Subpart1: "
				+ "\n\tNeural Network Regression on Various Function Data Inputs\n");

		String folder = "program2_data/part3/";

		int bestHiddenNodes = 12;
		int bestIterations = 12000;
		int bestSeedValue = 4539;
		double bestLearningRate = .63;

		buildAndRunTests(bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate, folder + "train1",
				folder + "validate1", folder + "test1", folder + "output1");

		buildAndRunTests(bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate, folder + "train2",
				folder + "validate2", folder + "test2", folder + "output2");

		buildAndRunTests(bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate, folder + "train3",
				folder + "validate3", folder + "test3", folder + "output3");

		buildAndRunTests(bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate, folder + "train4",
				folder + "validate4", folder + "test4", folder + "output4");

		buildAndRunTests(bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate, folder + "train5",
				folder + "validate5", folder + "test5", folder + "output5");

	}

	//method builds a Neural Network with the given parameters and runs training error and validation error tests
	private static void buildAndRunTests(int hiddenNodes, int iterations, int seedValue, double learningRate,
			String trainingFile, String validationFile, String testFile, String outFile) throws IOException {
		NeuralNetwork network = new NeuralNetwork(NeuralTestErrorComputer.ComputerType.RootMeanSquareErrorComputer);
		network.setValidationTrace(true);
		network.loadTrainingData(trainingFile);
		runTests(network, hiddenNodes, iterations, seedValue, learningRate, outFile, validationFile, testFile);
	}
}

/*
SAMPLE OUTPUT:

Running driver program for Program2-Part3-Subpart1: 
	Neural Network Regression on Various Function Data Inputs

PRINTING CLASSIFICATIONS OF program2_data/part3/test1 to program2_data/part3/output1
PRINTING NETWORK PARAMETERS TO program2_data/part3/output1
PRININTG TRAINING ERROR AND VALIDATION ERROR TO program2_data/part3/output1

VALIDATION TRACE:
input = 0.88  | predicted = 0.77  | actual = 0.78 

input = 0.98  | predicted = 0.82  | actual = 0.84 

input = 0.26  | predicted = 0.40  | actual = 0.41 

input = 0.80  | predicted = 0.73  | actual = 0.73 

input = 0.51  | predicted = 0.55  | actual = 0.55 

input = 0.87  | predicted = 0.77  | actual = 0.77 

input = 0.52  | predicted = 0.56  | actual = 0.56 

input = 0.16  | predicted = 0.35  | actual = 0.35 

input = 0.82  | predicted = 0.75  | actual = 0.74 

input = 0.30  | predicted = 0.43  | actual = 0.43 


PRINTING CLASSIFICATIONS OF program2_data/part3/test2 to program2_data/part3/output2
PRINTING NETWORK PARAMETERS TO program2_data/part3/output2
PRININTG TRAINING ERROR AND VALIDATION ERROR TO program2_data/part3/output2

VALIDATION TRACE:
input = 0.22  | predicted = 0.11  | actual = 0.12 

input = 0.76  | predicted = 0.63  | actual = 0.63 

input = 0.19  | predicted = 0.09  | actual = 0.10 

input = 0.94  | predicted = 0.91  | actual = 0.91 

input = 0.10  | predicted = 0.05  | actual = 0.05 

input = 0.48  | predicted = 0.32  | actual = 0.32 

input = 0.89  | predicted = 0.83  | actual = 0.82 

input = 0.19  | predicted = 0.09  | actual = 0.10 

input = 0.26  | predicted = 0.14  | actual = 0.15 

input = 0.48  | predicted = 0.33  | actual = 0.33 


PRINTING CLASSIFICATIONS OF program2_data/part3/test3 to program2_data/part3/output3
PRINTING NETWORK PARAMETERS TO program2_data/part3/output3
PRININTG TRAINING ERROR AND VALIDATION ERROR TO program2_data/part3/output3

VALIDATION TRACE:
input = 0.08  | predicted = 0.92  | actual = 0.91 

input = 0.78  | predicted = 0.26  | actual = 0.31 

input = 0.87  | predicted = 0.06  | actual = 0.00 

input = 0.26  | predicted = 0.45  | actual = 0.46 

input = 0.36  | predicted = 0.05  | actual = 0.00 

input = 0.91  | predicted = 0.08  | actual = 0.04 

input = 0.84  | predicted = 0.08  | actual = 0.04 

input = 0.86  | predicted = 0.06  | actual = 0.01 

input = 0.94  | predicted = 0.14  | actual = 0.17 

input = 0.05  | predicted = 0.81  | actual = 0.80 

input = 0.77  | predicted = 0.33  | actual = 0.37 

input = 0.94  | predicted = 0.14  | actual = 0.16 

input = 0.08  | predicted = 0.92  | actual = 0.93 

input = 0.80  | predicted = 0.16  | actual = 0.23 

input = 0.91  | predicted = 0.08  | actual = 0.06 

input = 0.78  | predicted = 0.26  | actual = 0.32 

input = 0.92  | predicted = 0.09  | actual = 0.08 

input = 0.46  | predicted = 0.23  | actual = 0.25 

input = 0.42  | predicted = 0.09  | actual = 0.08 

input = 0.68  | predicted = 0.89  | actual = 0.90 

input = 0.46  | predicted = 0.23  | actual = 0.24 

input = 0.12  | predicted = 0.97  | actual = 1.00 

input = 0.29  | predicted = 0.26  | actual = 0.27 

input = 0.66  | predicted = 0.92  | actual = 0.96 

input = 0.24  | predicted = 0.56  | actual = 0.54 

input = 0.93  | predicted = 0.12  | actual = 0.11 

input = 0.39  | predicted = 0.05  | actual = 0.00 

input = 0.48  | predicted = 0.36  | actual = 0.37 

input = 0.88  | predicted = 0.06  | actual = 0.00 

input = 0.01  | predicted = 0.55  | actual = 0.54 

input = 0.22  | predicted = 0.68  | actual = 0.67 

input = 0.28  | predicted = 0.32  | actual = 0.33 

input = 0.72  | predicted = 0.73  | actual = 0.69 

input = 0.24  | predicted = 0.56  | actual = 0.56 

input = 0.90  | predicted = 0.07  | actual = 0.02 

input = 0.17  | predicted = 0.93  | actual = 0.92 

input = 0.64  | predicted = 0.94  | actual = 0.99 

input = 0.52  | predicted = 0.66  | actual = 0.64 

input = 0.80  | predicted = 0.16  | actual = 0.18 

input = 0.88  | predicted = 0.06  | actual = 0.00 

input = 0.83  | predicted = 0.09  | actual = 0.07 

input = 0.46  | predicted = 0.23  | actual = 0.25 

input = 0.46  | predicted = 0.23  | actual = 0.28 

input = 0.97  | predicted = 0.30  | actual = 0.35 

input = 0.21  | predicted = 0.75  | actual = 0.76 

input = 0.40  | predicted = 0.06  | actual = 0.03 

input = 0.11  | predicted = 0.96  | actual = 0.99 

input = 0.62  | predicted = 0.94  | actual = 1.00 

input = 0.28  | predicted = 0.32  | actual = 0.34 

input = 0.72  | predicted = 0.73  | actual = 0.71 


PRINTING CLASSIFICATIONS OF program2_data/part3/test4 to program2_data/part3/output4
PRINTING NETWORK PARAMETERS TO program2_data/part3/output4
PRININTG TRAINING ERROR AND VALIDATION ERROR TO program2_data/part3/output4

VALIDATION TRACE:
input = 0.56 0.42 0.20  | predicted = 0.12  | actual = 0.12 

input = 0.44 0.22 0.93  | predicted = 0.12  | actual = 0.11 

input = 0.27 0.15 0.06  | predicted = 0.03  | actual = 0.01 

input = 0.99 0.82 0.79  | predicted = 0.76  | actual = 0.74 

input = 0.83 0.32 0.67  | predicted = 0.16  | actual = 0.15 

input = 0.37 0.71 0.40  | predicted = 0.22  | actual = 0.22 

input = 0.42 0.76 0.08  | predicted = 0.25  | actual = 0.25 

input = 0.50 0.58 0.33  | predicted = 0.19  | actual = 0.20 

input = 0.19 0.19 0.95  | predicted = 0.10  | actual = 0.10 

input = 0.04 0.48 0.26  | predicted = 0.04  | actual = 0.04 


PRINTING CLASSIFICATIONS OF program2_data/part3/test5 to program2_data/part3/output5
PRINTING NETWORK PARAMETERS TO program2_data/part3/output5
PRININTG TRAINING ERROR AND VALIDATION ERROR TO program2_data/part3/output5

VALIDATION TRACE:
input = 1.00 0.62 0.31  | predicted = 0.38 0.31  | actual = 0.39 0.31 

input = 0.03 0.46 0.59  | predicted = 0.21 0.04  | actual = 0.21 0.02 

input = 0.54 0.27 0.52  | predicted = 0.07 0.29  | actual = 0.07 0.28 

input = 0.33 0.09 0.98  | predicted = 0.01 0.33  | actual = 0.01 0.32 

input = 0.17 0.68 0.98  | predicted = 0.46 0.15  | actual = 0.47 0.17 

input = 0.55 0.78 0.82  | predicted = 0.60 0.44  | actual = 0.61 0.45 

input = 0.70 0.87 0.50  | predicted = 0.77 0.35  | actual = 0.76 0.35 

input = 0.79 0.96 0.77  | predicted = 0.91 0.61  | actual = 0.92 0.61 

input = 0.37 0.84 0.41  | predicted = 0.72 0.15  | actual = 0.71 0.15 

input = 0.18 0.11 0.09  | predicted = 0.02 0.02  | actual = 0.01 0.02 
*/