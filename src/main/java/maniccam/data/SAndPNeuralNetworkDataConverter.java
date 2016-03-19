package maniccam.data;

public class SAndPNeuralNetworkDataConverter implements DataConverter<Double> {

	@Override
	public Double convert(String label, int column) {
		Double value = Double.parseDouble(label);

		// System.out.println("====" + value);

		// values must be betwwen -2% and +2%
		value = Math.max(value, -2.0);
		value = Math.min(value, 2.0);

		// convert to [0.0, 1.0] range
		value = (value + 2.0) / 4.0;
		// System.out.println(value + "====");
		return value;
	}

	@Override
	public String convert(Double value, int column) {
		// convert from [0.0,1.0] range to [-2.0,2.0] range
		// System.out.println("====" + value);
		value = value * 4.0 - 2.0;
		// System.out.println(value + "====");
		return String.format("%.2f", value);
	}

}
