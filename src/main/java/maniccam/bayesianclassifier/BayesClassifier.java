package maniccam.bayesianclassifier;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import maniccam.data.DataConverter;

/**
 * This is the code for a Bayesian Classifer program given out to students of
 * Dr. Maniccam's Data Mining class at Eastern Michigan University.
 *
 * @date Winter Semester, 2016.
 * @author Suchindran Maniccam
 */

public class BayesClassifier {

	private static class Record {
		private int[] attributes;
		private int className;

		public Record(int[] attributes, int className) {
			this.attributes = attributes;
			this.className = className;
		}
	}

	private static class ClassificationResult {
		private int className;
		private double confidence;

		private ClassificationResult(int className, double confidence) {
			this.className = className;
			this.confidence = confidence;
		}
	}

	private DataConverter<Integer> dataConverter;

	private List<Record> records;
	private int[] attributeValues;
	private int numberRecords;
	private int numberAttributes;
	private int numberClasses;

	double[] classTable; // class frequencies
	double[][][] table; // conditional probabilities

	public BayesClassifier(DataConverter<Integer> dataConverter) {
		this.dataConverter = dataConverter;

		this.records = null;
		this.attributeValues = null;

		this.numberRecords = 0;
		this.numberAttributes = 0;
		this.numberClasses = 0;

		this.classTable = null;
		this.table = null;
	}

