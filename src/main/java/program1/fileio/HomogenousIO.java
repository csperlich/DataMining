package program1.fileio;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import program1.data.attribute.AttributeInfo;
import program1.data.feature.FeatureStrategy;
import program1.data.record.Record;

public class HomogenousIO extends RecordIO {

	public HomogenousIO(List<AttributeInfo<?>> attributeInfos, FeatureStrategy featureStrategy)
			throws FileNotFoundException {
		super(attributeInfos, featureStrategy);
	}

	@Override
	protected List<Record> readData(boolean training) {

		List<Record> records = new ArrayList<>();

		while (this.reader.hasNext()) {
			Object[] values = new Object[this.attributeInfos.size()];
			for (int i = 0; i < this.attributeInfos.size(); i++) {
				String val = this.reader.next();
				/*
				 * System.out.println("--"); System.out.println("i = " + i);
				 * System.out.println(val);
				 * System.out.println(this.attributeInfos.get(i));
				 * System.out.println("--");
				 */
				values[i] = this.attributeInfos.get(i).parseValue(val);
			}
			Record record = new Record(values);
			if (training) {
				record.setLabel(this.reader.next());
			}
			records.add(record);
			// System.out.println(record.getLabel());
			// System.out.println(curLabel);

			// System.out.println(record.csvString(' '));
			// System.out.println(records.get(records.size() - 1).getLabel() + "
			// labelCount=" + labelCount);

		}

		return records;

	}

}
