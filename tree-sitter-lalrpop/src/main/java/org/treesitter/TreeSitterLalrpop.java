
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterLalrpop extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-lalrpop");
    }
    private native static long tree_sitter_lalrpop();

    public TreeSitterLalrpop() {
        super(tree_sitter_lalrpop());
    }


    private TreeSitterLalrpop(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterLalrpop(copyPtr());
    }
}
