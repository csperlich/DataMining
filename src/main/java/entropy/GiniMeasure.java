package entropy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.record.Record;

public class GiniMeasure implements EntropyMeasure {

	@Override
	public double getEntropy(List<Record> records) {
		Map<String, Double> labelFrequencies = new HashMap<>();

		// find frequencies of labels
		for (Record record : records) {
			labelFrequencies.put(record.getLabel(), labelFrequencies.getOrDefault(record.getLabel(), 0.0) + 1.0);
		}

		double sum = 0;
		// normalize frequencies and calculate sum
		for (Map.Entry<String, Double> entry : labelFrequencies.entrySet()) {
			entry.setValue(entry.getValue() / records.size());
			sum += entry.getValue();
		}

		return 1 - sum;
	}

}
