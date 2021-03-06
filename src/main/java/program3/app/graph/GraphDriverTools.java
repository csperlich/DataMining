package program3.app.graph;

import java.io.IOException;

import program3.clustering.Graph;
import program3.visualization.ClusterGrapher;

public class GraphDriverTools {
	public static void runTest(Graph clustering, double delta, String outputFolder, String outputFileName,
			boolean graphResults) throws IOException {

		String outputFileNameWithParams = outputFileName + "_" + delta + "delta";
		System.out.println("\n------------------------------------------------------------\n");
		System.out.println("RUNNING KMEANS CLUSTERING ON PART2-2 DATA WITH DELTA=" + delta);

		clustering.setParameters(delta);

		clustering.cluster();

		System.out.println("\nSUM SQUARED ERROR = " + clustering.sumSquaredError());
		System.out.println("NUMBER OF CLUSTERS = " + clustering.getNumberOfClusters());

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
