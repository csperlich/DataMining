package maniccam.data;

public class LoanDataNeuralNetworkDataConverter implements DataConverter<Double> {

	double[] colFourRangeMarkers = new double[] { 1 / 4.0, 3 / 4.0 };
	double[] colFiveRangeMarkers = new double[] { 1 / 6.0, 3 / 6.0, 5 / 6.0 };
	double[] colSixRangeMarkers = new double[] { 1 / 8.0, 3 / 8.0, 5 / 8.0, 7 / 8.0 };

	@Override
	public Double convert(String label, int column) {
		Double value = 0.0;

		switch (column) {
		case 1:
			value = (Double.parseDouble(label) - 500.0) / 400.0;
			break;
		case 2:
			value = (Double.parseDouble(label) - 30.0) / 60.0;
			break;
		case 3:
			value = (Double.parseDouble(label) - 30.0) / 50.0;
			break;
		case 4:
			value = label.equals("male") ? 0.25 : 0.75;
			break;
		case 5:
			if (label.equals("single")) {
				value = 1 / 6.0;
			} else {
				value = label.equals("divorced") ? 3 / 6.0 : 5 / 6.0;
			}
			break;
		case 6:
			if (label.equals("low")) {
				value = 1 / 8.0;
			} else if (label.equals("medium")) {
				value = 3 / 8.0;
			} else {
				value = label.equals("undetermined") ? 5 / 8.0 : 7 / 8.0;
			}
			break;
		default:
			throw new IllegalArgumentException("Bad attribute column number");
		}
		return value;
	}

	@Override
	public String convert(Double value, int column) {
		String label;
		int rangeIndex;
		switch (column) {
		case 1:
			label = String.format("%.2f", value * 400.0 + 500.0);
			break;
		case 2:
			label = String.format("%.2f", value * 60.0 + 30);
			break;
		case 3:
			label = String.format("%.2f", value * 50.0 + 30);
			break;
		case 4:
			rangeIndex = this.getRange(value, this.colFourRangeMarkers);
			label = rangeIndex == 0 ? "male" : "female";
			break;
		case 5:
			rangeIndex = this.getRange(value, this.colFiveRangeMarkers);
			if (rangeIndex == 0) {
				label = "single";
			} else {
				label = rangeIndex == 1 ? "divorced" : "married";
			}
			break;
		case 6:
			rangeIndex = this.getRange(value, this.colSixRangeMarkers);
			if (rangeIndex == 0) {
				label = "low";
			} else if (rangeIndex == 1) {
				label = "medium";
			} else {
				label = rangeIndex == 2 ? "high" : "undetermined";
			}
			break;
		default:
			throw new IllegalArgumentException("Bad attribute column number!");
		}
		return label;
	}

}
