package org.treesitter.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.treesitter.TSLanguage;
import org.treesitter.TreeSitterScala;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CorpusTestTest {

    private static List<TestExample> examples;

    private CorpusTest loadTest(String filename) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename)) {
            return new CorpusTest(inputStream, filename);
        }
    }

    @Test
    void runTest() throws IOException {
        CorpusTest corpusTest = loadTest("test/corpus/definitions.txt");
        TSLanguage lang = new TreeSitterScala();
        corpusTest.runTest(lang);
    }

    @Test
    void runAllTestInFolder() throws IOException {
        TSLanguage lang = new TreeSitterScala();
        CorpusTest.runAllTestsInFolder("src/test/resources/test/corpus", lang);
    }

}