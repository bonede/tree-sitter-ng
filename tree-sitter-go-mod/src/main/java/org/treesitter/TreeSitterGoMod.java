
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterGoMod extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-go-mod");
    }
    private native static long tree_sitter_go_mod();

    public TreeSitterGoMod() {
        super(tree_sitter_go_mod());;
    }

    private TreeSitterGoMod(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterGoMod(copyPtr());
    }
}
