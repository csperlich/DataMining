package assignment1.app;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import assignment1.classifier.decisiontree.DecisionTree;
import assignment1.data.attribute.AttributeInfo;
import assignment1.data.attribute.NominalAttributeInfo;
import assignment1.data.feature.DefaultFeautureStrategy;
import assignment1.data.feature.FeatureStrategy;
import assignment1.data.record.Record;
import assignment1.entropy.GiniMeasure;
import assignment1.fileio.HomogenousIO;

public class Assignment1Part3 {
	public static void main(String[] args) throws FileNotFoundException {

		// Define the attribute info
		List<AttributeInfo<?>> attInf = new LinkedList<>();
		FeatureStrategy fStrat = new DefaultFeautureStrategy();
		attInf.add(new NominalAttributeInfo(0, "major", Arrays.asList("other", "cs"), fStrat, false));
		attInf.add(new NominalAttributeInfo(1, "java experience", Arrays.asList("no", "java"), fStrat, false));
		attInf.add(new NominalAttributeInfo(2, "c/c++ experience", Arrays.asList("no", "c/c++"), fStrat, false));
		attInf.add(new NominalAttributeInfo(3, "gpa", Arrays.asList("gpa<3", "gpa>3"), fStrat, false));
		attInf.add(new NominalAttributeInfo(4, "large project experience", Arrays.asList("small", "large"), fStrat,
				false));
		attInf.add(
				new NominalAttributeInfo(5, "years of experience", Arrays.asList("years<5", "years>5"), fStrat, false));

		// Read in the data
		HomogenousIO recordIO = new HomogenousIO(attInf, fStrat);
		List<Record> trainingData = recordIO.getTrainingData("DataMining-Data/train2");

		// Initialize the tree and build it
		DecisionTree dTree = new DecisionTree(trainingData, attInf, new GiniMeasure());
		dTree.buildClassifier();

		// Ordered printing of nodes, which we can draw a picture of
		// the tree from
		System.out.println("\nPATHS TO NODES");
		dTree.printTree();

		// ---------------------------------------------------
		// CLASSIFICATION AND VALIDAITON USING GINI MEASURE
		// ---------------------------------------------------

		System.out.println("\nCLASSIFICATION AND VALIDAITON USING GINI MEASURE");

		// Load the test data, classify the test records, write results to
		// file, and print the results to the console
		List<Record> testData = recordIO.getTestData("DataMining-Data/test2");
		dTree.classify(testData);
		recordIO.writeData("output/results2-GiniMeasure", testData);

		// Print out training error
		System.out.println("Training Error: " + dTree.trainingError());

		// Print out random sampling validation error
		System.out.println("Random Sampling Validaiton Error: " + dTree.validateRandomSampling(100));

		// Print out leave-one-out validation error
		System.out.println("Leave-One-Out Validation Error: " + dTree.validateLeaveOneOut());

		// ---------------------------------------------------
		// CLASSIFICATION AND VALIDAITON USING SHANNON ENTROPY
		// ---------------------------------------------------

		System.out.println("\nCLASSIFICATION AND VALIDAITON USING SHANNON ENTROPY");

		// Load the test data, classify the test records, write results to
		// file, and print the results to the console
		testData = recordIO.getTestData("DataMining-Data/test2");
		dTree.classify(testData);
		recordIO.writeData("output/results2-ShannonEntropy", testData);

		// Print out training error
		System.out.println("Training Error: " + dTree.trainingError());

		// Print out random sampling validation error
		System.out.println("Random Sampling Validaiton Error: " + dTree.validateRandomSampling(100));

		// Print out leave-one-out validation error
		System.out.println("Leave-One-Out Validation Error: " + dTree.validateLeaveOneOut());

	}
}
