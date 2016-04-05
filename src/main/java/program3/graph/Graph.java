package program3.graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

//Graph basesd clustering class
public class Graph {
	//data record class
	private class Record {
		private double[] attributes;

		private Record(double[] attributes) {
			this.attributes = attributes;
		}
	}

	private int numberRecords;
	private int numberAttributes;
	private double delta;	//neighbor threshold

	private ArrayList<Record> records;
	private int[][] matrix; //adjacency matrix
	private int[] clusters; //clusters of records

	public void load(String inputFile) throws IOException {
		Scanner inFile = new Scanner(new File(inputFile));

		this.numberRecords = inFile.nextInt();
		this.numberAttributes = inFile.nextInt();

		this.records = new ArrayList<Record>();

		for (int i = 0; i < this.numberRecords; i++) {
			//read attributes
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
	public void setParameters(double delta) {
		//set neighbor threshold
		this.delta = delta;
	}

	//method performs clustering
	public void cluster() {
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
			if (this.clusters[index] == -1) {
				//assign cluster name to record and all records connected to it
				this.assignCluster(index, clusterName);

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

		//entery (i, j) is 0 or 1 depending on i and j are neighbors or not
		for (int i = 0; i < this.numberRecords; i++) {
			for (int j = 0; j < this.numberRecords; j++) {
				this.matrix[i][j] = this.neighbor(this.records.get(i), this.records.get(j));
			}
		}
	}

	//method decides whether two records are neighbors or not
	//method is application specific
	private int neighbor(Record u, Record v) {
		double distance = 0;

		//find Euclidean distance between two records
		for (int i = 0; i < u.attributes.length; i++) {
			distance += (u.attributes[i] - v.attributes[i]) * (u.attributes[i] - v.attributes[i]);
		}

		distance = Math.sqrt(distance);

		//if distance is less than neighbor threshold recorsd are neighbors,
		//otherwise records are not neighbors
		if (distance <= this.delta) {
			return 1;
		} else {
			return 0;
		}
	}

	//initializes clusters of records
	private void initializeClusters() {
		//create array of cluster labels
		this.clusters = new int[this.numberRecords];

		//assign cluster -1 to all records
		for (int i = 0; i < this.numberRecords; i++) {
			this.clusters[i] = -1;
		}
	}

	//method assigns cluster name to a record and all records
	//connected to it, uses breadth first traversal
	private void assignCluster(int index, int clusterName) {
		//assign cluster name to record
		this.clusters[index] = clusterName;

		//list used in traversal
		LinkedList<Integer> list = new LinkedList<Integer>();

		//put record into list
		list.addLast(index);

		//while list has records
		while (!list.isEmpty()) {
			//remove first record from list
			int i = list.removeFirst();

			//find neighbors of record which have no cluster names
			for (int j = 0; j < this.numberRecords; j++) {
				if (this.matrix[i][j] == 1 && this.clusters[j] == -1) {
					//assign cluster name to neighbor
					this.clusters[j] = clusterName;

					//add neighbor to list
					list.addLast(j);
				}
			}
		}
	}

	//writes records and their clusters to output file
	public void display(String outputFile) throws IOException {
		PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		for (int i = 0; i < this.numberRecords; i++) {
			//write attributes of record
			for (int j = 0; j < this.numberAttributes; j++) {
				outFile.print(this.records.get(i).attributes[j] + " ");
			}

			//write cluster
			outFile.println(this.clusters[i] + 1);
		}

		outFile.close();
	}

}
