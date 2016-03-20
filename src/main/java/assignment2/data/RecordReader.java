package assignment2.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
This class handles the reading of records and print out of records for
records for a Bayesian Classifier and a Neural Network. This class also
effectively acts as the classifier for the Bayesian Classifier and Neural
Network since it handles the conversion from original values to doubles, and
from the double values back to the original values.

NOTE: !!!THE readTrainigRecords METHOD MUST BE CALLED BEFORE READING IN VALIDATION
	  OR TEST DATA OR BEFORE WRITING OUT RECORDS TO FILE!!!

All training record files have input of the form:

<numberOfInputs> <numberOfOutputs>

<firstAttributeType>, <attributeName>, <firstValue> <secondValue> ... <nthValue>
<2ndAttributeType>, <attributeName>, <firstValue> <secondValue> ... <nthValue>
...
<nthAttributeType>, <attributeName>, <firstValue> <secondValue> ... <nthValue>

<row1input1> <row1input2> ... <row1inputn> <row1output1> <row1output2> ... <row1outputn>
<row2input1> <row2input2> ... <row2inputn> <row2output1> <row2output2> ... <row2outputn>
<rowninput1> <rowninput2> ... <rowninputn> <rownoutput1> <rownoutput2> ... <rownoutputn>

 All validation record files have the form:

<row1input1> <row1input2> ... <row1inputn> <row1output1> <row1output2> ... <row1outputn>
<row2input1> <row2input2> ... <row2inputn> <row2output1> <row2output2> ... <row2outputn>
<rowninput1> <rowninput2> ... <rowninputn> <rownoutput1> <rownoutput2> ... <rownoutputn>

All test record files have the form:
<row1input1> <row1input2> ... <row1inputn>
<row2input1> <row2input2> ... <row2inputn>
<rowninput1> <rowninput2> ... <rowninputn>

All input files must have no trailing blank lines
See program2_data/part2/train2 for an example of training file input format
See program2_data/part2/validate2 for an example of training file input format
See program2_data/part2/test2 for an example of training file input format
 */
public class RecordReader {

	//private interfaces to ensure functionality of data mapping and conversion
	//for the different attribute types: BINARY, ORDINAL, NOMINAL, CONTINUOUS
	private interface DataMapper {
		DataConverter getDataConverter(String[] values, boolean normalize);
	}

	private interface DataConverter {
		default String formatDouble(double value) {
			return String.format("%8.4f", value);
		}

		double convert(String stringValue);

		String convert(double numberValue);

		int getNumValues();
	}

	/* Inner ENUM for the different attribute types.
	*  It implements DataMapper and so has methods to return DataConverters that
	*  use closures to return parameterized functions for the conversion of the attribute
	*  based on the values of the attribute.
	*/
	private enum AttributeType implements DataMapper {

		BINARY, ORDINAL, NOMINAL, CONTINUOUS {
			@Override
			public DataConverter getDataConverter(final String[] values, boolean normalize) {

				if (normalize) {
					//return a DataConverter that maps numbers from there range and
					//offset to the range[0.0, 1.0], and back.
					return new DataConverter() {

						private double offset = Double.parseDouble(values[0]);
						private double range = Double.parseDouble(values[1]) - this.offset;

						@Override
						public double convert(String stringValue) {
							double value = Double.parseDouble(stringValue);
							value = value - this.offset;
							value = value / this.range;
							return value;
						}

						@Override
						public String convert(double numberValue) {
							double value = numberValue;
							value = value * this.range;
							value = value + this.offset;
							return this.formatDouble(value);
						}

						@Override
						public int getNumValues() {
							return Integer.MAX_VALUE; //there are an infite number of values
						}

					};
				} else { //if not normalize
					//return a DataConverter that simply passes the values through
					return new DataConverter() {
						@Override
						public double convert(String stringValue) {
							return Double.parseDouble(stringValue);
						}

						@Override
						public String convert(double numberValue) {
							return this.formatDouble(numberValue);
						}

						@Override
						public int getNumValues() {
							return Integer.MAX_VALUE; //there are an infinte number of values
						}
					};
				}
			}
		};

		/* This is the current behavior of getDataConverter for attribute types: BINARY, ORDINAL, and NOMINAL
		* If normalization is on, then the DataConverter uses a set of range values between 0 and 1
		* 	to map arbitrary string values to doubles.
		* Otherwise, values are mapped from arbitray string values to
		* 	incrementing doubled values 0.0, 1.0, 2.0, etc...
		*/
		@Override
		public DataConverter getDataConverter(final String[] values, boolean normalize) {
			if (normalize) {
				final HashMap<String, Double> stringToNumberMap = this.createValueMap(values, normalize);
				final double[] rangeMarkers = this.getRangeMarkers(values);
				return new DataConverter() {
					@Override
					public double convert(String stringValue) {
						return stringToNumberMap.get(stringValue);
					}

					@Override
					public String convert(double numberValue) {
						for (int i = 0; i < rangeMarkers.length; i++) {
							double distance = Math.abs(numberValue - rangeMarkers[i]);
							if (distance <= (1.0 / (rangeMarkers.length * 2))) {
								return values[i];
							}
						}
						throw new IllegalArgumentException(
								"Number Values: " + numberValue + " is not in a valid range!");
					}

					@Override
					public int getNumValues() {
						return stringToNumberMap.size();
					}
				};
			} else {
				final HashMap<String, Double> stringToNumberMap = this.createValueMap(values, normalize);
				final HashMap<Double, String> numberToStringMap = this.reverseMap(stringToNumberMap);
				return new DataConverter() {

					@Override
					public double convert(String stringValue) {
						return stringToNumberMap.get(stringValue);
					}

					@Override
					public String convert(double numberValue) {
						return numberToStringMap.get(numberValue);
					}

					@Override
					public int getNumValues() {
						return stringToNumberMap.size();
					}
				};

			}
		}

