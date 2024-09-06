package io.p4r53c.telran.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

abstract class SortedSetTest extends SetTest {

    SortedSet<Integer> sortedSet;

    @Override
    void setUp() {
        // Previously, there was no entry point to this code. This class should be made
        // abstract, TreeSetTest should be inherited from this class and entry point
        // should be from there.
        super.setUp();
        sortedSet = (SortedSet<Integer>) collection;
    }

    @Test
    void testFirst() {
        assertEquals(-10, sortedSet.first());
    }

    @Test
    void testLast() {
        assertEquals(100, sortedSet.last());
    }

    @Test
    void testFloor() {
        assertEquals(3, sortedSet.floor(3));
        assertEquals(3, sortedSet.floor(4));
        assertNull(sortedSet.floor(-256));
        assertEquals(8, sortedSet.floor(9));
    }

    @Test
    void testCeiling() {
        assertEquals(3, sortedSet.ceiling(3));
        assertEquals(8, sortedSet.ceiling(4));
        assertNull(sortedSet.ceiling(256));
        assertEquals(-10, sortedSet.ceiling(-11));
    }

    @Test
    void testSubSet() {
        Integer[] expected = { 10, 17 };
        Integer[] actual = sortedSet.subSet(10, 20).stream().toArray(Integer[]::new);
        assertArrayEquals(expected, actual);
    }

}
