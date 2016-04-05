package program3.kmeans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Kmeans {

	private class Record {
		private double[] attributes;

		private Record(double[] attributes) {
			this.attributes = attributes;
		}
	}

	private int numberRecords;
	private int numberAttributes;
	private int numberClusters;

	private ArrayList<Record> records;
	private ArrayList<Record> centroids;
	private int[] clusters;
	private Random rand;

	//loads records from input file
	public void load(String inputFile) throws FileNotFoundException {
		Scanner inFile = new Scanner(new File(inputFile));

		this.numberRecords = inFile.nextInt();
		this.numberAttributes = inFile.nextInt();

		this.records = new ArrayList<Record>();

		for (int i = 0; i < this.numberRecords; i++) {

			double[] attributes = new double[this.numberAttributes];
			for (int j = 0; j < this.numberAttributes; j++) {
				attributes[j] = inFile.nextDouble();
			}

			Record record = new Record(attributes);
			this.records.add(record);
		}
		inFile.close();
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
		ArrayList<Record> clusterSum = new ArrayList<Record>();

		//for each cluster
		for (int i = 0; i < this.numberClusters; i++) {
			//create vector [0 0 . . . 0 ]
			double[] attributes = new double[this.numberAttributes];
			for (int j = 0; j < this.numberAttributes; j++) {
				attributes[j] = 0;
			}

			//initialize sum to [0 0 . . . 0]
			clusterSum.add(new Record(attributes));
		}

		//array of cluster sizes
		int[] clusterSize = new int[this.numberClusters];

		//initialize cluster sizes to 0
		for (int i = 0; i < this.numberClusters; i++) {
			clusterSize[i] = 0;
		}

		//for each record
		for (int i = 0; i < this.numberRecords; i++) {
			//find cluster of record
			int cluster = this.clusters[i];

			//add record to cluster sum
			Record sum = this.sum(clusterSum.get(cluster), this.records.get(i));
			clusterSum.set(cluster, sum);

			//increment cluster size
			clusterSize[cluster] += 1;
		}

		//for each cluster
		for (int i = 0; i < this.numberClusters; i++) {
			//find average by dividing cluster sum by cluster size
			Record average = this.scale(clusterSum.get(i), 1.0 / clusterSize[i]);
			this.centroids.set(i, average);
		}

	}

	//initializes clusters of records
	private void initializeClusters() {
		this.clusters = new int[this.numberRecords];

		for (int i = 0; i < this.numberRecords; i++) {
			this.clusters[i] = -1;
		}
	}

	private void initializeCentroids() {
		this.centroids = new ArrayList<Record>();

		for (int i = 0; i < this.numberClusters; i++) {
			int index = this.rand.nextInt(this.numberRecords);
			this.centroids.add(this.records.get(index));
		}
	}

	private int assignClusters() {
		int clusterChanges = 0;

		//go through records and assign clusters to them
		for (int i = 0; i < this.numberRecords; i++) {
			Record record = this.records.get(i);

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

			//if closest clsuter is different from current cluster
			if (this.clusters[i] != minIndex) {
				//change cluster of record
				this.clusters[i] = minIndex;
				//keep count of cluster changes
				clusterChanges++;
			}
		}
		return clusterChanges;
	}

	//finds Euclidean distance between two records
	private double distance(Record u, Record v) {
		double sum = 0;

		for (int i = 0; i < u.attributes.length; i++) {
			sum += (u.attributes[i] - v.attributes[i]) * (u.attributes[i] - v.attributes[i]);
		}
		return sum;
	}

	//finds sum of two records
	private Record sum(Record u, Record v) {
		double[] result = new double[u.attributes.length];

		//find sum by adding corresponding attributes of records
		for (int i = 0; i < u.attributes.length; i++) {
			result[i] = u.attributes[i] + v.attributes[i];
		}
		return new Record(result);
	}

	//finds scaler multiple of a record
	private Record scale(Record u, double k) {
		double[] result = new double[u.attributes.length];

		//multiply attributes of record by scaler
		for (int i = 0; i < result.length; i++) {
			result[i] = k * u.attributes[i];
		}
		return new Record(result);
	}

	public void display(String outputFile) throws IOException {
		PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		for (int i = 0; i < this.numberRecords; i++) {
			//write attributes of record
			for (int j = 0; j < this.numberAttributes; j++) {
				outFile.print(this.records.get(0).attributes[j] + " ");
			}
			outFile.println(this.clusters[i] + 1);
		}
		outFile.close();
	}

}
