
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterJson5 extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-json5");
    }
    private native static long tree_sitter_json5();


    public TreeSitterJson5() {
        super(tree_sitter_json5());
    }

    private TreeSitterJson5(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterJson5(copyPtr());
    }


}
