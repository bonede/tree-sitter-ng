
#include <jni.h>
void *tree_sitter_pgn();
/*
 * Class:     org_treesitter_TreeSitterPgn
 * Method:    tree_sitter_pgn
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_treesitter_TreeSitterPgn_tree_1sitter_1pgn
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_pgn();
}
