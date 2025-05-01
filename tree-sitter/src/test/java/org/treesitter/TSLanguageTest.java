package org.treesitter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(25, json.symbolCount());
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
        assertEquals(32, json.stateCount());
    }

    @Test
    void nextState(){
        assertEquals(0, json.nextState(0, 15));
    }

    @Test
    void abiVersion(){
        assertTrue( json.abiVersion() > 0);
    }

    @Test
    void metadata(){
        assertNull(json.metadata());
    }

    @Test
    void supertypes(){
        assertArrayEquals(new int[] {}, json.supertypes());
    }

    @Test
    void subtype(){
        assertArrayEquals(new int[] {}, json.subtypes(0));
    }

    @Test
    void name(){
        assertNull(json.name());
    }

}