
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterPod extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-pod");
    }
    private native static long tree_sitter_pod();



    public TreeSitterPod() {
        super(tree_sitter_pod());
    }

    private TreeSitterPod(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterPod(copyPtr());
    }
}
