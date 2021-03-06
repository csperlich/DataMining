package program3.clustering;

import java.util.ArrayList;
import java.util.LinkedList;

import program2.data.RecordReader;
import program3.data.ClusteringRecord;
import program3.data.IClusteringRecord;

//Graph basesd clustering class
public class Graph extends Clusterer {

	private double delta;	//neighbor threshold
	private int[][] matrix; //adjacency matrix

	public Graph(RecordReader recordReader) {
		this.recordReader = recordReader;
	}

	//sets parameters of clustering
	public void setParameters(double delta) {
		//set neighbor threshold
		this.delta = delta;

	}

	//method performs clustering
	@Override
	public void cluster() {
		this.centroids = new ArrayList<>();

		//create adjaceny matrix of records
		this.createMatrix();

		//initialize clusters of records
		this.initializeClusters();

		//initial record index is 0
		int index = 0;

		//initial cluster name is 0
		int clusterName = 0;

		//while ther are more records
		while (index < this.numberRecords) {
			//if record does not have clustesr name
			if (this.records.get(index).getCluster() == -1) {
				//assign cluster name to record and all records connected to it
				this.assignClusterAndCreateCentroid(index, clusterName);

				//find next cluster name
				clusterName = clusterName + 1;
			}

			//go to next record
			index = index + 1;
		}
	}

	//creates adjacency matrix
	private void createMatrix() {
		//allocate adjacency matrix
		this.matrix = new int[this.numberRecords][this.numberRecords];

		//entry (i, j) is 0 or 1 depending on i and j are neighbors or not
		for (int i = 0; i < this.numberRecords; i++) {
			for (int j = 0; j < this.numberRecords; j++) {
				this.matrix[i][j] = this.neighbor(this.records.get(i), this.records.get(j));
			}
		}
	}

	//method decides whether two records are neighbors or not
	//method is application specific
	private int neighbor(IClusteringRecord u, IClusteringRecord v) {
		double distance = 0;

		//find Euclidean distance between two records
		distance = Math.sqrt(ClusteringRecord.squaredDistance(u, v));

		//if distance is less than neighbor threshold recorsd are neighbors,
		//otherwise records are not neighbors
		if (distance <= this.delta) {
			return 1;
		} else {
			return 0;
		}
	}

	//method assigns cluster name to a record and all records
	//connected to it, uses breadth first traversal
	//it also calculates adds the centroid for the cluster to the centroid list
	private void assignClusterAndCreateCentroid(int index, int clusterName) {
		//assign cluster name to record
		this.records.get(index).setCluster(clusterName);

		//list used in traversal
		LinkedList<Integer> list = new LinkedList<Integer>();

		//put record into list
		list.addLast(index);

		IClusteringRecord centroid = null;

		int numRecsInCluster = 0;

		//while list has records
		while (!list.isEmpty()) {
			//remove first record from list
			int i = list.removeFirst();

			numRecsInCluster++;
			if (centroid == null) {
				centroid = this.records.get(i);
			} else {
				centroid = ClusteringRecord.sum(centroid, this.records.get(i));
			}

			//find neighbors of record which have no cluster names
			for (int j = 0; j < this.numberRecords; j++) {
				if (this.matrix[i][j] == 1 && this.records.get(j).getCluster() == -1) {
					//assign cluster name to neighbor
					this.records.get(j).setCluster(clusterName);

					//add neighbor to list
					list.addLast(j);
				}
			}
		}

		this.centroids.add(ClusteringRecord.scale(centroid, 1.0 / numRecsInCluster));
	}

}
