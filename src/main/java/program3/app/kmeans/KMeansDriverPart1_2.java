package program3.app.kmeans;

import static program3.app.kmeans.KMeansDriverTools.runTest;

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

		clustering.load(ClassificationRecordAdapter.adaptList(classificationRecords));

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

/*
SAMPLE OUTPUT
=============

------------------------------------------------------------

RUNNING KMEANS CLUSTERING ON PART1-2 DATA WITH CLUSTERS= 3 AND CLUSTER SEED=4539

CENTROID TRACING IS ON...

INITIAL CENTROIDS:
20.000 25.000 0
30.000 33.000 0
24.000 36.000 0

UPDATED CENTROIDS:
26.357 16.119 1
38.143 26.939 1
25.556 40.333 1

UPDATED CENTROIDS:
26.870 16.543 1
40.105 25.395 1
27.875 39.625 1

UPDATED CENTROIDS:
26.787 16.915 1
41.242 23.879 1
29.300 38.850 1

UPDATED CENTROIDS:
26.435 17.196 1
41.710 22.290 1
30.304 38.174 1

UPDATED CENTROIDS:
26.133 17.511 1
41.677 21.258 1
30.750 38.042 1

UPDATED CENTROIDS:
25.841 17.841 1
41.594 20.688 1
30.750 38.042 1

UPDATED CENTROIDS:
25.651 17.837 1
41.313 20.188 1
31.240 37.880 1

UPDATED CENTROIDS:
24.925 18.550 1
40.800 19.171 1
31.240 37.880 1

UPDATED CENTROIDS:
23.944 19.194 1
39.865 17.730 1
32.185 37.519 1

UPDATED CENTROIDS:
23.152 19.909 1
39.162 16.162 1
33.100 36.833 1

UPDATED CENTROIDS:
22.367 20.400 1
38.270 15.000 1
33.909 36.152 1

UPDATED CENTROIDS:
22.367 20.400 1
38.086 14.286 1
34.343 35.657 1

UPDATED CENTROIDS:
22.367 20.400 1
38.086 14.286 1
34.343 35.657 1

FINAL CENTROIDS:
22.367 20.400 1
38.086 14.286 1
34.343 35.657 1

SUM SQUARED ERROR IS = 6549.82380952381

WRITING RESULTS TO: program3_data/part1/part1-2_output_3clusters_4539clusterSeed
WRITING SCATTER CHART RESULTS TO: program3_data/part1/part1-2_output_3clusters_4539clusterSeed.jpeg

------------------------------------------------------------

RUNNING KMEANS CLUSTERING ON PART1-2 DATA WITH CLUSTERS= 3 AND CLUSTER SEED=1653

FINAL CENTROIDS:
25.025 19.650 1
34.950 34.325 1
40.350 10.050 1

SUM SQUARED ERROR IS = 6668.250000000004

WRITING RESULTS TO: program3_data/part1/part1-2_output_3clusters_1653clusterSeed
WRITING SCATTER CHART RESULTS TO: program3_data/part1/part1-2_output_3clusters_1653clusterSeed.jpeg

------------------------------------------------------------

RUNNING KMEANS CLUSTERING ON PART1-2 DATA WITH CLUSTERS= 2 AND CLUSTER SEED=4539

FINAL CENTROIDS:
30.133 16.450 1
34.950 34.325 1

SUM SQUARED ERROR IS = 11028.458333333332

WRITING RESULTS TO: program3_data/part1/part1-2_output_2clusters_4539clusterSeed
WRITING SCATTER CHART RESULTS TO: program3_data/part1/part1-2_output_2clusters_4539clusterSeed.jpeg

------------------------------------------------------------

RUNNING KMEANS CLUSTERING ON PART1-2 DATA WITH CLUSTERS= 5 AND CLUSTER SEED=4539

FINAL CENTROIDS:
22.367 20.400 1
39.440 30.440 1
25.800 35.700 1
31.333 46.167 1
38.034 12.172 1

SUM SQUARED ERROR IS = 3859.4567816091935

WRITING RESULTS TO: program3_data/part1/part1-2_output_5clusters_4539clusterSeed
WRITING SCATTER CHART RESULTS TO: program3_data/part1/part1-2_output_5clusters_4539clusterSeed.jpeg

------------------------------------------------------------

RUNNING KMEANS CLUSTERING ON PART1-2 DATA WITH CLUSTERS= 10 AND CLUSTER SEED=4539

FINAL CENTROIDS:
17.917 22.583 1
34.667 34.444 1
24.625 36.375 1
33.250 46.000 1
23.846 16.692 1
33.875 12.875 1
27.500 46.500 1
34.688 24.500 1
44.000 32.111 1
44.636 9.727  1

SUM SQUARED ERROR IS = 1564.5707556332547

WRITING RESULTS TO: program3_data/part1/part1-2_output_10clusters_4539clusterSeed
WRITING SCATTER CHART RESULTS TO: program3_data/part1/part1-2_output_10clusters_4539clusterSeed.jpeg
*/
