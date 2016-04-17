package program3.data;

import java.util.ArrayList;
import java.util.List;

import program2.data.Record;

/**
 * This class is used to adapt program2.data.Record class to IClusteringRecord.
 * This way the program2.data.RecordReader class may be used for program3 clustering data
 * @author cade
 *
 */
public class ClassificationRecordAdapter implements IClusteringRecord {

	private Record classificationRecord;

	public ClassificationRecordAdapter(Record classificationRecord) {
		this.classificationRecord = classificationRecord;
		this.classificationRecord.setOutput(new double[] { -1 });
	}

	public ClassificationRecordAdapter(int size) {
		this.classificationRecord = new Record(new double[size], new double[1]);
		this.classificationRecord.getOutputs()[0] = -1;
	}

	public ClassificationRecordAdapter(double[] attributes) {
		this.classificationRecord = new Record(attributes, new double[1]);
		this.classificationRecord.getOutputs()[0] = -1;
	}

	@Override
	public int getCluster() {
		return (int) Math.round(this.classificationRecord.getOutputs()[0]);
	}

	@Override
	public void setCluster(int cluster) {
		this.classificationRecord.getOutputs()[0] = cluster;

	}

	//adapts a list of classification records from program2 into clustering records for program3
	public static List<IClusteringRecord> adaptList(List<Record> classificationRecords) {
		List<IClusteringRecord> clusteringRecords = new ArrayList<>();

		for (Record classificationRecord : classificationRecords) {
			clusteringRecords.add(new ClassificationRecordAdapter(classificationRecord));
		}

		return clusteringRecords;
	}

	@Override
	public int getSize() {
		return this.classificationRecord.getInputs().length;
	}

	@Override
	public double[] getAttributes() {
		return this.classificationRecord.getInputs();
	}

	@Override
	public String toString() {
		return this.toPrettyString();
	}

}
