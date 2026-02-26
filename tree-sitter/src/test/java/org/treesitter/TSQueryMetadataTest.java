package org.treesitter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TSQueryMetadataTest {
    public static final String JSON_SRC = "[1, 2]";
    private TSTree tree;
    private TSLanguage json;
    private TSParser parser;
    private TSQueryCursor cursor;
    private TSNode rootNode;

    @BeforeEach
    void beforeEach() {
        parser = new TSParser();
        json = new TreeSitterJson();
        parser.setLanguage(json);
        tree = parser.parseString(null, JSON_SRC);
        rootNode = tree.getRootNode();
        cursor = new TSQueryCursor();
    }

    @Test
    void testSetMetadataDirective() {
        // ((number) @n (#set! role "foo"))
        TSQuery query = new TSQuery(json, "((number) @n (#set! role \"foo\"))");
        cursor.exec(query, rootNode);
        TSQueryMatch match = new TSQueryMatch();
        
        int count = 0;
        while (cursor.nextMatch(match)) {
            count++;
            Map<String, String> metadata = match.getMetadata();
            assertEquals("foo", metadata.get("role"), "Metadata 'role' should be 'foo'");
        }
        assertEquals(2, count, "Should have matched two numbers");
    }

    @Test
    void testIsPredicateSuccess() {
        // ((number) @n (#set! role "foo") (#is? role "foo"))
        TSQuery query = new TSQuery(json, "((number) @n (#set! role \"foo\") (#is? role \"foo\"))");
        cursor.exec(query, rootNode);
        TSQueryMatch match = new TSQueryMatch();

        int count = 0;
        while (cursor.nextMatch(match)) {
            count++;
            assertEquals("foo", match.getMetadata().get("role"));
        }
        assertEquals(2, count, "Both matches should satisfy #is? role 'foo'");
    }

    @Test
    void testIsPredicateFailure() {
        // ((number) @n (#set! role "foo") (#is? role "bar"))
        TSQuery query = new TSQuery(json, "((number) @n (#set! role \"foo\") (#is? role \"bar\"))");
        cursor.exec(query, rootNode);
        TSQueryMatch match = new TSQueryMatch();

        int count = 0;
        while (cursor.nextMatch(match)) {
            count++;
        }
        assertEquals(0, count, "No matches should be returned because role is 'foo', not 'bar'");
    }

    @Test
    void testMetadataWithNextCapture() {
        // ((number) @n (#set! role "foo") (#is? role "foo"))
        TSQuery query = new TSQuery(json, "((number) @n (#set! role \"foo\") (#is? role \"foo\"))");
        cursor.exec(query, rootNode);
        TSQueryMatch match = new TSQueryMatch();

        int count = 0;
        while (cursor.nextCapture(match)) {
            count++;
            assertEquals("foo", match.getMetadata().get("role"), "Metadata should be preserved in nextCapture");
        }
        assertEquals(2, count, "Both captures should satisfy #is? role 'foo'");
    }
}
