package app;

import java.io.FileNotFoundException;
import java.util.List;

import data.AttributeInfo;
import data.Feature;
import data.Record;
import decisiontree.DecisionTree;
import fileio.BinaryDataReader;

public class BinaryDTree {
	public static void main(String[] args) throws FileNotFoundException {
		BinaryDataReader reader = new BinaryDataReader("DataMining-Data/train1", 5);
		DecisionTree dTree = new DecisionTree(reader.getData());

		dTree.printData();

		AttributeInfo<?>[] attInfos = dTree.getAttributeInfos();
		Feature<?> feature = attInfos[0].getFeatures().get(0);
		List<List<Record>> newRecs = feature.splitRecords(dTree.getRecords());
		System.out.println();
		for (List<Record> recs : newRecs) {
			for (Record rec : recs) {
				System.out.println(rec);
			}
			System.out.println();
		}
	}
}
