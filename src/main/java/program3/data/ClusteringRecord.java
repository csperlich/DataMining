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

}
