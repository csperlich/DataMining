import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DecisionTree {
	private class Node {
		private Node left;
		private Node right;

		private Node(Node left, Node right) {
			this.left = left;
			this.right = right;
		}
	}

	private class InternalNode extends Node {
		private Condition condition;

		public InternalNode(Node left, Node right, Condition condition) {
			super(left, right);
			this.condition = condition;
		}
	}

	private class LeafNode extends Node {
		private ClassName className;

		public LeafNode(ClassName className) {
			super(null, null);
			this.className = className;
		}
	}

	private Node root;
	private ArrayList<Record> records;
	private ArrayList<Attribute> attributes;
	private Set<ClassName> classNames;
	private int numRecords;
	private int numAttributes;

	public DecisionTree() {
	}

	public void buildTree() {
		this.root = this.build(this.records, this.attributes);
	}

	private Node build(ArrayList<Record> records, ArrayList<Attribute> attributes) {

		Node node = null;
		if (this.sameClass(records)) {
			ClassName className = records.get(0).getClassName();
			node = new LeafNode(className);
		} else if (attributes.isEmpty()) {
			ClassName className = this.majorityClass(records);
			node = new LeafNode(className);
		} else {
			Condition condition = bestCondition(records, attributes);
			ArrayList<Record> leftRecords = this.collect(records, condition, 0);
			ArrayList<Record> rightRecords = this.collect(records, condition, 1);

			if (leftRecords.isEmpty() || rightRecords.isEmpty()) {
				ClassName className = this.majorityClass(records);
				node = new LeafNode(className);
			} else {
				ArrayList<Attribute> leftAttributes = copyAttributes(attributes);
				ArrayList<Attribute> rightAttributes = copyAttributes(attributes);

				leftAttributes.remove(new Integer(condition.getColumnNumber()));
				rightAttributes.remove(new Integer(condition.getColumnNumber()));

				node = new InternalNode(null, null, condition);
				node.left = this.build(leftRecords, leftAttributes);
				node.right = this.build(rightRecords, rightAttributes);
			}
		}

		return node;
	}

	private ArrayList<Record> collect(ArrayList<Record> records, Condition condition) {
		ArrayList<Record> result = new ArrayList<>();

		for (Record record : records) {
			if (condition.test(record)) {
				result.add(record);
			}
		}
		return result;
	}

	private ClassName majorityClass(ArrayList<Record> records) {
		Map<ClassName, Integer> classFreqs = new HashMap<>();

		for (int i = 0; i < records.size(); i++) {
			ClassName className = records.get(i).getClassName();
			classFreqs.put(className, classFreqs.getOrDefault(className, 0) + 1);
		}

		ClassName className = null;
		int max = 0;
		for (Map.Entry<ClassName, Integer> entry : classFreqs.entrySet()) {
			if (entry.getValue() > max) {
				max = entry.getValue();
				className = entry.getKey();
			}
		}
		return className;
	}

	private boolean sameClass(ArrayList<Record> records) {
		for (int i = 0; i < records.size(); i++) {
			if (!records.get(i).getClassName().equals(records.get(0).getClassName())) {
				return false;
			}
		}
		return true;
	}
}
