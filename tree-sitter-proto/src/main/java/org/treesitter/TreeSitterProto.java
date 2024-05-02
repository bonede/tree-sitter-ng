
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterProto extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-proto");
    }
    private native static long tree_sitter_proto();

    public TreeSitterProto() {
        super(tree_sitter_proto());
    }

    private TreeSitterProto(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterProto(copyPtr());
    }
}
