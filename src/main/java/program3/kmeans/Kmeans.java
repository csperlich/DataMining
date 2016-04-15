package program3.kmeans;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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

	//loads records from input file
	public void load(List<IClusteringRecord> clusteringRecords) throws FileNotFoundException {
		this.records = clusteringRecords;
		this.numberRecords = this.records.size();
		this.numberAttributes = this.records.get(0).getSize();
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
			stopCondition = clusterChanges == 0;
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
			IClusteringRecord sum = this.sum(clusterSum.get(cluster), this.records.get(i));
			clusterSum.set(cluster, sum);

			//increment cluster size
			clusterSize[cluster] += 1;
		}

		//for each cluster
		for (int i = 0; i < this.numberClusters; i++) {
			//find average by dividing cluster sum by cluster size
			IClusteringRecord average = this.scale(clusterSum.get(i), 1.0 / clusterSize[i]);
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
	}

	private int assignClusters() {
		int clusterChanges = 0;

		//go through records and assign clusters to them
		for (int i = 0; i < this.numberRecords; i++) {
			IClusteringRecord record = this.records.get(i);

			//find distance between record and first centroid
			double minDistance = this.distance(record, this.centroids.get(0));
			int minIndex = 0;

			//go through centroids and find closest centroid
			for (int j = 0; j < this.numberClusters; j++) {
				//find distance between record and centroid
				double distance = this.distance(record, this.centroids.get(j));

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

	//finds Euclidean distance between two records
	private double distance(IClusteringRecord u, IClusteringRecord v) {
		double sum = 0;

		for (int i = 0; i < u.getSize(); i++) {
			sum += (u.getAttributes()[i] - v.getAttributes()[i]) * (u.getAttributes()[i] - v.getAttributes()[i]);
		}
		return sum;
	}

	//finds sum of two records
	private IClusteringRecord sum(IClusteringRecord u, IClusteringRecord v) {
		double[] result = new double[u.getSize()];

		//find sum by adding corresponding attributes of records
		for (int i = 0; i < u.getSize(); i++) {
			result[i] = u.getAttributes()[i] + v.getAttributes()[i];
		}
		return new ClusteringRecord(result);
	}

	//finds scaler multiple of a record
	private IClusteringRecord scale(IClusteringRecord u, double k) {
		double[] result = new double[u.getSize()];

		//multiply attributes of record by scaler
		for (int i = 0; i < u.getSize(); i++) {
			result[i] = k * u.getAttributes()[i];
		}
		return new ClusteringRecord(result);
	}

	public void display(String outputFile) throws IOException {
		PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		for (int i = 0; i < this.numberRecords; i++) {
			//write attributes of record
			for (int j = 0; j < this.numberAttributes; j++) {
				outFile.print(this.records.get(i).getAttributes()[j] + " ");
			}
			outFile.println(this.records.get(i).getCluster() + 1);
		}
		outFile.close();
	}

	public List<IClusteringRecord> getRecords() {
		return this.records;
	}

}
