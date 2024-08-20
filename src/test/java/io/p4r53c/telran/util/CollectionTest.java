package io.p4r53c.telran.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;

public abstract class CollectionTest {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(Collection.class);

    protected Collection<Integer> collection;

    Integer[] array = { 3, -10, 20, 1, 10, 8, 100, 17 };

    @BeforeEach
    void setUp() {
        Arrays.stream(array).forEach(collection::add);
    }

    @Test
    void testAdd() {
        assertTrue(collection.add(200));
        assertTrue(collection.add(17));

        assertEquals(array.length + 2, collection.size());
    }

    @Test
    void testRemove() {
        assertTrue(collection.remove(100));
        assertEquals(7, collection.size());

        assertFalse(collection.remove(-100));
        assertEquals(7, collection.size());
    }

    @Test
    void testSize() {
        assertEquals(array.length, collection.size());
    }

    void testIsEmpty() {
        assertFalse(collection.isEmpty());
    }

    @Test
    void testContains() {
        assertTrue(collection.contains(17));
        assertFalse(collection.contains(-100));
    }

    @Test
    void testIterator() {
        Integer[] expected = { 3, -10, 20, 1, 10, 8, 100, 17, 200, 17 };

        int i = 0;
        for (Integer e : collection) {
            assertEquals(expected[i++], e);
        }
    }

    // Test performance of stream and parallelStream
    @Test
    void testStreamsPerformance() {

        for (int i = 0; i < 100; i++) {
            collection.add(i);
        }

        long sequentialTime = measureTime(() -> collection.stream().forEach(this::simulateWork));
        long parallelTime = measureTime(() -> collection.parallelStream().forEach(this::simulateWork));

        logger.info("Parallel time: [{}], Sequential time: [{}]", parallelTime, sequentialTime);

        assertTrue(parallelTime < sequentialTime);
    }

    private long measureTime(Runnable task) {
        long start = System.nanoTime();
        task.run();
        return System.nanoTime() - start;
    }

    private void simulateWork(Integer n) {
        long startTime = System.nanoTime();
        while (System.nanoTime() - startTime < 10_000_000) {
            // do nothing, think better than Thread.sleep()
        }
    }
}
