
#include <jni.h>
void *tree_sitter_wgsl();
/*
 * Class:     org_treesitter_TreeSitterWgsl
 * Method:    tree_sitter_wgsl
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterWgsl_tree_1sitter_1wgsl
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_wgsl();
}
