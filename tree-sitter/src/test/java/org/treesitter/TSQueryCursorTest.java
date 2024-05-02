package org.treesitter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        json = new TreeSitterJson();
        parser.setLanguage(json);
        tree = parser.parseString(null, JSON_SRC);
        query = new TSQuery(json, "((document) @root (#eq? @root \"foo\"))");

        cursor = new TSQueryCursor();
        rootNode = tree.getRootNode();

    }
    @Test
    void exec() {
        cursor.exec(query, rootNode);
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
        cursor.setByteRange(0, 5);
    }

    @Test
    void setPointRange() {
        cursor.setPointRange(new TSPoint(0, 0), new TSPoint(0, 10));
    }

    @Test
    void nextMatch() {
        TSQueryMatch match = new TSQueryMatch();
        assertThrows(TSException.class, () -> cursor.nextMatch(match));
        cursor.exec(query, rootNode);
        assertTrue(cursor.nextMatch(match));
        assertEquals(0, match.getId());
        assertEquals(0, match.getPatternIndex());
    }

    @Test
    void removeMatch() {
        cursor.exec(query, rootNode);
        TSQueryMatch match = new TSQueryMatch();
        cursor.removeMatch(match.getId());
    }

    @Test
    void nextCapture() {
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
}