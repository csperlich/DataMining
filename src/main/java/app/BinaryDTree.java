package app;

import java.io.FileNotFoundException;

import decisiontree.DecisionTree;
import entropy.GiniMeasure;
import fileio.BinaryDataReader;

public class BinaryDTree {
	public static void main(String[] args) throws FileNotFoundException {
		BinaryDataReader reader = new BinaryDataReader("DataMining-Data/train1", 5);
		DecisionTree dTree = new DecisionTree(reader.getData(), new GiniMeasure());
		dTree.buildTree();

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
