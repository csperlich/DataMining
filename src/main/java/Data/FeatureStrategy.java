package data;

import java.util.ArrayList;
import java.util.List;

public interface FeatureStrategy {
	default List<Feature<String>> getBinaryFeatures(AttributeInfo<?> attributeInfo) {
		String representation = attributeInfo.getColumnName() + ": is " + attributeInfo.getDiscreteValues(0);

		Feature<String> feature = Feature.createFeature(representation, attributeInfo.getColumnNumber(),
				Predicates.is((String) attributeInfo.getDiscreteValues(0)));
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
