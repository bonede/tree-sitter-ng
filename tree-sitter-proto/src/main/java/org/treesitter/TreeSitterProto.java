
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterProto implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-proto");
    }
    private native static long tree_sitter_proto();

    private final long ptr;

    public TreeSitterProto() {
        ptr = tree_sitter_proto();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
