package org.treesitter.json;

import org.junit.jupiter.api.Test;
import org.treesitter.tests.CorpusTest;
import org.treesitter.TreeSitterJson;

import java.io.IOException;

class TreeSitterJsonTest {
    @Test
    void corpusTest() throws IOException {
        CorpusTest.runAllTestsInDefaultFolder(new TreeSitterJson(), "json");
    }
}
