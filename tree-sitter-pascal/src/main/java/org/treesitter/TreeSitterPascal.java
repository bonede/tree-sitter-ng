
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterPascal extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-pascal");
    }
    private native static long tree_sitter_pascal();

    public TreeSitterPascal() {
        super(tree_sitter_pascal());
    }

    private TreeSitterPascal(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterPascal(copyPtr());
    }
}
