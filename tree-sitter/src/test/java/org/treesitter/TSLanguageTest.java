package org.treesitter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TSLanguageTest {
    private final TSLanguage json = new TreeSitterJson();

    @Test
    void version() {
        assertEquals(14, json.version());
    }

    @Test
    void fieldCount() {
        assertEquals(2, json.fieldCount());
    }

    @Test
    void fieldNameForId() {
        assertEquals("key",json.fieldNameForId(1));
    }

    @Test
    void fieldIdForName() {
        assertEquals(1, json.fieldIdForName("key"));
    }

    @Test
    void symbolType() {
        assertEquals(TSSymbolType.TSSymbolTypeAuxiliary, json.symbolType(0));
    }

    @Test
    void symbolCount() {
        assertEquals(26, json.symbolCount());
    }

    @Test
    void symbolName() {
        assertEquals("end", json.symbolName(0));
    }

    @Test
    void symbolForName() {
        assertEquals(0, json.symbolForName("end", false));
    }

    @Test
    void copy(){
        assertNotNull(json.copy());
    }

    @Test
    void stateCount(){
        assertEquals(33, json.stateCount());
    }

    @Test
    void nextState(){
        assertEquals(0, json.nextState(0, 15));
    }

}