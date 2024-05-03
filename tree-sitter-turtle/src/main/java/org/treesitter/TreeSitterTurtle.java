
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterTurtle extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-turtle");
    }
    private native static long tree_sitter_turtle();

    public TreeSitterTurtle() {
        super(tree_sitter_turtle());
    }

    private TreeSitterTurtle(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterTurtle(copyPtr());
    }
}
