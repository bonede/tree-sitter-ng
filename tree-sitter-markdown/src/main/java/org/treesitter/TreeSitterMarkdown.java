
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterMarkdown implements TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-markdown");
    }
    private native static long tree_sitter_markdown();

    private final long ptr;

    public TreeSitterMarkdown() {
        ptr = tree_sitter_markdown();
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
