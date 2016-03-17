package maniccam.bayesianclassifier.data;

public interface DataConverter {

	int convert(String label, int column);

	String convert(int value);
}
