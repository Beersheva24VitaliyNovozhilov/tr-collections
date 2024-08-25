package io.p4r53c.telran.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

public abstract class ListTest extends CollectionTest {

    List<Integer> list;

    @Override
    void setUp() {
        super.setUp();
        list = (List<Integer>) collection;
    }

    @Override
    @Test
    void testAdd() {
        list.add(8, 2000);
        Integer[] expected = { 3, -10, 20, 1, 10, 8, 100, 17, 2000 };
        assertArrayEquals(expected, fromList(list));

        list.add(0, 200);
        expected = new Integer[] { 200, 3, -10, 20, 1, 10, 8, 100, 17, 2000 };
        assertArrayEquals(expected, fromList(list));

    }

    @Override
    @Test
    void testRemove() {
        list.remove(3);
        assertEquals(7, list.size());
        Integer[] expected = { 3, -10, 20, 10, 8, 100, 17 };
        assertArrayEquals(expected, fromList(list));

        list.remove(5);
        assertEquals(6, list.size());
        Integer[] nextExpected = { 3, -10, 20, 10, 8, 17 };
        assertArrayEquals(nextExpected, fromList(list));

        assertThrowsExactly(IndexOutOfBoundsException.class, () -> list.remove(100));

    }

    @Test
    public void testGet() {
        assertEquals(3, list.get(0));
        assertEquals(-10, list.get(1));
        assertEquals(10, list.get(4));
        assertEquals(17, list.get(7));
        assertThrowsExactly(IndexOutOfBoundsException.class, () -> list.get(-1));
    }

    @Test
    public void testIndexOf() {
        assertEquals(0, list.indexOf(3));
        assertEquals(4, list.indexOf(10));
        assertEquals(7, list.indexOf(17));
        assertEquals(6, list.indexOf(100));
        assertEquals(-1, list.indexOf(1000));
    }

    @Test
    public void testLastIndexOf() {
        list.add(8, 17);

        Integer[] expected = { 3, -10, 20, 1, 10, 8, 100, 17, 17 };

        assertArrayEquals(expected, fromList(list));

        assertEquals(8, list.lastIndexOf(17));
        assertEquals(-1, list.lastIndexOf(1000));
    }

    @Test
    void testIterator() {
        Integer[] expected = { 3, -10, 20, 1, 10, 8, 100, 17, 200, 17 };

        int i = 0;
        for (Integer e : list) {
            assertEquals(expected[i++], e);
        }
    }

    private Integer[] fromList(List<Integer> list) {
        return list.stream().toArray(Integer[]::new);
    }
}
