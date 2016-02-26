package data.feature;

import java.util.ArrayList;
import java.util.List;

import data.attribute.AttributeInfo;
import data.attribute.NominalAttributeInfo;

public interface FeatureStrategy {
	default List<Feature<Integer>> getBinaryFeatures(NominalAttributeInfo attributeInfo) {
		String representation = " is " + attributeInfo.getDiscreteValues(0);
		Feature<Integer> feature = Feature.createFeature(Predicates.is((Integer) attributeInfo.getDiscreteValues(0)),
				attributeInfo, representation);
		List<Feature<Integer>> features = new ArrayList<>();
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
