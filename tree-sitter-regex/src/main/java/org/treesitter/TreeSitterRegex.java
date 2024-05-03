
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterRegex extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-regex");
    }
    private native static long tree_sitter_regex();


    public TreeSitterRegex() {
        super(tree_sitter_regex());
    }

    private TreeSitterRegex(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterRegex(copyPtr());
    }
}
