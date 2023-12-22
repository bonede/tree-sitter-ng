package org.treesitter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TSTreeCursorTest {
    public static final String JSON_SRC = "[1, null]";
    private TSTree tree;
    private TSLanguage json;
    private TSParser parser;
    private TSNode rootNode;
    private TSNode arrayNode;
    private TSNode numberNode;
    private TSTreeCursor rootCursor;

    @BeforeEach
    void beforeEach() {
        parser = new TSParser();
        json = new TreeSitterJson();
        parser.setLanguage(json);
        tree = parser.parseString(null, JSON_SRC);
        rootNode = tree.getRootNode();
        arrayNode = rootNode.getNamedChild(0);
        numberNode = arrayNode.getNamedChild(0);
        rootCursor = new TSTreeCursor(rootNode);
    }
    @Test
    void reset() {
        rootCursor.reset(arrayNode);
        assertEquals("array", rootCursor.currentNode().getType());
    }

    @Test
    void currentNode() {
        assertEquals("document", rootCursor.currentNode().getType());
    }

    @Test
    void currentFieldName() {
        assertNull(rootCursor.currentFieldName());
    }

    @Test
    void currentFieldId() {
        assertEquals(0, rootCursor.currentFieldId());
    }

    @Test
    void gotoParent() {
        assertFalse(rootCursor.gotoParent());
    }

    @Test
    void gotoNextSibling() {
        assertFalse(rootCursor.gotoNextSibling());
    }

    @Test
    void gotoFirstChild() {
        assertTrue(rootCursor.gotoFirstChild());
        assertEquals("array", rootCursor.currentNode().getType());
    }

    @Test
    void gotoFirstChildForByte() {
        assertEquals(0, rootCursor.gotoFirstChildForByte(0));
        assertEquals("array", rootCursor.currentNode().getType());
    }

    @Test
    void gotoFirstChildForPoint() {
        assertEquals(0, rootCursor.gotoFirstChildForPoint(new TSPoint(0, 0)));
        assertEquals("array", rootCursor.currentNode().getType());
    }

    @Test
    void copy() {
        rootCursor.gotoFirstChild();
        TSTreeCursor copy = rootCursor.copy();
        assertEquals("array", copy.currentNode().getType());
    }
}