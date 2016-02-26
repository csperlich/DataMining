package fileio;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import data.attribute.AttributeInfo;
import data.feature.NoFeatureStrategy;
import data.record.Record;

public class HomogenousIO extends RecordIO {

	public HomogenousIO(List<AttributeInfo<?>> attributeInfos) throws FileNotFoundException {
		super(attributeInfos, new NoFeatureStrategy());
	}

	/*
	 * @Override protected Pair<List<Record>, List<AttributeInfo<?>>>
	 * readData(boolean training) {
	 * 
	 * boolean normalize = true; List<AttributeInfo<?>> attributeInfos = new
	 * LinkedList<>(); attributeInfos.add(new ContinuousAttribute(0, "Score",
	 * null, this.featureStrategy, 0.0, 100.0, normalize));
	 * attributeInfos.add(new ContinuousAttribute(1, "GPA", null,
	 * this.featureStrategy, 0.0, 100.0, normalize)); attributeInfos .add(new
	 * OrdinalAttribute(2, "Grades", Arrays.asList("C", "B", "A"),
	 * this.featureStrategy, normalize)); attributeInfos.add(new
	 * OrdinalAttribute(3, "Class", Arrays.asList("bad", "average", "good"),
	 * this.featureStrategy, normalize));
	 * 
	 * List<Record> records = new ArrayList<>();
	 * 
	 * while (this.reader.hasNext()) { Object[] values = new
	 * Object[attributeInfos.size()]; for (int i = 0; i < attributeInfos.size();
	 * i++) { values[i] = this.reader.next(); } Record record = new
	 * Record(values); if (training) { record.setLabel(this.reader.next()); }
	 * records.add(record); }
	 * 
	 * return new Pair<List<Record>, List<AttributeInfo<?>>>(records,
	 * attributeInfos);
	 * 
	 * }
	 */

	@Override
	protected List<Record> readData(boolean training) {

		List<Record> records = new ArrayList<>();

		while (this.reader.hasNext()) {
			Object[] values = new Object[this.attributeInfos.size()];
			for (int i = 0; i < this.attributeInfos.size(); i++) {
				values[i] = this.attributeInfos.get(i).parseValue(this.reader.next());
			}
			Record record = new Record(values);
			if (training) {
				record.setLabel(this.reader.next());
			}
			records.add(record);
		}

		return records;

	}

}
