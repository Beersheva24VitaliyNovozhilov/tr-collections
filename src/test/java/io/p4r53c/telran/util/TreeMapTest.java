package io.p4r53c.telran.util;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

public class TreeMapTest extends AbstractMapTest {

    TreeMap<Integer, Integer> treeMap;

    @Override
    @BeforeEach
    void setUp() {
        map = new TreeMap<>();
        super.setUp();
        treeMap = (TreeMap<Integer, Integer>) map;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> void runTest(T[] expected, T[] actual) {
        Arrays.sort(expected, (o1, o2) -> ((Comparable<T>) o1).compareTo(o2));

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
