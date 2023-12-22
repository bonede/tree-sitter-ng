package org.treesitter;

import org.treesitter.utils.NativeUtils;

import java.io.*;
import java.lang.ref.Cleaner;

public class TSParser {
    static {
        NativeUtils.loadLib("lib/tree-sitter");
    }

    public  static native long ts_parser_new();
    public static native void ts_parser_delete(long tree_parser_ptr);
    private static native boolean ts_parser_set_language(long ts_parser_ptr, long ts_language_ptr);
    private static native long ts_parser_language(long ts_parser_ptr);
    private static native boolean ts_parser_set_included_ranges(long ts_parser_ptr, TSRange[] ranges);
    private static native TSRange[] ts_parser_included_ranges(long ts_parser_ptr);
    private static native long ts_parser_parse(long ts_parser_ptr,  byte[] buf, long ts_tree_ptr,TSReader reader, int tsInputEncoding);
    private static native long ts_parser_parse_string(long ts_parser_ptr, long ts_tree_ptr, String input);
    private static native long ts_parser_parse_string_encoding(long ts_parser_ptr, long ts_tree_ptr, String input, int tsInputEncoding);
    private static native void ts_parser_reset(long ts_parser_ptr);
    private static native void ts_parser_set_timeout_micros(long ts_parser_ptr, long timeout);
    private static native long ts_parser_timeout_micros(long ts_parser_ptr);
    private static native void ts_parser_set_cancellation_flag(long ts_parser_ptr, long flag_ptr);
    private static native long ts_parser_cancellation_flag(long ts_parser_ptr);
    private static native long alloc_cancellation_flag();
    private static native long get_cancellation_flag_value(long flag_ptr);
    private static native void free_cancellation_flag(long flag_ptr);
    private static native void write_cancellation_flag(long flag_ptr, long value);
    private static native int ts_language_version(long ts_language_ptr);
    private static native void ts_parser_set_logger(long ts_parser_ptr, TSLogger logger);
    private static native void free_logger(long ts_parser_ptr);
    private static native void ts_parser_print_dot_graphs(long ts_parser_ptr, FileDescriptor fileDescriptor);
    protected static native long ts_tree_copy(long tree_ptr);
    protected static native void ts_tree_delete(long tree_ptr);
    protected static native TSNode ts_tree_root_node(long tree_ptr);
    protected static native TSNode ts_tree_root_node_with_offset(long tree_ptr, int offsetBytes, TSPoint offsetPoint);
    protected static native long ts_tree_language(long tree_ptr);
    protected static native TSRange[] ts_tree_included_ranges(long tree_ptr);
    protected static native void ts_tree_edit(long tree_ptr, TSInputEdit edit);
    protected static native TSRange[] ts_tree_get_changed_ranges(long old_tree_ptr, long new_tree_ptr);
    protected static native String ts_node_type(TSNode node);
    protected static native int ts_node_symbol(TSNode node);
    protected static native int ts_node_start_byte(TSNode node);
    protected static native TSPoint ts_node_start_point(TSNode node);
    protected static native int ts_node_end_byte(TSNode node);
    protected static native TSPoint ts_node_end_point(TSNode node);
    protected static native String ts_node_string(TSNode node);
    protected static native boolean ts_node_is_null(TSNode node);
    protected static native boolean ts_node_is_named(TSNode node);
    protected static native boolean ts_node_is_missing(TSNode node);
    protected static native boolean ts_node_is_extra(TSNode node);
    protected static native boolean ts_node_has_changes(TSNode node);
    protected static native boolean ts_node_has_error(TSNode node);
    protected static native TSNode ts_node_parent(TSNode node);
    protected static native TSNode ts_node_child(TSNode node, int index);
    protected static native String ts_node_field_name_for_child(TSNode node, int index);
    protected static native int ts_node_child_count(TSNode node);
    protected static native TSNode ts_node_named_child(TSNode node, int index);
    protected static native int ts_node_named_child_count(TSNode node);
    protected static native TSNode ts_node_child_by_field_name(TSNode node, String field_name);
    protected static native TSNode ts_node_child_by_field_id(TSNode node, int ts_field_id);
    protected static native TSNode ts_node_next_sibling(TSNode node);
    protected static native TSNode ts_node_prev_sibling(TSNode node);
    protected static native TSNode ts_node_next_named_sibling(TSNode node);
    protected static native TSNode ts_node_prev_named_sibling(TSNode node);
    protected static native TSNode ts_node_first_child_for_byte(TSNode node, int start_byte);
    protected static native TSNode ts_node_first_named_child_for_byte(TSNode node, int start_byte);
    protected static native TSNode ts_node_descendant_for_byte_range(TSNode node, int start_byte, int end_byte);
    protected static native TSNode ts_node_descendant_for_point_range(TSNode node, TSPoint start_point, TSPoint end_point);
    protected static native TSNode ts_node_named_descendant_for_byte_range(TSNode node, int start_byte, int end_byte);
    protected static native TSNode ts_node_named_descendant_for_point_range(TSNode node, TSPoint start_point, TSPoint end_point);
    protected static native TSNode ts_node_edit(TSNode node, TSInputEdit edit);
    protected static native boolean ts_node_eq(TSNode a, TSNode b);
    protected static native void free_cursor(long ts_tree_cursor_ptr);
    protected static native long ts_tree_cursor_new(TSNode node);
    protected static native void ts_tree_cursor_delete(long cursor_ptr);
    protected static native void ts_tree_cursor_reset(long cursor_ptr, TSNode node);
    protected static native TSNode ts_tree_cursor_current_node(long cursor_ptr);
    protected static native String ts_tree_cursor_current_field_name(long cursor_ptr);
    protected static native int ts_tree_cursor_current_field_id(long cursor_ptr);
    protected static native boolean ts_tree_cursor_goto_parent(long cursor_ptr);
    protected static native boolean ts_tree_cursor_goto_next_sibling(long cursor_ptr);
    protected static native boolean ts_tree_cursor_goto_first_child(long cursor_ptr);
    protected static native int ts_tree_cursor_goto_first_child_for_byte(long cursor_ptr, int startByte);
    protected static native int ts_tree_cursor_goto_first_child_for_point(long cursor_ptr, TSPoint startPoint);
    protected static native long ts_tree_cursor_copy(long cursor_ptr);
    protected static native long ts_query_new(long ts_language_ptr, String source);
    protected static native void ts_query_delete(long ts_query_ptr);
    protected static native int ts_query_pattern_count(long ts_query_ptr);
    protected static native int ts_query_capture_count(long ts_query_ptr);
    protected static native int ts_query_string_count(long ts_query_ptr);
    protected static native int ts_query_start_byte_for_pattern(long ts_query_ptr, int pattern_index);
    protected static native TSQueryPredicateStep[] ts_query_predicates_for_pattern(long ts_query_ptr, int pattern_index);
    protected static native boolean ts_query_is_pattern_rooted(long ts_query_ptr, int pattern_index);
    protected static native boolean ts_query_is_pattern_non_local(long ts_query_ptr, int pattern_index);
    protected static native boolean ts_query_is_pattern_guaranteed_at_step(long ts_query_ptr, int byte_offset);
    protected static native String ts_query_capture_name_for_id(long ts_query_ptr, int index);
    protected static native int ts_query_capture_quantifier_for_id(long ts_query_ptr, int pattern_id, int index);
    protected static native String ts_query_string_value_for_id(long ts_query_ptr, int index);
    protected static native void ts_query_disable_capture(long ts_query_ptr, String name);
    protected static native void ts_query_disable_pattern(long ts_query_ptr, int patter_index);
    protected static native long ts_query_cursor_new();
    protected static native void ts_query_cursor_delete(long ts_query_cursor_ptr);
    protected static native void ts_query_cursor_exec(long ts_query_cursor_ptr, long ts_query_ptr, TSNode node);
    protected static native boolean ts_query_cursor_did_exceed_match_limit(long ts_query_cursor_ptr);
    protected static native int ts_query_cursor_match_limit(long ts_query_cursor_ptr);
    protected static native void ts_query_cursor_set_match_limit(long ts_query_cursor_ptr, int limit);
    protected static native void ts_query_cursor_set_byte_range(long ts_query_cursor_ptr, int start_byte, int end_byte);
    protected static native void ts_query_cursor_set_point_range(long ts_query_cursor_ptr, TSPoint start_point, TSPoint end_point);
    protected static native boolean ts_query_cursor_next_match(long ts_query_cursor_ptr, TSQueryMatch match);
    protected static native void ts_query_cursor_remove_match(long ts_query_cursor_ptr, int match_id);
    protected static native boolean ts_query_cursor_next_capture(long ts_query_cursor_ptr, TSQueryMatch match);
    protected static native void ts_tree_print_dot_graph(long ts_tree_ptr, FileDescriptor fileDescriptor);
    private static native int ts_language_field_count(long ts_language_ptr);
    private static native String ts_language_field_name_for_id(long ts_language_ptr, int ts_field_id);
    private static native int ts_language_field_id_for_name(long ts_language_ptr, String field_name);
    private static native int ts_language_symbol_type(long ts_language_ptr, int ts_symbol);
    private static native int ts_language_symbol_count(long ts_language_ptr);
    private static native String ts_language_symbol_name(long ts_language_ptr, int ts_symbol);
    private static native int ts_language_symbol_for_name(long ts_language_ptr, String name, boolean is_named);

