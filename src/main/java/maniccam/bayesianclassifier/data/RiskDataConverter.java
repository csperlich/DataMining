package maniccam.bayesianclassifier.data;

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
	public String convert(int value, int column) {
		String name;

		if (column == 1) {
			name = value == 1 ? "college" : "highschool";
		} else if (column == 2) {
			name = value == 1 ? "smoker" : "nonsmoker";
		} else if (column == 3) {
			name = value == 1 ? "married" : "notmarried";
		} else if (column == 4) {
			name = value == 1 ? "male" : "female";
		} else if (column == 5) {
			name = value == 1 ? "nonsmoker" : "smoker";
		} else {
			if (value == 1) {
				name = "highrisk";
			} else if (value == 2) {
				name = "mediumrisk";
			} else if (value == 3) {
				name = "lowrisk";
			} else {
				name = "undetermined";
			}
		}
		return name;
	}
}
