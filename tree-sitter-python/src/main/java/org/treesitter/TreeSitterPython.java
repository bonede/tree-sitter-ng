
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterPython implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-python");
    }
    private native static long tree_sitter_python();

    private final long ptr;

    public TreeSitterPython() {
        ptr = tree_sitter_python();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
