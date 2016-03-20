package program2.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DoubleFileDataBounder {
	public static void limitRange(double lowerBound, double upperBound, int doublesPerLine, String inFile,
			String outFile) throws FileNotFoundException {
		Scanner fileIn = new Scanner(new File(inFile));
		PrintWriter fileOut = new PrintWriter(new File(outFile));

		while (fileIn.hasNextLine()) {
			for (int i = 0; i < doublesPerLine; i++) {

				double val = fileIn.nextDouble();
				if (val < lowerBound) {
					val = lowerBound;
				} else if (val > upperBound) {
					val = upperBound;
				}
				fileOut.write(val + "\t");
			}
			fileOut.write("\n");
		}

		fileIn.close();
		fileOut.close();
	}

	public static void main(String[] args) throws FileNotFoundException {
		limitRange(-2, 2, 3, "program2_data/s&p/test_apr2014", "program2_data/s&p/test_apr2014_bounded");

		limitRange(-2, 2, 4, "program2_data/s&p/training_jan2014-dec2014",
				"program2_data/s&p/training_jan2014-dec2014_bounded");

		limitRange(-2, 2, 4, "program2_data/s&p/validate_jan2015-mar2015",
				"program2_data/s&p/validate_jan2015-mar2015_bounded");

	}
}
