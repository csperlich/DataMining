package app;

import java.io.FileNotFoundException;

import DecisionTree.DecisionTree;
import FileIO.BinaryDataReader;

public class BinaryDTree {
	public static void main(String[] args) throws FileNotFoundException {
		BinaryDataReader reader = new BinaryDataReader("DataMining-Data/train1", 5);
		DecisionTree dTree = new DecisionTree(reader.getData());

		dTree.printData();

		/*
		 * for (Record record : dTree.getRecords()) { for (int i = 0; i <
		 * record.getSize(); i++) { System.out.print(record.getAttribute(i) +
		 * " "); } System.out.print(record.getLabel()); System.out.println();
		 * 
		 * }
		 */
	}
}
