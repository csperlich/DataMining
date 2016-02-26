package app;

import org.javatuples.Pair;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import classifier.nearestneighbor.NearestNeighbor;
import data.attribute.AttributeInfo;
import data.attribute.ContinuousAttribute;
import data.attribute.OrdinalAttribute;
import data.feature.FeatureStrategy;
import data.feature.NoFeatureStrategy;
import data.record.Record;
import fileio.HomogenousIO;

public class NearestNeighborApp {
	public static void main(String[] args) throws FileNotFoundException {

		FeatureStrategy featureStrategy = new NoFeatureStrategy();

		List<AttributeInfo<?>> attributeInfos = new LinkedList<>();
		attributeInfos.add(new ContinuousAttribute(0, "Score", null, featureStrategy, 0.0, 100.0, true));
		attributeInfos.add(new ContinuousAttribute(1, "GPA", null, featureStrategy, 0.0, 4.0, true));
		attributeInfos.add(new OrdinalAttribute(2, "Grades", Arrays.asList("C", "B", "A"), featureStrategy, true));

		HomogenousIO reader = new HomogenousIO(attributeInfos);
		List<Record> trainingRecords = reader.getTrainingData("DataMining-Data/train3");

		NearestNeighbor nearestNeighbor = new NearestNeighbor(
				new Pair<List<Record>, List<AttributeInfo<?>>>(trainingRecords, attributeInfos), 5);

		// System.out.println(trainingRecords);
		// System.out.println(attributeInfos);

		List<Record> testRecords = reader.getTestData("DataMining-Data/test3");
		System.out.println(nearestNeighbor.classify(testRecords.get(0)));
		nearestNeighbor.classify(testRecords);

		List<Record> unNormalizedTestRecords = reader.getRawRecords("DataMining-Data/test3");

		for (int i = 0; i < unNormalizedTestRecords.size(); i++) {
			unNormalizedTestRecords.get(i).setLabel(testRecords.get(i).getLabel());
			// System.out.println(unNormalizedTestRecords.get(i));
			System.out.println(testRecords.get(i));
		}
	}
}
