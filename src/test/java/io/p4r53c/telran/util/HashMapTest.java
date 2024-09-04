package io.p4r53c.telran.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;

public class HashMapTest extends AbstractMapTest {

	@Override
	@BeforeEach
	void setUp() {
		map = new HashMap<>();
		super.setUp();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T> void runTest(T[] expected, T[] actual) {
		Arrays.sort(expected, (o1, o2) -> ((Comparable<T>) o1).compareTo(o2));
		Arrays.sort(actual, (o1, o2) -> ((Comparable<T>) o1).compareTo(o2));

		for (T i : expected) {
			assertEquals(i, actual[Arrays.asList(expected).indexOf(i)]);
		}
	}

	@Override
	protected <T> T[] fromCollection(Collection<T> collection, T[] array) {
		int i = 0;

		for (T o : collection) {
			array[i++] = o;
		}

		return array;
	}
}
