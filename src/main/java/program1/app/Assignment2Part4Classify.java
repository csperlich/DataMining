package program1.app;

import org.javatuples.Pair;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import program1.classifier.nearestneighbor.NearestNeighbor;
import program1.data.attribute.AttributeInfo;
import program1.data.attribute.NominalAttributeInfo;
import program1.data.feature.DefaultFeautureStrategy;
import program1.data.feature.FeatureStrategy;
import program1.data.feature.NoFeatureStrategy;
import program1.data.record.Record;
import program1.fileio.HomogenousIO;
import program1.majorityrule.WeightedMajorityRule;

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
		List<Record> trainingRecords = recordIO.getTrainingData("program1_data/digits_20by20_training_spaced");

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
				List<Record> testData = recordIO.getTestData("program1_data/test_digits/" + file);
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
		keyboard.close();
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

/*
 * SAMPLE RUN
 *
 * CONSTRUCTING NEAREST NEIGHBOR CLASSIFIER WITH 1000 DIGIT FILES USING WEIGHTED
 * MAJORITY RULE AND 10 NEIGHBORS
 *
 * PLEASE ENTER A FILE TO CLASSIFY THERE ARE 25 TEST FILES FOR EACH DIGIT)
 * FILENAMES HAVE THE FORM: 0_1 3_14 3_15 etc.. Enter filename: 8_4 The file 8_4
 * is classified as 8 00000000000000000000 00000000000000000000
 * 00000000000000000000 00000000000000000000 00000110000000000000
 * 11111111111111111000 11111111111111111110 11111111111111111110
 * 00000111100000111000 00000000110011110000 00000000111111100000
 * 00000000011111100000 00000000001111000000 00000000011110000000
 * 00000000011111000000 00000000011111100000 00000000011111100000
 * 00000000001111110000 00000000000111111000 00000000000001100000
 *
 *
 * Continue 'y'/'n'?: y
 *
 * PLEASE ENTER A FILE TO CLASSIFY THERE ARE 25 TEST FILES FOR EACH DIGIT)
 * FILENAMES HAVE THE FORM: 0_1 3_14 3_15 etc.. Enter filename: 3_5 The file 3_5
 * is classified as 3 00000000000000000000 00000000000000000000
 * 00000000000000000000 00000000000000000000 00000111111111100000
 * 00001111111111110000 00000100000001100000 00000000000111100000
 * 00000000111111100000 00000001111111111000 00000000000000011100
 * 00000000000000001110 00000000000000000110 00000000000000001100
 * 11100000000000011100 00111100000011110000 00001111111111000000
 * 00000011111000000000 00000000000000000000 00000000000000000000
 *
 *
 * Continue 'y'/'n'?: y
 *
 * PLEASE ENTER A FILE TO CLASSIFY THERE ARE 25 TEST FILES FOR EACH DIGIT)
 * FILENAMES HAVE THE FORM: 0_1 3_14 3_15 etc.. Enter filename: 4_8 The file 4_8
 * is classified as 4 00000000000000000000 00000000000000000000
 * 00000000000000000000 00000000000010000000 00000000010010000000
 * 00000000110010000000 00000001110010000000 00000011100011000000
 * 00000111000011000000 00011110000001100000 00111111111111110000
 * 01111111111111111111 11111000000001100010 00000000000001100000
 * 00000000000001100000 00000000000000100000 00000000000000100000
 * 00000000000000110000 00000000000000110000 00000000000000000000
 *
 *
 * Continue 'y'/'n'?: y
 *
 * PLEASE ENTER A FILE TO CLASSIFY THERE ARE 25 TEST FILES FOR EACH DIGIT)
 * FILENAMES HAVE THE FORM: 0_1 3_14 3_15 etc.. Enter filename: 2_6 The file 2_6
 * is classified as 2 00000000000000000000 00000000000000000000
 * 00000000000000000000 00000000000000000000 00000000001000000000
 * 00000001111100000000 00000010001100000000 00000000001100000000
 * 00000000011000000001 00000000110000000110 00000000110000011000
 * 00000001100001100000 00000011000110000000 00000011011000000000
 * 00000011100000000000 00000111000000000000 00011100000000000000
 * 00000000000000000000 00000000000000000000 00000000000000000000
 *
 *
 * Continue 'y'/'n'?: y
 *
 * PLEASE ENTER A FILE TO CLASSIFY THERE ARE 25 TEST FILES FOR EACH DIGIT)
 * FILENAMES HAVE THE FORM: 0_1 3_14 3_15 etc.. Enter filename: 7_1 The file 7_1
 * is classified as 7 00000000000000000000 00000000000000000000
 * 00000000000000000000 00000000000000000000 00000000000000000000
 * 00000000000000000000 00000000000000000000 00000000000011100000
 * 11111111111111111000 00000000000000011100 00000000000000111100
 * 00000000000001110000 00000000000111100000 00000000001111000000
 * 00000000001100000000 00000000111000000000 00000000110000000000
 * 00000000110000000000 00000000000000000000 00000000000000000000
 *
 *
 * Continue 'y'/'n'?: y
 *
 * PLEASE ENTER A FILE TO CLASSIFY THERE ARE 25 TEST FILES FOR EACH DIGIT)
 * FILENAMES HAVE THE FORM: 0_1 3_14 3_15 etc.. Enter filename: 9_4 The file 9_4
 * is classified as 9 00000000000000000000 00000000000000000000
 * 00000000000000000000 00000000000000000000 00000000110000000000
 * 00000011111110000000 00000111111110000000 00001111011111000000
 * 00001111011111000000 00001111111111000000 00001111110011100000
 * 00000111100011100000 00000000000011100000 00000000000011100000
 * 00000000000011100000 00000000000001100000 00000000000001110000
 * 00000000000001110000 00000000000000110000 00000000000000110000
 *
 *
 * Continue 'y'/'n'?: n GOODBYE!
 *
 */
