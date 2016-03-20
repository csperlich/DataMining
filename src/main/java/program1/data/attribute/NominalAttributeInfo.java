package program1.data.attribute;

import java.util.List;

import program1.data.feature.FeatureStrategy;

public class NominalAttributeInfo extends AttributeInfo<Integer> {

	public NominalAttributeInfo(int columnNumber, List<Object> discreteValues, FeatureStrategy featureStrategy,
			boolean normalize) {
		super(AttributeType.BINARY, columnNumber, "" + columnNumber, discreteValues, featureStrategy, normalize);
	}

	public NominalAttributeInfo(int columnNumber, String columnName, List<Object> discreteValues,
			FeatureStrategy featureStrategy, boolean normalize) {
		super(AttributeType.BINARY, columnNumber, columnName, discreteValues, featureStrategy, normalize);
	}

	@Override
	protected void addNonContinuousFeatures() {
		this.features.addAll(this.featureStrategy.getBinaryFeatures(this));

	}

	@Override
	protected void createValueMap(List<Object> discreteValues) {
		for (int i = 0; i < discreteValues.size(); i++) {

			this.valueMap.put(discreteValues.get(i), i);
		}
	}

	@Override
	public Object parseValue(String next) {
		return this.valueMap.get(next);
	}

	@Override
	public double getAttributeDistance(Object val1, Object val2) {
		return val1.equals(val2) ? 0 : 1;
	}

}
