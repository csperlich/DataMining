package fileio;

import org.javatuples.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import data.attribute.AttributeInfo;
import data.feature.FeatureStrategy;
import data.record.Record;

public abstract class RecordIO {

	protected Scanner reader;
	protected FeatureStrategy featureStrategy;
	protected PrintWriter writer;

	public RecordIO(FeatureStrategy featureStrategy) throws FileNotFoundException {
		this.featureStrategy = featureStrategy;
	}

	public Pair<List<Record>, List<AttributeInfo<?>>> getTrainingData(String fileName) throws FileNotFoundException {
		Record.resetRecordCount();
		this.reader = new Scanner(new File(fileName));
		Pair<List<Record>, List<AttributeInfo<?>>> data = this.readData(true);
		this.reader.close();
		return data;
	}

	public List<Record> getTestData(String fileName) throws FileNotFoundException {
		Record.resetRecordCount();
		this.reader = new Scanner(new File(fileName));
		Pair<List<Record>, List<AttributeInfo<?>>> data = this.readData(false);
		this.reader.close();
		return data.getValue0();
	}

	public void writeData(String fileName, List<Record> records) throws FileNotFoundException {
		this.writer = new PrintWriter(new File(fileName));
		for (Record record : records) {
			this.writer.println(record.csvString());
		}
		this.writer.close();
	}

	protected abstract Pair<List<Record>, List<AttributeInfo<?>>> readData(boolean training);
}
