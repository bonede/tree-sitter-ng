
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterAgda extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-agda");
    }
    private native static long tree_sitter_agda();

    public TreeSitterAgda() {
        super(tree_sitter_agda());
    }

    protected TreeSitterAgda(long ptr){
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterAgda(copyPtr());
    }
}
