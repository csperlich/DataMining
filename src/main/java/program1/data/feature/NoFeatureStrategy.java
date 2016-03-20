package program1.data.feature;

import java.util.List;

import program1.data.attribute.AttributeInfo;
import program1.data.attribute.NominalAttributeInfo;

public class NoFeatureStrategy implements FeatureStrategy {
	@Override
	public List<Feature<Integer>> getBinaryFeatures(NominalAttributeInfo attributeInfo) {
		return null;
	}

	@Override
	public List<Feature<Integer>> getNominalFeatures(AttributeInfo<?> attributeInfo) {
		return null;
	}

	@Override
	public List<Feature<Integer>> getOrdinalFeatures(AttributeInfo<?> attributeInfo) {
		return null;
	}

	@Override
	public List<Feature<Double>> getContinuosFeatures(AttributeInfo<?> attributeInfo) {
		return null;
	}
}
