
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterDockerfile implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-dockerfile");
    }
    private native static long tree_sitter_dockerfile();

    private final long ptr;

    public TreeSitterDockerfile() {
        ptr = tree_sitter_dockerfile();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
