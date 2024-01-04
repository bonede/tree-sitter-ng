
#include <jni.h>
void *tree_sitter_kotlin();
/*
 * Class:     org_treesitter_TreeSitterKotlin
 * Method:    tree_sitter_kotlin
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterKotlin_tree_1sitter_1kotlin
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_kotlin();
}
