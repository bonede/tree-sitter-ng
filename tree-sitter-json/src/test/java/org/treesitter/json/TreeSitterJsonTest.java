package org.treesitter.json;

import org.junit.jupiter.api.Test;
import org.treesitter.TreeSitterJson;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TreeSitterJsonTest {
    @Test
    public void init(){
        TreeSitterJson json = new TreeSitterJson();
        assertEquals(2, json.fieldCount());
    }
}