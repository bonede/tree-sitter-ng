
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterToml extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-toml");
    }
    private native static long tree_sitter_toml();

    public TreeSitterToml() {
        super(tree_sitter_toml());
    }

    private TreeSitterToml(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterToml(copyPtr());
    }
}
