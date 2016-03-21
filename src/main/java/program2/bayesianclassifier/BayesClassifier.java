package program2.bayesianclassifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import program2.data.Record;
import program2.data.RecordReader;

/**
 * This class is based on the code for a Bayesian Classifer program given out to
 * students of Dr. Maniccam's Data Mining class at Eastern Michigan University.
 * I have altered it and extended it as per the requirements of the programming
 * assignment.
 *
 * @date Winter Semester, 2016.
 * @author Suchindran Maniccam & Cade Sperlich
 */

public class BayesClassifier {

	/*
	 * Helper class to return confidence of a classification
	 */
	private static class ClassificationResult {
		private double className;
		private double confidence;

		private ClassificationResult(int className, double confidence) {
			this.className = className;
			this.confidence = confidence;
		}
	}

	private List<Record> records;
	private double[] attributeValues;
	private int numberRecords;
	private int numberAttributes;
	private int numberClasses;

	double[] classTable;// class frequencies
	double[][][] table;// conditional probabilities

	private RecordReader recordReader;

	public BayesClassifier() {
		this.recordReader = new RecordReader(false);
	}

	// method loads training records from training file
	public void loadTrainingData(String trainingFile) throws IOException {

		this.records = this.recordReader.readTrainingRecords(trainingFile);

		// set number of records, attributes, classes
		this.numberRecords = this.records.size();
		this.numberAttributes = this.records.get(0).getInputs().length;
		this.numberClasses = this.recordReader.getNumValues(this.numberAttributes);

		// read attribute values
		this.attributeValues = new double[this.numberAttributes];
		for (int i = 0; i < this.numberAttributes; i++) {
			this.attributeValues[i] = this.recordReader.getNumValues(i);
		}

	}

	// method build bayes's model
	public void buildModel() {

		// compute class frequencies
		this.fillClassTable();

		// compute conditional probabilities
		this.fillProbabilityTable();

	}

	// method computes class frequencies
	private void fillClassTable() {
		this.classTable = new double[this.numberClasses];

		// initialize frequencies
		for (int i = 0; i < this.numberClasses; i++) {
			this.classTable[i] = 0;
		}

		// compute frequencies
		for (int i = 0; i < this.numberRecords; i++) {
			this.classTable[(int) (this.records.get(i).getOutputs()[0])] += 1;
		}

		// normalize frequencies
		for (int i = 0; i < this.numberClasses; i++) {
			this.classTable[i] /= this.numberRecords;
		}
	}

	// method computes conditional probabilities
	private void fillProbabilityTable() {
		// array to store probabilites
		this.table = new double[this.numberAttributes][][];

		// compute probabilities
		for (int i = 0; i < this.numberAttributes; i++) {
			this.fill(i);
		}
	}

	// method computes conditional probabilities for an attribute
	private void fill(int attribute) {

		// find number of attribute values
		int attributeValues = (int) this.attributeValues[attribute];

		// create array to hold probabilities
		this.table[attribute] = new double[this.numberClasses][attributeValues];

		// initialize probabilities
		for (int i = 0; i < this.numberClasses; i++) {
			for (int j = 0; j < attributeValues; j++) {
				this.table[attribute][i][j] = 0;
			}
		}

		// compute class-attribute frequencies
		for (int k = 0; k < this.numberRecords; k++) {
			int i = (int) (this.records.get(k).getOutputs()[0]);
			int j = (int) (this.records.get(k).getInputs()[attribute]);
			this.table[attribute][i][j] += 1;
		}

		// compute probabilities, use laplace correction
		for (int i = 0; i < this.numberClasses; i++) {
			for (int j = 0; j < attributeValues; j++) {

				double value = (this.table[attribute][i][j] + 1)
						/ (this.classTable[i] * this.numberRecords + attributeValues);
				this.table[attribute][i][j] = value;

			}
		}
	}

