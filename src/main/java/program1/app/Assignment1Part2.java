package program1.app;

import org.javatuples.Pair;

import java.io.FileNotFoundException;
import java.util.List;

import program1.classifier.decisiontree.DecisionTree;
import program1.data.attribute.AttributeInfo;
import program1.data.record.Record;
import program1.entropy.ClassError;
import program1.entropy.GiniMeasure;
import program1.fileio.BinaryIO;

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

/*
 * TRACING TREE BUILD INTERNAL NODE CREATED --> internal node Node
 * [numChildren=0, feature=Column "2" is 1, label=null, isLeaf=false,
 * confidence=0.0, support=0.0 Records: [1 1 1 1 0 ] [1 0 0 1 1 ] [1 1 0 1 0 ]
 * [0 1 1 1 0 ] [1 0 1 1 0 ] [1 1 0 0 0 ] [0 1 1 0 0 ] [0 1 0 0 0 ] [0 0 0 0 1 ]
 * [0 1 0 1 1 ] [0 1 0 0 1 ] [1 0 0 0 0 ] [1 1 1 0 1 ] [1 0 0 0 1 ] [0 1 0 1 0 ]
 * [1 0 1 1 1 ] [1 0 1 0 0 ] [0 0 1 0 1 ] [0 0 1 1 0 ] [0 1 1 1 1 ] [1 1 1 0 0 ]
 * [1 0 0 1 0 ] [0 0 0 1 1 ] [0 0 0 1 0 ] Attribute Columns: 0 1 2 3 4
 * 
 * INTERNAL NODE CREATED --> internal node Node [numChildren=0, feature=Column
 * "0" is 1, label=null, isLeaf=false, confidence=0.0, support=0.0 Records: [1 1
 * 1 1 0 ] [0 1 1 1 0 ] [1 0 1 1 0 ] [0 1 1 0 0 ] [1 1 1 0 1 ] [1 0 1 1 1 ] [1 0
 * 1 0 0 ] [0 0 1 0 1 ] [0 0 1 1 0 ] [0 1 1 1 1 ] [1 1 1 0 0 ] Attribute
 * Columns: 0 1 3 4
 * 
 * LEAF NODE CREATED --> homogenous records Node [numChildren=0, feature=null,
 * label=4, isLeaf=true, confidence=1.0, support=0.25 Records: [1 1 1 1 0 ] [1 0
 * 1 1 0 ] [1 1 1 0 1 ] [1 0 1 1 1 ] [1 0 1 0 0 ] [1 1 1 0 0 ] Attribute
 * Columns: 1 3 4
 * 
 * LEAF NODE CREATED --> homogenous records Node [numChildren=0, feature=null,
 * label=1, isLeaf=true, confidence=1.0, support=0.20833333333333334 Records: [0
 * 1 1 1 0 ] [0 1 1 0 0 ] [0 0 1 0 1 ] [0 0 1 1 0 ] [0 1 1 1 1 ] Attribute
 * Columns: 1 3 4
 * 
 * INTERNAL NODE CREATED --> internal node Node [numChildren=0, feature=Column
 * "3" is 1, label=null, isLeaf=false, confidence=0.0, support=0.0 Records: [1 0
 * 0 1 1 ] [1 1 0 1 0 ] [1 1 0 0 0 ] [0 1 0 0 0 ] [0 0 0 0 1 ] [0 1 0 1 1 ] [0 1
 * 0 0 1 ] [1 0 0 0 0 ] [1 0 0 0 1 ] [0 1 0 1 0 ] [1 0 0 1 0 ] [0 0 0 1 1 ] [0 0
 * 0 1 0 ] Attribute Columns: 1 3 4 0
 * 
 * INTERNAL NODE CREATED --> internal node Node [numChildren=0, feature=Column
 * "4" is 1, label=null, isLeaf=false, confidence=0.0, support=0.0 Records: [1 0
 * 0 1 1 ] [1 1 0 1 0 ] [0 1 0 1 1 ] [0 1 0 1 0 ] [1 0 0 1 0 ] [0 0 0 1 1 ] [0 0
 * 0 1 0 ] Attribute Columns: 1 4 0
 * 
 * LEAF NODE CREATED --> homogenous records Node [numChildren=0, feature=null,
 * label=3, isLeaf=true, confidence=1.0, support=0.125 Records: [1 0 0 1 1 ] [0
 * 1 0 1 1 ] [0 0 0 1 1 ] Attribute Columns: 1 0
 * 
 * LEAF NODE CREATED --> homogenous records Node [numChildren=0, feature=null,
 * label=2, isLeaf=true, confidence=1.0, support=0.16666666666666666 Records: [1
 * 1 0 1 0 ] [0 1 0 1 0 ] [1 0 0 1 0 ] [0 0 0 1 0 ] Attribute Columns: 1 0
 * 
 * LEAF NODE CREATED --> homogenous records Node [numChildren=0, feature=null,
 * label=1, isLeaf=true, confidence=1.0, support=0.25 Records: [1 1 0 0 0 ] [0 1
 * 0 0 0 ] [0 0 0 0 1 ] [0 1 0 0 1 ] [1 0 0 0 0 ] [1 0 0 0 1 ] Attribute
 * Columns: 1 0 4
 * 
 * 
 * PATHS TO NODES Path:Root Node:feature->Column "2" is 1 Path:Root,L
 * Node:feature->Column "0" is 1 Path:Root,L,L Node:label->4, confidence-> 1.0,
 * support->0.25 Path:Root,L,R Node:label->1, confidence-> 1.0,
 * support->0.20833333333333334 Path:Root,R Node:feature->Column "3" is 1
 * Path:Root,R,L Node:feature->Column "4" is 1 Path:Root,R,L,L Node:label->3,
 * confidence-> 1.0, support->0.125 Path:Root,R,L,R Node:label->2, confidence->
 * 1.0, support->0.16666666666666666 Path:Root,R,R Node:label->1, confidence->
 * 1.0, support->0.25
 * 
 * CLASSIFICATION AND VALIDAITON USING GINI MEASURE Training Error: 0.0 Random
 * Sampling Validaiton Error: 0.07726190476190475 Leave-One-Out Validation
 * Error: 0.08333333333333333
 * 
 * CLASSIFICATION AND VALIDAITON USING CLASS ERROR ENTROPY Training Error: 0.0
 * Random Sampling Validaiton Error: 0.4811666666666666 Leave-One-Out Validation
 * Error: 0.5
 */
