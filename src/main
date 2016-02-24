package classifier.decisiontree;

import java.util.ArrayList;
import java.util.List;

import data.feature.Feature;

public class Node {
	private List<Node> children = new ArrayList<>();
	private Feature<?> feature;
	private String label;
	private boolean isLeaf;

	private Node(Feature<?> feature) {
		this.feature = feature;
	}

	private Node(String label) {
		this.label = label;
		this.isLeaf = true;
	}

	public boolean isLeaf() {
		return this.isLeaf;
	}

	public Feature<?> getFeature() {
		return this.feature;
	}

	public static Node newLeafNode(String label) {
		return new Node(label);
	}

	public static Node newInternalNode(Feature<?> feature) {
		return new Node(feature);
	}

	public Node getchild(int index) {
		return this.children.get(index);
	}

	public void addChild(Node child) {
		this.children.add(child);
	}

	public String getLabel() {
		return this.label;
	}

	public List<Node> getChildren() {
		return this.children;
	}

	public String simpleString() {
		if (!this.isLeaf) {
			return "feature->" + this.feature;
		}
		return "label->" + this.label;
	}

	@Override
	public String toString() {
		return "Node [numChildren=" + this.children.size() + ", feature=" + this.feature + ", label=" + this.label
				+ ", isLeaf=" + this.isLeaf;
	}
}
