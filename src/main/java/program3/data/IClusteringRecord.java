package program3.data;

public interface IClusteringRecord {
	public int getCluster();

	public void setCluster(int cluster);

	public int getSize();

	public double[] getAttributes();

	public default String toPrettyString() {
		StringBuilder sb = new StringBuilder();
		double[] attributes = this.getAttributes();
		for (int i = 0; i < attributes.length; i++) {
			sb.append(attributes[i] + " ");
		}
		sb.append(this.getCluster() + 1);
		return sb.toString();
	}
}
