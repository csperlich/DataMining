package classifier;

import java.util.List;

import data.record.Record;

public interface Classifier {
	public String classify(Record record);

	public double trainingError();

	default public double classificationError(List<Record> records) {
		int numCorrect = 0;
		for (Record record : records) {
			String classification = this.classify(record);
			if (classification.equals(record.getLabel())) {
				numCorrect++;
			}
			// System.out.println("guess: " + classification + ", actual: " +
			// record.getLabel());
		}
		return 1.0 - (double) numCorrect / records.size();
	}

	public void classify(List<Record> record);

}
