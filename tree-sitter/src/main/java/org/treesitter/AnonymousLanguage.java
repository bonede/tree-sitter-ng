package org.treesitter;

public class AnonymousLanguage implements TSLanguage{
    private final long ptr;

    public AnonymousLanguage(long ptr) {
        this.ptr = ptr;
    }

    @Override
    public long getPtr() {
        return ptr;
    }
}
