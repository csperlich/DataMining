package program3.kmeans;

import java.io.IOException;
import java.util.List;

import program2.data.Record;
import program2.data.RecordReader;
import program3.data.ClassificationRecordAdapter;
import program3.visualization.ClusterGrapher;

public class Tester {
	public static void main(String[] args) throws IOException {
		//create clustering object
		Kmeans clustering = new Kmeans();

		RecordReader recordReader = new RecordReader(false);
		List<Record> classificationRecords = recordReader
				.readTrainingRecords("program3_data/example/kmeans/part1-2_input2");
		//load data records
		clustering.load(ClassificationRecordAdapter.adaptList(classificationRecords));

		//set parameters
		clustering.setParameters(2, 4539);

		//turn tracing on
		clustering.setTrace(true);

		//perform clustering
		clustering.cluster();

		//display records and clusters
		clustering.display("program3_data/example/kmeans/outputfile3");

		//display grouped records and clusters
		clustering.displayGrouped("program3_data/example/kmeans/outputfile3_grouped");

		//create graph of clustered data
		ClusterGrapher.graphClusters(clustering.getRecords(), "example1", "xAxis", "yAxis", "example1");
	}
}
