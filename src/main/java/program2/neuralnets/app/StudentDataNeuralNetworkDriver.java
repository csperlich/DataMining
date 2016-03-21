package program2.neuralnets.app;

import static program2.neuralnets.app.NeuralNetworkDriverTools.runTests;

import java.io.IOException;

import program2.neuralnets.NeuralNetwork;
import program2.neuralnets.testerrorcomputers.NeuralTestErrorComputer;

public class StudentDataNeuralNetworkDriver {
	public static void main(String[] args) throws IOException {
		System.out.println("Running driver program for Program2-Part2-Subpart2: "
				+ "\n\tNeural Network Student Data Ranking Classification With Multiple Network Parameters\n");

		String testFile = "program2_data/part2/test1";
		String validationFile = "program2_data/part2/validate1";
		String trainingFile = "program2_data/part2/train1";
		String outFile = "program2_data/part2/output1";

		NeuralNetwork network = new NeuralNetwork(
				NeuralTestErrorComputer.ComputerType.SingleOutputClassificationTestComputer);
		network.loadTrainingData(trainingFile);

		int bestHiddenNodes = 18;
		int bestIterations = 20000;
		int bestSeedValue = 4539;
		double bestLearningRate = .7;

		network.setParameters(bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate);

		System.out.println("---BEST NETWORK PARAMETERS---");
		System.out.println(network.getParamString());

		System.out.println("\n--- NETWORK INFO AFTER TRAINING ---");
		network.train();
		network.printNetwork();

		network.setValidationTrace(true);

		System.out.println("RUNNING TESTS WITH BEST NETWORK PARAMETERS");
		runTests(network, bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate, outFile + "_best",
				validationFile, testFile);

		System.out.println("RUNNING TESTS WITH TWICE AS MANY HIDDEN NODES");
		runTests(network, bestHiddenNodes * 2, bestIterations, bestSeedValue, bestLearningRate,
				outFile + "_hiddenNodesDoubled", validationFile, testFile);

		System.out.println("RUNNING TESTS WITH HALF AS MANY HIDDEN NODES");
		runTests(network, bestHiddenNodes / 2, bestIterations, bestSeedValue, bestLearningRate,
				outFile + "_hiddenNodesHalved", validationFile, testFile);

		System.out.println("RUNNING TESTS WITH TWICE AS MANY ITERATIONS");
		runTests(network, bestHiddenNodes, bestIterations * 2, bestSeedValue, bestLearningRate,
				outFile + "_iterationsDoubled", validationFile, testFile);

		System.out.println("RUNNING TESTS WITH HALF AS MANY ITERATIONS");
		runTests(network, bestHiddenNodes, bestIterations / 2, bestSeedValue, bestLearningRate,
				outFile + "_iterationsHalved", validationFile, testFile);

		System.out.println("RUNNING TESTS WITH LEARNING RATE HALVED");
		runTests(network, bestHiddenNodes, bestIterations, bestSeedValue, bestLearningRate / 2,
				outFile + "_learningRateHalved", validationFile, testFile);
	}
}

