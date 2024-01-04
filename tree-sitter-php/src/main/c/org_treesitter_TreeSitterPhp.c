
#include <jni.h>
void *tree_sitter_php();
/*
 * Class:     org_treesitter_TreeSitterPhp
 * Method:    tree_sitter_php
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterPhp_tree_1sitter_1php
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_php();
}
