package app;

import org.javatuples.Pair;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import classifier.nearestneighbor.NearestNeighbor;
import data.attribute.AttributeInfo;
import data.attribute.NominalAttributeInfo;
import data.feature.DefaultFeautureStrategy;
import data.feature.FeatureStrategy;
import data.feature.NoFeatureStrategy;
import data.record.Record;
import fileio.HomogenousIO;
import majorityrule.WeightedMajorityRule;

public class Assignment2Part4 {
	public static void main(String[] args) throws FileNotFoundException {
		// Declare the feature strategy and attribute information
		FeatureStrategy featureStrategy = new DefaultFeautureStrategy();
		List<AttributeInfo<?>> attributeInfos = new LinkedList<>();
		for (int i = 0; i < 400; i++) {
			attributeInfos.add(new NominalAttributeInfo(0, Arrays.asList("0", "1"), featureStrategy, true));
		}

		// Read in the training data
		HomogenousIO recordIO = new HomogenousIO(attributeInfos, new NoFeatureStrategy());
		List<Record> trainingRecords = recordIO.getTrainingData("DataMining-Data/digits_20by20_train.txt");

		// Construct the nearest neighbor classifier
		System.out.println("CONSTRUCTING NEAREST NEIGHBOR CLASSIFIER...");
		NearestNeighbor nearestNeighbor = new NearestNeighbor(
				new Pair<List<Record>, List<AttributeInfo<?>>>(trainingRecords, attributeInfos), 5,
				new WeightedMajorityRule());
		System.out.println(trainingRecords.size());

		/*
		 * System.out.println("DETERMINING OPTIMAL NUMBER OF NEIGHBORS AND \n" +
		 * "MAJORITY RULE FOR RECORDS IN \"test4\"");
		 *
		 * Pair<Integer, Double> optimalWeightedMajorityParams =
		 * nearestNeighbor.getOptimalNumNeighborsAndError();
		 * System.out.println("---------------------------");
		 * nearestNeighbor.setMajorityRule(new UnWeightedMajorityRule());
		 * Pair<Integer, Double> optimalUnWeightedMajorityParams =
		 * nearestNeighbor.getOptimalNumNeighborsAndError();
		 *
		 * String majorityRule = ""; if
		 * (optimalWeightedMajorityParams.getValue1() <
		 * optimalUnWeightedMajorityParams.getValue1()) { majorityRule =
		 * "WEIGHTED MAJORITY RULE"; nearestNeighbor.setMajorityRule(new
		 * WeightedMajorityRule());
		 * nearestNeighbor.setNumberNeighbors(optimalWeightedMajorityParams.
		 * getValue0()); } else { majorityRule =
		 * "OPTIMAL MAJORITY RULE IS -> UNWEIGHTED MAJORITY RULE";
		 * nearestNeighbor.setMajorityRule(new UnWeightedMajorityRule());
		 * nearestNeighbor.setNumberNeighbors(optimalUnWeightedMajorityParams.
		 * getValue0()); } System.out.println("OPTIMAL MAJORITY RULE IS -> " +
		 * majorityRule); System.out.println(
		 * "OPTIMAL NUMBER OF NEIGHBORS IS -> " +
		 * nearestNeighbor.getNumNeighbors()); System.out.println(
		 * "TRAINING ERROR IS -> " + nearestNeighbor.trainingError());
		 * System.out.println("LEAVE-ONE-OUT VALIDATION ERR IS " +
		 * nearestNeighbor.validateLeaveOneOut());
		 */
	}
}
