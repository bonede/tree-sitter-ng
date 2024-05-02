
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterGitignore extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-gitignore");
    }
    private native static long tree_sitter_gitignore();

    public TreeSitterGitignore() {
        super(tree_sitter_gitignore());
    }

    private TreeSitterGitignore(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterGitignore(copyPtr());
    }
}
