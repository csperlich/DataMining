package program2.neuralnets;
/**
 * This class is based on the code for a Neural Network program given out to
 * students of Dr. Maniccam's Data Mining class at Eastern Michigan University. I have
 * altered it and extended it as per the requirements of the programming
 * assignment.
 *
 * @date Winter Semester, 2016.
 * @author Suchindran Maniccam
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import program2.data.Record;
import program2.data.RecordReader;
import program2.neuralnets.testerrorcomputers.NeuralTestErrorComputer;

public class NeuralNetwork {

	private int numberInputs;
	private int numberOutputs;

	private int numberMiddle;
	private int numberIterations;
	private int seed;//random generator seed
	private double rate;//learning rate

	private List<Record> records;//list of training records

	private double[] input;//inputs
	private double[] middle;//outputs at hidden nodes
	private double[] output;//outputs at output nodes

	private double[] errorMiddle;//errors at hidden nodes
	private double[] errorOut;//errors at output nodes

	private double[] thetaMiddle;//thetas at hidden nodes
	private double[] thetaOut;//thetas at output nodes

	private double[][] matrixMiddle;//weights between input/hidden nodes
	private double[][] matrixOut;//weights between hidden/output nodes

	private RecordReader recordReader;//reads and writes records
	private NeuralTestErrorComputer testErrorComputer;//computes errors for training error and validation error
	private boolean validationTrace = false;//enables trace for validation error
	private boolean trainingErrorTrace = false;//enables trace for training error

	public NeuralNetwork(NeuralTestErrorComputer.ComputerType neuralTestErrorComputerType) {
		this.recordReader = new RecordReader(true);
		this.testErrorComputer = NeuralTestErrorComputer.getNeuralTestErrorComputer(neuralTestErrorComputerType, this);
	}

	public void setValidationTrace(boolean validationTrace) {
		this.validationTrace = validationTrace;
	}

	public void setTrainingErrorTrace(boolean trainingErrorTrace) {
		this.trainingErrorTrace = trainingErrorTrace;
	}

	//method prints the weights and thetas of the network
	public void printNetwork() {
		System.out.println("MIDDLE WEIGHTS:");
		this.printMatrix(this.matrixMiddle);

		System.out.println("MIDDLE BIASES:");
		this.printArray(this.thetaMiddle);

		System.out.println("OUT WEIGHTS: ");
		this.printMatrix(this.matrixOut);

		System.out.println("OUT BIASES:");
		this.printArray(this.thetaOut);
	}

	//helper method to print an array
	private void printArray(double[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.printf("%10.4f", array[i]);
		}
		System.out.println("\n");

	}

	//helper method to print a matrix
	private void printMatrix(double[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.printf("%10.4f", matrix[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	//method loads training data and sets numberInputs and numberOutputs
	public void loadTrainingData(String trainingFile) throws FileNotFoundException {
		List<Record> trainingRecords = this.recordReader.readTrainingRecords(trainingFile);
		this.numberInputs = trainingRecords.get(0).getInputs().length;
		this.numberOutputs = trainingRecords.get(0).getOutputs().length;
		this.records = trainingRecords;
	}

	//method sets the parameters for the network
	//all weights and thetas get a random value between -1 and 1
	public void setParameters(int numberMiddle, int numberIterations, int seed, double rate) {
		this.numberMiddle = numberMiddle;
		this.numberIterations = numberIterations;
		this.seed = seed;
		this.rate = rate;

		Random rand = new Random(this.seed);

		this.input = new double[this.numberInputs];
		this.middle = new double[numberMiddle];
		this.output = new double[this.numberOutputs];

		this.errorMiddle = new double[numberMiddle];
		this.errorOut = new double[this.numberOutputs];

		this.thetaMiddle = new double[numberMiddle];
		for (int i = 0; i < numberMiddle; i++) {
			this.thetaMiddle[i] = 2 * rand.nextDouble() - 1;
		}

		this.thetaOut = new double[this.numberOutputs];
		for (int i = 0; i < this.numberOutputs; i++) {
			this.thetaOut[i] = 2 * rand.nextDouble() - 1;
		}

		this.matrixMiddle = new double[this.numberInputs][numberMiddle];
		for (int i = 0; i < this.numberInputs; i++) {
			for (int j = 0; j < numberMiddle; j++) {
				this.matrixMiddle[i][j] = 2 * rand.nextDouble() - 1;
			}
		}

		this.matrixOut = new double[numberMiddle][this.numberOutputs];
		for (int i = 0; i < numberMiddle; i++) {
			for (int j = 0; j < this.numberOutputs; j++) {
				this.matrixOut[i][j] = 2 * rand.nextDouble() - 1;
			}
		}
	}

	//trains the neural network using back propogation
	public void train() {
		for (int i = 0; i < this.numberIterations; i++) {
			for (int j = 0; j < this.records.size(); j++) {
				this.forwardCalculation(this.records.get(j).getInputs());
				this.backwardCalculation(this.records.get(j).getOutputs());
			}
		}
	}

	//method performs the back propagation, computing errors and updating weights and thetas
	private void backwardCalculation(double[] trainingOutput) {

		//compute error at each output node
		for (int i = 0; i < this.numberOutputs; i++) {
			this.errorOut[i] = this.output[i] * (1 - this.output[i]) * (trainingOutput[i] - this.output[i]);
		}

		//compute error at each hidden node
		for (int i = 0; i < this.numberMiddle; i++) {
			double sum = 0;

			for (int j = 0; j < this.numberOutputs; j++) {
				sum += this.matrixOut[i][j] * this.errorOut[j];
			}

			this.errorMiddle[i] = this.middle[i] * (1 - this.middle[i]) * sum;
		}

		//update weights between hidden/output nodes
		for (int i = 0; i < this.numberMiddle; i++) {
			for (int j = 0; j < this.numberOutputs; j++) {
				this.matrixOut[i][j] += this.rate * this.middle[i] * this.errorOut[j];
			}
		}

		//update weights between input/hidden nodes
		for (int i = 0; i < this.numberInputs; i++) {
			for (int j = 0; j < this.numberMiddle; j++) {
				this.matrixMiddle[i][j] += this.rate * this.input[i] * this.errorMiddle[j];
			}
		}

		//update thetas at output nodes
		for (int i = 0; i < this.numberOutputs; i++) {
			this.thetaOut[i] += this.rate * this.errorOut[i];
		}

		//update thetas at hidden nodes
		for (int i = 0; i < this.numberMiddle; i++) {
			this.thetaMiddle[i] += this.rate * this.errorMiddle[i];
		}

	}

	//method performs forward pass - computes output from a given input
	private void forwardCalculation(double[] trainingInput) {

		//feed inputs of record
		for (int i = 0; i < this.numberInputs; i++) {
			this.input[i] = trainingInput[i];
		}

		//for each hidden node
		for (int i = 0; i < this.numberMiddle; i++) {
			double sum = 0;

			//compute the input at hidden node
			for (int j = 0; j < this.numberInputs; j++) {
				sum += this.input[j] * this.matrixMiddle[j][i];
			}

			//add theta
			sum += this.thetaMiddle[i];

			//pass through sigmoid function to compute output at hidden node
			this.middle[i] = 1 / (1 + Math.exp(-sum));
		}

		//for each output node
		for (int i = 0; i < this.numberOutputs; i++) {
			double sum = 0;

			//compute input at output node
			for (int j = 0; j < this.numberMiddle; j++) {
				sum += this.middle[j] * this.matrixOut[j][i];
			}

			//add theta
			sum += this.thetaOut[i];

			//pass through sigmoid function to compute output at output node
			this.output[i] = 1 / (1 + Math.exp(-sum));
		}
	}

	//returns the output for a test record
	private double[] test(double[] input) {
		this.forwardCalculation(input);
		return this.output;
	}

	//method reads inputs from input file and writes outputs to output file
	public void testData(String testInputFile, String outputFile) throws IOException {

		//read records
		List<Record> testRecords = this.recordReader.readTestRecords(testInputFile);

		//test each record and set the classification
		for (int i = 0; i < testRecords.size(); i++) {
			Record record = testRecords.get(i);
			double[] output = this.test(record.getInputs()).clone();
			record.setOutput(output);
		}

		//write records to output file
		this.recordReader.writeRecords(testRecords, outputFile);
	}

	//method validates the network using the data from  file
	//validation error depends on the method of error computation used
	public double validate(String validationFile) throws IOException {
		//read records from the file
		List<Record> records = this.recordReader.readValidationRecords(validationFile);

		double error = 0;
		if (this.validationTrace) {
			System.out.println("\nVALIDATION TRACE:");
		}

		//for each record, calculate error
		for (int i = 0; i < records.size(); i++) {

			double[] input = records.get(i).getInputs();
			double[] actualOutput = records.get(i).getOutputs();
			double[] predictedOutput = this.test(input);

			if (this.validationTrace) {
				this.printTrace(input, actualOutput, predictedOutput);
			}

			error += this.computeError(actualOutput, predictedOutput);
		}

		//return average error
		return error / records.size();
	}

	//prints a trace of a record validation, reporting the input, predicted output, and actual output
	private void printTrace(double[] input, double[] actual, double[] predicted) {
		System.out.print("input = ");
		for (int j = 0; j < input.length; j++) {
			System.out.printf("%.2f ", input[j]);
		}
		System.out.print(" | ");

		System.out.print("predicted = ");
		for (int j = 0; j < predicted.length; j++) {
			System.out.printf("%.2f ", predicted[j]);
		}
		System.out.print(" | ");

		System.out.print("actual = ");
		for (int j = 0; j < actual.length; j++) {
			System.out.printf("%.2f ", actual[j]);
		}
		System.out.println("\n");
	}

	//computes the error based on the method of error computation
	private double computeError(double[] actualOutput, double[] predictedOutput) {
		return this.testErrorComputer.computeError(actualOutput, predictedOutput);
	}

	//calculates the training error for the neural network
	//training error depends on the method of error computation used
	public double trainingError() {

		double error = 0;

		if (this.trainingErrorTrace) {
			System.out.println("\nTRAINING ERROR TRACE:");
		}

		//for each training record, compute the error
		for (int i = 0; i < this.records.size(); i++) {

			double[] input = this.records.get(i).getInputs();
			double[] actualOutput = this.records.get(i).getOutputs();
			double[] predictedOutput = this.test(input);
			if (this.trainingErrorTrace) {
				this.printTrace(input, actualOutput, predictedOutput);
			}

			error += this.computeError(actualOutput, predictedOutput);
		}

		//return the average error
		return error / this.records.size();
	}

	//method returns the parameters of the neural network as a String
	public String getParamString() {
		StringBuilder sb = new StringBuilder();
		sb.append("INPUT NODES: " + this.numberInputs + "\n");
		sb.append("HIDDEN NODES: " + this.numberMiddle + "\n");
		sb.append("OUTPUT NODES: " + this.numberOutputs + "\n");
		sb.append("TRAINING ITERATIONS: " + this.numberIterations + "\n");
		sb.append("LEARNING RATE: " + this.rate + "\n");
		sb.append("RANDOM SEED: " + this.seed + "\n");
		return sb.toString();
	}

	public List<Record> getTrainingData() {
		return this.records;
	}

	public RecordReader getRecordReader() {
		return this.recordReader;
	}

	public String getTestErrorString() {
		return this.testErrorComputer.toString();
	}
}
