package data.attribute;

import java.util.ArrayList;
import java.util.List;

import data.feature.Feature;
import data.feature.FeatureStrategy;

public abstract class AttributeInfo<T> {
	private AttributeType attributeType;
	protected List<Feature<T>> features;
	private int columnNumber;
	private String columnName;
	private List<Object> discreteValues;
	protected FeatureStrategy featureStrategy;

	public AttributeInfo(AttributeType attributeType, int columnNumber, String columnName, List<Object> discreteValues,
			FeatureStrategy featureStrategy) {
		this.attributeType = attributeType;
		this.columnNumber = columnNumber;
		this.columnName = columnName;
		this.discreteValues = discreteValues;
		this.features = new ArrayList<>();
		this.featureStrategy = featureStrategy;
		this.addNonContinuousFeatures();
	}

	protected abstract void addNonContinuousFeatures();

	public String getColumnName() {
		return this.columnName;
	}

	public int getColumnNumber() {
		return this.columnNumber;
	}

	public Object getDiscreteValues(int i) {
		return this.discreteValues.get(i);
	}

	@Override
	public String toString() {
		return "AttributeInfo [attributeType=" + this.attributeType + ", features=" + this.features + ", columnNumber="
				+ this.columnNumber + ", columnName=" + this.columnName + ", discreteValues=" + this.discreteValues
				+ "]";
	}

	public List<Feature<T>> getFeatures() {
		return this.features;
	}
}
