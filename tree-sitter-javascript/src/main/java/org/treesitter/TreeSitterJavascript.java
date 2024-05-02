
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterJavascript extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-javascript");
    }
    private native static long tree_sitter_javascript();

    public TreeSitterJavascript() {
        super(tree_sitter_javascript());
    }

    private TreeSitterJavascript(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterJavascript(copyPtr());
    }
}
