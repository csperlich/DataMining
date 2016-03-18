package maniccam.data;

public class Part1JustNumbersDataConverter implements DataConverter<Integer> {
	@Override
	public Integer convert(String label, int column) {
		if (column == 1 || column == 4) {
			return label.equals("0") ? 1 : 2;
		}
		return Integer.parseInt(label);
	}

	@Override
	public String convert(Integer value, int column) {
		if (column == 1 || column == 4) {
			return value == 1 ? 0 + "" : 1 + "";
		}
		return value + "";
	}
}
