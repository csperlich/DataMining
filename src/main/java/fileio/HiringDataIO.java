package fileio;

import org.javatuples.Pair;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import data.attribute.AttributeInfo;
import data.attribute.BinaryAttributeInfo;
import data.feature.DefaultFeautureStrategy;
import data.feature.FeatureStrategy;
import data.record.Record;

public class HiringDataIO extends RecordIO {

	public HiringDataIO() throws FileNotFoundException {
		super(new DefaultFeautureStrategy());
	}

	@Override
	protected Pair<List<Record>, List<AttributeInfo<?>>> readData(boolean training) {
		List<AttributeInfo<?>> attInf = new LinkedList<>();
		FeatureStrategy fStrat = new DefaultFeautureStrategy();
		attInf.add(new BinaryAttributeInfo(0, "major", Arrays.asList("cs", "other"), fStrat));
		attInf.add(new BinaryAttributeInfo(1, "java experience", Arrays.asList("java", "no"), fStrat));
		attInf.add(new BinaryAttributeInfo(2, "c/c++ experience", Arrays.asList("c/c++", "no"), fStrat));
		attInf.add(new BinaryAttributeInfo(3, "gpa", Arrays.asList("gpa>3", "gpa<3"), fStrat));
		attInf.add(new BinaryAttributeInfo(4, "large project experience", Arrays.asList("large", "small"), fStrat));
		attInf.add(new BinaryAttributeInfo(5, "years of experience", Arrays.asList("years>5", "years<5"), fStrat));

		List<Record> records = new ArrayList<>();

		while (this.reader.hasNext()) {
			Object[] attributes = new Object[attInf.size()];
			for (int i = 0; i < attributes.length; i++) {
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

}
