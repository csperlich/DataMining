package data.record;

import java.util.Arrays;

public class Record {
	private Object[] attributes;
	private String label;
	private int recordNumber;
	private static int totalRecords = 0;

	public Record(String label, Object... attributes) {
		this.attributes = attributes;
		this.label = label;
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

}
