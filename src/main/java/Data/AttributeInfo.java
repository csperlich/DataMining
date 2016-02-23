package Data;

import java.util.ArrayList;
import java.util.List;

public class AttributeInfo {
	private AttributeType attributeType;
	private List<Feature> features;
	private int columnNumber;
	private String columnName;
	private List<Object> discreteValues;
	private FeatureStrategy featureStrategy;

	public AttributeInfo(AttributeType attributeType, int columnNumber, List<Object> discreteValues,
			FeatureStrategy featureStrategy) {
		this.attributeType = attributeType;
		this.columnNumber = columnNumber;
		this.columnName = "" + columnNumber;
		this.discreteValues = discreteValues;
		this.features = new ArrayList<>();
		this.addNonContinuousFeatures();
	}

	private void addNonContinuousFeatures() {

		switch (this.attributeType) {
		case BINARY:
			this.featureStrategy.getBinaryFeatures(this);
			break;
		case NOMINAL:
			this.featureStrategy.getNominalFeatures(this);
			break;
		case ORDINAL:
			this.featureStrategy.getOrdinalFeatures(this);
			break;
		default:
			break;
		}

	}

	public String getColumnName() {
		return this.columnName;
	}

	public int getColumnNumber() {
		return this.columnNumber;
	}

	public Object discreteValues(int i) {
		return this.discreteValues(i);
	}

	@Override
	public String toString() {
		return "AttributeInfo [attributeType=" + this.attributeType + ", features=" + this.features + ", columnNumber="
				+ this.columnNumber + ", columnName=" + this.columnName + ", discreteValues=" + this.discreteValues
				+ "]";
	}
}
