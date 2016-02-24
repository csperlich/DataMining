package data.feature;

import java.util.ArrayList;
import java.util.List;

import data.attribute.AttributeInfo;
import data.attribute.BinaryAttributeInfo;

public interface FeatureStrategy {
	default List<Feature<String>> getBinaryFeatures(BinaryAttributeInfo attributeInfo) {
		String representation = " is " + attributeInfo.getDiscreteValues(0);
		Feature<String> feature = Feature.createFeature(Predicates.is((String) attributeInfo.getDiscreteValues(0)),
				attributeInfo, representation);
		List<Feature<String>> features = new ArrayList<>();
		features.add(feature);
		return features;
	}

	default List<Feature<Integer>> getNominalFeatures(AttributeInfo<?> attributeInfo) {
		return null;
	}

	default List<Feature<Integer>> getOrdinalFeatures(AttributeInfo<?> attributeInfo) {
		return null;
	}

	default List<Feature<Double>> getContinuosFeatures(AttributeInfo<?> attributeInfo) {
		return null;
	}
}
