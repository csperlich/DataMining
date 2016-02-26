package data.feature;

import java.util.function.Predicate;

public class Predicates {
	public static <T> Predicate<T> is(T val) {
		System.out.println(val);
		return p -> p.equals(val);
	}
}
