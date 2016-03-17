package maniccam.bayesianclassifier.data;

public class Part1JustNumbersDataConverter implements DataConverter {
	@Override
	public int convert(String label, int column) {
		if (column == 1 || column == 4) {
			return label.equals("0") ? 1 : 2;
		}
		return Integer.parseInt(label);
	}

	@Override
	public String convert(int value) {
		return value + "";
	}
}