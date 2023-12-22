package org.treesitter;

import static org.treesitter.TSParser.*;

public class TSNode {
    private int context0;
    private int context1;
    private int context2;
    private int context3;
    private long idPtr;
    private long treePtr;

    public int getChildCount() {
        return ts_node_child_count(this);
    }

    public int getNamedChildCount(){
        return ts_node_named_child_count(this);
    }
    public TSNode getNamedChild(int index) {
        return ts_node_named_child(this, index);
    }

    public String getType(){
        return ts_node_type(this);
    }

    public int getSymbol(){
        return ts_node_symbol(this);
    }

    public boolean isNull(){
        return TSParser.ts_node_is_null(this);
    }

    public boolean isNamed(){
        if(isNull()){
            throw new RuntimeException("Node is a null node");
        }
        return ts_node_is_named(this);
    }

    public boolean isMissing(){
        return ts_node_is_missing(this);
    }

    public boolean isExtra(){
        return ts_node_is_extra(this);
    }

    public boolean hasChanges(){
        return ts_node_has_changes(this);
    }

    public boolean hasError(){
        return ts_node_has_error(this);
    }

    public int getStartByte(){
        return ts_node_start_byte(this);
    }

    public int getEndByte(){
        return ts_node_end_byte(this);
    }

    public TSPoint getStartPoint(){
        return ts_node_start_point(this);
    }

    public TSPoint getEndPoint(){
        return ts_node_end_point(this);
    }

    public TSNode getParent(){
        return ts_node_parent(this);
    }

    public TSNode getChild(int index){
        return ts_node_child(this, index);
    }

    public String getFieldNameForChild(int index){
        return ts_node_field_name_for_child(this, index);
    }

    public TSNode getNextNamedSibling(){
        return ts_node_next_named_sibling(this);
    }

    public TSNode getPrevNamedSibling(){
        return ts_node_prev_named_sibling(this);
    }

    public TSNode getNextSibling(){
        return ts_node_next_sibling(this);
    }

    public TSNode getPrevSibling(){
        return ts_node_prev_sibling(this);
    }

    public TSNode getChildByFieldName(String fieldName){
        return ts_node_child_by_field_name(this, fieldName);
    }

    public TSNode getChildByFieldId(int fieldId){
        return ts_node_child_by_field_id(this, fieldId);
    }

    public TSNode getFirstChildForByte(int startByte){
        return ts_node_first_child_for_byte(this, startByte);
    }

    public TSNode getFirstNamedChildForByte(int startByte){
        return ts_node_first_named_child_for_byte(this, startByte);
    }

    public TSNode getDescendantForByteRange(int startByte, int endByte){
        return ts_node_descendant_for_byte_range(this, startByte, endByte);
    }

    public TSNode getDescendantForPointRange(TSPoint startPoint, TSPoint endPoint){
        return ts_node_descendant_for_point_range(this, startPoint, endPoint);
    }

    public TSNode getNamedDescendantForByteRange(int startByte, int endByte){
        return ts_node_named_descendant_for_byte_range(this, startByte, endByte);
    }

    public TSNode getNamedDescendantForPointRange(TSPoint startPoint, TSPoint endPoint){
        return ts_node_named_descendant_for_point_range(this, startPoint, endPoint);
    }

    public void edit(TSInputEdit inputEdit){
        TSNode tsNode = ts_node_edit(this, inputEdit);
        context0 = tsNode.context0;
        context1 = tsNode.context1;
        context2 = tsNode.context2;
        context3 = tsNode.context3;
        idPtr = tsNode.idPtr;
        treePtr = tsNode.treePtr;
    }

    public static boolean eq(TSNode a, TSNode b){
      return ts_node_eq(a, b);
    }

    @Override
    public String toString() {
        if(isNull()){
            return "null";
        }
        return TSParser.ts_node_string(this);
    }
}
