
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterYaml extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-yaml");
    }
    private native static long tree_sitter_yaml();

    public TreeSitterYaml() {
        super(tree_sitter_yaml());
    }


    private TreeSitterYaml(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterYaml(copyPtr());
    }
}
