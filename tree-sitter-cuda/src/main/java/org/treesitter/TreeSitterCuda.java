
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterCuda implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-cuda");
    }
    private native static long tree_sitter_cuda();

    private final long ptr;

    public TreeSitterCuda() {
        ptr = tree_sitter_cuda();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
