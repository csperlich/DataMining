package program2.neuralnets.app;

import static program2.neuralnets.app.NeuralNetworkDriverTools.runTests;

import java.io.IOException;

import program2.neuralnets.NeuralNetwork;
import program2.neuralnets.testerrorcomputers.NeuralTestErrorComputer;

public class LoanDataNeuralNetworkDriver {
	public static void main(String[] args) throws IOException {
		System.out.println("Running driver program for Program2-Part2-Subpart3: "
				+ "\n\tNeural Network Loan Data Risk Classification\n");

		NeuralNetwork network = new NeuralNetwork(
				NeuralTestErrorComputer.ComputerType.SingleOutputClassificationTestComputer);

		String trainingFile = "program2_data/part2/train2";
		String outFile = "program2_data/part2/output2";
		String validationFile = "program2_data/part2/validate2";
		String testFile = "program2_data/part2/test2";

		network.loadTrainingData(trainingFile);
		network.setValidationTrace(true);

		runTests(network, 12, 15000, 4539, .5, outFile, validationFile, testFile);
	}
}

/*
SAMPLE OUTPUT:

Running driver program for Program2-Part2-Subpart3: 
	Neural Network Loan Data Risk Classification

PRINTING CLASSIFICATIONS OF program2_data/part2/test2 to program2_data/part2/output2
PRINTING NETWORK PARAMETERS TO program2_data/part2/output2
PRININTG TRAINING ERROR AND VALIDATION ERROR TO program2_data/part2/output2

VALIDATION TRACE:
input = 0.78 0.83 0.20 0.25 0.17  | predicted = 0.11  | actual = 0.13 

input = 0.75 0.08 0.90 0.25 0.50  | predicted = 0.02  | actual = 0.38 

input = 0.08 0.90 0.14 0.75 0.83  | predicted = 0.87  | actual = 0.88 

input = 0.83 0.20 0.84 0.75 0.83  | predicted = 0.07  | actual = 0.13 

input = 0.13 0.83 0.80 0.25 0.17  | predicted = 0.89  | actual = 0.63 

input = 0.41 0.25 0.14 0.75 0.50  | predicted = 0.86  | actual = 0.88 

input = 0.63 0.58 0.40 0.75 0.83  | predicted = 0.37  | actual = 0.13 

input = 0.38 0.93 0.46 0.25 0.83  | predicted = 0.44  | actual = 0.63 

input = 0.58 0.25 0.48 0.75 0.83  | predicted = 0.26  | actual = 0.38 

input = 0.43 0.80 0.56 0.75 0.50  | predicted = 0.93  | actual = 0.88 

input = 0.40 0.37 0.04 0.25 0.17  | predicted = 0.69  | actual = 0.63 

input = 0.88 0.12 0.04 0.25 0.50  | predicted = 0.36  | actual = 0.38 

input = 0.23 0.08 0.90 0.75 0.17  | predicted = 0.50  | actual = 0.63 

input = 0.06 0.13 0.72 0.75 0.83  | predicted = 0.44  | actual = 0.38 

input = 0.09 0.63 0.96 0.25 0.50  | predicted = 0.98  | actual = 0.88 
*/