package app;

import java.io.FileNotFoundException;

import classifier.decisiontree.DecisionTree;
import entropy.GiniMeasure;
import fileio.BinaryRecordIO;

public class BinaryDTree {
	public static void main(String[] args) throws FileNotFoundException {
		BinaryRecordIO reader = new BinaryRecordIO(5);
		DecisionTree dTree = new DecisionTree(reader.getData("DataMining-Data/train1", true), new GiniMeasure());
		dTree.buildTree();
		System.out.println(dTree.trainingError());
		System.out.println(dTree.checkForDuplicateRecords());
		reader.writeData("out1", dTree.getRecords());
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
