package app;

import java.io.FileNotFoundException;
import java.util.List;

import classifier.decisiontree.DecisionTree;
import data.record.Record;
import entropy.GiniMeasure;
import fileio.BinaryRecordIO;

public class BinaryDTree {
	public static void main(String[] args) throws FileNotFoundException {
		BinaryRecordIO reader = new BinaryRecordIO(5);
		DecisionTree dTree = new DecisionTree(reader.getTrainingData("DataMining-Data/train1"), new GiniMeasure());
		dTree.buildTree();

		System.out.println(dTree.trainingError());
		dTree.printTree();
		List<Record> testData = reader.getTestData("DataMining-Data/test1");
		dTree.classify(testData);
		reader.writeData("output/out1", testData);

		System.out.println(dTree.getAttributeInfos());

		// System.out.println("classification error = " +
		// dTree.classificationError(testData));
		System.out.println("leave one out sampling validation error = " + dTree.validateLeaveOneOut());
		System.out.println("random sampling validation error = " + dTree.validateRandomSampling(20));
		System.out.println(dTree.getAttributeInfos());

		// dTree.printData();
		// dTree.printTree();

		/*
		 * List<AttributeInfo<?>> attInfos = dTree.getAttributeInfos(); for
		 * (AttributeInfo<?> attInfo : attInfos) {
		 *
		 * Feature<?> feature = attInfo.getFeatures().get(0); List<List<Record>>
		 * newRecs = feature.splitRecords(dTree.getRecords());
		 * System.out.println(); for (List<Record> recs : newRecs) { for (Record
		 * rec : recs) { System.out.println(rec); } System.out.println(); }
		 * System.out.println(); }
		 */

	}
}
