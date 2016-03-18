package maniccam.neuralnets;
/**
 * This is the code for a Neural Network program given out to
 * students of Dr. Maniccam's Data Mining class at Eastern Michigan University.
 *
 * @date Winter Semester, 2016.
 * @author Suchindran Maniccam
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import maniccam.data.DataConverter;

public class NeuralNetwork {

	private class Record {
		private double[] input;
		private double[] output;

		private Record(double[] input, double[] output) {
			this.input = input;
			this.output = output;
		}
	}

	private DataConverter<Double> dataConverter;

	private int numberRecords;
	private int numberInputs;
	private int numberOutputs;

	private int numberMiddle;
	private int numberIterations;
	private int seed;
	private double rate;

	private ArrayList<Record> records;

	private double[] input;
	private double[] middle;
	private double[] output;

	private double[] errorMiddle;
	private double[] errorOut;

	private double[] thetaMiddle;
	private double[] thetaOut;

	private double[][] matrixMiddle;
	private double[][] matrixOut;

	private double[] rangeMarkers;

	public NeuralNetwork(DataConverter<Double> dataConverter) {
		this.dataConverter = dataConverter;
	}

	public NeuralNetwork(DataConverter<Double> dataConverter, int numClasses) {
		this.dataConverter = dataConverter;
		this.initRangeMarkers(numClasses);
	}

	private void initRangeMarkers(int numClasses) {
		this.rangeMarkers = new double[numClasses];
		for (int i = 0; i < numClasses; i++) {
			this.rangeMarkers[i] = (i * 2 + 1) / (double) (numClasses * 2);
		}

	}

	public void loadTrainingData(String trainingFile) throws IOException {
		Scanner inFile = new Scanner(new File(trainingFile));

		this.numberRecords = inFile.nextInt();
		this.numberInputs = inFile.nextInt();
		this.numberOutputs = inFile.nextInt();

		this.records = new ArrayList<Record>();

		for (int i = 0; i < this.numberRecords; i++) {

			// read inputs
			double[] input = new double[this.numberInputs];
			for (int j = 0; j < this.numberInputs; j++) {
				input[j] = this.dataConverter.convert(inFile.next(), j + 1);
			}

			// read outputs
			double[] output = new double[this.numberOutputs];
			for (int j = 0; j < this.numberOutputs; j++) {
				output[j] = this.dataConverter.convert(inFile.next(), this.numberInputs + j + 1);
			}

			Record record = new Record(input, output);

			this.records.add(record);
		}

		inFile.close();
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

		System.out.println("MIDDLE ERRORS:");
		this.printArray(this.errorMiddle);

		System.out.println("OUT WEIGHTS: ");
		this.printMatrix(this.matrixOut);

		System.out.println("OUT BIASES:");
		this.printArray(this.thetaOut);

		System.out.println("OUT ERRORS:");
		this.printArray(this.errorOut);
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
				this.matrixMiddle[i][j] = 2 * rand.nextLong() - 1;
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
			for (int j = 0; j < this.numberRecords; j++) {
				this.forwardCalculation(this.records.get(j).input);
				this.backwardCalculation(this.records.get(j).output);
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

	public void testData(String inputFile, String outputFile) throws IOException {

		Scanner inFile = new Scanner(new File(inputFile));
		PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		int numberRecords = inFile.nextInt();

		for (int i = 0; i < numberRecords; i++) {
			double[] input = new double[this.numberInputs];

			for (int j = 0; j < this.numberInputs; j++) {
				input[j] = inFile.nextDouble();
			}

			double[] output = this.test(input);

			for (int j = 0; j < this.numberOutputs; j++) {
				outFile.println(output[j] + " ");
			}
			outFile.println();
		}

		inFile.close();
		outFile.close();
	}

	public void validate(String validationFile) throws IOException {
		Scanner inFile = new Scanner(new File(validationFile));

		int numberRecords = inFile.nextInt();
		double error = 0;

		for (int i = 0; i < numberRecords; i++) {

			double[] input = new double[this.numberInputs];
			for (int j = 0; j < this.numberInputs; j++) {
				input[j] = inFile.nextDouble();
			}

			double[] actualOutput = new double[this.numberOutputs];
			for (int j = 0; j < this.numberOutputs; j++) {
				actualOutput[j] = inFile.nextDouble();
			}

			double[] predictedOutput = this.test(input);

			error += this.computeError(actualOutput, predictedOutput);
		}

		System.out.println(error / numberRecords);

		inFile.close();
	}

	private double computeError(double[] actualOutput, double[] predictedOutput) {
		double error = 0;

		for (int i = 0; i < actualOutput.length; i++) {
			error += Math.pow(actualOutput[i] - predictedOutput[i], 2);
		}

		return Math.sqrt(error / actualOutput.length);
	}

	public void setBiases(double[] middleBiases, double[] outBiases) {
		this.thetaMiddle = middleBiases;
		this.thetaOut = outBiases;
	}

}
