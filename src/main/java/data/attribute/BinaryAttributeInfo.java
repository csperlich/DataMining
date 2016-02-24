package data.attribute;

import java.util.List;

import data.feature.FeatureStrategy;

public class BinaryAttributeInfo extends AttributeInfo<String> {

	public BinaryAttributeInfo(AttributeType attributeType, int columnNumber, List<Object> discreteValues,
			FeatureStrategy featureStrategy) {
		super(attributeType, columnNumber, "" + columnNumber, discreteValues, featureStrategy);
	}

	@Override
	protected void addNonContinuousFeatures() {
		this.features.addAll(this.featureStrategy.getBinaryFeatures(this));

	}

}
