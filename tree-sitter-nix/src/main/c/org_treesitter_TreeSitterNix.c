
#include <jni.h>
void *tree_sitter_nix();
/*
 * Class:     org_treesitter_TreeSitterNix
 * Method:    tree_sitter_nix
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterNix_tree_1sitter_1nix
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_nix();
}
