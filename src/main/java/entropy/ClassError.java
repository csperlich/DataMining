package entropy;

import java.util.List;

import data.record.Record;

public class ClassError implements EntropyMeasure {

	@Override
	public double getEntropy(List<Record> records) {
		return -1.0;
	}

}
