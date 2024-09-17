package io.p4r53c.telran.util;

import org.junit.jupiter.api.BeforeEach;

public class TreeSetStandardTest extends SortedSetTest {

    @BeforeEach
    @Override
    void setUp() {
        collection = new TreeSetStandard<>();
        super.setUp();
    }
}
