package maniccam.data;

public interface DataConverter<T> {

	T convert(String label, int column);

	String convert(T value, int column);
}
