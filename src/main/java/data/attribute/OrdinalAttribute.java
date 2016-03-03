package data.attribute;

import java.util.List;

import data.feature.FeatureStrategy;

public class OrdinalAttribute extends AttributeInfo<Double> {

	public OrdinalAttribute(int columnNumber, String columnName, List<Object> discreteValues,
			FeatureStrategy featureStrategy, boolean normalize) {
		super(AttributeType.ORDINAL, columnNumber, columnName, discreteValues, featureStrategy, normalize);
	}

	@Override
	protected void addNonContinuousFeatures() {
	}

	@Override
	public Double parseValue(String next) {
		return this.valueMap.get(next);
	}

	@Override
	protected void createValueMap(List<Object> discreteValues) {
		for (int i = 0; i < discreteValues.size(); i++) {
			if (this.normalize) {
				this.valueMap.put(discreteValues.get(i), (double) i / (discreteValues.size() - 1));
			} else {
				this.valueMap.put(discreteValues.get(i), (double) i);
			}
		}
	}

	@Override
	public double getAttributeDistance(Object val1, Object val2) {
		return (double) val1 - (double) val2;
	}

}
