package data.attribute;

import java.util.List;

import data.feature.FeatureStrategy;

public class ContinuousAttribute extends AttributeInfo<Double> {

	private double lowerBound;
	private double upperBound;

	public ContinuousAttribute(int columnNumber, String columnName, List<Object> discreteValues,
			FeatureStrategy featureStrategy, double lowerBound, double upperBound, boolean normalize) {
		super(AttributeType.CONTINOUS, columnNumber, columnName, discreteValues, featureStrategy, normalize);
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	@Override
	protected void addNonContinuousFeatures() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createValueMap(List<Object> discreteValues) {
		return;
	}

	@Override
	public Double parseValue(String next) {
		if (this.normalize) {
			return (Double.parseDouble(next) - this.lowerBound) / (this.upperBound - this.lowerBound);
		}
		return Double.parseDouble(next);
	}

	@Override
	public double getAttributeDistance(Object val1, Object val2) {
		return (double) val1 - (double) val2;
	}

}
