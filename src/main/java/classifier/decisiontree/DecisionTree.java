package classifier.decisiontree;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import classifier.Classifier;
import data.attribute.AttributeInfo;
import data.feature.Feature;
import data.record.Record;
import entropy.EntropyMeasure;

public class DecisionTree implements Classifier {
	private List<AttributeInfo<?>> attributeInfos;
	private List<Record> trainingRecords;
	private Node root;
	private boolean trace = false;
	private EntropyMeasure entropyMeasure;

	private boolean oneQuestionPerAttribute = true; // TODO: do not change this
													// until code to handle it
													// is in place

	public DecisionTree(List<Record> trainingRecords, List<AttributeInfo<?>> attributeInfos,
			EntropyMeasure entropyMeasure) {
		this.trainingRecords = trainingRecords;
		this.attributeInfos = new LinkedList<>();
		this.attributeInfos.addAll(attributeInfos);
		this.entropyMeasure = entropyMeasure;
	}

	public void setTrace(boolean trace) {
		this.trace = trace;
	}

	public void buildTree() {
		this.root = this.build(this.trainingRecords, this.attributeInfos);
		this.reorderAttributeInfos();
	}

	@Override
	public void setTrainingRecords(List<Record> records) {
		this.trainingRecords = records;
	}

	public void setEntropyMeasure(EntropyMeasure entropyMeasure) {
		this.entropyMeasure = entropyMeasure;
	}

	private void tracePrint(List<Record> records, List<AttributeInfo<?>> attributeInfos, Node node, String reason) {
		System.out.println("NODE CREATED --> " + reason);
		System.out.println(node);
		System.out.println(records);
		System.out.println(attributeInfos);
		System.out.println();
	}

	private Node build(List<Record> records, List<AttributeInfo<?>> attributeInfos) {
		Node node = null;

		if (this.isHomogeneous(records)) {
			node = Node.newLeafNode(records.get(0).getLabel());
			if (this.trace) {
				this.tracePrint(records, attributeInfos, node, "homogenous records");
			}
		} else if (attributeInfos.isEmpty()) {
			node = Node.newLeafNode(Record.getMajorityLabel(records));
			if (this.trace) {
				this.tracePrint(records, attributeInfos, node, "no more features");
			}
		} else {

			// get best feature
			Feature<?> bestFeature = this.bestFeature(records, attributeInfos);

			// split records
			List<List<Record>> splitRecords = bestFeature.splitRecords(records);

			// if only one group left, make leaf node with majority class
			if (splitRecords.size() == 1) {
				node = Node.newLeafNode(Record.getMajorityLabel(splitRecords.get(0)));
				if (this.trace) {
					this.tracePrint(records, attributeInfos, node, "all records satisfy feature");
				}
			} else {

				node = Node.newInternalNode(bestFeature);
				if (this.trace) {
					this.tracePrint(records, attributeInfos, node, "internal node");
				}
				if (this.oneQuestionPerAttribute) {
					// remove attribute from list
					AttributeInfo<?> attributeInfo = bestFeature.getAttributeInfo();
					attributeInfos.remove(attributeInfo);

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

	private void reorderAttributeInfos() {

		this.attributeInfos = this.attributeInfos.parallelStream().sorted((AttributeInfo<?> a1,
				AttributeInfo<?> a2) -> Integer.compare(a1.getColumnNumber(), a2.getColumnNumber()))
				.collect(Collectors.toList());
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

	private boolean isHomogeneous(List<Record> records) {
		String label = records.get(0).getLabel();
		for (Record record : records) {
			if (!record.getLabel().equals(label)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<Record> getTrainingRecords() {
		return this.trainingRecords;
	}

	public List<AttributeInfo<?>> getAttributeInfos() {
		return this.attributeInfos;
	}

	public void printTree() {
		this.preOrderPrint(this.root, "Root");
	}

	private void preOrderPrint(Node root, String path) {
		System.out.printf("Path:%-20sNode:%s\n", path, root.simpleString());
		if (root.isLeaf()) {
			return;
		}

		// System.out.println(root);
		for (int i = 0; i < root.getChildren().size(); i++) {
			char direction = i == 0 ? 'L' : 'R';
			this.preOrderPrint(root.getchild(i), path + "," + direction);
			// this.preOrderPrint(root.getchild(i), path + "," + i);
		}

	}

	public void printData() {
		System.out.println("Attribute Info: ");
		for (AttributeInfo<?> attInf : this.attributeInfos) {
			System.out.println(attInf);
		}

		System.out.println();

		for (Record record : this.trainingRecords) {
			System.out.println(record);
		}
	}

	@Override
	public String classify(Record record) {
		Node node = this.root;
		while (!node.isLeaf()) {
			int bin = node.getFeature().binRecord(record);
			node = node.getchild(bin);
		}
		return node.getLabel();
	}

	@Override
	public double trainingError() {
		int numCorrect = 0;
		for (Record record : this.trainingRecords) {
			String classification = this.classify(record);
			if (classification.equals(record.getLabel())) {
				numCorrect++;
			}
			// System.out.println("guess: " + classification + ", actual: " +
			// record.getLabel());
		}
		return 1.0 - (double) numCorrect / this.trainingRecords.size();
	}

	public boolean checkForDuplicateRecords() {
		for (int i = 0; i < this.trainingRecords.size() - 1; i++) {
			for (int j = i + 1; j < this.trainingRecords.size(); j++) {
				if (this.trainingRecords.get(i).equals(this.trainingRecords.get(j))) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void buildClassifier() {
		this.buildTree();
	}

}
