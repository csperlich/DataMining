package app;

import org.javatuples.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import classifier.nearestneighbor.NearestNeighbor;
import data.attribute.AttributeInfo;
import data.attribute.ContinuousAttribute;
import data.attribute.NominalAttributeInfo;
import data.feature.DefaultFeautureStrategy;
import data.feature.FeatureStrategy;
import data.feature.NoFeatureStrategy;
import data.record.Record;
import fileio.HomogenousIO;
import majorityrule.UnWeightedMajorityRule;
import majorityrule.WeightedMajorityRule;

public class Assignment2Part3 {

	public static void main(String[] args) throws FileNotFoundException {
		// Declare the feature strategy and attribute information
		FeatureStrategy featureStrategy = new DefaultFeautureStrategy();
		List<AttributeInfo<?>> attributeInfos = new LinkedList<>();
		attributeInfos.add(new ContinuousAttribute(0, "Credit", null, featureStrategy, 500.0, 900.0, true));
		attributeInfos.add(new ContinuousAttribute(1, "Income", null, featureStrategy, 30, 40, true));
		attributeInfos.add(new ContinuousAttribute(2, "Age", null, featureStrategy, 30.0, 80.0, true));
		attributeInfos.add(new NominalAttributeInfo(3, Arrays.asList("male", "female"), featureStrategy, true));
		attributeInfos.add(
				new NominalAttributeInfo(4, Arrays.asList("single", "divorced", "married"), featureStrategy, true));

		// Read in the training data
		HomogenousIO recordIO = new HomogenousIO(attributeInfos, new NoFeatureStrategy());
		List<Record> trainingRecords = recordIO.getTrainingData("DataMining-Data/train4");

		// Construct the nearest neighbor classifier
		System.out.println("CONSTRUCTING NEAREST NEIGHBOR CLASSIFIER...");
		NearestNeighbor nearestNeighbor = new NearestNeighbor(
				new Pair<List<Record>, List<AttributeInfo<?>>>(trainingRecords, attributeInfos), 5,
				new WeightedMajorityRule());

		// Classify the data with trace on
		System.out.println("DETERMINING OPTIMAL NUMBER OF NEIGHBORS AND \n" + "MAJORITY RULE FOR RECORDS IN \"test4\"");

		Pair<Integer, Double> optimalWeightedMajorityParams = nearestNeighbor.getOptimalNumNeighborsAndError();
		System.out.println("---------------------------");
		nearestNeighbor.setMajorityRule(new UnWeightedMajorityRule());
		Pair<Integer, Double> optimalUnWeightedMajorityParams = nearestNeighbor.getOptimalNumNeighborsAndError();

		String majorityRule = "";
		if (optimalWeightedMajorityParams.getValue1() < optimalUnWeightedMajorityParams.getValue1()) {
			majorityRule = "WEIGHTED MAJORITY RULE";
			nearestNeighbor.setMajorityRule(new WeightedMajorityRule());
			nearestNeighbor.setNumberNeighbors(optimalWeightedMajorityParams.getValue0());
		} else {
			majorityRule = "OPTIMAL MAJORITY RULE IS -> UNWEIGHTED MAJORITY RULE";
			nearestNeighbor.setMajorityRule(new UnWeightedMajorityRule());
			nearestNeighbor.setNumberNeighbors(optimalUnWeightedMajorityParams.getValue0());
		}
		System.out.println("OPTIMAL MAJORITY RULE IS -> " + majorityRule);
		System.out.println("OPTIMAL NUMBER OF NEIGHBORS IS -> " + nearestNeighbor.getNumNeighbors());
		System.out.println("TRAINING ERROR IS -> " + nearestNeighbor.trainingError());
		System.out.println("LEAVE-ONE-OUT VALIDATION ERR IS " + nearestNeighbor.validateLeaveOneOut());

		// Read in the test data
		List<Record> testRecords = recordIO.getTestData("DataMining-Data/test4");
		nearestNeighbor.classify(testRecords);

		// Classify the data with trace on
		System.out.println("PRINTING CLASSIFICATION TRACE FOR RECORD SET \"test4\"");
		System.out.println(
				"USING " + nearestNeighbor.getNumNeighbors() + " NEAREST NEIGHBORS AND WEIGHTED MAJORITY RULE...\n");
		System.out.println("==================================================\t");
		nearestNeighbor.classify(testRecords);

		// Print the unNormalized test records with their classifications to
		// file. Have to reread the files since my NearestNeighor classifier
		// normalizes everything
		System.out.println("WRITING \"test4\" CLASSIFACTIONS TO OUTPUT FILE \"results4\"...\n");
		List<Record> unNormalizedTestRecords = recordIO.getRawRecords("DataMining-Data/test4");
		PrintWriter recordWriter = new PrintWriter(new File("output/results4"));
		for (int i = 0; i < unNormalizedTestRecords.size(); i++) {
			Record record = unNormalizedTestRecords.get(i);
			record.setLabel(testRecords.get(i).getLabel());
			recordWriter.println(record.csvString(' ') + record.getLabel());
		}
		recordWriter.close();

	}
}
