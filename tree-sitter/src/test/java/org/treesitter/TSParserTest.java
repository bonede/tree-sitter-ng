package org.treesitter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

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
        File dotFile = File.createTempFile("json", ".dot");
        parser.printDotGraphs(dotFile);
        parser.parseString(null, JSON_SRC);
        assertTrue(dotFile.length() > 0);
        parser.printDotGraphs(null);
        parser.reset();
        parser.parseString(null, JSON_SRC);

    }

    @Test
    void emojiInUtf16() {
        // the 🌏 as in utf16
        // ref https://www.fileformat.info/info/unicode/char/1f30e/index.htm
        String emoji = "\uD83C\uDF0E";
        parser.reset();
        TSTree tree = parser.parseString(null, emoji);
        TSNode node = tree.getRootNode();
        byte[] bytes = emoji.getBytes(StandardCharsets.UTF_8);
        assertEquals(4, bytes.length);
        assertEquals(node.getEndByte(), bytes.length);
    }

    @Test
    void emojiInSourceCode() {
        // 🌏 emoji
        String emoji = "\uD83C\uDF10";
        parser.reset();
        TSTree tree = parser.parseString(null, emoji);
        TSNode node = tree.getRootNode();
        byte[] bytes = emoji.getBytes(StandardCharsets.UTF_8);
        assertEquals(4, bytes.length);
        assertEquals(node.getEndByte(), bytes.length);
    }

    @Test
    void nodeText(){
        // 🌏 emoji
        String emoji = "\uD83C\uDF10";
        parser.reset();
        TSTree tree = parser.parseString(null, emoji);
        TSNode node = tree.getRootNode();
        byte[] bytes = emoji.getBytes(StandardCharsets.UTF_8);
        int startByte = node.getStartByte();
        int endByte = node.getEndByte();
        byte[] nodeBytes = Arrays.copyOfRange(bytes, startByte, endByte);
        String s = new String(nodeBytes, StandardCharsets.UTF_8);
        assertEquals(4, bytes.length);
        assertEquals(node.getEndByte(), bytes.length);
        assertEquals(s, emoji);
    }

}