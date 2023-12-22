package org.treesitter;

public interface TSLogger {
    void log(TSLogType type, String message);
}