    private final long ptr;


    static class TSParserCleaner implements Runnable{
        private final long ptr;

        public TSParserCleaner(long ptr) {
            this.ptr = ptr;
        }

        @Override
        public void run() {
            long flagPtr = ts_parser_cancellation_flag(this.ptr);
            if(flagPtr != 0){
                free_cancellation_flag(flagPtr);
            }
            free_logger(ptr);
            ts_parser_delete(ptr);
        }
    }

    static Cleaner cleaner = Cleaner.create();
    private TSLogger logger;
    /**
     * Create a new parser.
     */
    public TSParser() {
        this.ptr = ts_parser_new();
        long cancellationFlagPtr = alloc_cancellation_flag();
        write_cancellation_flag(cancellationFlagPtr, 0);
        ts_parser_set_cancellation_flag(ptr, cancellationFlagPtr);
        cleaner.register(this, new TSParserCleaner(this.ptr));
    }

    public TSLogger getLogger() {
        return logger;
    }

    public void setLogger(TSLogger logger) {
        this.logger = logger;
        ts_parser_set_logger(ptr, logger);
    }

    /**
     **
     * Set the language that the parser should use for parsing.
     * Returns a boolean indicating whether or not the language was successfully
     * assigned. True means assignment succeeded. False means there was a version
     * mismatch: the language was generated with an incompatible version of the
     * Tree-sitter CLI. Check the language's version using `ts_language_version`
     * and compare it to this library's `TREE_SITTER_LANGUAGE_VERSION` and
     * `TREE_SITTER_MIN_COMPATIBLE_LANGUAGE_VERSION` constants.
     * @param language
     */
    public boolean setLanguage(TSLanguage language) {
        return ts_parser_set_language(ptr, language.getPtr());
    }

