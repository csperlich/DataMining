package program3.app.graph;

import static program3.app.graph.GraphDriverTools.runTest;

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

/*
SAMPLE OUTPUT
=============

------------------------------------------------------------

RUNNING KMEANS CLUSTERING ON PART2-2 DATA WITH DELTA=3.0

SUM SQUARED ERROR = 159.24242999999993
NUMBER OF CLUSTERS = 4

WRITING RESULTS TO: program3_data/part2/part2-2_output_3.0delta
WRITING SCATTER CHART RESULTS TO: program3_data/part2/part2-2_output_3.0delta.jpeg

------------------------------------------------------------

RUNNING KMEANS CLUSTERING ON PART2-2 DATA WITH DELTA=1.0

SUM SQUARED ERROR = 62.83695000000002
NUMBER OF CLUSTERS = 5

WRITING RESULTS TO: program3_data/part2/part2-2_output_1.0delta
WRITING SCATTER CHART RESULTS TO: program3_data/part2/part2-2_output_1.0delta.jpeg

------------------------------------------------------------

RUNNING KMEANS CLUSTERING ON PART2-2 DATA WITH DELTA=5.0

SUM SQUARED ERROR = 1368.56967375
NUMBER OF CLUSTERS = 2

WRITING RESULTS TO: program3_data/part2/part2-2_output_5.0delta
WRITING SCATTER CHART RESULTS TO: program3_data/part2/part2-2_output_5.0delta.jpeg

------------------------------------------------------------

RUNNING KMEANS CLUSTERING ON PART2-2 DATA WITH DELTA=10.0

SUM SQUARED ERROR = 3177.7940460000004
NUMBER OF CLUSTERS = 1

WRITING RESULTS TO: program3_data/part2/part2-2_output_10.0delta
WRITING SCATTER CHART RESULTS TO: program3_data/part2/part2-2_output_10.0delta.jpeg

 */
