package org.treesitter;

/**
 * Interface for logging messages from the parser.
 */
public interface TSLogger {
    /**
     * Log a message.
     *
     * @param type The type of message.
     * @param message The message to log.
     */
    void log(TSLogType type, String message);
}
