
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterClojure extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-clojure");
    }
    private native static long tree_sitter_clojure();

    public TreeSitterClojure() {
        super(tree_sitter_clojure());
    }

    public TreeSitterClojure(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterClojure(copyPtr());
    }
}
