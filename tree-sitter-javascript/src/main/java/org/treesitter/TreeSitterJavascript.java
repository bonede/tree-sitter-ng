
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterJavascript implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-javascript");
    }
    private native static long tree_sitter_javascript();

    private final long ptr;

    public TreeSitterJavascript() {
        ptr = tree_sitter_javascript();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
