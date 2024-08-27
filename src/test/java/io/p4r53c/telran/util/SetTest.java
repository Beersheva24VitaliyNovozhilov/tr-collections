package io.p4r53c.telran.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public abstract class SetTest extends CollectionTest {

    Set<Integer> set;

    @Override
    void setUp() {
        super.setUp();
        set = (Set<Integer>) collection;
    }

    @Override
    @Test
    void testExistingAdd() {
        assertFalse(set.add(17));
    }

    @Test
    void testGetPattern() {
        assertEquals(-10, set.get(-10));
        assertNull(set.get(100000));
    }
}
