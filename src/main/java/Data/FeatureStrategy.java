package Data;

import java.util.ArrayList;
import java.util.List;

public interface FeatureStrategy {
	default List<Feature<String>> getBinaryFeatures(AttributeInfo attributeInfo) {
		String representation = attributeInfo.getColumnName() + ", is ";

		Feature<String> feature = Feature.createFeature(representation, attributeInfo.getColumnNumber(),
				Predicates.is((String) attributeInfo.discreteValues(0)));
		List<Feature<String>> features = new ArrayList<>();
		features.add(feature);
		return features;
	}

	default List<Feature<Integer>> getNominalFeatures(AttributeInfo attributeInfo) {
		return null;
	}

	default List<Feature<Integer>> getOrdinalFeatures(AttributeInfo attributeInfo) {
		return null;
	}

	default List<Feature<Double>> getContinuosFeatures(AttributeInfo attributeInfo) {
		return null;
	}
}
