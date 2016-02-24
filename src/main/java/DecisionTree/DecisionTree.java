package decisiontree;

import org.javatuples.Pair;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import data.attribute.AttributeInfo;
import data.feature.Feature;
import data.record.Record;
import entropy.EntropyMeasure;

public class DecisionTree {
	private List<AttributeInfo<?>> attributeInfos;
	private List<Record> records;
	private Node root;
	private double threshold = 1.0;
	private EntropyMeasure entropyMeasure;

	private boolean oneQuestionPerAttribute = true;

	public DecisionTree(Pair<List<Record>, List<AttributeInfo<?>>> data, EntropyMeasure entropyMeasure) {
		this.records = data.getValue0();
		this.attributeInfos = data.getValue1();
		this.entropyMeasure = entropyMeasure;
	}

	public void buildTree() {
		this.root = this.build(this.records, this.attributeInfos);
	}

	private Node build(List<Record> records, List<AttributeInfo<?>> attributeInfos) {
		Node node = null;
		System.out.println("Records: " + records);
		if (this.isHomogeneous(records)) {
			node = Node.newLeafNode(records.get(0).getLabel());
			System.out.println("Homgenous, adding new leaf node: " + node + "\n");
		} else if (attributeInfos.isEmpty()) {
			node = Node.newLeafNode(this.majorityLabel(records));
			System.out.println("attributes empty, adding new leaf node: " + node + "\n");
		} else {

			// get best feature
			Feature<?> bestFeature = this.bestFeature(records, attributeInfos);
			System.out.println("Creating bestFeature: " + bestFeature);
			// split records
			List<List<Record>> splitRecords = bestFeature.splitRecords(records);

			// if only one group left, make leaf node with majority class
			if (splitRecords.size() == 1) {
				node = Node.newLeafNode(this.majorityLabel(splitRecords.get(0)));
				System.out.println("Best Split has only one group, cerating leaf Node " + node + "\n");
			} else {

				node = Node.newInternalNode(bestFeature);
				System.out.println("Creating internal node: " + node);
				if (this.oneQuestionPerAttribute) {
					// remove attribute from list
					AttributeInfo<?> attributeInfo = bestFeature.getAttributeInfo();
					attributeInfos.remove(attributeInfo);

					System.out.println("Attching " + splitRecords.size() + " children..." + "\n");
					// create n child nodes with the attribute removed
					for (List<Record> recordGroup : splitRecords) {
						node.addChild(this.build(recordGroup, attributeInfos));
					}

					// replace the attribute
					attributeInfos.add(attributeInfo);
				} else {
					// TODO: IMPLEMENT THIS
					/*
					 * AttributeInfo<?> attributeInfo =
					 * bestFeature.getAttributeInfo();
					 * attributeInfo.getFeatures().remove(bestFeature); boolean
					 * attributeRemoved = false; if
					 * (attributeInfo.getFeatures().isEmpty()) {
					 * attributeInfos.remove(attributeInfo); attributeRemoved =
					 * true; }
					 *
					 * for (List<Record> recordGroup : splitRecords) {
					 * node.addChild(this.build(recordGroup, attributeInfos)); }
					 *
					 * // replace the removed attribute if (attributeRemoved) {
					 * attributeInfos.add(attributeInfo); }
					 *
					 * // replace the feature
					 * attributeInfo.getFeatures().add(bestFeature);
					 */
				}
			}
		}

		return node;
	}

	private Feature<?> bestFeature(List<Record> records, List<AttributeInfo<?>> attributeInfos) {
		double minValue = Double.MAX_VALUE;
		Feature<?> bestFeature = null;

		for (AttributeInfo<?> attributeInfo : attributeInfos) {
			for (Feature<?> feature : attributeInfo.getFeatures()) {
				List<List<Record>> splitRecords = feature.splitRecords(records);
				double value = this.entropyMeasure.weightedAverage(splitRecords);
				if (value < minValue) {
					minValue = value;
					bestFeature = feature;
				}
			}
		}

		return bestFeature;
	}

	private Map<String, Long> getLabelCounts(List<Record> records) {
		return records.parallelStream().collect(Collectors.groupingBy(Record::getLabel, Collectors.counting()));
	}

	private String majorityLabel(List<Record> records) {
		Map<String, Long> labelCounts = this.getLabelCounts(records);
		return labelCounts.entrySet().parallelStream().max(Map.Entry.comparingByValue()).get().getKey();
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

	public void printTree() {
		this.preOrderPrint(this.root);
	}

	private void preOrderPrint(Node root) {
		if (root.isLeaf()) {
			System.out.println(root);
			return;
		}

		System.out.println(root);
		for (Node child : root.getChildren()) {
			this.preOrderPrint(child);
		}

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
