package majorityrule;

import org.javatuples.Pair;

import java.util.List;

import data.record.Record;

public interface MajorityRule {
	public String getMajorityLabel(List<Record> records, List<Pair<Integer, Double>> idsAndDistances);
}
