package assignment2.bayesianclassifier.app;

import java.io.IOException;

import assignment2.bayesianclassifier.BayesClassifier;

public class HiringDataBayesClassifierDriver {
	public static void main(String[] args) throws IOException {
		System.out.println("Running driver program for Assignment2-Part1-Subpart3: "
				+ "\n\tBayesian Classifer - Making Hiring Decision Classification With Application Data\n");

		BayesClassifier classifier = new BayesClassifier();

		String trainingFile = "program2_data/part1/train2";
		String testFile = "program2_data/part1/test2";
		String outputFile = "program2_data/part1/output2";

		System.out.println("LOADING TRAINING DATA " + trainingFile + "\n");
		classifier.loadTrainingData(trainingFile);

		System.out.println("BUILDING MODEL\n");
		classifier.buildModel();

		System.out.println("CLASSIFYING TEST DATA " + testFile + "\n\tand writing results to " + outputFile + "\n");
		classifier.classifyData(testFile, outputFile);

		System.out.printf("TRAINING ERROR IS: %.2f%%%n", classifier.trainingError() * 100);
		System.out.printf("LEAVE ONE OUT VALIDATION ERROR IS: %.2f%%%n", classifier.validateLeaveOneOut() * 100);

		System.out.println("\nLAPLACE ADJUSTED CONDITIONAL PROBABILITES:");
		classifier.printLaplaceConditionalProbabilites(20);
	}
}
