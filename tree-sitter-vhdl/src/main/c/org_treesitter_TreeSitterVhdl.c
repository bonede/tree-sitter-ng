
#include <jni.h>
void *tree_sitter_vhdl();
/*
 * Class:     org_treesitter_TreeSitterVhdl
 * Method:    tree_sitter_vhdl
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterVhdl_tree_1sitter_1vhdl
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_vhdl();
}
