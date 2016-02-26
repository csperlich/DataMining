package data.attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.feature.Feature;
import data.feature.FeatureStrategy;

public abstract class AttributeInfo<T> {
	private AttributeType attributeType;
	protected List<Feature<T>> features;
	private int columnNumber;
	private String columnName;
	private List<Object> discreteValues;
	protected Map<Object, T> valueMap = new HashMap<>();
	protected FeatureStrategy featureStrategy;

	protected boolean normalize;

	public AttributeInfo(AttributeType attributeType, int columnNumber, String columnName, List<Object> discreteValues,
			FeatureStrategy featureStrategy, boolean normalize) {
		this.normalize = normalize;
		this.attributeType = attributeType;
		this.columnNumber = columnNumber;
		this.columnName = columnName;
		this.discreteValues = discreteValues;
		this.features = new ArrayList<>();
		this.createValueMap(discreteValues);
		this.featureStrategy = featureStrategy;
		this.addNonContinuousFeatures();
	}

	protected abstract void createValueMap(List<Object> discreteValues);

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

	public T getMappedValue(Object o) {
		return this.valueMap.get(o);
	}

	public abstract Object parseValue(String next);

	public abstract double getAttributeDistance(Object val1, Object val2);
}
