package program3.app;

import java.io.IOException;
import java.util.List;

import program2.data.Record;
import program2.data.RecordReader;
import program3.data.ClassificationRecordAdapter;
import program3.kmeans.Kmeans;
import program3.visualization.ClusterGrapher;

public class KMeansDriverPart1_2 {
	public static void main(String[] args) throws IOException {
		//create clustering object
		Kmeans clustering = new Kmeans();

		RecordReader recordReader = new RecordReader(false);
		List<Record> classificationRecords = recordReader.readTrainingRecords("program3_data/part1/part1-2_input");

		//load data records
		clustering.load(ClassificationRecordAdapter.adaptList(classificationRecords));

		//turn tracing on
		clustering.setTrace(true);

		String outputFolder = "program3_data/part1/";
		String outputFileBaseName = "part1-2_output";

		runTest(clustering, 3, 4539, outputFolder, outputFileBaseName);

		clustering.setTrace(false);

		//change initial centroids
		runTest(clustering, 3, 1653, outputFolder, outputFileBaseName);

		//change number of centroids
		runTest(clustering, 2, 4539, outputFolder, outputFileBaseName);
		runTest(clustering, 5, 4539, outputFolder, outputFileBaseName);
		runTest(clustering, 10, 4539, outputFolder, outputFileBaseName);

	}

	public static void runTest(Kmeans clustering, int numClusters, int clusterSeed, String outputFolder,
			String outputFileName) throws IOException {

		String outputFileNameWithParams = outputFileName + "_" + numClusters + "clusters_" + clusterSeed
				+ "clusterSeed";

		System.out.println("RUNNING KMEANS CLUSTERING ON PART1-2 DATA WITH 2 CLUSTERS");
		//set parameters
		clustering.setParameters(numClusters, clusterSeed);

		//perform clustering
		clustering.cluster();

		clustering.printCentroids("FINAL CENTROIDS:");

		System.out.println("\nSUM SQUARED ERROR IS = " + clustering.sumSquaredError());

		System.out.println("\nWRITING RESULTS TO: " + outputFolder + outputFileNameWithParams);
		clustering.displayGrouped(outputFolder + outputFileNameWithParams);

		System.out.println("WRITING SCATTER CHART RESULTS TO: " + outputFolder + outputFileNameWithParams + ".jpeg");
		ClusterGrapher.graphClusters(clustering.getRecords(), outputFolder + outputFileNameWithParams, "xAxis", "yAxis",
				outputFolder + outputFileNameWithParams);
	}
}
