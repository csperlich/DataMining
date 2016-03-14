package maniccam;
/**
 * This is the code for a Neural Network program given out to 
 * students of Dr. Maniccam's Data Mining class at Eastern Michigan University.
 * 
 * @date Winter Semester, 2016.
 * @author Suchindran Maniccam
 */

import java.io.IOException;

public class Tester {
	public static void main(String[] args) throws IOException {
		NeuralNetwork network = new NeuralNetwork();
		
		network.loadTrainingData("trainingfile");
		
		network.setParameters(4, 1000, 4539, 0.9);
		
		network.train();
		
		network.testData("inputfile", "outputfile");
	}
}
