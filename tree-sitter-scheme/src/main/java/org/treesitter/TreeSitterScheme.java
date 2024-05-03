
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterScheme extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-scheme");
    }
    private native static long tree_sitter_scheme();

    public TreeSitterScheme() {
        super(tree_sitter_scheme());
    }

    private TreeSitterScheme(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterScheme(copyPtr());
    }
}
