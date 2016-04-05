package program3.graph;

import java.io.IOException;

public class Tester {
	public static void main(String[] args) throws IOException {
		//create clustring object
		Graph clustering = new Graph();

		//load data records
		clustering.load("program3_data/example/graph/inputfile");

		//perform clustering
		clustering.cluster();

		//display records and clusters
		clustering.display("program3_data/example/graph/outputfile");
	}
}
