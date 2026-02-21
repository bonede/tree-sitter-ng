package org.treesitter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.treesitter.utils.NativeUtils;

import java.util.Iterator;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class TSQueryCursorTest {
    public static final String JSON_SRC = "[1, null]";
    private TSTree tree;
    private TSLanguage json;
    private TSParser parser;
    private TSQuery query;
    private TSQueryCursor cursor;
    private TSNode rootNode;

    @BeforeEach
    void beforeEach() {
        parser = new TSParser();
        json = null;
        try {
            // Self-contained check for language availability
            java.io.File libFile = NativeUtils.libFile("tree-sitter-json");
            if (libFile.exists()) {
                json = TSLanguage.load(libFile.getAbsolutePath(), "tree_sitter_json");
                if (json != null) {
                    parser.setLanguage(json);
                    tree = parser.parseString(null, JSON_SRC);
                    query = new TSQuery(json, "((document) @root (#eq? @root \"foo\"))");
                    rootNode = tree.getRootNode();
                }
            }
        } catch (Exception e) {
            // If the library fails to load for any reason, we mark it as null
            // so tests can be skipped via assumeTrue(json != null).
            json = null;
        }
        cursor = new TSQueryCursor();
    }

    @Test
    void exec() {
        assumeTrue(json != null, "JSON language library not available");
        cursor.exec(query, rootNode);
    }

    @Test
    void execWithOptions(){
        assumeTrue(json != null);
        parser.reset();
        tree = parser.parseString(null, "{\"id\": 1}");
        query = new TSQuery(json, "(number)");
        cursor.execWithOptions(query, tree.getRootNode(), (state) -> {
            assertTrue(state.getCurrentByteOffset() >= 0);
            return false;
        });

        TSQueryMatch match = new TSQueryMatch();
        while (cursor.nextMatch(match)){
            assertTrue(match.getId() >= 0);
        }
    }

    @Test
    void didExceedMatchLimit() {
        assertFalse(cursor.didExceedMatchLimit());
    }

    @Test
    void getMatchLimit() {
        cursor.setMatchLimit(10);
        assertEquals(10, cursor.getMatchLimit());
    }

    @Test
    void setMatchLimit() {
        cursor.setMatchLimit(10);
    }

    @Test
    void setByteRange() {
        assertTrue(cursor.setByteRange(0, 5));
    }

    @Test
    void setPointRange() {
        assertTrue(cursor.setPointRange(new TSPoint(0, 0), new TSPoint(0, 10)));
    }

    @Test
    void nextMatch() {
        assumeTrue(json != null, "JSON language library not available");
        TSQueryMatch match = new TSQueryMatch();
        assertThrows(TSException.class, () -> cursor.nextMatch(match));
        cursor.exec(query, rootNode);
        assertTrue(cursor.nextMatch(match));
        assertEquals(0, match.getId());
        assertEquals(0, match.getPatternIndex());
    }

    @Test
    void removeMatch() {
        assumeTrue(json != null);
        cursor.exec(query, rootNode);
        TSQueryMatch match = new TSQueryMatch();
        cursor.removeMatch(match.getId());
    }

    @Test
    void nextCapture() {
        assumeTrue(json != null);
        cursor.exec(query, rootNode);
        TSQueryMatch match = new TSQueryMatch();
        assertTrue(cursor.nextCapture(match));
        assertEquals(0, match.getId());
        assertEquals(0, match.getPatternIndex());
        assertEquals(1, match.getCaptures().length);
        assertEquals(0, match.getCaptureIndex());
        assertTrue(TSNode.eq(rootNode, match.getCaptures()[0].getNode()));
    }

    @Test
    void getMatches(){
        assumeTrue(json != null);
        cursor.exec(query, rootNode);
        TSQueryCursor.TSMatchIterator matchIter = cursor.getMatches();
        while (matchIter.hasNext()){
            TSQueryMatch match = matchIter.next();
            assertEquals(0, match.getId());
            assertEquals(0, match.getPatternIndex());
        }
    }

    @Test
    void getCaptures(){
        assumeTrue(json != null);
        cursor.exec(query, rootNode);
        TSQueryCursor.TSMatchIterator captureIter = cursor.getCaptures();
        while (captureIter.hasNext()){
            TSQueryMatch match = captureIter.next();
            assertEquals(0, match.getId());
            assertEquals(0, match.getPatternIndex());
            assertEquals(1, match.getCaptures().length);
            assertEquals(0, match.getCaptureIndex());
            assertTrue(TSNode.eq(rootNode, match.getCaptures()[0].getNode()));
        }
    }


    @Test
    void setContainingByteRange() {
        assertTrue(cursor.setContainingByteRange(0, 1));
        assertFalse(cursor.setContainingByteRange(2, 1));
    }

    @Test
    void setContainingPointRange(){
        assertTrue(cursor.setContainingPointRange(new TSPoint(0, 0), new TSPoint(0, 10)));
        assertFalse(cursor.setContainingPointRange(new TSPoint(0, 10), new TSPoint(0, 1)));
    }

    @Test
    void predicateFilteringEq() {
        assumeTrue(json != null);
        // [1, null]
        // #eq? @val "1" matches the first element
        query = new TSQuery(json, "((number) @val (#eq? @val \"1\"))");
        cursor.exec(query, rootNode, JSON_SRC);
        TSQueryMatch match = new TSQueryMatch();
        assertTrue(cursor.nextMatch(match));
        assertEquals(1, match.getCaptures().length);
        assertEquals("1", JSON_SRC.substring(match.getCaptures()[0].getNode().getStartByte(), match.getCaptures()[0].getNode().getEndByte()));

        // #eq? @val "2" should not match anything in [1, null]
        query = new TSQuery(json, "((number) @val (#eq? @val \"2\"))");
        cursor.exec(query, rootNode, JSON_SRC);
        assertFalse(cursor.nextMatch(match));
    }

    @Test
    void predicateFilteringNotMatch() {
        assumeTrue(json != null);
        // [1, null]
        // #not-match? @val "^n" matches 1 but excludes null
        query = new TSQuery(json, "((_) @val (#not-match? @val \"^n\"))");
        cursor.exec(query, rootNode, JSON_SRC);
        TSQueryMatch match = new TSQueryMatch();

        boolean foundOne = false;
        boolean foundNull = false;
        while(cursor.nextMatch(match)) {
            String text = JSON_SRC.substring(match.getCaptures()[0].getNode().getStartByte(), match.getCaptures()[0].getNode().getEndByte());
            if (text.equals("1")) foundOne = true;
            if (text.equals("null")) foundNull = true;
        }
        assertTrue(foundOne, "Should have matched '1'");
        assertFalse(foundNull, "Should not have matched 'null' due to #not-match?");
    }

    @Test
    void predicateEqWithSourceText() {
        assumeTrue(json != null);
        // Test #eq? @foo "bar"
        String src = "[\"bar\", \"baz\"]";
        tree = parser.parseString(null, src);
        query = new TSQuery(json, "((string) @foo (#eq? @foo \"\\\"bar\\\"\"))");
        cursor.exec(query, tree.getRootNode(), src);
        TSQueryMatch match = new TSQueryMatch();
        assertTrue(cursor.nextMatch(match));
        assertEquals("\"bar\"", src.substring(match.getCaptures()[0].getNode().getStartByte(), match.getCaptures()[0].getNode().getEndByte()));
        assertFalse(cursor.nextMatch(match));
    }

    @Test
    void predicateNotMatchWithSourceText() {
        assumeTrue(json != null);
        // Test #not-match? @foo "^[A-Z]"
        String src = "[\"Alpha\", \"beta\"]";
        tree = parser.parseString(null, src);
        query = new TSQuery(json, "((string) @foo (#not-match? @foo \"^\\\"[A-Z]\"))");
        cursor.exec(query, tree.getRootNode(), src);
        TSQueryMatch match = new TSQueryMatch();
        assertTrue(cursor.nextMatch(match));
        assertEquals("\"beta\"", src.substring(match.getCaptures()[0].getNode().getStartByte(), match.getCaptures()[0].getNode().getEndByte()));
        assertFalse(cursor.nextMatch(match));
    }
}
