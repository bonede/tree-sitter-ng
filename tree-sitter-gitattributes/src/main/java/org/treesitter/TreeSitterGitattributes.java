
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterGitattributes extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-gitattributes");
    }
    private native static long tree_sitter_gitattributes();

    public TreeSitterGitattributes() {
        super(tree_sitter_gitattributes());
    }

    private TreeSitterGitattributes(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterGitattributes(copyPtr());
    }
}
