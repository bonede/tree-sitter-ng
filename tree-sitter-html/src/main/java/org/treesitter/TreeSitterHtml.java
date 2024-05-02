
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterHtml extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-html");
    }
    private native static long tree_sitter_html();

    public TreeSitterHtml() {
        super(tree_sitter_html());
    }

    private TreeSitterHtml(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterHtml(copyPtr());
    }
}
