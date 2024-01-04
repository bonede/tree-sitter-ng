
#include <jni.h>
void *tree_sitter_llvm();
/*
 * Class:     org_treesitter_TreeSitterLlvm
 * Method:    tree_sitter_llvm
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterLlvm_tree_1sitter_1llvm
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_llvm();
}
