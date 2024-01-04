
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterPod implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-pod");
    }
    private native static long tree_sitter_pod();

    private final long ptr;

    public TreeSitterPod() {
        ptr = tree_sitter_pod();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
