
#include <jni.h>
void *tree_sitter_erlang();
/*
 * Class:     org_treesitter_TreeSitterErlang
 * Method:    tree_sitter_erlang
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterErlang_tree_1sitter_1erlang
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_erlang();
}
