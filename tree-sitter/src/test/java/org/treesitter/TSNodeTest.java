package org.treesitter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TSNodeTest {
    public static final String JSON_SRC = "[1, null]";
    private TSTree tree;
    private TSLanguage json;
    private TSParser parser;
    private TSNode rootNode;
    private TSNode arrayNode;
    private TSNode numberNode;

    @BeforeEach
    void beforeEach() {
        parser = new TSParser();
        json = new TreeSitterJson();
        parser.setLanguage(json);
        tree = parser.parseString(null, JSON_SRC);
        rootNode = tree.getRootNode();
        arrayNode = rootNode.getNamedChild(0);
        numberNode = arrayNode.getNamedChild(0);
    }

    @Test
    void getChildCount() {
        assertEquals(1, rootNode.getChildCount());
    }

    @Test
    void getNamedChildCount() {
        assertEquals(2, arrayNode.getNamedChildCount());
    }

    @Test
    void getNamedChild() {
        assertEquals("number", arrayNode.getNamedChild(0).getType());
    }

    @Test
    void getType() {
        assertEquals(15, rootNode.getSymbol());
    }

    @Test
    void getSymbol() {
        assertEquals("array", arrayNode.getType());
    }

    @Test
    void isNull() {
        assertFalse(arrayNode.isNull());
    }

    @Test
    void isNamed() {
        assertTrue(arrayNode.isNamed());
    }

    @Test
    void isMissing(){
        assertFalse(rootNode.isMissing());
    }

    @Test
    void isExtra(){
        assertFalse(rootNode.isExtra());
    }

    @Test
    void hasChanges(){
        assertFalse(rootNode.hasChanges());
    }

    @Test
    void hasError(){
        assertFalse(rootNode.hasError());
    }

    @Test
    void isError(){
        assertFalse(rootNode.isError());
    }

    @Test
    void getParserState(){
        assertEquals(rootNode.getParserState(), 0);
    }

    @Test
    void nextParserState(){
        assertEquals(rootNode.getNextParserState(), 0);
    }

    @Test
    void getStartByte() {
        assertEquals(0, rootNode.getStartByte());
    }

    @Test
    void getEndByte() {
        assertEquals(9, rootNode.getEndByte());
    }

    @Test
    void getStartPoint() {
        assertEquals(0, rootNode.getStartPoint().getRow());
        assertEquals(0, rootNode.getStartPoint().getRow());
    }

    @Test
    void getEndPoint() {
        assertEquals(0, rootNode.getEndPoint().getRow());
        assertEquals(9, rootNode.getEndPoint().getColumn());
    }

    @Test
    void getParent() {
        assertTrue(rootNode.getParent().isNull());
    }

    @Test
    void getChildContainingDescendant(){
        TSNode child = rootNode.getChild(0);
        TSNode descendant = rootNode.getChild(0).getChild(0);
        assertEquals(child.toString(), rootNode.getChildContainingDescendant(descendant).toString());
    }

    @Test
    void getChild() {
        assertEquals("array", rootNode.getChild(0).getType());
    }

    @Test
    void getFieldNameForChild(){
        assertNull(rootNode.getFieldNameForChild(0));
    }


    @Test
    void getNextNamedSibling() {
        assertTrue(rootNode.getNextNamedSibling().isNull());
    }

    @Test
    void getPrevNamedSibling() {
        assertTrue(rootNode.getPrevNamedSibling().isNull());
    }

    @Test
    void getNextSibling() {
        assertEquals(",", numberNode.getNextSibling().getType());
    }

    @Test
    void getPrevSibling() {
        assertEquals("[", numberNode.getPrevSibling().getType());
    }

    @Test
    void getChildByFieldName() {
        parser.reset();
        tree = parser.parseString(null, "{\"foo\": 42}");
        assertEquals("string", tree.getRootNode().getNamedChild(0)
                    .getNamedChild(0)
                    .getChildByFieldName("key")
                    .getType());
    }

    @Test
    void getChildByFieldId() {
        parser.reset();
        tree = parser.parseString(null, "{\"foo\": 42}");
        assertEquals("string", tree.getRootNode().getNamedChild(0)
                .getNamedChild(0)
                .getChildByFieldId(1)
                .getType());
    }

    @Test
    void getFirstChildForByte() {
        assertEquals("[", arrayNode.getFirstChildForByte(0).getType());
    }

    @Test
    void getFirstNamedChildForByte() {
        assertEquals("number", arrayNode.getFirstNamedChildForByte(0).getType());
    }

    @Test
    void getDescendantForByteRange() {
        assertEquals("[", arrayNode.getDescendantForByteRange(0, 1).getType());
    }

    @Test
    void getDescendantForPointRange() {
        assertEquals("[", arrayNode.getDescendantForPointRange(
                new TSPoint(0, 0), new TSPoint(0, 1)).getType()
        );
    }

    @Test
    void getNamedDescendantForByteRange() {
        assertEquals("number", arrayNode.getNamedDescendantForByteRange(1, 2).getType());
    }

    @Test
    void getNamedDescendantForPointRange() {
        assertEquals("number", arrayNode.getNamedDescendantForPointRange(
                new TSPoint(0, 1), new TSPoint(0, 2)).getType()
        );
    }

    @Test
    void edit() {
        int editStart = 0;
        int editEnd = 1;
        rootNode.edit(new TSInputEdit(editStart, editStart, editEnd,
                new TSPoint(0, editStart), new TSPoint(0, editStart), new TSPoint(0, editEnd)));
    }

    @Test
    void eq() {
        assertFalse(TSNode.eq(rootNode, numberNode));
    }

    @Test
    void getGrammarType(){
        assertEquals("document", rootNode.getGrammarType());
    }


    @Test
    void getGrammarSymbol(){
        assertEquals(15, rootNode.getGrammarSymbol());
    }

    @Test
    void getFieldNameForNamedChild() {
        assertNull(rootNode.getFieldNameForNamedChild(0));
    }

    @Test
    void getChildWithDescendant() {
        TSNode child = rootNode.getChild(0);
        TSNode descendant = rootNode.getChild(0).getChild(0);
        assertEquals(child.toString(), rootNode.getChildWithDescendant(descendant).toString());
        assertNotNull(rootNode.getChildWithDescendant(descendant));
    }
}