
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterJulia extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-julia");
    }
    private native static long tree_sitter_julia();

    public TreeSitterJulia() {
        super(tree_sitter_julia());
    }

    private TreeSitterJulia(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterJulia(copyPtr());
    }
}
