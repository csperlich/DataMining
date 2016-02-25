package data.attribute;

import java.util.List;

import data.feature.FeatureStrategy;

public class BinaryAttributeInfo extends AttributeInfo<String> {

	public BinaryAttributeInfo(int columnNumber, List<Object> discreteValues, FeatureStrategy featureStrategy) {
		super(AttributeType.BINARY, columnNumber, "" + columnNumber, discreteValues, featureStrategy);
	}

	public BinaryAttributeInfo(int columnNumber, String columnName, List<Object> discreteValues,
			FeatureStrategy featureStrategy) {
		super(AttributeType.BINARY, columnNumber, columnName, discreteValues, featureStrategy);
	}

	@Override
	protected void addNonContinuousFeatures() {
		this.features.addAll(this.featureStrategy.getBinaryFeatures(this));

	}

}
