package org.treesitter;

import static org.treesitter.TSParser.*;

public class TSNode {
    private int context0;
    private int context1;
    private int context2;
    private int context3;
    private long idPtr;
    private long treePtr;

    /**
     * Get the node's number of children.
     */
    public int getChildCount() {
        return ts_node_child_count(this);
    }

    /**
     * Get the node's number of *named* children.<br>
     *
     * See also {@link #isNamed()}.
     */
    public int getNamedChildCount(){
        return ts_node_named_child_count(this);
    }

    /**
     * Get the node's *named* child at the given index.<br>
     *
     * See also {@link #isNamed()}.
     */
    public TSNode getNamedChild(int index) {
        return ts_node_named_child(this, index);
    }
    /**
     * Get the node's type as a string.
     */
    public String getType(){
        return ts_node_type(this);
    }

    /**
     * Get the node's type as a numerical id.
     */
    public int getSymbol(){
        return ts_node_symbol(this);
    }

    /**
     * Check if the node is null. Functions like {@link #getChild(int) getChild()} and
     * {@link #getNextSibling()} will return a null node to indicate that no such node
     * was found.
     */
    public boolean isNull(){
        return TSParser.ts_node_is_null(this);
    }

    /**
     * Check if the node is *named*. Named nodes correspond to named rules in the
     * grammar, whereas *anonymous* nodes correspond to string literals in the
     * grammar.
     */
    public boolean isNamed(){
        if(isNull()){
            throw new RuntimeException("Node is a null node");
        }
        return ts_node_is_named(this);
    }

    /**
     * Check if the node is *missing*. Missing nodes are inserted by the parser in
     * order to recover from certain kinds of syntax errors.
     */
    public boolean isMissing(){
        return ts_node_is_missing(this);
    }

    /**
     * Check if the node is *extra*. Extra nodes represent things like comments,
     * which are not required the grammar, but can appear anywhere.
     */
    public boolean isExtra(){
        return ts_node_is_extra(this);
    }

    /**
     * Check if a syntax node has been edited.
     */
    public boolean hasChanges(){
        return ts_node_has_changes(this);
    }

    /**
     * Check if the node is a syntax error or contains any syntax errors.
     */
    public boolean hasError(){
        return ts_node_has_error(this);
    }

    /**
     * Get the node's start byte.
     */
    public int getStartByte(){
        return ts_node_start_byte(this);
    }

    /**
     * Get the node's end byte.
     */
    public int getEndByte(){
        return ts_node_end_byte(this);
    }

    /**
     * Get the node's start position in terms of rows and columns.
     */
    public TSPoint getStartPoint(){
        return ts_node_start_point(this);
    }

    /**
     * Get the node's end position in terms of rows and columns.
     */
    public TSPoint getEndPoint(){
        return ts_node_end_point(this);
    }

    /**
     * Get the node's immediate parent.
     */
    public TSNode getParent(){
        return ts_node_parent(this);
    }

    /**
     * Get the node's child at the given index, where zero represents the first
     * child.
     */
    public TSNode getChild(int index){
        return ts_node_child(this, index);
    }

    /**
     * Get the field name for node's child at the given index, where zero represents
     * the first child. Returns <code>null</code>, if no field is found.
     */
    public String getFieldNameForChild(int index){
        return ts_node_field_name_for_child(this, index);
    }

    /**
     * Get the node's next *named* sibling.
     */
    public TSNode getNextNamedSibling(){
        return ts_node_next_named_sibling(this);
    }

    /**
     * Get the node's previous *named* sibling.
     */
    public TSNode getPrevNamedSibling(){
        return ts_node_prev_named_sibling(this);
    }

    /**
     * Get the node's next sibling.
     */
    public TSNode getNextSibling(){
        return ts_node_next_sibling(this);
    }

    /**
     * Get the node's previous sibling.
     */
    public TSNode getPrevSibling(){
        return ts_node_prev_sibling(this);
    }

    /**
     * Get the node's child with the given field name.
     */
    public TSNode getChildByFieldName(String fieldName){
        return ts_node_child_by_field_name(this, fieldName);
    }

    /**
     * Get the node's child with the given numerical field id.<br>
     *
     * You can convert a field name to an id using the
     * {@link TSLanguage#fieldIdForName(String) fieldIdForName()} function.
     */
    public TSNode getChildByFieldId(int fieldId){
        return ts_node_child_by_field_id(this, fieldId);
    }

    /**
     * Get the node's first child that extends beyond the given byte offset.
     */
    public TSNode getFirstChildForByte(int startByte){
        return ts_node_first_child_for_byte(this, startByte);
    }

    /**
     * Get the node's first named child that extends beyond the given byte offset.
     */
    public TSNode getFirstNamedChildForByte(int startByte){
        return ts_node_first_named_child_for_byte(this, startByte);
    }
    /**
     * Get the smallest node within this node that spans the given range of bytes.
     */
    public TSNode getDescendantForByteRange(int startByte, int endByte){
        return ts_node_descendant_for_byte_range(this, startByte, endByte);
    }
    /**
     * Get the smallest node within this node that spans the given (row, column) positions.
     */
    public TSNode getDescendantForPointRange(TSPoint startPoint, TSPoint endPoint){
        return ts_node_descendant_for_point_range(this, startPoint, endPoint);
    }
    /**
     * Get the smallest named node within this node that spans the given range of
     * bytes.
     */
    public TSNode getNamedDescendantForByteRange(int startByte, int endByte){
        return ts_node_named_descendant_for_byte_range(this, startByte, endByte);
    }

    /**
     * Get the smallest named node within this node that spans the given range of
     * (row, column) positions.
     */
    public TSNode getNamedDescendantForPointRange(TSPoint startPoint, TSPoint endPoint){
        return ts_node_named_descendant_for_point_range(this, startPoint, endPoint);
    }

    /**
     * Edit the node to keep it in-sync with source code that has been edited.<br>
     *
     * This function is only rarely needed. When you edit a syntax tree with the
     * {@link TSTree#edit(TSInputEdit) TStree#edit()} function, all the nodes that you retrieve from the tree
     * afterward will already reflect the edit. You only need to use this function
     * when you have a {@link TSNode} instance that you want to keep and continue to use
     * after an edit.
     */
    public void edit(TSInputEdit inputEdit){
        TSNode tsNode = ts_node_edit(this, inputEdit);
        context0 = tsNode.context0;
        context1 = tsNode.context1;
        context2 = tsNode.context2;
        context3 = tsNode.context3;
        idPtr = tsNode.idPtr;
        treePtr = tsNode.treePtr;
    }
    
    /**
     * Check if two nodes are identical.
     */
    public static boolean eq(TSNode a, TSNode b){
      return ts_node_eq(a, b);
    }

    /**
     * Get an S-expression representing the node as a string.<br>
     *
     */
    @Override
    public String toString() {
        if(isNull()){
            return "null";
        }
        return TSParser.ts_node_string(this);
    }
}
