package org.treesitter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class TSTreeTest {
    public static final String JSON_SRC = "[1, null]";
    private static TSTree tree;
    private static TSLanguage json = new TreeSitterJson();
    private static TSParser parser = new TSParser();

    @BeforeAll
    static void beforeAll() {
        parser.setLanguage(json);
        tree = parser.parseString(null, JSON_SRC);
    }


    @Test
    void copy() {
        tree.copy();
        assertNotEquals(tree.getPtr(), tree.copy().getPtr());
    }

    @Test
    void getRootNode() {
        TSNode rootNode = tree.getRootNode();
        assertEquals("document", rootNode.getType());
    }

    @Test
    void getRootNodeWithOffset() {
        TSNode rootNode = tree.getRootNodeWithOffset(0, new TSPoint(0, 0));
        assertEquals("document", rootNode.getType());
    }


    @Test
    void getLanguage() {
        assertEquals(json.getPtr(), tree.getLanguage().getPtr());
    }

    @Test
    void getIncludedRanges() {
        TSRange[] ranges = tree.getIncludedRanges();
        assertTrue(ranges.length > 0);
        TSRange range = tree.getIncludedRanges()[0];
        assertEquals(0, range.getStartByte());
        assertEquals(-1, range.getEndByte());
        assertEquals(0, range.getStartPoint().getRow());
        assertEquals(0, range.getStartPoint().getColumn());
        assertEquals(-1, range.getEndPoint().getRow());
        assertEquals(-1, range.getEndPoint().getColumn());
    }

    @Test
    void edit() {
        final AtomicBoolean edited = new AtomicBoolean(false);
        parser.reset();
        byte[] buf = new byte[1024];
        String newJsonSrc = "[1, null, 4]";
        TSReader reader = new TSReader() {
            @Override
            public int read(byte[] buf, int offset, TSPoint position) {
                ByteBuffer byteBuffer = ByteBuffer.wrap(buf);
                if(edited.get()){
                    if(offset >= newJsonSrc.length()){
                        return 0;
                    }

                    byteBuffer.put(newJsonSrc.getBytes());
                    return newJsonSrc.length();
                }else {
                    if(offset >= JSON_SRC.length()){
                        return 0;
                    }
                    ByteBuffer charBuffer = ByteBuffer.wrap(buf);
                    charBuffer.put(JSON_SRC.getBytes());
                    return JSON_SRC.length();
                }
            }
        };
        tree = parser.parse(buf, null, reader, TSInputEncoding.TSInputEncodingUTF8);
        assertEquals(1, tree.getRootNode().getChildCount());
        assertEquals(2, tree.getRootNode().getNamedChild(0).getNamedChildCount());
        int editStart = 0;
        int editEnd = 1;
        tree.edit(new TSInputEdit(editStart, editStart, editEnd,
                new TSPoint(0, editStart), new TSPoint(0, editStart), new TSPoint(0, editEnd)));
        edited.set(true);
        tree = parser.parse(buf, tree, reader, TSInputEncoding.TSInputEncodingUTF8);
        assertEquals(1, tree.getRootNode().getChildCount());
        assertEquals(3, tree.getRootNode().getNamedChild(0).getNamedChildCount());
    }

    @Test
    void getChangedRanges() {
        final AtomicBoolean edited = new AtomicBoolean(false);
        parser.reset();
        byte[] buf = new byte[1024];
        String newJsonSrc = "[1, null, 4]";
        TSReader reader = new TSReader() {
            @Override
            public int read(byte[] buf, int offset, TSPoint position) {
                ByteBuffer byteBuffer = ByteBuffer.wrap(buf);
                if(edited.get()){
                    if(offset >= newJsonSrc.length()){
                        return 0;
                    }

                    byteBuffer.put(newJsonSrc.getBytes());
                    return newJsonSrc.length();
                }else {
                    if(offset >= JSON_SRC.length()){
                        return 0;
                    }
                    ByteBuffer charBuffer = ByteBuffer.wrap(buf);
                    charBuffer.put(JSON_SRC.getBytes());
                    return JSON_SRC.length();
                }
            }
        };
        tree = parser.parse(buf, null, reader, TSInputEncoding.TSInputEncodingUTF8);
        assertEquals(1, tree.getRootNode().getChildCount());
        assertEquals(2, tree.getRootNode().getNamedChild(0).getNamedChildCount());
        int editStart = 0;
        int editEnd = 1;
        tree.edit(new TSInputEdit(editStart, editStart, editEnd,
                new TSPoint(0, editStart), new TSPoint(0, editStart), new TSPoint(0, editEnd)));
        edited.set(true);
        TSTree newTree = parser.parse(buf, tree, reader, TSInputEncoding.TSInputEncodingUTF8);
        TSRange[] ranges = TSTree.getChangedRanges(tree, newTree);
        assertTrue(ranges.length > 0);
        assertEquals(12, ranges[0].getEndByte());
    }

    @Test
    void printDotGraphs() throws IOException {
        File dotFile = File.createTempFile("tree", ".dot");
        tree.printDotGraphs(dotFile);
        assertTrue(dotFile.length() > 0);
    }

    @Test
    void testTreeGC() throws InterruptedException {
        TSTree tree1 = tree.copy();
        TSNode node = tree1.getRootNode().getChild(0);
        tree1 = null;
        System.gc();
        Thread.sleep(1000);
        assertNull(tree1);
        assertEquals(tree.getRootNode().getChildCount(), node.getParent().getChildCount());
    }
}