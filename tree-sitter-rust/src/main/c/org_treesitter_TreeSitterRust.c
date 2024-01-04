
#include <jni.h>
void *tree_sitter_rust();
/*
 * Class:     org_treesitter_TreeSitterRust
 * Method:    tree_sitter_rust
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterRust_tree_1sitter_1rust
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_rust();
}