		//helper method to return an array of double values, representing the center
		//of each separate range
		protected double[] getRangeMarkers(String[] values) {
			double[] rangeMarkers = new double[values.length];
			for (int i = 0; i < rangeMarkers.length; i++) {
				double value = (i * 2 + 1) / (double) (values.length * 2);
				rangeMarkers[i] = value;
			}
			return rangeMarkers;
		}

		//helper method to create value map from double values to string values
		protected HashMap<Double, String> reverseMap(HashMap<String, Double> stringToNumberMap) {
			HashMap<Double, String> reverseHashMap = new HashMap<>();
			for (Map.Entry<String, Double> entry : stringToNumberMap.entrySet()) {
				reverseHashMap.put(entry.getValue(), entry.getKey());
			}
			return reverseHashMap;
		}

		//helper method to create a value map from string values to double values
		protected HashMap<String, Double> createValueMap(String[] values, boolean normalize) {
			HashMap<String, Double> hashMap = new HashMap<>();
			if (normalize) {
				for (int i = 0; i < values.length; i++) {
					double value = (i * 2 + 1) / (double) (values.length * 2);
					hashMap.put(values[i], value);
				}
			} else {
				for (int i = 0; i < values.length; i++) {
					hashMap.put(values[i], (double) i);
				}
			}
			return hashMap;
		}
	} //End of inner ENUM AttributeType

	//Record reader instance variables
	private List<DataConverter> dataConverters = new ArrayList<>();
	private List<String> attributeNames = new ArrayList<>();
	private int numInputs;
	private int numOutputs;
	private boolean normalize; //normalize data to [0.0, 1.0] if true,
								//otherwise data is converted to [0.0, 1.0, 2.0, ...] values

	public RecordReader(boolean normalize) {
		this.normalize = normalize;
	}

	public int getNumValues(int attributeIndex) {
		return this.dataConverters.get(attributeIndex).getNumValues();
	}

	/*
	 * Reads the training records and setups up all of the data converters.
	 * !!!THIS METHOD MUST BE CALLED BEFORE READING IN VALIDATION
	 * OR TEST DATA OR BEFORE WRITING OUT RECORDS TO FILE!!!
	 */
	public List<Record> readTrainingRecords(String trainingFile) throws FileNotFoundException {
		List<Record> records = new ArrayList<>();
		Scanner fileReader = new Scanner(new File(trainingFile));

		this.numInputs = fileReader.nextInt();
		this.numOutputs = fileReader.nextInt();

		fileReader.nextLine();
		fileReader.nextLine();

		// read attribute info and create mappings
		for (int i = 0; i < this.numInputs + this.numOutputs; i++) {
			String[] attributeInfo = fileReader.nextLine().split(",");
			AttributeType attributeType = AttributeType.valueOf(attributeInfo[0].trim().toUpperCase());

			String attributeName = attributeInfo[1].trim();
			this.attributeNames.add(attributeName);

			String[] values = attributeInfo[2].trim().split(" ");
			this.dataConverters.add(attributeType.getDataConverter(values, this.normalize));
		}

		while (fileReader.hasNextLine()) {
			records.add(this.readRecord(fileReader, true));
		}

		fileReader.close();
		return records;
	}

	private Record readRecord(Scanner fileReader, boolean hasOutputs) {
		double[] inputs = new double[this.numInputs];
		double[] outputs = null;

		for (int i = 0; i < this.numInputs; i++) {
			inputs[i] = this.dataConverters.get(i).convert(fileReader.next());
		}

		if (hasOutputs) {
			outputs = new double[this.numOutputs];
			for (int i = 0; i < outputs.length; i++) {
				String next = fileReader.next();
				outputs[i] = this.dataConverters.get(this.numInputs + i).convert(next);
			}
		}
		return new Record(inputs, outputs);
	}

	public void writeRecords(List<Record> records, String outputFile) throws FileNotFoundException {
		this.writeRecords(records, outputFile, null);
	}

	public void writeRecords(List<Record> records, String outputFile, List<String> confidences)
			throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new File(outputFile));

		for (int i = 0; i < records.size(); i++) {
			Record record = records.get(i);
			double[] inputs = record.getInputs();
			for (int j = 0; j < inputs.length; j++) {
				writer.printf("%-12s", this.dataConverters.get(j).convert(inputs[j]));
			}

			writer.print(" | ");

			double[] outputs = record.getOutputs();
			for (int j = 0; j < outputs.length; j++) {
				writer.printf("%-12s", this.dataConverters.get(inputs.length - j).convert(outputs[j]));
			}

			if (confidences != null) {
				writer.print(" " + confidences.get(i));
			}

			writer.println();
		}
		writer.close();
	}

	public List<Record> readTestRecords(String testFile) throws FileNotFoundException {
		List<Record> records = new ArrayList<>();

		Scanner fileReader = new Scanner(new File(testFile));
		while (fileReader.hasNextLine()) {
			records.add(this.readRecord(fileReader, false));
		}

		fileReader.close();
		return records;
	}

	public List<Record> readValidationRecords(String validationFile) throws FileNotFoundException {
		List<Record> records = new ArrayList<>();

		Scanner fileReader = new Scanner(new File(validationFile));
		while (fileReader.hasNextLine()) {
			records.add(this.readRecord(fileReader, true));
		}

		fileReader.close();
		return records;
	}

	public String convert(int index, double value) {
		return this.dataConverters.get(index).convert(value);
	}

	public String getAttributeName(int index) {
		return this.attributeNames.get(index);
	}

	public int getNumAttributes() {
		return this.attributeNames.size();
	}
}
