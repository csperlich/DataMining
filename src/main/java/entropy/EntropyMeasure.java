package entropy;

import java.util.ArrayList;
import java.util.List;

import data.record.Record;

public interface EntropyMeasure {
	public double getEntropy(List<Record> records);

	public default double weightedAverage(List<List<Record>> recordSets) {
		int totalRecords = 0;
		List<Double> entropies = new ArrayList<>();
		for (List<Record> records : recordSets) {
			entropies.add(getEntropy(records));
			totalRecords += records.size();
		}

		double average = 0;
		for (int i = 0; i < entropies.size(); i++) {
			average += entropies.get(i) * recordSets.get(i).size() / totalRecords;
		}

		return average;
	}
}
