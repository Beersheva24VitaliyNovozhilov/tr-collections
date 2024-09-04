package io.p4r53c.telran.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.p4r53c.telran.util.Map.Entry;

public abstract class AbstractMapTest {

	protected Map<Integer, Integer> map;
	protected Integer[] keySet = { -1, 4, 7, 3 };

	@BeforeEach
	void setUp() {

		for (Integer key : keySet) {
			map.put(key, key * key);
		}
	}

	@Test
	void getTest() {
		assertEquals(1, map.get(-1));
		assertNull(map.get(1));
	}

	@Test
	void getOrDefaultTest() {
		assertEquals(1, map.getOrDefault(-1, 0));
		assertEquals(0, map.getOrDefault(1, 0));
	}

	@Test
	void testPutIfAbsent() {
		int newValue = 256;
		assertNull(map.putIfAbsent(1, 1));
		assertEquals(1, map.get(1));
		assertEquals(1, map.putIfAbsent(1, newValue));
		assertEquals(1, map.get(1));
	}

	@Test
	public void testPutNewPair() {
		assertNull(map.put(1, 256));
		assertEquals(256, map.get(1));
	}

	@Test
	void testRemove() {
		assertNull(map.remove(1));
		assertEquals(1, map.remove(-1));
		assertNull(map.get(-1));
	}

	@Test
	public void testContainsKey() {
		assertFalse(map.containsKey(256));
		assertTrue(map.containsKey(7));
		assertFalse(map.containsKey(null));
	}

	@Test
	public void testContainsValue() {
		map.put(1, 60);
		map.put(2, 256);
		assertTrue(map.containsValue(60));
		assertTrue(map.containsValue(256));
	}

	@SuppressWarnings("unchecked")
	@Test
	void testEntrySet() {
		Entry<Integer, Integer>[] expected = new Entry[4];

		expected[0] = new Entry<>(-1, 1);
		expected[1] = new Entry<>(4, 16);
		expected[2] = new Entry<>(7, 49);
		expected[3] = new Entry<>(3, 9);

		Collection<Entry<Integer, Integer>> entries = map.entrySet();

		Entry<Integer, Integer>[] actual = new Entry[entries.size()];
		actual = fromCollection(entries, actual);

		runTest(expected, actual);
	}

	@Test
	void testKeySet() {
		Integer[] expected = { -1, 4, 7, 3 };
		Collection<Integer> keys = map.keySet();

		Integer[] actual = new Integer[keys.size()];
		actual = fromCollection(keys, actual);

		runTest(expected, actual);
	}

	@Test
	void testValues() {
		Integer[] expected = { 1, 16, 49, 9 };
		Collection<Integer> values = map.values();

		Integer[] actual = new Integer[values.size()];
		actual = fromCollection(values, actual);

		runTest(expected, actual);
	}

	abstract <T> void runTest(T[] expected, T[] actual);

	abstract <T> T[] fromCollection(Collection<T> collection, T[] array);
}
