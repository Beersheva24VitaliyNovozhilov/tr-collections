package io.p4r53c.telran.util;

import org.junit.jupiter.api.BeforeEach;

public class LinkedListTest extends ListTest {

    @Override
    @BeforeEach
    void setUp() {
        collection = new LinkedList<>();
        super.setUp();
    }
}
