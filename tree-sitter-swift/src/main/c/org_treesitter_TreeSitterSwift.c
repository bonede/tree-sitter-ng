
#include <jni.h>
void *tree_sitter_swift();
/*
 * Class:     org_treesitter_TreeSitterSwift
 * Method:    tree_sitter_swift
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterSwift_tree_1sitter_1swift
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_swift();
}
