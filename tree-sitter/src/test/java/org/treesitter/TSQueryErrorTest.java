package org.treesitter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TSQueryErrorTest {
    private TSLanguage json;
    private TSParser parser;

    @BeforeEach
    void beforeEach() {
        parser = new TSParser();
        json = new TreeSitterJson();
        parser.setLanguage(json);
    }

    @Test
    void testInvalidQueryThrowsException() {
        // This query is syntactically invalid (missing parentheses or bad structure)
        // ts_query_new should return NULL, which TSQuery constructor should check.
        assertThrows(TSQueryException.class, () -> new TSQuery(json, "invalid query"));
    }

    @Test
    void testQueryWithSyntaxError() {
        assertThrows(TSQueryException.class, () -> new TSQuery(json, "((document) @d (#eq? @d))"));
    }
}