    public static int getLanguageVersion(TSLanguage lang) {
        return ts_language_version(lang.getPtr());
    }
    public static TSSymbolType getLanguageSymbolType(TSLanguage lang, int symbol) {
        int type = ts_language_symbol_type(lang.getPtr(), symbol);
        switch (type){
            case 0: return TSSymbolType.TSSymbolTypeRegular;
            case 1: return TSSymbolType.TSSymbolTypeAnonymous;
            case 2: return TSSymbolType.TSSymbolTypeAnonymous;
            default: throw new TSException("Can't handle symbol type: %d" + type);
        }
    }
    public static String getLanguageSymbolName(TSLanguage lang, int symbol) {
        return ts_language_symbol_name(lang.getPtr(), symbol);
    }
    public static int languageSymbolForName(TSLanguage lang, String name, boolean isNamed) {
        return ts_language_symbol_for_name(lang.getPtr(), name, isNamed);
    }
    public static int getLanguageSymbolCount(TSLanguage lang) {
        return ts_language_symbol_count(lang.getPtr());
    }
    public static int getFieldCount(TSLanguage lang) {
        return ts_language_field_count(lang.getPtr());
    }

    public static String getFieldNameForId(TSLanguage lang, int fieldId){
        return ts_language_field_name_for_id(lang.getPtr(), fieldId);
    }

    public static int getFieldIdForName(TSLanguage lang, String fieldName){
        return ts_language_field_id_for_name(lang.getPtr(), fieldName);
    }

    public TSTree parseString(TSTree oldTree, String input) {
        long oldTreePtr = oldTree == null ? 0 : oldTree.getPtr();
        long treePtr = ts_parser_parse_string(ptr, oldTreePtr, input);
        return new TSTree(treePtr);
    }

    public TSTree parseStringEncoding(TSTree oldTree, String input, TSInputEncoding encoding){
        long oldTreePtr = oldTree == null ? 0 : oldTree.getPtr();
        long treePtr = ts_parser_parse_string_encoding(ptr, oldTreePtr, input, encoding.ordinal());
        return new TSTree(treePtr);
    }

    public TSTree parse(byte[] buf, TSTree oldTree, TSReader reader, TSInputEncoding encoding){
        long oldTreePtr = oldTree == null ? 0 : oldTree.getPtr();
        long treePtr = ts_parser_parse(ptr, buf, oldTreePtr, reader, encoding.ordinal());
        return new TSTree(treePtr);
    }

    public TSLanguage getLanguage(){
        return () -> ts_parser_language(ptr);
    }

    public boolean setIncludedRanges(TSRange[] ranges) {
        return ts_parser_set_included_ranges(ptr, ranges);
    }

    public TSRange[] getIncludedRanges() {
        return ts_parser_included_ranges(ptr);
    }

    public void reset(){
        ts_parser_reset(ptr);
    }

    public void setTimeoutMicros(long timeoutMicros){
        ts_parser_set_timeout_micros(ptr, timeoutMicros);
    }

    public long getTimeoutMicros(){
        return ts_parser_timeout_micros(ptr);
    }
    public void setCancellationFlag(long flag){
        write_cancellation_flag(ts_parser_cancellation_flag(ptr), flag);
    }

    public long getCancellationFlag(){
        return get_cancellation_flag_value(ts_parser_cancellation_flag(ptr));
    }
    public void printDotGraphs(File file) throws IOException {
        // TODO windows support
        FileOutputStream outputStream = new FileOutputStream(file);
        ts_parser_print_dot_graphs(ptr, outputStream.getFD());
        outputStream.close();
    }
}
