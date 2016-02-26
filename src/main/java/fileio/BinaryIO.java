package fileio;

import org.javatuples.Pair;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import data.attribute.AttributeInfo;
import data.attribute.NominalAttributeInfo;
import data.feature.DefaultFeautureStrategy;
import data.record.Record;

public class BinaryIO extends HomogenousIO {

	public BinaryIO(Pair<String, String> binaryValues, int numAttributes, int tr) throws FileNotFoundException {
		super(null, new DefaultFeautureStrategy());
		List<AttributeInfo<?>> attInf = new LinkedList<>();
		List<Object> values = Arrays.asList(binaryValues.getValue0(), binaryValues.getValue1());
		for (int i = 0; i < numAttributes; i++) {
			attInf.add(new NominalAttributeInfo(i, values, this.featureStrategy, false));
		}
		this.attributeInfos = attInf;
	}

	public Pair<List<Record>, List<AttributeInfo<?>>> getData(String fileName) throws FileNotFoundException {
		List<Record> records = this.getTrainingData(fileName);
		return new Pair<List<Record>, List<AttributeInfo<?>>>(records, this.attributeInfos);
	}

}
