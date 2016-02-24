package classifier;

import java.util.List;

import data.record.Record;

public interface Classifier {
	public String classify(Record record);

	public double trainingError();

	public void classify(List<Record> record);
}
