package fileio;

import org.javatuples.Pair;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import data.attribute.AttributeInfo;
import data.attribute.NominalAttributeInfo;
import data.feature.DefaultFeautureStrategy;
import data.record.Record;

public class BinaryRecordIO extends RecordIO {

	private int numAttributes;

	public BinaryRecordIO(int numAttributes) throws FileNotFoundException {
		super(new DefaultFeautureStrategy());
		this.numAttributes = numAttributes;
	}

	@Override
	protected Pair<List<Record>, List<AttributeInfo<?>>> readData(boolean training) {
		List<AttributeInfo<?>> attInf = new LinkedList<>();
		List<Object> values = Arrays.asList("1", "0");
		for (int i = 0; i < this.numAttributes; i++) {
			attInf.add(new NominalAttributeInfo(i, values, this.featureStrategy, training));
		}

		List<Record> records = new ArrayList<>();

		while (this.reader.hasNext()) {
			Object[] attributes = new Object[this.numAttributes];
			for (int i = 0; i < this.numAttributes; i++) {
				attributes[i] = this.reader.next();
			}
			if (training) {
				String label = this.reader.next();
				records.add(new Record(label, attributes));
			} else {
				records.add(new Record(attributes));
			}
		}

		return new Pair<List<Record>, List<AttributeInfo<?>>>(records, attInf);
	}

	@Override
	protected List<Record> readData(List<AttributeInfo<?>> attributeInfos, boolean training) {
		// TODO Auto-generated method stub
		return null;
	}

}
