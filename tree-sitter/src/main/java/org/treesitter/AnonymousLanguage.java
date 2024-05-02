package org.treesitter;

public class AnonymousLanguage extends TSLanguage{

    protected AnonymousLanguage(long ptr) {
        super(ptr);
    }

    @Override
    public TSLanguage copy() {
        return new AnonymousLanguage(copyPtr());
    }
}
