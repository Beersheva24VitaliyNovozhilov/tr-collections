package io.p4r53c.telran.util;

import org.junit.jupiter.api.BeforeEach;

public class ArrayListTest extends ListTest {

    @Override
    @BeforeEach
    void setUp() {
        collection = new ArrayList<>(3);
        super.setUp();
    }
}
