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

public class Assignment2Part4Validate {
	public static void main(String[] args) throws FileNotFoundException {
		// Declare the feature strategy and attribute information
		FeatureStrategy featureStrategy = new DefaultFeautureStrategy();
		List<AttributeInfo<?>> attributeInfos = new LinkedList<>();
		for (int i = 0; i < 400; i++) {
			attributeInfos.add(new NominalAttributeInfo(0, Arrays.asList("0", "1"), featureStrategy, true));
		}

		// Read in the training data
		HomogenousIO recordIO = new HomogenousIO(attributeInfos, new NoFeatureStrategy());
		List<Record> trainingRecords = recordIO.getTrainingData("DataMining-Data/digits_20by20_training_spaced");

		// Construct the nearest neighbor classifier
		System.out.println("CONSTRUCTING NEAREST NEIGHBOR CLASSIFIER WITH 1000 DIGIT FILES");
		System.out.println("USING WEIGHTED MAJORITY RULE AND 10 NEIGHBORS");
		NearestNeighbor nearestNeighbor = new NearestNeighbor(
				new Pair<List<Record>, List<AttributeInfo<?>>>(trainingRecords, attributeInfos), 5,
				new WeightedMajorityRule());

		System.out.println("CALCULATING VALIDAITON ERROR... THIS WILL TAKE ABOUT 30 SECONDS...");
		System.out.println("RANDOM SAMPLING VALIDATION ERROR IS " + nearestNeighbor.validateRandomSampling(10));

		System.out.println("GOODBYE!");

	}
}
