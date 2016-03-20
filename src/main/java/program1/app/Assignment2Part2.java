package program1.app;

import org.javatuples.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import program1.classifier.nearestneighbor.NearestNeighbor;
import program1.data.attribute.AttributeInfo;
import program1.data.attribute.ContinuousAttribute;
import program1.data.attribute.OrdinalAttribute;
import program1.data.feature.FeatureStrategy;
import program1.data.feature.NoFeatureStrategy;
import program1.data.record.Record;
import program1.fileio.HomogenousIO;
import program1.majorityrule.UnWeightedMajorityRule;
import program1.majorityrule.WeightedMajorityRule;

public class Assignment2Part2 {
	public static void main(String[] args) throws FileNotFoundException {

		// Declare the feature strategy and attribute information
		FeatureStrategy featureStrategy = new NoFeatureStrategy();
		List<AttributeInfo<?>> attributeInfos = new LinkedList<>();
		attributeInfos.add(new ContinuousAttribute(0, "Score", null, featureStrategy, 0.0, 100.0, true));
		attributeInfos.add(new ContinuousAttribute(1, "GPA", null, featureStrategy, 0.0, 4.0, true));
		attributeInfos.add(new OrdinalAttribute(2, "Grades", Arrays.asList("C", "B", "A"), featureStrategy, true));

		// Read in the training data
		HomogenousIO recordIO = new HomogenousIO(attributeInfos, new NoFeatureStrategy());
		List<Record> trainingRecords = recordIO.getTrainingData("program1_data/train3");

		// Construct the nearest neighbor classifier
		System.out.println("CONSTRUCTING NEAREST NEIGHBOR CLASSIFIER...");
		NearestNeighbor nearestNeighbor = new NearestNeighbor(
				new Pair<List<Record>, List<AttributeInfo<?>>>(trainingRecords, attributeInfos), 5,
				new WeightedMajorityRule());

		// Read in the test data
		List<Record> testRecords = recordIO.getTestData("program1_data/test3");

		// Classify the data with trace on
		System.out.println("PRINTING CLASSIFICATION TRACE FOR RECORD SET \"test3\"");
		System.out.println("USING 5 NEAREST NEIGHBORS AND WEIGHTED MAJORITY RULE...\n");
		System.out.println("==================================================\t");
		nearestNeighbor.setTrace(true);
		nearestNeighbor.classify(testRecords);
		nearestNeighbor.setTrace(false);

		// Print the unNormalized test records with their classifications to
		// file. Have to reread the files since my NearestNeighor classifier
		// normalizes everything
		System.out.println("WRITING \"test3\" CLASSIFACTIONS TO OUTPUT FILE \"results3\"...\n");
		List<Record> unNormalizedTestRecords = recordIO.getRawRecords("program1_data/test3");
		PrintWriter recordWriter = new PrintWriter(new File("program1_data/output3"));
		for (int i = 0; i < unNormalizedTestRecords.size(); i++) {
			Record record = unNormalizedTestRecords.get(i);
			record.setLabel(testRecords.get(i).getLabel());
			recordWriter.println(record.csvString(' ') + record.getLabel());
		}
		recordWriter.close();

		// Print out statistics for different runs
		System.out.println("PRINTING OUT STATISTICS FOR VARIOUS RUNS");
		System.out.println("==================================================\n");

		System.out.println("USING 3 NEAREST NEIGHBORS AND WEIGHTED MAJORITY RULE...");
		System.out.println("-------------------------------------------------------");
		// testRecords = recordIO.getTestData("DataMining-Data/test3");
		nearestNeighbor.setNumberNeighbors(3);
		runTest(nearestNeighbor, testRecords);

		System.out.println("USING 10 NEAREST NEIGHBORS AND WEIGHTED MAJORITY RULE...");
		System.out.println("-------------------------------------------------------");
		nearestNeighbor.setNumberNeighbors(10);
		runTest(nearestNeighbor, testRecords);

		System.out.println("USING 20 NEAREST NEIGHBORS AND WEIGHTED MAJORITY RULE...");
		System.out.println("-------------------------------------------------------");
		nearestNeighbor.setNumberNeighbors(20);
		runTest(nearestNeighbor, testRecords);

		System.out.println("USING 3 NEAREST NEIGHBORS AND UNWEIGHTED MAJORITY RULE...");
		System.out.println("-------------------------------------------------------");
		nearestNeighbor.setMajorityRule(new UnWeightedMajorityRule());
		nearestNeighbor.setNumberNeighbors(3);
		runTest(nearestNeighbor, testRecords);

		System.out.println("USING 5 NEAREST NEIGHBORS AND UNWEIGHTED MAJORITY RULE...");
		System.out.println("-------------------------------------------------------");
		nearestNeighbor.setNumberNeighbors(5);
		runTest(nearestNeighbor, testRecords);

		System.out.println("USING 10 NEAREST NEIGHBORS AND UNWEIGHTED MAJORITY RULE...");
		System.out.println("-------------------------------------------------------");
		nearestNeighbor.setNumberNeighbors(10);
		runTest(nearestNeighbor, testRecords);

		System.out.println("USING 20 NEAREST NEIGHBORS AND UNWEIGHTED MAJORITY RULE...");
		System.out.println("-------------------------------------------------------");
		nearestNeighbor.setNumberNeighbors(20);
		runTest(nearestNeighbor, testRecords);

	}

	public static void runTest(NearestNeighbor nearestNeighbor, List<Record> testRecords) {
		nearestNeighbor.classify(testRecords);
		System.out.println("Classifications: ");
		for (Record record : testRecords) {
			System.out.println(record.getLabel());
		}
		System.out.println("Training Error: " + nearestNeighbor.trainingError());
		System.out.println("Leave-One-Out Validation: " + nearestNeighbor.validateLeaveOneOut());
		System.out.println();
	}
}
