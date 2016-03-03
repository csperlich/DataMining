package classifier;

import java.util.ArrayList;
import java.util.List;

import data.record.Record;

public interface Classifier {
	public String classify(Record record);

	public List<Record> getTrainingRecords();

	public void setTrainingRecords(List<Record> records);

	default public double trainingError() {
		return classificationError(this.getTrainingRecords());
	}

	default public double classificationError(List<Record> records) {
		int numIncorrect = 0;
		for (Record record : records) {
			String classification = this.classify(record);
			if (!classification.equals(record.getLabel())) {
				numIncorrect++;
			}
		}
		return ((double) numIncorrect / records.size());
	}

	default public void classify(List<Record> records) {
		for (Record record : records) {
			record.setLabel(this.classify(record));
		}
	}

	public void buildClassifier();

	default public double validateLeaveOneOut() {
		List<Record> originalTrainingRecords = getTrainingRecords();
		double trainingError = 0;
		for (int i = 0; i < originalTrainingRecords.size(); i++) {
			List<Record> newTrainingRecords = new ArrayList<>();
			List<Record> newTestRecords = new ArrayList<>();

			newTrainingRecords.addAll(originalTrainingRecords);
			newTestRecords.add(newTrainingRecords.remove(i));

			this.setTrainingRecords(newTrainingRecords);
			this.buildClassifier();
			double error = this.classificationError(newTestRecords);

			trainingError += error;

		}

		// reset tree
		this.setTrainingRecords(originalTrainingRecords);
		this.buildClassifier();
		return trainingError / originalTrainingRecords.size();
	}

	default public double validateRandomSampling(int numIterations) {
		List<Record> originalTrainingRecords = getTrainingRecords();
		double trainingError = 0;
		for (int i = 0; i < numIterations; i++) {

			List<Record> newTrainingRecords = new ArrayList<>();
			List<Record> newTestRecords = new ArrayList<>();
			for (Record record : originalTrainingRecords) {
				if (Math.random() < .9) {
					newTrainingRecords.add(record);
				} else {
					newTestRecords.add(record);
				}
			}

			// make sure neither set is empty
			if (newTrainingRecords.size() == 0) {
				newTrainingRecords.add(newTestRecords.remove(newTestRecords.size() - 1));
			}
			if (newTestRecords.size() == 0) {
				newTestRecords.add(newTrainingRecords.remove(newTrainingRecords.size() - 1));
			}

			this.setTrainingRecords(newTrainingRecords);
			this.buildClassifier();
			double error = this.classificationError(newTestRecords);

			trainingError += error;

		}

		// reset tree
		this.setTrainingRecords(originalTrainingRecords);
		this.buildClassifier();
		return trainingError / numIterations;
	}

	public void setTrace(boolean trace);

}
