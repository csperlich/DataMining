package decisiontree;

import org.javatuples.Pair;

import java.util.List;

import data.attribute.AttributeInfo;
import data.record.Record;

public class DecisionTree {
	private List<AttributeInfo<?>> attributeInfos;
	private List<Record> records;
	private Node root;
	private double threshold = 1.0;

	public DecisionTree(Pair<List<Record>, List<AttributeInfo<?>>> data) {
		this.records = data.getValue0();
		this.attributeInfos = data.getValue1();
	}

	public void buildTree() {
		this.root = this.build(this.records, this.attributeInfos);
	}

	private Node build(List<Record> records, List<AttributeInfo<?>> attributeInfos) {
		Node node = null;

		if (this.isHomogeneous(records)) {
			node = Node.newLeafNode(this.records.get(0).getLabel());
		}
		return node;
	}

	private boolean isHomogeneous(List<Record> records) {
		String label = records.get(0).getLabel();
		for (Record record : records) {
			if (!record.getLabel().equals(label)) {
				return false;
			}
		}
		return true;
	}

	public List<Record> getRecords() {
		return this.records;
	}

	public List<AttributeInfo<?>> getAttributeInfos() {
		return this.attributeInfos;
	}

	public void printData() {
		System.out.println("Attribute Info: ");
		for (AttributeInfo<?> attInf : this.attributeInfos) {
			System.out.println(attInf);
		}

		System.out.println();

		for (Record record : this.records) {
			System.out.println(record);
		}
	}
}
