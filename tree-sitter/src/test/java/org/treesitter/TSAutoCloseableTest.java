package org.treesitter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests for AutoCloseable implementation in Tree-sitter Java bindings.
 * <p>
 * Note on TSTree: TSTree is usually created by parser.parse().
 * We test idempotency via manual instantiation if we can't easily get a valid tree without a language.
 * TSTree constructor is package-private, so we test via parser if we had a language.
 * Since we can't easily create a valid TSTree without a language in this generic test
 * and calling methods on a NULL tree pointer crashes, we'll focus on the classes
 * we can safely instantiate and close.
 */
class TSAutoCloseableTest {

    @Test
    void testParserAutoClose() {
        assertDoesNotThrow(() -> {
            try (TSParser parser = new TSParser()) {
                // usage
            }
        });
    }

    @Test
    void testParserDoubleClose() {
        TSParser parser = new TSParser();
        parser.close();
        assertDoesNotThrow(parser::close);
    }


    @Test
    void testQueryCursorAutoClose() {
        assertDoesNotThrow(() -> {
            try (TSQueryCursor cursor = new TSQueryCursor()) {
                // usage
            }
        });
    }

    @Test
    void testQueryCursorDoubleClose() {
        TSQueryCursor cursor = new TSQueryCursor();
        cursor.close();
        assertDoesNotThrow(cursor::close);
    }

}
