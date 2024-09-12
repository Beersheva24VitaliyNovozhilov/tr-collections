package io.p4r53c.telran.util;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
