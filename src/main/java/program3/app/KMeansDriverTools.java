package program3.app;

import java.io.IOException;

import program3.kmeans.Kmeans;
import program3.visualization.ClusterGrapher;

public class KMeansDriverTools {
	public static void runTest(Kmeans clustering, int numClusters, int clusterSeed, String outputFolder,
			String outputFileName, boolean graphResults) throws IOException {

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

		if (graphResults) {
			System.out
					.println("WRITING SCATTER CHART RESULTS TO: " + outputFolder + outputFileNameWithParams + ".jpeg");
			ClusterGrapher.graphClusters(clustering.getRecords(), outputFolder + outputFileNameWithParams, "xAxis",
					"yAxis", outputFolder + outputFileNameWithParams);
		}
	}
}