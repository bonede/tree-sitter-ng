
package org.treesitter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TreeSitterCppTest {
    @Test
    void parse() {
        TSParser parser = new TSParser();
        parser.setLanguage(new TreeSitterCpp());
        TSTree tree = parser.parseString(null, "}");
        assertNotNull(tree);
    }
}
