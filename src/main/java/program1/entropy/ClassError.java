package program1.entropy;

import java.util.List;
import java.util.Map;

import program1.data.record.Record;

public class ClassError implements EntropyMeasure {

	@Override
	public double getEntropy(List<Record> records) {

		long maxFrequency = Record.getLabelFrequencies(records).entrySet().parallelStream()
				.max(Map.Entry.comparingByValue()).get().getValue();
		double maxProbability = maxFrequency / (double) records.size();
		return 1.0 - maxProbability;
	}

}
