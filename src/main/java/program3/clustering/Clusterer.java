package program3.clustering;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import program2.data.RecordReader;
import program3.data.ClusteringRecord;
import program3.data.IClusteringRecord;

public abstract class Clusterer {

	protected int numberRecords;
	protected int numberAttributes;

	protected List<IClusteringRecord> records;
	protected RecordReader recordReader;

	//loads records
	public void load(List<IClusteringRecord> clusteringRecords) throws FileNotFoundException {
		this.records = clusteringRecords;
		this.numberRecords = this.records.size();
		this.numberAttributes = this.records.get(0).getSize();
	}

	//performs k-means clustering
	public abstract void cluster();

	//initializes clusters of records
	protected void initializeClusters() {
		for (int i = 0; i < this.numberRecords; i++) {
			this.records.get(i).setCluster(-1);
		}
	}

	public void display(String outputFile) throws IOException {
		PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		for (int i = 0; i < this.numberRecords; i++) {
			outFile.println(this.convert(this.records.get(i)));
		}
		outFile.close();
	}

	public List<IClusteringRecord> getRecords() {
		return this.records;
	}

	public abstract double sumSquaredError();

	public void displayGrouped(String outputFile) throws IOException {
		PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		Map<Integer, List<IClusteringRecord>> groups = ClusteringRecord.sortRecordsByCluster(this.records);

		for (Map.Entry<Integer, List<IClusteringRecord>> group : groups.entrySet()) {
			for (IClusteringRecord record : group.getValue()) {
				outFile.println(this.convert(record));
			}
		}
		outFile.close();
	}

	private String convert(IClusteringRecord record) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < record.getAttributes().length; i++) {
			sb.append(this.recordReader.convert(i, record.getAttributes()[i]) + " ");
		}
		sb.append(record.getCluster() + 1);
		return sb.toString();
	}
}