	// method loads training records from training file
	public void loadTrainingData(String trainingFile) throws IOException {

		Scanner inFile = new Scanner(new File(trainingFile));

		// read number of records, attributes, classes
		this.numberRecords = inFile.nextInt();
		this.numberAttributes = inFile.nextInt();
		this.numberClasses = inFile.nextInt();

		// read attribute values
		this.attributeValues = new int[this.numberAttributes];
		for (int i = 0; i < this.numberAttributes; i++) {
			this.attributeValues[i] = inFile.nextInt();
		}

		// empty list of records
		this.records = new ArrayList<Record>();

		// for each record
		for (int i = 0; i < this.numberRecords; i++) {
			// create attribute array
			int[] attributeArray = new int[this.numberAttributes];

			// read attributes and convert them to numerical form
			for (int j = 0; j < this.numberAttributes; j++) {

				String label = inFile.next();
				attributeArray[j] = this.convert(label, j + 1);
			}

			// read class and convert it to numerical form
			String label = inFile.next();
			int className = this.convert(label, this.numberAttributes + 1);

			// create record
			Record record = new Record(attributeArray, className);

			// add record to list of records
			this.records.add(record);

		}

		inFile.close();

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
			this.classTable[this.records.get(i).className - 1] += 1;
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
			this.fill(i + 1);
		}
	}

	// method computes conditional probabilities for an attribute
	private void fill(int attribute) {

		// find number of attribute values
		int attributeValues = this.attributeValues[attribute - 1];

		// create array to hold probabilities
		this.table[attribute - 1] = new double[this.numberClasses][attributeValues];

		// initialize probabilities
		for (int i = 0; i < this.numberClasses; i++) {
			for (int j = 0; j < attributeValues; j++) {
				this.table[attribute - 1][i][j] = 0;
			}
		}

		// compute class-attribute frequencies
		for (int k = 0; k < this.numberRecords; k++) {
			int i = this.records.get(k).className - 1;
			int j = this.records.get(k).attributes[attribute - 1] - 1;
			this.table[attribute - 1][i][j] += 1;
		}

		// compute probabilities, use laplace correction
		for (int i = 0; i < this.numberClasses; i++) {
			for (int j = 0; j < attributeValues; j++) {

				double value = (this.table[attribute - 1][i][j] + 1)
						/ (this.classTable[i] * this.numberRecords + attributeValues);
				this.table[attribute - 1][i][j] = value;

			}
		}
	}

	private double findProbability(int className, int[] attributes) {
		double value;
		double product = 1;

		for (int i = 0; i < this.numberAttributes; i++) {
			value = this.table[i][className - 1][attributes[i] - 1];
			product = product * value;
		}

		return product * this.classTable[className - 1];
	}

	public void classifyData(String testFile, String classifiedFile) throws IOException {
		Scanner inFile = new Scanner(new File(testFile));
		PrintWriter outFile = new PrintWriter(new FileWriter(classifiedFile));

		int numberRecords = inFile.nextInt();

		for (int i = 0; i < numberRecords; i++) {
			int[] attributeArray = new int[this.numberAttributes];

			for (int j = 0; j < this.numberAttributes; j++) {
				String label = inFile.next();
				attributeArray[j] = this.convert(label, j + 1);
			}

			ClassificationResult result = this.classify(attributeArray);

			String label = this.convert(result.className, this.numberAttributes + 1);
			outFile.printf("class = %s, confidence = %d%%%n", label, (int) (result.confidence * 100));
		}

		inFile.close();
		outFile.close();
	}

	public void validate(String validationFile) throws IOException {
		Scanner inFile = new Scanner(new File(validationFile));

		int numberRecords = inFile.nextInt();

		int numberErrors = 0;

		for (int i = 0; i < numberRecords; i++) {
			int[] attributeArray = new int[this.numberAttributes];

			for (int j = 0; j < this.numberAttributes; j++) {
				String label = inFile.next();
				attributeArray[j] = this.convert(label, j + 1);
			}

			ClassificationResult result = this.classify(attributeArray);
			int predictedClass = result.className;

			String label = inFile.next();
			int actualClass = this.convert(label, this.numberAttributes + 1);

			if (predictedClass != actualClass) {
				numberErrors += 1;
			}

		}
		// find and print error rate
		double errorRate = 100.0 * numberErrors / numberRecords;
		System.out.println(errorRate + " percent error");

		inFile.close();
	}

	private int convert(String label, int column) {
		return this.dataConverter.convert(label, column);
	}

	/**
	 *
	 * @param value
	 * @return the string associated with the int value for the given column
	 */
	private String convert(int value, int column) {
		return this.dataConverter.convert(value, column);
	}

	public void printLaplaceConditionalProbabilites(int colWidth) {
		for (int i = 0; i < this.table.length; i++) {

			// print header
			System.out.println("ATTRIBUTE: " + (i + 1));

			for (int j = 0; j < this.table[i].length; j++) {
				for (int k = 0; k < this.table[i][j].length; k++) {
					System.out.printf("%" + colWidth + "s",
							"P(" + this.convert(k + 1, i + 1) + "|" + this.convert(j + 1, this.numberAttributes + 1)
									+ ")=" + String.format("%4.2f", this.table[i][j][k]));
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	private ClassificationResult classify(int[] attributes) {
		double maxProbability = 0;
		int maxClass = -1;

		double probabilitySum = 0;

		for (int i = 0; i < this.numberClasses; i++) {
			double probability = this.findProbability(i + 1, attributes);
			probabilitySum += probability;

			if (probability > maxProbability) {
				maxProbability = probability;
				maxClass = i;
			}
		}

		double confidence = maxProbability / probabilitySum;

		return new ClassificationResult(maxClass + 1, confidence);
	}

	public double trainingError() {
		return this.classificationError(this.records);
	}

	public double classificationError(List<Record> records) {
		int numIncorrect = 0;
		for (Record record : records) {
			int classification = this.classify(record.attributes).className;
			if (classification != record.className) {
				numIncorrect++;
			}
		}
		return (double) numIncorrect / records.size();
	}

	public double validateLeaveOneOut() {
		double trainingError = 0;
		for (int i = 0; i < this.records.size(); i++) {

			// remove one record
			Record theOneOut = this.records.remove(i);

			// build the classifer
			this.numberRecords--;
			this.buildModel();

			// test the classifer against the one out and update the error
			int theClass = this.classify(theOneOut.attributes).className;
			if (theClass != theOneOut.className) {
				trainingError += 1;
			}

			// put the record back in
			this.records.add(i, theOneOut);
			this.numberRecords++;

		}

		return trainingError / this.records.size();
	}

}
