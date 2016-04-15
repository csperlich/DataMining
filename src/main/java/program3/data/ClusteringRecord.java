package program3.data;

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

}
