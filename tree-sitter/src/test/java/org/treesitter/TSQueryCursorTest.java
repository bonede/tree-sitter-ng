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
    void execWithOptions(){
        parser.reset();
        tree = parser.parseString(null, "{\n" +
                "  \"users\": [\n" +
                "    {\n" +
                "      \"id\": 1001,\n" +
                "      \"name\": \"Alice\",\n" +
                "      \"email\": \"alice@example.com\",\n" +
                "      \"age\": 30,\n" +
                "      \"isActive\": true,\n" +
                "      \"roles\": [\"admin\", \"editor\"],\n" +
                "      \"profile\": {\n" +
                "        \"bio\": \"Loves programming and cats.\",\n" +
                "        \"location\": \"San Francisco\",\n" +
                "        \"website\": \"https://alice.dev\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1002,\n" +
                "      \"name\": \"Bob\",\n" +
                "      \"email\": \"bob@example.com\",\n" +
                "      \"age\": 28,\n" +
                "      \"isActive\": false,\n" +
                "      \"roles\": [\"user\"],\n" +
                "      \"profile\": {\n" +
                "        \"bio\": \"Enjoys hiking and photography.\",\n" +
                "        \"location\": \"New York\",\n" +
                "        \"website\": \"https://bobpics.com\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1003,\n" +
                "      \"name\": \"Charlie\",\n" +
                "      \"email\": \"charlie@example.com\",\n" +
                "      \"age\": 35,\n" +
                "      \"isActive\": true,\n" +
                "      \"roles\": [\"editor\"],\n" +
                "      \"profile\": {\n" +
                "        \"bio\": \"Tech enthusiast and blogger.\",\n" +
                "        \"location\": \"Seattle\",\n" +
                "        \"website\": \"https://charlietech.blog\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1004,\n" +
                "      \"name\": \"Diana\",\n" +
                "      \"email\": \"diana@example.com\",\n" +
                "      \"age\": 26,\n" +
                "      \"isActive\": true,\n" +
                "      \"roles\": [\"user\", \"moderator\"],\n" +
                "      \"profile\": {\n" +
                "        \"bio\": \"Passionate about UI/UX design.\",\n" +
                "        \"location\": \"Los Angeles\",\n" +
                "        \"website\": \"https://dianadesigns.io\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1005,\n" +
                "      \"name\": \"Ethan\",\n" +
                "      \"email\": \"ethan@example.com\",\n" +
                "      \"age\": 40,\n" +
                "      \"isActive\": false,\n" +
                "      \"roles\": [\"user\"],\n" +
                "      \"profile\": {\n" +
                "        \"bio\": \"Backend engineer, coffee lover.\",\n" +
                "        \"location\": \"Chicago\",\n" +
                "        \"website\": \"https://ethancode.dev\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1006,\n" +
                "      \"name\": \"Fiona\",\n" +
                "      \"email\": \"fiona@example.com\",\n" +
                "      \"age\": 33,\n" +
                "      \"isActive\": true,\n" +
                "      \"roles\": [\"admin\"],\n" +
                "      \"profile\": {\n" +
                "        \"bio\": \"Scrum master and agile coach.\",\n" +
                "        \"location\": \"Austin\",\n" +
                "        \"website\": \"https://fionaagile.com\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1007,\n" +
                "      \"name\": \"George\",\n" +
                "      \"email\": \"george@example.com\",\n" +
                "      \"age\": 29,\n" +
                "      \"isActive\": true,\n" +
                "      \"roles\": [\"user\", \"reviewer\"],\n" +
                "      \"profile\": {\n" +
                "        \"bio\": \"Writes about tech gadgets.\",\n" +
                "        \"location\": \"Boston\",\n" +
                "        \"website\": \"https://georgegadgets.blog\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1008,\n" +
                "      \"name\": \"Hannah\",\n" +
                "      \"email\": \"hannah@example.com\",\n" +
                "      \"age\": 24,\n" +
                "      \"isActive\": false,\n" +
                "      \"roles\": [\"user\"],\n" +
                "      \"profile\": {\n" +
                "        \"bio\": \"New developer learning JavaScript.\",\n" +
                "        \"location\": \"Denver\",\n" +
                "        \"website\": \"https://hannahlearns.dev\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1009,\n" +
                "      \"name\": \"Ian\",\n" +
                "      \"email\": \"ian@example.com\",\n" +
                "      \"age\": 38,\n" +
                "      \"isActive\": true,\n" +
                "      \"roles\": [\"manager\"],\n" +
                "      \"profile\": {\n" +
                "        \"bio\": \"Leading product teams since 2010.\",\n" +
                "        \"location\": \"San Diego\",\n" +
                "        \"website\": \"https://ianpm.com\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1010,\n" +
                "      \"name\": \"Julia\",\n" +
                "      \"email\": \"julia@example.com\",\n" +
                "      \"age\": 31,\n" +
                "      \"isActive\": true,\n" +
                "      \"roles\": [\"designer\", \"editor\"],\n" +
                "      \"profile\": {\n" +
                "        \"bio\": \"Illustrator and UI artist.\",\n" +
                "        \"location\": \"Portland\",\n" +
                "        \"website\": \"https://juliaart.io\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}");
        query = new TSQuery(json, "(string)");
        cursor.execWithOptions(query, tree.getRootNode(), (state) -> {
            assertTrue(state.getCurrentByteOffset() > 0);
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
}