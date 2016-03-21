package program2.neuralnets.app;

import static program2.neuralnets.app.NeuralNetworkDriverTools.runTests;

import java.io.IOException;

import program2.neuralnets.NeuralNetwork;
import program2.neuralnets.testerrorcomputers.NeuralTestErrorComputer;

public class SAndPDataNeuralNetworkDriver {
	public static void main(String[] args) throws IOException {
		System.out.println("Running driver program for Program2-Part3-Subpart2: "
				+ "\n\tNeural Network Regression On S&P Daily Percentage Change Stock Price Data\n");

		NeuralNetwork network = new NeuralNetwork(
				NeuralTestErrorComputer.ComputerType.DailyAverageReturnOnMoneyInvestedComputer);

		String trainingFile = "program2_data/s&p/training_jan2014-dec2014_bounded";
		String outFile = "program2_data/s&p/output";
		String validationFile = "program2_data/s&p/validate_jan2015-mar2015_bounded";
		String testFile = "program2_data/s&p/test_apr2015_bounded";

		network.loadTrainingData(trainingFile);
		network.setValidationTrace(true);

		runTests(network, 6, 80000, 3126, .07, outFile, validationFile, testFile);
	}
}

/*
SAMPLE OUTPUT:

Running driver program for Program2-Part3-Subpart2: 
	Neural Network Regression On S&P Daily Percentage Change Stock Price Data

PRINTING CLASSIFICATIONS OF program2_data/s&p/test_apr2015_bounded to program2_data/s&p/output
PRINTING NETWORK PARAMETERS TO program2_data/s&p/output
PRININTG TRAINING ERROR AND VALIDATION ERROR TO program2_data/s&p/output

VALIDATION TRACE:
input = 0.04 0.28 0.79  | predicted = 0.41  | actual = 0.95 

input = 0.28 0.79 0.95  | predicted = 0.67  | actual = 0.29 

input = 0.79 0.95 0.29  | predicted = 0.81  | actual = 0.30 

input = 0.95 0.29 0.30  | predicted = 0.38  | actual = 0.44 

input = 0.29 0.30 0.44  | predicted = 0.66  | actual = 0.35 

input = 0.30 0.44 0.35  | predicted = 0.68  | actual = 0.27 

input = 0.44 0.35 0.27  | predicted = 0.65  | actual = 0.84 

input = 0.35 0.27 0.84  | predicted = 0.44  | actual = 0.54 

input = 0.27 0.84 0.54  | predicted = 0.45  | actual = 0.62 

input = 0.84 0.54 0.62  | predicted = 0.60  | actual = 0.88 

input = 0.54 0.62 0.88  | predicted = 0.66  | actual = 0.36 

input = 0.62 0.88 0.36  | predicted = 0.93  | actual = 0.56 

input = 0.88 0.36 0.56  | predicted = 0.49  | actual = 0.17 

input = 0.36 0.56 0.17  | predicted = 0.55  | actual = 0.16 

input = 0.56 0.17 0.16  | predicted = 0.54  | actual = 0.74 

input = 0.17 0.16 0.74  | predicted = 0.42  | actual = 0.18 

input = 0.16 0.74 0.18  | predicted = 0.58  | actual = 0.82 

input = 0.74 0.18 0.82  | predicted = 0.52  | actual = 0.86 

input = 0.18 0.82 0.86  | predicted = 0.62  | actual = 0.40 

input = 0.82 0.86 0.40  | predicted = 0.97  | actual = 0.76 

input = 0.86 0.40 0.76  | predicted = 0.62  | actual = 0.41 

input = 0.40 0.76 0.41  | predicted = 0.41  | actual = 0.39 

input = 0.76 0.41 0.39  | predicted = 0.46  | actual = 0.77 

input = 0.41 0.39 0.77  | predicted = 0.48  | actual = 0.50 

input = 0.39 0.77 0.50  | predicted = 0.46  | actual = 0.74 

input = 0.77 0.50 0.74  | predicted = 0.63  | actual = 0.60 

input = 0.50 0.74 0.60  | predicted = 0.56  | actual = 0.54 

input = 0.74 0.60 0.54  | predicted = 0.55  | actual = 0.49 

input = 0.60 0.54 0.49  | predicted = 0.49  | actual = 0.47 

input = 0.54 0.49 0.47  | predicted = 0.49  | actual = 0.65 

input = 0.49 0.47 0.65  | predicted = 0.48  | actual = 0.49 

input = 0.47 0.65 0.49  | predicted = 0.48  | actual = 0.57 

input = 0.65 0.49 0.57  | predicted = 0.50  | actual = 0.48 

input = 0.49 0.57 0.48  | predicted = 0.49  | actual = 0.46 

input = 0.57 0.48 0.46  | predicted = 0.49  | actual = 0.43 

input = 0.48 0.46 0.43  | predicted = 0.53  | actual = 0.65 

input = 0.46 0.43 0.65  | predicted = 0.46  | actual = 0.39 

input = 0.43 0.65 0.39  | predicted = 0.46  | actual = 0.39 

input = 0.65 0.39 0.39  | predicted = 0.50  | actual = 0.53 

input = 0.39 0.39 0.53  | predicted = 0.50  | actual = 0.15 

input = 0.39 0.53 0.15  | predicted = 0.57  | actual = 0.60 

input = 0.53 0.15 0.60  | predicted = 0.40  | actual = 0.08 

input = 0.15 0.60 0.08  | predicted = 0.47  | actual = 0.45 

input = 0.60 0.08 0.45  | predicted = 0.46  | actual = 0.82 

input = 0.08 0.45 0.82  | predicted = 0.42  | actual = 0.35 

input = 0.45 0.82 0.35  | predicted = 0.61  | actual = 0.84 

input = 0.82 0.35 0.84  | predicted = 0.63  | actual = 0.42 

input = 0.35 0.84 0.42  | predicted = 0.38  | actual = 0.80 

input = 0.84 0.42 0.80  | predicted = 0.63  | actual = 0.38 

input = 0.42 0.80 0.38  | predicted = 0.44  | actual = 0.73 

input = 0.80 0.38 0.73  | predicted = 0.59  | actual = 0.46 

input = 0.38 0.73 0.46  | predicted = 0.45  | actual = 0.35 

input = 0.73 0.46 0.35  | predicted = 0.47  | actual = 0.14 

input = 0.46 0.35 0.14  | predicted = 0.41  | actual = 0.44 

input = 0.35 0.14 0.44  | predicted = 0.65  | actual = 0.56 

input = 0.14 0.44 0.56  | predicted = 0.54  | actual = 0.81 

input = 0.44 0.56 0.81  | predicted = 0.59  | actual = 0.28 
*/