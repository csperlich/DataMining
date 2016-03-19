package maniccam.data;

public class RegressionDataNeuralNetworkDataConverter implements DataConverter<Double> {

	private double[][] ranges;

	public RegressionDataNeuralNetworkDataConverter(double[][] ranges) {
		this.ranges = ranges;
	}

	@Override
	public Double convert(String label, int column) {
		column = column - 1;
		Double value = Double.parseDouble(label);

		double offset = this.ranges[column][0];
		double range = this.ranges[column][1] - this.ranges[column][0];

		// remove offset
		value = value - offset;

		// normalize to 1
		value = value / range;

		return value;
	}

	@Override
	public String convert(Double value, int column) {
		column = column - 1;

		double offset = this.ranges[column][0];
		double range = this.ranges[column][1] - this.ranges[column][0];

		// un-normalize range
		value = value * range;

		// re-offset
		value = value + offset;
		return value + "";

	}

}
