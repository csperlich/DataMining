package program2.bayesianclassifier.app;

import java.io.IOException;

import program2.bayesianclassifier.BayesClassifier;

public class HiringDataBayesClassifierDriver {
	public static void main(String[] args) throws IOException {
		System.out.println("Running driver program for Program2-Part1-Subpart3: "
				+ "\n\tBayesian Classifer - Making Hiring Decisions With Application Data\n");

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

/*
SAMPLE OUTPUT:

Running driver program for Program2-Part1-Subpart3: 
	Bayesian Classifer - Making Hiring Decisions With Application Data

LOADING TRAINING DATA program2_data/part1/train2

BUILDING MODEL

CLASSIFYING TEST DATA program2_data/part1/test2
	and writing results to program2_data/part1/output2

TRAINING ERROR IS: 22.92%
LEAVE ONE OUT VALIDATION ERROR IS: 33.33%

LAPLACE ADJUSTED CONDITIONAL PROBABILITES:
ATTRIBUTE: NUMBER OF LANGUAGES
        P(0|no)=0.29        P(1|no)=0.32        P(2|no)=0.39
      P(0|hire)=0.08      P(1|hire)=0.54      P(2|hire)=0.38

ATTRIBUTE: JAVA KNOWLEDGE
       P(no|no)=0.59     P(java|no)=0.41
     P(no|hire)=0.40   P(java|hire)=0.60

ATTRIBUTE: YEARS OF EXPERIENCE
        P(0|no)=0.39        P(1|no)=0.39        P(2|no)=0.21
      P(0|hire)=0.19      P(1|hire)=0.23      P(2|hire)=0.58

ATTRIBUTE: MAJOR
    P(other|no)=0.59       P(cs|no)=0.41
  P(other|hire)=0.52     P(cs|hire)=0.48

ATTRIBUTE: ACADEMIC STANDING
        P(D|no)=0.31        P(C|no)=0.31        P(B|no)=0.21        P(A|no)=0.17
      P(D|hire)=0.19      P(C|hire)=0.19      P(B|hire)=0.30      P(A|hire)=0.33
*/