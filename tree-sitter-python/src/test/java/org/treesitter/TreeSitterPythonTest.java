
package org.treesitter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TreeSitterPythonTest {
    @Test
    void parse() {
        TSParser parser = new TSParser();
        parser.setLanguage(new TreeSitterPython());
        TSTree tree = parser.parseString(null, "print('Hello')");
        assertNotNull(tree);
    }
}
