package assignment2.neuralnets;
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

import assignment2.data.Record;
import assignment2.data.RecordReader;

public class NeuralNetwork {

	private int numberInputs;
	private int numberOutputs;

	private int numberMiddle;
	private int numberIterations;
	private int seed;
	private double rate;

	private List<Record> records;

	private double[] input;
	private double[] middle;
	private double[] output;

	private double[] errorMiddle;
	private double[] errorOut;

	private double[] thetaMiddle;
	private double[] thetaOut;

	private double[][] matrixMiddle;
	private double[][] matrixOut;

	private RecordReader recordReader;
	private NeuralTestErrorComputer testErrorComputer;

	public NeuralNetwork(NeuralTestErrorComputer.ComputerType neuralTestErrorComputerType) {
		this.recordReader = new RecordReader(true);
		this.testErrorComputer = NeuralTestErrorComputer.getNeuralTestErrorComputer(neuralTestErrorComputerType, this);
	}

	public void setWeights(double[][] weightsMiddle, double[][] weightsOut) {
		this.matrixMiddle = weightsMiddle;
		this.matrixOut = weightsOut;
	}

	public void printNetwork() {
		System.out.println("MIDDLE WEIGHTS:");
		this.printMatrix(this.matrixMiddle);

		System.out.println("MIDDLE BIASES:");
		this.printArray(this.thetaMiddle);

		// System.out.println("MIDDLE ERRORS:");
		// this.printArray(this.errorMiddle);

		System.out.println("OUT WEIGHTS: ");
		this.printMatrix(this.matrixOut);

		System.out.println("OUT BIASES:");
		this.printArray(this.thetaOut);

		// System.out.println("OUT ERRORS:");
		// this.printArray(this.errorOut);
	}

	private void printArray(double[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.printf("%10.4f", array[i]);
		}
		System.out.println("\n");

	}

	private void printMatrix(double[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.printf("%10.4f", matrix[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	public void loadTrainingData(String trainingFile) throws FileNotFoundException {
		List<Record> trainingRecords = this.recordReader.readTrainingRecords(trainingFile);
		this.numberInputs = trainingRecords.get(0).getInputs().length;
		this.numberOutputs = trainingRecords.get(0).getOutputs().length;
		this.records = trainingRecords;
	}

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

	public void train() {
		for (int i = 0; i < this.numberIterations; i++) {
			for (int j = 0; j < this.records.size(); j++) {
				this.forwardCalculation(this.records.get(j).getInputs());
				this.backwardCalculation(this.records.get(j).getOutputs());
			}
		}
	}

	private void backwardCalculation(double[] trainingOutput) {

		for (int i = 0; i < this.numberOutputs; i++) {
			this.errorOut[i] = this.output[i] * (1 - this.output[i]) * (trainingOutput[i] - this.output[i]);
		}

		for (int i = 0; i < this.numberMiddle; i++) {
			double sum = 0;

			for (int j = 0; j < this.numberOutputs; j++) {
				sum += this.matrixOut[i][j] * this.errorOut[j];
			}

			this.errorMiddle[i] = this.middle[i] * (1 - this.middle[i]) * sum;
		}

		for (int i = 0; i < this.numberMiddle; i++) {
			for (int j = 0; j < this.numberOutputs; j++) {
				this.matrixOut[i][j] += this.rate * this.middle[i] * this.errorOut[j];
			}
		}

		for (int i = 0; i < this.numberInputs; i++) {
			for (int j = 0; j < this.numberMiddle; j++) {
				this.matrixMiddle[i][j] += this.rate * this.input[i] * this.errorMiddle[j];
			}
		}

		for (int i = 0; i < this.numberOutputs; i++) {
			this.thetaOut[i] += this.rate * this.errorOut[i];
		}

		for (int i = 0; i < this.numberMiddle; i++) {
			this.thetaMiddle[i] += this.rate * this.errorMiddle[i];
		}

	}

	private void forwardCalculation(double[] trainingInput) {
		for (int i = 0; i < this.numberInputs; i++) {
			this.input[i] = trainingInput[i];
		}

		for (int i = 0; i < this.numberMiddle; i++) {
			double sum = 0;

			for (int j = 0; j < this.numberInputs; j++) {
				sum += this.input[j] * this.matrixMiddle[j][i];
			}

			sum += this.thetaMiddle[i];

			this.middle[i] = 1 / (1 + Math.exp(-sum));
		}

		for (int i = 0; i < this.numberOutputs; i++) {
			double sum = 0;

			for (int j = 0; j < this.numberMiddle; j++) {
				sum += this.middle[j] * this.matrixOut[j][i];
			}

			sum += this.thetaOut[i];

			this.output[i] = 1 / (1 + Math.exp(-sum));
		}
	}

	private double[] test(double[] input) {
		this.forwardCalculation(input);
		return this.output;
	}

	public void testData(String testInputFile, String outputFile) throws IOException {

		List<Record> testRecords = this.recordReader.readTestRecords(testInputFile);

		for (int i = 0; i < testRecords.size(); i++) {
			Record record = testRecords.get(i);

			double[] output = this.test(record.getInputs()).clone();

			record.setOutput(output);

		}

		this.recordReader.writeRecords(testRecords, outputFile);
	}

	public double validate(String validationFile) throws IOException {
		List<Record> records = this.recordReader.readValidationRecords(validationFile);
		double error = 0;

		for (int i = 0; i < records.size(); i++) {

			double[] input = records.get(i).getInputs();

			double[] actualOutput = records.get(i).getOutputs();

			double[] predictedOutput = this.test(input);

			System.out.print("input= ");
			for (int j = 0; j < input.length; j++) {
				System.out.print(input[j] + " ");
			}
			System.out.println();

			System.out.print("predicted= ");
			for (int j = 0; j < predictedOutput.length; j++) {
				System.out.print(predictedOutput[j] + " ");
			}
			System.out.println();

			System.out.print("actual= ");
			for (int j = 0; j < actualOutput.length; j++) {
				System.out.print(actualOutput[j] + " ");
			}
			System.out.println("\n");

			error += this.computeError(actualOutput, predictedOutput);
		}

		return error / records.size();

	}

	private double computeError(double[] actualOutput, double[] predictedOutput) {

		return this.testErrorComputer.computeError(actualOutput, predictedOutput);

	}

	public void setBiases(double[] middleBiases, double[] outBiases) {
		this.thetaMiddle = middleBiases;
		this.thetaOut = outBiases;
	}

	public double trainingError() {

		int numberRecords = this.records.size();
		double error = 0;

		for (int i = 0; i < numberRecords; i++) {

			double[] input = this.records.get(i).getInputs();
			double[] actualOutput = this.records.get(i).getOutputs();
			double[] predictedOutput = this.test(input);

			System.out.println("predicted " + predictedOutput[0] + ", actual " + actualOutput[0]);

			error += this.computeError(actualOutput, predictedOutput);
		}

		return error / numberRecords;
	}

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
