package program3.app;

import static program3.app.KMeansDriverTools.runTest;

import java.io.IOException;
import java.util.List;

import program2.data.Record;
import program2.data.RecordReader;
import program3.clustering.Kmeans;
import program3.data.ClassificationRecordAdapter;

public class KMeansDriverPart1_2 {
	public static void main(String[] args) throws IOException {

		RecordReader recordReader = new RecordReader(false);
		List<Record> classificationRecords = recordReader.readTrainingRecords("program3_data/part1/part1-2_input");

		Kmeans clustering = new Kmeans(recordReader);

		//load data records
		clustering.load(ClassificationRecordAdapter.adaptList(classificationRecords));

		//turn tracing on
		clustering.setTrace(true);

		runPart1_2Test(clustering, 3, 4539);

		clustering.setTrace(false);

		//change initial centroids
		runPart1_2Test(clustering, 3, 1653);

		//change number of centroids
		runPart1_2Test(clustering, 2, 4539);
		runPart1_2Test(clustering, 5, 4539);
		runPart1_2Test(clustering, 10, 4539);

	}

	public static void runPart1_2Test(Kmeans clustering, int numClusters, int clusterSeed) throws IOException {
		runTest(clustering, numClusters, clusterSeed, "program3_data/part1/", "part1-2_output", true);
	}

}
