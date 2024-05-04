package org.treesitter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TsLookAheadIteratorTest {
    private TSLanguage json;
    private TsLookAheadIterator iter;

    @BeforeEach
    void setup(){
        json = new TreeSitterJson();
        iter = new TsLookAheadIterator(json, 0);
    }

    @Test
    void constructor(){
        assertNotNull(new TsLookAheadIterator(json, 0));
        assertThrows(TSException.class, () -> {
            new TsLookAheadIterator(json, 999);
        });
    }

    @Test
    void resetState() {
        assertTrue(iter.resetState(2));
    }

    @Test
    void reset() {
        assertTrue(iter.reset(json, 1));
    }

    @Test
    void getLanguage() {
        assertNotNull(iter.getLanguage());
    }

    @Test
    void next() {
        assertTrue(iter.next());
    }

    @Test
    void currentSymbol() {
        assertEquals(65535, iter.currentSymbol());
    }

    @Test
    void currentSymbolName() {
        assertEquals("ERROR", iter.currentSymbolName());
    }
}