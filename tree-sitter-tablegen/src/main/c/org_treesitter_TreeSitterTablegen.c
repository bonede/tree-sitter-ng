
#include <jni.h>
void *tree_sitter_tablegen();
/*
 * Class:     org_treesitter_TreeSitterTablegen
 * Method:    tree_sitter_tablegen
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterTablegen_tree_1sitter_1tablegen
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_tablegen();
}
