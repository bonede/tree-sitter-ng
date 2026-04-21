
package org.treesitter;

import org.treesitter.utils.NativeUtils;

public class TreeSitterPowershell extends TSLanguage {

    static {
        NativeUtils.loadLib("lib/tree-sitter-powershell");
    }
    private native static long tree_sitter_powershell();

    public TreeSitterPowershell() {
        super(tree_sitter_powershell());
    }

    private TreeSitterPowershell(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new TreeSitterPowershell(copyPtr());
    }
}
