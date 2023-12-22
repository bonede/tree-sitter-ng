
#include <jni.h>
void *tree_sitter_agda();
/*
 * Class:     org_treesitter_TreeSitterAgda
 * Method:    tree_sitter_agda
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterAgda_tree_1sitter_1agda
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_agda();
}
