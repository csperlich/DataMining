package program1.majorityrule;

import org.javatuples.Pair;

import java.util.List;

import program1.data.record.Record;

public interface MajorityRule {
	public String getMajorityLabel(List<Record> records, List<Pair<Integer, Double>> idsAndDistances);
}