/*
SAMPLE OUTPUT:

Running driver program for Program2-Part2-Subpart2: 
	Neural Network Student Data Ranking Classification With Multiple Network Parameters

---BEST NETWORK PARAMETERS---
INPUT NODES: 3
HIDDEN NODES: 18
OUTPUT NODES: 1
TRAINING ITERATIONS: 20000
LEARNING RATE: 0.7
RANDOM SEED: 4539


--- NETWORK INFO AFTER TRAINING ---
MIDDLE WEIGHTS:
   18.2092    3.6662  -22.1362   -1.8933   -1.1592   -0.4955   -0.6895   -0.0660   -2.7855   12.5993   -7.8626   -8.4429   -2.6167   14.9148  -18.0065   -1.2367    0.2115   -1.2052
   -4.0813   10.5993  -13.3594   -1.4201    0.2948   -1.0766   -3.8429    0.5218   -2.9690   -7.7751  -12.9301   -0.4518   -1.6535   -4.0482  -10.6262   -1.3310   -2.9252   -0.8435
    6.0198   -8.0501   10.4750   -1.6028    5.1234   -0.4815    2.9194   -1.2664   -3.0261   -5.6993   -8.8520    0.2746   -1.7394    9.5047   13.8352   -0.6261    1.0106   -1.3371

MIDDLE BIASES:
  -12.2055   -9.5556    9.7215   -0.4606   -3.5046   -1.7768   -1.5008   -1.3473    3.0950    0.2136    8.9505    4.7207    0.4696  -10.8464    5.2859   -1.4309   -2.4288   -1.3922

OUT WEIGHTS: 
    6.9124
   -3.3215
  -11.8102
   -1.0212
   -2.4970
    0.1267
    2.0105
    0.9842
   -3.4447
   -4.6094
   13.2372
   -5.1211
   -1.9263
   -6.5473
   12.0843
   -0.3175
   -0.0994
   -0.2436

OUT BIASES:
    5.0288

RUNNING TESTS WITH BEST NETWORK PARAMETERS
PRINTING CLASSIFICATIONS OF program2_data/part2/test1 to program2_data/part2/output1_best
PRINTING NETWORK PARAMETERS TO program2_data/part2/output1_best
PRININTG TRAINING ERROR AND VALIDATION ERROR TO program2_data/part2/output1_best

VALIDATION TRACE:
input = 0.60 0.90 0.50  | predicted = 0.62  | actual = 0.50 

input = 0.79 0.85 0.83  | predicted = 0.92  | actual = 0.83 

input = 0.52 0.68 0.83  | predicted = 0.95  | actual = 0.17 

input = 0.90 0.68 0.17  | predicted = 0.41  | actual = 0.50 

input = 0.45 0.80 0.83  | predicted = 0.88  | actual = 0.83 

input = 0.80 0.63 0.50  | predicted = 0.47  | actual = 0.50 

input = 0.65 0.70 0.50  | predicted = 0.28  | actual = 0.17 

input = 0.85 0.88 0.17  | predicted = 0.26  | actual = 0.83 

input = 0.55 0.95 0.17  | predicted = 0.22  | actual = 0.17 

input = 0.90 0.80 0.50  | predicted = 0.52  | actual = 0.50 


RUNNING TESTS WITH TWICE AS MANY HIDDEN NODES
PRINTING CLASSIFICATIONS OF program2_data/part2/test1 to program2_data/part2/output1_hiddenNodesDoubled
PRINTING NETWORK PARAMETERS TO program2_data/part2/output1_hiddenNodesDoubled
PRININTG TRAINING ERROR AND VALIDATION ERROR TO program2_data/part2/output1_hiddenNodesDoubled

VALIDATION TRACE:
input = 0.60 0.90 0.50  | predicted = 0.62  | actual = 0.50 

input = 0.79 0.85 0.83  | predicted = 0.89  | actual = 0.83 

input = 0.52 0.68 0.83  | predicted = 0.98  | actual = 0.17 

input = 0.90 0.68 0.17  | predicted = 0.46  | actual = 0.50 

input = 0.45 0.80 0.83  | predicted = 0.87  | actual = 0.83 

input = 0.80 0.63 0.50  | predicted = 0.45  | actual = 0.50 

input = 0.65 0.70 0.50  | predicted = 0.26  | actual = 0.17 

input = 0.85 0.88 0.17  | predicted = 0.15  | actual = 0.83 

input = 0.55 0.95 0.17  | predicted = 0.22  | actual = 0.17 

input = 0.90 0.80 0.50  | predicted = 0.52  | actual = 0.50 


RUNNING TESTS WITH HALF AS MANY HIDDEN NODES
PRINTING CLASSIFICATIONS OF program2_data/part2/test1 to program2_data/part2/output1_hiddenNodesHalved
PRINTING NETWORK PARAMETERS TO program2_data/part2/output1_hiddenNodesHalved
PRININTG TRAINING ERROR AND VALIDATION ERROR TO program2_data/part2/output1_hiddenNodesHalved

VALIDATION TRACE:
input = 0.60 0.90 0.50  | predicted = 0.57  | actual = 0.50 

input = 0.79 0.85 0.83  | predicted = 0.92  | actual = 0.83 

input = 0.52 0.68 0.83  | predicted = 0.96  | actual = 0.17 

input = 0.90 0.68 0.17  | predicted = 0.30  | actual = 0.50 

input = 0.45 0.80 0.83  | predicted = 0.79  | actual = 0.83 

input = 0.80 0.63 0.50  | predicted = 0.50  | actual = 0.50 

input = 0.65 0.70 0.50  | predicted = 0.24  | actual = 0.17 

input = 0.85 0.88 0.17  | predicted = 0.01  | actual = 0.83 

input = 0.55 0.95 0.17  | predicted = 0.18  | actual = 0.17 

input = 0.90 0.80 0.50  | predicted = 0.55  | actual = 0.50 


RUNNING TESTS WITH TWICE AS MANY ITERATIONS
PRINTING CLASSIFICATIONS OF program2_data/part2/test1 to program2_data/part2/output1_iterationsDoubled
PRINTING NETWORK PARAMETERS TO program2_data/part2/output1_iterationsDoubled
PRININTG TRAINING ERROR AND VALIDATION ERROR TO program2_data/part2/output1_iterationsDoubled

VALIDATION TRACE:
input = 0.60 0.90 0.50  | predicted = 0.67  | actual = 0.50 

input = 0.79 0.85 0.83  | predicted = 0.88  | actual = 0.83 

input = 0.52 0.68 0.83  | predicted = 0.97  | actual = 0.17 

input = 0.90 0.68 0.17  | predicted = 0.30  | actual = 0.50 

input = 0.45 0.80 0.83  | predicted = 0.90  | actual = 0.83 

input = 0.80 0.63 0.50  | predicted = 0.40  | actual = 0.50 

input = 0.65 0.70 0.50  | predicted = 0.29  | actual = 0.17 

input = 0.85 0.88 0.17  | predicted = 0.23  | actual = 0.83 

input = 0.55 0.95 0.17  | predicted = 0.28  | actual = 0.17 

input = 0.90 0.80 0.50  | predicted = 0.49  | actual = 0.50 


RUNNING TESTS WITH HALF AS MANY ITERATIONS
PRINTING CLASSIFICATIONS OF program2_data/part2/test1 to program2_data/part2/output1_iterationsHalved
PRINTING NETWORK PARAMETERS TO program2_data/part2/output1_iterationsHalved
PRININTG TRAINING ERROR AND VALIDATION ERROR TO program2_data/part2/output1_iterationsHalved

VALIDATION TRACE:
input = 0.60 0.90 0.50  | predicted = 0.47  | actual = 0.50 

input = 0.79 0.85 0.83  | predicted = 0.86  | actual = 0.83 

input = 0.52 0.68 0.83  | predicted = 0.74  | actual = 0.17 

input = 0.90 0.68 0.17  | predicted = 0.40  | actual = 0.50 

input = 0.45 0.80 0.83  | predicted = 0.70  | actual = 0.83 

input = 0.80 0.63 0.50  | predicted = 0.48  | actual = 0.50 

input = 0.65 0.70 0.50  | predicted = 0.35  | actual = 0.17 

input = 0.85 0.88 0.17  | predicted = 0.20  | actual = 0.83 

input = 0.55 0.95 0.17  | predicted = 0.11  | actual = 0.17 

input = 0.90 0.80 0.50  | predicted = 0.50  | actual = 0.50 


RUNNING TESTS WITH LEARNING RATE HALVED
PRINTING CLASSIFICATIONS OF program2_data/part2/test1 to program2_data/part2/output1_learningRateHalved
PRINTING NETWORK PARAMETERS TO program2_data/part2/output1_learningRateHalved
PRININTG TRAINING ERROR AND VALIDATION ERROR TO program2_data/part2/output1_learningRateHalved

VALIDATION TRACE:
input = 0.60 0.90 0.50  | predicted = 0.47  | actual = 0.50 

input = 0.79 0.85 0.83  | predicted = 0.85  | actual = 0.83 

input = 0.52 0.68 0.83  | predicted = 0.76  | actual = 0.17 

input = 0.90 0.68 0.17  | predicted = 0.39  | actual = 0.50 

input = 0.45 0.80 0.83  | predicted = 0.73  | actual = 0.83 

input = 0.80 0.63 0.50  | predicted = 0.46  | actual = 0.50 

input = 0.65 0.70 0.50  | predicted = 0.35  | actual = 0.17 

input = 0.85 0.88 0.17  | predicted = 0.17  | actual = 0.83 

input = 0.55 0.95 0.17  | predicted = 0.09  | actual = 0.17 

input = 0.90 0.80 0.50  | predicted = 0.52  | actual = 0.50 
*/