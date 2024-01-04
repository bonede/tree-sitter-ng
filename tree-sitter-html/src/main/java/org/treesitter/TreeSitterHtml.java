
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterHtml implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-html");
    }
    private native static long tree_sitter_html();

    private final long ptr;

    public TreeSitterHtml() {
        ptr = tree_sitter_html();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
