package data.record;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Record {
	private Object[] attributes;
	private String label;
	private int recordNumber;
	private static int totalRecords = 0;

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
		StringBuilder sb = new StringBuilder();
		for (Object value : this.attributes) {
			sb.append(value.toString() + "\t");
		}
		sb.append(this.label);
		return sb.toString();
	}

	public static Map<String, Long> getLabelFrequencies(List<Record> records) {
		Map<String, Long> labelFrequencies = new HashMap<>();
		for (Record record : records) {
			labelFrequencies.put(record.getLabel(), labelFrequencies.getOrDefault(record.getLabel(), 0L) + 1L);
		}
		return labelFrequencies;
	}

	public static Map<String, Double> getLabelProbabilites(List<Record> records) {
		Map<String, Double> labelProbabilites = new HashMap<>();
		for (Map.Entry<String, Long> entry : getLabelFrequencies(records).entrySet()) {
			labelProbabilites.put(entry.getKey(), entry.getValue() / (double) records.size());
		}
		return labelProbabilites;
	}

}
