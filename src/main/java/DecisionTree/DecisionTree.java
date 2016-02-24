package decisiontree;

import org.javatuples.Pair;

import java.util.List;

import data.AttributeInfo;
import data.Record;

public class DecisionTree {
	private AttributeInfo[] attributeInfos;
	private List<Record> records;

	public DecisionTree(Pair<List<Record>, AttributeInfo[]> data) {
		this.records = data.getValue0();
		this.attributeInfos = data.getValue1();
	}

	public List<Record> getRecords() {
		return this.records;
	}

	public AttributeInfo[] getAttributeInfos() {
		return this.attributeInfos;
	}

	public void printData() {
		System.out.println("Attribute Info: ");
		for (AttributeInfo attInf : this.attributeInfos) {
			System.out.println(attInf);
		}

		System.out.println();

		for (Record record : this.records) {
			System.out.println(record);
		}
	}
}
