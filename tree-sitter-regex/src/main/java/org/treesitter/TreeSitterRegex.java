
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterRegex implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-regex");
    }
    private native static long tree_sitter_regex();

    private final long ptr;

    public TreeSitterRegex() {
        ptr = tree_sitter_regex();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
