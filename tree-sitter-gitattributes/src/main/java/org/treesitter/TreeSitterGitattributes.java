
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterGitattributes implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-gitattributes");
    }
    private native static long tree_sitter_gitattributes();

    private final long ptr;

    public TreeSitterGitattributes() {
        ptr = tree_sitter_gitattributes();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
