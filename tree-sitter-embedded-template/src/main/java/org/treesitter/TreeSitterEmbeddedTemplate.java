
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterEmbeddedTemplate extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-embedded-template");
    }
    private native static long tree_sitter_embedded_template();

    public TreeSitterEmbeddedTemplate() {
        super(tree_sitter_embedded_template());
    }

    private TreeSitterEmbeddedTemplate(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterEmbeddedTemplate(copyPtr());
    }
}
