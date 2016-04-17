package program3.app.kmeans;

import static program3.app.kmeans.KMeansDriverTools.runTest;

import java.io.IOException;
import java.util.List;

import program2.data.Record;
import program2.data.RecordReader;
import program3.clustering.Kmeans;
import program3.data.ClassificationRecordAdapter;

public class KMeansDriverPart1_4 {
	public static void main(String[] args) throws IOException {

		RecordReader recordReader = new RecordReader(true);
		List<Record> classificationRecords = recordReader.readTrainingRecords("program3_data/part1/part1-4_input");

		Kmeans clustering = new Kmeans(recordReader);

		//load data records
		clustering.load(ClassificationRecordAdapter.adaptList(classificationRecords));
		int clusterSeed = 4536;
		int numClusters = 1;

		double percentageIncreaseThreshold = .15;
		double previousSumSquareError = Double.MAX_VALUE;

		while (true) {
			clustering.setParameters(numClusters, clusterSeed);
			clustering.cluster();
			double currentSumSquareError = clustering.sumSquaredError();
			double difference = previousSumSquareError - currentSumSquareError;
			double percentageChange = difference / previousSumSquareError;

			if (percentageChange < percentageIncreaseThreshold) {
				break;
			}
			previousSumSquareError = currentSumSquareError;
			numClusters++;
		}

		if (numClusters > 1) {
			numClusters--;
		}

		String outputFolder = "program3_data/part1/";
		String outputFileBaseName = "part1-4_output";

		System.out.println("\nBEST NUMBER OF CLUSTERS IS " + numClusters);
		runTest(clustering, numClusters, clusterSeed, outputFolder, outputFileBaseName, false);
	}
}

/*
SAMPLE OUTPUT
=============

BEST NUMBER OF CLUSTERS IS 3

------------------------------------------------------------

RUNNING KMEANS CLUSTERING ON PART1-2 DATA WITH CLUSTERS= 3 AND CLUSTER SEED=4536

FINAL CENTROIDS:
0.448  0.750  0.688  1
0.740  0.067  0.323  1
0.106  0.366  0.063  1

SUM SQUARED ERROR IS = 1.3142041053921572

WRITING RESULTS TO: program3_data/part1/part1-4_output_3clusters_4536clusterSeed

*/