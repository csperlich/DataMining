package maniccam.bayesianclassifier.app;

import java.io.IOException;

import maniccam.bayesianclassifier.BayesClassifier;
import maniccam.data.HiringDataBayesDataConverter;

public class HiringDataBayesClassifierDriver {
	public static void main(String[] args) throws IOException {
		BayesClassifier classifier = new BayesClassifier(new HiringDataBayesDataConverter());

		System.out.println("LOADING TRAINING DATA \"program2_data/part1/train2\"\n");
		classifier.loadTrainingData("program2_data/part1/train2");

		System.out.println("BUILDING MODEL\n");
		classifier.buildModel();

		System.out.println("CLASSIFYING TEST DATA \"program2_data/part1/test2\""
				+ "\n\tand writing results to \"program2_data/part1/classified2\"\n");
		classifier.classifyData("program2_data/part1/test2", "program2_data/part1/classified2");

		System.out.println("TRAINING ERROR IS: " + classifier.trainingError());
		System.out.println("LEAVE ONE OUT VALIDATION ERROR IS: " + classifier.validateLeaveOneOut() + "\n");

		System.out.println("LAPLACE ADJUSTED CONDITIONAL PROBABILITES:\n");
		classifier.printLaplaceConditionalProbabilites(20);
	}
}