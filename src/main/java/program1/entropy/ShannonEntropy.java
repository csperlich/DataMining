package program1.entropy;

import static program1.util.math.CustomMath.log2;

import java.util.List;
import java.util.Map;

import program1.data.record.Record;

public class ShannonEntropy implements EntropyMeasure {

	@Override
	public double getEntropy(List<Record> records) {
		double sum = 0;
		for (Map.Entry<String, Double> entry : Record.getLabelProbabilites(records).entrySet()) {
			sum += entry.getValue() * log2(entry.getValue());
		}

		return sum * -1;
	}

}
