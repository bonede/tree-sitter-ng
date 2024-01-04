
#include <jni.h>
void *tree_sitter_llvm_mir();
/*
 * Class:     org_treesitter_TreeSitterLlvmMir
 * Method:    tree_sitter_llvm_mir
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterLlvmMir_tree_1sitter_1llvm_1mir
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_llvm_mir();
}
