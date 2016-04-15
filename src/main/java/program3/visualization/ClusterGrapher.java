package program3.visualization;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import program3.data.ClusteringRecord;
import program3.data.IClusteringRecord;

public class ClusterGrapher {

	/*creates scatter chart of clustered data
	 *RECORDS MUST HAVE 2 ATTRIBUTES; no more, no less 
	 */
	public static void graphClusters(List<IClusteringRecord> allRecords, String title, String xAxisLabel,
			String yAxisLabel, String fileName) throws IOException {
		if (allRecords.get(0).getAttributes().length != 2) {
			throw new IllegalArgumentException("Records must have 2 attributes to be graphed by this method!");
		}
		Map<Integer, List<IClusteringRecord>> sortedRecords = ClusteringRecord.sortRecordsByCluster(allRecords);

		XYSeriesCollection seriesLists = new XYSeriesCollection();
		for (Map.Entry<Integer, List<IClusteringRecord>> entry : sortedRecords.entrySet()) {

			XYSeries newSeries = new XYSeries("Cluster" + (entry.getKey() + 1));
			for (IClusteringRecord record : entry.getValue()) {
				newSeries.add(record.getAttributes()[0], record.getAttributes()[1]);
			}
			seriesLists.addSeries(newSeries);

		}

		JFreeChart xyScatterChart = ChartFactory.createScatterPlot(title, xAxisLabel, yAxisLabel, seriesLists,
				PlotOrientation.VERTICAL, true, true, false);

		int width = 640; /* Width of the image */
		int height = 480; /* Height of the image */
		File XYScatterChart = new File("images/" + fileName + ".jpeg");
		ChartUtilities.saveChartAsJPEG(XYScatterChart, xyScatterChart, width, height);
	}

}
