package org.treesitter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.treesitter.utils.NativeUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class TSQueryPredicateTest {
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
        rootNode = tree.getRootNode();
        cursor = new TSQueryCursor();
    }

    @Test
    void predicateFilteringEq() {
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

    @Test
    void predicateWithMultiByteChars() {
        // Test #eq? and #not-eq? with multi-byte characters (Emoji and CJK)
        // [ "😊", "世界" ]
        String src = "[ \"\uD83D\uDE0A\", \"\u4E16\u754C\" ]";
        tree = parser.parseString(null, src);
        TSNode root = tree.getRootNode();
        TSQueryMatch match = new TSQueryMatch();

        // 1. Positive test for Emoji
        query = new TSQuery(json, "((string) @s (#eq? @s \"\\\"\uD83D\uDE0A\\\"\"))");
        cursor.exec(query, root, src);
        assertTrue(cursor.nextMatch(match), "Should match the emoji string");
        assertFalse(cursor.nextMatch(match), "Should only match once");

        // 2. Positive test for CJK
        query = new TSQuery(json, "((string) @s (#eq? @s \"\\\"\u4E16\u754C\\\"\"))");
        cursor.exec(query, root, src);
        assertTrue(cursor.nextMatch(match), "Should match the CJK string");
        assertFalse(cursor.nextMatch(match), "Should only match once");

        // 3. Negative test using #not-eq?
        query = new TSQuery(json, "((string) @s (#not-eq? @s \"\\\"\uD83D\uDE0A\\\"\"))");
        cursor.exec(query, root, src);
        assertTrue(cursor.nextMatch(match), "Should match '世界' because it is not '😊'");
        // Verify it matched the second string, not the first
        byte[] srcBytes = src.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        int start = match.getCaptures()[0].getNode().getStartByte();
        int end = match.getCaptures()[0].getNode().getEndByte();
        String matchedText = new String(srcBytes, start, end - start, java.nio.charset.StandardCharsets.UTF_8);
        assertEquals("\"\u4E16\u754C\"", matchedText);
        assertFalse(cursor.nextMatch(match), "Should not match the emoji string");

        // 4. Regex test using #match?
        query = new TSQuery(json, "((string) @s (#match? @s \"\u4E16\"))");
        cursor.exec(query, root, src);
        assertTrue(cursor.nextMatch(match), "Should match '世界' using partial regex");
        assertFalse(cursor.nextMatch(match));
    }
}
