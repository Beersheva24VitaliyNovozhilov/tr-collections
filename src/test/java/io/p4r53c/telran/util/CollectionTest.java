package io.p4r53c.telran.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import java.util.concurrent.TimeUnit;

import java.util.concurrent.CopyOnWriteArrayList;

import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public abstract class CollectionTest {

    protected Collection<Integer> collection;

    Integer[] array = { 3, -10, 20, 1, 10, 8, 100, 17 };

    private static final int N_ELEMENTS = 2_000_000;

    private Random random = new Random();

    @BeforeEach
    void setUp() {
        Arrays.stream(array).forEach(collection::add);
    }

    @Test
    void testNonExistingAdd() {
        assertTrue(collection.add(200));

        runTest(new Integer[] { 3, -10, 20, 1, 10, 8, 100, 17, 200 });
    }

    @Test
    void testExistingAdd() {
        assertTrue(collection.add(17));
        runTest(new Integer[] { 3, -10, 20, 1, 10, 8, 100, 17, 17 });
    }

    @Test()
    void testRemove() {
        assertTrue(collection.remove(3));
        assertEquals(array.length - 1, collection.size());
        assertFalse(collection.remove(1000));
    }

    @Test
    void testRemoveIf() {
        assertTrue(collection.removeIf(n -> n % 2 == 0));
        assertFalse(collection.removeIf(n -> n % 2 == 0));
        assertTrue(collection.stream().allMatch(n -> n % 2 != 0));
    }

    @Test
    void testClear() {
        collection.clear();
        assertTrue(collection.isEmpty());

    }

    @Test
    void testIteratorRemove() {
        Iterator<Integer> iterator = collection.iterator();

        assertThrowsExactly(IllegalStateException.class, () -> iterator.remove());

        Integer next = iterator.next();
        iterator.remove();

        assertFalse(collection.contains(next));
        assertThrowsExactly(IllegalStateException.class, () -> iterator.remove());
    }

    // GitHub Actions workers can be slow and the test can fail during the build in
    // pipeline.
    // So I set a much larger timeout. This shows that such tests are also
    // not a very good solution.
    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    void testPerformance() {
        collection.clear();

        IntStream.range(0, N_ELEMENTS).forEach(i -> collection.add(random.nextInt()));
        collection.removeIf(n -> n % 2 == 0);
        assertTrue(collection.stream().allMatch(n -> n % 2 != 0));

        collection.clear();
        assertTrue(collection.isEmpty());
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
        collection.clear();

        for (int i = 0; i < 200; i++) {
            collection.add(i);
        }

        List<Integer> result = new ArrayList<>();

        collection.parallelStream()
                .sorted()
                .forEachOrdered(result::add);

        assertTrue(isSorted(result), "Parallel stream with forEachOrdered result should be sorted");
    }

    /**
     * Tests if parallel stream sorting produces an unsorted result.
     * 
     * Undefined behavior. Randomly throws ArrayIndexOutOfBoundsException with
     * {@link io.p4r53c.telran.util.ArrayList}
     * 
     * I am not sure what is actually wrong, but probably it is synchronization
     * issue.
     * 
     * As a first approximation I used A thread-safe variant of
     * {@link java.util.ArrayList} - {@link CopyOnWriteArrayList}.
     */
    @RepeatedTest(5)
    void testParallelStreamSort() {
        collection.clear();

        for (int i = 0; i < 200; i++) {
            collection.add(i);
        }

        CopyOnWriteArrayList<Integer> result = new CopyOnWriteArrayList<Integer>();

        collection.parallelStream()
                .sorted()
                .forEach(result::add);

        assertFalse(isSorted(result), "Parallel stream result should not be sorted");
    }

    @Test
    void testSequentialStreamSort() {
        collection.clear();

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

    private boolean isSorted(CopyOnWriteArrayList<Integer> result) {
        int i = 1;
        boolean sorted = true;

        while (i < result.size() && sorted) {
            sorted = result.get(i) >= result.get(i - 1);
            i++;
        }

        return sorted;
    }

    protected void runTest(Integer[] expected) {
        assertArrayEquals(expected, collection.stream().toArray(Integer[]::new));
        assertEquals(expected.length, collection.size());
    }
}
