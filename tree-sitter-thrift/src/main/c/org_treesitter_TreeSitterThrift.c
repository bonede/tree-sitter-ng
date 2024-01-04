
#include <jni.h>
void *tree_sitter_thrift();
/*
 * Class:     org_treesitter_TreeSitterThrift
 * Method:    tree_sitter_thrift
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterThrift_tree_1sitter_1thrift
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_thrift();
}
