package org.treesitter;

public class TSParser {

    private long ptr;
    private static native long ts_parser_new();
    private static native long ts_parser_set_language(long ts_parser_ptr, long ts_language_ptr);

    private static native long ts_parser_parse_string(long ts_parser_pt, long ts_tree_ptr, String input);

    public TSParser() {
        ptr = ts_parser_new();
    }

    public void setLang(TSLanguage lang) {
        ts_parser_set_language(ptr, lang.getPtr());
    }

    public TSTree parseString(TSTree oldTree, String input) {
        long treePtr = ts_parser_parse_string(ptr, oldTree.getPtr(), input);
        return new TSTree(treePtr);
    }


}
