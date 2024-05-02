
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterCss extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-css");
    }
    private native static long tree_sitter_css();


    public TreeSitterCss() {
        super(tree_sitter_css());
    }

    private TreeSitterCss(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterCss(copyPtr());
    }
}
