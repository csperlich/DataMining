package assignment1.majorityrule;

import org.javatuples.Pair;

import java.util.List;

import assignment1.data.record.Record;

public class WeightedMajorityRule implements MajorityRule {

	@Override
	public String getMajorityLabel(List<Record> records, List<Pair<Integer, Double>> idsAndDistances) {
		int maxIndex = -1;
		double maxContribution = -1;
		for (int i = 0; i < records.size(); i++) {
			double contribution = 1.0 / idsAndDistances.get(i).getValue1();
			if (contribution > maxContribution) {
				maxIndex = i;
				maxContribution = contribution;
			}
		}
		String label = records.get(maxIndex).getLabel();
		return label;
	}

}
