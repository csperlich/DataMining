package data;

import java.util.Arrays;

public class Record {
	private Object[] attributes;
	private String label;
	private int size;

	public Record(String label, Object... attributes) {
		this.attributes = attributes;
		this.label = label;
		this.size = attributes.length;
	}

	public Object getAttribute(int index) {
		return this.attributes[index];
	}

	public int getSize() {
		return this.size;
	}

	public String getLabel() {
		return this.label;
	}

	@Override
	public String toString() {
		return "Record [attributes=" + Arrays.toString(this.attributes) + ", label=" + this.label + ", size="
				+ this.size + "]";
	}
}
