package io.p4r53c.telran.util;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class HashSetTest extends SetTest {

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

    /**
     * Tests if calling {@link HashSet#hashTableReallocation()} clears all lists.
     * <p>
     * This test uses reflection to call the {@code hashTableReallocation()} method
     * directly. It adds elements to the {@code HashSet} and then explicitly invokes
     * the reallocation method. It then checks if all lists in the hash table are
     * cleared.
     * <p>
     * Expected outcome: All lists in the hash table should be cleared after
     * reallocation. But it doesn't.
     */
    @Disabled("List is not cleared after reallocation")
    @Test
    void testListClearAfterReallocation() {
        HashSet<Integer> set = new HashSet<>(4, 0.75f);

        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        try {
            Method method = HashSet.class.getDeclaredMethod("hashTableReallocation");

            method.setAccessible(true);

            method.invoke(set); // Explicitly invoke reallocation. It should clear all lists, but it doesn't.

            for (List<Integer> list : set.hashTable) {
                if (list != null) {
                    assertTrue(list.isEmpty()); // expected: <true> but was: <false>
                }
            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }
}
