package maniccam.data;

public interface DataConverter<T> {

	T convert(String label, int column);

	String convert(T value, int column);

	default int getRange(Double value, double[] rangeMarkers) {
		int closestIndex = 0;
		double closestDistance = 2.0;
		for (int i = 0; i < rangeMarkers.length; i++) {
			double distance = Math.abs(value - rangeMarkers[i]);
			if (distance < closestDistance) {
				closestDistance = distance;
				closestIndex = i;
			}
		}
		return closestIndex + 1;
	}
}
