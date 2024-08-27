package io.p4r53c.telran.util;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

public class HashSetTest extends SetTest {

    @Override
    @BeforeEach
    void setUp() {
        collection = new HashSet<>();
        super.setUp();
    }

    @Override
    protected void runTest(Integer[] expected) {
        Integer[] expectedSorted = Arrays.copyOf(expected, expected.length);
        Arrays.sort(expectedSorted);

        Integer[] actual = collection.stream().toArray(Integer[]::new);
        Arrays.sort(actual);

        assertArrayEquals(expectedSorted, actual);
        assertEquals(expected.length, collection.size());
    }

}
