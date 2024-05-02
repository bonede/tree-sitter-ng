package org.treesitter;

import static org.treesitter.TSParser.ts_tree_language;

public class TsLookAheadIterator {
    private long ptr;

    protected TsLookAheadIterator(TSLanguage language, int tsStateId) {
        this.ptr = TSParser.ts_lookahead_iterator_new(language.getPtr(), tsStateId);
        if(this.ptr == 0){
            throw new TSException("tsStateId is invalid.");
        }
        CleanerRunner.register(this, new TsLookAheadIteratorCleanAction(ptr));
    }

    public boolean resetState(int tsStateId){
        return TSParser.ts_lookahead_iterator_reset_state(ptr, tsStateId);
    }

    public boolean reset(TSLanguage language, int tsStateId){
        return TSParser.ts_lookahead_iterator_reset(ptr, language.getPtr(), tsStateId);
    }

    public TSLanguage getLanguage(){
        return new AnonymousLanguage(TSParser.ts_lookahead_iterator_language(ptr));
    }

    public boolean next(){
        return TSParser.ts_lookahead_iterator_next(ptr);
    }

    public int currentSymbol(){
        return TSParser.ts_lookahead_iterator_current_symbol(ptr);
    }
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
