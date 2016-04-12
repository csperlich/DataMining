package program3.kmeans;

import java.io.IOException;
import java.util.List;

import program2.data.Record;
import program2.data.RecordReader;
import program3.data.ClassificationRecordAdapter;

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

		//perform clustering
		clustering.cluster();

		//display records and clusters
		clustering.display("program3_data/example/kmeans/outputfile2");
	}
}
