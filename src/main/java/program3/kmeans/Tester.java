package program3.kmeans;

import java.io.IOException;

public class Tester {
	public static void main(String[] args) throws IOException {
		//create clustering object
		Kmeans clustering = new Kmeans();

		//load data records
		clustering.load("program3_data/example/kmeans/part1-2_input");

		//set parameters
		clustering.setParameters(2, 4539);

		//perform clustering
		clustering.cluster();

		//display records and clusters
		clustering.display("program3_data/example/kmeans/outputfile");
	}
}
