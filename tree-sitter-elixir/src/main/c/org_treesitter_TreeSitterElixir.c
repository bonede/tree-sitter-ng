
#include <jni.h>
void *tree_sitter_elixir();
/*
 * Class:     org_treesitter_TreeSitterElixir
 * Method:    tree_sitter_elixir
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterElixir_tree_1sitter_1elixir
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_elixir();
}
