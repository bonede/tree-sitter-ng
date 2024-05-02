
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterCuda extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-cuda");
    }
    private native static long tree_sitter_cuda();

    public TreeSitterCuda() {
        super(tree_sitter_cuda());
    }

    private TreeSitterCuda(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterCuda(copyPtr());
    }
}
