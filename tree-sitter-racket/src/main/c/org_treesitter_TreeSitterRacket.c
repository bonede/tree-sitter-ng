
#include <jni.h>
void *tree_sitter_racket();
/*
 * Class:     org_treesitter_TreeSitterRacket
 * Method:    tree_sitter_racket
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterRacket_tree_1sitter_1racket
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_racket();
}
