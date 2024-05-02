
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterBash extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-bash");
    }
    private native static long tree_sitter_bash();

    public TreeSitterBash() {
        super(tree_sitter_bash());
    }

    protected TreeSitterBash(long ptr){
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterBash(copyPtr());
    }
}
