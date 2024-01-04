
#include <jni.h>
void *tree_sitter_zig();
/*
 * Class:     org_treesitter_TreeSitterZig
 * Method:    tree_sitter_zig
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterZig_tree_1sitter_1zig
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_zig();
}
