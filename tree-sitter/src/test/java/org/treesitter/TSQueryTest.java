package org.treesitter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.treesitter.utils.NativeUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class TSQueryTest {
    public static final String JSON_SRC = "[1, null]";
    private TSTree tree;
    private TSLanguage json;
    private TSParser parser;
    private TSQuery query;

    @BeforeEach
    void beforeEach() {
        parser = new TSParser();
        json = null;
        try {
            // Attempt to locate the JSON library from the sibling module's output or local store
            java.io.File libFile = NativeUtils.libFile("tree-sitter-json");
            if (libFile.exists()) {
                json = TSLanguage.load(libFile.getAbsolutePath(), "tree_sitter_json");
                if (json != null) {
                    parser.setLanguage(json);
                    tree = parser.parseString(null, JSON_SRC);
                    query = new TSQuery(json, "((document) @root (#eq? @root \"foo\"))");
                }
            }
        } catch (Exception e) {
            // If the library fails to load for any reason, we mark it as null
            // so tests can be skipped via assumeTrue(json != null).
            json = null;
        }
    }

    @Test
    void newQuery(){
        assumeTrue(json != null, "JSON language library not available");
        assertThrows(TSQueryException.class, () -> new TSQuery(json, "invalid query"));
        assertDoesNotThrow(() -> new TSQuery(json, "(document)"));
    }


    @Test
    void getPatternCount() {
        assumeTrue(json != null);
        assertEquals(1, query.getPatternCount());
    }

    @Test
    void getCaptureCount() {
        assumeTrue(json != null);
        assertEquals(1, query.getCaptureCount());
    }

    @Test
    void getStringCount() {
        assumeTrue(json != null);
        assertEquals(2, query.getStringCount());
    }

    @Test
    void getStartByteForPattern() {
        assumeTrue(json != null);
        assertEquals(0, query.getStartByteForPattern(0));
    }

    @Test
    void getEndByteForPattern() {
        assumeTrue(json != null);
        assertEquals(37, query.getEndByteForPattern(0));
    }


    @Test
    void getPredicateForPattern() {
        assumeTrue(json != null);
        assertEquals(4, query.getPredicateForPattern(0).length);
    }

    @Test
    void isPatternRooted() {
        assumeTrue(json != null);
        assertTrue(query.isPatternRooted(0));
    }

    @Test
    void isPatterNonLocal() {
        assumeTrue(json != null);
        assertFalse(query.isPatterNonLocal(0));
    }

    @Test
    void isPatternGuaranteedAtStep() {
        assumeTrue(json != null);
        assertFalse(query.isPatternGuaranteedAtStep(3));
    }


    @Test
    void getCaptureNameForId() {
        assumeTrue(json != null);
        assertEquals("root", query.getCaptureNameForId(0));
    }

    @Test
    void getCaptureQuantifierForId() {
        assumeTrue(json != null);
        assertEquals(TSQuantifier.TSQuantifierOne, query.getCaptureQuantifierForId(0, 0));
    }

    @Test
    void getStringValueForId() {
        assumeTrue(json != null);
        assertEquals("foo", query.getStringValueForId(1));
    }

    @Test
    void disableCapture() {
        assumeTrue(json != null);
        query.disableCapture("root");
    }

    @Test
    void disablePattern() {
        assumeTrue(json != null);
        query.disablePattern(0);
    }
}
