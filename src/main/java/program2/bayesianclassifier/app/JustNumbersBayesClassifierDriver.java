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

/*
SAMPLE OUTPUT:

Running driver program for Program2-Part1-Subpart2: 
	Bayesian Classifer - Making Classifications Of Data That Consists Of Only Numbers

LOADING TRAINING DATA program2_data/part1/train1

BUILDING MODEL

CLASSIFYING TEST DATA program2_data/part1/test1
	and writing results to program2_data/part1/output1

TRAINING ERROR IS: 4.00%
LEAVE ONE OUT VALIDATION ERROR IS: 52.00%

LAPLACE ADJUSTED CONDITIONAL PROBABILITES:
ATTRIBUTE: ATTRIBUTE_1
  P(0|1)=0.73  P(1|1)=0.27
  P(0|2)=0.80  P(1|2)=0.20
  P(0|3)=0.20  P(1|3)=0.80
  P(0|4)=0.33  P(1|4)=0.67

ATTRIBUTE: ATTRIBUTE_2
  P(1|1)=0.42  P(2|1)=0.17  P(3|1)=0.42
  P(1|2)=0.67  P(2|2)=0.17  P(3|2)=0.17
  P(1|3)=0.67  P(2|3)=0.17  P(3|3)=0.17
  P(1|4)=0.15  P(2|4)=0.46  P(3|4)=0.38

ATTRIBUTE: ATTRIBTE_3
  P(1|1)=0.23  P(2|1)=0.23  P(3|1)=0.23  P(4|1)=0.31
  P(1|2)=0.29  P(2|2)=0.14  P(3|2)=0.29  P(4|2)=0.29
  P(1|3)=0.29  P(2|3)=0.29  P(3|3)=0.14  P(4|3)=0.29
  P(1|4)=0.21  P(2|4)=0.21  P(3|4)=0.29  P(4|4)=0.29

ATTRIBUTE: ATTRIBUTE_4
  P(0|1)=0.73  P(1|1)=0.27
  P(0|2)=0.20  P(1|2)=0.80
  P(0|3)=0.20  P(1|3)=0.80
  P(0|4)=0.50  P(1|4)=0.50
*/