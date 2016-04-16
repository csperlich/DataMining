package program3.app;

import static program3.app.KMeansDriverTools.runTest;

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
