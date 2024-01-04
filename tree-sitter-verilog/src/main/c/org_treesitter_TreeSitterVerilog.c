
#include <jni.h>
void *tree_sitter_verilog();
/*
 * Class:     org_treesitter_TreeSitterVerilog
 * Method:    tree_sitter_verilog
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterVerilog_tree_1sitter_1verilog
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_verilog();
}
