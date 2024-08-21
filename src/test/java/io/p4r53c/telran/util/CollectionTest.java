package io.p4r53c.telran.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public abstract class CollectionTest {


    protected Collection<Integer> collection;

    Integer[] array = { 3, -10, 20, 1, 10, 8, 100, 17 };

    @BeforeEach
    void setUp() {
        Arrays.stream(array).forEach(collection::add);
    }

    // This two next test methods are overrided in the subclass and technically do
    // not invoke by JUnit Runner
    @Test
    void testAdd() {
        assertTrue(collection.add(200));
        assertTrue(collection.add(17));

        assertEquals(array.length + 2, collection.size());
    }

    @Test()
    void testRemove() {
        assertTrue(collection.remove(3));
        assertEquals(array.length - 1, collection.size());
        assertFalse(collection.remove(1000));
    }

    @Test
    void testSize() {
        assertEquals(array.length, collection.size());
    }

    @Test
    void testIsEmpty() {
        assertFalse(collection.isEmpty());
    }

    @Test
    void testContains() {
        assertTrue(collection.contains(17));
        assertFalse(collection.contains(-100));
    }

    @Test
    void testParallelStreamSortWithForEachOrdered() {
        for (int i = 0; i < 200; i++) {
            collection.add(i);
        }

        List<Integer> result = new ArrayList<>();

        collection.parallelStream()
                .sorted()
                .forEachOrdered(result::add);

        assertTrue(isSorted(result), "Parallel stream with forEachOrdered result should be sorted");
    }

    @Test
    void testParallelStreamSort() {
        for (int i = 0; i < 200; i++) {
            collection.add(i);
        }

        List<Integer> result = new ArrayList<>();

        collection.parallelStream()
                .sorted()
                .forEach(result::add);

        assertFalse(isSorted(result), "Parallel stream result should not be sorted");
    }

    @Test
    void testSequentialStreamSort() {

        for (int i = 0; i < 200; i++) {
            collection.add(i);
        }

        List<Integer> result = new ArrayList<>();

        collection.stream()
                .sorted()
                .forEach(result::add);

        assertTrue(isSorted(result), "Sequential stream result should be sorted");
    }

    private boolean isSorted(List<Integer> list) {
        int i = 1;
        boolean sorted = true;

        while (i < list.size() && sorted) {
            sorted = list.get(i) >= list.get(i - 1);
            i++;
        }

        return sorted;
    }

}
