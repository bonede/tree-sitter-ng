
#include <jni.h>
void *tree_sitter_julia();
/*
 * Class:     org_treesitter_TreeSitterJulia
 * Method:    tree_sitter_julia
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterJulia_tree_1sitter_1julia
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_julia();
}
