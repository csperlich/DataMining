package program3.data;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ClusteringRecord implements IClusteringRecord {

	private double[] attributes;
	private int cluster;

	public ClusteringRecord(double[] attributes) {
		this.attributes = attributes;
	}

	@Override
	public int getCluster() {
		return this.cluster;
	}

	@Override
	public void setCluster(int cluster) {
		this.cluster = cluster;
	}

	@Override
	public int getSize() {
		return this.attributes.length;
	}

	@Override
	public double[] getAttributes() {
		return this.attributes;
	}

	public static Map<Integer, List<IClusteringRecord>> sortRecordsByCluster(List<IClusteringRecord> records) {
		return records.stream().collect(Collectors.groupingBy(new Function<IClusteringRecord, Integer>() {
			@Override
			public Integer apply(IClusteringRecord t) {
				return t.getCluster();
			}
		}));
	}

	@Override
	public String toString() {
		return this.toPrettyString();
	}

	public static double squaredDistance(IClusteringRecord record1, IClusteringRecord record2) {
		double sum = 0;
		for (int i = 0; i < record1.getSize(); i++) {
			sum += Math.pow(record1.getAttributes()[i] - record2.getAttributes()[i], 2);
		}
		return sum;
	}

	//finds scaler multiple of a record
	public static IClusteringRecord scale(IClusteringRecord u, double k) {
		double[] result = new double[u.getSize()];

		//multiply attributes of record by scaler
		for (int i = 0; i < u.getSize(); i++) {
			result[i] = k * u.getAttributes()[i];
		}
		return new ClusteringRecord(result);
	}

	//finds sum of two records
	public static IClusteringRecord sum(IClusteringRecord u, IClusteringRecord v) {
		double[] result = new double[u.getSize()];

		//find sum by adding corresponding attributes of records
		for (int i = 0; i < u.getSize(); i++) {
			result[i] = u.getAttributes()[i] + v.getAttributes()[i];
		}
		return new ClusteringRecord(result);
	}

}
