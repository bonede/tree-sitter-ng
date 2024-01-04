
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterCss implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-css");
    }
    private native static long tree_sitter_css();

    private final long ptr;

    public TreeSitterCss() {
        ptr = tree_sitter_css();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
