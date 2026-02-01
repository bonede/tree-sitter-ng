
package org.treesitter;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import org.treesitter.tests.CorpusTest;
class TreeSitterTsxTest {
    @Test
    void corpusTest() throws IOException {
        CorpusTest.runAllTestsInDefaultFolderSecondaryLang(new TreeSitterTsx(), "typescript", "tsx");
    }
}
