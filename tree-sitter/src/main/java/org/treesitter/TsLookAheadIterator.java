package org.treesitter;

public class TsLookAheadIterator implements AutoCloseable {
    private long ptr;
    private TSLanguage lang;
    private final java.lang.ref.Cleaner.Cleanable cleanable;
    /**
     * Create a new lookahead iterator for the given language and parse state. <br>
     *
     * Repeatedly using {@link TsLookAheadIterator#next()} and
     * {@link TsLookAheadIterator#currentSymbol()} will generate valid symbols in the
     * given parse state. Newly created lookahead iterators will contain the ERROR
     * symbol. <br>
     *
     * Lookahead iterators can be useful to generate suggestions and improve syntax
     * error diagnostics. To get symbols valid in an ERROR node, use the lookahead
     * iterator on its first leaf node state. For MISSING nodes, a lookahead
     * iterator created on the previous non-extra leaf node may be appropriate.
     *
     * @param language The language
     * @param state Parser state
     *
     * @throws TSException If state is invalid for the language
     */
    public TsLookAheadIterator(TSLanguage language, int state) {
        this.ptr = TSParser.ts_lookahead_iterator_new(language.getPtr(), state);
        if(this.ptr == 0){
            throw new TSException("State is invalid.");
        }
        this.lang = language;
        this.cleanable = CleanerRunner.register(this, new TsLookAheadIteratorCleanAction(ptr));
    }

    @Override
    public void close() {
        cleanable.clean();
    }

    /**
     * Reset the lookahead iterator to another state.
     *
     * @param state New state
     *
     * @return <code>true</code> if the iterator was reset to the given state and <code>false</code> otherwise.
     */
    public boolean resetState(int state){
        return TSParser.ts_lookahead_iterator_reset_state(ptr, state);
    }

    /**
     * Reset the lookahead iterator.
     *
     * @param language The language
     * @param state New state
     *
     * @return <code>true</code> if the language was set successfully and <code>false</code> otherwise.
     */
    public boolean reset(TSLanguage language, int state){
        return TSParser.ts_lookahead_iterator_reset(ptr, language.getPtr(), state);
    }

    /**
     * Get the current language of the lookahead iterator.
     *
     * @return The language of the lookahead iterator
     */
    public TSLanguage getLanguage(){
        return new AnonymousLanguage(TSParser.ts_lookahead_iterator_language(ptr));
    }

    /**
     * Advance the lookahead iterator to the next symbol.
     *
     * @return <code>true</code> if there is a new symbol and <code>false</code> otherwise.
     */
    public boolean next(){
        return TSParser.ts_lookahead_iterator_next(ptr);
    }

    /**
     * Get the current symbol of the lookahead iterator
     *
     * @return Current symbol
     */
    public int currentSymbol(){
        return TSParser.ts_lookahead_iterator_current_symbol(ptr);
    }

    /**
     * Get the current symbol type of the lookahead iterator as a string.
     *
     * @return Current symbol name
     */
    public String currentSymbolName(){
        return TSParser.ts_lookahead_iterator_current_symbol_name(ptr);
    }

    protected long getPtr(){
        return ptr;
    }

    public static class TsLookAheadIteratorCleanAction implements Runnable{
        private long ptr;

        public TsLookAheadIteratorCleanAction(long ptr) {
            this.ptr = ptr;
        }

        @Override
        public void run() {
            TSParser.ts_lookahead_iterator_delete(ptr);
        }
    }
}
