package data.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import data.attribute.AttributeInfo;
import data.record.Record;

public class Feature<T> {
	private List<Predicate<T>> predicates;
	private String representation;
	private AttributeInfo<T> attributeInfo;

	private Feature(Predicate<T> predicate, AttributeInfo<T> attributeInfo, String representation) {
		this.predicates = new ArrayList<>();
		this.predicates.add(predicate);
		this.attributeInfo = attributeInfo;
		this.representation = representation;

	}

	private Feature(List<Predicate<T>> predicates, AttributeInfo<T> attributeInfo, String representation) {
		this.predicates = predicates;
		this.attributeInfo = attributeInfo;
		this.representation = representation;
		;
	}

	private String getRepresentation() {
		return "Column:" + this.attributeInfo.getColumnName() + " " + this.representation;
	}

	public static <T> Feature<T> createFeature(Predicate<T> predicate, AttributeInfo<T> attributeInfo,
			String representation) {
		Feature<T> newFeature = new Feature<T>(predicate, attributeInfo, representation);
		return newFeature;
	}

	@SuppressWarnings("unchecked")
	public List<List<Record>> splitRecords(List<Record> records) {
		List<List<Record>> newPartitioning = new ArrayList<>();

		for (Predicate<T> predicate : this.predicates) {
			Map<Boolean, List<Record>> partitions = records.parallelStream().collect(Collectors.partitioningBy(
					record -> predicate.test((T) record.getAttribute(this.attributeInfo.getColumnNumber()))));

			List<Record> truePartition = partitions.get(true);

			if (truePartition != null && !truePartition.isEmpty()) {
				newPartitioning.add(truePartition);

				List<Record> falsePartition = partitions.get(false);
				if (falsePartition != null && !falsePartition.isEmpty()) {
					records = falsePartition;
				}
			}
		}
		if (!records.isEmpty()) {
			newPartitioning.add(records);
		}
		return newPartitioning;
	}

	@Override
	public String toString() {
		return this.getRepresentation();
	}
}
