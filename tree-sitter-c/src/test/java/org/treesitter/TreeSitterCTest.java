package org.treesitter;

import org.junit.jupiter.api.Test;
import org.treesitter.tests.CorpusTest;

import java.io.IOException;

class TreeSitterCTest {
    @Test
    void corpusTest() throws IOException {
        CorpusTest.runAllTestsInDefaultFolder(new TreeSitterC(), "c");
    }
}
