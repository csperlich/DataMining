package assignment1.entropy;

import java.util.List;
import java.util.Map;

import assignment1.data.record.Record;

public class GiniMeasure implements EntropyMeasure {

	@Override
	public double getEntropy(List<Record> records) {
		double sum = 0;
		for (Map.Entry<String, Double> entry : Record.getLabelProbabilites(records).entrySet()) {
			sum += Math.pow(entry.getValue(), 2);
		}

		return 1 - sum;
	}

}
