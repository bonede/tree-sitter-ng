
#include <jni.h>
void *tree_sitter_haskell();
/*
 * Class:     org_treesitter_TreeSitterHaskell
 * Method:    tree_sitter_haskell
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterHaskell_tree_1sitter_1haskell
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_haskell();
}
