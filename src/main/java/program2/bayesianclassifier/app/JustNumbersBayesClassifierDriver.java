package program2.bayesianclassifier.app;

import java.io.IOException;

import program2.bayesianclassifier.BayesClassifier;

public class JustNumbersBayesClassifierDriver {
	public static void main(String[] args) throws IOException {
		System.out.println("Running driver program for Program2-Part1-Subpart2: "
				+ "\n\tBayesian Classifer - Making Classifications Of Data That Consists Of Only Numbers\n");

		BayesClassifier classifier = new BayesClassifier();

		String trainingFile = "program2_data/part1/train1";
		String testFile = "program2_data/part1/test1";
		String outputFile = "program2_data/part1/output1";

		System.out.println("LOADING TRAINING DATA " + trainingFile + "\n");
		classifier.loadTrainingData(trainingFile);

		System.out.println("BUILDING MODEL\n");
		classifier.buildModel();

		System.out.println("CLASSIFYING TEST DATA " + testFile + "\n\tand writing results to " + outputFile + "\n");
		classifier.classifyData(testFile, outputFile);

		System.out.printf("TRAINING ERROR IS: %.2f%%%n", classifier.trainingError() * 100);
		System.out.printf("LEAVE ONE OUT VALIDATION ERROR IS: %.2f%%%n", classifier.validateLeaveOneOut() * 100);

		System.out.println("\nLAPLACE ADJUSTED CONDITIONAL PROBABILITES:");
		classifier.printLaplaceConditionalProbabilites(13);
	}
}
