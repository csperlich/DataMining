package program3.app;

import static program3.app.GraphDriverTools.runTest;

import java.io.IOException;
import java.util.List;

import program2.data.Record;
import program2.data.RecordReader;
import program3.clustering.Graph;
import program3.data.ClassificationRecordAdapter;

public class GraphDriverPart2_2 {
	public static void main(String[] args) throws IOException {
		RecordReader recordReader = new RecordReader(false);
		List<Record> classificationRecords = recordReader.readTrainingRecords("program3_data/part2/part2-2_input");

		Graph clustering = new Graph(recordReader);

		//load data records
		clustering.load(ClassificationRecordAdapter.adaptList(classificationRecords));

		runPart2_2Test(clustering, 3);
		runPart2_2Test(clustering, 1);
		runPart2_2Test(clustering, 5);
		runPart2_2Test(clustering, 10);

	}

	public static void runPart2_2Test(Graph clustering, double delta) throws IOException {
		runTest(clustering, delta, "program3_data/part2/", "part2-2_output", true);
	}
}
