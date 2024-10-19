package org.treesitter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TSQueryTest {
    public static final String JSON_SRC = "[1, null]";
    private TSTree tree;
    private TSLanguage json;
    private TSParser parser;
    private TSQuery query;

    @BeforeEach
    void beforeEach() {
        parser = new TSParser();
        json = new TreeSitterJson();
        parser.setLanguage(json);
        tree = parser.parseString(null, JSON_SRC);
        query = new TSQuery(json, "((document) @root (#eq? @root \"foo\"))");
    }
    @Test
    void newQuery(){
        assertThrows(TSQueryException.class, () -> new TSQuery(json, "invalid query"));
        assertDoesNotThrow(() -> new TSQuery(json, "(document)"));
    }


    @Test
    void getPatternCount() {
        assertEquals(1, query.getPatternCount());
    }

    @Test
    void getCaptureCount() {
        assertEquals(1, query.getCaptureCount());
    }

    @Test
    void getStringCount() {
        assertEquals(2, query.getStringCount());
    }

    @Test
    void getStartByteForPattern() {
        assertEquals(0, query.getStartByteForPattern(0));
    }

    @Test
    void getEndByteForPattern() {
        assertEquals(37, query.getEndByteForPattern(0));
    }


    @Test
    void getPredicateForPattern() {
        assertEquals(4, query.getPredicateForPattern(0).length);
    }

    @Test
    void isPatternRooted() {
        assertTrue(query.isPatternRooted(0));
    }

    @Test
    void isPatterNonLocal() {
        assertFalse(query.isPatterNonLocal(0));
    }

    @Test
    void isPatternGuaranteedAtStep() {
        assertFalse(query.isPatternGuaranteedAtStep(3));
    }


    @Test
    void getCaptureNameForId() {
        assertEquals("root", query.getCaptureNameForId(0));
    }

    @Test
    void getCaptureQuantifierForId() {
        assertEquals(3, query.getCaptureQuantifierForId(0, 0));
    }

    @Test
    void getStringValueForId() {
        assertEquals("foo", query.getStringValueForId(1));
    }

    @Test
    void disableCapture() {
        query.disableCapture("root");
    }

    @Test
    void disablePattern() {
        query.disablePattern(0);
    }

    @Test
    void setTimeoutMicros() {
        query.setTimeoutMicros(1000l);
    }

    @Test
    void getTimeoutMicros() {
        long timeout = 1000l;
        query.setTimeoutMicros(1000l);
        assertEquals(timeout, query.getTimeoutMicros());;
    }
}