package app;

import org.javatuples.Pair;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import classifier.decisiontree.DecisionTree;
import data.attribute.AttributeInfo;
import data.attribute.NominalAttributeInfo;
import data.feature.DefaultFeautureStrategy;
import data.feature.FeatureStrategy;
import data.record.Record;
import entropy.GiniMeasure;
import entropy.ShannonEntropy;
import fileio.BinaryIO;
import fileio.HomogenousIO;

public class BinaryDTree {
	public static void main(String[] args) throws FileNotFoundException {

		BinaryIO reader = new BinaryIO(new Pair<String, String>("0", "1"), 5, 0);
		Pair<List<Record>, List<AttributeInfo<?>>> data = reader.getData("DataMining-Data/train1");
		DecisionTree dTree = new DecisionTree(data.getValue0(), data.getValue1(), new GiniMeasure());
		// dTree.setEntropyMeasure(new ShannonEntropy());
		dTree.setTrace(true); // System.out.println(data.getValue1()); //
		System.out.println(data.getValue0());
		System.out.println(dTree.getAttributeInfos());
		System.out.println(dTree.getTrainingRecords());
		dTree.buildTree();

		dTree.setTrace(false);

		System.out.println(dTree.trainingError());
		dTree.printTree();
		List<Record> testData = reader.getTestData("DataMining-Data/test1");
		dTree.classify(testData);
		reader.writeData("output/out1", testData);

		System.out.println(dTree.getAttributeInfos());

		System.out.println("classification error = " + dTree.classificationError(testData));
		System.out.println("leave one out sampling validation error = " + dTree.validateLeaveOneOut());
		System.out.println("random sampling validation error = " + dTree.validateRandomSampling(20));
		System.out.println(dTree.getAttributeInfos());

		List<AttributeInfo<?>> attInf = new LinkedList<>();
		FeatureStrategy fStrat = new DefaultFeautureStrategy();

		attInf.add(new NominalAttributeInfo(0, "major", Arrays.asList("other", "cs"), fStrat, false));
		attInf.add(new NominalAttributeInfo(1, "java experience", Arrays.asList("no", "java"), fStrat, false));
		attInf.add(new NominalAttributeInfo(2, "c/c++ experience", Arrays.asList("no", "c/c++"), fStrat, false));
		attInf.add(new NominalAttributeInfo(3, "gpa", Arrays.asList("gpa<3", "gpa>3"), fStrat, false));
		attInf.add(new NominalAttributeInfo(4, "large project experience", Arrays.asList("small", "large"), fStrat,
				false));
		attInf.add(
				new NominalAttributeInfo(5, "years of experience", Arrays.asList("years<5", "years>5"), fStrat, false));

		HomogenousIO reader2 = new HomogenousIO(attInf, fStrat);
		List<Record> trainingData = reader2.getTrainingData("DataMining-Data/train2");
		DecisionTree dTree2 = new DecisionTree(trainingData, attInf, new ShannonEntropy());
		dTree2.buildClassifier();
		System.out.println(attInf);
		dTree2.printTree();
		System.out.println(dTree2.trainingError());
		List<Record> hiringTestData = reader2.getTestData("DataMining-Data/test2");
		System.out.println("leave one out validation -> " + dTree2.validateLeaveOneOut());
		System.out.println("random sampling validation -> " + dTree2.validateRandomSampling(100));
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
