package classifier.nearestneighbor;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

import classifier.Classifier;
import data.attribute.AttributeInfo;
import data.record.Record;

public class NearestNeighbor implements Classifier {

	private List<AttributeInfo<?>> attributeInfos;
	private List<Record> trainingRecords;
	private int numberNeighbors;

	public NearestNeighbor(Pair<List<Record>, List<AttributeInfo<?>>> data, int numberNeighbors) {
		this.trainingRecords = data.getValue0();
		this.attributeInfos = data.getValue1();
		this.numberNeighbors = numberNeighbors;
	}

	@Override
	public String classify(Record record) {

		List<Pair<Integer, Double>> idsAndDistances = new ArrayList<>();

		for (int i = 0; i < this.trainingRecords.size(); i++) {
			idsAndDistances.add(new Pair<Integer, Double>(i, this.distance(record, this.trainingRecords.get(i))));
		}

		return Record.getMajorityLabel(this.nearestNeighbors(idsAndDistances));

	}

	private List<Record> nearestNeighbors(List<Pair<Integer, Double>> idsAndDistances) {

		for (int i = 0; i < this.numberNeighbors; i++) {
			int minIndex = i;
			for (int j = i; j < this.trainingRecords.size(); j++) {
				if (idsAndDistances.get(minIndex).getValue1() > idsAndDistances.get(j).getValue1()) {
					minIndex = j;
				}
			}
			Pair<Integer, Double> temp = idsAndDistances.get(i);
			idsAndDistances.set(i, idsAndDistances.get(minIndex));
			idsAndDistances.set(minIndex, temp);
		}

		List<Record> records = new ArrayList<>();
		for (int i = 0; i < this.numberNeighbors; i++) {
			records.add(this.trainingRecords.get(idsAndDistances.get(i).getValue0()));
		}
		System.out.println(records);
		return records;
	}

	private double distance(Record record1, Record record2) {

		double sum = 0;

		for (int i = 0; i < record1.getSize(); i++) {
			double distance = this.attributeInfos.get(i).getAttributeDistance(record1.getAttribute(i),
					record2.getAttribute(i));
			sum += distance * distance;
		}

		return Math.sqrt(sum);

	}

	@Override
	public List<Record> getTrainingRecords() {
		return this.trainingRecords;
	}

	@Override
	public void setTrainingRecords(List<Record> records) {
		this.trainingRecords = records;
	}

	@Override
	public void buildClassifier() {
		// TODO Auto-generated method stub

	}

}
