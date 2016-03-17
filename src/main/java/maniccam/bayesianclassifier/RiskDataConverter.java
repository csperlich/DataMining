package maniccam.bayesianclassifier;

public class RiskDataConverter implements DataConverter {

	@Override
	public int convert(String label, int column) {
		int value;

		if (column == 1) {
			value = label.equals("college") ? 1 : 2;
		} else if (column == 2) {
			value = label.equals("smoker") ? 1 : 2;
		} else if (column == 3) {
			value = label.equals("married") ? 1 : 2;
		} else if (column == 4) {
			value = label.equals("male") ? 1 : 2;
		} else if (column == 5) {
			value = label.equals("works") ? 1 : 2;
		} else {
			if (label.equals("highrisk")) {
				value = 1;
			} else if (label.equals("mediumrisk")) {
				value = 2;
			} else if (label.equals("lowrisk")) {
				value = 3;
			} else {
				value = 4;
			}
		}
		return value;
	}

	@Override
	public String convert(int value) {
		String label;

		if (value == 1) {
			label = "highrisk";
		} else if (value == 2) {
			label = "mediumrisk";
		} else if (value == 3) {
			label = "lowrisk";
		} else {
			label = "undetermined";
		}

		return label;

	}
}
