
#include <jni.h>
void *tree_sitter_cpp();
/*
 * Class:     org_treesitter_TreeSitterCpp
 * Method:    tree_sitter_cpp
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterCpp_tree_1sitter_1cpp
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_cpp();
}
