package assignment1.majorityrule;

import org.javatuples.Pair;

import java.util.List;

import assignment1.data.record.Record;

public class UnWeightedMajorityRule implements MajorityRule {

	@Override
	public String getMajorityLabel(List<Record> records, List<Pair<Integer, Double>> idsAndDistances) {
		String label = Record.getMajorityLabel(records);
		return label;
	}

}
