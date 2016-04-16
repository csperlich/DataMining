package program3.app;

import static program3.app.GraphDriverTools.runTest;

import java.io.IOException;
import java.util.List;

import program2.data.Record;
import program2.data.RecordReader;
import program3.clustering.Graph;
import program3.data.ClassificationRecordAdapter;

public class GraphDriverPart2_4 {
	public static void main(String[] args) throws IOException {

		RecordReader recordReader = new RecordReader(true);
		List<Record> classificationRecords = recordReader.readTrainingRecords("program3_data/part2/part2-4_input");

		Graph clustering = new Graph(recordReader);

		//load data records
		clustering.load(ClassificationRecordAdapter.adaptList(classificationRecords));
		double delta = 1.0;

		double percentageChangeThreshold = .15;
		double percentageChange = Double.MAX_VALUE;
		double previousSumSquareError = Double.MAX_VALUE;
		int prevNumClusters = 0;

		do {
			delta -= .001;
			clustering.setParameters(delta);
			clustering.cluster();
			int newNumClusters = clustering.getNumberOfClusters();
			if (newNumClusters > prevNumClusters) {
				//System.out.println(newNumClusters);
				prevNumClusters = newNumClusters;

				double newSumSquareError = clustering.sumSquaredError();
				percentageChange = (previousSumSquareError - newSumSquareError) / previousSumSquareError;
				previousSumSquareError = newSumSquareError;

				//System.out.println(percentageChange);
			}
		} while (percentageChange > percentageChangeThreshold);

		delta += .0015;
		clustering.setParameters(delta);
		clustering.cluster();

		String outputFolder = "program3_data/part2/";
		String outputFileBaseName = "part2-4_output";

		System.out.println("\nTHE BEST DELTA VALUE IS " + delta);
		System.out.println("THE NUMBER OF CLUSTERS IS " + clustering.getNumberOfClusters());
		runTest(clustering, delta, outputFolder, outputFileBaseName, false);
	}
}
