package io.p4r53c.telran.util;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class TreeSetTest extends SortedSetTest {

    TreeSet<Integer> treeSet;

    @Override
    @BeforeEach
    void setUp() {
        collection = new TreeSet<>();
        super.setUp();
        treeSet = (TreeSet<Integer>) collection;
    }

    @Override
    protected void runTest(Integer[] expected) {
        Integer[] expectedSorted = Arrays.copyOf(expected, expected.length);
        Arrays.sort(expectedSorted);
        Integer[] actual = collection.stream().toArray(Integer[]::new);
        // Arrays.sort(actual);
        assertArrayEquals(expectedSorted, actual);
        assertEquals(expected.length, collection.size());
    }

    @Test
    void testDisplayTreeRotated() {
        treeSet.setSymbolsPerLevel(5);
        assertDoesNotThrow(() -> treeSet.displayTreeRotated());
    }

    @Test
    void testDisplayTreeParentChildren() {
        treeSet.setSymbolsPerLevel(5);
        assertDoesNotThrow(() -> treeSet.displayTreeParentChildren());
    }

    @Test
    void testWidth() {
        assertEquals(4, treeSet.width());
    }

    @Test
    void testHeight() {
        assertEquals(4, treeSet.height());
    }

    @Test
    void testInversion() {
        Integer[] expected = { 100, 20, 17, 10, 8, 3, 1, -10 };
        treeSet.inversion();
        Integer[] actual = treeSet.stream().toArray(Integer[]::new);

        assertArrayEquals(expected, actual);
        assertTrue(treeSet.contains(100));
    }

    @Test
    void testExtremeCases() {
        TreeSet<Integer> localTreeSet = new TreeSet<>();
        IntStream.rangeClosed(1, 7).boxed().forEach(localTreeSet::add);
        assertEquals(7, localTreeSet.height());
        assertEquals(1, localTreeSet.width());
        localTreeSet.clear();

        Integer[] balancedArray = { 4, 2, 1, 3, 6, 5, 7 };
        Arrays.stream(balancedArray).forEach(localTreeSet::add);

        assertEquals(3, localTreeSet.height());
        assertEquals(4, localTreeSet.width());
    }

    @Test
    void testBalancedTree() {
        TreeSet<Integer> localTreeSet = new TreeSet<>();
        Integer[] array = getBigArrayCW();
        Arrays.stream(array).forEach(localTreeSet::add);

        localTreeSet.balance();

        assertEquals(20, localTreeSet.height());
        assertEquals((N_ELEMENTS + 1) / 2, localTreeSet.width());
    }

    /**
     * Tests that the height of the tree set after balancing is log2(N_ELEMENTS)
     * and the width of the tree set after balancing is (N_ELEMENTS + 1) / 2.
     * <p>
     * This test is expected to take no longer than 300 milliseconds.
     */
    @Test
    @Timeout(value = 300, unit = TimeUnit.MILLISECONDS)
    void testBalancedTreeHW() {
        TreeSet<Integer> localTreeSet = new TreeSet<>();
        Integer[] array = getBigArrayHW();

        // Transform the array before adding to the TreeSet
        Integer[] transformedArray = transform(array);
        Arrays.stream(transformedArray).forEach(localTreeSet::add);

        assertEquals(20, localTreeSet.height());
        assertEquals((N_ELEMENTS + 1) / 2, localTreeSet.width());
    }

    /**
     * Transforms the given array in-place by rotating it to the right by the
     * given number of positions and then reversing the resulting array.
     * 
     * @param array the array to be transformed
     * @return the transformed array
     */
    private Integer[] transform(Integer[] array) {
        Integer[] transformedArray = new Integer[array.length];
        transform(transformedArray, array, 0, 0, array.length - 1);
        return transformedArray;
    }

    /**
     * Recursively transforms the given array in-place by rotating it to the right
     * by the given number of positions and then reversing the resulting array.
     * 
     * @param transformedArray the array to store the transformed elements
     * @param array            the array to be transformed
     * @param index            the current index in the transformedArray
     * @param left             the left boundary of the array segment to be
     *                         transformed
     * @param right            the right boundary of the array segment to be
     *                         transformed
     * @return the index after the transformation
     */
    private int transform(Integer[] transformedArray, Integer[] array,
            int index, int left, int right) {

        if (left <= right) {
            int root = (left + right) / 2;

            transformedArray[index] = array[root];

            index++;

            index = transform(transformedArray, array, index, left, root - 1);
            index = transform(transformedArray, array, index, root + 1, right);
        }

        return index;
    }
}
