package program3.kmeans;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import program3.data.ClusteringRecord;
import program3.data.IClusteringRecord;

public class Kmeans {

	private int numberRecords;
	private int numberAttributes;
	private int numberClusters;

	private List<IClusteringRecord> records;
	private List<IClusteringRecord> centroids;
	private Random rand;
	private boolean traceCentroids = false;

	//loads records
	public void load(List<IClusteringRecord> clusteringRecords) throws FileNotFoundException {
		this.records = clusteringRecords;
		this.numberRecords = this.records.size();
		this.numberAttributes = this.records.get(0).getSize();
	}

	public void setTrace(boolean traceCentroids) {
		this.traceCentroids = traceCentroids;
	}

	//sets parameters of clustering
	public void setParameters(int numberClusters, int seed) {
		this.numberClusters = numberClusters;
		this.rand = new Random(seed);
	}

	//performs k-means clustering
	public void cluster() {
		this.initializeClusters();
		this.initializeCentroids();

		boolean stopCondition = false;

		while (!stopCondition) {
			int clusterChanges = this.assignClusters();
			this.updateCentroids();
			if (this.traceCentroids) {
				this.printCentroids("UPDATED CENTROIDS:");
			}
			stopCondition = clusterChanges == 0;
		}
	}

	public void printCentroids(String message) {
		System.out.println("\n" + message);
		for (IClusteringRecord record : this.centroids) {
			System.out.println(record);
		}

	}

	//updates centroids of clusters
	private void updateCentroids() {
		ArrayList<IClusteringRecord> clusterSum = new ArrayList<>();

		//for each cluster
		for (int i = 0; i < this.numberClusters; i++) {
			//create vector [0 0 . . . 0 ]
			double[] attributes = new double[this.numberAttributes];
			for (int j = 0; j < this.numberAttributes; j++) {
				attributes[j] = 0;
			}

			//initialize sum to [0 0 . . . 0]
			clusterSum.add(new ClusteringRecord(attributes));
		}

		//array of cluster sizes
		int[] clusterSize = new int[this.numberClusters];

		//for each record
		for (int i = 0; i < this.numberRecords; i++) {
			//find cluster of record
			int cluster = this.records.get(i).getCluster();

			//add record to cluster sum
			IClusteringRecord sum = ClusteringRecord.sum(clusterSum.get(cluster), this.records.get(i));
			clusterSum.set(cluster, sum);

			//increment cluster size
			clusterSize[cluster] += 1;
		}

		//for each cluster
		for (int i = 0; i < this.numberClusters; i++) {
			//find average by dividing cluster sum by cluster size
			IClusteringRecord average = ClusteringRecord.scale(clusterSum.get(i), 1.0 / clusterSize[i]);
			this.centroids.set(i, average);
		}

	}

	//initializes clusters of records
	private void initializeClusters() {
		for (int i = 0; i < this.numberRecords; i++) {
			this.records.get(i).setCluster(-1);
		}
	}

	private void initializeCentroids() {
		this.centroids = new ArrayList<>();

		for (int i = 0; i < this.numberClusters; i++) {
			int index = this.rand.nextInt(this.numberRecords);
			this.centroids.add(this.records.get(index));
		}

		this.printCentroids("INITIAL CENTROIDS:");

		if (this.traceCentroids) {
			System.out.println("\nCENTROID TRACING IS ON...");
		}

	}

	private int assignClusters() {
		int clusterChanges = 0;

		//go through records and assign clusters to them
		for (int i = 0; i < this.numberRecords; i++) {
			IClusteringRecord record = this.records.get(i);

			//find distance between record and first centroid
			double minDistance = ClusteringRecord.squaredDistance(record, this.centroids.get(0));
			int minIndex = 0;

			//go through centroids and find closest centroid
			for (int j = 0; j < this.numberClusters; j++) {
				//find distance between record and centroid
				double distance = ClusteringRecord.squaredDistance(record, this.centroids.get(j));

				if (distance < minDistance) {
					minDistance = distance;
					minIndex = j;
				}
			}

			//if closest cluster is different from current cluster
			if (this.records.get(i).getCluster() != minIndex) {
				//change cluster of record
				this.records.get(i).setCluster(minIndex);
				//keep count of cluster changes
				clusterChanges++;
			}
		}
		return clusterChanges;
	}

	public void display(String outputFile) throws IOException {
		PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		for (int i = 0; i < this.numberRecords; i++) {
			outFile.println(this.records.get(i));
		}
		outFile.close();
	}

	public List<IClusteringRecord> getRecords() {
		return this.records;
	}

	public double sumSquaredError() {
		double sse = 0;
		for (IClusteringRecord record : this.records) {
			sse += ClusteringRecord.squaredDistance(record, this.centroids.get(record.getCluster()));
		}
		return sse;
	}

	public void displayGrouped(String outputFile) throws IOException {
		PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		Map<Integer, List<IClusteringRecord>> groups = ClusteringRecord.sortRecordsByCluster(this.records);

		for (Map.Entry<Integer, List<IClusteringRecord>> group : groups.entrySet()) {
			for (IClusteringRecord record : group.getValue()) {
				outFile.println(record);
			}
		}
		outFile.close();
	}

}
