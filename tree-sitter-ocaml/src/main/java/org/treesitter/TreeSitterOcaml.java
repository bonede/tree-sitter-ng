
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterOcaml extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-ocaml");
    }
    private native static long tree_sitter_ocaml();

    public TreeSitterOcaml() {
        super(tree_sitter_ocaml());
    }

    private TreeSitterOcaml(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterOcaml(copyPtr());
    }
}
