
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterYaml implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-yaml");
    }
    private native static long tree_sitter_yaml();

    private final long ptr;

    public TreeSitterYaml() {
        ptr = tree_sitter_yaml();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
