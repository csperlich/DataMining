package maniccam.data;

public class PassThroughDoubleDataConverter implements DataConverter<Double> {

	@Override
	public Double convert(String label, int column) {
		return Double.parseDouble(label);
	}

	@Override
	public String convert(Double value, int column) {
		return String.format("%.2f", value);
	}

}
