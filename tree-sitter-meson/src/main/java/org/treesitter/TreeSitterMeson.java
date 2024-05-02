
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterMeson extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-meson");
    }
    private native static long tree_sitter_meson();

    public TreeSitterMeson() {
        super(tree_sitter_meson());
    }

    private TreeSitterMeson(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterMeson(copyPtr());
    }
}
