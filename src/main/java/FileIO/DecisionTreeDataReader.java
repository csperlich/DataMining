package FileIO;

import org.javatuples.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import Data.AttributeInfo;
import Data.FeatureStrategy;
import Data.Record;

public abstract class DecisionTreeDataReader {

	protected Scanner reader;
	protected FeatureStrategy featureStrategy;

	public DecisionTreeDataReader(String fileName, FeatureStrategy featureStrategy) throws FileNotFoundException {
		this.reader = new Scanner(new File(fileName));
		this.featureStrategy = featureStrategy;
	}

	public Pair<List<Record>, AttributeInfo[]> getData() {
		Pair<List<Record>, AttributeInfo[]> data = this.readData();
		this.reader.close();
		return data;
	}

	protected abstract Pair<List<Record>, AttributeInfo[]> readData();
}
