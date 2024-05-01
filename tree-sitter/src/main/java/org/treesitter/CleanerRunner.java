package org.treesitter;

import java.lang.ref.Cleaner;

public abstract class CleanerRunner {
    static Cleaner cleaner = Cleaner.create();
    public static void register(Object obj, Runnable action){
        cleaner.register(obj, action);
    };
}
