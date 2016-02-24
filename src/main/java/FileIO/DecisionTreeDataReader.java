package fileio;

import org.javatuples.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import data.attribute.AttributeInfo;
import data.feature.FeatureStrategy;
import data.record.Record;

public abstract class DecisionTreeDataReader {

	protected Scanner reader;
	protected FeatureStrategy featureStrategy;

	public DecisionTreeDataReader(String fileName, FeatureStrategy featureStrategy) throws FileNotFoundException {
		this.reader = new Scanner(new File(fileName));
		this.featureStrategy = featureStrategy;
	}

	public Pair<List<Record>, List<AttributeInfo<?>>> getData() {
		Pair<List<Record>, List<AttributeInfo<?>>> data = this.readData();
		this.reader.close();
		return data;
	}

	protected abstract Pair<List<Record>, List<AttributeInfo<?>>> readData();
}
