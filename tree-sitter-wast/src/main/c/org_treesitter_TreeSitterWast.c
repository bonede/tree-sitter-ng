
#include <jni.h>
void *tree_sitter_wast();
/*
 * Class:     org_treesitter_TreeSitterWast
 * Method:    tree_sitter_wast
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterWast_tree_1sitter_1wast
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_wast();
}
