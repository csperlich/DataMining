package maniccam.bayesianclassifier;

public interface DataConverter {

	int convert(String label, int column);

	String convert(int value);
}
