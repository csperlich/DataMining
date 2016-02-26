package fileio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import data.attribute.AttributeInfo;
import data.feature.FeatureStrategy;
import data.record.Record;

public abstract class RecordIO {

	protected Scanner reader;
	protected FeatureStrategy featureStrategy;
	protected PrintWriter writer;
	protected List<AttributeInfo<?>> attributeInfos;

	public RecordIO(List<AttributeInfo<?>> attributeInfos, FeatureStrategy featureStrategy)
			throws FileNotFoundException {
		this.featureStrategy = featureStrategy;
		this.attributeInfos = attributeInfos;
	}

	/*
	 * public Pair<List<Record>, List<AttributeInfo<?>>> getTrainingData(String
	 * fileName) throws FileNotFoundException { Record.resetRecordCount();
	 * this.reader = new Scanner(new File(fileName)); Pair<List<Record>,
	 * List<AttributeInfo<?>>> data = this.readData(true); this.reader.close();
	 * return data; }
	 */

	public List<Record> getTrainingData(String fileName) throws FileNotFoundException {
		Record.resetRecordCount();
		this.reader = new Scanner(new File(fileName));
		List<Record> data = this.readData(true);
		this.reader.close();
		return data;
	}

	public List<Record> getTestData(String fileName) throws FileNotFoundException {
		Record.resetRecordCount();
		this.reader = new Scanner(new File(fileName));
		List<Record> data = this.readData(false);
		this.reader.close();
		return data;
	}

	public void writeData(String fileName, List<Record> records) throws FileNotFoundException {
		this.writer = new PrintWriter(new File(fileName));
		for (Record record : records) {
			this.writer.println(record.csvString());
		}
		this.writer.close();
	}

	public List<Record> getRawRecords(String fileName) throws FileNotFoundException {
		List<Record> records = new ArrayList<>();
		this.reader = new Scanner(new File(fileName));
		while (this.reader.hasNext()) {
			Object[] attributes = new Object[this.attributeInfos.size()];

			for (int i = 0; i < this.attributeInfos.size(); i++) {
				attributes[i] = this.reader.next();
			}

			records.add(new Record(attributes));
		}
		this.reader.close();
		return records;
	}

	protected abstract List<Record> readData(boolean training);

}
