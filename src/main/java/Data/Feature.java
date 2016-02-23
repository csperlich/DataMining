package Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Feature<T> {
	private List<Predicate<T>> predicates;
	private String representation;
	private int columnNumber;

	private Feature(Predicate<T> predicate, String representation, int columnNumber) {
		this.predicates = new ArrayList<>();
		this.predicates.add(predicate);
		this.representation = representation;
		this.columnNumber = columnNumber;
	}

	private Feature(List<Predicate<T>> predicates, String representation, int columnNumber) {
		this.predicates = predicates;
		this.representation = representation;
		this.columnNumber = columnNumber;
	}

	public static <T> Feature<T> createFeature(String representation, int columnNumber, Predicate<T> predicate) {
		Feature<T> newFeature = new Feature<T>(predicate, representation, columnNumber);
		return newFeature;
	}

	@SuppressWarnings("unchecked")
	public List<List<Record>> splitRecords(List<Record> records) {
		List<List<Record>> newPartitioning = new ArrayList<>();

		for (Predicate<T> predicate : this.predicates) {
			Map<Boolean, List<Record>> partitions = records.parallelStream().collect(
					Collectors.partitioningBy(record -> predicate.test((T) record.getAttribute(this.columnNumber))));

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
		return this.representation;
	}
}
