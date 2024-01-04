
#include <jni.h>
void *tree_sitter_svelte();
/*
 * Class:     org_treesitter_TreeSitterSvelte
 * Method:    tree_sitter_svelte
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterSvelte_tree_1sitter_1svelte
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_svelte();
}
