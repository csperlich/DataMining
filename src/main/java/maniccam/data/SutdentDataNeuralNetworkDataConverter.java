package maniccam.data;

public class SutdentDataNeuralNetworkDataConverter implements DataConverter<Double> {

	double[] rangeMarkers = new double[] { 1 / 6.0, 3 / 6.0, 5 / 6.0 };

	@Override
	public Double convert(String label, int column) {
		Double value = 0.0;

		switch (column) {
		case 1:
			value = Integer.parseInt(label) / 100.0;
			break;
		case 2:
			value = Double.parseDouble(label) / 4.0;
			break;
		case 3:
			if (label.equals("C")) {
				value = 1 / 6.0;
			} else {
				value = label.equals("B") ? 3 / 6.0 : 5 / 6.0;
			}
			break;
		case 4:
			if (label.equals("bad")) {
				value = 1 / 6.0;
			} else if (label.equals("average")) {
				value = 3 / 6.0;
			} else {
				value = 5 / 6.0; // "good"
			}
			break;
		default:
			throw new IllegalArgumentException("Bad attribute column number!");
		}

		return value;
	}

	@Override
	public String convert(Double value, int column) {
		String label;

		switch (column) {
		case 1:
			label = String.format("%.2f", value * 100.0);
			break;
		case 2:
			label = String.format("%.2f", value * 4.0);
			break;
		case 3:
			int gradeRange = this.getRange(value, this.rangeMarkers);
			if (gradeRange == 1) {
				label = "C";
			} else {
				label = gradeRange == 2 ? "B" : "A";
			}
			break;
		case 4:
			int classRange = this.getRange(value, this.rangeMarkers);
			if (classRange == 1) {
				label = "bad";
			} else {
				label = classRange == 2 ? "average" : "good";
			}
			break;
		default:
			throw new IllegalArgumentException("Bad attribute column number!");
		}
		return label;
	}

}
