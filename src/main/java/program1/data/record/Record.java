package program1.data.record;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Record {
	private Object[] attributes;
	private String label;
	private int recordNumber;
	private static int totalRecords = 0;
	private double confidence;
	private double support;

	public static void resetRecordCount() {
		totalRecords = 0;
	}

	public Record(String label, Object... attributes) {
		this.attributes = attributes;
		this.label = label;
		this.recordNumber = ++totalRecords;
	}

	public Record(Object[] attributes) {
		this.attributes = attributes;
		this.recordNumber = ++totalRecords;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public void setSupport(double support) {
		this.support = support;
	}

	public double getConfidence() {
		return this.confidence;
	}

	public double getSupport() {
		return this.support;
	}

	public Object getAttribute(int index) {
		return this.attributes[index];
	}

	public int getSize() {
		return this.attributes.length;
	}

	public String getLabel() {
		return this.label;
	}

	@Override
	public String toString() {
		return "Record [attributes=" + Arrays.toString(this.attributes) + ", label=" + this.label + ", recordNumber="
				+ this.recordNumber + "]";
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String csvString() {
		return this.csvString('\t');
	}

	public String csvString(char separator) {
		StringBuilder sb = new StringBuilder();
		for (Object value : this.attributes) {
			sb.append(value.toString() + separator);
		}
		return sb.toString();
	}

	public static int numCorrect(List<Record> records, String label) {
		int count = 0;
		for (Record record : records) {
			if (record.label.equals(label)) {
				count++;
			}
		}
		return count;
	}

	public static Map<String, Long> getLabelFrequencies(List<Record> records) {
		return records.parallelStream().collect(Collectors.groupingBy(Record::getLabel, Collectors.counting()));
	}

	public static String getMajorityLabel(List<Record> records) {
		return getLabelFrequencies(records).entrySet().parallelStream().max(Map.Entry.comparingByValue()).get()
				.getKey();
	}

	public static Map<String, Double> getLabelProbabilites(List<Record> records) {
		Map<String, Double> labelProbabilites = new HashMap<>();
		for (Map.Entry<String, Long> entry : getLabelFrequencies(records).entrySet()) {
			labelProbabilites.put(entry.getKey(), entry.getValue() / (double) records.size());
		}
		return labelProbabilites;
	}

}
