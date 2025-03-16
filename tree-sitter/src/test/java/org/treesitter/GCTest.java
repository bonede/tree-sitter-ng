package org.treesitter;

import org.junit.jupiter.api.Test;

public class GCTest {
    @Test
    void gc(){
        // Force system to gc to test invalid pointers;
        System.gc();
    }
}
