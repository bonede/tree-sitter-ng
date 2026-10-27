package org.treesitter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
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
    public void  parseWithOptions(){
        parser.reset();
        byte[] buffer = new byte[1024];
        String input = "{\n" +
                "  \"id\": 12345,\n" +
                "  \"name\": \"Alice\",\n" +
                "  \"email\": \"alice@example.com\",\n" +
                "  \"age\": 30,\n" +
                "  \"isActive\": true,\n" +
                "  \"roles\": [\"admin\", \"editor\"],\n" +
                "  \"profile\": {\n" +
                "    \"bio\": \"Loves programming and cats.\",\n" +
                "    \"location\": \"San Francisco\",\n" +
                "    \"website\": \"https://alice.dev\"\n" +
                "  }\n" +
                "}";
        TSReader reader = (buf, offset, position) -> {
            if(offset >= input.length()){
                return 0;
            }
            ByteBuffer charBuffer = ByteBuffer.wrap(buf);
            charBuffer.put(input.getBytes());
            return input.length();
        };
        TSTree tree = parser.parseWithOptions(buffer, null, reader, TSInputEncoding.TSInputEncodingUTF8, (parserState) -> {
            assertTrue(parserState.getCurrentByteOffset() > 0);
            return parserState.hasError();
        });
        TSNode rootNode = tree.getRootNode();
        TSNode arrayNode = rootNode.getNamedChild(0);
        TSNode numberNode = arrayNode.getNamedChild(0);
        assertEquals("document", rootNode.getType());
        assertEquals("object", arrayNode.getType());
        assertEquals("pair", numberNode.getType());
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

    // parseString must parse the UTF-8 encoding of the string as is, or the byte offsets of the nodes do not
    // match src.getBytes(UTF_8) and the node text (see nodeText) is wrong

    @Test
    void byteOrderMarkIsKept() {
        // a file read with a BOM, e.g., written by Windows Notepad or Visual Studio
        assertParsedAsUtf8Bytes("\uFEFF[1, 2]", "2");
    }

    @Test
    void zeroWidthNoBreakSpaceIsKept() {
        assertParsedAsUtf8Bytes("[\"a\uFEFFb\", 2]", "\"a\uFEFFb\"", "2");
    }

    @Test
    void nonCharactersAreKept() {
        assertParsedAsUtf8Bytes("[\"\uFFFE\uFFFF\", 2]", "\"\uFFFE\uFFFF\"", "2");
    }

    @Test
    void controlCharactersAreKept() {
        // a form feed, e.g., a page break in GNU C or Python sources
        assertParsedAsUtf8Bytes("[1,\f2]", "2");
        assertParsedAsUtf8Bytes("[\"a\u0001b\", 2]", "\"a\u0001b\"", "2");
    }

    private static void assertParsedAsUtf8Bytes(String src, String... texts) {
        byte[] bytes = src.getBytes(StandardCharsets.UTF_8);
        parser.reset();
        TSTree tree = parser.parseString(null, src);
        TSNode root = tree.getRootNode();
        assertEquals(bytes.length, root.getEndByte());
        // the same tree as when the UTF-8 bytes are parsed with a reader
        parser.reset();
        TSReader reader = (buf, offset, position) -> {
            int length = Math.max(0, Math.min(buf.length, bytes.length - offset));
            System.arraycopy(bytes, offset, buf, 0, length);
            return length;
        };
        TSTree bytesTree = parser.parse(new byte[bytes.length], null, reader, TSInputEncoding.TSInputEncodingUTF8);
        assertEquals(bytesTree.getRootNode().toString(), root.toString());
        for (String text : texts) {
            assertTrue(hasNodeWithText(root, bytes, text), "no node with text " + text + " in " + root);
        }
    }

    private static boolean hasNodeWithText(TSNode node, byte[] bytes, String text) {
        byte[] nodeBytes = Arrays.copyOfRange(bytes, node.getStartByte(), node.getEndByte());
        if (new String(nodeBytes, StandardCharsets.UTF_8).equals(text)) {
            return true;
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            if (hasNodeWithText(node.getChild(i), bytes, text)) {
                return true;
            }
        }
        return false;
    }

    @Test
    void cursor() {
        TSParser parser = new TSParser();
        TSLanguage lang = new TreeSitterJson();
        parser.setLanguage(lang);
        TSTree tree = parser.parseString(null, "c");
        TSNode rootNode = tree.getRootNode();
        TSTreeCursor cursor = new TSTreeCursor(rootNode);
        TSNode currentNode = cursor.currentNode();
        assertNotNull(currentNode.getType());
        assertNotNull(currentNode.getTree());
    }
}