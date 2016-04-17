package program3.clustering;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import program2.data.RecordReader;
import program3.data.ClusteringRecord;
import program3.data.IClusteringRecord;

public class Kmeans extends Clusterer {

	private Random rand;
	private boolean traceCentroids = false;
	private int numberClusters;

	public Kmeans(RecordReader recordReader) {
		this.recordReader = recordReader;
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
	@Override
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

	private void initializeCentroids() {
		this.centroids = new ArrayList<>();

		for (int i = 0; i < this.numberClusters; i++) {
			int index = this.rand.nextInt(this.numberRecords);
			this.centroids.add(this.records.get(index));
		}

		if (this.traceCentroids) {
			System.out.println("\nCENTROID TRACING IS ON...");
			this.printCentroids("INITIAL CENTROIDS:");
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

	@Override
	public List<IClusteringRecord> getRecords() {
		return this.records;
	}

}
