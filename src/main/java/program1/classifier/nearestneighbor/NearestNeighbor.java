package program1.classifier.nearestneighbor;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

import program1.classifier.Classifier;
import program1.data.attribute.AttributeInfo;
import program1.data.record.Record;
import program1.majorityrule.MajorityRule;

public class NearestNeighbor implements Classifier {

	private List<AttributeInfo<?>> attributeInfos;
	private List<Record> trainingRecords;
	private int numberNeighbors;
	private MajorityRule majorityRule;
	private boolean trace;

	public NearestNeighbor(Pair<List<Record>, List<AttributeInfo<?>>> data, int numberNeighbors,
			MajorityRule majorityRule) {
		this.trainingRecords = data.getValue0();
		this.attributeInfos = data.getValue1();
		this.numberNeighbors = numberNeighbors;
		this.majorityRule = majorityRule;
		this.setTrace(false);
	}

	public void setMajorityRule(MajorityRule majorityRule) {
		this.majorityRule = majorityRule;
	}

	public void setNumberNeighbors(int numberNeighbors) {
		this.numberNeighbors = numberNeighbors;
	}

	@Override
	public String classify(Record record) {

		List<Pair<Integer, Double>> idsAndDistances = new ArrayList<>();

		for (int i = 0; i < this.trainingRecords.size(); i++) {
			idsAndDistances.add(new Pair<Integer, Double>(i, this.distance(record, this.trainingRecords.get(i))));
		}
		List<Record> nearestRecs = this.nearestNeighbors(idsAndDistances);
		String label = this.majorityRule.getMajorityLabel(nearestRecs, idsAndDistances);

		if (this.trace) {
			this.printTrace(record, label, idsAndDistances, nearestRecs);
		}

		return label;
	}

	private void printTrace(Record record, String label, List<Pair<Integer, Double>> idsAndDistances,
			List<Record> nearestNeighbors) {
		System.out.println("RECORD CLASSIFIED -> CLASS = " + label);
		System.out.println("Record: " + record.csvString(' '));
		System.out.println("----------------------------------------------------");
		System.out.println("ALL TRAINING RECORDS AND DISTANCES TO TARGET RECORD:");
		for (int i = 0; i < idsAndDistances.size(); i++) {
			Record trainingRecord = this.trainingRecords.get(i);
			double distance = idsAndDistances.get(i).getValue1();
			System.out.println(trainingRecord.csvString('\t') + "\tdistance = " + distance);
		}
		System.out.println("--------------------------------------------------------");
		System.out.println("NEAREST NEIGHBOR RECORDS AND DISTANCES TO TARGET RECORD:");
		for (int i = 0; i < nearestNeighbors.size(); i++) {
			Record nearest = nearestNeighbors.get(i);
			double distance = idsAndDistances.get(i).getValue1();
			System.out.println(nearest.csvString('\t') + "\tdistance = " + distance);
		}
		System.out.println("\n");

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

	public Pair<Integer, Double> getOptimalNumNeighborsAndError() {
		double optimalError = Double.MAX_VALUE;
		int optimalNumNeighbors = -1;

		for (int i = 1; i < this.trainingRecords.size(); i++) {
			this.setNumberNeighbors(i);
			double error = this.validateLeaveOneOut();
			if (error < optimalError) {
				optimalError = error;
				optimalNumNeighbors = i;
			}
		}
		return new Pair<Integer, Double>(optimalNumNeighbors, optimalError);
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

	@Override
	public void setTrace(boolean trace) {
		this.trace = trace;
	}

	public int getNumNeighbors() {
		return this.numberNeighbors;
	}

}
