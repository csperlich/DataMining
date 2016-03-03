package app;

import org.javatuples.Pair;

import java.io.FileNotFoundException;
import java.util.List;

import classifier.decisiontree.DecisionTree;
import data.attribute.AttributeInfo;
import data.record.Record;
import entropy.ClassError;
import entropy.GiniMeasure;
import fileio.BinaryIO;

public class Assignment1Part2 {
	public static void main(String[] args) throws FileNotFoundException {

		// Read in the data
		BinaryIO binaryIO = new BinaryIO(new Pair<String, String>("0", "1"), 5, 0);
		Pair<List<Record>, List<AttributeInfo<?>>> data = binaryIO.getData("DataMining-Data/train1");
		// Create empty tree with the data and GiniMeasure
		DecisionTree dTree = new DecisionTree(data.getValue0(), data.getValue1(), new GiniMeasure());

		// Build tree with trace on
		System.out.println("TRACING TREE BUILD");
		dTree.setTrace(true);
		dTree.buildTree();
		dTree.setTrace(false);

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
		List<Record> testData = binaryIO.getTestData("DataMining-Data/test1");
		dTree.classify(testData);
		binaryIO.writeData("output/results1-GiniMeasure", testData);

		// Print out training error
		System.out.println("Training Error: " + dTree.trainingError());

		// Print out random sampling validation error
		System.out.println("Random Sampling Validaiton Error: " + dTree.validateRandomSampling(100));

		// Print out leave-one-out validation error
		System.out.println("Leave-One-Out Validation Error: " + dTree.validateLeaveOneOut());

		// -------------------------------------------------------
		// CLASSIFICATION AND VALIDAITON USING CLASS ERROR ENTROPY
		// -------------------------------------------------------

		System.out.println("\nCLASSIFICATION AND VALIDAITON USING CLASS ERROR ENTROPY");

		// Reinitialize and rebuild the tree with ClassError Measure
		dTree = new DecisionTree(data.getValue0(), data.getValue1(), new ClassError());
		dTree.buildTree();

		// Load the test data, classify the test records, write results to
		// file, and print the results to the console
		testData = binaryIO.getTestData("DataMining-Data/test1");
		dTree.classify(testData);
		binaryIO.writeData("output/results1-ClassError", testData);

		// Print out training error
		System.out.println("Training Error: " + dTree.trainingError());

		// Print out random sampling validation error
		System.out.println("Random Sampling Validaiton Error: " + dTree.validateRandomSampling(100));

		// Print out leave-one-out validation error
		System.out.println("Leave-One-Out Validation Error: " + dTree.validateLeaveOneOut());

	}
}
