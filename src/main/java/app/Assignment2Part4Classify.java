package app;

import org.javatuples.Pair;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import classifier.nearestneighbor.NearestNeighbor;
import data.attribute.AttributeInfo;
import data.attribute.NominalAttributeInfo;
import data.feature.DefaultFeautureStrategy;
import data.feature.FeatureStrategy;
import data.feature.NoFeatureStrategy;
import data.record.Record;
import fileio.HomogenousIO;
import majorityrule.WeightedMajorityRule;

public class Assignment2Part4Classify {

	public static void main(String[] args) throws FileNotFoundException {
		// Declare the feature strategy and attribute information
		FeatureStrategy featureStrategy = new DefaultFeautureStrategy();
		List<AttributeInfo<?>> attributeInfos = new LinkedList<>();
		for (int i = 0; i < 400; i++) {
			attributeInfos.add(new NominalAttributeInfo(0, Arrays.asList("0", "1"), featureStrategy, true));
		}

		// Read in the training data
		HomogenousIO recordIO = new HomogenousIO(attributeInfos, new NoFeatureStrategy());
		List<Record> trainingRecords = recordIO.getTrainingData("DataMining-Data/digits_20by20_training_spaced");

		// Construct the nearest neighbor classifier
		System.out.println("CONSTRUCTING NEAREST NEIGHBOR CLASSIFIER WITH 1000 DIGIT FILES");
		System.out.println("USING WEIGHTED MAJORITY RULE AND 10 NEIGHBORS");
		NearestNeighbor nearestNeighbor = new NearestNeighbor(
				new Pair<List<Record>, List<AttributeInfo<?>>>(trainingRecords, attributeInfos), 5,
				new WeightedMajorityRule());

		// Do classification with user input
		Scanner keyboard = new Scanner(System.in);
		while (true) {
			try {
				showMenu();
				String file = keyboard.nextLine();
				List<Record> testData = recordIO.getTestData("test_digits/" + file);
				Record testRecord = testData.get(0);
				String label = nearestNeighbor.classify(testRecord);
				System.out.println("The file " + file + " is classified as " + label);
				prettyPrintDigit(testRecord);

				System.out.print("\nContinue 'y'/'n'?: ");
				if (keyboard.nextLine().toLowerCase().charAt(0) != 'y') {
					break;
				}
			} catch (FileNotFoundException e) {
				System.out.println("bad filename");
			}

		}

		System.out.println("GOODBYE!");

	}

	private static void prettyPrintDigit(Record record) {
		StringBuilder sb = new StringBuilder("");
		int attributeNum = 0;
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				sb.append(record.getAttribute(attributeNum++));
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}

	private static void showMenu() {
		System.out.println();
		System.out.println("PLEASE ENTER A FILE TO CLASSIFY");
		System.out.println("THERE ARE 25 TEST FILES FOR EACH DIGIT)");
		System.out.println("FILENAMES HAVE THE FORM: 0_1 3_14 3_15 etc..");
		System.out.print("Enter filename: ");
	}
}
