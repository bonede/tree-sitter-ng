
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterGitignore implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-gitignore");
    }
    private native static long tree_sitter_gitignore();

    private final long ptr;

    public TreeSitterGitignore() {
        ptr = tree_sitter_gitignore();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
