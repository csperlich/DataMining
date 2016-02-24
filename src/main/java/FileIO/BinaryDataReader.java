package fileio;

import org.javatuples.Pair;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import data.AttributeInfo;
import data.AttributeType;
import data.BinaryAttributeInfo;
import data.DefaultFeautureStrategy;
import data.Record;

public class BinaryDataReader extends DecisionTreeDataReader {

	private int numAttributes;

	public BinaryDataReader(String fileName, int numAttributes) throws FileNotFoundException {
		super(fileName, new DefaultFeautureStrategy());
		this.numAttributes = numAttributes;
	}

	@Override
	protected Pair<List<Record>, AttributeInfo[]> readData() {
		AttributeInfo[] attInf = new AttributeInfo[this.numAttributes];
		List<Object> values = Arrays.asList("1", "0");
		for (int i = 0; i < attInf.length; i++) {
			attInf[i] = new BinaryAttributeInfo(AttributeType.BINARY, i, values, this.featureStrategy);
		}

		List<Record> records = new ArrayList<>();

		while (this.reader.hasNext()) {
			Object[] attributes = new Object[this.numAttributes];
			for (int i = 0; i < this.numAttributes; i++) {
				attributes[i] = this.reader.next();
			}
			String label = this.reader.next();
			records.add(new Record(label, attributes));
		}

		return new Pair<List<Record>, AttributeInfo[]>(records, attInf);
	}

}
