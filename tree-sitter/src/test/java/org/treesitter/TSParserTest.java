package org.treesitter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;

class TSParserTest {

    public static final String JSON_SRC = "[1, null]";
    private static TSLanguage json = new TreeSitterJson();
    private static TSParser parser = new TSParser();
    @BeforeAll
    static void beforeAll(){
        parser.setLanguage(json);
    }

    @Test
    public void parseString(){
        TSTree tree = parser.parseString(null, JSON_SRC);
        assertNotNull(tree);
    }

    @Test
    public void parseEncoding(){
        parser.reset();
        TSTree tree = parser.parseStringEncoding(null, JSON_SRC, TSInputEncoding.TSInputEncodingUTF8);
        assertNotNull(tree);
    }

    @Test
    public void  parse(){
        parser.reset();
        byte[] buffer = new byte[1024];
        String input = JSON_SRC;
        TSReader reader = (buf, offset, position) -> {
            if(offset >= input.length()){
                return 0;
            }
            ByteBuffer charBuffer = ByteBuffer.wrap(buf);
            charBuffer.put(input.getBytes());
            return input.length();
        };
        TSTree tree = parser.parse(buffer, null, reader, TSInputEncoding.TSInputEncodingUTF8);
        TSNode rootNode = tree.getRootNode();
        TSNode arrayNode = rootNode.getNamedChild(0);
        TSNode numberNode = arrayNode.getNamedChild(0);
        assertEquals("document", rootNode.getType());
        assertEquals("array", arrayNode.getType());
        assertEquals("number", numberNode.getType());
    }

    @Test
    public void parseRange(){
        TSParser parser = new TSParser();
        TSLanguage json = new TreeSitterJson();
        parser.setLanguage(json);
        assertTrue(parser.setIncludedRanges(new TSRange[]{new TSRange(
                new TSPoint(0, 0),
                new TSPoint(0, 9),
                0, 9
        )}));
        TSRange[] ranges = parser.getIncludedRanges();
        assertEquals(1, ranges.length);
        assertEquals(0, ranges[0].getStartByte());
        assertEquals(9, ranges[0].getEndByte());
        assertEquals(0, ranges[0].getStartPoint().getRow());
        assertEquals(0, ranges[0].getStartPoint().getColumn());
        assertEquals(0, ranges[0].getEndPoint().getRow());
        assertEquals(9, ranges[0].getEndPoint().getColumn());
    }

    @Test
    void reset() {
        TSParser parser = new TSParser();
        parser.reset();
    }

    @Test
    void setTimeoutMicros() {
        TSParser parser = new TSParser();
        parser.setTimeoutMicros(100l);
    }

    @Test
    void getTimeoutMicros() {
        TSParser parser = new TSParser();
        parser.setTimeoutMicros(100l);
        assertEquals(100l, parser.getTimeoutMicros());
    }

    @Test
    void setCancellationFlag() {
        TSParser parser = new TSParser();
        parser.setTimeoutMicros(1);
    }

    @Test
    void getCancellationFlag() {
        TSParser parser = new TSParser();
        parser.setCancellationFlag(1);
        assertEquals(1, parser.getCancellationFlag());
    }

    @Test
    void setLogger() {
        TSParser parser = new TSParser();
        TSLanguage json = new TreeSitterJson();
        parser.setLanguage(json);
        TSLogger logger = (type, message) -> {
            assertFalse(message.isEmpty());
            assertNotNull(type);
        };
        parser.setLogger(logger);
        parser.parseString(null, JSON_SRC);
    }

    @Test
    void getLogger() {
        TSParser parser = new TSParser();
        TSLanguage json = new TreeSitterJson();
        parser.setLanguage(json);
        TSLogger logger = (type, message) -> System.out.format("%s %s\n", type, message);
        parser.setLogger(logger);
        assertEquals(logger, parser.getLogger());
    }

    @Test
    void getLanguage(){
        TSParser parser = new TSParser();
        TSLanguage json = new TreeSitterJson();
        parser.setLanguage(json);
        assertEquals(parser.getLanguage().getPtr(), json.getPtr());
    }


    @Test
    void printDotGraphs() throws IOException {
        TSParser parser = new TSParser();
        TSLanguage json = new TreeSitterJson();
        parser.setLanguage(json);
        parser.printDotGraphs(new File("/data/foo.dot"));
        parser.parseString(null, JSON_SRC);
        parser.printDotGraphs(null);
        parser.reset();
    }
}