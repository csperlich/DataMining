package data;

import java.util.function.Predicate;

public class Predicates {
	public static <T> Predicate<T> is(T val) {
		return p -> p.equals(val);
	}
}
