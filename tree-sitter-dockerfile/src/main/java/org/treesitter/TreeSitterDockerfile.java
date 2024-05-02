
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterDockerfile extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-dockerfile");
    }
    private native static long tree_sitter_dockerfile();

    public TreeSitterDockerfile() {
        super(tree_sitter_dockerfile());
    }

    private TreeSitterDockerfile(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterDockerfile(copyPtr());
    }
}
