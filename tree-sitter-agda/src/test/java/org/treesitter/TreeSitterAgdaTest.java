
package org.treesitter;

import org.junit.jupiter.api.Test;
import org.treesitter.tests.CorpusTest;

import java.io.IOException;

class TreeSitterAgdaTest {
    @Test
    void corpusTest() throws IOException {
        CorpusTest.runAllTestsInDefaultFolder(new TreeSitterAgda(), "agda");
    }
}
