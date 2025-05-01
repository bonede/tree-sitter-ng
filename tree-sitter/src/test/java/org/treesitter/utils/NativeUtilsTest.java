package org.treesitter.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NativeUtilsTest {

    @Test
    void loadLib() {
        NativeUtils.loadLib("lib/tree-sitter");
    }
}