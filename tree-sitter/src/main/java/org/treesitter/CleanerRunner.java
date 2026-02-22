package org.treesitter;

import java.lang.ref.Cleaner;

public abstract class CleanerRunner {
    static Cleaner cleaner = Cleaner.create();
    public static Cleaner.Cleanable register(Object obj, Runnable action){
        return cleaner.register(obj, action);
    }
}
