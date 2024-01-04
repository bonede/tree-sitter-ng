
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterTurtle implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-turtle");
    }
    private native static long tree_sitter_turtle();

    private final long ptr;

    public TreeSitterTurtle() {
        ptr = tree_sitter_turtle();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
