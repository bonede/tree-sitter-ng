
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterRuby extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-ruby");
    }
    private native static long tree_sitter_ruby();

    public TreeSitterRuby() {
        super(tree_sitter_ruby());
    }


    private TreeSitterRuby(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterRuby(copyPtr());
    }
}
