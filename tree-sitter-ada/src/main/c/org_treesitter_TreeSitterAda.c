#include <jni.h>
void *tree_sitter_ada();
/*
 * Class:     org_treesitter_TreeSitterAda
 * Method:    tree_sitter_ada
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterAda_tree_1sitter_1ada
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_ada();
}