
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterCapnp extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-capnp");
    }
    private native static long tree_sitter_capnp();

    public TreeSitterCapnp() {
        super(tree_sitter_capnp());
    }

    protected TreeSitterCapnp(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterCapnp(copyPtr());
    }


}
