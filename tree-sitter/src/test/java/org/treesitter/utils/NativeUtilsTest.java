package org.treesitter.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NativeUtilsTest {

    @Test
    void loadLib() {
        for (int i = 1; i <= 10; i++) {
            Thread thread = new Thread(() -> {
                NativeUtils.loadLib("lib/tree-sitter");
            });
            thread.start();
        }
    }
}