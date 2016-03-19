package maniccam.data;

public class HiringDataBayesDataConverter implements DataConverter<Integer> {

	@Override
	public Integer convert(String label, int column) {
		int value;

		switch (column) {
		case 1:
		case 3:
			value = Integer.parseInt(label) + 1;
			break;
		case 2:
			value = label.equals("java") ? 1 : 2;
			break;
		case 4:
			value = label.equals("cs") ? 1 : 2;
			break;
		case 5:
			if (label.equals("A")) {
				value = 1;
			} else if (label.equals("B")) {
				value = 2;
			} else {
				value = label.equals("C") ? 3 : 4;
			}
			break;
		case 6:
			value = label.equals("hire") ? 1 : 2;
			break;
		default:
			throw new IllegalArgumentException("Bad column number for conversion!");
		}
		return value;

	}

	@Override
	public String convert(Integer value, int column) {
		String label;

		switch (column) {
		case 1:
		case 3:
			label = (value - 1) + "";
			break;
		case 2:
			label = value == 1 ? "java" : "no";
			break;
		case 4:
			label = value == 1 ? "cs" : "other";
			break;
		case 5:
			if (value == 1) {
				label = "A";
			} else if (value == 2) {
				label = "B";
			} else {
				label = value == 3 ? "C" : "D";
			}
			break;
		case 6:
			label = value == 1 ? "hire" : "no";
			break;
		default:
			throw new IllegalArgumentException("Bad column number for conversion!");
		}
		return label;
	}

}
