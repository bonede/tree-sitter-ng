
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterClojure implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-clojure");
    }
    private native static long tree_sitter_clojure();

    private final long ptr;

    public TreeSitterClojure() {
        ptr = tree_sitter_clojure();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