	//method computes conditional probability of a class for a given attribute
	private double findProbability(int className, double[] attributes) {
		double value;
		double product = 1;

		for (int i = 0; i < this.numberAttributes; i++) {
			value = this.table[i][className][(int) (attributes[i])];
			product = product * value;
		}

		return product * this.classTable[className];
	}

	//method reads test records from test file and writes classes to classified file
	public void classifyData(String testFile, String classifiedFile) throws IOException {
		List<Record> testRecords = this.recordReader.readTestRecords(testFile);
		List<String> confidences = new ArrayList<>();
		for (int i = 0; i < testRecords.size(); i++) {

			ClassificationResult result = this.classify(testRecords.get(i).getInputs());
			testRecords.get(i).setOutput(new double[] { result.className });
			confidences.add(String.format("confidence = %d%%", (int) (result.confidence * 100)));
		}

		this.recordReader.writeRecords(testRecords, classifiedFile, confidences);
	}

	//method validates classifier using validation file and displays error rate
	public void validate(String validationFile) throws IOException {

		List<Record> validationRecords = this.recordReader.readValidationRecords(validationFile);

		int numberErrors = 0;
		for (int i = 0; i < validationRecords.size(); i++) {

			ClassificationResult result = this.classify(validationRecords.get(i).getInputs());
			double predictedClass = result.className;

			double actualClass = validationRecords.get(i).getOutputs()[0];

			if (predictedClass != actualClass) {
				numberErrors += 1;
			}

		}

		// find and print error rate
		double errorRate = 100.0 * numberErrors / this.numberRecords;
		System.out.println(errorRate + " percent error");

	}

	//returns the probability tables using laplace corrections
	public void printLaplaceConditionalProbabilites(int colWidth) {
		for (int i = 0; i < this.table.length; i++) {

			// print header
			System.out.println("ATTRIBUTE: " + (this.recordReader.getAttributeName(i)));

			//print probabilities for attribute i
			for (int j = 0; j < this.table[i].length; j++) {
				for (int k = 0; k < this.table[i][j].length; k++) {
					System.out.printf("%" + colWidth + "s",
							"P(" + this.recordReader.convert(i, k) + "|"
									+ this.recordReader.convert(this.numberAttributes, j) + ")="
									+ String.format("%4.2f", this.table[i][j][k]));
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	//method classifies a record and returns the result along with the confidence of classification
	private ClassificationResult classify(double[] attributes) {
		double maxProbability = 0;
		int maxClass = -1;

		double probabilitySum = 0;

		for (int i = 0; i < this.numberClasses; i++) {
			double probability = this.findProbability(i, attributes);
			probabilitySum += probability;

			if (probability > maxProbability) {
				maxProbability = probability;
				maxClass = i;
			}
		}

		double confidence = maxProbability / probabilitySum;
		return new ClassificationResult(maxClass, confidence);
	}

	//method computes the training error
	public double trainingError() {
		return this.classificationError(this.records);
	}

	//method computes classification error for a given set of records
	public double classificationError(List<Record> records) {
		int numIncorrect = 0;
		for (Record record : records) {
			double classification = this.classify(record.getInputs()).className;
			if (classification != record.getOutputs()[0]) {
				numIncorrect++;
			}
		}
		return (double) numIncorrect / records.size();
	}

	//method computes validation classification error using leave one out method
	public double validateLeaveOneOut() {
		double trainingError = 0;
		for (int i = 0; i < this.records.size(); i++) {

			// remove one record
			Record theOneOut = this.records.remove(i);

			// build the classifer
			this.numberRecords--;
			this.buildModel();

			// test the classifer against the one out and update the error
			double theClass = this.classify(theOneOut.getInputs()).className;
			if (theClass != theOneOut.getOutputs()[0]) {
				trainingError += 1;
			}

			// put the record back in
			this.records.add(i, theOneOut);
			this.numberRecords++;

		}

		// rebuild the original model
		this.buildModel();
		return trainingError / this.records.size();
	}
}