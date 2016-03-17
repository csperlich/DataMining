package maniccam.bayesianclassifier.app;

import java.io.IOException;

import maniccam.bayesianclassifier.BayesClassifier;
import maniccam.bayesianclassifier.data.Part1JustNumbersDataConverter;

public class Part1SubPart2Driver {
	public static void main(String[] args) throws IOException {
		BayesClassifier classifier = new BayesClassifier(new Part1JustNumbersDataConverter());

		classifier.loadTrainingData("program2_data/part1/train1");

		classifier.buildModel();

		classifier.classifyData("program2_data/part1/test1", "program2_data/part1/classified1");

		System.out.println("TRAINING ERROR IS: " + classifier.trainingError());
		System.out.println("LEAVE ONE OUT VALIDATION ERROR IS: " + classifier.validateLeaveOneOut());

	}
}
