package program3.graph;

import java.io.IOException;
import java.util.List;

import program2.data.Record;
import program2.data.RecordReader;
import program3.data.ClassificationRecordAdapter;

public class Tester {
	public static void main(String[] args) throws IOException {

		//load data records
		RecordReader recordReader = new RecordReader(false);
		List<Record> classificationRecords = recordReader
				.readTrainingRecords("program3_data/example/graph/part2-2_input2");
		Graph clustering = new Graph(recordReader);
		clustering.load(ClassificationRecordAdapter.adaptList(classificationRecords));

		//set parameters
		clustering.setParameters(3);

		//perform clustering
		clustering.cluster();

		//display records and clusters
		clustering.display("program3_data/example/graph/outputfile3");
	}
}
