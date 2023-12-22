package org.treesitter;

public class TSTree {
    private long ptr;

    TSTree(long ptr) {
        this.ptr = ptr;
    }

    public long getPtr(){
        return ptr;
    }
}
