package program1.data.feature;

import java.util.function.Predicate;

public class Predicates {
	public static <T> Predicate<T> is(T val) {
		return p -> p.equals(val);
	}
}
