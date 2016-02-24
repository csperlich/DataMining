package data;

import java.util.List;

public class BinaryAttributeInfo extends AttributeInfo<String> {

	public BinaryAttributeInfo(AttributeType attributeType, int columnNumber, List<Object> discreteValues,
			FeatureStrategy featureStrategy) {
		super(attributeType, columnNumber, discreteValues, featureStrategy);
	}

	@Override
	protected void addNonContinuousFeatures() {
		this.features.addAll(this.featureStrategy.getBinaryFeatures(this));

	}

}
