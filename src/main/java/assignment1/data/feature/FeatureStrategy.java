package assignment1.data.feature;

import java.util.ArrayList;
import java.util.List;

import assignment1.data.attribute.AttributeInfo;
import assignment1.data.attribute.NominalAttributeInfo;

public interface FeatureStrategy {
	default List<Feature<Integer>> getBinaryFeatures(NominalAttributeInfo attributeInfo) {
		String representation = " is " + attributeInfo.getDiscreteValues(1);
		Feature<Integer> feature = Feature.createFeature(
				Predicates.is(attributeInfo.getMappedValue(attributeInfo.getDiscreteValues(1))), attributeInfo,
				representation);
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
