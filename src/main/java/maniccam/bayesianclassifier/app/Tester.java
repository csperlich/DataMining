package maniccam.bayesianclassifier.app;

import java.io.IOException;

import maniccam.bayesianclassifier.BayesClassifier;
import maniccam.bayesianclassifier.data.RiskDataConverter;

/**
 * This is the code for a driver program for a Bayesian Classifer Class given
 * out to students of Dr. Maniccam's Data Mining class at Eastern Michigan
 * University.
 *
 * @date Winter Semester, 2016.
 * @author Suchindran Maniccam
 */
public class Tester {
	public static void main(String[] args) throws IOException {
		BayesClassifier classifier = new BayesClassifier(new RiskDataConverter());

		classifier.loadTrainingData("bayes_data/trainingfile");

		classifier.buildModel();

		classifier.classifyData("bayes_data/testfile", "bayes_data/classifiedfile");

		classifier.printLaplaceConditionalProbabilites(34);
	}
}
