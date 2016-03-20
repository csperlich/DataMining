package assignment2.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RecordReader {

	private boolean normalize;

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

	private enum AttributeType implements DataMapper {

		BINARY, ORDINAL, NOMINAL, CONTINUOUS

		{

			@Override
			public DataConverter getDataConverter(final String[] values, boolean normalize) {

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
						// TODO Auto-generated method stub
						return 0;
					}

				};
			}
		};

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

		protected double[] getRangeMarkers(String[] values) {
			double[] rangeMarkers = new double[values.length];
			for (int i = 0; i < rangeMarkers.length; i++) {
				double value = (i * 2 + 1) / (double) (values.length * 2);
				rangeMarkers[i] = value;
			}
			return rangeMarkers;
		}

		protected HashMap<Double, String> reverseMap(HashMap<String, Double> stringToNumberMap) {
			HashMap<Double, String> reverseHashMap = new HashMap<>();
			for (Map.Entry<String, Double> entry : stringToNumberMap.entrySet()) {
				reverseHashMap.put(entry.getValue(), entry.getKey());
			}
			return reverseHashMap;
		}

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
	}

	private List<DataConverter> dataConverters = new ArrayList<>();
	private List<String> attributeNames = new ArrayList<>();
	private int numInputs;
	private int numOutputs;

	public RecordReader(boolean normalize) {
		this.normalize = normalize;
	}

	public int getNumValues(int attributeIndex) {
		return this.dataConverters.get(attributeIndex).getNumValues();
	}

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

	public static void main(String[] args) throws FileNotFoundException {
		RecordReader recordReader = new RecordReader(true);
		List<Record> trainingRecords = recordReader.readTrainingRecords("training_data");

		System.out.println("TRAINING RECORDS");
		for (Record record : trainingRecords) {
			System.out.println(record);
		}

		List<Record> validationRecords = recordReader.readValidationRecords("validation_data");
		System.out.println("\nVALIDATION RECORDS");
		for (Record record : validationRecords) {
			System.out.println(record);
		}

		List<Record> testRecords = recordReader.readTestRecords("test_data");
		System.out.println("\nTEST RECORDS");
		for (Record record : testRecords) {
			System.out.println(record);
		}

		recordReader.writeRecords(trainingRecords, "output_file");
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
